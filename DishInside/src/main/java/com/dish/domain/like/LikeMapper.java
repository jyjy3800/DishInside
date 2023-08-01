package com.dish.domain.like;

import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface LikeMapper {

	/**
     * 좋아요
     * @param params - 좋아요 정보
     */
    void like(LikeRequest params);
    
    /**
     * 좋아요다시 누르기
     * @param params - 좋아요 정보
     */
    void updateLike(LikeRequest params);
    
    
    /**
     * 좋아요
     * @param params - 취소 정보
     */
    void cancel(LikeRequest params);
    
    /**
     * 좋아요 개수 카운팅
     * 
     * @return 좋아요 개수 
     */
    int likeCount(Long post_id);
    
    /**
     * 좋아요 여부 확인
     * 
     * @return 좋아요 여부
     */
    int likeCheck(LikeRequest params);
    
  
    
    

}
