package eu.kubenetic.commands;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

class InstreamTest extends IntegrationEnvironmentBase {

    private final static Path TEST_DATA_DIRECTORY = Paths.get("src", "test", "resources", "test-data");
    private final Instream command;

    InstreamTest() {
        this.command = new Instream("localhost", super.clamavPort);
    }

    @Test
    void testDwgSample() throws IOException {
        File testFile = TEST_DATA_DIRECTORY.resolve("colorwh.dwg").toFile();
        try (InputStream inputStream = new FileInputStream(testFile.getAbsoluteFile())) {
            System.out.println(command.execute(inputStream));
        }
    }

    @Test
    void testEICARZip() throws IOException {
        File testFile = TEST_DATA_DIRECTORY.resolve("eicar_com.zip").toFile();
        try (InputStream inputStream = new FileInputStream(testFile.getAbsoluteFile())) {
            System.out.println(command.execute(inputStream));
        }
    }

    @Test
    void testEICARText() throws IOException {
        File testFile = TEST_DATA_DIRECTORY.resolve("eicar.txt").toFile();
        try (InputStream inputStream = new FileInputStream(testFile.getAbsoluteFile())) {
            System.out.println(command.execute(inputStream));
        }
    }

//    public void testParseClamDRepsonse() {
//        String inputText = "stream: Win.Test.EICAR_HDB-1 FOUND\n" +
//                "stream: Win.Test.EICAR_HDB-1 FOUND\n" +
//                "stream: OK";
//
//
//    }
}