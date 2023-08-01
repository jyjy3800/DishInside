package com.dish.domain.post;

import org.apache.ibatis.annotations.Mapper;

import com.dish.common.dto.SearchDto;

import java.util.List;

@Mapper
public interface PostMapper {

    /**
     * 게시글 저장
     *
     * @param params - 게시글 정보
     */
    void save(PostRequest params);

    /**
     * 게시글 조회수 증가
     *
     * @param id - PK
     */   
    void increaseViewCount(Long id);
    
    
    
    /**
     * 게시글 상세정보 조회
     *
     * @param id - PK
     * @return 게시글 상세정보
     */    
    PostResponse findById(Long id);
    
   

    /**
     * 게시글 수정
     *
     * @param params - 게시글 정보
     */
    void update(PostRequest params);

    /**
     * 게시글 삭제
     *
     * @param id - PK
     */
    void deleteById(Long id);

    /**
     * 게시글 리스트 조회
     *
     * @return 게시글 리스트
     */
    List<PostResponse> findAll(SearchDto params);

    /**
     * 게시글 공지 조회
     *
     * @param id - PK
     * @return 게시글 상세정보
     */    
    List<PostResponse> findNotice(SearchDto params);
    
    /**
     * 게시글 조회수 Best20
     *
     * @param id - PK
     * @return 게시글 상세정보
     */    
    List<PostResponse> findBest(SearchDto params);
    
    
    /**
     * 게시글 좋아요수 Best20
     *
     * @param id - PK
     * @return 게시글 상세정보
     */    
    List<PostResponse> findBestLike(SearchDto params);
    
    /**
     * 좋아요 누른 것만 보기
     *
     * @param id - PK
     * @return 게시글 상세정보
     */    
    List<PostResponse> findLike(SearchDto params);
    
    /**
     * 내가 쓴 것만 보기
     *
     * @param id - PK
     * @return 게시글 상세정보
     */    
    List<PostResponse> findMine(SearchDto params);
    
    
    /**
     * 공지 수 카운팅
     *
     * @return 게시글 수
     */
    int noticeCount(SearchDto params);
    
    /**
     * 게시글 수 카운팅
     *
     * @return 게시글 수
     */
    int postCount(SearchDto params);
    
    /**
     * 좋아요 누른 게시글 수 카운팅
     *
     * @return 게시글 수
     */
    int likeCount(SearchDto params);
    
    /**
     * 좋아요 누른 게시글 수 카운팅
     *
     * @return 게시글 수
     */
    int mineCount(SearchDto params);
    

}
