package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.poscodx.mysite.vo.BoardVo;

public class BoardDao {
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

	// 게시물 작성(insert) 함수
	public void insert(BoardVo boardVo) {

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"insert into board (no, title, contents, hit, reg_date, g_no, o_no, depth, user_no) "
								+ "select null, ?, ?, 0, now(), coalesce(max(g_no), 0) + 1, 1, 0, ? from board");) {
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContents());
			pstmt.setLong(3, boardVo.getUserNo());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 리스트 한 페이지에 정해진 수 만큼만 꺼내는 함수
	public List<BoardVo> findByPageAndKeyword(int page,String kwd) {
		List<BoardVo> result = new ArrayList<>();

		ResultSet rs = null;
		int postsPerPage = 5;
		int offset = (page - 1) * postsPerPage;

		try (Connection conn = getConnection();

				PreparedStatement pstmt1 = conn.prepareStatement(
						"select b.no, b.title, b.contents, b.hit, b.reg_date, b.g_no, b.o_no, b.depth, b.user_no, u.name as userName "
								+ "from board b, user u " + "where b.user_no = u.no "
								+ "order by b.g_no desc, b.o_no asc " + "limit ?, ?");
				PreparedStatement pstmt2 = conn.prepareStatement(
						"select b.no, b.title, b.contents, b.hit, b.reg_date, b.g_no, b.o_no, b.depth, b.user_no, u.name as userName " +
					             "from board b, user u " +
					             "where b.user_no = u.no " +
					             "and (b.title like ? or b.contents like ?) " +
					             "order by b.g_no desc, b.o_no asc " +
					             "limit ?, ?");) {
			if(kwd==null) {
				pstmt1.setInt(1, offset);
				pstmt1.setInt(2, postsPerPage);
				// 5. SQL 실행
				rs = pstmt1.executeQuery();
			}
			else {
				pstmt2.setString(1, "%" + kwd + "%");
				pstmt2.setString(2, "%" + kwd + "%");
				pstmt2.setInt(3, offset);
				pstmt2.setInt(4, postsPerPage);
				// 5. SQL 실행
				rs = pstmt2.executeQuery();
			}

			// 6. 결과 처리
			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String reg_date = rs.getString(5);
				Long g_no = rs.getLong(6);
				Long o_no = rs.getLong(7);
				Long depth = rs.getLong(8);
				Long user_no = rs.getLong(9);
				String user_name = rs.getString(10);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(reg_date);
				vo.setGroupNo(g_no);
				vo.setOrderNo(o_no);
				vo.setDepth(depth);
				vo.setUserNo(user_no);
				vo.setUserName(user_name);

				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return result;
	}

	// 게시글 전체 숫자 반환 함수
	public int getTotalPosts(String kwd) {
		
		
		int total = 0;
		
		ResultSet rs=null;
		

		try (Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement(
						"select count(*) from board");
				PreparedStatement pstmt2 = conn.prepareStatement(
						"select count(*) from board where title like ? or contents like ?");
				) {
			if(kwd==null) {
				rs=pstmt1.executeQuery();
			}else {
				pstmt2.setString(1, "%" + kwd + "%");
				pstmt2.setString(2, "%" + kwd + "%");
				rs=pstmt2.executeQuery();
			}
			 
			if (rs.next()) {
				total = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return total;
	}

	// 게시물 보기?
	public BoardVo findByBoardNo(Long no) {
		ResultSet rs = null;
		BoardVo vo = new BoardVo();
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"select * from board where no=?");) {
			pstmt.setLong(1, no);
			

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Long no1 = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String reg_date = rs.getString(5);
				Long g_no = rs.getLong(6);
				Long o_no = rs.getLong(7);
				Long depth = rs.getLong(8);
				Long user_no = rs.getLong(9);
				

				
				vo.setNo(no1);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(reg_date);
				vo.setGroupNo(g_no);
				vo.setOrderNo(o_no);
				vo.setDepth(depth);
				vo.setUserNo(user_no);
				

				
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vo;
	}

	// 게시물 수정(update)
	public void updateWithTitleAndContentsByNo(String title, String contents, Long no) {
		try (Connection conn = getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(
		                 "update board set title = ?, contents = ? where no = ?");) {
		        
		        pstmt.setString(1, title);
		        pstmt.setString(2, contents);
		        pstmt.setLong(3, no);

		        pstmt.executeUpdate();

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		
	}
	
	
	
	public Map<String, Long> findGroupAndOrderNoAndDepthByNo(Long no) {
		
		 Map<String, Long> groupNoAndOrderNoAndDepth = new HashMap<>();
	        
	        try (Connection conn = getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(
	                     "SELECT g_no, o_no,depth FROM board WHERE no = ?")) {
	            pstmt.setLong(1, no);

	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                	groupNoAndOrderNoAndDepth.put("g_no", rs.getLong("g_no"));
	                	groupNoAndOrderNoAndDepth.put("o_no", rs.getLong("o_no"));
	                	groupNoAndOrderNoAndDepth.put("depth", rs.getLong("depth"));
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        return groupNoAndOrderNoAndDepth;
	}

	public void reply(BoardVo boardVo, BoardVo parentboardVo) {
		try (Connection conn = getConnection();
		         PreparedStatement pstmt1 = conn.prepareStatement(
		                 "update board set o_no = o_no+1 where g_no = ? and o_no >=?");
				PreparedStatement pstmt2 = conn.prepareStatement(
						 "insert into board (title, contents, hit, reg_date, g_no, o_no, depth, user_no) "
		                         + "values (?, ?, 0, now(), ?, ?, ?, ?)");) {
		        
		        pstmt1.setLong(1, parentboardVo.getGroupNo());
		        pstmt1.setLong(2, parentboardVo.getOrderNo());
		        

		        pstmt1.executeUpdate();
		        
		        pstmt2.setString(1, boardVo.getTitle());
				pstmt2.setString(2, boardVo.getContents());
				pstmt2.setLong(3, parentboardVo.getGroupNo());
				pstmt2.setLong(4, parentboardVo.getOrderNo());
				pstmt2.setLong(5, parentboardVo.getDepth());
				pstmt2.setLong(6, boardVo.getUserNo());

				pstmt2.executeUpdate();

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		
		
		
	}

	public void deleteByNo(Long no) {
	    try (Connection conn = getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(
	                 "DELETE FROM board WHERE no = ?");) {
	        
	        
	        pstmt.setLong(1, no);

	        
	        pstmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	// 조회수 증가 함수
	public void increaseHit(Long no) {
	    try (Connection conn = getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(
	                 "update board set hit = hit + 1 where no = ?");) {
	        
	        pstmt.setLong(1, no);
	        pstmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	

	

	

	
}
