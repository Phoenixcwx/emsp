package com.volvo.emspdemo;

import com.volvo.emspdemo.util.ContractIdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContractIdGeneratorTest {

    @Autowired
    ContractIdGenerator contractIdGenerator;

    @Test
    public void testConcurrentIdGeneration() throws InterruptedException {
        int threadCount = 100;
        Set<String> ids = new HashSet<>();

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                String id = contractIdGenerator.generateContractIdWithoutPlaceHolder();
                synchronized (ids) {
                    ids.add(id);
                }
            });
        }

        ids.forEach(id -> System.out.println(id));

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        assertEquals(threadCount, ids.size()); // Ensure all IDs are unique
    }
}
