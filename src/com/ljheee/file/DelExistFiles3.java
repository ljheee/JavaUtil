package com.ljheee.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * http://blog.csdn.net/j2eelamp/article/details/6408366
 *
 */
public class DelExistFiles3 {
	// 定义一个数组，用来存放不重复的文件的MD5值
	private List<String> md5List = new ArrayList<String>();
	static int num = 0;

	/**
	 * 删除指定目录下的所有重复文件
	 * 
	 * @param path
	 *            要删除的文件路径
	 */
	public void delFile(String path) {
		File f = new File(path);
		// 如果给的是一个文件，则继续
		if (f.isFile()) {
			// 取得该文件的MD5值
			String md5 = getMd5(f).toString();
			// 如果存放md5值的数组不为空
			if (md5List.size() > 0) {
				for (String s : md5List) {
					// 如果数组中有了和该文件md5z值相同的项，表明该文件已经存在,删除该文件
					if (md5.equals(s)) {
						f.delete();
						num++;
						System.out.println("文件/t" + f.getName() + "/t和已有文件重复，已被删除");
					}
				}
				// 判断该文件是否存在，如果该文件没有被删除，表明没有和该文件相同的文件，则把其md5值存入数组之中
				if (f.exists()) {
					md5List.add(md5);
				}
			} else {
				// 数组为空，表示还没有相同的项,将把其md5值存入数组之中
				md5List.add(md5);
			}
		} else if (f.isDirectory()) {
			// 拿到它的子文件列表
			File[] flist = f.listFiles();
			for (File file : flist) {
				// 如果列表项为File，则继续，同上
				if (file.isFile()) {
					String md5 = getMd5(file).toString();
					if (md5List.size() > 0) {
						for (String s : md5List) {
							if (md5.equals(s)) {
								file.delete();
								num++;
								System.out.println("文件/t" + file.getName() + "/t和已有文件重复，已被删除");
							}
						}
						if (file.exists()) {
							md5List.add(md5);
						}
					} else {
						md5List.add(md5);
					}
					// 如果列表项为文件夹，递归调用自身
				} else if (file.isDirectory()) {
					delFile(file.getPath());
				}
			}
		}
	}

	/**
	 * 取得文件的Md5值
	 * 
	 * @param file
	 *            要计算的文件
	 * @return MD5值的字符序列
	 */
	public static StringBuilder getMd5(File file) {
		StringBuilder sb = new StringBuilder();
		StringBuilder fileNotFound = new StringBuilder("系统找不到指定的路径,请重新输入");
		StringBuilder noAlgorith = new StringBuilder("无法进行Md5加密算法，可能是因为的java虚拟机版本太低");
		StringBuilder IOError = new StringBuilder("IO错误");
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5"); // 生产MD5类的实例
			FileInputStream in = new FileInputStream(file);
			BufferedInputStream bs = new BufferedInputStream(in);
			byte[] b = new byte[bs.available()]; // 定义数组b为文件不受阻塞的可读取字节数

			// 将文件以字节方式读取到数组b中
			while ((bs.read(b, 0, b.length)) != -1) {
			}
			md5.update(b); // 执行md5算法
			for (byte by : md5.digest()) {
				sb.append(String.format("%02X", by)); // 将生成的字节转化成16进制的字符串
			}
			bs.close();
		} catch (NoSuchAlgorithmException e) {
			return noAlgorith;
		} catch (FileNotFoundException e) {
			return fileNotFound;
		} catch (IOException e) {
			return IOError;
		}
		return sb;
	}

	public static void main(String[] args) {
		DelExistFiles3 df = new DelExistFiles3();
		System.out.println("请输入要删除的文件夹");
		Scanner scanner = new Scanner(System.in);
		String str = scanner.next();
		df.delFile(str);
		System.out.println("操作结束，共删除了/t" + num + "/t个重复文件");
	}
}
