package designPattern.proxy;


import designPattern.decoration.Test;
import designPattern.decoration.cafe.Cafe;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;

@Intercepts({@Signature(type = Cafe.class, method = "getDescription", args = {}),
        @Signature(type = Test.class, method = "test", args = {})})
public class MilkInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
