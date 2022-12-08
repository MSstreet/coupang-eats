package com.example.demo.src.Board;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.Board.model.PostBoardReq;
import com.example.demo.src.Board.model.PostBoardRes;
import com.example.demo.src.menu.model.PostMenuRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.config.BaseResponseStatus.POST_MENU_INVALID_PRICE;

@RestController
@RequestMapping("/app/boards")
public class BoardController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final BoardService boardService;
    @Autowired
    private final BoardProvider boardProvider;
    @Autowired
    private final JwtService jwtService;

    public BoardController(BoardService boardService,BoardProvider boardProvider,JwtService jwtService){
        this.boardService = boardService;
        this.boardProvider = boardProvider;
        this.jwtService = jwtService;
    }

    //게시판을 유저가 생성하는게 아니니까 jwt X
    @ResponseBody
    @PostMapping("/join")
    public BaseResponse<PostBoardRes> creatBoard(@RequestBody PostBoardReq postBoardReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!

        try{

            if(postBoardReq.getTitle() == null || postBoardReq.getTitle().length() == 0) {
                return new BaseResponse<>(POST_BOARD_EMPTY_BOARD_TITLE);
            }

            if(postBoardReq.getContent() == null || postBoardReq.getContent().length() == 0) {
                return new BaseResponse<>(POST_BOARD_EMPTY_BOARD_CONTENT);
            }

            PostBoardRes postBoardRes = boardService.createBoard(postBoardReq);

            return new BaseResponse<>(postBoardRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/modify/{boardIdx}")
    public BaseResponse<PostBoardRes> modifyBoard(@PathVariable("boardIdx") int boardIdx, @RequestBody PostBoardReq postBoardReq){

        try{

            if(postBoardReq.getTitle() == null || postBoardReq.getTitle().length() == 0) {
                return new BaseResponse<>(POST_BOARD_EMPTY_BOARD_TITLE);
            }

            if(postBoardReq.getContent() == null || postBoardReq.getContent().length() == 0) {
                return new BaseResponse<>(POST_BOARD_EMPTY_BOARD_CONTENT);
            }

            PostBoardRes postBoardRes = boardService.modifyBoard(postBoardReq,boardIdx);

            return new BaseResponse<>(postBoardRes);

        }catch(BaseException exception) {

            return new BaseResponse<>((exception.getStatus()));

        }
    }

    @ResponseBody
    @PatchMapping("/delete/{boardIdx}")
    public BaseResponse<Integer> deleteBoard(@PathVariable("boardIdx") int boardIdx){

        try {
            int result = boardService.deleteBoard(boardIdx);

            if(result == 0){
                return new BaseResponse<>(POST_BOARD_EMPTY_BOARD);
            }

            return new BaseResponse<>(boardIdx);
        }catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @GetMapping("/list") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<PostBoardRes>> getAllBoards(){

        try{
            List<PostBoardRes> postBoardRes = boardProvider.getAllBoards();

            if(postBoardRes.size() == 0){
                return new BaseResponse<>(POST_BOARD_EMPTY_BOARDS);
            }

            return new BaseResponse<>(postBoardRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @GetMapping("/{boardIdx}") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<PostBoardRes> getBoardByBoardId(@PathVariable("boardIdx") int boardIdx){

        try{
            PostBoardRes postBoardRes = boardProvider.getBoardByBoardId(boardIdx);

            if(postBoardRes == null){
                return new BaseResponse<>(POST_BOARD_EMPTY_BOARDS);
            }

            return new BaseResponse<>(postBoardRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

}
