package fz.cs.daoyun;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

@SpringBootTest
class DaoyunApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public static void main(String[] args) {
        // 连接到本地的redis服务
        Jedis jedis = new Jedis("127.0.0.1");
        System.out.println("连接成功");
        // 查看服务是否运行
        System.out.println("服务正在运行：" + jedis.ping());
    }

}
