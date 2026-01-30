package com.company.tests.dataproviders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class DataLoader {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private DataLoader() {}

    public static List<LinkedHashMap<String, Object>> loadJsonArray(File f) throws IOException {
        if (!f.exists()) return new ArrayList<>();
        return MAPPER.readValue(f, new TypeReference<List<LinkedHashMap<String,Object>>>(){});
    }

    public static List<Map<String, Object>> loadCsv(File f) throws IOException {
        List<Map<String,Object>> rows = new ArrayList<>();
        if (!f.exists()) return rows;
        List<String> lines = Files.readAllLines(f.toPath());
        if (lines.isEmpty()) return rows;
        String[] headers = lines.get(0).split(",");
        for (int i = 1; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            Map<String,Object> row = new LinkedHashMap<>();
            for (int j = 0; j < headers.length; j++) {
                String key = headers[j].trim();
                String val = j < parts.length ? parts[j].trim() : null;
                row.put(key, val);
            }
            rows.add(row);
        }
        return rows;
    }
}
