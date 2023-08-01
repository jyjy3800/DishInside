package com.dish.domain.attendance;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AttendanceController {
	@GetMapping("/attendant.do")
    private String openAttendance() {
        return "attendance/calender";
    }
}
