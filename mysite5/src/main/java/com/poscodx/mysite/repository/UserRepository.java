package com.poscodx.mysite.repository;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.security.UserDetailsImpl;
import com.poscodx.mysite.vo.UserVo;

@Repository
public class UserRepository {
	private SqlSession sqlSession;
	
	public UserRepository(SqlSession sqlSession) {
		this.sqlSession=sqlSession;
	}
	

	public void insert(UserVo vo) {
		sqlSession.insert("user.insert",vo);
		
	}

	public UserVo findByEmailAndPassword(String email, String password) {
		return sqlSession.selectOne("user.findByEmailAndPassword",Map.of("email",email,"password",password));
	}

	public UserVo findByNo(Long no) {
		return sqlSession.selectOne("user.findByNo",no);
	}
	
	public UserVo findByEmail(String email) {
		
		return sqlSession.selectOne("user.findByEmail",email);
	}
	
public UserDetailsImpl findByEmail2(String email) {
		
	return sqlSession.selectOne("user.findByEmail2",email);
	}

	public void update(UserVo vo) {
		sqlSession.update("user.update",vo);
		
		
	}


	


	
	
	
}
