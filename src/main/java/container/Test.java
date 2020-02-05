package container;

import java.util.*;

public class Test {

    @org.junit.jupiter.api.Test
    public void test() {

        Set<String> set = new TreeSet<>();
        set.add("1");
        set.add("2");
        set.add("3");
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        List<String> list = new LinkedList<>();
        list.add("!");
        list.add("2");
        list.add("3");
        iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        Queue<String> queue = new PriorityQueue<>();
        queue.add("!");
        queue.add("2");
        queue.add("3");
        iterator = queue.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        Map<String, String> map = new LinkedHashMap<String, String>(8, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                boolean tooBig = size() > 3;
                return tooBig;
            }
        };
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("2 = " + map.get("2"));
        map.put("4", "c");
        iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }

}
