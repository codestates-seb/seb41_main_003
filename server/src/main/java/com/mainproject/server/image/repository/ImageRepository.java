package com.mainproject.server.image.repository;

import com.mainproject.server.image.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ProfileImage, Long> {
}
