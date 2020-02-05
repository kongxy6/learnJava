package mybatis.interceptor.ParameterHandler;


import mybatis.model.Blog;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.PreparedStatement;
import java.util.Properties;

@Intercepts(@Signature(
        type = ParameterHandler.class,
        method = "setParameters",
        args = {PreparedStatement.class}))
public class ParameterInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MetaObject metaStatementHandler = SystemMetaObject.forObject(invocation.getTarget());
        while (metaStatementHandler.hasGetter("h")) {
            metaStatementHandler = SystemMetaObject.forObject(metaStatementHandler.getValue("h"));
        }
        while (metaStatementHandler.hasGetter("target")) {
            metaStatementHandler = SystemMetaObject.forObject(metaStatementHandler.getValue("target"));
        }
        metaStatementHandler = SystemMetaObject.forObject(metaStatementHandler.getValue("parameterObject"));
        Object paramObject = metaStatementHandler.getOriginalObject();
        if (!(paramObject instanceof Blog)) {
            paramObject = (MapperMethod.ParamMap) paramObject;
        }
        System.out.println("paramMap: " + paramObject);
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
