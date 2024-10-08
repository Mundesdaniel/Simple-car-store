package w7.d1;

import java.util.ArrayList;
import java.util.Scanner;

public class AbstractCar {
    public static void main(String[] args) {
        Dealer dealer = new Dealer();
        dealer.dealerMenu();
    }
}

class Dealer {
    private ArrayList<Car> cars;
    private Scanner scanner;

    public Dealer() {
        cars = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void dealerMenu() {
        while (true) {
            System.out.println("\nDealer Menu:");
            System.out.println("1. Show all cars");
            System.out.println("2. Add a car");
            System.out.println("3. Sell a car");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

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
                    System.out.println("Exiting...");
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
        scanner.nextLine(); // Consume newline

        System.out.print("Is it a Four-Door or Two-Door car? (4/2): ");
        int type = scanner.nextInt();
        scanner.nextLine(); // Consume newline

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

// Car interface
interface Car {
    void run();
    void accident();
    void repair();
    void stop();
    double sell();
}

// Abstract class for FourDoorCar
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
        return 12000 + ((year == 0 ? 0.001 : year / (mileage == 0 ? 0.0001 : mileage)) * power);
    }
}

abstract class TwoDoorToyota extends TwoDoorCar {
    String miniChargerModel;

    public TwoDoorToyota(String carName) {
        super(carName, 980, 300);
        this.miniChargerModel = "SM-4k-2022";
    }

    @Override
    public String toString() {
        return super.toString() + "\n\tMini Charger Model: " + miniChargerModel;
    }

    @Override
    public double sell() {
        return super.sell() * 1.5;
    }
}
