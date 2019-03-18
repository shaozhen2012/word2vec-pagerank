package com.rongpingkeji.common.client.fileservice;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by Computer on 2018/3/29.
 */

public class FileBaseService {

    public static String[] imgExt = {"png", "jpg", "jpeg", "bmp", "gif"};
    public static String[] fileExt = {"pdf","mp4","mp3","xls","jpg","zip"};


    /**
     * 随机生成文件名
     *
     * @return
     */
    public static String setFileName() {

        LocalDate now = LocalDate.now();
        UUID uuid = UUID.randomUUID();
        return String.format("%4d%02d%02d%s%s"
                , now.getYear()
                , now.getMonthValue()
                , now.getDayOfMonth()
                , Long.toHexString(uuid.getLeastSignificantBits())
                , Long.toHexString(uuid.getMostSignificantBits())
        );

    }

    public static boolean CheckImgExt(String ext) {

        List<String> list = Arrays.asList(imgExt);
        return list.contains(ext);
    }


    public static boolean CheckFileExt(String ext) {

        List<String> list = Arrays.asList(fileExt);
        return list.contains(ext);
    }



}
