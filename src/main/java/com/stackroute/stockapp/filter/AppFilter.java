package com.stackroute.stockapp.filter;

 /*
  * create filter class which implements OncePerRequestFilter
  * if the request is valid then it will forward the request to the controller
  * else it will be  send  the response as unauthorized
  * check  the jwt token is valid for all the endpoints
  * exclude  /api/v1/user/register and /api/v1/user/login
  */

import java.io.IOException;
import java.util.Date;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;


public class AppFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;


        if (request.getRequestURI().equals("/api/v1/user/register") || request.getRequestURI().equals("/api/v1/user/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ") && requestTokenHeader.length() > 7){
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = Jwts.parser().setSigningKey("Success").parseClaimsJws(jwtToken).getBody().getSubject();
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
 


 


 





 