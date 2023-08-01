package com.dish.domain.like;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LikeRequest {
	private long id;
	private long postId;
	private long memberId;
	private int likeYn;

}
