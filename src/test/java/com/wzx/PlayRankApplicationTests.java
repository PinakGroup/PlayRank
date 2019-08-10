package com.wzx;

import com.wzx.middleware.Sender;
import com.wzx.service.impl.MsgServiceImpl;
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

    @Autowired
    private MsgServiceImpl msgService;

    @Test
    public void hello() throws Exception {
        String msg = new Date().toString();
        msgService.setMsg("1", msg);
    }
}
