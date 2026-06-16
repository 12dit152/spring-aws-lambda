package com.samardash.config;

import org.springframework.stereotype.Component;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class PaginationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String pageNumber = req.getParameter("pageNumber");
        if (pageNumber != null && !pageNumber.equals("1") && req.getMethod().equalsIgnoreCase("GET")) {
            res.setContentType("application/json");
            res.getWriter().write("{ \"succeeded\": true, \"data\": [], \"pageNumber\": " + pageNumber + ", \"nextPage\": null }");
            return;
        }

        chain.doFilter(request, response);
    }
}
