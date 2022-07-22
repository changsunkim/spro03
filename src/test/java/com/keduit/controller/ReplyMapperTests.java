package com.keduit.controller;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.keduit.domain.Criteria;
import com.keduit.domain.ReplyVO;
import com.keduit.mapper.ReplyMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {
	
	private Long[] bnoArr = {1L, 2L, 3L, 4L, 5L};
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	@Test
	public void testMapper() {
		log.info(mapper);
	}
	
	@Test
	public void testCreate() {
		
		IntStream.rangeClosed(1, 10).forEach(i -> {
			
			ReplyVO vo = new ReplyVO();
			
			vo.setBno(bnoArr[i % 5]);
			vo.setReply("댓글 테스트입니다." + i);
			vo.setReplyer("replyer"+i);
			
			mapper.insert(vo);
		
		});
	}
	
	@Test
	public void testRead() {
		Long rno =70L;
		
		ReplyVO vo = mapper.read(rno);
		log.info(vo);
		
	}
	
	@Test
	public void testDelete() {
		Long rno = 3L;
		
		int result = mapper.delete(rno);
		log.info("삭제결과 : " + result );
	}
	
	@Test
	public void testUpdate() {
		Long rno = 70L;
		ReplyVO vo = mapper.read(rno);
		
		vo.setReply("Update 댓글 테스트");
		int result = mapper.update(vo);
		log.info("update 결과 : " + result);
		vo = mapper.read(rno);
		log.info("update 후의 reply" + vo);
 	}
	
	@Test
	public void testList() {
		Criteria cri = new Criteria();
		
		List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[2]);
		
		replies.forEach(reply-> log.info(reply));
	}
	
	@Test
	public void testList2() {
		Criteria cri = new Criteria();
		List<ReplyVO> replies = mapper.getListWithPaging(cri, 4L);
		
		replies.forEach(reply-> log.info(reply));
	}
}
