package com.dish.domain.comment;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dish.common.paging.PagingResponse;
import com.dish.domain.member.MemberResponse;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

	private final CommentService commentService;

	// 신규 댓글 생성
	@PostMapping("/posts/{postId}/comments")
	public CommentResponse saveComment(@PathVariable final Long postId, @RequestBody final CommentRequest params) {
		Long id = commentService.saveComment(params);
		return commentService.findCommentById(id);
	}

	// 댓글 리스트 조회
	@GetMapping("/posts/{postId}/comments")
	public PagingResponse<CommentResponse> findAllComment(@PathVariable final Long postId,
			final CommentSearchDto params) {
		return commentService.findAllComment(params);
	}

	// 댓글 상세정보 조회
	@GetMapping("/posts/{postId}/comments/{id}")
	public CommentResponse findCommentById(@PathVariable final Long postId, @PathVariable final Long id) {
		return commentService.findCommentById(id);
	}

	// 기존 댓글 수정
	@PatchMapping("/posts/{postId}/comments/{id}")
	public int updateComment(HttpServletRequest request,@PathVariable final Long id, @RequestBody final CommentRequest params) {
		HttpSession session = request.getSession();
		MemberResponse loginMember = (MemberResponse) session.getAttribute("loginMember");
		String memberId = loginMember.getLoginId();
		int role = loginMember.getRole();
		System.out.println("이거랑 " + params.getWriter());
		System.out.println("이거랑 " + memberId);
		if (params.getWriter().equals(memberId) || role == 1) {
			commentService.updateComment(params);
			return 1;
		} else
			return 0;	
	}

	// 댓글 삭제
	@DeleteMapping("/posts/{postId}/comments/{id}")
	public int deleteComment(HttpServletRequest request,@PathVariable final Long id, @RequestBody final CommentRequest params) {
		HttpSession session = request.getSession();
		MemberResponse loginMember = (MemberResponse) session.getAttribute("loginMember");
		String memberId = loginMember.getLoginId();
		int role = loginMember.getRole();		
		if (params.getWriter().equals(memberId) || role == 1) {
			commentService.deleteComment(id);
			return 1;
		} else
			return 0;

	}

}
