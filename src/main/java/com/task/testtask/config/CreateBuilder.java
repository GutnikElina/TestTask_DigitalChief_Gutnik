package com.task.testtask.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateBuilder {

    private static final CreateBuilder INSTANCE = new CreateBuilder();

    public static CreateBuilder getInstance() {
        return INSTANCE;
    }

    public XContentBuilder buildMapping() throws IOException {
        Map<String, String> properties = new HashMap<>() {{
            put("name", "text");
            put("description", "text");
            put("active", "boolean");
            put("startDate", "date");
        }};

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject()
                .startObject("properties");

        for (Map.Entry<String, String> entry : properties.entrySet()) {
            builder.startObject(entry.getKey()).field("type", entry.getValue()).endObject();
        }

        builder.endObject()
                .endObject();
        return builder;
    }
}
