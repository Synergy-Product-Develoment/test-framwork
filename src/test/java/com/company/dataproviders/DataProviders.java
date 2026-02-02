package com.company.dataproviders;

import org.testng.annotations.DataProvider;

import java.io.File;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class DataProviders {

    private DataProviders() {}

    @DataProvider(name = "json")
    public static Object[][] jsonDataProvider(Method m) {
        String file = System.getProperty("data.file");
        if (file == null || file.isBlank()) {
            file = "src/test/resources/data/" + m.getDeclaringClass().getSimpleName() + "_" + m.getName() + ".json";
        }
        File f = new File(file);
        try {
            List<LinkedHashMap<String, Object>> rows = DataLoader.loadJsonArray(f);
            if (rows.isEmpty()) return new Object[][]{{Map.of()}};
            Object[][] out = new Object[rows.size()][];
            for (int i = 0; i < rows.size(); i++) out[i] = new Object[]{rows.get(i)};
            return out;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load JSON data from " + file, e);
        }
    }

    @DataProvider(name = "csv")
    public static Object[][] csvDataProvider(Method m) {
        String file = System.getProperty("data.file");
        if (file == null || file.isBlank()) {
            file = "src/test/resources/data/" + m.getDeclaringClass().getSimpleName() + "_" + m.getName() + ".csv";
        }
        File f = new File(file);
        try {
            List<Map<String,Object>> rows = DataLoader.loadCsv(f);
            if (rows.isEmpty()) return new Object[][]{{Map.of()}};
            Object[][] out = new Object[rows.size()][];
            for (int i = 0; i < rows.size(); i++) out[i] = new Object[]{rows.get(i)};
            return out;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load CSV data from " + file, e);
        }
    }
}
