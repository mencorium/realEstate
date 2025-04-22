import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Property {
    // class properties
    private final String squareFeet;
    private final String numBedrooms;
    private final String numBathrooms;

    // constructor
    public Property(String squareFeet, String beds, String bath) {
        if (squareFeet == null || beds == null || bath == null) {
            throw new IllegalArgumentException("Property fields cannot be null");
        }
        this.squareFeet = squareFeet;
        this.numBathrooms = bath;
        this.numBedrooms = beds;
    }

    public void display() {
        System.out.println("Property details");
        System.out.println("==================");
        System.out.printf("Square footage: %s%n", this.squareFeet);
        System.out.printf("Bedrooms: %s%n", this.numBedrooms);
        System.out.printf("Bathrooms: %s%n", this.numBathrooms);
    }

    public static Map<String, String> promptInit(Scanner scanner) {
        Map<String, String> properties = new HashMap<>();
        System.out.print("Enter square footage: ");
        properties.put("squareFeet", scanner.nextLine());

        System.out.print("Enter number of bedrooms: ");
        properties.put("numBedrooms", scanner.nextLine());

        System.out.print("Enter number of bathrooms: ");
        properties.put("numBathrooms", scanner.nextLine());

        return properties;
    }

    // Getters
    public String getSquareFeet() { return squareFeet; }
    public String getNumBedrooms() { return numBedrooms; }
    public String getNumBathrooms() { return numBathrooms; }
}

class Apartment extends Property {
    private static final List<String> validLaundries = List.of("coin", "ensuite", "none");
    private static final List<String> validBalconies = List.of("yes", "no", "solarium");
    private final String balcony;
    private final String laundry;

    public Apartment(String squareFeet, String beds, String bath, String balcony, String laundry) {
        super(squareFeet, beds, bath);
        if (!validBalconies.contains(balcony.toLowerCase()) || !validLaundries.contains(laundry.toLowerCase())) {
            throw new IllegalArgumentException("Invalid balcony or laundry value");
        }
        this.balcony = balcony;
        this.laundry = laundry;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Apartment Details");
        System.out.printf("Laundry: %s%n", this.laundry);
        System.out.printf("Balcony: %s%n", this.balcony);
    }

    public static Map<String, String> promptInit(Scanner scanner) {
        Map<String, String> parentInit = Property.promptInit(scanner);

        String laundry = "";
        while (!validLaundries.contains(laundry.toLowerCase())) {
            System.out.printf("What laundry facilities does the property have? (%s): ",
                    String.join(", ", validLaundries));
            laundry = scanner.nextLine();
        }

        String balcony = "";
        while (!validBalconies.contains(balcony.toLowerCase())) {
            System.out.printf("Does the property have a balcony? (%s): ",
                    String.join(", ", validBalconies));
            balcony = scanner.nextLine();
        }

        parentInit.put("laundry", laundry);
        parentInit.put("balcony", balcony);

        return parentInit;
    }
}

class House extends Property {
    private static final List<String> validGarage = List.of("attached", "detached", "none");
    private static final List<String> validFence = List.of("yes", "no");
    private final String garage;
    private final String fenced;
    private final String numStories;

    public House(String squareFeet, String beds, String bath, String numStories, String garage, String fenced) {
        super(squareFeet, beds, bath);
        if (!validGarage.contains(garage.toLowerCase()) || !validFence.contains(fenced.toLowerCase())) {
            throw new IllegalArgumentException("Invalid garage or fence value");
        }
        this.garage = garage;
        this.fenced = fenced;
        this.numStories = numStories;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("HOUSE DETAILS");
        System.out.println("# of stories: " + this.numStories);
        System.out.println("Garage: " + this.garage);
        System.out.println("Fenced yard: " + this.fenced);
    }

    public static Map<String, String> promptInit(Scanner scanner) {
        Map<String, String> parentInit = Property.promptInit(scanner);

        String fenced = "";
        while (!validFence.contains(fenced.toLowerCase())) {
            System.out.printf("Is the yard fenced? (%s): ",
                    String.join(", ", validFence));
            fenced = scanner.nextLine();
        }

        String garage = "";
        while (!validGarage.contains(garage.toLowerCase())) {
            System.out.printf("Is there a garage? (%s): ",
                    String.join(", ", validGarage));
            garage = scanner.nextLine();
        }

        System.out.print("How many stories? ");
        String numStories = scanner.nextLine();

        parentInit.put("fenced", fenced);
        parentInit.put("garage", garage);
        parentInit.put("numStories", numStories);

        return parentInit;
    }
}