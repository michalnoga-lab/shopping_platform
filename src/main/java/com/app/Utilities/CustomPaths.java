package com.app.Utilities;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface CustomPaths {

    Path RESOURCES_FOLDER = Paths.get("src", "main", "resources", "files");
    Path COMPANIES_FILE_PATH = Paths.get(String.valueOf(RESOURCES_FOLDER), "companies.csv");
    // TODO: 11.02.2020 path to external folder
}