package com.focusedpoint.weighttracker.DataStructures.hashtable;

import java.io.PrintStream;
import java.util.Arrays;

import com.focusedpoint.weighttracker.DataStructures.list.ArrayList;
import com.focusedpoint.weighttracker.DataStructures.list.ConvertableToString;
import com.focusedpoint.weighttracker.DataStructures.list.LinkedList;
import com.focusedpoint.weighttracker.DataStructures.list.List;
import com.focusedpoint.weighttracker.DataStructures.map.Map;

public class HashTableSC<K, V> implements Map<K, V> {

	/**
	 * The values in the linked lists within our buckets will be of this type.
	 * @author Juan O. Lopez
	 * edited by Christopher Lagos
	 */
	
	private static class BucketNode<K, V> implements ConvertableToString {
		private K key;
		private V value;

		public BucketNode(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		@Override
		public String toString() {
			String result ="";
			if(value == null||key==null)
				return result;
			if(value instanceof ConvertableToString){
				result = result+key+":"+((ConvertableToString)value).toString();
			}else{
				result = result+key+":"+value;
			}
			return result;
		}
	}


	// private fields
	private int currentSize;
	private List<BucketNode<K, V>>[] buckets;
	private HashFunction<K> hashFunction;

	@SuppressWarnings("unchecked")
	public HashTableSC(int initialCapacity, HashFunction<K> hashFunction) {
		if (initialCapacity < 1)
			throw new IllegalArgumentException("Capacity must be at least 1");
		if (hashFunction == null)
			throw new IllegalArgumentException("Hash function cannot be null");

		currentSize = 0;
		this.hashFunction = hashFunction;
		buckets = new LinkedList[initialCapacity];
		for (int i = 0; i < initialCapacity; i++)
			buckets[i] = new LinkedList<BucketNode<K, V>>();
	}

	private void rehash() {
        @SuppressWarnings("unchecked")
        List<BucketNode<K, V>>[] newBuckets= new LinkedList[buckets.length*2];
        int m = newBuckets.length; //number of linked lists(buckets). 
        //This for loop initializes empty linked lists for every bucket.
        for (int i = 0; i < m; i++) {
            newBuckets[i] = new LinkedList<BucketNode<K, V>>();
            }
        //We will now hash all the elements in the original bucket to the new buckets.
        for (int i = 0; i < buckets.length; i++) { 
        	List<BucketNode<K, V>> list = this.buckets[i];
            for (BucketNode<K, V> node : list) {
                int targetBucket = hashFunction.hashCode(node.getKey()) % m;
                // Since we are using separate chaining, this means that the buckets contain linked lists.
                List<BucketNode<K, V>> L = newBuckets[targetBucket];
                //Add the Key & its Value to its corresponding linked list.
                L.add(0, node);
            }
        }
        this.buckets= newBuckets;
        System.out.println(currentSize);
    }

	public V get(K key) {
		if (key == null)
			throw new IllegalArgumentException("Parameter cannot be null.");

		/* First we determine the bucket corresponding to this key */
		int targetBucket = hashFunction.hashCode(key) % buckets.length;
		/* Within that bucket there is a linked list, since we're using Separate Chaining */
		List<BucketNode<K, V>> L = buckets[targetBucket];
		/* Look for the key within the nodes of that linked list */
		for (BucketNode<K, V> BN : L) {
			if (BN.getKey().equals(key)) // Found it!
				return BN.getValue();
		}

		return null; // Did not find it
	}

	@Override
	public void put(K key, V value) {
		if (key == null || value == null)
			throw new IllegalArgumentException("Parameter cannot be null.");
		// this double indicates us at what size the Load Factor of 0.75 will be reached according to its currentSize.
		double lambda = buckets.length*0.75; 
		if (currentSize >= lambda) {
			rehash();
		}
		/* Can't have two elements with same key,
		 * so remove existing element with the given key (if any) */
		remove(key);
		/* Determine the bucket corresponding to this key */
		int targetBucket = hashFunction.hashCode(key) % buckets.length;
		/* Within that bucket there is a linked list, since we're using Separate Chaining */
		List<BucketNode<K, V>> L = buckets[targetBucket];
		/* Finally, add the key/value to the linked list */
		L.add(0, new BucketNode<K, V>(key, value));
		currentSize++;
	}

	@Override
	public V remove(K key) {
		if (key == null)
			throw new IllegalArgumentException("Parameter cannot be null.");

		/* First we determine the bucket corresponding to this key */
		int targetBucket = hashFunction.hashCode(key) % buckets.length;
		/* Within that bucket there is a linked list, since we're using Separate Chaining */
		List<BucketNode<K, V>> L = buckets[targetBucket];
		/* Iterate over linked list trying to find this the key */
		int pos = 0;
		for (BucketNode<K, V> BN : L) {
			if (BN.getKey().equals(key)) { // Found it!
				L.remove(pos);
				currentSize--;
				return BN.getValue();
			}
			else
				pos++;
		}
		return null;
	}

	@Override
	public boolean containsKey(K key) {
		return get(key) != null;
	}

	@Override
	public List<K> getKeys() {
		List<K> result = new ArrayList<K>(size());
		/* For each bucket in the hash table, get the keys in that linked list */
		for (int i = 0; i < buckets.length; i++)
			for (BucketNode<K, V> BN : buckets[i])
				result.add(0, BN.getKey());
		return result;
	}

	@Override
	public List<V> getValues() {
		List<V> result = new ArrayList<V>(size());
		/* For each bucket in the hash table, get the values in that linked list */
		for (int i = 0; i < buckets.length; i++)
			for (BucketNode<K, V> BN : buckets[i])
				result.add(0, BN.getValue());
		return result;
	}

	@Override
	public int size() {
		return currentSize;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public void clear() {
		currentSize = 0;
		for (int i = 0; i < buckets.length; i++)
			buckets[i].clear();
	}

	@Override
	public void print(PrintStream out) {
		/* For each bucket in the hash table, print the elements in that linked list */
		for (int i = 0; i < buckets.length; i++)
			for (BucketNode<K, V> BN : buckets[i])
				out.printf("(%s, %s)\n", BN.getKey(), BN.getValue());
	}

	@Override
	public String toString() {
		String result="";
		for (int i = 0; i < buckets.length; i++)
			for (BucketNode<K, V> BN : buckets[i])
				result=result+BN.toString()+"\n";
	return result;
	}
}