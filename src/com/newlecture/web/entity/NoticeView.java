package com.newlecture.web.entity;

import java.sql.Date;

public class NoticeView extends Notice {
	
	private int cmtCount;
	
	public int getCmtCount() {
		return cmtCount;
	}

	public void setCmtCount(int cmtCount) {
		this.cmtCount = cmtCount;
	}

	public NoticeView() {
		// TODO Auto-generated constructor stub
	}

	public NoticeView(int id, String title, Date regdate, String writer_id, int hit, String files, int cmtCount) {
		super(id, title, regdate, writer_id, hit, files, "");
		this.cmtCount = cmtCount;
	}
	
}
