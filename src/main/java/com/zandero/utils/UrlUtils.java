package com.zandero.utils;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public final class UrlUtils {

	private UrlUtils() {
		// hide constructor
	}

	public static String getFullUrl(String rootUrl, String relativeUrl) {

		if (rootUrl == null || rootUrl.trim().length() == 0) {
			throw new IllegalArgumentException("rootUrl must be given.");
		}

		URI root;
		try {
			if (rootUrl.endsWith("/")) {
				rootUrl = rootUrl.substring(0, rootUrl.length() - 1);
			}

			root = new URI(rootUrl);
		}
		catch (URISyntaxException e) {
			throw new IllegalArgumentException("Invalid root URL given.");
		}

		if (relativeUrl != null) {
			try {
				URI uri = new URI(relativeUrl);
				relativeUrl = uri.toString();

				if (uri.isAbsolute()) {
					return relativeUrl;
				}

				if (!relativeUrl.startsWith("/")) {
					relativeUrl = "/" + relativeUrl;
				}

				URI full = root.resolve(relativeUrl);
				return full.toString();
			}
			catch (URISyntaxException e) {
				throw new IllegalArgumentException("Invalid relative URL given.");
			}
		}

		return root.toString();
	}

	public static String composeUrl(String root, String path) {

		if (StringUtils.isNullOrEmptyTrimmed(path)) {
			return root.endsWith("/") ? root.substring(0, root.length() - 1) : root;
		}

		if (root.endsWith("/")) {

			if (path.startsWith("/")) {
				path = path.substring(1);
			}

			return root + path;
		}

		if (!path.startsWith("/")) {
			path = "/" + path;
		}

		return root + path;
	}

	public static String composeUrl(String scheme, String domain, int port) {

		return composeUrl(scheme, domain, port, null, null);
	}

	public static String composeUrl(String scheme, String domain, int port, String path) {

		return composeUrl(scheme, domain, port, path, null);
	}

	public static String composeUrl(String scheme, String domain, int port, String path, Map<String, String> query) {

		// clean up path ...
		if (StringUtils.isNullOrEmptyTrimmed(path)) {
			path = "";
		}
		else if (!path.startsWith("/") &&
			!path.startsWith("?")) {
			path = "/" + path;
		}

		// add port if needed ...
		if (port > 0 && port != 80 && port != 443) {
			domain = domain + ":" + port;
		}

		String queryString = "";
		if (query != null && query.size() > 0) {

			for (String name : query.keySet()) {

				String value = query.get(name);

				if (!StringUtils.isNullOrEmptyTrimmed(name) &&
					!StringUtils.isNullOrEmptyTrimmed(value)) {

					if (queryString.length() > 1) {
						queryString = queryString + "&";
					}

					queryString = queryString + name + "=" + encodeQuery(value);
				}
			}
		}

		// clean up ...
		if (path.endsWith("/") || path.endsWith("?") || path.endsWith("&")) {
			path = path.substring(0, path.length() - 1);
		}

		if (queryString.length() > 1) {
			// path already contains some query ... continue
			queryString = (path.contains("?") ? "&" : "?") + queryString;
		}

		return scheme + "://" + domain + path + queryString;
	}

	/**
	 * Resolves domain name from given URL
	 *
	 * @param url to get domain name from
	 * @return domain name or null if not resolved
	 */
	public static String resolveDomain(String url) {

		if (StringUtils.isNullOrEmptyTrimmed(url)) {
			return null;
		}

		// check if url starts with scheme part (http:// ... ) if not add one manually
		if (!url.contains("://")) {
			url = "http://" + url;
		}

		try {
			URI uri = new URI(url);
			return uri.getHost();
		}
		catch (URISyntaxException e) {
			return null;
		}
	}


	public static String getBaseUrl(String url) {

		if (!isUrl(url)) {
			return null;
		}

		String scheme = resolveScheme(url);
		String domain = resolveDomain(url);
		Integer port = resolvePort(url);
		return composeUrl(scheme, domain, port == null ? 80 : port);
	}

	public static String urlEncode(String link) {

		try {
			String decodedURL = URLDecoder.decode(link, EncodeUtils.UTF_8);
			URL url = new URL(decodedURL);
			URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			return uri.toASCIIString().trim();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String resolveScheme(String baseUrl) {

		try {
			URI uri = new URI(baseUrl);
			return uri.getScheme();
		}
		catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Integer resolvePort(String baseUrl) {

		try {
			URI uri = new URI(baseUrl);
			int port = uri.getPort();

			return port >= 0 ? port : 80;
		}
		catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String encodeQuery(String query) {

		if (StringUtils.isNullOrEmptyTrimmed(query)) {
			return query;
		}

		try {
			return URLEncoder.encode(query, EncodeUtils.UTF_8);
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return query;
		}
	}

	public static boolean isUrl(String value) {

		return !StringUtils.isNullOrEmptyTrimmed(value) &&
			UrlValidator.getInstance().isValid(value);
	}

	public static Map<String, String> parse(String path) {

		Map<String, String> query = new LinkedHashMap<>();

		int minLength = 0;
		int index = path.indexOf("?");
		if (index >= 0) {
			path = path.substring(index + 1);
		}
		else if (isUrl(path)) {
			minLength = 1;
		}

		String[] parts = path.split("&");
		if (parts.length > minLength) {

			for (String part: parts) {
				String[] nameValue = part.split("=");
				if (nameValue.length == 2) {
					query.put(nameValue[0], nameValue[1]);
				}

				if (nameValue.length == 1) {
					query.put(nameValue[0], null);
				}
			}
		}

		return query;
	}
}