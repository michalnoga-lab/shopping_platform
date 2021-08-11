package com.app.domain.auction_product.dto.validator;

import com.app.domain.auction_product.dto.CreateAuctionProductDto;
import com.app.domain.configs.validator.Validator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * CreateAuctionProductDtoValidator class
 * Validates values given by admin at products import
 *
 * @see CreateAuctionProductDto
 */
public class CreateAuctionProductDtoValidator implements Validator<CreateAuctionProductDto> {

    @Override
    public Map<String, String> validate(CreateAuctionProductDto createAuctionProductDto) {

        var errors = new HashMap<String, String>();

        if (createAuctionProductDto == null) {
            errors.put("create auction product dto", "is null");
        }

        var name = createAuctionProductDto.getName();
        if (hasIncorrectName(name)) {
            errors.put("create auction product dto", "has incorrect name");
        }

        var description = createAuctionProductDto.getDescription();
        if (hasIncorrectDescription(description)) {
            errors.put("create auction product dto", "has incorrect description");
        }

        var auctionIndex = createAuctionProductDto.getAuctionIndex();
        if (hasIncorrectAuctionIndex(auctionIndex)) {
            errors.put("create auction product dto", "has incorrect index");
        }

        var auctionConsecutiveNumber = createAuctionProductDto.getAuctionConsecutiveNumber();
        if (hasIncorrectConsecutiveNumber(auctionConsecutiveNumber)) {
            errors.put("create auction product dto", "has incorrect consecutive number");
        }

        var nettPrice = createAuctionProductDto.getNettPrice();
        if (hasIncorrectNettPrice(nettPrice)) {
            errors.put("create auction product dto", "has incorrect nett price");
        }

        var vat = createAuctionProductDto.getVat();
        if (hasIncorrectVat(vat)) {
            errors.put("create auction product dto", "has incorrect vat");
        }

        var grossPrice = createAuctionProductDto.getGrossPrice();
        if (hasIncorrectGrossPrice(grossPrice)) {
            errors.put("create auction product dto", "has incorrect gross price");
        }

        var ean = createAuctionProductDto.getEan();
        if (hasIncorrectEan(ean)) {
            errors.put("create auction product dto", "has incorrect EAN code");
        }

        return errors;
    }

    private boolean hasIncorrectName(String name) {
        return name == null;
    }

    private boolean hasIncorrectDescription(String description) {
        return description == null;
    }

    private boolean hasIncorrectAuctionIndex(String index) {
        return index == null;
    }

    private boolean hasIncorrectConsecutiveNumber(String consecutiveNumber) {
        return consecutiveNumber == null;
    }

    private boolean hasIncorrectNettPrice(BigDecimal nettPrice) {
        return nettPrice == null || nettPrice.compareTo(BigDecimal.ZERO) <= 0;
    }

    private boolean hasIncorrectVat(Integer vat) {
        return vat == null || (vat != 8 && vat != 23);
    }

    private boolean hasIncorrectGrossPrice(BigDecimal grossPrice) {
        return grossPrice == null || grossPrice.compareTo(BigDecimal.ZERO) <= 0;
    }

    private boolean hasIncorrectEan(String ean) {
        return ean == null;
    }
}
