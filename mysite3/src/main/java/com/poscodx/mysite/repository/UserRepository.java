package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.poscodx.mysite.exception.UserRepositoryException;
import com.poscodx.mysite.vo.UserVo;

@Repository
public class UserRepository {
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
			throw new UserRepositoryException(e.toString());
			
		}
		
	}

	public UserVo findByEmailAndPassword(String email, String password) {
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

	public UserVo findByNo(Long no) {
		UserVo result=null;
		
		try (Connection conn = getConnection();

				PreparedStatement pstmt = conn.prepareStatement("select no,name,email,gender from user where no=?");
					) {

				// 4. binding
				pstmt.setLong(1, no);
				
				
				

				// 5. SQL 실행
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					Long no_2 = rs.getLong(1);
					String name = rs.getString(2);
					String email = rs.getString(3);
					String gender = rs.getString(4);
					result=new UserVo();
					result.setNo(no_2);
					result.setName(name);
					result.setEmail(email);
					result.setGender(gender);
				}
				rs.close();
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		
		return result;
	}

	public void update(UserVo vo) {
		int count=0;
		try (Connection conn = getConnection();

				PreparedStatement pstmt1 = conn.prepareStatement("UPDATE user SET name=?, password=password(?), gender=? WHERE no=?");
				PreparedStatement pstmt2 = conn.prepareStatement("UPDATE user SET name=?, gender=? WHERE no=?");
					) {
				
				if("".equals(vo.getPassword())) {
					pstmt2.setString(1, vo.getName());
					pstmt2.setString(2, vo.getGender());
					pstmt2.setLong(3, vo.getNo());
					count = pstmt2.executeUpdate();
				}else {
					pstmt1.setString(1, vo.getName());
					pstmt1.setString(2, vo.getPassword());
					pstmt1.setString(3, vo.getGender());
					pstmt1.setLong(4, vo.getNo());
					
					count = pstmt1.executeUpdate();
				}
				// 4. binding
				
				
				
				

				
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		
		
	}
	
	
}
