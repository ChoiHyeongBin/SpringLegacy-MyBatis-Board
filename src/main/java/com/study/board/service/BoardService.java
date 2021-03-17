package com.study.board.service;

import java.util.List;

import com.study.domain.BoardVO;

public interface BoardService {

	// 게시글 목록
	List<BoardVO> list() throws Exception;
	
	// 게시글 작성
	void write(BoardVO vo) throws Exception;
	
	// 게시글 상세조회
	BoardVO view(int bno) throws Exception;
	
	// 게시글 수정
	void modify(BoardVO vo) throws Exception;
	
}
