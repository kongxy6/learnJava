package java8;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class UseLmd {

    Function<String, Integer> stringToInteger = Integer::parseInt;

    List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH)
    );

    List<String> threeHighColors = menu.stream()
            .filter(dish -> dish.getCalories() > 300)
            .map(Dish::getName)
            .limit(3)
            .collect(toList());

    @Test
    void test() {
        System.out.println(stringToInteger.apply("-1"));
        threeHighColors.forEach(System.out::println);

    }

}
