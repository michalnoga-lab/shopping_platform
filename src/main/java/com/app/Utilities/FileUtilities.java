package com.app.Utilities;

import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

public interface FileUtilities {

    static String generateFileName(String nip) {
        LocalDateTime time = LocalDateTime.now();
        String uuid = UUID.randomUUID().toString();

        return time.toString().replaceAll("[-T:.]+", "").substring(0, 14)
                + "_" + nip
                + "_" + uuid.replaceAll("[-]+", "").substring(0, 12)
                + ".xml";
    }

    static String saveFileToDisk(String fileContent, String fileName) {

        byte[] fileContentInBytes = fileContent.getBytes(StandardCharsets.UTF_8);

        try (FileOutputStream fileOutputStream = new FileOutputStream(CustomPaths.SAVED_ORDERS_PATH + fileName)) {
            fileOutputStream.write(fileContentInBytes, 0, fileContentInBytes.length);

        } catch (Exception e) {
            throw new AppException(ExceptionCodes.FILE_UTILITIES, "saveToDisk - error saving file: " + fileName);
        }
        return CustomPaths.SAVED_ORDERS_PATH + fileName;
    }
}