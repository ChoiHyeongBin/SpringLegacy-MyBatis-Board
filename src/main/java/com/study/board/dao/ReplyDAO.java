package com.study.board.dao;

import java.util.List;

import com.study.board.domain.ReplyVO;

public interface ReplyDAO {

	// 댓글 조회
	List<ReplyVO> list(int bno) throws Exception;

	// 댓글 조회
	void write(ReplyVO vo) throws Exception;

	// 댓글 수정
	void modify(ReplyVO vo) throws Exception;

	// 댓글 삭제
	void delete(ReplyVO vo) throws Exception;
	
}
