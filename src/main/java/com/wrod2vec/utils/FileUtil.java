package com.wrod2vec.utils;


import com.rongpingkeji.common.util.ExceptionUtil;

import java.io.*;

/**
 * Created by 振 on 2018/3/15.
 */
public class FileUtil {


    public static void writeFile(String path, String content) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(path);
        fileOutputStream.write(content.getBytes());
        fileOutputStream.close();
    }


    /**
     * 追加文件
     *
     * @param path
     * @param content
     */
    public static void appendFile(String path, String content) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true)));
            out.write(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    public static String readFile(String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);
        return new String(bytes);

    }

    public static File mkdir(String folderPath) {
        File file = new File(folderPath);
        if (!file.exists() || !file.isDirectory()) {
            boolean success = false;
            do {
                success = file.mkdirs();
            } while (success);

        }
        return file;
    }


    /**
     * 列出指定目录的文件
     *
     * @param dirPath
     * @return
     */
    public static File[] listFile(String dirPath) {

        File f = new File(dirPath);
        if (!f.isDirectory()) {
           ExceptionUtil.BaseError("is not a valid directory");
        }

        File[] result = f.listFiles();
        for (File item : result) {
            System.out.println(item.getPath());
        }
        return result;
    }


    /**
     * 合并文件到同一个文件下
     *
     * @param dirPath
     * @param newFile
     * @return
     */
    public static boolean mergeFile(String dirPath, String newFile) {

        File[] result = listFile(dirPath);
        String content = "";
        for (File item : result) {
            try {
                content = readFile(item.getPath()).replaceAll("\n\r","");
                appendFile(newFile, content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


}
