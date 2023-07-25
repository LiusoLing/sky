package org.sky.admin.api.infrastructure.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * HttpServlet 工具类，获取当前请求的 request 和 response 及内部信息
 * </p>
 *
 * @author liusongling
 * @since 2023-07-25 11:20:12
 */
@Slf4j
@UtilityClass
public class CommonServletUtil {
    private static final String LOCAL_REMOTE_HOST = "0:0:0:0:0:0:0:1";
    private static final Searcher searcher;

    static {
        String fileName = "/ip2region.xdb";
        File existFile = FileUtil.file(FileUtil.getTmpDir() + FileUtil.FILE_SEPARATOR + fileName);
        if (!FileUtil.exist(existFile)) {
            InputStream resourceAsStream = CommonServletUtil.class.getResourceAsStream(fileName);
            if (ObjectUtils.isEmpty(resourceAsStream)) {
                throw new RuntimeException("CommonServletUtil初始化失败，原因：IP离线地址库数据不存在");
            }
            FileUtil.writeFromStream(resourceAsStream, existFile);
        }

        String dbPath = existFile.getPath();
        //1、从 dbPath 加载整个 xdb 到内存。
        byte[] cBuff;
        try {
            cBuff = Searcher.loadContentFromFile(dbPath);
        } catch (IOException e) {
            log.error(">>> CommonServletUtil初始化异常: ", e);
            throw new RuntimeException("CommonServletUtil初始化异常");
        }

        //2、使用上述的 cBuff 创建一个完全基于内存的查询对象。
        try {
            searcher = Searcher.newWithBuffer(cBuff);
        } catch (IOException e) {
            log.error(">>> CommonServletUtil初始化异常: ", e);
            throw new RuntimeException("CommonServletUtil初始化异常");
        }
    }

    /**
     * 获取请求的 HttpServletRequest
     *
     * @return {@link HttpServletRequest}
     */
    public HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = null;
        try {
            servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        } catch (Exception e) {
            log.error(">>> 非web上下文，无法获取Request：", e);
        }
        if (servletRequestAttributes == null) {
            throw new RuntimeException("非web上下文，无法获取Request");
        } else {
            return servletRequestAttributes.getRequest();
        }
    }

    /**
     * 获取请求的 HttpServletResponse
     *
     * @return {@link HttpServletResponse}
     */
    public HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = null;
        try {
            servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        } catch (Exception e) {
            log.error(">>> 非web上下文，无法获取Response：", e);
        }
        if (servletRequestAttributes == null) {
            throw new RuntimeException("非web上下文，无法获取Response");
        } else {
            return servletRequestAttributes.getResponse();
        }
    }

    /**
     * 从请求中获取指定参数
     *
     * @param paramName 参数名称
     * @return {@link String}
     */
    public String getParamFromRequest(String paramName) {
        HttpServletRequest request = getRequest();

        //1.尝试从请求体内读取
        String paramValue = request.getParameter(paramName);

        //2.尝试从header读取
        if (ObjectUtils.isEmpty(paramName)) {
            paramValue = request.getHeader(paramName);
        }

        //3.尝试从cookie读取
        if (ObjectUtils.isEmpty(paramValue)) {
            Cookie[] cookies = request.getCookies();
            if (!ObjectUtils.isEmpty(cookies)) {
                for (Cookie cookie : cookies) {
                    String cookieName = cookie.getName();
                    if (cookieName.equals(paramName)) {
                        return cookie.getValue();
                    }
                }
            }
        }

        return paramValue;
    }

    /**
     * 获取客户端IP
     *
     * @param request 请求
     * @return {@link String}
     */
    public String getIp(HttpServletRequest request) {
        if (ObjectUtils.isEmpty(request)) {
            return Ipv4Util.LOCAL_IP;
        } else {
            try {
                String clientIP = ServletUtil.getClientIP(request);
                return LOCAL_REMOTE_HOST.equals(clientIP) ? Ipv4Util.LOCAL_IP : clientIP;
            } catch (Exception e) {
                log.error(">>> 获取客户端ip异常: ", e);
                return Ipv4Util.LOCAL_IP;
            }
        }
    }

    /**
     * 根据IP离线获取城市区域
     *
     * @param ip 客户端IP
     * @return {@link String}
     */
    public String getRegion(String ip) {
        try {
            ip = ip.trim();
            //3、执行查询
            String region = searcher.search(ip);
            return region.replace("0|", "").replace("|0", "");
        } catch (Exception e) {
            log.error(">>> IP离线获取城市区域异常: ", e);
            return "未知";
        }
    }

    /**
     * 获取客户端浏览器
     *
     * @param request 请求
     * @return {@link String}
     */
    public String getBrowser(HttpServletRequest request) {
        UserAgent userAgent = getUserAgent(request);
        if (ObjectUtils.isEmpty(userAgent)) {
            return StrUtil.DASHED;
        } else {
            String browser = userAgent.getBrowser().toString();
            return "unknown".equalsIgnoreCase(browser) ? StrUtil.DASHED : browser;
        }
    }

    /**
     * 获取客户端操作系统
     *
     * @param request 请求
     * @return {@link String}
     */
    public String getOs(HttpServletRequest request) {
        UserAgent userAgent = getUserAgent(request);
        if (ObjectUtils.isEmpty(userAgent)) {
            return StrUtil.DASHED;
        } else {
            String os = userAgent.getOs().toString();
            return "unknown".equalsIgnoreCase(os) ? StrUtil.DASHED : os;
        }
    }

    /**
     * 获取请求代理头
     *
     * @param request 请求
     * @return {@link UserAgent}
     */
    private UserAgent getUserAgent(HttpServletRequest request) {
        String userAgentStr = ServletUtil.getHeaderIgnoreCase(request, "User-Agent");
        UserAgent userAgent = UserAgentUtil.parse(userAgentStr);
        if (ObjectUtil.isNotEmpty(userAgent)) {
            if ("unknown".equals(userAgent.getBrowser().getName())) {
                userAgent.setBrowser(new Browser(userAgentStr, null, ""));
            }
        }
        return userAgent;
    }
}
