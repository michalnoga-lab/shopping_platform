package com.app.Utilities;

import com.app.exceptions.AppException;
import com.app.model.InfoCodes;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

public interface FileManager {

    static String generateFileName() {
        LocalDateTime time = LocalDateTime.now();
        String uuid = UUID.randomUUID().toString();

        return time.toString().replaceAll("[-T:.]+", "").substring(0, 14)
                + "_" + uuid.replaceAll("[-]+", "").substring(0, 12);
    }

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
            throw new AppException(InfoCodes.FILE_UTILITIES, "saveToDisk - error saving file: " + fileName);
        }
        return CustomPaths.SAVED_ORDERS_PATH + fileName;
    }

    static String uploadFileFromUser(MultipartFile multipartFile) {
        try {
            if (multipartFile == null || multipartFile.getBytes().length == 0) {
                throw new AppException(InfoCodes.FILE_MANAGER, "uploadFile - file is not correct");
            }

            final String filename = FileManager.generateFileName();
            FileCopyUtils.copy(multipartFile.getBytes(), new File(CustomPaths.UPLOADED_PRODUCTS_PATH + filename));
            return filename;

        } catch (Exception e) {
            throw new AppException(InfoCodes.FILE_MANAGER, "uploadFile - failed to upload: ");
        }
    }
}