package com.app.service;

import com.app.dto.ProductDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {

    public List<ProductDTO> getProductsFromFile(MultipartFile file) {
        try {
            if (file == null || file.getBytes().length == 0) {
                throw new AppException(ExceptionCodes.FILE_UPLOAD, "getProductsFromFile - file is not correct");
            }
            List<ProductDTO> productDTOS = new ArrayList<>();

            System.out.println("------------- 1 -------------");
            System.out.println(file.getName());
            System.out.println(Arrays.toString(file.getBytes()));


            return productDTOS;
        } catch (Exception e) {
            throw new AppException(ExceptionCodes.FILE_UPLOAD, "getProductsFromFile - error during file upload");
        }
    }
}