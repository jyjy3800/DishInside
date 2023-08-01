package com.dish.domain.attendance;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttendanceMapper {
	
	/**
     * 게시글 저장
     *
     * @param params - 출석 정보
     */
	Long check(AttendanceDto params);
	
	/**
     * 이번달 출석 정보 불러오기
     *
     * @param params - 출석 정보
     * @return 이번달 출석 일자
     **/
	List<Integer> monthCheck(AttendanceDto params);
	
	/**
	 * 오늘 출석했는지 확인
	 *
	 * @return 출석 유무 (1: 출석, 0: 미출석)
	 **/
	int beforeCheck(AttendanceDto params);

}
