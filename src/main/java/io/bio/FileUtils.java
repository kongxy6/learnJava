package io.bio;

/* 之后改为common io的直接使用*/

import com.sun.istack.internal.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TIP: 使用buffer时，一般适合小粒度的读写请求；
 * 当读写粒度大时，使用buffer反而会增加底层资源打开和关闭的次数
 */
public class FileUtils {

    /**
     * 用来测试缓冲区功能的段长度
     */
    static final int SEGMENT_SIZE = 1024;

    public static List<String> getAllStrings(String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> stringList = new ArrayList<>();
        String temp = null;
        while ((temp = bufferedReader.readLine()) != null) {
            stringList.add(temp);
        }
        bufferedReader.close();
        return stringList;
    }

    public boolean createFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            return true;
        } else {
            return file.createNewFile();
        }
    }

    /**
     * some about Java IO
     */

    /**
     * 不包含文件夹下的文件夹
     *
     * @param folderName
     * @return
     */
    public File[] getAllFilesOfFloder(String folderName) {
        File file = new File(folderName);
        File[] files;
        if (file.isDirectory()) {
            files = file.listFiles(pathname -> !pathname.isDirectory());
            return files;
        } else {
            return null;
        }

    }

    /**
     * 若此处使用write(byte [])的方法，可能会触发fileOutputStream底层的一次性批量写入
     * 若此处len>buffer的容量，效率等同于fileOutputStream，触发底层批量写入
     * 只有在len 小于(或远小于)buffer容量时，buffer才生效
     * 当刷新缓存时，也是调用的底层fileOutputStream的批量刷新方法
     *
     * @param file
     * @param append
     * @param bytes
     */
    public void writeFileByOutputStream(File file, boolean append, @NotNull byte[] bytes) {
        FileOutputStream fileOutputStream;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file, append);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            int offset = 0;
            int len = bytes.length;
            while (offset < len) {
                if (offset + SEGMENT_SIZE > len) {
                    bufferedOutputStream.write(bytes);
                    bufferedOutputStream.write(bytes, offset, len - offset);
                    break;
                } else {
                    bufferedOutputStream.write(bytes, offset, SEGMENT_SIZE);
                    offset += SEGMENT_SIZE;
                }
            }
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param file
     * @return
     */
    public byte[] readFileByInputStream(File file) {
        FileInputStream fileInputStream;
        BufferedInputStream bufferedInputStream = null;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            fileInputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            int len;
            byte[] buf = new byte[SEGMENT_SIZE];
            // 如果len 小于buffer容量时，buffer生效，否则直接使用FileInputStream进行批量读入
            // 且需要填充缓冲区时，直接使用底层fileInputStream一次性读入
            while ((len = bufferedInputStream.read(buf, 0, SEGMENT_SIZE)) != -1) {
                bytes.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes.toByteArray();
    }

    /**
     * 如果调用BufferedWriter接受字符数组的方法
     * 如果数组长度大于缓冲区，将直接调用se写入到底层
     *
     * @param file
     * @param append
     * @param s
     */
    public void writeFileByWriter(File file, boolean append, String s) {
        BufferedWriter bufferedWriter = null;
        try {
            // 想要指定字符集建议重写FileWriter
            // 或使用new OutputStreamWriter(new FileOutputStream(bio, append),Charset)
            bufferedWriter = new BufferedWriter(new FileWriter(file, append));
            int offset = 0;
            int len = s.length();
            while (offset < len) {
                if (offset + SEGMENT_SIZE > len) {
                    // 直接调用write(String)，也使用buffer，满8192才刷新到底层流
                    // 而导致分批次写入，反而降低了效率
                    bufferedWriter.write(s, offset, len - offset);
                    break;
                } else {
                    bufferedWriter.write(s, offset, SEGMENT_SIZE);
                    offset += SEGMENT_SIZE;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String readFileByReader(File file) {
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            char[] buf = new char[SEGMENT_SIZE];
            int len;
            // 若是请求长度大于缓冲区，将直接使用底层流一次性读入
            while ((len = bufferedReader.read(buf, 0, SEGMENT_SIZE)) != -1) {
                stringBuffer.append(new String(buf, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }

    public void appendToFile(String content, String fileName) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName, true);
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
