package com.example.demo.src.address;

import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
