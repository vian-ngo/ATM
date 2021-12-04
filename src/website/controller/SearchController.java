package website.controller;

import MODEL.SanPham;
import website.dao.SearchDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/search")
public class SearchController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String input = request.getParameter("input");
        PrintWriter out = response.getWriter();
        StringBuilder resp = new StringBuilder();
        String giaBan;
        if (input != null && !input.equals("")) {
            try {
                List<SanPham> dssp = SearchDAO.search(input);
                if (dssp != null) {
                    for (SanPham sp : dssp) {
                        if (sp.getGiaBan() != 0) {
                            giaBan = String.format("%,.0f", sp.getGiaBan()) + "đ";
                        } else {
                            giaBan = "";
                        }
                        resp.append("<li><a href='/chi-tiet-san-pham?id=").append(sp.getMaSanPham()).append("'").append(">").append("<img src='").append(sp.getHinhAnh()).append("' alt=''>").append("<h3>").append(sp.getTenSanPham()).append("</h3>").append("<span class='price'>").append(String.format("%,.0f", sp.getGiaDaGiam())).append("đ").append("</span>").append("<cite>").append(giaBan).append("</cite>").append("</a></li>");
                    }
                    out.print(resp);
                } else {
                    out.print("empty");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            out.print("empty");
        }
    }
}