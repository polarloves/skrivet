package com.skrivet.supports.file.web.impl.local;

import com.skrivet.core.common.convert.IdGenerator;
import com.skrivet.core.common.exception.UnknownExp;
import com.skrivet.supports.file.web.core.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.*;
@Service
@ConditionalOnProperty(value = "skrivet.file.type",havingValue = "local",matchIfMissing = true)
public class LocalFileService implements FileService {
    @Autowired
    private IdGenerator idGenerator;
    @Value("${skrivet.file.path:/home/files}")
    public String basicPath;

    @Override
    public String storeFile(String fileName, InputStream inputStream, String contentType) {
        FileOutputStream out = null;
        try {
            String id = idGenerator.generate();
            File file2 = new File(basicPath + File.separator + id);
            if (!file2.getParentFile().exists()) {
                file2.getParentFile().mkdirs();
            }
            file2.createNewFile();
            out = new FileOutputStream(file2);
            byte[] tmp = new byte[1024];
            int i = -1;
            while ((i = inputStream.read(tmp)) != -1) {
                out.write(tmp, 0, i);
            }
            return id;
        } catch (Exception e) {
            throw new UnknownExp().copyStackTrace(e);
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(inputStream);
        }
    }

    @Override
    public void writeFile(String id, OutputStream outputStream) {
        InputStream in = null;
        try {
            in = new FileInputStream(basicPath + File.separator + id);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            IOUtils.closeQuietly(outputStream);
        } catch (Exception e) {
            throw new UnknownExp().copyStackTrace(e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
