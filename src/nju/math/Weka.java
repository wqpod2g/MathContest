package nju.math;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class Weka {
	
	private static int cases = 1000;// 样本数
	private static int locus = 9445*3;// 位点数
	
	private static int[] attrIndex;//排序后的属性
	
	 /**
     * @param args
     */
    public static void AttributeSelect() {
       Instances trainIns = null;
      
       try{
          
           /*
            * 1.读入训练
            * 在此我们将训练样本和测试样本是由weka提供的segment数据集构成的
            */
           File file= new File("data/training_data.arff");
           ArffLoader loader = new ArffLoader();
           loader.setFile(file);
           trainIns = loader.getDataSet();
          
           //在使用样本之前一定要首先设置instances的classIndex，否则在使用instances对象是会抛出异常
           trainIns.setClassIndex(trainIns.numAttributes()-1);
          
           /*
            * 2.初始化搜索算法（search method）及属性评测算法（attribute evaluator）
            */
           Ranker rank = new Ranker();
           InfoGainAttributeEval eval = new InfoGainAttributeEval();
          
           /*
            * 3.根据评测算法评测各个属性
            */
           eval.buildEvaluator(trainIns);
           //System.out.println(rank.search(eval, trainIns));
          
           /*
            * 4.按照特定搜索算法对属性进行筛选
            * 在这里使用的Ranker算法仅仅是属性按照InfoGain的大小进行排序
            */
           attrIndex = rank.search(eval, trainIns);
          
       }catch(Exception e){
           e.printStackTrace();
       }
    }
    
    public static int[] getDeleteKAttr(int k) {
    	int[] array = Arrays.copyOfRange(attrIndex, k, locus);
    	Arrays.sort(array);
    	return array;
    }
    
    public static void TrainWithMainAttri(int[] DeleteAttriArray) {
    	try{
    		NaiveBayes m_classifier = new NaiveBayes();
    		// 训练语料文件
    		File inputFile = new File("data\\training_data.arff");
    		ArffLoader atf = new ArffLoader();
    		atf.setFile(inputFile);
    		Instances instancesTrain = atf.getDataSet(); // 读入训练文件
    		Remove filter = new Remove();
    		filter.setAttributeIndicesArray(DeleteAttriArray);
    		filter.setInputFormat(instancesTrain);
    		instancesTrain = Filter.useFilter(instancesTrain, filter);
    		
    		instancesTrain.setClassIndex(instancesTrain.numAttributes()-1);
    		m_classifier.buildClassifier(instancesTrain); // 训练
    		Evaluation eval = new Evaluation(instancesTrain);
    		eval.crossValidateModel(m_classifier, instancesTrain, 10, new Random(1));
    		System.out.println("result:"+eval.pctCorrect());
    	}catch(Exception e ) {
    		e.printStackTrace();
    	}
    }
    
    public static void process() {
    	for(int i=1;i<=50;i++) {
    		int[] array = getDeleteKAttr(i);
        	TrainWithMainAttri(array);
    	}
    }
    
    public static void main(String[] args) {
    	AttributeSelect();
    	process();
    }
 
}

