package com.rongpingkeji.common.client.fileservice;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Computer on 2018/3/29.
 */
public interface FileService {
    /**
     * 返回图片的在线地址
     *
     * @param inputStream
     * @return
     */
    public Map<String, String> saveFile(String name, long size, InputStream inputStream);


    /**
     * 返回文件的在线地址
     * @param inputStream
     * @return
     */
    public Map<String, String> saveFileCenter(String name, long size, InputStream inputStream, String dirName,boolean resetName);


    default  List<String> listFile(String path){
        return null;
    };

}
