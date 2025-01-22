package com.volvo.emspdemo.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SequenceInitializer implements CommandLineRunner {

    private final SequenceManager sequenceManager;

    public SequenceInitializer(SequenceManager sequenceManager) {
        this.sequenceManager = sequenceManager;
    }

    @Override
    public void run(String... args) throws Exception {
        sequenceManager.initializeSequence();
    }
}
