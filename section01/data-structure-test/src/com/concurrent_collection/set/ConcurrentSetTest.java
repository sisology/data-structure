package com.concurrent_collection.set;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

public class ConcurrentSetTest {

    /**
     * 1. **ConcurrentSkipListSet**, **CopyOnWriteArraySet**, **synchronizedSet**, **HashSet**에 대해 각각 테스트
     * 2. 각 `Set`에 대해 데이터를 동시에 삽입하고 읽는 작업을 여러 스레드에서 수행
     * 3. 각 `Set`의 최종 크기를 출력하여 멀티스레드 환경에서 안전하게 작동했는지 확인

     * 예상 결과
     * - ConcurrentSkipListSet: 동시성 보장과 정렬을 유지하며, 최종 크기가 일관되게 유지
     * - CopyOnWriteArraySet: 동시성 보장은 되지만, 쓰기 작업이 많은 경우 성능 저하가 있을 수 있음
     * - Collections.synchronizedSet: 모든 작업이 동기화 블록 내에서 처리되므로 성능은 다소 느리지만, 동시성은 보장
     * - HashSet: 스레드 안전하지 않으므로, 멀티스레드 환경에서는 최종 크기가 비정상적이거나 데이터 손실이 발생할 수 있음

     * 실행 결과
     * Testing ConcurrentSkipListSet
     * Testing with: ConcurrentSkipListSet
     * Final set size: 400000
     * Time taken by ConcurrentSkipListSet: 99 ms
     * // 0.099초

     * Testing CopyOnWriteArraySet
     * Testing with: CopyOnWriteArraySet
     * Final set size: 389485
     * Time taken by CopyOnWriteArraySet: 60005 ms
     * // 60.005초;;

     * Testing SynchronizedSet
     * Testing with: SynchronizedSet
     * Final set size: 400000
     * Time taken by SynchronizedSet: 127 ms
     * // 0.127초

     * Testing HashSet (for comparison, not thread-safe)
     * Testing with: HashSet
     * Final set size: 187819
     * Time taken by HashSet: 23 ms
     * // 0.023초이지만? 결과 처참
     * */

    private static final int NUM_THREADS = 4;
    private static final int NUM_OPERATIONS = 100_000;

    public static void main(String[] args) throws InterruptedException {
        // 테스트할 Set 구현체
        System.out.println("Testing ConcurrentSkipListSet");
        testSetPerformance(new ConcurrentSkipListSet<>());

        System.out.println("Testing CopyOnWriteArraySet");
        testSetPerformance(new CopyOnWriteArraySet<>());

        System.out.println("Testing SynchronizedSet");
        testSetPerformance(Collections.synchronizedSet(new HashSet<>()));

        System.out.println("Testing HashSet (for comparison, not thread-safe)");
        testSetPerformance(new HashSet<>());

        // 모든 테스트 완료 후 프로그램 종료
        System.exit(0);
    }

    private static void testSetPerformance(Set<Integer> set) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        System.out.println("Testing with: " + set.getClass().getSimpleName());

        // 시작 시간 기록
        long startTime = System.currentTimeMillis();

        // 데이터 추가 작업
        for (int i = 0; i < NUM_THREADS; i++) {
            final int offset = i * NUM_OPERATIONS;
            executor.execute(() -> {
                for (int j = 0; j < NUM_OPERATIONS; j++) {
                    set.add(offset + j);  // Set에 고유한 데이터 추가
                }
            });
        }

        // 데이터 검색 작업
        for (int i = 0; i < NUM_THREADS; i++) {
            executor.execute(() -> {
                for (int j = 0; j < NUM_OPERATIONS; j++) {
                    set.contains(j);  // Set에서 데이터 검색
                }
            });
        }

        // 스레드 풀 종료
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // 종료 시간 기록
        long endTime = System.currentTimeMillis();

        // 최종 Set 크기 출력 및 실행 시간 표시
        System.out.println("Final set size: " + set.size());
        System.out.println("Time taken by " + set.getClass().getSimpleName() + ": " + (endTime - startTime) + " ms\n");
    }

}
