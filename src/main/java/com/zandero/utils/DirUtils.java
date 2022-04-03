package com.zandero.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Path / folder utilities
 */
public final class DirUtils {

	private DirUtils() {
		// hide constructor
	}

	/**
	 * Returns file extension of given file path
	 * /test/file.txt -> txt
	 * @param filePath to get file extension
	 * @return file extension without '.' of null if none found
	 */
	public static String getFileExtension(String filePath) {

		if (filePath == null) {
			return null;
		}

		filePath = filePath.trim();

		int extSeparatorIndex = filePath.lastIndexOf(".");
		if (extSeparatorIndex == -1) {
			return null;
		}
		return filePath.substring(extSeparatorIndex + 1);
	}

	/**
	 * Lists all files on given folder recursively
	 * @param rootDir folder
	 * @param fileExtension file extension filter or null to get all files
	 * @return list of files or empty list if none found
	 */
	public static List<File> getAllFilesRecursive(File rootDir, String fileExtension) {
		return getAllFilesRecursive(rootDir, fileExtension == null ? null : new String[]{fileExtension});
	}

	/**
	 * Lists all files on given folder recursively
	 * @param rootDir folder
	 * @param fileExtensions set of file extensions to filter or null to get all files
	 * @return list of files or empty list if none found
	 */
	public static List<File> getAllFilesRecursive(File rootDir, String[] fileExtensions) {
		List<File> javaFiles = new ArrayList<>();
		collectFilesInDirectoryTree(rootDir, javaFiles, fileExtensions, true);
		return javaFiles;
	}

	/**
	 * Returns all files on a given folder (non-recursively)
	 * @param rootDir folder
	 * @param fileExtension file extension filter or null to get all files
	 * @return list of found files
	 */
	public static List<File> getAllFilesInDir(File rootDir, String fileExtension) {
		return getAllFilesInDir(rootDir, fileExtension == null ? null : new String[]{fileExtension});
	}

	/**
	 * Returns all files on a given folder (non-recursively)
	 * @param rootDir folder
	 * @param fileExtensions list of file extensions to filter or null to get all files
	 * @return list of found files
	 */
	public static List<File> getAllFilesInDir(File rootDir, String[] fileExtensions) {
		List<File> javaFiles = new ArrayList<>();
		collectFilesInDirectoryTree(rootDir, javaFiles, fileExtensions, false);
		return javaFiles;
	}

	/**
	 * Collects files on a given folder
	 * @param directory folder
	 * @param fileList collected files
	 * @param fileExtensions file extension to filter out or null for all
	 * @param recursive if we want to get all files recursevely
	 */
	private static void collectFilesInDirectoryTree(File directory, List<File> fileList, final String[] fileExtensions, boolean recursive) {
		File[] files = directory.listFiles((file) -> {
			if (file.isDirectory()) {
				return false;
			}

			// pass any no file extensions defined
			if (fileExtensions == null || fileExtensions.length == 0) {
				return true;
			}

			for (String extension : fileExtensions) {
				if (file.getName().endsWith("." + extension)) {
					return true;
				}
			}
			return false;
		});

		if (files != null && files.length > 0) {
			fileList.addAll(Arrays.asList(files));
		}

		if (recursive) {
			File[] dirs = directory.listFiles(File::isDirectory);
			if (dirs != null) {
				for (File dir : dirs) {
					collectFilesInDirectoryTree(dir, fileList, fileExtensions, recursive);
				}
			}
		}
	}

	/**
	 * Calculates size of all files in given directory and it's sub-directories.
	 *
	 * @param directory to check size
	 * @return sum of all file sizes in given directory, 0 if no files present
	 */
	public static long dirSize(File directory) {

		if (directory == null) {
			throw new IllegalArgumentException("Missing directory/folder!");
		}

		File[] files = directory.listFiles();

		long length = 0;
		if (files != null) {
			for (File file : files) {
				if (file.isFile())
					length += file.length();
				else
					length += dirSize(file);
			}
		}
		return length;
	}

}
