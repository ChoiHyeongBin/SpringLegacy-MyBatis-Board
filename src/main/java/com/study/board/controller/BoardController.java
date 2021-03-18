package com.study.board.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.board.service.BoardService;
import com.study.board.domain.BoardVO;
import com.study.board.domain.Page;

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
		
		Page page = new Page();
		
		page.setNum(num);
		page.setCount(service.count());
		
		List<BoardVO> list = null;
		list = service.listPage(page.getDisplayPost(), page.getPostNum());
		
		model.addAttribute("list", list);
		/*
		model.addAttribute("pageNum", page.getPageNum());
		
		model.addAttribute("startPageNum", page.getStartPageNum());
		model.addAttribute("endPageNum", page.getEndPageNum());
		
		model.addAttribute("prev", page.isPrev());
		model.addAttribute("next", page.isNext());
		*/
		model.addAttribute("page", page);
		model.addAttribute("select", num);
		
		/*
		// 게시물 총 갯수
		int count = service.count();
		
		// 한 페이지에 출력할 게시물 갯수
		int postNum = 10;
		
		// 하단 페이징 번호 ([ 게시물 총 갯수 / 한 페이지에 출력할 갯수 ]의 올림)
		int pageNum = (int) Math.ceil((double)count/postNum);
		
		// 출력할 게시물
		int displayPost = (num - 1) * postNum;
		
		// 한번에 표시할 페이징 번호의 갯수
		int pageNum_cnt = 10;
		
		// 표시되는 페이지 번호 중 마지막 번호
		int endPageNum = (int) (Math.ceil((double)num / (double)pageNum_cnt) * pageNum_cnt);	
		
		<페이징 원리>
		현재 페이지 번호가 8번이라면, 한번에 표시할 페이지 번호의 갯수인 10으로 나눕니다. 8 / 10 = 0.8
		소수점을 올림처리(ceil)하면 0.8은 1이 됩니다.
		1을 한번에 표시할 페이지 번호의 갯수인 10을 곱하면 10이 됩니다.

		현재 페이지 번호가 41이라면, 한번에 표시할 페이지 번호의 갯수인 10으로 나눕니다. 41 / 10 = 4.1
		소수점을 올림처리하면 4.1은 5가 됩니다
		5를 한번에 표시할 페이지 번호의 갯수인 10을 곱하면 50이 됩니다.
		
		마지막 페이지 번호에서 한번에 표시할 번호의 갯수를 빼면,
		마지막 페이지 번호가 10일 경우 0
		마지막 페이지 번호가 50일 경우 40
		여기에 1을 더하면 각 페이지의 시작 번호가 됩니다.
		
		// 표시되는 페이지 번호 중 첫번째 번호
		int startPageNum = endPageNum - (pageNum_cnt - 1);
		
		//  마지막 번호 재계산
		int endPageNum_tmp = (int) (Math.ceil((double)count / (double)pageNum_cnt));
		
		if (endPageNum > endPageNum_tmp) {
			endPageNum = endPageNum_tmp;
		}
		
		boolean prev = startPageNum == 1 ? false : true;
		boolean next = endPageNum * pageNum_cnt >= count ? false : true;
		
		List<BoardVO> list = null;
		list = service.listPage(displayPost, postNum);
		model.addAttribute("list", list);
		model.addAttribute("pageNum", pageNum);
		
		// 시작 및 끝 번호
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		
		// 이전 및 다음
		model.addAttribute("prev", prev);
		model.addAttribute("next", next);
		
		// 현재 페이지
		model.addAttribute("select", num);
		*/
	}
	
	// 게시글 목록 + 페이징 + 검색
	@RequestMapping(value = "/listPageSearch", method = RequestMethod.GET)
	public void getListPageSearch(Model model, @RequestParam("num") int num, 
			@RequestParam(value = "searchType", required = false, defaultValue = "title") String searchType,	// value 는 받고자할 데이터의 키, required 는 해당 데이터의 필수여부, defaultValue 는 만약 데이터가 들어오지 않았을 경우 대신할 기본값
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) 
					throws Exception {	// Model 은 Controller 와 View 를 연결해주는 역할
		
		Page page = new Page();
		
		page.setNum(num);
		page.setCount(service.count());
		
		List<BoardVO> list = null;
		// list = service.listPage(page.getDisplayPost(), page.getPostNum());
		list = service.listPageSearch(page.getDisplayPost(), page.getPostNum(), searchType, keyword);
		
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		model.addAttribute("select", num);
	}
	
}
