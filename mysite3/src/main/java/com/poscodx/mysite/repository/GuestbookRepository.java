package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
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

	public void insert(GuestbookVo vo) {

		try (Connection conn = getConnection();

				PreparedStatement pstmt = conn.prepareStatement("insert into guestbook values(null, ?, ?, ?,now())");) {

			// 4. binding
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContents());

			// 5. SQL 실행
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	public void deleteByNoAndPassword(Long no, String password) {
		

		
		

		try (Connection conn = getConnection();

				PreparedStatement pstmt = conn.prepareStatement("delete from guestbook where no=? and password = ?");){
			

			

			

			// 4. binding
			pstmt.setLong(1, no);
			pstmt.setString(2, password);

			// 4. SQL 실행
			pstmt.executeUpdate();

			

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		

		
	}

	public List<GuestbookVo> findAll() {
		List<GuestbookVo> result = new ArrayList<>();

		
		
		ResultSet rs = null;

		try (Connection conn = getConnection();

				PreparedStatement pstmt = conn.prepareStatement("   select no, name, password, contents, DATE_FORMAT(reg_date, '%Y-%m-%d') as reg_date"
						+ "     from guestbook" + " order by no desc");){
			

			

			

			// 5. SQL 실행
			rs = pstmt.executeQuery();

			// 6. 결과 처리
			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String contents = rs.getString(4);
				String reg_date = rs.getString(5);

				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setPassword(password);
				vo.setContents(contents);
				vo.setRegDate(reg_date);

				result.add(vo);
			}
		}  catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		

		return result;
	}
}