package com.app.Utilities;

import java.nio.file.Paths;

public interface CustomPaths {

    String ROOT = System.getProperty("user.dir");
    String RESOURCES_FOLDER = Paths.get(ROOT, "src", "main", "resources", "files").toString();
    String COMPANIES_FILE_PATH = Paths.get(RESOURCES_FOLDER, "companies.csv").toString();
    String SAVED_ORDERS_PATH = Paths.get(RESOURCES_FOLDER, "saved_orders").toString() + System.getProperty("file.separator");
}