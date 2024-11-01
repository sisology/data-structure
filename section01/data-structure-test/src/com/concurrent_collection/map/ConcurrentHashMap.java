package com.concurrent_collection.map;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentHashMap {

    /**
     * 스레드 개수는 4개,
     * 그리고 각 스레드는 1,000,000번씩의
     * put(), computeIfPresent(), get() 작업을 수행하도록 설정

     * 총 작업 수 = 스레드 수 X 각 스레드의 작업 수
     * 총 작업 수 = 4,000,000

     * ConcurrentHashMap과 HashMap을 비교하여,
     * 동시성 문제를 방지하는 ConcurrentHashMap의 장점을 테스트
     */

    private static final int NUM_THREADS = 4;
    private static final int NUM_OPERATIONS = 1_000_000;

    public static void main(String[] args) throws InterruptedException {
        // HashMap 테스트
        System.out.println("HashMap Test:");
        Map<Integer, Integer> unsafeMap = new HashMap<>();
        runTest(unsafeMap);

        // ConcurrentHashMap 테스트
        System.out.println("ConcurrentHashMap Test:");
        Map<Integer, Integer> concurrentMap = new java.util.concurrent.ConcurrentHashMap<>();
        runTest(concurrentMap);
    }

    private static void runTest(Map<Integer, Integer> map) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        // 1. 삽입 작업
        executeOperation(executor, () -> {
            for (int j = 0; j < NUM_OPERATIONS; j++) {
                map.put(j, j); // 맵에 데이터 삽입
            }
        });

        // 2. 수정 작업 (특정 키에 1씩 더함)
        executeOperation(executor, () -> {
            for (int j = 0; j < NUM_OPERATIONS; j++) {
                map.computeIfPresent(j, (key, value) -> value + 1); // 값이 있으면 1 더함
            }
        });

        // 3. 검색 작업 (맵에서 값을 읽어옴)
        executeOperation(executor, () -> {
            for (int j = 0; j < NUM_OPERATIONS; j++) {
                map.get(j); // 맵에서 데이터 검색
            }
        });

        // 스레드 풀 종료
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // 작업 완료 후 맵의 크기 및 값 출력
        System.out.println("Final map size: " + map.size());
        System.out.println("Sample value for key 0: " + map.get(0)); // 예시로 key 0의 값 출력
    }

    private static void executeOperation(ExecutorService executor, Runnable task) {
        for (int i = 0; i < NUM_THREADS; i++) {
            executor.execute(task);
        }
    }

    /**
     * 테스트 요약 및 의도
     * ConcurrentHashMap의 장점
     * 동시성을 보장하면서 여러 스레드가 동시에 데이터를 삽입, 수정, 검색하는 상황에서도 충돌 없이 안전하게 작동

     * ConcurrentHashMap 사용 시 결과
     * 여러 스레드가 동시에 데이터를 삽입하거나 수정해도 데이터가 안전하게 처리되며, 최종적으로 일관된 상태를 유지

     * HashMap 사용 시 결과
     * 멀티스레드 환경에서 데이터 경합으로 인해 데이터 손실, 충돌, 비일관적인 상태가 발생할 수 있음

     * 따라서 이 테스트의 의도는 멀티스레드 환경에서 안전하게 데이터를 처리할 수 있는 방법을 보여주기 위한 것이며,
     * ConcurrentHashMap은 이러한 문제를 해결하는 적합한 데이터 구조임을 입증
     */

}
