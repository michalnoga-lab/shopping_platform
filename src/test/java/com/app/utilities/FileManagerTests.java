package com.app.utilities;

import com.app.Utilities.FileManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
public class FileManagerTests {

    @Test
    @DisplayName("generateFileName")
    void test10() {

        String regex = "\\d{14}_\\d{10}_[\\da-z]{12}.xml";

        String filename1 = FileManager.generateFileName("0000000000");
        String filename2 = FileManager.generateFileName("0000000000");
        String filename3 = FileManager.generateFileName("0000000000");
        String filename4 = FileManager.generateFileName("0000000000");
        String filename5 = FileManager.generateFileName("0000000000");
        String filename6 = FileManager.generateFileName("0000000000");

        Assertions.assertTrue(filename1.matches(regex));
        Assertions.assertTrue(filename2.matches(regex));
        Assertions.assertTrue(filename3.matches(regex));
        Assertions.assertTrue(filename4.matches(regex));
        Assertions.assertTrue(filename5.matches(regex));
        Assertions.assertTrue(filename6.matches(regex));
    }
}