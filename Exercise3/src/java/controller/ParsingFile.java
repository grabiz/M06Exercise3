/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nguyen Euler
 */
//@WebServlet("/parsing")
public class ParsingFile extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        StringBuffer html = new StringBuffer();
        String path = req.getParameter("path");
        //Path dir = Paths.get(path);
        html.append("<!DOCTYPE html> <html> <head>");
        html.append("<title> Parsing a txt file </title>");
        html.append("<meta charset=\"UTF-8\">");
        html.append("<link rel=\"stylesheet\" href=\"css/main.css\"> </head>");
        html.append("<body>");
        ServletContext sc= getServletContext();
        try(InputStream servletPath = sc.getResourceAsStream(path))
        {
            InputStreamReader isr = new InputStreamReader(servletPath);
            Charset charset = Charset.forName("UTF-8");
            try (BufferedReader reader = new BufferedReader(isr)) {
                html.append("<table><caption>Email List</caption><tr><th>Email</th><th>First Name</th><th>Last Name</th></tr>");
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String email = line.substring(0, line.indexOf('/'));
                    String temp1 = line.substring(line.indexOf('/') + 1);
                    String firstName = temp1.substring(0, temp1.indexOf('/'));
                    String lastName = temp1.substring(temp1.indexOf('/') + 1);
                    html.append("<tr><td>" + email + "</td> <td>" + firstName + "</td> <td>" + lastName + "</td></tr>");
                }
                html.append("</table>");

            } catch (IOException e) {
                html.append("<h1> Can't open this file to parse.</h1>");
            }
        }catch(NullPointerException e)
        {
             html.append("<h1> Wrong path!!! Please check your path again.</h1>");
        }
        html.append("</html>");
        out.println(html);
    }
    
    
}
