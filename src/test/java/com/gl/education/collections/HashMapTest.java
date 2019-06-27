package com.gl.education.collections;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class HashMapTest {

    private static final int INITIAL_CAPACITY = 16;

    private HashMap<Integer, String> hashMap;

    @Before
    public void beforeTest(){
        hashMap = new HashMap<Integer, String>();
    }

    @Test
    public void putValue() {
        String value = generateValue();
        assertEquals(value, hashMap.put(1, value));
    }

    @Test
    public void overrideValue() {
        String value1 = generateValue();
        String value2 = generateValue();
        assertEquals(value1, hashMap.put(1, value1));
        assertEquals(value2, hashMap.put(1, value2));
        assertEquals(value2, hashMap.get(1));
    }

    @Test
    public void putValues() {
        String value1 = generateValue();
        String value2 = generateValue();
        String value3 = generateValue();
        String value4 = generateValue();
        assertEquals(value1, hashMap.put(1, value1));
        assertEquals(value2, hashMap.put(2, value2));
        assertEquals(value3, hashMap.put(3, value3));
        assertEquals(value4, hashMap.put(4, value4));
    }

    @Test
    public void removeValue() {
        String value = generateValue();
        assertEquals(value, hashMap.put(1, value));
        assertEquals(value, hashMap.get(1));
        assertEquals(value, hashMap.remove(1));
        assertNull(hashMap.get(1));
    }

    @Test
    public void removeNonexistentValue() {
        assertNull(hashMap.get(1));
        assertNull(hashMap.remove(1));
        assertNull(hashMap.get(1));
    }

    @Test
    public void getValue() {
        assertNull(hashMap.get(1));
        String value = generateValue();
        assertEquals(value, hashMap.put(1, value));
        assertEquals(value, hashMap.get(1));
    }

    @Test
    public void size() {
        assertEquals(0, hashMap.size());
        int count = generateInt(20);

        for (int i = 0; i < count; i++) {
            hashMap.put(i, generateValue());
        }

        assertEquals(count, hashMap.size());

        for (int i = 0; i < count; i++) {
            hashMap.remove(i);
        }

        assertEquals(0, hashMap.size());
    }

    @Test
    public void isEmpty() {
        assertTrue(hashMap.isEmpty());
        int count = generateInt(20);

        for (int i = 0; i < count; i++) {
            hashMap.put(i, generateValue());
        }

        assertFalse(hashMap.isEmpty());

        for (int i = 0; i < count; i++) {
            hashMap.remove(i);
        }

        assertTrue(hashMap.isEmpty());
    }

    @Test
    public void checkCapacity() {
        assertEquals(INITIAL_CAPACITY, hashMap.capacity());

        for (int i = 0; i < 11; i++) {
            hashMap.put(i, generateValue());
        }

        assertEquals(INITIAL_CAPACITY, hashMap.capacity());
        hashMap.put(11, generateValue());
        assertEquals(INITIAL_CAPACITY * 2, hashMap.capacity());
    }

    private int generateInt(int bound) {
        Random random = new Random();
        return random.nextInt(bound) + 1;
    }

    private String generateValue() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = generateInt(10);
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);

        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }
}
