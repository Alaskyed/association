package cn.com.alasky;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * cn.com.alasky
 * 2020/1/7 下午11:26
 * author: Alasky
 * description: 使用主机自定义的Tomcat启动应用
 */
public class ServletInitializer extends SpringBootServletInitializer {
    //创建日志
    private static Logger logger= LogManager.getLogger(StartApp.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        logger.info("外部tomcat启动!");
        return application.sources(StartApp.class);
    }

}

