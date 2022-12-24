package com.example.poc.mongodb.controller;

import com.example.poc.mongodb.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private Service service;

    @PostMapping("/test")
    public void test() {
        service.test();
    }

    @PostMapping("/stress")
    public void demoStress(int iterations, boolean isFixedTime) {
        service.stress(iterations, isFixedTime);
    }

    @PostMapping("/export")
    public void export(String fileName) {
        service.export(fileName);
    }

    @PostMapping("/clean")
    public void clean() {
        service.clean();
    }

}
