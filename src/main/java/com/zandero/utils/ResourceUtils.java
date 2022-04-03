package com.zandero.utils;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Utility class to read resources
 */
public final class ResourceUtils {

    private static final int BUFFER_SIZE = 100_000;

    private static final String UTF_8 = "UTF-8";

    private ResourceUtils() {
        // hide constructor
    }

    /**
     * Loads class resource to String
     *
     * @param resourceFile to read
     * @param clazz        to use for resource access
     * @return String representing the resource or null if resource could not be read
     */
    @Deprecated
    public static String getResourceAsString(String resourceFile, Class clazz) {

        Assert.notNullOrEmptyTrimmed(resourceFile, "Missing resource file!");

        Scanner scanner = null;

        try {
            InputStream resource = clazz.getResourceAsStream(resourceFile);
            scanner = new Scanner(resource, UTF_8);
            return scanner.useDelimiter("\\A").next();
        } catch (Exception e) {
            return null;
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * Loads resource to String
     *
     * @param resourceFile to read
     * @return String representing the resource or null if resource could not be read
     */
    public static String getResourceAsString(String resourceFile) {

        return getResourceAsString(resourceFile, ResourceUtils.class);
    }

    /**
     * Loads resource to Stream
     *
     * @param resource to read
     * @return Stream representing the resource
     */
    public InputStream getResourceAsStream(String resource) {
        return ResourceUtils.class.getResourceAsStream(resource);
    }

    /**
     * Loads resource as a set of Strings, where each word is added to the set
     *
     * @param resourceFile to read
     * @param clazz        to use for resource access
     * @return set of strings (lines) or null if resource could not be read
     */
    public static Set<String> getResourceWords(String resourceFile, Class clazz) {

        Assert.notNullOrEmptyTrimmed(resourceFile, "Missing resource file!");

        Scanner scanner = null;

        try {
            InputStream resource = clazz.getResourceAsStream(resourceFile);
            scanner = new Scanner(resource, UTF_8);

            Set<String> list = new LinkedHashSet<>();
            while (scanner.hasNext()) {
                String next = scanner.next();
                if (next != null && next.trim().length() > 0) {
                    list.add(next);
                }
            }

            return list;
        } catch (Exception e) {
            return null;
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * Loads resource as a set of Strings, where each word is added to the set
     *
     * @param resourceFile to read
     * @return set of strings (lines) or null if resource could not be read
     */
    public static Set<String> getResourceWords(String resourceFile) {

        return getResourceWords(resourceFile, ResourceUtils.class);
    }

    /**
     * Get resource last modified date
     *
     * @param resourceFile to read
     * @param clazz        to use for resource access
     * @return last modified date or null if resource could not be read
     */
    @Deprecated
    public static Long getLastModifiedTime(String resourceFile, Class clazz) {


        Assert.notNullOrEmptyTrimmed(resourceFile, "Missing resource file!");

        try {
            URL url = clazz.getResource(resourceFile);
            return url.openConnection().getLastModified(); // get last modified date of resource
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Get resource last modified date
     *
     * @param resourceFile to read
     * @return last modified date or null if resource could not be read
     */
    public static Long getLastModifiedTime(String resourceFile) {
        return getLastModifiedTime(resourceFile, ResourceUtils.class);
    }

    /**
     * Load input stream into string
     *
     * @param is stream
     * @return String representation of given input
     */
    public static String getString(final InputStream is) {

        return getString(is, UTF_8);
    }

    /**
     * Load input stream into string
     *
     * @param is       stream
     * @param encoding to use when reading input stream
     * @return String representation of given input
     */
    public static String getString(final InputStream is, String encoding) {

        if (is == null) {
            return null;
        }

        if (StringUtils.isNullOrEmptyTrimmed(encoding)) {
            encoding = UTF_8;
        }

        final char[] buffer = new char[BUFFER_SIZE];
        final StringBuilder out = new StringBuilder();
        try {
            try (Reader in = new InputStreamReader(is, encoding)) {
                for (; ; ) {
                    int rsz = in.read(buffer, 0, buffer.length);
                    if (rsz < 0) {
                        break;
                    }
                    out.append(buffer, 0, rsz);
                }
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        return out.toString();
    }

    /**
     * Load input stream into byte array
     *
     * @param is stream
     * @return byte representation of given input
     */
    public static byte[] getBytes(InputStream is) {

        if (is == null) {
            return null;
        }

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[BUFFER_SIZE];
        try {

            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
        } catch (IOException ignored) {
        }

        return buffer.toByteArray();
    }

    /**
     * Reads file into String
     *
     * @param file to be read
     * @return file content
     * @throws IOException in case file does't exist or is not a file
     */
    public static String readFileToString(File file) throws IOException {

        Assert.isTrue(file.exists(), "File '" + file + "' does not exist");
        Assert.isFalse(file.isDirectory(), "File '" + file + "' is a directory");
        Assert.isTrue(file.canRead(), "File '" + file + "' cannot be read");

        FileInputStream stream = new FileInputStream(file);
        return getString(stream);
    }

    /**
     * Gets absolute file path of resource
     *
     * @param resource to get absolute file path for
     * @param clazz    namespace holding resource
     * @return file path if found
     * @throws IllegalArgumentException if resource can not be found
     */
    @Deprecated
    public static String getResourceAbsolutePath(String resource, Class clazz) {

        Assert.notNullOrEmptyTrimmed(resource, "Missing resource name!");

        URL file = clazz.getResource(resource);
        Assert.notNull(file, "Resource: '" + resource + "', not found!");

        return file.getFile();
    }

    /**
     * Retursn absolute location of resource on file system
     *
     * @param resource to get absolute path for
     * @return absolute path on system for resource if found
     */
    public static String getResourceAbsolutePath(String resource) {

        return getResourceAbsolutePath(resource, ResourceUtils.class);
    }
}
