package com.dish.domain.like;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LIkeService {
	
	private final LikeMapper likemapper;
	
	
	@Transactional
	public Long like(final LikeRequest params) {
		int check = likeCheck(params);
		if(check ==0) {
			likemapper.like(params);
		}
		else {
			likemapper.updateLike(params);
		}
		return params.getId();
	}
	
	@Transactional
	public Long cancel(final LikeRequest params) {		
		likemapper.cancel(params);
		return params.getId();
	}
	
	@Transactional
	public int likeCount(final Long post_id) {		
		return likemapper.likeCount(post_id);
		
	}
	
	@Transactional
	public int likeCheck(final  LikeRequest params) {		
		return likemapper.likeCheck(params);
		
	}
	
	
}
