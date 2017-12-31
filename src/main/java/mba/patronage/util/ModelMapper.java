package mba.patronage.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class ModelMapper {
    private static final ObjectMapper OBJECT_MAPPER_ENABLED_ANNOTATIONS = new ObjectMapper();
    private static final ObjectMapper OBJECT_MAPPER_DISABLED_ANNOTATIONS = new ObjectMapper();

    static {
        OBJECT_MAPPER_DISABLED_ANNOTATIONS
                .disable(MapperFeature.USE_ANNOTATIONS)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T convertToModel(Object view, Class<T> modelClass) {
        return OBJECT_MAPPER_ENABLED_ANNOTATIONS.convertValue(view, modelClass);
    }

    public static <T> T convertToView(Object model, Class<T> viewClass) {
        return OBJECT_MAPPER_DISABLED_ANNOTATIONS.convertValue(model, viewClass);
    }

    public static <T > List<T> convertToViewList(List<?> models, Class<T> viewClass) {
        List<T> views = new ArrayList<>();
        for (Object model : models) {
            T view = convertToView(model, viewClass);
            views.add(view);
        }
        return views;
    }

    public static String convertToJsonString(Object value) {
        try {
            return OBJECT_MAPPER_DISABLED_ANNOTATIONS.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
