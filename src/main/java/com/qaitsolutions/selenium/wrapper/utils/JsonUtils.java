package com.qaitsolutions.selenium.wrapper.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Utility class for Json operations
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {



    /**
     * Deserializes a json from a {@link String} to a specified type
     *
     * @param json {@link String} version of the json
     * @param type Type that will be deserialized to
     * @return Deserialized version of the json
     */
    public static <T> T deserialize(@NonNull final String json, @NonNull final Class<T> type) {
        try {
            final var mapper = new ObjectMapper();
            final var jvType = mapper.getTypeFactory().constructType(type);

            return mapper
                    .registerModule(new JavaTimeModule())
                    .readValue(json, jvType);

        } catch (JsonProcessingException e) {
            throw new AssertionError("Failed to convert json string to object", e);
        }
    }

    /**
     * Reads a json file and saves into a list of given type
     *
     * @param path full path of the json (e.g. path/to/file.json)
     * @param type Type that will be deserialized to
     * @return Deserialized version of the json
     */
    public static <T> List<T> deserializeFromPath(@NonNull final String path, @NonNull final Class<T> type) {
        try {
            final var mapper = new ObjectMapper();
            final var jvType = mapper.getTypeFactory().constructCollectionType(List.class, type);

            return mapper
                    .registerModule(new JavaTimeModule())
                    .readValue(new File(path), jvType);

        } catch (IOException e) {
            throw new AssertionError("Failed to convert/retrieve file and/or convert to object", e);
        }
    }
}
