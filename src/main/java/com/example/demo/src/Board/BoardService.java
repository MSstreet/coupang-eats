package com.example.demo.src.Board;

import com.example.demo.config.BaseException;
import com.example.demo.src.Board.model.PostBoardReq;
import com.example.demo.src.Board.model.PostBoardRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.MODIFY_FAIL_RESTAURANT;

@Service
public class BoardService {


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BoardDao boardDao;

    private final JwtService jwtService;

    @Autowired
    public BoardService(BoardDao boardDao, JwtService jwtService){
        this.boardDao = boardDao;
        this.jwtService = jwtService;
    }

    public PostBoardRes createBoard(PostBoardReq postBoardReq) throws BaseException {

        try {

            int boardId = boardDao.createBoard(postBoardReq);

            PostBoardRes postBoardRes = boardDao.getBoardByBoardId(boardId);

            return postBoardRes;

            }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostBoardRes modifyBoard(PostBoardReq postBoardReq, int boardIdx) throws BaseException {

        try{
            int result = boardDao.modifyBoard(postBoardReq,boardIdx);

            System.out.println("=================================================================");
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_RESTAURANT);
            }

            PostBoardRes postBoardRes = boardDao.getBoardByBoardId(boardIdx);

            return postBoardRes;

        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int deleteBoard(int boardId) throws BaseException{
        try{

            int result = boardDao.deleteBoard(boardId);

            return result;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
