package cn.tycoding.api.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.sun.org.apache.bcel.internal.classfile.Utility.printArray;

/**
 * Description:
 *
 * @author 创建者
 * @author 修改者
 * @version 2019/12/16 17:37
 * @since 2019/12/16 17:37
 */
@Component
public class MyLogFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(MyLogFilter.class);

    /**
     * filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
     * pre：路由之前
     * routing：路由之时
     * post： 路由之后
     * error：发送错误调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * filterOrder：过滤的顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * shouldFilter：这里可以写逻辑判断，是否要过滤，本文true,永远过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * run：过滤器的具体逻辑。可以很复杂，包括查sql，nosql去判断该请求到底有没有权限访问。
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();
            log.info(String.format("logger filter：%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
            Map<String, String[]> map = request.getParameterMap();
            // 打印请求url参数
            if (map != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("request parameters:\t");
                for (Map.Entry<String, String[]> entry : map.entrySet()) {
                sb.append("[" + entry.getKey() + "=" + printArray(entry.getValue()) + "]");
                }
                log.info(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
         }
        return null;
    }
}
