package springtest;

import com.rongpingkeji.app.RpApplication;
import com.rongpingkeji.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Daniel on 2019/1/28.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RpApplication.class)
@Slf4j
public class UserTest {

    @Autowired
    private AdminService admin;


    @Test
    public void testRoleDetail() {

        log.info("roleDetail={}", admin.roleDetail(1));

    }


}
