package com.vtheatre.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.apache.commons.io.FileUtils;

@Component
public class ResourceUtils {

    public static File getFileFromResources(String path, String fileName, String fileExtension) throws IOException {
        File file = null;
        ClassPathResource classPathResource = new ClassPathResource(path + fileName + fileExtension);
        InputStream inputStream = classPathResource.getInputStream();
        file = File.createTempFile(fileName, fileExtension);
        FileUtils.copyInputStreamToFile(inputStream, file);
        return file;
    }
    
}
