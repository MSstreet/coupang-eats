package com.example.demo.src.Board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Access;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostBoardReq {

    // 제목
    private String title;

    // 내용
    private String content;

    // 삭제 여부 0:비활성, 1:활성
    //private Boolean deleteYn;

}
