package com.gl.education.collections;

import java.util.LinkedList;
import java.util.List;

/**
 * Custom implementation of {@link java.util.HashMap}.
 *
 * @param <K> the type of keys of map.
 * @param <V> the type of values of map.
 * @author oleksii.slavik
 */
public class HashMap<K, V> {

    /**
     * load factor value.
     */
    private static final float LOAD_FACTOR = 0.75f;

    /**
     * initial count of buckets.
     */
    private static final int BUCKET_SIZE = 16;

    /**
     *
     */
    private Entry<K, V>[] hashTable;

    /**
     * count of elements in map.
     */
    private int size = 0;

    /**
     * threshold value, in case if size bigger than it, the count of buckets increased in twice.
     */
    private float threshold;

    /**
     * Default initialization. Create one bucket and update threshold value.
     */
    public HashMap() {
        hashTable = new Entry[BUCKET_SIZE];
        threshold = hashTable.length * LOAD_FACTOR;
    }

    /**
     * Returns the number of elements in this map.
     *
     * @return the number of elements in this map.
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if this map doesn't contains any elements.
     *
     * @return true if this map doesn't contains any elements.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns number of buckets.
     *
     * @return number of buckets.
     */
    public int capacity() {
        return hashTable.length;
    }

    /**
     * Add new element to map.
     *
     * @param key   key of new element
     * @param value value of new element
     * @return value associated with key
     */
    public V put(final K key, final V value) {
        // check capacity of current buckets and if need increase count of buckets
        if (size + 1 >= threshold) {
            threshold *= 2;
            resize();
        }

        Entry<K, V> newEntry = new Entry<K, V>(key, value);
        int index = hashCode(key);

        // add new entry to empty index
        if (hashTable[index] == null) {
            return addEntryToEmptyIndex(index, newEntry);
        }

        // mechanism of add new entry to existed indexes
        List<Entry<K, V>> entries = hashTable[index].getEntries();

        for (Entry<K, V> entry : entries) {
            if (entry.isValueUpdated(newEntry) || entry.isCollisionResolved(newEntry)) {
                return value;
            }
        }

        return null;
    }

    /**
     * Remove element from map using given key.
     *
     * @param key given key value.
     * @return value of removed element, or null in case if element was not found by using given key.
     */
    public V remove(K key) {
        int index = hashCode(key);

        if (index < hashTable.length && hashTable[index] != null) {
            List<Entry<K, V>> entries = hashTable[index].getEntries();

            for (Entry<K, V> entry : entries) {
                if (key.equals(entry.getKey())) {
                    entries.remove(entry);
                    size--;
                    return entry.getValue();
                }
            }
        }

        return null;
    }

    /**
     * Find element in map using given key.
     *
     * @param key given key value.
     * @return value of element, or null in case if element was not found by using given key.
     */
    public V get(K key) {
        int index = hashCode(key);

        if (index < capacity() && hashTable[index] != null) {
            List<Entry<K, V>> entries = hashTable[index].getEntries();

            for (Entry<K, V> entry : entries) {
                if (key.equals(entry.getKey())) {
                    return entry.getValue();
                }
            }
        }

        return null;
    }

    /**
     * Create bucket with index and add new entry into it.
     *
     * @param index value of new bucket
     * @param entry {@link Entry} object with some data.
     * @return value of added entry.
     */
    private V addEntryToEmptyIndex(int index, Entry<K, V> entry) {
        hashTable[index] = new Entry<K, V>();
        hashTable[index].getEntries().add(entry);
        size++;
        return entry.getValue();
    }

    /**
     * Method for increasing in twice size of hashTable.
     */
    private void resize() {
        Entry<K, V>[] oldHashTable = hashTable;
        hashTable = new Entry[oldHashTable.length * 2];
        size = 0;

        for (Entry<K, V> bucket : oldHashTable) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket.getEntries()) {
                    put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    /**
     * Calculate hash value of given key.
     * Calculated value less than length of size of hashTable.
     *
     * @param key given key value.
     * @return index of array in hashTable.
     */
    private int hashCode(K key) {
        int hash = 31;
        hash = hash * 17 + key.hashCode();
        return hash % capacity();
    }

    /**
     * Pair of key and associated with them value.
     *
     * @param <K> the type of keys of map.
     * @param <V> the type of values of map.
     */
    private class Entry<K, V> {

        /**
         * key.
         */
        private K key;

        /**
         * value.
         */
        private V value;

        /**
         * list of entries in one bucket.
         */
        private List<Entry<K, V>> entries;

        private Entry() {
            this(null, null);
        }

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
            entries = new LinkedList<Entry<K, V>>();
        }

        private K getKey() {
            return key;
        }

        private V getValue() {
            return value;
        }

        private List<Entry<K, V>> getEntries() {
            return entries;
        }

        /**
         * Check if value need to be updated with value from another entry.
         *
         * @param other {@link Entry} object with potentially actual data.
         * @return true in case if value updated, false in otherwise.
         */
        private boolean isValueUpdated(Entry<K, V> other) {
            if (key.equals(other.getKey()) || !value.equals(other.getValue())) {
                value = other.getValue();
                return true;
            }

            return false;
        }

        /**
         * Check collisions with another entry.
         *
         * @param other {@link Entry} object with some data.
         * @return true in case if collision resolved, false in otherwise.
         */
        private boolean isCollisionResolved(Entry<K, V> other) {
            if (hashCode() == other.hashCode() && !key.equals(other.getKey()) && !value.equals(other.getValue())) {
                entries.add(other);
                size++;
                return true;
            }

            return false;
        }
    }
}
