package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;

import com.example.demo.src.address.model.PostAddressReq;
import com.example.demo.src.address.model.PostAddressRes;

import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/addresses")
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

    @ResponseBody
    @PostMapping("/{userIdx}/join")
    public BaseResponse<PostAddressRes> creatAddress(@PathVariable("userIdx") int userIdx, @RequestBody PostAddressReq postAddressReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!

        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

        try{

            if(postAddressReq.getAddressName() == null || postAddressReq.getAddressName().length() == 0) {
                return new BaseResponse<>(POST_MENU_EMPTY_GB_CODE);
            }

            PostAddressRes postAddressRes = addressService.createAddress(postAddressReq);

            return new BaseResponse<>(postAddressRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //상세 주소만 변경
    @ResponseBody
    @PatchMapping("/modify/{userIdx}/{addressIdx}")
    public BaseResponse<PostAddressRes> modifyAddress(@PathVariable("userIdx") int userIdx, @PathVariable("addressIdx") int addressId , @RequestBody PostAddressReq postAddressReq){

        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

        try{
            PostAddressRes postAddressRes = addressService.modifyAddress(postAddressReq);

            return new BaseResponse<>(postAddressRes);

        }catch(BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/delete/{userIdx}/{addressIdx}")
    public BaseResponse<Integer> deleteAddress(@PathVariable("userIdx") int userIdx, @PathVariable("addressIdx") int addressId){

        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

        try {

           int result =  addressService.deleteAddress(addressId);

            if(result == 0){
                return new BaseResponse<>(POST_ADDRESS_EMPTY_ADDRESS);
            }


            return new BaseResponse<>(addressId);
        }catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/list") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<PostAddressRes>> getAllAddress() {
        try{
            List<PostAddressRes> postAddressRes = addressProvider.getAllAddress();

            if(postAddressRes.size() == 0){
                return new BaseResponse<>(POST_ADDRESS_EMPTY_ADDRESS);
            }

            return new BaseResponse<>(postAddressRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/list/{userIdx}") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<PostAddressRes>> getAddressByUserIdx(@PathVariable("userIdx") int userIdx) {
        try{
            List<PostAddressRes> postAddressRes = addressProvider.getAddressByUserIdx(userIdx);

            if(postAddressRes.size() == 0){
                return new BaseResponse<>(POST_ADDRESS_EMPTY_ADDRESS);
            }
            return new BaseResponse<>(postAddressRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<PostAddressRes>> getAddressByKeyword(@RequestParam String keyword) {
        try{

            List<PostAddressRes> postAddressRes = addressProvider.getAddressByKeyword(keyword);

            if(postAddressRes.size() == 0){
                return new BaseResponse<>(POST_ADDRESS_EMPTY_ADDRESS);
            }
            return new BaseResponse<>(postAddressRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
