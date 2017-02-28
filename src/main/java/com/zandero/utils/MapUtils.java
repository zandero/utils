package com.zandero.utils;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Map manipulation utils
 */
public final class MapUtils {

	/**
	 * Merging single level aggregation maps, ie. grouped by userId, or grouped by interval
	 */
	public static <K, V, M extends Map<K, V>> M mergeMaps(Stream<? extends Map<K, V>> stream,
	                                                      BinaryOperator<V> mergeFunction, Supplier<M> mapSupplier) {

		return stream.collect(mapSupplier,
			(a, b) -> b.forEach((k, v) -> a.merge(k, v, mergeFunction)),
			Map::putAll);
	}

	public static <K1> Map<K1, Long> mergeMapsLevel1Sum(Map<K1, Long> map1, Map<K1, Long> map2) {

		return mergeMaps(Arrays.asList(map1, map2).stream(), Long::sum, HashMap::new);
	}

	/**
	 * Merging two level aggregation maps, ie. grouped by timeline, userId
	 */
	public static <K1, K2> Map<K1, Map<K2, Long>> mergeMapsLevel2Sum(Map<K1, Map<K2, Long>> map1, Map<K1, Map<K2, Long>> map2) {

		map2.forEach((k, v) -> map1.merge(k, v, (mm1, mm2) -> mergeMaps(Arrays.asList(mm1, mm2).stream(), Long::sum, HashMap::new)));
		return map1;
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
}
