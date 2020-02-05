package mybatis.interceptor.StatementHandler;


import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Properties;

@Intercepts(@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}))
public class MyStatementInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler routingStatementHandler = (RoutingStatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = SystemMetaObject.forObject(routingStatementHandler);
        while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h");
            metaStatementHandler = SystemMetaObject.forObject(object);
        }
        // 分离最后一个代理对象的目标类
        while (metaStatementHandler.hasGetter("target")) {
            Object object = metaStatementHandler.getValue("target");
            metaStatementHandler = SystemMetaObject.forObject(object);
        }
        String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        sql += "\n  and 1=1";
        // TODO: 确认是否对一级缓存是否有影响
        if ("SELECT".equals(metaStatementHandler.getValue("delegate.mappedStatement.sqlCommandType.name"))) {
            metaStatementHandler.setValue("delegate.boundSql.sql", sql);
        }
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
