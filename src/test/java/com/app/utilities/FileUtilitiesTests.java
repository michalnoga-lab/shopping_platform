package com.app.utilities;

import com.app.Utilities.FileUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
public class FileUtilitiesTests {

    @Test
    @DisplayName("generateFileName")
    void test10() {

        String regex = "\\d{14}_\\d{10}_[\\da-z]{12}";

        String filename1 = FileUtilities.generateFileName("0000000000");
        String filename2 = FileUtilities.generateFileName("0000000000");
        String filename3 = FileUtilities.generateFileName("0000000000");
        String filename4 = FileUtilities.generateFileName("0000000000");
        String filename5 = FileUtilities.generateFileName("0000000000");
        String filename6 = FileUtilities.generateFileName("0000000000");

        Assertions.assertTrue(filename1.matches(regex));
        Assertions.assertTrue(filename2.matches(regex));
        Assertions.assertTrue(filename3.matches(regex));
        Assertions.assertTrue(filename4.matches(regex));
        Assertions.assertTrue(filename5.matches(regex));
        Assertions.assertTrue(filename6.matches(regex));
    }
}