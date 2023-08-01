package com.dish.domain.attendance;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;




@RestController
@RequiredArgsConstructor
public class AttendanceApi {
	
	
	private final AttendanceService attendanceservice;
	
	
	
	@PostMapping("/attendant/monthcheck")
	public List<Integer> monthCheck(@RequestBody final AttendanceDto params){				
		return attendanceservice.monthCheck(params);
	}
	
	@PostMapping("/attendant/check")
	public Long saveCheck(@RequestBody final AttendanceDto params){				
		return attendanceservice.check(params);
	}
	
	//test용
	@PostMapping("/attendant/beforecheck")
	public int beforeChek(@RequestBody final AttendanceDto params){				
		return attendanceservice.beforeCheck(params);
	}
}
