package com.moyur.profile.imageupload;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadRepository extends JpaRepository<UploadEntity, Long> {
	Optional<UploadEntity> findByUserid_Username(String username);
	List<UploadEntity> findAllByUserid_Username(String username);
}