package com.study.board.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.study.board.domain.ReplyVO;

@Repository	// 해당 클래스를 루트 컨테이너에 빈(Bean) 객체로 생성해줌 (퍼시스턴스 레이어, DB 나 파일같은 외부 I/O 작업을 처리함)
public class ReplyDAOImpl implements ReplyDAO {
	
	@Inject
	private SqlSession sql;

	private static String namespace = "com.study.mappers.reply";

	// 댓글 조회
	@Override
	public List<ReplyVO> list(int bno) throws Exception {
	    return sql.selectList(namespace + ".replyList", bno);
	}

	// 댓글 작성
	@Override
	public void write(ReplyVO vo) throws Exception {
	    sql.insert(namespace + ".replyWrite", vo);
	}

	// 댓글 수정
	@Override
	public void modify(ReplyVO vo) throws Exception {
	    sql.update(namespace + ".replyModify", vo);
	}

	// 댓글 삭제
	@Override
	public void delete(ReplyVO vo) throws Exception {
	    sql.delete(namespace + ".replyDelete", vo);
	}
	
}
