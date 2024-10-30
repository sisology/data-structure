package com.concurrent_collection.Queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class A_ConcurrentLinkedQueue {

    /**
     * 스레드 개수는 4개,
     * 그리고 각 스레드는 1,000,000번씩의
     * offer()와 poll() 작업을 수행하도록 설정되어 있습니다.

     * 총 작업 수 = 스레드 수 X 각 스레드의 작업 수
     * 총 작업 수 = 4,000,000

     * 결과적으로 동시에 추가된 값이 모두 제거되어 큐에는 값이 남아있지 않아야 합니다.
     * */

    private static final int NUM_THREADS = 4;
    private static final int NUM_OPERATIONS = 1_000_000;

    public static void main(String[] args) throws InterruptedException {
        // LinkedList 초기화 (스레드 안전하지 않음)
        Queue<Integer> unsafeQueue = new LinkedList<>();

        // 스레드 풀 생성
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        // 여러 스레드가 동시에 큐에 데이터를 추가하고 제거하는 작업을 수행
        for (int i = 0; i < NUM_THREADS; i++) {
            executor.execute(() -> {
                for (int j = 0; j < NUM_OPERATIONS; j++) {
                    unsafeQueue.offer(j); // 큐에 데이터 추가
                    unsafeQueue.poll();   // 큐에서 데이터 제거
                }
            });
        }

        // 스레드 풀 종료
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // 작업 완료 후 큐의 크기 출력 (스레드 안전하지 않으므로 비정상적인 동작이 발생)
        System.out.println("Final queue size: " + unsafeQueue.size());
    }
}
