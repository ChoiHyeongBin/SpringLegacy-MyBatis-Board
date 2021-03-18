package com.study.board.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.board.service.BoardService;
import com.study.domain.BoardVO;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Inject	// @Aurowired와 동일하게 작동하지만 찾는 순서가 다르다 (주입하려고 하는 객체의 타입이 일치하는지를 찾고 객체를 자동으로 주입)
	BoardService service;
	
	// 게시글 목록
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void getList(Model model) throws Exception {	// Model 은 Controller 와 View 를 연결해주는 역할
		
		List<BoardVO> list = null;
		list = service.list();
		
		model.addAttribute("list", list);
	}
	
	// 게시글 작성
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void getWrite() throws Exception {
		
	}
	
	// 게시글 작성
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String postWrite(BoardVO vo) throws Exception {
		service.write(vo);
		
		return "redirect:/board/list";		// 모든 작업을 마치고 /board/list, 즉 게시물 목록 화면으로 이동하겠다는 의미
	}
	
	// 게시글 상세조회
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public void getView(@RequestParam("bno") int bno, Model model) throws Exception {	// @RequestParam([문자열]) -> 주소에 있는 특정한 값을 가져올 수 있음
		BoardVO vo = service.view(bno);
		model.addAttribute("view", vo);
	}
	
	// 게시글 수정
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void getModify(@RequestParam("bno") int bno, Model model) throws Exception {
		BoardVO vo = service.view(bno);
		model.addAttribute("view", vo);
	}
	
	// 게시글 수정
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String postModify(BoardVO vo) throws Exception {
		service.modify(vo);
		
		return "redirect:/board/view?bno=" + vo.getBno();
	}
	
	// 게시글 삭제
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String getDelete(@RequestParam("bno") int bno) throws Exception {
		service.delete(bno);
		
		return "redirect:/board/list";
	}
	
	// 게시글 목록 + 페이징
	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public void getListPage(Model model, @RequestParam("num") int num) throws Exception {	// Model 은 Controller 와 View 를 연결해주는 역할
		
		// 게시물 총 갯수
		int count = service.count();
		
		// 한 페이지에 출력할 게시물 갯수
		int postNum = 10;
		
		// 하단 페이징 번호 ([ 게시물 총 갯수 / 한 페이지에 출력할 갯수 ]의 올림)
		int pageNum = (int) Math.ceil((double)count/postNum);
		
		// 출력할 게시물
		int displayPost = (num - 1) * postNum;
		
		List<BoardVO> list = null;
		list = service.listPage(displayPost, postNum);
		model.addAttribute("list", list);
		model.addAttribute("pageNum", pageNum);
	}
	
}
