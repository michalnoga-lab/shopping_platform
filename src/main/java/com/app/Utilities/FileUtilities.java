package com.app.Utilities;

import java.time.LocalDateTime;
import java.util.UUID;

public interface FileUtilities {

    static String generateFileName(String nip) {
        LocalDateTime time = LocalDateTime.now();
        String uuid = UUID.randomUUID().toString();

        return time.toString().replaceAll("[-T:.]+", "").substring(0, 14)
                + "_" + nip
                + "_" + uuid.replaceAll("[-]+", "").substring(0, 12);
    }

    static String saveFileToDisk(String fileContent, String fileName) {

        // TODO: 11.02.2020

        return "";
    }

}