package com.newlecture.web.controller.notice;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;
import com.newlecture.web.service.NoticeService;

@WebServlet("/notice/list")
public class ListController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String field = "title";
		String query = "";
		int page = 1;
		
		String field_ = req.getParameter("f");
		String query_ = req.getParameter("q");
		String page_ = req.getParameter("p"); // null로 들어올 수 있기 때문
		
		if(field_!=null && !field_.equals("")) field = field_;
		if(query_!=null && !query_.equals("")) query = query_;
		if(page_!=null && !page_.equals("")) page = Integer.parseInt(page_);
		
		
		NoticeService service = new NoticeService();
		List<NoticeView> list = service.getNoticePubList(field, query, page);
		int count = service.getNoticeCount(field, query);
		
		req.setAttribute("list", list);
		req.setAttribute("count", count);
		req.getRequestDispatcher("/WEB-INF/view/notice/list.jsp").forward(req, resp); 
		
	}

}
