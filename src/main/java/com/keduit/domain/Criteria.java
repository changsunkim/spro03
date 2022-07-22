package com.keduit.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

@Data
public class Criteria {
	
	private int pageNum;
	private int amount;
	
	private String type; //  T, C, W, TC, TCW , CW  T-Title C-content W-writer
	private String keyword;
	
	
	public Criteria() {
		this(1,10);
	}

	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public String[] getTypeArr() {
		return type == null? new String[] {} : type.split(""); //split-하나씩 분리
	}
	
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", this.pageNum)
				.queryParam("amount", this.getAmount())
				.queryParam("type", this.getType()) //get으로도 그냥 type으로도 가져와도 상관없다
				.queryParam("keyword", this.keyword);
		
		return builder.toUriString();
	}
	
}
