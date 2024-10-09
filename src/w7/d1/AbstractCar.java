package w7.d1;

import java.util.ArrayList;
import java.util.Scanner;

public class AbstractCar {
    public static void main(String[] args) {
        Dealers dealers = new Dealers();
        dealers.dealersMenu(); // Run the main Dealers menu
    }
}

class Dealers {
    private ArrayList<Dealer> dealerList;
    private Scanner scanner;

    public Dealers() {
        dealerList = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void dealersMenu() {
        while (true) {
            System.out.println("\nDealers Menu:");
            System.out.println("1. View Existing Dealers");
            System.out.println("2. Create New Dealer");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewDealers();
                    break;
                case 2:
                    createNewDealer();
                    break;
                case 3:
                    System.out.println("Exiting Dealers Menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewDealers() {
        if (dealerList.isEmpty()) {
            System.out.println("No dealers available.");
        } else {
            System.out.println("\nList of Dealers:");
            for (int i = 0; i < dealerList.size(); i++) {
                System.out.println((i + 1) + ". " + dealerList.get(i).getName());
            }

            System.out.print("Select a dealer (enter number) to access their menu or 0 to return: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice > 0 && choice <= dealerList.size()) {
                dealerList.get(choice - 1).dealerMenu();
            } else if (choice == 0) {
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private void createNewDealer() {
        System.out.print("Enter the name of the new dealer: ");
        String name = scanner.nextLine();
        dealerList.add(new Dealer(name));
        System.out.println("Dealer " + name + " added successfully.");
    }
}

class Dealer {
    private String name;
    private ArrayList<Car> cars;
    private Scanner scanner;

    public Dealer(String name) {
        this.name = name;
        cars = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public String getName() {
        return name;
    }

    public void dealerMenu() {
        while (true) {
            System.out.println("\nDealer Menu for " + name + ":");
            System.out.println("1. Show all cars");
            System.out.println("2. Add a car");
            System.out.println("3. Sell a car");
            System.out.println("4. Create a new deal");
            System.out.println("5. Exit to Dealers Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showAllCars();
                    break;
                case 2:
                    addCar();
                    break;
                case 3:
                    sellCar();
                    break;
                case 4:
                    createNewDeal();
                    break;
                case 5:
                    System.out.println("Exiting " + name + "'s Dealer Menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showAllCars() {
        if (cars.isEmpty()) {
            System.out.println("No cars available.");
        } else {
            for (Car car : cars) {
                System.out.println(car);
            }
        }
    }

    private void addCar() {
        System.out.print("Enter car name: ");
        String carName = scanner.nextLine();
        System.out.print("Enter car year: ");
        int year = scanner.nextInt();
        System.out.print("Enter car mileage: ");
        double mileage = scanner.nextDouble();
        System.out.print("Enter car power: ");
        double power = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Is it a Four-Door or Two-Door car? (4/2): ");
        int type = scanner.nextInt();
        scanner.nextLine();

        Car newCar;
        if (type == 4) {
            newCar = new FourDoorToyotaTester(carName, year, mileage, power);
        } else {
            newCar = new TwoDoorToyotaTester(carName, year, mileage, power);
        }

        cars.add(newCar);
        System.out.println("Car added successfully: " + newCar);
    }

    private void sellCar() {
        System.out.print("Enter the name of the car to sell: ");
        String carName = scanner.nextLine();
        Car carToSell = null;

        for (Car car : cars) {
            if (car.toString().contains(carName)) {
                carToSell = car;
                break;
            }
        }

        if (carToSell != null) {
            System.out.println("Selling " + carToSell + " for $" + carToSell.sell());
            cars.remove(carToSell);
            System.out.println("Car sold successfully.");
        } else {
            System.out.println("Car not found.");
        }
    }

    private void createNewDeal() {
        System.out.print("Enter deal description: ");
        String dealDescription = scanner.nextLine();
        System.out.print("Enter discount percentage (0-100): ");
        double discount = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("New deal created: " + dealDescription + " with " + discount + "% discount.");
    }
}

interface Car {
    void run();
    void accident();
    void repair();
    void stop();
    double sell();
}

class FourDoorToyotaTester extends FourDoorToyota {
    public FourDoorToyotaTester(String carName, int year, double mileage, double power) {
        super(carName);
        this.year = year;
        this.mileage = mileage;
        this.power = power;
    }

    @Override
    public String toString() {
        return "FourDoorToyotaTester: " + super.toString();
    }
}

class TwoDoorToyotaTester extends TwoDoorToyota {
    public TwoDoorToyotaTester(String carName, int year, double mileage, double power) {
        super(carName);
        this.year = year;
        this.mileage = mileage;
        this.power = power;
    }

    @Override
    public String toString() {
        return "TwoDoorToyotaTester: " + super.toString();
    }
}

abstract class FourDoorCar implements Car {
    String carName;
    int numOfDoors;
    double maxSpeed;
    double currentSpeed;
    boolean isWorking;
    int year;
    double mileage;
    double power;

    public FourDoorCar(String carName, double carMaxSpeed) {
        this.carName = carName;
        this.numOfDoors = 4;
        this.maxSpeed = carMaxSpeed;
        this.currentSpeed = 0;
        this.isWorking = true;
        this.year = 0;
        this.mileage = 0;
        this.power = 0;
    }

    @Override
    public void run() {
        if (isWorking) {
            System.out.println(carName + " is running");
            currentSpeed = maxSpeed;
        } else {
            System.out.println(carName + " can't run, needs repairs");
        }
    }

    @Override
    public void stop() {
        System.out.println(carName + " is stopping");
        currentSpeed = 0;
    }

    @Override
    public void repair() {
        System.out.println(carName + " is being repaired");
        isWorking = true;
    }

    @Override
    public void accident() {
        System.out.println(carName + " got into an accident");
        stop();
        isWorking = false;
    }

    @Override
    public String toString() {
        return "Car name: " + carName + ", number of doors: " + numOfDoors +
                "\n\tCar max speed: " + maxSpeed + ", Car current speed: " + currentSpeed +
                "\n\tIs car OK: " + (isWorking ? "yes" : "no") +
                "\n\tYear: " + year + ", Mileage: " + mileage + ", Power: " + power;
    }

    @Override
    public double sell() {
        return 25000 + ((year == 0 ? 0.001 : year / (mileage == 0 ? 0.0001 : mileage)) * power);
    }
}

abstract class FourDoorToyota extends FourDoorCar {
    String smartTVModel;

    public FourDoorToyota(String carName) {
        super(carName, 1700);
        this.smartTVModel = "SM-4k-2022";
    }

    @Override
    public String toString() {
        return super.toString() + "\n\tSmart TV Model: " + smartTVModel;
    }

    @Override
    public double sell() {
        return super.sell() * 0.5;
    }
}

abstract class TwoDoorCar implements Car {
    String carName;
    int numOfDoors;
    double maxSpeed;
    double currentSpeed;
    boolean isWorking;
    int year;
    double mileage;
    double power;

    public TwoDoorCar(String carName, double carMaxSpeed, double power) {
        this.carName = carName;
        this.numOfDoors = 2;
        this.maxSpeed = carMaxSpeed;
        this.currentSpeed = 0;
        this.isWorking = true;
        this.year = 0;
        this.mileage = 0;
        this.power = power;
    }

    @Override
    public void run() {
        if (isWorking) {
            System.out.println(carName + " is running");
            currentSpeed = maxSpeed;
        } else {
            System.out.println(carName + " can't run, needs repairs");
        }
    }

    @Override
    public void stop() {
        System.out.println(carName + " is stopping");
        currentSpeed = 0;
    }

    @Override
    public void repair() {
        System.out.println(carName + " is being repaired");
        isWorking = true;
    }

    @Override
    public void accident() {
        System.out.println(carName + " got into an accident");
        stop();
        isWorking = false;
    }

    @Override
    public String toString() {
        return "Car name: " + carName + ", number of doors: " + numOfDoors +
                "\n\tCar max speed: " + maxSpeed + ", Car current speed: " + currentSpeed +
                "\n\tIs car OK: " + (isWorking ? "yes" : "no") +
                "\n\tYear: " + year + ", Mileage: " + mileage + ", Power: " + power;
    }

    @Override
    public double sell() {
        return 15000 + ((year == 0 ? 0.001 : year / (mileage == 0 ? 0.0001 : mileage)) * power);
    }
}

abstract class TwoDoorToyota extends TwoDoorCar {
    public TwoDoorToyota(String carName) {
        super(carName, 1600, 200);
    }

    @Override
    public double sell() {
        return super.sell() * 0.5;
    }
}
