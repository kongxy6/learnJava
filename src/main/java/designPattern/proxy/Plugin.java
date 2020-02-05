package designPattern.proxy;

import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

public class Plugin implements InvocationHandler {

    private Interceptor interceptor;
    private Object target;
    private Map<Class<?>, Set<Method>> signatureMap;

    public Plugin(Interceptor interceptor, Object target, Map<Class<?>, Set<Method>> signatureMap) {
        this.interceptor = interceptor;
        this.target = target;
        this.signatureMap = signatureMap;
    }

    public static Object wrap(Object object, Interceptor interceptor) {
        // 创建代理对象
        // 判断对象与拦截器类型是否一致
        Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
        Set<Class<?>> interfaces = getAllInterfaces(object);
        Set<Class<?>> specialInterfaces = new HashSet<>();
        for (Class<?> clazz : signatureMap.keySet()) {
            if (interfaces.contains(clazz)) {
                specialInterfaces.add(clazz);
            }
        }

        return null;
    }

    public static Map<Class<?>, Set<Method>> getSignatureMap(Interceptor interceptor) {
        Annotation annotation = interceptor.getClass().getAnnotation(Intercepts.class);
        if (annotation == null) {
            return null;
        }
        Intercepts intercepts = (Intercepts) annotation;
        Signature[] signatures = intercepts.value();
        Map<Class<?>, Set<Method>> signatureMap = new HashMap<>();
        for (Signature signature : signatures) {
            String methodString = signature.method();
            Class<?>[] args = signature.args();
            Class<?> clazz = signature.type();
            Set<Method> methods = signatureMap.computeIfAbsent(clazz, k -> new HashSet<>());
            try {
                Method method = clazz.getMethod(methodString, args);
                methods.add(method);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return signatureMap;
    }

    public static Set<Class<?>> getAllInterfaces(Object object) {
        Class<?> type = object.getClass();
        Set<Class<?>> interfaces = new HashSet<>();
        while (type != null) {
            interfaces.addAll(Arrays.asList(type.getInterfaces()));
            type = type.getSuperclass();
        }
        return interfaces;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 判断方法名称是否在interceptor内

        return null;
    }

}
