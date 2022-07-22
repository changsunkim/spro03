package com.keduit.domain;

import lombok.Data;

@Data
public class PageDTO {

	private int startPage;
	private int endPage;
	private boolean prev, next;
	
	private int total;
	private Criteria cri;
	
	public PageDTO(int total, Criteria cri) {
		this.total = total;
		this.cri = cri;
		
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0) * 10);
		this.startPage = this.endPage - 9;
		int realEnd = (int) (Math.ceil((total * 1.0)/cri.getAmount())); 
		//total 페이지를 실수로 바꾸기위해 total * 1.0 를 함
		if (realEnd < this.endPage) {
			this.endPage = realEnd;
			
		}
		//prev = true값과false 값을 가진다
		this.prev = this.startPage >1; //true 1보다 크면 옆에 prev 라는것이 생겨 초기로 넘어갈 수 있게
		this.next = this.endPage < realEnd; //같거나 크면 next표시가 생기지 않는다.
	}
	
}
