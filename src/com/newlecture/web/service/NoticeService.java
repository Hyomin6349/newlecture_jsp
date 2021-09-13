package com.newlecture.web.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;
import com.newlecture.web.util.JDBCClose;
import com.newlecture.web.util.JDBCConnection;

public class NoticeService {
	
	public int removeNoticeAll(int[] ids) {
		return 0;
	}
	
	public int insertNotice(Notice notice) {
		return 0;
	}
	
	public int deleteNotice(int id) {
		return 0;
	}
	
	public int updateNotice(Notice notice) {
		return 0;
	}
	
	public List<Notice> getNoticeNewestList(){
		return null;
	}
	
	
	public List<NoticeView> getNoticeList(){
		
		return getNoticeList("title", "", 1);
	}
	
	public List<NoticeView> getNoticeList(int page){
		
		return getNoticeList("title", "", page);
	}
	
	public List<NoticeView> getNoticeList(String field, String query, int page){
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		List<NoticeView> list = new ArrayList<>();
		String sql = "SELECT * FROM " 
				+"(SELECT ROW_NUMBER() OVER (ORDER BY REGDATE DESC) NUM, N.* "
				+ "FROM NOTICE_VIEW N "
				+ "WHERE "+ field +" LIKE ? ) "
				+ "WHERE NUM BETWEEN ? AND ?";

		try {
			con = JDBCConnection.getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, "%"+query+"%");
			st.setInt(2, 1+(page-1)*10);
			st.setInt(3, page*10);
			rs = st.executeQuery();

			while(rs.next()){	
				int id = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regdate = rs.getDate("REGDATE");
				String writer_id = rs.getString("WRITER_ID");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				//String content = rs.getString("CONTENT");
				int cmtCount = rs.getInt("CMT_COUNT");
				
				NoticeView notice = new NoticeView(id, title, regdate, writer_id, hit, files, cmtCount);
				list.add(notice);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCClose.close(con, st, rs);
		}
		
		return list;
	}
	
	public int getNoticeCount(){
		
		return getNoticeCount("title", "");
	}
	
	public int getNoticeCount(String field, String query) {
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		int count = 0; 
		String sql = "SELECT COUNT(*) COUNT "
				+ "FROM NOTICE N "
				+ "WHERE "+ field +" LIKE ? ";
		
		try {
			con = JDBCConnection.getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, "%"+query+"%");
			rs = st.executeQuery();

			if(rs.next())
				count = rs.getInt("count");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCClose.close(con, st, rs);
		}
		
		return count;
	}
	
	public Notice getNotice(int id) {
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		Notice notice = null;
		String sql = "SELECT * FROM NOTICE WHERE ID=?";
		
		try {
			con = JDBCConnection.getConnection();
			st = con.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();

			if(rs.next()){	
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regdate = rs.getDate("REGDATE");
				String writer_id = rs.getString("WRITER_ID");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				
				notice = new Notice(nid, title, regdate, writer_id, hit, files, content);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCClose.close(con, st, rs);
		}
		
		return notice;
	}
	
	public Notice getPrevNotice(int id) {
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		Notice notice = null;
		String sql = "SELECT * FROM NOTICE WHERE ID = "
				   + "(SELECT ID FROM NOTICE WHERE REGDATE < ( "
				   + "SELECT REGDATE FROM NOTICE WHERE ID = ?) "
				   + "AND ROWNUM=1)";
		
		try {
			con = JDBCConnection.getConnection();
			st = con.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();

			if(rs.next()){	
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regdate = rs.getDate("REGDATE");
				String writer_id = rs.getString("WRITER_ID");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				
				notice = new Notice(nid, title, regdate, writer_id, hit, files, content);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCClose.close(con, st, rs);
		}
		
		return notice;
	}
	
	public Notice getNextNotice(int id) {
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		Notice notice = null;
		String sql = "SELECT * FROM NOTICE WHERE ID = "
				   + "(SELECT MAX(ID), ROW_NUMBER() OVER (ORDER BY REGDATE DESC) "
				   + "FROM NOTICE WHERE REGDATE > ( "
				   + "SELECT REGDATE FROM NOTICE WHERE ID = ?) "
				   + "AND ROWNUM=1)";
		

		try {
			con = JDBCConnection.getConnection();
			st = con.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();

			if(rs.next()){	
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regdate = rs.getDate("REGDATE");
				String writer_id = rs.getString("WRITER_ID");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				
				notice = new Notice(nid, title, regdate, writer_id, hit, files, content);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCClose.close(con, st, rs);
		}
		
		return notice;
	}
}
