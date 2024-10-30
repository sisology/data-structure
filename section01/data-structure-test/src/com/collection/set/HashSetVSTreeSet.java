package com.collection.set;

import java.util.*;

public class HashSetVSTreeSet {

    private static final int NUM_ELEMENTS = 1000000;
    private static final int NUM_OPERATIONS = 10000;

    public static void main(String[] args) {
        // 테스트할 데이터 준비
        Random random = new Random();
        List<Integer> testData = new ArrayList<>();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            testData.add(random.nextInt(NUM_ELEMENTS));
        }

        // HashSet 성능 테스트
        Set<Integer> hashSet = new HashSet<>();
        long[] hashSetTimes = testSetPerformance(hashSet, testData);

        // TreeSet 성능 테스트
        Set<Integer> treeSet = new TreeSet<>();
        long[] treeSetTimes = testSetPerformance(treeSet, testData);

        // 결과 출력
        System.out.println("=== HashSet vs TreeSet 성능 비교 ===");
        printResults("HashSet", hashSetTimes);
        printResults("TreeSet", treeSetTimes);
    }

    private static long[] testSetPerformance(Set<Integer> set, List<Integer> testData) {
        long[] times = new long[3]; // 삽입, 검색, 삭제 시간을 저장

        // 삽입 시간 측정
        long startTime = System.nanoTime();
        for (Integer num : testData) {
            set.add(num);
        }
        times[0] = System.nanoTime() - startTime;

        // 검색 시간 측정
        startTime = System.nanoTime();
        for (int i = 0; i < NUM_OPERATIONS; i++) {
            set.contains(testData.get(i));
        }
        times[1] = System.nanoTime() - startTime;

        // 삭제 시간 측정
        startTime = System.nanoTime();
        for (int i = 0; i < NUM_OPERATIONS; i++) {
            set.remove(testData.get(i));
        }
        times[2] = System.nanoTime() - startTime;

        return times;
    }

    private static void printResults(String setType, long[] times) {
        System.out.printf("%s 성능:%n", setType);
        System.out.printf("삽입 시간: %.3f ms%n", times[0] / 1_000_000.0);
        System.out.printf("검색 시간: %.3f ms%n", times[1] / 1_000_000.0);
        System.out.printf("삭제 시간: %.3f ms%n", times[2] / 1_000_000.0);
        System.out.println();
    }

}
