package springtest;

import com.rongpingkeji.app.RpApplication;
import com.rongpingkeji.common.client.fileservice.FileService;
import com.rongpingkeji.common.client.opencv.OpenCVVideoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Daniel on 2019/2/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RpApplication.class)
@Slf4j
public class OpenCVTest {


    @Autowired
    private OpenCVVideoService video;

    @Autowired
    @Qualifier("oos")
    private FileService file;

    @Value("${application.file.upload.temp}")
    private String fileTemp;

    @Test
    public void testVideoFrame() {
        video.getVideoFrameToAliyun("http://rpkj-opencv.oss-cn-shanghai.aliyuncs.com/opencv/rongping-dev/rpkj-test/20190216/20190216ae7893ebb9838624502f57e5d3d747c4.mp4", "rpkj-test");
    }

    @Test
    public void testZip() {
        video.zipDirectory("https://rpkj-opencv.oss-cn-shanghai.aliyuncs.com/opencv/rongping-dev/20190216ae7893ebb9838624502f57e5d3d747c4/", "D:\\\\JAVAWEB\\\\RpOpenCV\\\\src\\\\main\\\\resources\\\\test-file\\\\output\\\\rpkj-test.zip");
    }

    @Test
    public void testListObjects() {

        file.listFile("opencv/rongping-dev/20190216ae7893ebb9838624502f57e5d3d747c4/");
    }


    @Test
    public void testZipToOss() {
        video.zipOssDirectory("opencv/rongping-dev/20190216ae7893ebb9838624502f57e5d3d747c4/", "opencv/rongping-dev/", "test.zip");
    }


}
