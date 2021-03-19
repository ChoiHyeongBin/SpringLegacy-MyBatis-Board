package com.study.board.dao;

import java.util.List;

import com.study.board.domain.BoardVO;

public interface BoardDAO {

	// 게시글 목록
	List<BoardVO> list() throws Exception;
	
	// 게시글 작성
	void write(BoardVO vo) throws Exception;
	
	// 게시글 상세조회
	BoardVO view(int bno) throws Exception;
	
	// 게시글 수정
	void modify(BoardVO vo) throws Exception;
	
	// 게시글 삭제
	void delete(int bno) throws Exception;
	
	// 게시글 총 갯수
	int count() throws Exception;
	
	// 게시글 목록 + 페이징
	List<BoardVO> listPage(int displayPost, int postNum) throws Exception;
	
	// 게시글 목록 + 페이징 + 검색
	List<BoardVO> listPageSearch(int displayPost, int postNum, String searchType, String keyword) throws Exception;
	
	// 게시글 총 갯수 + 검색
	int searchCount(String searchType, String keyword) throws Exception;
	
}
