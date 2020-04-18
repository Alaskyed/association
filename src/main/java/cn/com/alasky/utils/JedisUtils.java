package cn.com.alasky.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Author: Alaskyed
 * Time: 3/23/2020 5:29 PM
 * Package: cn.com.alasky.utils
 * Description:
 */
@Component
@Slf4j
public class JedisUtils {
    //创建连接池的引用
    private static volatile JedisPool jedisPool = null;

    //获取redis地址和端口
    private static String url;
    private static int port;
    @Value("${spring.redis.host}")
    public void setUrl(String url) {
        this.url = url;
    }
    @Value("${spring.redis.port}")
    public void setPort(int port) {
        this.port = port;
    }


    /**
     * 对外提供Redis连接池实例
     *
     * @return
     */
    public static JedisPool getJedisPoolInstance() {
        if (jedisPool == null) {
            //判断jedisPool是否是null,如果是,就加上锁
            synchronized (JedisUtils.class) {
                //再次判断是否为空
                if (jedisPool == null) {
                    JedisPoolConfig poolConfig = new JedisPoolConfig();
                    //设置最多有多少个空闲的实例
                    poolConfig.setMaxIdle(32);
                    //设置每个实例的最大等待时间
                    poolConfig.setMaxWaitMillis(100 * 1000);
                    //设置浏览器测试
                    //poolConfig.setTestOnBorrow(true);
                    jedisPool = new JedisPool(poolConfig, url, port);
                }
            }
        }
        return jedisPool;
    }

    /**
     * 关闭连接池的连接
     */
    public static void closeConnect(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 网页浏览量+1
     */

    public static void pageviewsIncrease() {
        //检测是否有连接池
        if (jedisPool == null) {
            jedisPool=getJedisPoolInstance();
        }

        //从连接池获取连接
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.incr("pageviews");
        } catch (Exception e) {
            log.error("获取Jedis连接出错: " + String.valueOf(e));
        } finally {
            //释放资源
            closeConnect(jedis);
        }


    }

}
