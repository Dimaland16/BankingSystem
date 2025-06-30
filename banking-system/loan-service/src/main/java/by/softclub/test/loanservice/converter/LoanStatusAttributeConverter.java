package by.softclub.test.loanservice.converter;

import by.softclub.test.loanservice.entity.LoanStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LoanStatusAttributeConverter implements AttributeConverter<LoanStatus, String> {

    @Override
    public String convertToDatabaseColumn(LoanStatus status) {
        if (status == null) return null;
        return status.name().toLowerCase(); // LoanStatus.CREATED → "created"
    }

    @Override
    public LoanStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return LoanStatus.valueOf(dbData.toUpperCase()); // "created" → LoanStatus.CREATED
    }
}