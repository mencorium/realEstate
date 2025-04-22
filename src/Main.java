import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean shutdownRequested = false;

    public static void main(String[] args) {
        Agent agent = new Agent();

        try {
            System.out.println("Real Estate Agent System\n========================");

            while (!shutdownRequested) {
                displayMenu(agent);
            }

            System.out.println("System exited successfully.");
        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
        } finally {
            safeCloseScanner();
        }
    }

    private static void displayMenu(Agent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("Agent cannot be null");
        }

        System.out.println("\nMain Menu:");
        System.out.println("1. Add Property");
        System.out.println("2. Display All Properties");
        System.out.println("3. Exit");
        System.out.print("Please enter your choice (1-3): ");

        try {
            if (!scanner.hasNextLine()) {
                shutdownRequested = true;
                return;
            }

            String choice = scanner.nextLine().trim();

            if (choice.isEmpty()) {
                System.out.println("Error: No input detected");
                return;
            }

            switch (choice) {
                case "1":
                    addPropertyFlow(agent);
                    break;
                case "2":
                    displayPropertiesFlow(agent);
                    break;
                case "3":
                    shutdownRequested = true;
                    break;
                default:
                    System.out.println("Invalid input. Please enter 1, 2 or 3.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Input device error - exiting");
            shutdownRequested = true;
        } catch (IllegalStateException e) {
            System.out.println("Scanner error - exiting");
            shutdownRequested = true;
        }
    }

    private static void addPropertyFlow(Agent agent) {
        System.out.println("\nAdding New Property...");
        try {
            agent.addProperty(scanner);
            System.out.println("\nProperty added successfully!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input format: " + e.getMessage());
            scanner.nextLine(); // Clear invalid input
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Please try again.");
            clearScannerBuffer();
        }
    }

    private static void displayPropertiesFlow(Agent agent) {
        if (agent.getPropertyList().isEmpty()) {
            System.out.println("\nNo properties in the system yet.");
        } else {
            System.out.println("\nAll Properties:\n===============");
            agent.displayProperties();
        }
    }

    private static void clearScannerBuffer() {
        try {
            while (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        } catch (IllegalStateException e) {
            System.out.println("Error clearing input buffer");
        }
    }

    private static void safeCloseScanner() {
        try {
            if (scanner != null) {
                scanner.close();
            }
        } catch (IllegalStateException e) {
            System.err.println("Error closing scanner: " + e.getMessage());
        }
    }
}