package com.rongpingkeji.common.client.fileservice.FileServiceImpl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectResult;

import com.rongpingkeji.common.client.fileservice.FileBaseService;
import com.rongpingkeji.common.client.fileservice.FileService;
import com.rongpingkeji.common.util.ExceptionUtil;
import com.rongpingkeji.common.util.FileUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Computer on 2018/3/30.
 */
@Service("oos")
@Slf4j
public class FileServiceAliyun implements FileService {

    @Value("${application.service.oss.endpoint}")
    private String endpoint;
    @Value("${application.service.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${application.service.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${application.service.oss.filePath}")
    private String filePath;
    @Value("${application.service.oss.bucketName}")
    private String bucketName;
    @Value("${application.service.oss.cdnDomain}")
    private String cdnDomain;
    @Value("${application.file.upload.maxSize:50}")
    public long MaxSize;

    @Override
    public Map<String, String> saveFile(String name, long size, InputStream inputStream) {

        String ext = FileUtil.getFileExtension(name).toLowerCase();

        Map<String, String> result = new HashMap<>();

        if (!FileBaseService.CheckImgExt(ext)) {
            ExceptionUtil.BaseError("图片格式错误");
        }
        if (MaxSize * 1024 * 1024 < size) {
            ExceptionUtil.BaseError("图片大小超出，最大为" + MaxSize + "M");
        }

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        String fn = String.format("%s/%s.%s",
                DateFormatUtils.format(new Date(), "yyyyMMdd"),
                FileBaseService.setFileName(),
                ext
        );
        String uploadpath = filePath.concat(fn);
        PutObjectResult putObjectResult = ossClient.putObject(bucketName, uploadpath, inputStream);

        if (putObjectResult.getRequestId() != "") {
            result.put("prefix", "");
            result.put("location", cdnDomain.concat(uploadpath));
        } else {
            ExceptionUtil.BaseError("上传失败");
        }
        ossClient.shutdown();
        return result;
    }


    @Override
    public Map<String, String> saveFileCenter(String name, long size, InputStream inputStream, String path, boolean resetName) {

        String ext = FileUtil.getFileExtension(name).toLowerCase();

        Map<String, String> result = new HashMap<>();

        if (!FileBaseService.CheckFileExt(ext)) {
            ExceptionUtil.BaseError("文件格式错误"+ext);
        }
        if (MaxSize * 1024 * 1024 < size) {
            ExceptionUtil.BaseError("文件大小超出，最大为" + MaxSize + "M");
        }

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        String fn = "";
        if (resetName) {
            fn = String.format(path + "/" + "%s/%s.%s",
                    DateFormatUtils.format(new Date(), "yyyyMMdd"),
                    FileBaseService.setFileName(),
                    ext
            );
        } else {
            fn = String.format(path + "/" + "%s/%s",
                    DateFormatUtils.format(new Date(), "yyyyMMdd"),
                    name
            );
        }

        String uploadpath = filePath.concat(fn);
        PutObjectResult putObjectResult = ossClient.putObject(bucketName, uploadpath, inputStream);

        if (putObjectResult.getRequestId() != "") {
            result.put("prefix", "");
            result.put("location", cdnDomain.concat(uploadpath));
        } else {
            ExceptionUtil.BaseError("上传失败");
        }
        ossClient.shutdown();
        return result;
    }


    @Override
    public List<String> listFile(String prefix) {

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        List<String> result=new ArrayList<>();
        listObjectsRequest.setPrefix(prefix);
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            result.add(objectSummary.getKey());
        }
        return result;
    }


}
