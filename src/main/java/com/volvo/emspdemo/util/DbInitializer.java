package com.volvo.emspdemo.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbInitializer implements CommandLineRunner {

    private final DbManager dbManager;

    public DbInitializer(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void run(String... args) throws Exception {
        dbManager.init();
    }
}
