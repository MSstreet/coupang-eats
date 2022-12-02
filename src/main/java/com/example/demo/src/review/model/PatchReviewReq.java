package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchReviewReq {
    private int reviewIdx;
    private Integer score;
    private String content;
    private boolean menuPublicYn;
    private boolean menuRecommendYn;
    private String ownerReply;
    private String reviewImagePath;
}
