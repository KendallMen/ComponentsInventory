package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    public static <T> List<T> readList(String path, TypeReference<List<T>> type) {
        try {
            File file = new File(path);

            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }

            return mapper.readValue(file, type);

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void writeJSON(String path, Object data) {
        try {

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(path), data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
