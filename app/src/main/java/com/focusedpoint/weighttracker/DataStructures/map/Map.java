package com.focusedpoint.weighttracker.DataStructures.map;

import java.io.PrintStream;

import com.focusedpoint.weighttracker.DataStructures.list.List;

public interface Map<K, V> {

	V get(K key);
	void put(K key, V value);
	V remove(K key);
	boolean containsKey(K key);
	List<K> getKeys();
	List<V> getValues();
	int size();
	boolean isEmpty();
	void clear();
	void print(PrintStream out); /* For debugging purposes */
}