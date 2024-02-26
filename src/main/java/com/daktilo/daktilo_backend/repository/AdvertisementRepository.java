package com.daktilo.daktilo_backend.repository;

import com.daktilo.daktilo_backend.entity.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, UUID> {

    public Page<Advertisement> findAll(Pageable pageRequest);

    public Page<Advertisement> findAllByIsActive(Pageable pageRequest, boolean isActive);

    public Page<Advertisement> findAllByPlace(Pageable pageable, String location);
}
