
package com.dish.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.dish.common.dto.MessageDto;
import com.dish.common.dto.SearchDto;
import com.dish.common.file.FileUtils;
import com.dish.common.paging.PagingResponse;
import com.dish.domain.file.FileRequest;
import com.dish.domain.file.FileResponse;
import com.dish.domain.file.FileService;
import com.dish.domain.member.MemberResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	private final FileService fileService;
	private final FileUtils fileUtils;

// 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
	private String showMessageAndRedirect(final MessageDto params, Model model) {
		model.addAttribute("params", params);
		return "common/messageRedirect";
	}

// 쿼리 스트링 파라미터를 Map에 담아 반환
	private Map<String, Object> queryParamsToMap(final SearchDto queryParams) {
		Map<String, Object> data = new HashMap<>();
		data.put("page", queryParams.getPage());
		data.put("recordSize", queryParams.getRecordSize());
		data.put("pageSize", queryParams.getPageSize());
		data.put("keyword", queryParams.getKeyword());
		data.put("searchType", queryParams.getSearchType());
		return data;
	}

// 게시글 작성 페이지
	@GetMapping("/post/write.do")
	public String openPostWrite(@RequestParam(value = "id", required = false) final Long id, Model model) {
		if (id != null) {
			PostResponse post = postService.findPostById(id);
			model.addAttribute("post", post);
		}
		return "post/write";
	}

// 공지 페이지
	@GetMapping("/post/notice.do")
	public String openNoticeList(@ModelAttribute("params") final SearchDto params, Model model) {
		PagingResponse<PostResponse> response = postService.findNotice(params);
		model.addAttribute("response", response);
		return "post/notice";
	}

// 메인 페이지
	@GetMapping("/post/main.do")
	public String openPostList(@ModelAttribute("params") final SearchDto params, Model model) {
		PagingResponse<PostResponse> response = postService.findAllPost(params);
		model.addAttribute("response", response);
		return "post/list";
	}

// 베스트 20 페이지
	@GetMapping("/post/bestview.do")
	public String openBestList(@ModelAttribute("params") final SearchDto params, Model model) {
		PagingResponse<PostResponse> response = postService.findBest(params);

		model.addAttribute("response", response);
		model.addAttribute("title", "조회수 BEST 20");
		model.addAttribute("sub", "좋아요 BEST 20");
		model.addAttribute("change", "bestlike");
		model.addAttribute("this", "best");

		return "post/list";
	}

// 베스트 20 페이지
	@GetMapping("/post/bestlike.do")
	public String openBestLike(@ModelAttribute("params") final SearchDto params, Model model) {
		PagingResponse<PostResponse> response = postService.findBestLIke(params);
		model.addAttribute("response", response);
		model.addAttribute("title", "좋아요 BEST 20");
		model.addAttribute("sub", "조회수 BEST 20");
		model.addAttribute("change", "bestview");
		model.addAttribute("this", "best");

		return "post/list";
	}

// 본인이 올린 페이지
	@GetMapping("/post/mypost.do")
	public String openLikeList(HttpServletRequest request, @ModelAttribute("params") final SearchDto params,
			Model model) {
		HttpSession session = request.getSession();
		MemberResponse loginMember = (MemberResponse) session.getAttribute("loginMember");
		Long memberId = loginMember.getId();
		params.setMemberId(memberId);
		PagingResponse<PostResponse> response = postService.findMyPost(params, memberId);
		model.addAttribute("response", response);
		return "post/list";
	}

// 좋아요 페이지
	@GetMapping("/post/like.do")
	public String openMyList(HttpServletRequest request, @ModelAttribute("params") final SearchDto params,
			Model model) {
		HttpSession session = request.getSession();
		MemberResponse loginMember = (MemberResponse) session.getAttribute("loginMember");
		Long memberId = loginMember.getId();

		PagingResponse<PostResponse> response = postService.findLikePost(params, memberId);
		model.addAttribute("response", response);
		return "post/list";

	}

// 게시글 상세 페이지
	@GetMapping("/post/view.do")
	public String openPostView(@RequestParam final Long id, Model model) {
		PostResponse post = postService.findPostById(id);
		model.addAttribute("post", post);
		return "post/view";
	}

// 신규 게시글 생성
	@PostMapping("/post/save.do")
	public String savePost(final PostRequest params, Model model) {
		Long id = postService.savePost(params);
		List<FileRequest> files = fileUtils.uploadFiles(params.getFiles());
		fileService.saveFiles(id, files);
		MessageDto message = new MessageDto("게시글 생성이 완료되었습니다.", "/post/main.do", RequestMethod.GET, null);
		return showMessageAndRedirect(message, model);
	}

// 기존 게시글 수정
	@PostMapping("/post/update.do")
	public String updatePost(final PostRequest params, final SearchDto queryParams, HttpServletRequest request,
			Model model) {

		HttpSession session = request.getSession();
		MemberResponse loginMember = (MemberResponse) session.getAttribute("loginMember");
		String memberId = loginMember.getName();
		int role = loginMember.getRole();
		if (memberId.equals(params.getWriter()) || role == 1) {
			// 1. 게시글 정보 수정
			postService.updatePost(params);

			// 2. 파일 업로드 (to disk)
			List<FileRequest> uploadFiles = fileUtils.uploadFiles(params.getFiles());

			// 3. 파일 정보 저장 (to database)
			fileService.saveFiles(params.getId(), uploadFiles);

			// 4. 삭제할 파일 정보 조회 (from database)
			List<FileResponse> deleteFiles = fileService.findAllFileByIds(params.getRemoveFileIds());

			// 5. 파일 삭제 (from disk)
			fileUtils.deleteFiles(deleteFiles);

			// 6. 파일 삭제 (from database)
			fileService.deleteAllFileByIds(params.getRemoveFileIds());

			MessageDto message = new MessageDto("게시글 수정이 완료되었습니다.", "/post/main.do", RequestMethod.GET,
					queryParamsToMap(queryParams));
			return showMessageAndRedirect(message, model);
		} else {
			MessageDto message = new MessageDto("권한이 없습니다.", "/post/main.do", RequestMethod.GET,
					queryParamsToMap(queryParams));
			return showMessageAndRedirect(message, model);
		}
	}

// 게시글 삭제
	@PostMapping("/post/delete.do")
	public String deletePost(@RequestParam("id") Long postId, 
			@RequestParam("writer") String postWriter,
			final SearchDto queryParams, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		MemberResponse loginMember = (MemberResponse) session.getAttribute("loginMember");
		String memberId = loginMember.getLoginId();
		System.out.println(postWriter);
		int role = loginMember.getRole();
		if (memberId.equals(postWriter) || role == 1) {
			postService.deletePost(postId);
			MessageDto message = new MessageDto("게시글 삭제가 완료되었습니다.", "/post/main.do", RequestMethod.GET,
					queryParamsToMap(queryParams));
			return showMessageAndRedirect(message, model);
		} else {
			MessageDto message = new MessageDto("권한이 없습니다.", "/post/main.do", RequestMethod.GET,
					queryParamsToMap(queryParams));
			return showMessageAndRedirect(message, model);
		}
	}

}