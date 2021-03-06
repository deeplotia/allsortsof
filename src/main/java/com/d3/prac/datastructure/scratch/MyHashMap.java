package com.d3.prac.datastructure.scratch;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * Some Useful Resources:
 * https://stackoverflow.com/questions/2334218/why-are-xor-often-used-in-java-hashcode-but-another-bitwise-operators-are-used
 * https://www.nagarro.com/en/blog/post/24/performance-improvement-for-hashmap-in-java-8
 * https://stackoverflow.com/questions/9335169/understanding-strange-java-hash-function
 */
public class MyHashMap<K, V> {

    private static int size;
    private static int DEFAULT_CAPACITY = 16;
    private static int CURRENT_CAPACITY = DEFAULT_CAPACITY;
    private Node<K,V>[] values = new Node[DEFAULT_CAPACITY];

    // TODO : put, get, remove, values, keySet, entrySet

    public void put(K key, V value) {
        ensureCapacity();
        int index = hash(key) & (values.length - 1);
        if(values[index] == null) {
            values[index] = new Node<>(key, value, null);
            size++;
        } else if(values[index].getKey() == key) {
            values[index].setValue(value);
        } else {
            Node<K,V> node = values[index].next;
            if(node != null) {
                while(node.next != null) {
                    node = node.next;
                }
                node.next = new Node<>(key, value, null);
            } else {
                values[index].next = new Node<>(key, value, null);
            }
            size++;
        }
    }

    public V get(K key) {
        int index = hash(key) & (values.length - 1);

        if(values[index].getValue() != null) {
            Node<K, V> node = values[index];
            if(node.getKey().equals(key)) {
                return node.getValue();
            } else if(node.next != null) {
                while(node.next !=null ) {
                    node = node.next;
                    if(node.getKey() == key) {
                        return node.getValue();
                    }
                }
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if(size == values.length) {
            int newSize = size * 2;
            CURRENT_CAPACITY = newSize;
            values = Arrays.copyOf(values, newSize);
        }
    }

    public void remove(K key) {
        int index = hash(key) & (values.length - 1);
        values[index] = null;
    }

    static final int hash(Object key) {
        int hash = 0;
        hash = key.hashCode() ^ key.hashCode() >>> 16;
        //hash = (int) Math.floor(Math.random() * CURRENT_CAPACITY);
        return hash;
    }
}

class Node<K, V> {
    private final K key;
    private V value;
    Node<K,V> next;

    public Node (K key, V value, Node<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Node<K, V> getNext() {
        return next;
    }

    public void setNext(Node<K, V> next) {
        this.next = next;
    }

}
