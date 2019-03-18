package com.rongpingkeji.common.client.opencv;


import com.rongpingkeji.common.client.fileservice.FileService;
import com.rongpingkeji.common.client.opencv.dto.VideoResponse;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Daniel on 2019/2/16.
 */
@Slf4j
public class OpenCVVideoService {


    @Value("${application.service.oss.cdnDomain}")
    private String cdnDomain;

    @Value("${application.file.upload.temp}")
    private String fileTemp;

    @Autowired
    @Qualifier("oos")
    private FileService fileService;

    public static void getVideoFrame(String videoPath, String outputPath) {
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(videoPath);
        int frameIndex = 0;
        Frame frame = null; //帧对象
        try {
            fFmpegFrameGrabber.start();
            int ftp = fFmpegFrameGrabber.getLengthInFrames();
            log.info("帧数量={}", ftp);
            while (frameIndex <= ftp) {
                File dir = new File(outputPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String fileName = outputPath + "/frame_" + String.valueOf(frameIndex) + ".jpg";
                File output = new File(fileName);
                frame = fFmpegFrameGrabber.grabImage();
                if (frame != null) {
                    ImageIO.write(FrameToBufferedImage(frame), "jpg", output);
                }
                frameIndex++;

            }
            fFmpegFrameGrabber.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 视频读帧上传入oss
     *
     * @param videoPath
     */
    public VideoResponse.VideoFrameResponse getVideoFrameToAliyun(String videoPath, String dirName) {
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(videoPath);
        VideoResponse.VideoFrameResponse result = new VideoResponse.VideoFrameResponse();
        int frameIndex = 0;
        Frame frame = null; //帧对象
        try {
            fFmpegFrameGrabber.start();
            int ftp = fFmpegFrameGrabber.getLengthInFrames();
            log.info("帧数量={}", ftp);
            result.setFrameNum(ftp);
            String videoName = videoPath.substring(videoPath.lastIndexOf("/") + 1).split("\\.")[0];
            result.setVideoName(videoName);
            String outputPath = dirName + "/" + videoName;
            log.info("输出目录={}", outputPath);
            while (frameIndex <= ftp) {
                String fileName = "frame_" + String.valueOf(frameIndex) + ".jpg";
                frame = fFmpegFrameGrabber.grabImage();
                if (frame != null) {
                    BufferedImage imgItem = FrameToBufferedImage(frame);
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    ImageIO.write(imgItem, "jpg", output);
                    fileService.saveFileCenter(fileName, output.size(), new ByteArrayInputStream(output.toByteArray()), outputPath, false);
                }
                frameIndex++;
            }
            fFmpegFrameGrabber.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * @param dirName (oss前缀目录)
     * @param zipPath (压缩的路径(oss))
     * @param zipName
     * @return
     */
    public String zipOssDirectory(String dirName, String zipPath, String zipName) {

        String zipResult = "";
        String localDir = System.getProperty("user.dir") + fileTemp;
        File dir = new File(localDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(localDir + zipName);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            List<String> fileList = fileService.listFile(dirName);
            for (String item : fileList) {
                URL url = new URL(cdnDomain + item);
                ZipEntry zipEntry = new ZipEntry(item.substring(item.lastIndexOf("/") + 1));
                zipOutputStream.putNextEntry(zipEntry);
                byte[] buffer = new byte[1024];
                int r = 0;
                InputStream imgBuffer = url.openConnection().getInputStream();
                while ((r = imgBuffer.read(buffer)) != -1) {
                    zipOutputStream.write(buffer, 0, r);
                }
                zipOutputStream.closeEntry();
            }
            zipOutputStream.close();//压缩到本地临时目录
            File file = new File(localDir + zipName);
            FileInputStream fileInput = new FileInputStream(localDir + zipName);
            Map<String, String> result = fileService.saveFileCenter(zipName, file.length(), fileInput, zipPath, false);  //压缩文件上传到阿里云
            zipResult = result.get("location");
            log.info("压缩文件目录={}", zipResult);
            file.delete();  //删除本地文件
            fileInput.close();
            fileOutputStream.close();
            zipOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zipResult;
    }


    /**
     * 压缩指定目录
     *
     * @param dirName
     * @return
     */
    public String zipDirectory(String dirName, String zipName) {

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipName);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            File file = new File(dirName);
            if (!file.isDirectory()) {
                throw new Exception("压缩目录不存在");
            }
            File[] fileList = file.listFiles();
            for (File item : fileList) {
                ZipEntry zipEntry = new ZipEntry(item.getName());
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.closeEntry();
            }
            zipOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zipName;
    }


    public static BufferedImage FrameToBufferedImage(Frame frame) {

        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }


}
