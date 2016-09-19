package nju.math;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Genotype {

	private static int cases = 1000;// 样本数
	private static int locus = 9445;// 位点数

	private static String[][] array = new String[cases + 1][locus];

	public static void readData() throws Exception {
		File file = new File("data/genotype.dat");
		Scanner in = new Scanner(file);
		for (int i = 0; i <= cases; i++) {
			for (int j = 0; j < locus; j++) {
				array[i][j] = in.next();
			}
		}
	}

	public static void processOneColumn(int index) {
		Set<String> set = new HashSet<>();
		for (int i = 1; i <= cases; i++) {
			set.add(array[i][index]);
		}
		Map<String, Integer> map = new HashMap<>();
		int code = 0;
		for (String gen : set) {
			map.put(gen, code++);
		}
		for (int i = 1; i <= cases; i++) {
			array[i][index] = map.get(array[i][index]) + "";
		}
	}

	public static void process() {
		for (int i = 0; i < locus; i++) {
			processOneColumn(i);
		}
	}

	public static void writeFile() {
		try{
			FileWriter fw = new FileWriter("D:/weka.txt");
			for (int i = 0; i <=cases; i++) {
				for (int j = 0; j < locus; j++) {
					fw.write(array[i][j]+",");
				}
				fw.write("\n");
			}
			fw.flush();
			fw.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		readData();
		process();
		writeFile();
	}
}
