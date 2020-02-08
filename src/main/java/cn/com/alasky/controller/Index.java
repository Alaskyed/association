package cn.com.alasky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * cn.com.alasky.controller
 * 2020/1/7 下午11:43
 * author: Alasky
 * description:
 */

@Controller
public class Index {
    @RequestMapping("/")
    public String index() {
        return "index.jsp";
    }
}
