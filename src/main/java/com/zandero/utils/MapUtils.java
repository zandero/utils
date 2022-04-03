package com.zandero.utils;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * Map manipulation utils
 */
public final class MapUtils {

	private MapUtils() {
		// hide constructor
	}

	/**
	 * Merging single level aggregation maps
	 *
	 * @param stream        source to merge
	 * @param mergeFunction operation
	 * @param mapSupplier   new map supplier
	 * @param <K>           type of key
	 * @param <V>           type of value
	 * @param <M>           type of map supplier
	 * @return new merged map
	 */
	public static <K, V, M extends Map<K, V>> M merge(Stream<? extends Map<K, V>> stream,
	                                                  BinaryOperator<V> mergeFunction,
	                                                  Supplier<M> mapSupplier) {

		Assert.notNull(stream, "Missing map merge function!");
		Assert.notNull(mergeFunction, "Missing map merge function!");
		Assert.notNull(mapSupplier, "Missing map supplier!");

		return stream.collect(mapSupplier,
		                      (a, b) -> b.forEach((k, v) -> a.merge(k, v, mergeFunction)),
		                      Map::putAll);
	}


	/**
	 * Splits map to list of maps
	 * @param map to be split
	 * @param limit max number of items in splitted map
	 * @param <K> key type
	 * @param <V> value type
	 * @return list of maps
	 */
	public static <K, V> List<Map<K, V>> split(Map<K, V> map, int limit) {

		Assert.notNull(map, "Missing map!");
		Assert.isTrue(limit > 0, "Map limit must be > 0!");

		if (map.size() <= limit) {
			return Collections.singletonList(map); // nothing to do
		}

		return map.entrySet().parallelStream().collect(mapSizer(limit));
	}

	/**
	 * Collector splitting map into seperate maps
	 * @param limit max number of items in slitted map
	 * @param <K> key type
	 * @param <V> value type
	 * @return collected map with size less or equal to limit
	 */
	private static <K, V> Collector<Map.Entry<K, V>, ?, List<Map<K, V>>> mapSizer(int limit) {
		return Collector.of(ArrayList::new,
		                    (l, e) -> {
			                    if (l.isEmpty() || l.get(l.size() - 1).size() == limit) {
				                    l.add(new HashMap<>());
			                    }
			                    l.get(l.size() - 1).put(e.getKey(), e.getValue());
		                    },
		                    (l1, l2) -> {
			                    if (l1.isEmpty()) {
				                    return l2;
			                    }
			                    if (l2.isEmpty()) {
				                    return l1;
			                    }
			                    if (l1.get(l1.size() - 1).size() < limit) {
				                    Map<K, V> map = l1.get(l1.size() - 1);
				                    ListIterator<Map<K, V>> mapsIte = l2.listIterator(l2.size());
				                    while (mapsIte.hasPrevious() && map.size() < limit) {
					                    Iterator<Map.Entry<K, V>> ite = mapsIte.previous().entrySet().iterator();
					                    while (ite.hasNext() && map.size() < limit) {
						                    Map.Entry<K, V> entry = ite.next();
						                    map.put(entry.getKey(), entry.getValue());
						                    ite.remove();
					                    }
					                    if (!ite.hasNext()) {
						                    mapsIte.remove();
					                    }
				                    }
			                    }
			                    l1.addAll(l2);
			                    return l1;
		                    }
		);
	}

	/**
	 * Sorts map with comparator
	 *
	 * @param map        to be sorted
	 * @param comparator to apply
	 * @param <K>        type of key
	 * @param <V>        type of value
	 * @return sorted map (LinkedHashMap)
	 */
	public static <K, V> Map<K, V> sort(Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {

		Assert.notNull(map, "Missing map!");
		Assert.notNull(comparator, "Missing comparator!");

		List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
		list.sort(comparator);

		Map<K, V> result = new LinkedHashMap<>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	/**
	 * Returns first value in map as given by the iterator or null if empty
	 *
	 * @param map to get first value out
	 * @param <T> object type of result
	 * @return first value in map or null if null or empty
	 */
	public static <T> T firstValue(Map<String, T> map) {

		if (map == null || map.size() == 0) {
			return null;
		}

		String firstKey = map.keySet().iterator().next();
		return map.get(firstKey);
	}

	/**
	 * Compares if two maps hold the same set of keys
	 *
	 * @param mapOne first map
	 * @param mapTwo second map
	 * @param <T>    object type map is holding
	 * @return true if all keys are present in both maps, or they are both empty or null, false otherwise
	 */
	public static <T> boolean equals(Map<String, T> mapOne, Map<String, T> mapTwo) {

		return equals(mapOne, mapTwo, false);
	}

	/**
	 * Compares if two maps hold the same set of keys and same values
	 * comparison of values utilizes the equals call, so make sure equals is implemented
	 *
	 * @param mapOne        first map
	 * @param mapTwo        second map
	 * @param compareValues true to compare also values, false to compare keys only
	 * @param <T>           object type map is holding
	 * @return true if all keys are present in both maps, or they are both empty or null, false otherwise
	 */
	public static <T> boolean equals(Map<String, T> mapOne, Map<String, T> mapTwo, boolean compareValues) {

		boolean firstEmpty = mapOne == null || mapOne.isEmpty();
		boolean secondEmpty = mapTwo == null || mapTwo.isEmpty();

		if (firstEmpty && secondEmpty) {
			return true;
		}

		if (firstEmpty || secondEmpty) {
			return false;
		}

		if (mapOne.size() != mapTwo.size()) {
			return false;
		}

		// compare keys
		for (String key : mapOne.keySet()) {

			if (compareValues) {
				T valueOne = mapOne.get(key);
				T valueTwo = mapTwo.get(key);

				if (valueOne == null && valueTwo == null) {
					continue;
				}

				if (valueTwo != null && valueTwo.equals(valueOne)) {
					continue;
				}

				return false;
			} else {
				if (!mapTwo.containsKey(key)) {
					return false;
				}
			}
		}

		return true;
	}
}
