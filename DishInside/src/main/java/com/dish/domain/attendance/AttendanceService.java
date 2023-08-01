package com.dish.domain.attendance;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceService {
	
	private final AttendanceMapper attendancemapper;
	
	@Transactional
	public Long check(final AttendanceDto params) {
		int beforeCheck = beforeCheck(params);
		if(beforeCheck!=0) {
			return params.getId();
		}
		return attendancemapper.check(params);
	}
	
	@Transactional
	public List<Integer> monthCheck(final AttendanceDto params) {
        return attendancemapper.monthCheck(params);
    }
	
	@Transactional
	public int beforeCheck(final AttendanceDto params) {		 
		return attendancemapper.beforeCheck(params);
	}
}
