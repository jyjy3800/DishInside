package com.dish.domain.like;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LikeApiController {
	
	private final LIkeService likeService;

	@PostMapping("/posts/{postId}/like")	
	public Long saveLike(@RequestBody final LikeRequest params) {
		
		return likeService.like(params);		
	}
	
	@PostMapping("/posts/{postId}/unlike")	
	public Long cancelLike(@RequestBody final LikeRequest params) {
		return likeService.cancel(params);
	
	}
	
	@GetMapping("/posts/{postId}/likeCount")	
	public int countLike(@PathVariable final Long postId) {		
	    return likeService.likeCount(postId);
	}
	
	@PostMapping("/posts/{postId}/checklike")	
	public int checklLike(@RequestBody final LikeRequest params) {
		return likeService.likeCheck(params);		
	}
	
	
}
