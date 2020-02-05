package io.bio;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class FileUtilsTest {

    @Test
    public void appendToFile() throws IOException {
        FileUtils fileUtils = new FileUtils();
//        byte[] bytes = fileUtils.readFileByInputStream(new File("tiktok.mp4"));
//        fileUtils.createFile("dsad.mp4");
//        fileUtils.writeFileByOutputStream(new File("dsad.mp4"), true, bytes);
        fileUtils.writeFileByWriter(new File("info.txt"), true, "dsdasada\n");

    }


}