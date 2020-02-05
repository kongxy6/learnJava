package classLoader;

import lombok.extern.slf4j.Slf4j;
import model.SqlObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

@Slf4j
public class ResourceTest {

    @Test
    void getResource() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        System.out.println(System.getProperty("sun.boot.class.path"));

        System.out.println(System.getProperty("java.ext.dirs"));

        System.out.println(System.getProperty("java.class.path"));

        // 获取资源
        URL url = this.getClass().getResource("resources/conf/sysConf.json");
        // 打开流
        InputStream inputStream = url.openStream();
        InputStreamReader inputStreamReader;
        inputStreamReader = new InputStreamReader(inputStream);
        char[] buf = new char[1024];
        int count = inputStreamReader.read(buf);
        System.out.println(new String(buf, 0, count));
        // 关闭流
        inputStreamReader.close();


        /**
         * 类对象getResource会根据当前类的绝对路径，将相对路径转为要加载资源的实际绝对路径
         * 因为最终加载资源是要交给classloader去做的，而且它只接受绝对路径
         */
        System.out.println(this.getClass().getResource("/classLoader/Resource.class"));

        //相对路径，实际上该方法还是会将相对路径转为绝对路径
        System.out.println(this.getClass().getResource("Resource.class"));

        System.out.println(this.getClass().getResource("resources/conf/sysConf.json"));

        //ClassLoader.getResource()的资源获取不能以/开头，统一从根路径开始搜索资源。
        System.out.println(this.getClass().getClassLoader().getResource("classLoader/resources/request.xml"));

        // 将Intern放到了bootstrap类加载器的加载目录下
        // 而在加载的时候也将SqlObject使用bootstrap加载了进来
        ClassLoader classLoader = new MyClassLoader();
        Class clazz = classLoader.loadClass("Intern");
        Object object = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("test");
        method.invoke(object);
        log.debug("myClassLoader的父加载器为: {}", classLoader.getParent());
        log.debug("SqlObject的类加载器为: {}", SqlObject.class.getClassLoader());
        log.debug("Intern类的加载器为: {}", clazz.getClassLoader());
    }
}
