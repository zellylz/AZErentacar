package com.rentacar.filter;

import com.rentacar.entity.Kullanici;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {
        "/booking.xhtml",
        "/rental.xhtml",
        "/success.xhtml"
})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        Kullanici kullanici = (Kullanici) req.getSession()
                .getAttribute("girisYapanKullanici");

        if (kullanici == null) {
            res.sendRedirect(req.getContextPath() + "/user.xhtml");
            return;
        }

        chain.doFilter(request, response);
    }
}
