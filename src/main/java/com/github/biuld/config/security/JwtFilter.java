package com.github.biuld.config.security;

import com.github.biuld.util.Result;
import com.github.biuld.util.Token;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by biuld on 2019/8/21.
 */
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtUserService jwtUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String token = request.getHeader("x-access-token");

        if (!StringUtils.isEmpty(token)) {
            Result result = Token.validate(token);

            //token验证不成功
            if (result.getCode() != 200) {
                print(response, result);
                return;
            }

            Claims claims = (Claims) result.getData();
            String userId = (Integer) claims.get("userId") + "";

            //整合Spring Security
            JwtUser jwtUser = (JwtUser) jwtUserService.loadUserByUsername(userId);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //加载用户信息到request，方便后续使用
            request.setAttribute("user", jwtUser.getUser());
        }

        chain.doFilter(request, response);
    }

    private void print(HttpServletResponse response, Object message) {
        try (PrintWriter writer = response.getWriter();) {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setHeader("Cache-Control", "no-cache, must-revalidate");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Origin", "*");
            writer.write(message.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
