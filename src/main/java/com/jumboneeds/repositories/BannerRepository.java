package com.jumboneeds.repositories;

import com.jumboneeds.beans.BannerBean;
import com.jumboneeds.entities.Banner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BannerRepository extends CrudRepository<Banner, String> {

    @Transactional
    @Query(value = "SELECT new com.jumboneeds.beans.BannerBean(b.id, b.title, b.description, b.bannerImageUrl, b.promoUrl, b.bannerType.type) FROM Banner b WHERE b.active = true")
    List<BannerBean> fetchActiveBanners();

}