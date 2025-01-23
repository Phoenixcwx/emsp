package com.volvo.emspdemo;

import com.volvo.emspdemo.util.ContractIdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ContractIdGeneratorTest {

    private final ContractIdGenerator contractIdGenerator;

    @Autowired
    public ContractIdGeneratorTest(ContractIdGenerator contractIdGenerator) {
        this.contractIdGenerator = contractIdGenerator;
    }

    @Test
    public void testConcurrentIdGeneration() throws InterruptedException {
        int threadCount = 100;
        Set<String> ids = new ConcurrentSkipListSet<>();


        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                String id = contractIdGenerator.generateContractIdWithoutPlaceHolder();
                synchronized (ids) {
                    ids.add(id);
                }
            });
        }

        ids.forEach(System.out::println);

        executorService.shutdown();
        boolean executeResult = executorService.awaitTermination(1, TimeUnit.MINUTES);
        assertTrue(executeResult);
        assertEquals(threadCount, ids.size()); // Ensure all IDs are unique
    }
}
