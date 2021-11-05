package main.java.com.luxoft.kirilin.tasks;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Для перемещений между из города в город он предпочитает использовать машину.
 * При этом он заправляется только на станциях в городах, но не на станциях по пути.
 * Поэтому он очень аккуратно выбирает маршруты, чтобы машина не заглохла в дороге.
 *
 * А ещё Петя очень важный член команды, поэтому он не может себе позволить путешествовать
 * слишком долго. Он решил написать программу, которая поможет ему с выбором очередного
 * путешествия. Но так как сейчас у него слишком много других задач, он попросил вас помочь ему.
 *
 * Расстояние между двумя городами считается как сумма модулей разности по каждой из координат.
 * Дороги есть между всеми парами городов.
 *
 * Формат ввода
 * В первой строке входных данных записано количество городов
 * n(2 ≤ n ≤ 1000)
 * В следующих n строках даны два целых числа: координаты каждого города, не превосходящие
 * по модулю миллиарда. Все города пронумерованы числами от 1 до n в порядке записи
 * во входных данных. В следующей строке записано целое положительное число k,
 * не превосходящее двух миллиардов, — максимальное расстояние между городами,
 * которое Петя может преодолеть без дозаправки машины.
 * В последней строке записаны два различных числа — номер города, откуда едет Петя,
 * и номер города, куда он едет.
 *
 * Формат вывода
 * Если существуют пути, удовлетворяющие описанным выше условиям, то выведите минимальное
 * количество дорог, которое нужно проехать, чтобы попасть из начальной точки маршрута в конечную.
 * Если пути не существует, выведите -1.
 */

public class Cities {

    private static int capacity;
    private static Location finishLocation;


    public static void main(String[] args) throws IOException {
        String input = new Scanner(new File(args[0]), Charset.defaultCharset()).useDelimiter("\\Z").next();
        List<String> rows = new ArrayList<>(Arrays.asList(input.split("\\r\\n")));

        int numberOfCities = Integer.parseInt(rows.remove(0));

        List<Location> locations = new ArrayList<>();

        for (int i = 0; i < numberOfCities; i++) {
            String[] coordinates = rows.get(i).split(" ");
            Location location = new Location(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
            locations.add(location);
        }
        capacity = Integer.parseInt(rows.get(numberOfCities));

        locations.forEach(location -> {
            List<Location> availableLocations = locations.stream()
                    .filter(loc -> !loc.equals(location))
                    .filter(loc -> resolveDistance(loc, location))
                    .collect(Collectors.toList());
            location.setAvailableLocations(availableLocations);
        });

        String[] route = rows.get(numberOfCities + 1).split(" ");
        Location startLocation = locations.get(Integer.parseInt(route[0]) - 1);
        finishLocation = locations.get(Integer.parseInt(route[1]) - 1);

        for (int i = 1; i < numberOfCities; i++) {
            boolean resolved = computeRoute(startLocation, i);
            if(resolved) {
                System.out.println(i);
                System.exit(0);
            }
        }
        System.out.println("-1");
    }

    private static boolean computeRoute(Location location, int i) {
        if(i == 1) {
            return resolveDistance(location, finishLocation);
        }
        i--;
        for (Location availableLocation : location.getAvailableLocations()) {
            boolean result = computeRoute(availableLocation, i);
            if(result) return true;
        }
        return false;
    }

    
    static boolean resolveDistance(Location one, Location two){
        return Math.abs(one.x - two.x) + Math.abs(one.y - two.y) <= capacity;
    }



    static class Location{
        int x, y;

        List<Location> availableLocations;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public List<Location> getAvailableLocations() {
            return availableLocations;
        }

        public void setAvailableLocations(List<Location> availableLocations) {
            this.availableLocations = availableLocations;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Location location = (Location) o;
            return x == location.x &&
                    y == location.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

}
