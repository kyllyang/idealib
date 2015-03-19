package org.kyll.idea.temp;

import oracle.jdbc.driver.OracleDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExportAndImportMet {
	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			DriverManager.registerDriver(new OracleDriver());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.10:1521:orcl", "emptyp", "emptyp");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int count = 0;
		File dir = new File("met\\img");
		File[] files = dir.listFiles();
		for (File file : files) {
			System.out.println(file.getName());

			count++;

			PreparedStatement pstmt = null;
			try {
				pstmt = conn.prepareStatement("insert into busi_metdl_image values(?, ?)");
				pstmt.setString(1, file.getName().replace(".jpg", ""));
				pstmt.setBinaryStream(2, new FileInputStream(file), (int) file.length());
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

			if (count % 10 == 0) {
				try {
					conn.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
