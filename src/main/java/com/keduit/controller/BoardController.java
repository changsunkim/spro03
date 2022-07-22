package com.keduit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.keduit.domain.BoardVO;
import com.keduit.domain.Criteria;
import com.keduit.domain.PageDTO;
import com.keduit.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequiredArgsConstructor
@Log4j
@RequestMapping("/board/*")
public class BoardController {

	private final BoardService service;
	
//	@GetMapping("/list")
//	public void list(Model model) {
//		
//		log.info("list............");
//		model.addAttribute("list",service.getList());
//		
//	}

	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		
		log.info("list............" + cri);
		model.addAttribute("list",service.getList(cri));
		int total = service.getTotalCount(cri);
		log.info("total : " + total);
		
		model.addAttribute("pageMaker", new PageDTO(total,cri));
		
	}
	
	@GetMapping("/register")
	public void register() {
		
	}
	
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("register......" + board);
		
		Long bno = service.register(board);
		log.info("bno====> " + bno);
		
		rttr.addFlashAttribute("result", board.getBno());
		
		return "redirect:/board/list";
		
	}
	
//	@GetMapping("/get") 아래쪽에 여러개 연결에 get이 포함되어있으므로 생략
//	public void get(@RequestParam("bno") Long bno, Model model) {
//		log.info("/get......");
//		model.addAttribute("board", service.get(bno));
//		
//	}
	
	@GetMapping({"/get","/modify"}) //getmapping 여러개 연결하기 위해서
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		log.info("/get or modify.............");
		model.addAttribute("board", service.get(bno));
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO board,  @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		
		log.info("modify.........");
		int count  = service.modify(board);
		if (count == 1) {
			rttr.addFlashAttribute("result","수정 성공");	
		}
		
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("type",cri.getType());
//		rttr.addAttribute("keyword", cri.getKeyword());
		
		
		
		return "redirect:/board/list" + cri.getListLink();
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		
		log.info("remove" + bno);
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result","삭제 성공");
		}
		
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("type",cri.getType());
//		rttr.addAttribute("keyword", cri.getKeyword());
		
		
		return "redirect:/board/list" + cri.getListLink();
	}
}
