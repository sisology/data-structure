package com.collection.list;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ArrayListVSLinkedList {

    /**
     * ArrayList add 걸린 시간  7350709 ns
     * LinkedList add 걸린 시간  2954667 ns

     * ArrayList get 걸린 시간     4042 ns
     * LinkedList get 걸린 시간   303584 ns

     * 나노초(nanosecond)는 10억 분의 1초를 의미
     * */

    public static void main(String[] args) {
        //Arraylist 컬렉션 객체 생성
        List<String> list1 = new ArrayList<String>();

        //LinkedList 컬렉션 객체 생성
        List<String> list2 = new LinkedList<String>();

        //시작 시간과 끝 시간을 저장할 변수 선언
        long startTime;
        long endTime;

        //Array 데이터 10만개 추가
        startTime = System.nanoTime();
        for(int i=0; i<100_000; i++){
            list1.add(String.valueOf(i));
        }
        endTime = System.nanoTime();
        System.out.printf("%-17s %8d ns \n", "ArrayList add 걸린 시간", (endTime - startTime));

        //LinkedList 데이터 10만개 추가
        startTime = System.nanoTime();
        for(int i=0; i<100_000; i++){
            list2.add(String.valueOf(i));
        }
        endTime = System.nanoTime();
        System.out.printf("%-17s %8d ns \n", "LinkedList add 걸린 시간", (endTime - startTime));

        //Array get()
        startTime = System.nanoTime();
        list1.get(53404);
        endTime = System.nanoTime();
        System.out.printf("%-17s %8d ns \n", "ArrayList get 걸린 시간", (endTime - startTime));

        //LinkedList get()
        startTime = System.nanoTime();
        list2.get(53404);
        endTime = System.nanoTime();
        System.out.printf("%-17s %8d ns \n", "LinkedList get 걸린 시간", (endTime - startTime));
    }

}