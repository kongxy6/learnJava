package test;

import baseAlgorithm.sort.BubbleSort;
import designPattern.decoration.cafe.Cafe;
import designPattern.decoration.cafe.DeCaf;
import designPattern.decoration.cafe.decorate.ChocolateDecorator;
import designPattern.decoration.cafe.decorate.MilkDecorator;
import model.SqlObject;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Cafe cafe = new DeCaf();
        cafe = new MilkDecorator(new ChocolateDecorator(cafe));
        System.out.println(cafe.getDescription());


        int[] a = {432, 324, 32, 4, 24, 324, 32, 4, 24, 23, 24, 23, 42, 3};
        BubbleSort.sort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
        //保证容器只new一次，也无法给其拷贝赋值
        final Map<String, SqlObject> loc = new HashMap<>();
        loc.put("1", new SqlObject(1, "1"));
        loc.replace("1", new SqlObject(2, "2"));
        //返回一个不可变得容器，无法进行put,remove,replace等
        final Map<String, SqlObject> unmodifiable = Collections.unmodifiableMap(loc);
        SqlObject sqlObject = unmodifiable.get("1");
        sqlObject.setId(3);
        sqlObject.setField("3");

        List<SqlObject> sqlObjectList = Collections.synchronizedList(new ArrayList<>());
        sqlObjectList.add(new SqlObject(1, "2"));
        System.out.println(sqlObjectList);

    }

}
