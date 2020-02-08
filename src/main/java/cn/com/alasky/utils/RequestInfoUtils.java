package cn.com.alasky.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * cn.com.alasky.utils
 * 2020/1/30 上午10:01
 * author: Alasky
 * description:
 */

public class RequestInfoUtils {
    /**
     * 获取访问设备信息
     * @param request
     * @return  设备信息
     */
    public static String getDeviceInfo(HttpServletRequest request) {
        //获取user-agent请求头信息
        String agentStr = request.getHeader("user-agent");
        //截取设备部分,其实就是截取的额第一个()里的内容,并且使用 [] 括起来
        String deviceInfo = "[ " + agentStr.substring(agentStr.indexOf("(") + 1, agentStr.indexOf(")")) + " ]";

        return deviceInfo;
    }

    /**
     * 获取IP地址
     * @param request
     * @return
     */
    public static String getIPAdrress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        /*System.out.println("====ipAddresses:"+ipAddresses);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            //打印所有头信息
            String s = headerNames.nextElement();
            String header = request.getHeader(s);
            System.out.println(s+"::::"+header);
        }
        System.out.println("RemoteHost:"+request.getRemoteHost());
        System.out.println("RemoteAddr:"+request.getRemoteAddr());
*/
        String unknown = "unknown";
        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 调用上面两个方法,获取IP地址和设备信息
     * @param request
     * @return
     */
    public static String getIPAndDeviceInfo(HttpServletRequest request) {
        String deviceInfo = getDeviceInfo(request);
        String iPAddr = getIPAdrress(request);
        return "IP: "+iPAddr+" 设备信息: "+deviceInfo;

    }
}
