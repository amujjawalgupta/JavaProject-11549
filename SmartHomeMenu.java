import java.util.Scanner;

// Base Class
class Device {
    protected String name;
    protected boolean status;

    public Device(String name) {
        this.name = name;
        this.status = false;
    }

    public void turnOn() {
        if (status) {
            System.out.println(name + " is already ON.");
        } else {
            status = true;
            System.out.println(name + " is now ON.");
        }
    }

    public void turnOff() {
        if (!status) {
            System.out.println(name + " is already OFF.");
        } else {
            status = false;
            System.out.println(name + " is now OFF.");
        }
    }

    public void showStatus() {
        System.out.println(name + " : " + (status ? "ON" : "OFF"));
    }
}

// Light Class
class Light extends Device {

    public Light(String name) {
        super(name);
    }

    @Override
    public void turnOn() {
        super.turnOn();
        if (status) {
            System.out.println("💡 Light is shining brightly.");
        }
    }
}

// Fan Class
class Fan extends Device {

    public Fan(String name) {
        super(name);
    }

    @Override
    public void turnOn() {
        super.turnOn();
        if (status) {
            System.out.println("🌀 Fan is spinning.");
        }
    }
}

// Main Class
public class SmartHomeMenu {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Light light = new Light("Living Room Light");
        Fan fan = new Fan("Bedroom Fan");

        int choice = 0;

        do {
            System.out.println("\n========== SMART HOME ==========");
            System.out.println("1. Turn ON Light");
            System.out.println("2. Turn OFF Light");
            System.out.println("3. Turn ON Fan");
            System.out.println("4. Turn OFF Fan");
            System.out.println("5. Show Device Status");
            System.out.println("6. Exit");
            System.out.print("Enter Choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("❌ Invalid Input! Please enter a number.");
                sc.next(); // Clear invalid input
                continue;
            }

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    light.turnOn();
                    break;

                case 2:
                    light.turnOff();
                    break;

                case 3:
                    fan.turnOn();
                    break;

                case 4:
                    fan.turnOff();
                    break;

                case 5:
                    System.out.println("\n----- DEVICE STATUS -----");
                    light.showStatus();
                    fan.showStatus();
                    break;

                case 6:
                    System.out.println("✅ Thank you for using Smart Home System.");
                    break;

                default:
                    System.out.println("❌ Invalid Choice! Please select 1-6.");
            }

        } while (choice != 6);

        sc.close();
    }
}
