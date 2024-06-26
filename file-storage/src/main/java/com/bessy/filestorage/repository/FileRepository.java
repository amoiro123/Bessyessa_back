package com.bessy.filestorage.repository;

import com.bessy.filestorage.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, String> {
}
