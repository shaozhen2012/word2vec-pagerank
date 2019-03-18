package beanunit;

import com.rongpingkeji.common.client.opencv.ImgUtil;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by Daniel on 2019/2/16.
 */
@RunWith(JUnit4.class)
@Slf4j
public class OpenvcTest {


    @Test
    public void testImg() {
        ImgUtil.smoothImg("D:\\JAVAWEB\\RpOpenCV\\src\\main\\resources\\test-file\\input\\avatar.jpg", "D:\\JAVAWEB\\RpOpenCV\\src\\main\\resources\\test-file\\output\\avatar_out.jpg");
    }

    @Test
    public void testVideoToFram() {
        String videoPath = "http://object.pingxinghulian.com/rpVideoCenter/rpkj-test/20190216/20190216a5f521699269f3bedf33c4ba7f2c4c92.mp4";
        //String outputPath=videoPath.substring(videoPath.lastIndexOf("/")+1).split(".")[0];
        String path=videoPath.substring(videoPath.lastIndexOf("/") + 1);
        log.info("输出路径={}", path);
        log.info("分割={}", path.split("\\.")[1]);
    }

    @Test
    public void testZip() {

        String item="/opencv/rongping-dev/20190216ae7893ebb9838624502f57e5d3d747c4/20190216/2019021680a84a232af588e42a0b5c7d4f9f4bb5.jpg";
        log.info("fileName={}",item.substring(item.lastIndexOf("/") + 1));
//        VideoUtil.zipDirectory("D:\\JAVAWEB\\RpOpenCV\\src\\main\\resources\\test-file\\output\\movie_2","D:\\JAVAWEB\\RpOpenCV\\src\\main\\resources\\test-file\\output\\movie_2.zip");
    }
}
