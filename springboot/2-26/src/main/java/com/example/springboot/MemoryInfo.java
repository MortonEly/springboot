package com.example.springboot;

/**
 * @Auther: 吕宏博
 * @Date: 2024--03--06--14:58
 * @Description:
 */
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class MemoryInfo {
    public static void main(String[] args) {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        System.out.println("Heap Memory Usage:");
        System.out.println("Initial: " + heapMemoryUsage.getInit() + " bytes");
        System.out.println("Used: " + heapMemoryUsage.getUsed() + " bytes");
        System.out.println("Committed: " + heapMemoryUsage.getCommitted() + " bytes");
        System.out.println("Max: " + heapMemoryUsage.getMax() + " bytes");

        System.out.println("\nNon-Heap Memory Usage:");
        System.out.println("Initial: " + nonHeapMemoryUsage.getInit() + " bytes");
        System.out.println("Used: " + nonHeapMemoryUsage.getUsed() + " bytes");
        System.out.println("Committed: " + nonHeapMemoryUsage.getCommitted() + " bytes");
        System.out.println("Max: " + nonHeapMemoryUsage.getMax() + " bytes");
    }
}
