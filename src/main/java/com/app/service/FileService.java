package com.app.service;

import com.app.dto.ProductDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import com.app.mappers.ProductMapper;
import com.app.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
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
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                int character = 0;
                while ((character = reader.read()) != -1) {
                    stringBuilder.append((char) character);
                }
            } catch (Exception e) {
                throw new AppException(ExceptionCodes.FILE_UPLOAD, "getProductsFromFile - error reading file content");
            }
            Arrays.stream(stringBuilder.toString().split("(])"))
                    .forEach(line -> {
                        try {
                            String linePured = line
                                    .replaceAll("\n", "")
                                    .replaceAll("\r", "")
                                    .replaceAll("\"", "")
                                    .replaceAll("'", "");

                            String[] lineSplit = linePured.split("(})");

                            productDTOS.add(ProductMapper.toDto(Product.builder()
                                    .name(lineSplit[0])
                                    .numberInAuction(lineSplit[1])
                                    .auctionIndex(lineSplit[2])
                                    .description(lineSplit[3])
                                    .nettPrice(BigDecimal.valueOf(Double.parseDouble(lineSplit[4]
                                            .replaceAll("[złl\\s]+", "")
                                            .replaceAll(",", "\\."))))
                                    .vat(Double.parseDouble(lineSplit[5]
                                            .replaceAll("[złl\\s]", "")
                                            .replaceAll(",", "\\.")))
                                    .grossPrice(BigDecimal.valueOf(Double.parseDouble(lineSplit[6]
                                            .replaceAll("[złl\\s]", "")
                                            .replaceAll(",", "\\."))))
                                    .build()));

                        } catch (Exception e) {
                            // TODO: 2020-01-18 exception to logs - line not added
                        }
                    });

            return productDTOS;
        } catch (Exception e) {
            throw new AppException(ExceptionCodes.FILE_UPLOAD, "getProductsFromFile - error during file upload");
        }
    }
}