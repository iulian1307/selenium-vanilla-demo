package com.qaitsolutions.selenium.wrapper.utils;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.IOException;

/**
 * Utility class for XML operations
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XmlUtils {

    /**
     * Deserializes a xml from a {@link String} or given xml file to a specified type
     *
     * @param xml  Can be either xml as {@link String} or the full path to a xml file (e.g. path/to/file.xml)
     * @param type Structure of the xml
     */
    public static <T> T deserialize(@NonNull final String xml, @NonNull final Class<T> type) {
        try {
            JacksonXmlModule module = new JacksonXmlModule();
            module.setDefaultUseWrapper(false);

            final var mapper = new XmlMapper(module);

            return mapper
                    .registerModule(new JavaTimeModule())
                    .readValue(xml, type);

        } catch (IOException e) {
            throw new AssertionError("Failed to convert xml string to object", e);
        }
    }
}
