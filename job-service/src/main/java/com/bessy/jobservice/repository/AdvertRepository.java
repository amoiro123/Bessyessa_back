package com.bessy.jobservice.repository;

import com.bessy.jobservice.enums.Advertiser;
import com.bessy.jobservice.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert, String> {
    List<Advert> getAdvertsByUserIdAndAdvertiser(String id, Advertiser advertiser);
}
