package com.github.biuld;

import com.github.biuld.util.Result;
import com.github.biuld.util.Token;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * Created by biuld on 2019/8/21.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenTest {

    @Test
    public void test() {

        String token = Token.create(Map.of("username", "vintage"));

        Result result = Token.validate(token);

        System.out.println(result);
    }
}
