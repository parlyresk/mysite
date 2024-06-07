package com.poscodx.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;

	public void addContents(BoardVo vo) {

		
		boardRepository.insert(vo);
	}

	public BoardVo getContents(Long no) {
		BoardVo vo=boardRepository.findByBoardNo(no);
		if(vo!=null) {
			boardRepository.increaseHit(no);
		}
		return vo;
		
	}

	public BoardVo getContents(Long no, Long userNo) {
		BoardVo vo=boardRepository.findByNoAndUserNo(no,userNo);
		
		return vo;
	}

	public void updateContents(BoardVo vo) {
		boardRepository.updateWithTitleAndContentsByNo(vo.getTitle(), vo.getContents(), vo.getNo());
	}

	public void deleteContents(HttpSession session, Long boardNo, Long userNo) {
		
		boardRepository.deleteByNoAndUserNo(boardNo,userNo);
	}

	public Map<String, Object> getContentsList(int currentPage, String keyword) {
		
		int postsPerPage = 5;
        int totalPosts = boardRepository.getTotalPosts(keyword);
        int offset = (currentPage - 1) * postsPerPage;
        
        int totalPages = (int) Math.ceil((double) totalPosts / postsPerPage);
		int pageNavSize = 5;
	    int currentNavStart = ((currentPage - 1) / pageNavSize) * pageNavSize + 1;
	    int currentNavEnd = currentNavStart+4;
        
        
        List<BoardVo> list = boardRepository.findByPageAndKeyword(Map.of(
                "page", currentPage,
                "keyword", keyword,
                "offset", offset,
                "postsPerPage", postsPerPage
        ));
        
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("totalPosts", totalPosts);
        map.put("currentPage", currentPage);
        map.put("postsPerPage", postsPerPage);
        map.put("totalPages", totalPages);
        map.put("currentNavStart", currentNavStart);
        map.put("currentNavEnd", currentNavEnd);

        return map;
	}
}
