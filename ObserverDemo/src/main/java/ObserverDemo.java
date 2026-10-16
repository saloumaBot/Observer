import java.util.ArrayList;
import java.util.List;

public class ObserverDemo {
    public static void main(String[] args) {
        observerLouage louage = new observerLouage("Tunis", 5);

        louage.addObserver(new observerPassenger("Ali"));
        louage.addObserver(new observerPassenger("Sami"));
        louage.addObserver(new observerPassenger("Mouna"));

        // observerLouage fills up one by one → each add triggers notify
        louage.addobserverPassenger();  // → notify: "Not full, 4 seats left"
        sleep(500);
        louage.addobserverPassenger();  // → notify: "Not full, 3 seats left"
        sleep(500);
        louage.addobserverPassenger();  // → notify: "Not full, 2 seats left"
        sleep(500);
        louage.addobserverPassenger();  // → notify: "Not full, 1 seat left"
        sleep(500);
        louage.addobserverPassenger();  // → notify: "FULL — Leaving!"

        sleep(500);
        System.out.println("\n[End of observer demo]");
    }

    static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }
}

// Observer interface — like GeeksforGeeks
interface Observer {
    void update(String message);
}

// Subject interface — like GeeksforGeeks
interface Subject {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}

// ConcreteSubject
class observerLouage implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private int count = 0;
    private final int capacity;
    private String destination;

    public observerLouage(String destination, int capacity) {
        this.destination = destination;
        this.capacity = capacity;
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
        System.out.println("  [observerLouage] observer registered");
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        String message;
        if (count >= capacity) {
            message = "FULL — Leaving to " + destination;
        } else {
            message = "Not full yet! Seats left: " + (capacity - count);
        }
        for (Observer o : observers) {
            o.update(message);
        }
    }

    // State change → automatic notify
    public void addobserverPassenger() {
        count++;
        System.out.println("\n  [observerLouage] observerPassenger added\t Count = " + count + "/" + capacity);
        notifyObservers();
    }
}

// ConcreteObserver
class observerPassenger implements Observer {
    private String name;

    public observerPassenger(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println("  " + name + " received → " + message);
    }
}