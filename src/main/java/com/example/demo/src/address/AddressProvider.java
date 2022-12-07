package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.src.address.model.PostAddressRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class AddressProvider {

    private final AddressDao addressDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AddressProvider(AddressDao addressDao, JwtService jwtService) {
        this.addressDao = addressDao;
        this.jwtService = jwtService;
    }

    public List<PostAddressRes> getAllAddress() throws BaseException {

        try{
            List<PostAddressRes> postAddressRes = addressDao.getAllAddress();

            return postAddressRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<PostAddressRes> getAddressByKeyword(String keyword) throws BaseException {

        try{
            List<PostAddressRes> postAddressRes = addressDao.getAddressByKeyword(keyword);

            return postAddressRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<PostAddressRes> getAddressByUserIdx(int userIdx) throws BaseException {

        try{
            List<PostAddressRes> postAddressRes = addressDao.getAddressByUserIdx(userIdx);

            return postAddressRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
