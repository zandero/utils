package com.zandero.utils;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Map manipulation utils
 */
public final class MapUtils {

	private MapUtils() {
		// hide constructor
	}

	/**
	 * Merging single level aggregation maps, ie. grouped by userId, or grouped by interval
	 */
	public static <K, V, M extends Map<K, V>> M mergeMaps(Stream<? extends Map<K, V>> stream,
	                                                      BinaryOperator<V> mergeFunction, Supplier<M> mapSupplier) {

		return stream.collect(mapSupplier,
			(a, b) -> b.forEach((k, v) -> a.merge(k, v, mergeFunction)),
			Map::putAll);
	}

	public static <K, V> Map<K, V> sort(Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {

		List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
		Collections.sort( list, comparator);


		Map<K, V> result = new LinkedHashMap<>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	public static <T> T getFirstValue(Map<String, T> out) {

		if (out == null || out.size() == 0) {
			return null;
		}

		String firstKey = out.keySet().iterator().next();
		return out.get(firstKey);
	}

	public static boolean equals(HashMap<String, String> mapOne, HashMap<String, String> mapTwo) {

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
		for (String key: mapOne.keySet()) {
			if (!mapTwo.containsKey(key)) {
				return false;
			}
		}

		return true;
	}
}
