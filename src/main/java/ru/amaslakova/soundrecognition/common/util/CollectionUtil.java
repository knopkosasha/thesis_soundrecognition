package ru.amaslakova.soundrecognition.common.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Util for collection operations.
 */
public final class CollectionUtil {

	private CollectionUtil() {
	}

	public static <T> Set<T> getExceededFromFirst(Set<T> first, Set<T> second) {
		Set<T> result = new HashSet<>();
		first.forEach(_t -> {
			if (second.contains(_t)) {
				result.add(_t);
			}
		});
		return result;
	}
}
