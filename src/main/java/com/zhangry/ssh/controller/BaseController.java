package com.zhangry.ssh.controller;

import com.zhangry.common.enums.AscDesc;
import com.zhangry.common.page.QueryParameter;
import com.zhangry.common.util.ExceptionUtil;
import com.zhangry.common.util.StringUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by zhangry on 2017/2/22.
 */
public abstract class BaseController {
    /**
     * 获取QueryParameter对象
     *
     * @return
     */
    protected QueryParameter getQueryParameter(Map<String, String> params) {
        QueryParameter queryParameter = new QueryParameter();

        queryParameter.setPageSize(Integer.parseInt(params.get("limit")));
        queryParameter.setPageNo(Integer.parseInt(params.get("offset")) / queryParameter.getPageSize() + 1);

        String sort = params.get("sort") == null ? null : params.get("sort");
        String order = params.get("order") == null ? null : params.get("order");

        if (StringUtil.isNullOrEmpty(sort) || StringUtil.isNullOrEmpty(order)) {
            return queryParameter;
        }

        if (order.equalsIgnoreCase(AscDesc.ASC.name())) {
            queryParameter.addSort(sort, AscDesc.ASC);
        } else if (order.equalsIgnoreCase(AscDesc.DESC.name())) {
            queryParameter.addSort(sort, AscDesc.DESC);
        } else {
            throw new IllegalArgumentException(StringUtil.format("order can only be asc or desc. current order = {0}", order));
        }

        return queryParameter;
    }


    /***
     * Get request query string, form method : post
     *
     * @param request
     * @return byte[]
     * @throws IOException
     */
    private byte[] getRequestPostBytes(HttpServletRequest request) {
        int contentLength = request.getContentLength();
        /*当无请求参数时，request.getContentLength()返回-1 */
        if (contentLength < 0) {
            return null;
        }

        byte buffer[] = new byte[contentLength];

        InputStream inputStream = null;

        try {
            inputStream = request.getInputStream();

            for (int i = 0; i < contentLength; ) {
                int readlen = inputStream.read(buffer, i, contentLength - i);
                if (readlen == -1) {
                    break;
                }

                i += readlen;
            }
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    throw ExceptionUtil.unchecked(ex);
                } finally {
                    inputStream = null;
                }
            }
        }

        return buffer;
    }

    /***
     * Get request query string, form method : post
     *
     * @param request
     * @return
     * @throws IOException
     */
    protected String getRequestPostString(HttpServletRequest request) {
        try {
            byte buffer[] = getRequestPostBytes(request);
            String charEncoding = request.getCharacterEncoding();
            if (charEncoding == null) {
                charEncoding = "UTF-8";
            }
            return new String(buffer, charEncoding);
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }


    /**
     * 获取HttpServletRequest
     *
     * @return
     */
    protected HttpServletRequest request() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取HttpServletResponse
     *
     * @return
     */
    protected HttpServletResponse response() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 直接将json字符串回显到前端
     *
     * @param s
     */
    protected void render(String s) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
