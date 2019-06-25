package com.gl.education;

import com.gl.education.collections.HashMap;

import java.util.Random;

public class Demo {

    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        System.out.println("Empty map:");
        System.out.println(map);

        for (int i = 0; i < 10; i++) {
            map.put(i, generateValue());
        }

        System.out.println("Generated map:");
        System.out.println(map);
        System.out.println("key = 1: " + map.get(1));
        System.out.println("key = 10: " + map.get(10));

        System.out.println("Update element with key = 1:");
        map.put(1, "test");
        System.out.println("key = 1: " + map.get(1));
        System.out.println(map);

        System.out.println("Add new elements to map:");
        map.put(10, generateValue());
        System.out.println(map);
        map.put(11, generateValue());
        System.out.println(map);

        System.out.println("Remove element with key = 3:");
        map.remove(3);
        System.out.println("key = 3: " + map.get(3));
        System.out.println(map);

        System.out.println("Remove element with key = 12:");
        map.remove(12);
        System.out.println("key = 12: " + map.get(12));
        System.out.println(map);
    }

    private static String generateValue() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
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
