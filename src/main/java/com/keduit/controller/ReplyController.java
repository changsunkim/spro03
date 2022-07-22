package com.keduit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.keduit.domain.Criteria;
import com.keduit.domain.ReplyPageDTO;
import com.keduit.domain.ReplyVO;
import com.keduit.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequestMapping("/replies/")
@RestController
@Log4j
@AllArgsConstructor//생성자 모두 읽음
public class ReplyController {

	
	private ReplyService service;

	@PostMapping(value="/new",
				consumes = "application/json",
				produces = {MediaType.TEXT_PLAIN_VALUE})
	
	public ResponseEntity<String> create(@RequestBody ReplyVO vo){
		
		log.info("ReplyVO : " + vo);
		
		int insertCount = service.register(vo);
		
		log.info("댓글 입력 count : " + insertCount);
		
		return insertCount == 1? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value="/pages/{bno}/{page}",//bno 쓰는 이유 : 게시글이 없으면 댓글도 없다
				produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
			)
	public ResponseEntity<ReplyPageDTO> getList(//getList = 댓글 목록
			@PathVariable("page") int page,
			@PathVariable("bno") Long bno){
		
		log.info("reply getList=======" + page + ", " +bno);
		Criteria cri = new Criteria(page, 10);
		log.info(cri);
		
		return new ResponseEntity<ReplyPageDTO>(
				service.getListPage(cri, bno), HttpStatus.OK);
	}
	
	
	@GetMapping(value="/{rno}",
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno){
		log.info("reply get : " + rno);
		return new ResponseEntity<ReplyVO>(service.get(rno), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{rno}",// deletemapping = 삭제를 위한 매핑
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno){
		
		log.info("reply remove======="+ rno);
		
		return service.remove(rno) == 1 ?
				new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	//put = 일부만 없데이트
	//patch = 일부만 업데이트
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH},
			value = "/{rno}",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo,
			@PathVariable("rno") Long rno){
		
		vo.setBno(rno);
		log.info("modify rno=========="+rno);
		log.info("modify vo=========="+vo);
		
		return service.modify(vo) == 1 ?
				new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
}
