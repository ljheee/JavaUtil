package com.ljheee.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
 
/**
 * https://www.oschina.net/code/snippet_129154_39174
 *
 */
public class FileDuplicateMain {
    private static Map<String, String> md5Map = new HashMap<String, String>();
    private static Map<String, List<String>> duplicateMap = new HashMap<String, List<String>>();
 
    public static void main(String[] args) throws Exception {
        String dir = "e:/ebook";
         
        Collection<File> files = listFile(dir);
        for (File file : files) {
            compare(file.getAbsolutePath());
        }
         
        for (List<String> duplicateFileList : duplicateMap.values()) {
            System.out.println(duplicateFileList);
        }
         
        //删除
        /*for (List<String> duplicateFileList : duplicateMap.values()) {
            for (int i = 0; i < duplicateFileList.size() - 1; i++) {
                String filePath = duplicateFileList.get(i);
                File file = new File(filePath);
                file.delete();
            }
        }*/
    }
     
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
        //long fromTime = System.currentTimeMillis();
         
        InputStream is = new FileInputStream(new File(file));
        byte[] data = new byte[1024];
         
        String md5 = "";
         
        while ((is.read(data)) != -1) {
            String tmp = DigestUtils.md5Hex(data);
            md5 = EncodeUtils.md5Hex(md5 + tmp);
        }
         
        is.close();
         
        //System.out.println(file + "计算MD5时间:\t" + (System.currentTimeMillis() - fromTime));
         
        return md5;
    }
}
