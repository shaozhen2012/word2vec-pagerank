package com.rongpingkeji.common.client.opencv;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

/**
 * Created by Daniel on 2019/2/16.
 */
public class ImgUtil {

    public static void smoothImg(String fileName, String fileOutputName) {
        Mat image = imread(fileName);
        if (image != null) {
            GaussianBlur(image, image, new Size(3, 3), 0);
            imwrite(fileOutputName, image);
        }
    }
}
