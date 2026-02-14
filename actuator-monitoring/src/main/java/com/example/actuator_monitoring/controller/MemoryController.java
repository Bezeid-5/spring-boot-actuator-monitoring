package com.example.actuator_monitoring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/memory")
public class MemoryController {

    private static final List<byte[]> memoryHog = new ArrayList<>();

    @GetMapping("/stress")
    public String stressMemory() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        long targetMemory = (long) (maxMemory * 0.90); // Use 90% of memory
        int blockSize = 1024 * 1024 * 1024; // 10 MB

        try {
            while ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) < targetMemory) {
                memoryHog.add(new byte[blockSize]);
            }
        } catch (OutOfMemoryError e) {
            return "Out of memory error triggered! Current usage: " +
                    (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024) + " MB";
        }

        return "Allocated approximately 90% of memory. Current usage: " +
                (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024) + " MB";
    }

    @GetMapping("/clear")
    public String clearMemory() {
        memoryHog.clear();
        System.gc();
        return "Memory cleared. Current usage: " +
                (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024) + " MB";
    }
}
