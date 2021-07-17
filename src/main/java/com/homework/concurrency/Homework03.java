package com.homework.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池， 异步运行一个方法，拿到这个方法的返回值后，退出主线程？ 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class Homework03 {

  public static void main(String[] args) {
    // 在这里创建一个线程或线程池，
    // 异步执行 下面方法
    method1();
    method2();
    method3();
  }

  private static void method1() {
    long start = System.currentTimeMillis();
    FutureTask<Integer> task = new FutureTask<>(() -> sum());
    Thread thread = new Thread(task);
    thread.setDaemon(true);
    thread.start();
    try {
      System.out.println("method1计算结果: " + task.get());
      System.out.println("method1使用时间：" + (System.currentTimeMillis() - start) + " ms");
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  private static void method2() {
    long start = System.currentTimeMillis();
    ExecutorService es = Executors.newSingleThreadExecutor();
    FutureTask<Integer> task = new FutureTask<>(() -> sum());
    es.execute(task);
    es.shutdown();
    try {
      System.out.println("method2计算结果: " + task.get());
      System.out.println("method2使用时间：" + (System.currentTimeMillis() - start) + " ms");
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  private static void method3() {
    long start = System.currentTimeMillis();
    ExecutorService es = Executors.newCachedThreadPool();
    Future<Integer> result = es.submit(() -> sum());
    es.shutdown();
    try {
      System.out.println("method3计算结果: " + result.get());
      System.out.println("method3使用时间：" + (System.currentTimeMillis() - start) + " ms");
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

  private static int sum() {
    return fibo(36);
  }

  private static int fibo(int a) {
      if (a < 2) {
          return 1;
      }
    return fibo(a - 1) + fibo(a - 2);
  }
}
