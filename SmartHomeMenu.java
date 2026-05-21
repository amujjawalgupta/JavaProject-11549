import java.util.Scanner;

// Base Class
class Device {
    String name;
    boolean status;

    Device(String name) {
        this.name = name;
        this.status = false;
    }

    void turnOn() {
        status = true;
        System.out.println(name + " is now ON");
    }

    void turnOff() {
        status = false;
        System.out.println(name + " is now OFF");
    }
}

// Light Class
class Light extends Device {
    Light(String name) {
        super(name);
    }
}

// Fan Class
class Fan extends Device {
    Fan(String name) {
        super(name);
    }
}

// Main Class
public class SmartHomeMenu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Light light = new Light("Living Room Light");
        Fan fan = new Fan("Fan");

        int choice;

        do {
            System.out.println("\n--- Smart Home Menu ---");
            System.out.println("1. Turn ON Light");
            System.out.println("2. Turn OFF Light");
            System.out.println("3. Turn ON Fan");
            System.out.println("4. Turn OFF Fan");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Turning ON Light...");
                    light.turnOn();
                    break;

                case 2:
                    System.out.println("Turning OFF Light...");
                    light.turnOff();
                    break;

                case 3:
                    System.out.println("Turning ON Fan...");
                    fan.turnOn();
                    break;

                case 4:
                    System.out.println("Turning OFF Fan...");
                    fan.turnOff();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);

        sc.close();
    }
}
