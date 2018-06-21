package com.gxf.webapi.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author 甘晓锋
 *
 * 2018年6月19日
 */
public class DateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String formatResult = dateFormat.format(value);
		gen.writeString(formatResult);
	}

}
