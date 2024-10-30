package com.concurrent_collection.Queue;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ConcurrentLinkedQueue; // 표준 Java 클래스

public class B_ConcurrentLinkedQueue {

    /**
     * 테스트 요약 및 의도

     * ConcurrentLinkedQueue의 장점: 동시성을 보장하면서 Lock-Free 방식으로 작동하여 여러 스레드가 동시에 데이터를 추가하고 제거하는 상황에서도 안전하게 동작합니다.
     * ConcurrentLinkedQueue 사용 시 결과: 여러 스레드가 큐에 데이터를 삽입하고 곧바로 제거해도 최종적으로 큐는 비어 있게 됩니다 (Final queue size: 0). 동시성 문제가 없으며 데이터가 안전하게 처리됨을 의미합니다.
     * ConcurrentLinkedQueue 미사용 시 결과: 스레드 안전하지 않은 큐(예: LinkedList)를 사용하면 데이터가 손실되거나 충돌이 발생할 수 있습니다. 멀티스레드 환경에서 동시성 문제로 인해 비정상적인 큐 상태가 발생할 수 있습니다.
     * 따라서 이 테스트의 의도는 멀티스레드 환경에서 동시성 문제 없이 안전하게 큐를 사용하는 방법을 보여주기 위한 것이며, ConcurrentLinkedQueue는 이러한 문제를 해결하는 적합한 데이터 구조임을 입증합니다.*/

    private static final int NUM_THREADS = 4;
    private static final int NUM_OPERATIONS = 1_000_000;

    public static void main(String[] args) throws InterruptedException {
        // ConcurrentLinkedQueue 초기화 (표준 라이브러리 클래스)
        Queue<Integer> concurrentQueue = new ConcurrentLinkedQueue<>();

        // 스레드 풀 생성
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        // 여러 스레드가 동시에 큐에 데이터를 추가하고 제거하는 작업을 수행
        for (int i = 0; i < NUM_THREADS; i++) {
            executor.execute(() -> {
                for (int j = 0; j < NUM_OPERATIONS; j++) {
                    concurrentQueue.offer(j); // 큐에 데이터 추가
                    concurrentQueue.poll();   // 큐에서 데이터 제거
                }
            });
        }

        // 스레드 풀 종료
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // 작업 완료 후 큐의 크기 출력 (ConcurrentLinkedQueue의 동시성 처리로 인해 안전하게 처리)
        System.out.println("Final queue size: " + concurrentQueue.size());
    }
}
