package com.dharun.tedboss.filters;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;




@WebFilter("/EmailFilter")
public class EmailFilter implements Filter {

    /**
     * Default constructor. 
     */
	Pattern emailPattern ;
	
    public EmailFilter() {
        // TODO Auto-generated constructor stub
    }


	public void destroy() {
		// TODO Auto-generated method stub
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String email = request.getParameter("email");
		Matcher matcher = emailPattern.matcher(email);
		if(!matcher.matches()) {
			response.getWriter().append("Invalid Email");
			return;
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		emailPattern=  Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$"); 
	}

}
