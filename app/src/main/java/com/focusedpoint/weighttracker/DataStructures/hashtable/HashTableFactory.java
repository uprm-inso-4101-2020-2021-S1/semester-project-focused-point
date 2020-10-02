package com.focusedpoint.weighttracker.DataStructures.hashtable;

import com.focusedpoint.weighttracker.DataStructures.map.Map;

public interface HashTableFactory<K, V> {

	Map<K, V> getInstance(int initialCapacity);

}