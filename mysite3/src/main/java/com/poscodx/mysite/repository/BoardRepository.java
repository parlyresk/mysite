package com.poscodx.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	private SqlSession sqlSession;

	public BoardRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	// 게시글 전체 숫자 반환 함수
	public int getTotalPosts(String keyword) {

		int total = sqlSession.selectOne("board.getTotalPosts", keyword);

		return total;
	}

	// 게시물 작성(insert) 함수
	public void insert(BoardVo boardVo) {
		
		
		sqlSession.insert("board.insert", boardVo);
		
		
		
		
		

	}
	public void reply(BoardVo boardVo) {
		sqlSession.update("board.replyUpdate", boardVo);
		sqlSession.insert("board.replyInsert", boardVo);
		
	}

	// 게시물 보기?
	public BoardVo findByBoardNo(Long no) {
		return sqlSession.selectOne("board.findByBoardNo", no);
	}

	public BoardVo findByNoAndUserNo(Long no, Long userNo) {
		Map<String, Long> map = new HashMap<String, Long>();
        map.put("no", no);
        map.put("userNo", userNo);

        return sqlSession.selectOne("board.findByNoAndUserNo", map);
	}

	// 게시물 수정(update)
	public void updateWithTitleAndContentsByNo(String title, String contents, Long no) {
		sqlSession.update("board.updateWithTitleAndContentsByNo",
				Map.of("title", title, "contents", contents, "no", no));

	}

	public Map<String, Long> findGroupAndOrderNoAndDepthByNo(Long no) {
		Map<String, Object> resultMap = sqlSession.selectOne("board.findGroupAndOrderNoAndDepthByNo", no);

		Map<String, Long> groupNoAndOrderNoAndDepth = new HashMap<>();
		if (resultMap != null) {
			groupNoAndOrderNoAndDepth.put("g_no", (Long) resultMap.get("g_no"));
			groupNoAndOrderNoAndDepth.put("o_no", (Long) resultMap.get("o_no"));
			groupNoAndOrderNoAndDepth.put("depth", (Long) resultMap.get("depth"));
		}

		return groupNoAndOrderNoAndDepth;
	}



	public void deleteByNoAndUserNo(Long no, Long userNo) {
		sqlSession.delete("board.deleteByNoAndUserNo", Map.of("no", no, "userNo", userNo));

	}

	// 조회수 증가 함수
	public void increaseHit(Long no) {
		sqlSession.update("board.increaseHit", no);

	}

	// 리스트 한 페이지에 정해진 수 만큼만 꺼내는 함수
	public List<BoardVo> findByPageAndKeyword(Map<String, Object> parameters) {

		int page = (int) parameters.get("page");
		String keyword = (String) parameters.get("keyword");
		int offset = (int) parameters.get("offset");
		int postsPerPage = (int) parameters.get("postsPerPage");

		return sqlSession.selectList("board.findByPageAndKeyword",
				Map.of("page", page, "keyword", keyword, "offset", offset, "postsPerPage", postsPerPage));

	}

	

}
