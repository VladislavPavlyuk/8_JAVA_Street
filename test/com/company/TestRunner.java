package com.company;

import com.company.model.*;
import com.company.utils.StreetFactory;
import com.company.enums.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

// Simple test runner for running tests without JUnit dependency

public class TestRunner {
    private static int testsRun = 0;
    private static int testsPassed = 0;
    private static int testsFailed = 0;
    private static List<String> failures = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("Running Tests for 8_JAVA_Street Project");
        System.out.println("=========================================\n");

        // Run all test classes
        runTestClass("com.company.model.ResidentialTest");
        runTestClass("com.company.model.StreetTest");
        runTestClass("com.company.model.ShopTest");
        runTestClass("com.company.model.SchoolTest");
        runTestClass("com.company.model.HospitalTest");
        runTestClass("com.company.utils.StreetFactoryTest");
        runTestClass("com.company.enums.EnumsTest");

        // Print summary
        System.out.println("\n=========================================");
        System.out.println("Test Summary");
        System.out.println("=========================================");
        System.out.println("Tests Run: " + testsRun);
        System.out.println("Tests Passed: " + testsPassed);
        System.out.println("Tests Failed: " + testsFailed);
        
        if (!failures.isEmpty()) {
            System.out.println("\nFailures:");
            for (String failure : failures) {
                System.out.println("  - " + failure);
            }
        }
        
        System.out.println("\n=========================================");
        if (testsFailed == 0) {
            System.out.println("ALL TESTS PASSED!");
        } else {
            System.out.println("SOME TESTS FAILED!");
        }
        System.out.println("=========================================");
    }

    private static void runTestClass(String className) {
        try {
            Class<?> testClass = Class.forName(className);
            System.out.println("Running tests in: " + className);
            
            Method[] methods = testClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("test") && 
                    method.getParameterCount() == 0) {
                    runTest(testClass, method);
                }
            }
            System.out.println();
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: Test class not found: " + className);
            System.out.println();
        } catch (Exception e) {
            System.out.println("ERROR running test class " + className + ": " + e.getMessage());
            e.printStackTrace();
            System.out.println();
        }
    }

    private static void runTest(Class<?> testClass, Method testMethod) {
        try {
            Object testInstance = testClass.getDeclaredConstructor().newInstance();
            testMethod.invoke(testInstance);
            testsRun++;
            testsPassed++;
            System.out.println("  ✓ " + testMethod.getName());
        } catch (AssertionError e) {
            testsRun++;
            testsFailed++;
            String failure = testClass.getSimpleName() + "." + testMethod.getName() + 
                           ": " + e.getMessage();
            failures.add(failure);
            System.out.println("  ✗ " + testMethod.getName() + " - " + e.getMessage());
        } catch (Exception e) {
            testsRun++;
            testsFailed++;
            String failure = testClass.getSimpleName() + "." + testMethod.getName() + 
                           ": " + e.getMessage();
            failures.add(failure);
            System.out.println("  ✗ " + testMethod.getName() + " - Exception: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }
    }
}

