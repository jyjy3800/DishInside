package com.dish.domain.attendance;



import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AttendanceDto {

	 private long id;                			// 출첵 번호 (PK)
	 private long memberId;            			// 멤버 번호 (FK)	   
	 private LocalDateTime attendanceDate;       // 날짜
	 private int month;
	 private int year;
	 private int day;	 
}
