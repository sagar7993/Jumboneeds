package com.jumboneeds.services;

import com.jumboneeds.entities.PartnerDetail;
import com.jumboneeds.repositories.PartnerDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartnerDetailService {

    @Autowired
    private PartnerDetailRepository partnerDetailRepository;

    public void save(PartnerDetail partnerDetail) {
        partnerDetailRepository.save(partnerDetail);
    }

    public PartnerDetail findById(String partnerDetailId){
        return partnerDetailRepository.findById(partnerDetailId);
    }

    public PartnerDetail findByMobileNumber(String mobileNumber) {
        return partnerDetailRepository.findByMobileNumber(mobileNumber);
    }

}