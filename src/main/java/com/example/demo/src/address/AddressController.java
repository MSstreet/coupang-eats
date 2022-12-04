package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;

import com.example.demo.src.address.model.PostAddressReq;
import com.example.demo.src.address.model.PostAddressRes;
import com.example.demo.src.restaurant.model.GetRestaurantRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ResponseBody
    @PostMapping("/{userIdx}/join")
    public BaseResponse<PostAddressRes> creatAddress(@PathVariable("userIdx") int userIdx, @RequestBody PostAddressReq postAddressReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!

        try{

            PostAddressRes postAddressRes = addressService.createAddress(postAddressReq);

            return new BaseResponse<>(postAddressRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PutMapping("/modify/{userIdx}/{addressId}")
    public BaseResponse<PostAddressRes> modifyMenuOption(@PathVariable("userIdx") int userIdx, @PathVariable("addressId") int addressId, @RequestBody PostAddressReq postAddressReq){
        try{

            PostAddressRes postAddressRes = addressService.modifyAddress(postAddressReq);

            return new BaseResponse<>(postAddressRes);

        }catch(BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/delete/{userIdx}/{addressId}")
    public BaseResponse<Integer> deleteMenu(@PathVariable("userIdx") int userIdx, @PathVariable("restaurantIdx") int restaurantIdx, @PathVariable("menuIdx") int menuIdx){

        try {

            addressService.deleteAddress(userIdx);

            return new BaseResponse<>(menuIdx);
        }catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/list") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<PostAddressRes>> getAllAddress() {
        try{
            List<PostAddressRes> postAddressRes = addressProvider.getAllAddress();
            return new BaseResponse<>(postAddressRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/keyword") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<PostAddressRes>> getAddressByKeyword(@RequestParam String keyword) {
        try{

            List<PostAddressRes> postAddressRes = addressProvider.getAddressByKeyword(keyword);
            return new BaseResponse<>(postAddressRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
