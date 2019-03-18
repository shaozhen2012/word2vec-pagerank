package com.rongpingkeji.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;
import java.util.Map;

/**
 * JSON 工具类
 *
 * @since 16/12/17
 */
public interface JsonUtil {

    /**
     * @return 是否为合法的 json 对象字符串
     */
    static boolean isValidObject(String json) {
        if (json == null || json.length() == 0) {
            return false;
        }
        try {
            JsonNode node = Holder.MAPPER.readTree(json);
            return node.isObject();
        } catch (IOException e) {
            // ignored
        }
        return false;
    }

    /**
     * @return null for invalid json
     */
    static JsonNodeType getJsonType(String json) {
        if (json == null || json.length() == 0) {
            return null;
        }
        try {
            JsonNode node = Holder.MAPPER.readTree(json);
            return node.getNodeType();
        } catch (IOException e) {
            // ignored
        }
        return null;
    }

    static Map<String, Object> toMap(String json) throws IOException {
        return Holder.MAPPER.readValue(json, Holder.TYPE_REF_MAP_STRING_OBJECT);
    }

    static Map<String, String> toMapStr(String json) throws IOException {
        return Holder.MAPPER.readValue(json, Holder.TYPE_REF_MAP_STRING_STRING);
    }

    static String stringify(Object o) {
        return stringify(o, false);
    }

    static String stringify(Object o, boolean pretty) {
        try {
            if (pretty) {
                return Holder.MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(o);
            } else {
                return Holder.MAPPER.writeValueAsString(o);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    static <T> T parse(String json, Class<T> type) {
        try {
            return Holder.MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * If {@link ObjectMapper} is not required, use {@link #reader()} or {@link #writer()} instead.
     *
     * @return The ObjectMapper used by {@link JsonUtil}, the returned mapper is immutable,{@link ObjectMapper#copy()} first
     * if you wanner change.
     * @see <a href=http://stackoverflow.com/a/3909846/1870054>How to share ObjectMapper</a>
     */
    static ObjectMapper mapper() {
        return Holder.MAPPER;
    }

    /**
     * @return Shared writer, this is immutable
     */
    static ObjectWriter writer() {
        return Holder.MAPPER.writer();
    }

    /**
     * @return Shared reader, this is immutable
     */
    static ObjectReader reader() {
        return Holder.MAPPER.reader();
    }

    class Holder {

        private static final ObjectMapper MAPPER =
                new ObjectMapper()
                        .findAndRegisterModules()
                        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
        private static final TypeReference<Map<String, Object>> TYPE_REF_MAP_STRING_OBJECT = new TypeReference<Map<String, Object>>() {
        };
        private static final TypeReference<Map<String, String>> TYPE_REF_MAP_STRING_STRING = new TypeReference<Map<String, String>>() {
        };
    }

    /**
     * 将节点保存为字符串, {@link com.fasterxml.jackson.annotation.JsonRawValue} 只作用于序列化, 该反序列化器相当于该操作的反向操作.
     * 使用注解 {@code @JsonDeserialize(using = JSON.KeepAsJsonDeserializer.class)}
     */
    class JsonRawValueDeserializer extends JsonDeserializer<String> {

        @Override
        public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            TreeNode tree = jp.getCodec().readTree(jp);
            return tree.toString();
        }
    }
}
