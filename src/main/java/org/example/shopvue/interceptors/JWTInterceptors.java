package org.example.shopvue.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.shopvue.context.UserContext;
import org.example.shopvue.model.Power;
import org.example.shopvue.utils.AddToken;
import org.example.shopvue.utils.Result;
import org.example.shopvue.utils.ResultCodeEnum;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class JWTInterceptors implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String message="";
        // 获取请求头中令牌
        String token = request.getHeader("token");

        //验证token
        Power power = AddToken.getInformation(token);

        if(power != null) {
            UserContext.setUserId(power.getId());
            return true;
        }
        if (requestURI.contains("/shop/")) {
            return true;
        }
        message = "token无效或为空";

        // 将HttpResult以json的形式响应到前台  HttpResult --> json  (jackson)
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(Result.failure(ResultCodeEnum.UNAUTHORIZED,message));
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 移除用户
        UserContext.clear();
    }


}


