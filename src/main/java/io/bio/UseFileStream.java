package io.bio;

import org.junit.jupiter.api.Test;

import java.io.*;

public class UseFileStream {

    public byte[] readFile(String fileName) {
        FileInputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = null;
        try {
            inputStream = new FileInputStream(fileName);
            byte[] bytes = new byte[1024 * 1024];
            int length;
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }
            data = outputStream.toByteArray();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public boolean writeFile(String fileName, byte[] bytes) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileName);
            outputStream.write(bytes);
        } catch (IOException e) {
            try {
                outputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            File file = new File(fileName);
            file.delete();
            return false;
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Test
    void test() {
        byte[] bytes = readFile("ansi.txt");
        writeFile("test_ansi.txt", bytes);
    }
}
