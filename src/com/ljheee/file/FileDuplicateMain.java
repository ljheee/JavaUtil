package com.ljheee.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
 
/**
 * 不足：重复的“副本”文件被保留
 * https://www.oschina.net/code/snippet_129154_39174
 *
 */
public class FileDuplicateMain {
    private static Map<String, String> md5Map = new HashMap<String, String>();
    private static Map<String, List<String>> duplicateMap = new HashMap<String, List<String>>();
 
     
    public static void compare(String filePath) throws Exception {
        String md5 = fileToMd5(filePath);
        if (md5Map.keySet().contains(md5)) {
            List<String> fileNameList = duplicateMap.get(md5);
            if (fileNameList == null) {
                fileNameList = new ArrayList<String>();
            }
            fileNameList.add(filePath);
             
            //加入第一个
            fileNameList.add(md5Map.get(md5));
            duplicateMap.put(md5, fileNameList);
             
        } else {
            md5Map.put(md5, filePath);
        }
    }
     
    public static Collection<File> listFile(String dir) {
        return FileUtils.listFiles(new File(dir), null, true);
    }
     
    public static String fileToMd5(String file) throws Exception {
         
    	/*
        InputStream is = new FileInputStream(new File(file));
        byte[] data = new byte[1024];
        String md5 = "";
        while ((is.read(data)) != -1) {
            String tmp = DigestUtils.md5Hex(data);
            md5 = EncodeUtils.md5Hex(md5 + tmp);
        }
        is.close();
         */
    	
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
			return noAlgorith.toString();
		} catch (FileNotFoundException e) {
			return fileNotFound.toString();
		} catch (IOException e) {
			return IOError.toString();
		}
		return sb.toString();
    }
    
    public static void main(String[] args) throws Exception {
        String dir = "E:\\oppo2";
         
        Collection<File> files = listFile(dir);
        for (File file : files) {
            compare(file.getAbsolutePath());
        }
         
        for (List<String> duplicateFileList : duplicateMap.values()) {
            System.out.println(duplicateFileList);
        }
         
        //删除
        for (List<String> duplicateFileList : duplicateMap.values()) {
            for (int i = 0; i < duplicateFileList.size() - 1; i++) {
                String filePath = duplicateFileList.get(i);
                File file = new File(filePath);
                file.delete();
                System.out.println("delete="+file.getAbsolutePath());
            }
        }
        
    }
}
