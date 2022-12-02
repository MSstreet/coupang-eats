package com.example.demo.src.review.model;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReviewReq {
    private Integer orderIdx;
    private Integer score;
    private String content;
    private boolean menuPublicYn;
    private boolean menuRecommendYn;
    private String ownerReply;
    private String reviewImagePath;
}