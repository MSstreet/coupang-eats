package com.example.demo.src.Board.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostBoardRes {

    // 보드ID
    private int boardId;

    // 제목
    private String title;

    // 내용
    private String content;

    // 삭제 여부 0:비활성, 1:활성
    private Boolean deleteYn;
}
