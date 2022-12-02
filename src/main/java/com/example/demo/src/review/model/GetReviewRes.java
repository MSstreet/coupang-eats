package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetReviewRes {
    private int reviewIdx;
    private int orderIdx;
    private int userIdx;
    private int restIdx;
    private int score;
    private String content;
    private boolean menuPublicYn;
    private boolean menuRecommendYn;
    private String ownerReply;
    private boolean deleteYn;
    private String reviewImagePath;
}
