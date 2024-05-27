package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.poscodx.mysite.vo.UserVo;

public class UserDao {
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://192.168.0.212:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}

	public void insert(UserVo vo) {
		try (Connection conn = getConnection();

			PreparedStatement pstmt1 = conn.prepareStatement("insert into user values(null,?,?,password(?),?,current_date())");
				) {

			// 4. binding
			pstmt1.setString(1, vo.getName());
			pstmt1.setString(2, vo.getEmail());
			pstmt1.setString(3, vo.getPassword());
			pstmt1.setString(4, vo.getGender());

			// 5. SQL 실행
			pstmt1.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
	}

	public UserVo findByNoAndPassword(String email, String password) {
		UserVo result=null;
		
		try (Connection conn = getConnection();

				PreparedStatement pstmt = conn.prepareStatement("select no,name from user where email=? and password=password(?)");
					) {

				// 4. binding
				pstmt.setString(1, email);
				
				pstmt.setString(2, password);
				

				// 5. SQL 실행
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					Long no = rs.getLong(1);
					String name = rs.getString(2);
					result=new UserVo();
					result.setNo(no);
					result.setName(name);
				}
				rs.close();
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		
		return result;
	}
	
	
}
