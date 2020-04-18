package cn.com.alasky;

import cn.com.alasky.utils.JedisUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.JedisPool;

/**
 * cn.com.alasky
 * 2020/1/7 下午11:26
 * author: Alasky
 * description: 启动类
 */

@SpringBootApplication
@EnableTransactionManagement    //添加事务管理
//@MapperScan("cn.com.alasky.mapper")
public class StartApp {
    //创建日志
    private static Logger logger= LogManager.getLogger(StartApp.class);
    public static void main(String[] args) {
        logger.info("启动应用");
        SpringApplication.run(StartApp.class, args);

    }
}

