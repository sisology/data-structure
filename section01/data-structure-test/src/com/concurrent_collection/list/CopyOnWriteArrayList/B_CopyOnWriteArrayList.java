package com.concurrent_collection.list.CopyOnWriteArrayList;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class B_CopyOnWriteArrayList {

    public static void main(String[] args) {

        List<Integer> sharedList = new CopyOnWriteArrayList<>();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                // ArrayList에 값 추가
                sharedList.add(i);
            }
        };

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(task);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        // 모든 스레드가 종료될 때까지 대기
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Expected size: " + (10 * 1000));
        System.out.println("Actual size: " + sharedList.size());

    }

}
