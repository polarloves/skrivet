package com.skrivet.core.common.starter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.skrivet", "${skrivet.project.package}"})
public class Starter implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Starter.class);
        System.out.println("System start successful!");
    }

    @Override
    public void run(String... args) throws Exception {
        Thread.sleep(1000*60);
    }
}
