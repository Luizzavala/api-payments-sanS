package me.quadradev.api.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.context.support.GenericApplicationContext;

class DotenvInitializerTest {

    @TempDir
    Path tempDir;

    @Test
    void insertsPropertiesFromDotenv() throws IOException {
        Files.writeString(tempDir.resolve(".env"), "TEST_KEY=dotenvValue\n");
        String originalUserDir = System.getProperty("user.dir");
        GenericApplicationContext context = new GenericApplicationContext();
        try {
            System.setProperty("user.dir", tempDir.toString());
            new DotenvInitializer().initialize(context);
            assertThat(context.getEnvironment().getProperty("TEST_KEY")).isEqualTo("dotenvValue");
        } finally {
            System.setProperty("user.dir", originalUserDir);
            context.close();
        }
    }

    @Test
    void systemPropertiesTakePrecedence() throws IOException {
        Files.writeString(tempDir.resolve(".env"), "TEST_KEY=dotenvValue\n");
        String originalUserDir = System.getProperty("user.dir");
        GenericApplicationContext context = new GenericApplicationContext();
        try {
            System.setProperty("user.dir", tempDir.toString());
            System.setProperty("TEST_KEY", "systemValue");
            new DotenvInitializer().initialize(context);
            assertThat(context.getEnvironment().getProperty("TEST_KEY")).isEqualTo("systemValue");
        } finally {
            System.clearProperty("TEST_KEY");
            System.setProperty("user.dir", originalUserDir);
            context.close();
        }
    }
}
