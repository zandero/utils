package com.zandero.utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
public final class KeywordUtils {

	private KeywordUtils() {}

	public static Set<String> getStopWords() {
		// remove common english words ... like "a", "to", "from" ... etc.
		// https://raw.githubusercontent.com/jdf/cue.language/master/src/cue/lang/stop/english - taken from Lucene
		return getStopWords("/keywords/english.txt");
	}

	public static Set<String> getStopWords(String resource) {

		return ResourceUtils.getResourceLines(resource, KeywordUtils.class);
	}

	/**
	 * Extracts words ... keywords from string and returns set of words
	 * ignores emails and URLs and common english words
	 *
	 * @param max max number of desired keywords to return, or <= 0 for unlimited
	 * @param value to extract keywords from
	 */
	public static Set<String> extractKeywords(String value, Set<String> stopWords, int max) {

		Set<String> keywords = new HashSet<>();

		if (StringUtils.isNullOrEmptyTrimmed(value)) {
			return keywords;  // nothing to do
		}

		// sanitize words first
		List<String> items = StringUtils.split(value, " ");


		for (String item : items) {

			// don't use emails, or URLs ...
			if (StringUtils.isEmail(item) ||
				UrlUtils.isUrl(item)) {

				continue;
			}

			List<String> words = StringUtils.getWords(item);

			// add keywords not found in stopWords
			for (String word : words) {
				word = word.trim().toLowerCase();

				if (stopWords == null || !stopWords.contains(word)) {

					if (word.length() > 1) { // at least two characters long to be considered a keyword
						keywords.add(word);
						if (max > 0 && keywords.size() >= max) {
							return keywords;
						}
					}
				}
			}

			if (max > 0 && keywords.size() >= max) {
				return keywords;
			}
		}

		return keywords;
	}

	public static Map<String, Integer> sortByAppearance(Map<String, Integer> keywords, int max) {
		// ok reduce number of keywords to top 10 by appearance...
		if (keywords.size() <= max) {
			return keywords;
		}

		return keywords.entrySet()
			.stream()
			.sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
			.limit(max)
			.collect(Collectors.toMap(
				Map.Entry::getKey,
				Map.Entry::getValue,
				(e1, e2) -> e1,
				LinkedHashMap::new
			));
	}
}
