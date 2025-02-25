package org.example.servlet.controller;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.TimeZone;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String timezone = req.getParameter("timezone");
        if (timezone != null && !timezone.isEmpty()) {
            timezone = timezone.replace(" ","+");
            if(!isValidTimezone(timezone)){
                res.setStatus(400);
                res.getWriter().println("Invalid timezone");
                res.getWriter().close();
                return;
            }
        }
        chain.doFilter(req, res);
    }
    private boolean isValidTimezone(String timezone) {
        try {
            String offsetStr = timezone.substring(3);
            int offsetHours = Integer.parseInt(offsetStr);
            int offsetMillis = offsetHours * 60 * 60 * 1000;
            String[] availableIDs = TimeZone.getAvailableIDs(offsetMillis);
            return availableIDs.length > 0;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
}
