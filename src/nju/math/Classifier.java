package nju.math;

import java.io.File;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class Classifier {
	public static void main(String[] args) throws Exception {
		NaiveBayes m_classifier = new NaiveBayes();
		// 训练语料文件，官方自带的 demo 里有
		File inputFile = new File("data\\genotype.arff");
		ArffLoader atf = new ArffLoader();
		atf.setFile(inputFile);
		Instances instancesTrain = atf.getDataSet(); // 读入训练文件
		instancesTrain.setClassIndex(instancesTrain.numAttributes()-1);
		m_classifier.buildClassifier(instancesTrain); // 训练
		Evaluation eval = new Evaluation(instancesTrain);
		eval.crossValidateModel(m_classifier, instancesTrain, 10, new Random(1));
		System.out.println("result:"+eval.toSummaryString());
	}

}
