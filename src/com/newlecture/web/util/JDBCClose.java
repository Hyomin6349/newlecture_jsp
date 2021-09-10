package com.newlecture.web.util;

public class JDBCClose {
	
	public static void close(AutoCloseable...autoCloseables) {
		for(AutoCloseable autoCloseable: autoCloseables) {
			if(autoCloseable!=null)
				try {
					autoCloseable.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}
