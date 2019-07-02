package com.wzx;

import com.wzx.middleware.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayRankApplicationTests {

    @Autowired
    private Sender sender;

    @Test
    public void hello() throws Exception {
        String msg = new Date().toString();
        sender.send(msg);
    }

}
