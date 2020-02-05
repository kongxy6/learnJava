package annotation.model;

import annotation.Constraints;
import annotation.DBTable;
import annotation.SQLInteger;
import annotation.SQLString;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TableCreator {
    public static String createTableSQL(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        // 获取类注解
        DBTable dbTable = clazz.getAnnotation(DBTable.class);
        if (dbTable == null) {
            return null;
        }
        String tableName = dbTable.name();
        if (tableName.length() == 0) {
            tableName = className.toUpperCase();
        }
        List<String> columnDefs = new ArrayList<>();
        // 获取域注解
        // 遍历所有的域，然后获取域上的注解
        for (Field field : clazz.getDeclaredFields()) {
            Annotation annotation = field.getAnnotation(SQLInteger.class);
            if (annotation != null) {
                String columnName;
                SQLInteger sqlInteger = (SQLInteger) annotation;
                if (sqlInteger.name().length() == 0) {
                    columnName = field.getName().toLowerCase();
                } else {
                    columnName = sqlInteger.name();
                }
                columnDefs.add(" " + columnName + " INT" + getConstraint(sqlInteger.constraint()));
                continue;
            }
            annotation = field.getAnnotation(SQLString.class);
            if (annotation != null) {
                String columnName;
                SQLString sqlString = (SQLString) annotation;
                if (sqlString.name().length() == 0) {
                    columnName = field.getName().toLowerCase();
                } else {
                    columnName = sqlString.name();
                }
                columnDefs.add(" " + columnName + " VARCHAR(" + sqlString.value() + ")" + getConstraint(sqlString.constraint()));
            }
        }
        StringBuilder tableSQL = new StringBuilder();
        tableSQL.append("create table " + tableName + "(");
        for (int i = 0; i < columnDefs.size(); ++i) {
            if (i == columnDefs.size() - 1) {
                tableSQL.append(columnDefs.get(i));
                tableSQL.append(" );");
            } else {
                tableSQL.append(columnDefs.get(i));
                tableSQL.append(",");
            }
        }
        return tableSQL.toString();
    }

    private static String getConstraint(Constraints con) {
        String constraints = "";
        if (!con.allowNull())
            constraints += " NOT NULL";
        if (con.primaryKey())
            constraints += " PRIMARY KEY";
        if (con.unique())
            constraints += " UNIQUE";
        return constraints;
    }
}
