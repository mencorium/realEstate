import java.util.*;

public class Agent {
    private final List<Property> propertyList = new ArrayList<>();
    private final Map<List<String>, Class<?>> typeMap;

    public Agent() {
        typeMap = Map.of(
                List.of("house", "rental"), HouseRental.class,
                List.of("house", "purchase"), HousePurchase.class,
                List.of("apartment", "rental"), ApartmentRental.class,
                List.of("apartment", "purchase"), ApartmentPurchase.class
        );
    }

    public void displayProperties() {
        if (propertyList.isEmpty()) {
            System.out.println("No properties available.");
            return;
        }
        propertyList.forEach(property -> {
            property.display();
            System.out.println("------------------");
        });
    }

    public void addProperty(Scanner scanner) {
        String propertyType = getValidInput(scanner, "What type of property? (house, apartment): ",
                List.of("house", "apartment"));
        String paymentType = getValidInput(scanner, "What payment type? (purchase, rental): ",
                List.of("purchase", "rental"));

        Class<?> propertyClass = typeMap.get(List.of(propertyType, paymentType));
        if (propertyClass == null) {
            System.out.println("Invalid property type combination");
            return;
        }

        try {
            Map<String, String> initArgs = switch (propertyClass.getSimpleName()) {
                case "HouseRental" -> HouseRental.promptInit(scanner);
                case "HousePurchase" -> HousePurchase.promptInit(scanner);
                case "ApartmentRental" -> ApartmentRental.promptInit(scanner);
                case "ApartmentPurchase" -> ApartmentPurchase.promptInit(scanner);
                default -> throw new IllegalStateException("Unexpected property type");
            };

            Property property = createPropertyInstance(propertyClass, initArgs);
            if (property != null) {
                propertyList.add(property);
                System.out.println("Property added successfully!");
            }
        } catch (Exception e) {
            System.out.println("Error creating property: " + e.getMessage());
        }
    }

    private Property createPropertyInstance(Class<?> propertyClass, Map<String, String> initArgs) {
        try {
            return switch (propertyClass.getSimpleName()) {
                case "HouseRental" -> new HouseRental(
                        initArgs.get("squareFeet"),
                        initArgs.get("numBedrooms"),
                        initArgs.get("numBathrooms"),
                        initArgs.get("furnished"),
                        initArgs.get("utilities"),
                        initArgs.get("rent"),
                        initArgs.get("numStories"),
                        initArgs.get("garage"),
                        initArgs.get("fenced")
                );
                case "HousePurchase" -> new HousePurchase(
                        initArgs.get("squareFeet"),
                        initArgs.get("numBedrooms"),
                        initArgs.get("numBathrooms"),
                        initArgs.get("price"),
                        initArgs.get("taxes"),
                        initArgs.get("numStories"),
                        initArgs.get("garage"),
                        initArgs.get("fenced")
                );
                case "ApartmentRental" -> new ApartmentRental(
                        initArgs.get("squareFeet"),
                        initArgs.get("numBedrooms"),
                        initArgs.get("numBathrooms"),
                        initArgs.get("furnished"),
                        initArgs.get("utilities"),
                        initArgs.get("rent"),
                        initArgs.get("balcony"),
                        initArgs.get("laundry")
                );
                case "ApartmentPurchase" -> new ApartmentPurchase(
                        initArgs.get("squareFeet"),
                        initArgs.get("numBedrooms"),
                        initArgs.get("numBathrooms"),
                        initArgs.get("price"),
                        initArgs.get("taxes"),
                        initArgs.get("balcony"),
                        initArgs.get("laundry")
                );
                default -> null;
            };
        } catch (Exception e) {
            System.out.println("Error creating property instance: " + e.getMessage());
            return null;
        }
    }

    private String getValidInput(Scanner scanner, String prompt, List<String> validOptions) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim().toLowerCase();
        } while (!validOptions.contains(input));
        return input;
    }

    public List<Property> getPropertyList() {
        return Collections.unmodifiableList(propertyList);
    }
}