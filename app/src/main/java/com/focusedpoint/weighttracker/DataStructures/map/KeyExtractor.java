package com.focusedpoint.weighttracker.DataStructures.map;

public interface KeyExtractor<K, V> {

	K getKey(V value);
}