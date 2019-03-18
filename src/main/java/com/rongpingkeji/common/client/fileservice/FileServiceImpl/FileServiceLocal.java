package com.rongpingkeji.common.client.fileservice.FileServiceImpl;


import com.rongpingkeji.common.client.fileservice.FileBaseService;
import com.rongpingkeji.common.client.fileservice.FileService;
import com.rongpingkeji.common.util.ExceptionUtil;
import com.rongpingkeji.common.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shozhen on 2018/3/29.
 * 上传图片到服务器
 */
@Service("local")
public class FileServiceLocal implements FileService {

    @Value("${application.file.upload.prefix}")
    private String filePrefix;

    @Value("${application.file.upload.path:/usr/local/docker/nginx/www}")
    private String UploadPath;

    @Value("${application.file.upload.maxSize:50}")
    public long MaxSize;

    @Value("${application.file.upload.displayPath}")
    private String displayPath;


    @Override
    public Map<String, String> saveFile(String name, long size, InputStream inputStream) {

        String ext = FileUtil.getFileExtension(name).toLowerCase();

        Map<String, String> result = new HashMap<>();
        result.put("prefix", filePrefix);
        if (!FileBaseService.CheckImgExt(ext)) {
            ExceptionUtil.BaseError("图片格式错误");
        }
        if (MaxSize * 1024 * 1024 < size) {
            ExceptionUtil.BaseError("图片大小超出，最大为" + MaxSize + "M");
        }
        String fn = String.format(displayPath + "/%s/%s.%s",
                DateFormatUtils.format(new Date(), "yyyyMMdd"),
                FileBaseService.setFileName(),
                ext
        );
        try {
            FileUtils.copyInputStreamToFile(inputStream, new File(UploadPath.concat(fn)));
            result.put("location", fn);
        } catch (IOException e) {
            ExceptionUtil.BaseError("创建目录失败");
        }
        return result;
    }


    @Override
    public Map<String, String> saveFileCenter(String name, long size, InputStream inputStream, String dirName,boolean resetName) {

        String ext = FileUtil.getFileExtension(name).toLowerCase();

        Map<String, String> result = new HashMap<>();
        result.put("prefix", filePrefix);
        if (!FileBaseService.CheckFileExt(ext)) {
            ExceptionUtil.BaseError("文件格式错误");
        }
        if (MaxSize * 1024 * 1024 < size) {
            ExceptionUtil.BaseError("图片大小超出，最大为" + MaxSize + "M");
        }
        String fn = String.format(displayPath + dirName + "/%s/%s.%s",
                DateFormatUtils.format(new Date(), "yyyyMMdd"),
                FileBaseService.setFileName(),
                ext
        );
        try {
            FileUtils.copyInputStreamToFile(inputStream, new File(UploadPath.concat(fn)));
            result.put("location", fn);
        } catch (IOException e) {
            ExceptionUtil.BaseError("创建目录失败");
        }
        return result;
    }


}
