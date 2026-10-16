public class NaiveDemo {
    public static void main(String[] args) {
        Louage louage = new Louage("Tunis");

        Passenger ali = new Passenger("Ali", louage);
        Passenger sami = new Passenger("Sami", louage);
        Passenger mouna = new Passenger("Mouna", louage);

        ali.start();
        sami.start();
        mouna.start();

        // Louage fills up one by one
        louage.addPassenger();
        sleep(500);
        louage.addPassenger();
        sleep(500);
        louage.addPassenger();
        sleep(500);
        louage.addPassenger();
        sleep(500);
        louage.addPassenger(); // full

        sleep(1500);
        System.out.println("\n[End of naive demo]");
        System.exit(0);
    }

    static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }
}

class Louage {
    private int count = 0;
    private final int capacity = 5;
    private String destination;

    public Louage(String destination) {
        this.destination = destination;
    }

    public void addPassenger() {
        count++;
        System.out.println("  [Louage] Passenger added. Count = " + count + "/" + capacity);
    }

    // Called every time a passenger asks
    public String ask() {
        if (count >= capacity) {
            return "Full! Leaving to " + destination;
        } else {
            return "Not leaving yet. Seats left: " + (capacity - count);
        }
    }
}

class Passenger extends Thread {
    private String name;
    private Louage louage;

    public Passenger(String name, Louage louage) {
        this.name = name;
        this.louage = louage;
    }

    @Override
    public void run() {
        while (true) {
            String answer = louage.ask();      // ← passenger asks
            System.out.println("  " + name + " asks → " + answer);

            if (answer.startsWith("Full")) {
                System.out.println("  " + name + " boards.");
                break;
            }

            try { Thread.sleep(800); } catch (InterruptedException e) { break; }
        }
    }
}