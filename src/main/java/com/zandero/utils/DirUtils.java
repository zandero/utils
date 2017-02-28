package com.zandero.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DirUtils {

	private DirUtils() {
		// hide constructor
	}

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

	public static List<File> getAllFilesRecursive(File rootDir, String fileExtension) {
		return getAllFilesRecursive(rootDir, fileExtension == null ? null : new String[]{fileExtension});
	}

	public static List<File> getAllFilesRecursive(File rootDir, String[] fileExtensions) {
		List<File> javaFiles = new ArrayList<>();
		collectFilesInDirectoryTree(rootDir, javaFiles, fileExtensions, true);
		return javaFiles;
	}

	public static List<File> getAllFilesInDir(File rootDir, String fileExtension) {
		return getAllFilesInDir(rootDir, fileExtension == null ? null : new String[]{fileExtension});
	}

	public static List<File> getAllFilesInDir(File rootDir, String[] fileExtensions) {
		List<File> javaFiles = new ArrayList<>();
		collectFilesInDirectoryTree(rootDir, javaFiles, fileExtensions, false);
		return javaFiles;
	}

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
		fileList.addAll(Arrays.asList(files));

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
	 * @param directory
	 * @return
	 */
	public static long dirSize(File directory) {
		long length = 0;
		for (File file : directory.listFiles()) {
			if (file.isFile())
				length += file.length();
			else
				length += dirSize(file);
		}
		return length;
	}

}
