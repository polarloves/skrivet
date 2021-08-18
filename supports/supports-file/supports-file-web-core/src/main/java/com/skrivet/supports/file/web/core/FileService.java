package com.skrivet.supports.file.web.core;

import java.io.InputStream;
import java.io.OutputStream;

public interface FileService {
    public String storeFile(String fileName, InputStream inputStream, String contentType);

    public void writeFile(String id, OutputStream outputStream);
}
