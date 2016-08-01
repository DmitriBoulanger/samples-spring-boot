package de.dbo.samples.springboot.rest.test.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA {@link AttributeConverter} for {@link VehicleIdentificationNumber}.
 *
 */
@Converter(autoApply = true)
public class VehicleIdentificationNumberAttributeConverter
		implements AttributeConverter<VehicleIdentificationNumber, String> {

	@Override
	public String convertToDatabaseColumn(VehicleIdentificationNumber attribute) {
		return attribute.toString();
	}

	@Override
	public VehicleIdentificationNumber convertToEntityAttribute(String dbData) {
		return new VehicleIdentificationNumber(dbData);
	}

}
