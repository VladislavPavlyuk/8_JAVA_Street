package com.company;

// Simple assertion utility for tests without JUnit

public class Assert {
    public static void assertTrue(String message, boolean condition) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void assertTrue(boolean condition) {
        assertTrue("Condition was false", condition);
    }

    public static void assertFalse(String message, boolean condition) {
        assertTrue(message, !condition);
    }

    public static void assertFalse(boolean condition) {
        assertFalse("Condition was true", condition);
    }

    public static void assertEquals(String message, Object expected, Object actual) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " - Expected: " + expected + ", Actual: " + actual);
        }
    }

    public static void assertEquals(Object expected, Object actual) {
        assertEquals("Values not equal", expected, actual);
    }

    public static void assertEquals(String message, int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError(message + " - Expected: " + expected + ", Actual: " + actual);
        }
    }

    public static void assertEquals(int expected, int actual) {
        assertEquals("Values not equal", expected, actual);
    }

    public static void assertNotNull(String message, Object object) {
        if (object == null) {
            throw new AssertionError(message);
        }
    }

    public static void assertNotNull(Object object) {
        assertNotNull("Object was null", object);
    }

    public static void assertNull(String message, Object object) {
        if (object != null) {
            throw new AssertionError(message + " - Expected null, but got: " + object);
        }
    }

    public static void assertNull(Object object) {
        assertNull("Object was not null", object);
    }
}


