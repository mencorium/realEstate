import java.util.*;

public class Purchase extends Property {
    private final String price;
    private final String taxes;

    // Fixed constructor parameter name (was Price instead of taxes)
    Purchase(String squareFeet, String beds, String bath, String price, String taxes) {
        super(squareFeet, beds, bath);
        this.price = price;
        this.taxes = taxes;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("PURCHASE DETAILS");
        System.out.println("selling price: " + this.price);
        System.out.println("estimated taxes: " + this.taxes);
    }

    public static Map<String, String> promptInit(Scanner scanner) {
        Map<String, String> purchaseDetails = new HashMap<>();
        System.out.print("What is the selling price? ");
        purchaseDetails.put("price", scanner.nextLine());
        System.out.print("What are the estimated taxes? ");
        purchaseDetails.put("taxes", scanner.nextLine());
        return purchaseDetails;
    }
}

class Rental extends Property {
    private final String furnished;
    private final String utilities;
    private final String rent;

    Rental(String squareFeet, String beds, String bath, String furnished, String utilities, String rent) {
        super(squareFeet, beds, bath);
        this.furnished = furnished;
        this.utilities = utilities;
        this.rent = rent;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("RENTAL DETAILS");
        System.out.println("rent: " + this.rent);
        System.out.println("estimated utilities: " + this.utilities);
        System.out.println("furnished: " + this.furnished);
    }

    public static Map<String, String> promptInit(Scanner scanner) {
        Map<String, String> rentalDetails = new HashMap<>();
        System.out.print("What is the monthly rent? ");
        rentalDetails.put("rent", scanner.nextLine());
        System.out.print("What are the estimated utilities? ");
        rentalDetails.put("utilities", scanner.nextLine());

        String furnished = "";
        List<String> validFurnished = List.of("yes", "no");
        while (!validFurnished.contains(furnished.toLowerCase())) {
            System.out.print("Is the property furnished? (yes, no): ");
            furnished = scanner.nextLine();
        }
        rentalDetails.put("furnished", furnished);
        return rentalDetails;
    }
}

class HouseRental extends Rental {
    HouseRental(String squareFeet, String beds, String bath, String furnished, String utilities, String rent,
                String numStories, String garage, String fenced) {
        super(squareFeet, beds, bath, furnished, utilities, rent);
    }

    public static Map<String, String> promptInit(Scanner scanner) {
        Map<String, String> init = House.promptInit(scanner);
        init.putAll(Rental.promptInit(scanner));
        return init;
    }
}

class ApartmentRental extends Rental {
    ApartmentRental(String squareFeet, String beds, String bath, String furnished, String utilities, String rent,
                    String balcony, String laundry) {
        super(squareFeet, beds, bath, furnished, utilities, rent);
    }

    public static Map<String, String> promptInit(Scanner scanner) {
        Map<String, String> init = Apartment.promptInit(scanner);
        init.putAll(Rental.promptInit(scanner));
        return init;
    }
}

class ApartmentPurchase extends Purchase {
    ApartmentPurchase(String squareFeet, String beds, String bath, String price, String taxes,
                      String balcony, String laundry) {
        super(squareFeet, beds, bath, price, taxes);
    }

    public static Map<String, String> promptInit(Scanner scanner) {
        Map<String, String> init = Apartment.promptInit(scanner);
        init.putAll(Purchase.promptInit(scanner));
        return init;
    }
}

class HousePurchase extends Purchase {
    HousePurchase(String squareFeet, String beds, String bath, String price, String taxes,
                  String numStories, String garage, String fenced) {
        super(squareFeet, beds, bath, price, taxes);
    }

    public static Map<String, String> promptInit(Scanner scanner) {
        Map<String, String> init = House.promptInit(scanner);
        init.putAll(Purchase.promptInit(scanner));
        return init;
    }
}

