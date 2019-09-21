package com.JnuWangIO;

import java.util.ArrayList;

public class Logistic {

    //训练集
    private ArrayList<ArrayList<Double>> datas;

    //学习速率
    private double alph = 0.001;

    //参数向量
    private Double[] b;

    public Logistic(ArrayList<ArrayList<Double>> datas, ArrayList<ArrayList<Double>> test) {
        this.datas = datas;
        init(datas);
    }

    /**
     * 初始化参数向量
     * @param datas 训练集
     */
    public void init(ArrayList<ArrayList<Double>> datas) {
        b = new Double[this.datas.get(0).size() - 1];
        System.out.println(b.length);
        for (int i = 0; i < b.length; i++) {
            b[i] = 1.0;
        }
    }

    /**
     * 预测分类函数
     * @param j
     * @return
     */
    public double h_theta_x_i(int j) {
        double c = 1.0;
        for (int i = 1; i < this.b.length; i++) {
            c += this.b[i] * this.datas.get(j).get(i);
        }
        return 1.0 / (1 + Math.exp(0.0 - c));
    }

    /**
     * 求thetaj的偏导
     * @param j
     * @return
     */
    public double compute_partial_derivative_for_theta(int j) {
        double sum = 0.0;
        for (int i = 0; i < this.datas.size(); i++) {
            sum += (datas.get(i).get(datas.get(0).size() - 1) - h_theta_x_i(i)) * datas.get(i).get(j);
        }
        return sum;
    }

    /**
     * 迭代求theta
     */
    public void compute_theta() {
        for (int i = 1; i < b.length; i++) {
            b[i] += this.alph * compute_partial_derivative_for_theta(i);
        }
    }

    /**
     * 打印
     */
    public void print() {
        int a = 1000000;
        while (a > 0) {
            a--;
            compute_theta();
            System.out.print(a + "theta:");
            for (int i = 0; i < b.length; i++) {
                System.out.print(b[i] + "\t");
            }
            System.out.println();
        }
    }

    /**
     * 对测试集进行预测
     * @param test
     */
    public void predect(ArrayList<ArrayList<Double>> test) {
        int count = 0;
        double sum = 0.0;
        for (int i = 0; i < test.size(); i++) {
            for (int j = 0; j < test.get(0).size() - 1; j++) {
                sum += this.b[j + 1] * test.get(i).get(j);
            }
            if ((1.0 / (1 + Math.exp(0.0 - sum))) > 0.5) {
                System.out.print(1);
                if (test.get(i).get((test.get(i).size() - 1)) == 1.0) {
                    count++;
                }
            } else {
                System.out.print(0);
                if (test.get(i).get((test.get(i).size() - 1)) == 0.0) {
                    count++;
                }
            }
        }
        System.out.println("正确率为：" + (double) count / test.size() * 100 + "%");
    }
}