package com.chessd.chess.storage.service;

import com.chessd.chess.user.entity.User;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {
    void init();

    String store(MultipartFile file, User user);

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteUserFile(User user);

    void deleteAll();
}
