package com.zandero.utils;

import java.util.*;

/**
 *
 */
public final class SetUtils {

	private SetUtils() {
		// hide constructor
	}

	/**
	 * Splits set in 1..N chunks of maxSize or less
	 *
	 * @param set     to be spitted
	 * @param maxSize of a given chunk
	 * @param <T>     type of class in set
	 * @return list of sets containing all elements of original set without duplicates
	 */
	public static <T> List<Set<T>> split(Set<T> set, int maxSize) {

		Assert.notNull(set, "Missing set to be split!");
		Assert.isTrue(maxSize > 0, "Max size must be > 0!");

		if (set.size() < maxSize) {
			return Collections.singletonList(set);
		}

		List<Set<T>> list = new ArrayList<>();
		Iterator<T> iterator = set.iterator();

		while (iterator.hasNext()) {
			Set<T> newSet = new HashSet<>();
			for (int j = 0; j < maxSize && iterator.hasNext(); j++) {
				T item = iterator.next();
				newSet.add(item);
			}
			list.add(newSet);
		}
		return list;
	}
}
