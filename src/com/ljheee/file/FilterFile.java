package com.ljheee.file;

import java.io.File;

public class FilterFile {

	public static void filterReduplicated(File file) {

		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				filterReduplicated(f);
			}
		} else {
			String name = file.getName();
			if (name.contains("副本")) { // 删除文件中包含'重命名'字样的文件
				System.out.println(file.getAbsolutePath());
				file.delete();
			}
		}

	}

	public static void main(String[] args) {

	}

}
