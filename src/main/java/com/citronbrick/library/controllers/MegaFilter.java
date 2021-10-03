package com.citronbrick.library.controllers;

import org.springframework.stereotype.*;

import javax.servlet.*;
import java.io.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

// @Component
@WebFilter("/*")
public class MegaFilter implements Filter {

	public void init(FilterConfig config) {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest hreq = (HttpServletRequest)request;
		System.out.println(hreq.getRequestURL());
		chain.doFilter(request, response);
	}

	public void destroy() {

	}
}