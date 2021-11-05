package main.java.com.luxoft.kirilin.tasks;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Cities {

    private static int capacity;
    private static Location finishLocation;


    public static void main(String[] args) throws IOException {
        String input = new Scanner(new File(args[0]), Charset.defaultCharset()).useDelimiter("\\Z").next();
        List<String> rows = new ArrayList<>(Arrays.asList(input.split("\\n")));

        int numberOfCities = Integer.parseInt(rows.remove(0));

        List<Location> locations = new ArrayList<>();

        for (int i = 1; i <= numberOfCities; i++) {
            String[] coordinates = rows.get(i).split(" ");
            Location location = new Location(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
            locations.add(location);
        }

        locations.forEach(location -> {
            location.setAvailableLocations(locations.stream()
                    .filter(loc -> resolveDistance(loc, location))
                    .collect(Collectors.toList()));
        });

        capacity = Integer.parseInt(rows.get(numberOfCities + 1));
        String[] route = rows.get(numberOfCities + 2).split(" ");
        Location startLocation = locations.get(Integer.parseInt(route[0]));
        finishLocation = locations.get(Integer.parseInt(route[1]));

        for (int i = 2; i < numberOfCities; i++) {
            boolean resolved = computeRoute(startLocation, i);
            if(resolved) {
                System.out.println(i);
                System.exit(0);
            }
        }
        System.out.println("-1");
    }

    private static boolean computeRoute(Location location, int i) {
        if(i == 2) {
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

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public List<Location> getAvailableLocations() {
            return availableLocations;
        }

        public void setAvailableLocations(List<Location> availableLocations) {
            this.availableLocations = availableLocations;
        }
    }

}
