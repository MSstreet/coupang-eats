package com.example.demo.src.Board;

import com.example.demo.config.BaseException;
import com.example.demo.src.Board.model.PostBoardRes;
import com.example.demo.src.menu.model.PostMenuRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class BoardProvider {

    private final BoardDao boardDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public BoardProvider(BoardDao boardDao, JwtService jwtService) {
        this.boardDao = boardDao;
        this.jwtService = jwtService;
    }


    public List<PostBoardRes> getAllBoards() throws BaseException {

        try{
            List<PostBoardRes> postBoardRes = boardDao.getAllBoards();
            return postBoardRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostBoardRes getBoardByBoardId(int boardIdx) throws BaseException {

        try{
            PostBoardRes postBoardRes = boardDao.getBoardByBoardId(boardIdx);
            return postBoardRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
