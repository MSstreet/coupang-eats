package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.address.model.PostAddressReq;
import com.example.demo.src.address.model.PostAddressRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/app/address")
public class AddressController {

    @Autowired
    private final AddressService addressService;

    @Autowired
    private final AddressProvider addressProvider;

    @Autowired
    private final AddressDao addressDao;
    @Autowired
    private final JwtService jwtService;

    public AddressController(AddressService addressService, AddressProvider addressProvider, AddressDao addressDao,JwtService jwtService){
        this.addressService = addressService;
        this.addressProvider = addressProvider;
        this.addressDao = addressDao;
        this.jwtService = jwtService;
    }

//    @ResponseBody
//    @PostMapping("/join")
//    public BaseResponse<PostAddressRes> creatAddressDetail(@RequestBody PostAddressReq postAddressReq) {
//        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
//
//        try{
//
//            PostAddressRes postAddressRes = addressService.createAddress(postAddressReq);
//
//            return new BaseResponse<>(postAddressRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }

}
