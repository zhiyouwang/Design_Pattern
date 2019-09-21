package com.JnuWangIO;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        String str = null;
        ArrayList<ArrayList<Double>> datas = new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> test = new ArrayList<ArrayList<Double>>();
        try {
            // 读取训练集数据训练参数向量
            FileInputStream fis = new FileInputStream("C:\\Users\\zfw\\Desktop\\java项目\\datas.txt");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            while ((str = br.readLine()) != null) {
                String[] strs = str.split(",");
                ArrayList<Double> array = new ArrayList<Double>();
                array.add(1.0);
                for (int i = 0; i < strs.length; i++) {
                    array.add(Double.parseDouble(strs[i]));
                    //System.out.println(strs[i]);
                }
                datas.add(array);
            }
            br.close();
            FileInputStream fis1 = new FileInputStream("C:\\Users\\zfw\\Desktop\\java项目\\test.txt");
            InputStreamReader isr1 = new InputStreamReader(fis1, "UTF-8");
            BufferedReader br1 = new BufferedReader(isr1);
            while ((str = br1.readLine()) != null) {
                String[] strs = str.split(",");
                ArrayList<Double> array = new ArrayList<Double>();
                for (int i = 0; i < strs.length; i++) {
                    array.add(Double.parseDouble(strs[i]));
                    //System.out.println(strs[i]);
                }
                test.add(array);
            }
            br1.close();
        } catch (IOException ioe) {
            System.out.println("错误！" + ioe);
        }

        Logistic result = new Logistic(datas, test);

        result.print();
        result.predect(test);
    }

}