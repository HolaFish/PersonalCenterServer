package com.fxsh.file.server;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileOperationService {
    public String uploadAvatar(MultipartFile file);
}
