package beanunit;

import com.rongpingkeji.common.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Daniel on 2019/1/27.
 */
@RunWith(JUnit4.class)
@Slf4j
public class JwtTest {

    @Test
    public void testToken() {
        log.info("token={}", JwtUtil.generateToken("123456"));

    }

    @Test
    public void  testVerifyToken(){
        log.info("checkResult={}",JwtUtil.verifyToken("123456","eysJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJyb25ncGluZ2tlamkifQ.vHvaqNVyt3YMF7QXkR9WUV-PWLxZfkpHP6_N3Tq6jNo"));
    }

}
