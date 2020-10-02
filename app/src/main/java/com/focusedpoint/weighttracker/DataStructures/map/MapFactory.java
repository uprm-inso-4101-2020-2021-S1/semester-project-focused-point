package com.focusedpoint.weighttracker.DataStructures.map;

public interface MapFactory<K, V> {

	Map<K, V> getInstance(KeyExtractor<K, V> extractor);
}