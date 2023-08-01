package com.dish.domain.member;
import java.util.ArrayList;
import java.util.List;

public class MemberInLogIn {
	private static List<Long> loggedInUsers = new ArrayList<>();

    // 이미 로그인한 사용자인지 확인
    public static boolean isAlreadyLoggedIn(Long memberId) {
        return loggedInUsers.contains(memberId);
    }

    // 새로운 로그인 사용자 추가
    public static void addLoggedInUser(Long memberId) {
        loggedInUsers.add(memberId);
    }

    // 로그아웃 처리
    public static void logout(Long memberId) {
        loggedInUsers.remove(memberId);
    }
}