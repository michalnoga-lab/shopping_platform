package com.app.service;

import com.app.Utilities.CustomPaths;
import com.app.Utilities.CustomRegex;
import com.app.Utilities.FileManager;
import com.app.dto.CompanyDTODetailsFromFile;
import com.app.dto.ProductDTO;
import com.app.exceptions.AppException;
import com.app.model.InfoCodes;
import com.app.mappers.ProductMapper;
import com.app.model.Company;
import com.app.model.LoggerInfo;
import com.app.model.Product;
import com.app.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private final CompanyRepository companyRepository;

    private final LoggerService loggerService;

    public List<ProductDTO> getProductsFromFile(MultipartFile file, Long companyId) {
        try {
            if (file == null || file.getBytes().length == 0) {
                throw new AppException(InfoCodes.FILE_UPLOAD, "getProductsFromFile - file is not correct");
            }
            if (companyId == null) {
                throw new AppException(InfoCodes.FILE_UPLOAD, "getProductsFromFile - company id is null");
            }
            if (companyId <= 0) {
                throw new AppException(InfoCodes.FILE_UPLOAD, "getProductsFromFile - company ID less than zero");
            }
         /*   if (!file.getName().matches(PRODUCTS_FILE_NAME_REGEX)) { // TODO: 14.02.2020 upload file name check
                System.out.println(file.getName());
                throw new AppException(ExceptionCodes.FILE_UPLOAD, "getProductsFromFile - incorrect file extension");
            }
*/
            String filename = FileManager.uploadFileFromUser(file);

            loggerService.add(LoggerInfo.builder()
                    .infoCode(InfoCodes.FILE_UPLOAD)
                    .message("file " + filename + " uploaded to server")
                    .build());

            List<ProductDTO> productDTOS = new ArrayList<>();
            InputStream inputStream = new ByteArrayInputStream(Files.readAllBytes(
                    Paths.get(CustomPaths.UPLOADED_PRODUCTS_PATH + filename)));

            StringBuilder stringBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                int character = 0;
                while ((character = reader.read()) != -1) {
                    if (String.valueOf((char) character).matches(CustomRegex.UPLOAD_FILES_ALLOWED_CHARS)) {
                        stringBuilder.append((char) character);
                    }
                }
            } catch (Exception e) {
                throw new AppException(InfoCodes.FILE_UPLOAD, "getProductsFromFile - error reading file content");
            }

            Arrays.stream(stringBuilder.toString().split("(])"))
                    .forEach(line -> {
                        try {
                            String linePured = line
                                    .replaceAll("\n", "")
                                    .replaceAll("\r", "")
                                    .replaceAll("\"", "")
                                    .replaceAll("'", "");

                            if (line.length() > 10) {

                                String[] lineSplit = linePured.split("(})");
                                Optional<Company> companyOptional = companyRepository.findById(companyId);
                                Company company = companyOptional.orElseThrow(() -> new AppException(
                                        InfoCodes.SERVICE_FILES, "getProductsFromFile - no company with ID: " + companyId));

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
                                        .company(company)
                                        .build()));
                            }
                        } catch (Exception e) {
                            throw new AppException(InfoCodes.FILE_UPLOAD, "line from file " + filename + " not added: " + line);
                        }
                    });

            return productDTOS;
        } catch (Exception e) {
            throw new AppException(InfoCodes.FILE_UPLOAD, "getProductsFromFile - error during file upload ");
        }
    }

    public CompanyDTODetailsFromFile getCompanyDetailsFromFile(String nip) {
        if (nip == null) {
            throw new AppException(InfoCodes.SERVICE_FILES, "getCompanyDetailsFromFile - nip is null");
        }
        int character = 0;

        try {
            InputStream inputStream = new ByteArrayInputStream(Files.readAllBytes(Paths.get(CustomPaths.COMPANIES_FILE_PATH)));
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            StringBuilder stringBuilder = new StringBuilder();

            while ((character = reader.read()) != -1) {
                if (String.valueOf((char) character).matches(CustomRegex.UPLOAD_FILES_ALLOWED_CHARS) ||
                        String.valueOf((char) character).matches("[\\.\\]]+")) {
                    stringBuilder.append((char) character);
                }
            }

            CompanyDTODetailsFromFile companyDTODetailsFromFile = new CompanyDTODetailsFromFile();

            Arrays.stream(stringBuilder.toString().split("\n"))
                    .forEach(line -> {

                                try {
                                    String[] lineSplit = line.split("]");

                                    if (lineSplit[2].replaceAll("[^\\d]+", "").matches(nip) &&
                                            line.length() > 10) {

                                        companyDTODetailsFromFile.setCode(lineSplit[0]);
                                        companyDTODetailsFromFile.setName(lineSplit[1]);
                                        companyDTODetailsFromFile.setNip(nip);
                                        companyDTODetailsFromFile.setPostalCode(lineSplit[3]);
                                        companyDTODetailsFromFile.setCity(lineSplit[4]);
                                        companyDTODetailsFromFile.setStreet(lineSplit[5]);
                                    }
                                } catch (Exception e) {
                                    throw new AppException(InfoCodes.FILE_UPLOAD, "line from file companies.csv not added: " + line);
                               }
                            }
                    );
            return companyDTODetailsFromFile;

        } catch (Exception e) {
            throw new AppException(InfoCodes.FILE_UPLOAD, "getCompanyDetailsFromFile - error reading file content");
        }
    }
}