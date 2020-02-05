package classLoader;

import java.io.*;

public class MyClassLoader extends ClassLoader {

    static final String path = "c:\\lib";

    public MyClassLoader() {

    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(path, name + ".class");
        try {
            FileInputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bytes, 0, 1024)) != -1) {
                buffer.write(bytes, 0, len);
            }
            bytes = buffer.toByteArray();
            inputStream.close();
            buffer.close();
            return defineClass(name, bytes, 0, bytes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ClassNotFoundException();
    }
}
