package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.src.address.model.PostAddressReq;
import com.example.demo.src.address.model.PostAddressRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class AddressService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AddressDao addressDao;

    private final JwtService jwtService;

    @Autowired
    public AddressService(AddressDao addressDao, JwtService jwtService){
        this.addressDao = addressDao;
        this.jwtService = jwtService;
    }


    public PostAddressRes createAddress(PostAddressReq postAddressReq) throws BaseException {

        try {

            int addressId = addressDao.createAddress(postAddressReq);

            PostAddressRes postAddressRes = addressDao.getAddressByAddressId(addressId);

            return postAddressRes;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostAddressRes modifyAddress(PostAddressReq postAddressReq) throws BaseException {

        try{
            int result = addressDao.modifyAddress(postAddressReq);

            if(result == 0){
                throw new BaseException(MODIFY_FAIL_RESTAURANT);
            }

            PostAddressRes postAddressRes = addressDao.getAddressByAddressId(postAddressReq.getAddressId());

            return postAddressRes;

        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteAddress(int AddressId) throws BaseException{
        try{

            int result = addressDao.deleteAddress(AddressId);

            if(result == 0){
                throw new BaseException(FAILED_TO_MODIFY);
            }

        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }



}
