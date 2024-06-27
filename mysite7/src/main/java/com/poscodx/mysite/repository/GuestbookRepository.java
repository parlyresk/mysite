package com.poscodx.mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	private SqlSession sqlSession;

	public GuestbookRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}


	public void insert(GuestbookVo vo) {

		sqlSession.insert("guestbook.insert", vo);

	}

	public void deleteByNoAndPassword(Long no, String password) {
		sqlSession.delete("guestbook.deleteByNoAndPassword", Map.of("no", no, "password", password));

	}

	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
	}
}