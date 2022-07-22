package com.keduit.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.keduit.domain.BoardVO;
import com.keduit.domain.Criteria;

public interface BoardMapper {

//   @Select("select * from t_board where bno > 0")
   public List<BoardVO> getList();
   
   
   public List<BoardVO> getListWithPaging(Criteria cri);
   
   
   public void insert(BoardVO board);
   
   
   public void insertSelectKey(BoardVO board);
   
  
   public BoardVO read(Long bno);
   
//   public void delete(Long bno);
   public int delete(Long bno);
   
   public int update(BoardVO board);
   
   public int getTotalCount(Criteria cri);
}
