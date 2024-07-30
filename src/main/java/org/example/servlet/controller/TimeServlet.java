package org.example.servlet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet (value = "/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        String timezone = req.getParameter("timezone");
        if (timezone == null || timezone.isEmpty()) {
            timezone = "UTC";
        }
        timezone = timezone.replace(" ", "+");
        ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of(timezone));
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(formattedTime + " "+timezone);
        resp.getWriter().close();
    }
}
