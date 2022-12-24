package com.firebasepoc.firebasepoc.controller;


import com.firebasepoc.firebasepoc.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Controller {

    @Autowired
    private Service service;

    @PostMapping("/stress")
    public void stressTest(int iterations, Boolean isFixedTime) {
        service.stressedTest(iterations, isFixedTime);
    }


    @PostMapping("/export")
    public void export(String fileName) {
        service.exportResult(fileName);
    }

    @PostMapping("/clean")
    public void clean() {
        service.clean();
    }
}
