package com.jumboneeds.services;

import com.jumboneeds.beans.CouponUserBean;
import com.jumboneeds.repositories.CouponUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by akash.mercer on 24-Nov-16.
 */

@Service
public class CouponUserService {

    @Autowired
    private CouponUserRepository couponUserRepository;

    public List<CouponUserBean> findByBuilding(String buildingId) {
        return  couponUserRepository.findByBuilding(buildingId);
    }
}
