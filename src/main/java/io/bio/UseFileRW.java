package io.bio;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class UseFileRW {

    Charset charsetGBK = Charset.forName("GBK");

    Charset charsetUTF = StandardCharsets.UTF_8;

    public String readFile(String fileName) {
        File file = new File(fileName);
        FileReader fileReader = null;
        StringBuffer stringBuffer = null;
        try {
            fileReader = new FileReader(file);
            stringBuffer = new StringBuffer();
            char[] buffer = new char[1024];
            int length;
            while ((length = fileReader.read(buffer)) != -1) {
                stringBuffer.append(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }

    public boolean write(String fileName, String text) {
        FileWriter fileWriter = null;
        char[] chars = text.toCharArray();
        try {
            fileWriter = new FileWriter(new File(fileName));
            fileWriter.write(chars, 0, text.length());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public String readFile(String fileName, Charset charset) {
        InputStreamReader fileReader = null;
        StringBuffer stringBuffer = null;
        try {
            // FileReader 等价于 new InputStreamReader(new FileInputStream(fileName))
            // 可以看出实际字符流就是解码器和流的合体
            fileReader = new InputStreamReader(new FileInputStream(fileName), charset);
            stringBuffer = new StringBuffer();
            char[] buffer = new char[1024];
            int length;
            while ((length = fileReader.read(buffer)) != -1) {
                stringBuffer.append(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }

    public boolean write(String fileName, String text, Charset charset) {
        OutputStreamWriter fileWriter = null;
        char[] chars = text.toCharArray();
        try {
            fileWriter = new OutputStreamWriter(new FileOutputStream(fileName), charset);
            fileWriter.write(chars, 0, text.length());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Test
    void test() {
        //获取系统默认编码
        System.out.println(System.getProperty("file.encoding"));
        System.out.println(Charset.defaultCharset());
        String utf = readFile("utf.txt", charsetUTF);
        String ansi = readFile("ansi.txt", charsetGBK);
        assert utf.equals(ansi);
        write("test_utf.txt", ansi);
        write("test_gbk.txt", utf, charsetGBK);
    }
}
