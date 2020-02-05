package mybatis.interceptor.Executor;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

@Intercepts(@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class HisExecutorInterceptor implements Interceptor {

    private Properties properties = null;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("===== hisInterceptor =====");
        Object[] object = invocation.getArgs();
        // 当只有一个参数时，直接将object[1] 转为参数
        HashMap<String, Object> paramMap = (MapperMethod.ParamMap) object[1];
        Date date = (Date) paramMap.get("time");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, Integer.parseInt(properties.getProperty("offset")));
        paramMap.put("time", calendar.getTime());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    // 用来传递配置的参数的
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}

