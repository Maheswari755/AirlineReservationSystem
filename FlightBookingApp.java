import java.util.ArrayList;
import java.util.Scanner;

/* Represents a traveler */
class Traveler {
    private String fullName;
    private String contactEmail;

    public Traveler(String fullName, String contactEmail) {
        this.fullName = fullName;
        this.contactEmail = contactEmail;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    @Override
    public String toString() {
        return "Traveler: " + fullName + " | Email: " + contactEmail;
    }
}

/* Represents an airline flight */
class AirlineFlight {
    private String code;
    private String fromCity;
    private String toCity;
    private int totalSeats;
    private int seatsTaken;

    public AirlineFlight(String code, String fromCity, String toCity, int totalSeats) {
        this.code = code;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.totalSeats = totalSeats;
        this.seatsTaken = 0;
    }

    public String getCode() {
        return code;
    }

    public int seatsLeft() {
        return totalSeats - seatsTaken;
    }

    public boolean reserveSeat() {
        if (seatsTaken < totalSeats) {
            seatsTaken++;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return code + " : " + fromCity + " → " + toCity +
               " | Seats Left: " + seatsLeft();
    }
}

/* Represents a reservation */
class Reservation {
    private Traveler traveler;
    private AirlineFlight flight;
    private String referenceNumber;

    public Reservation(Traveler traveler, AirlineFlight flight, String referenceNumber) {
        this.traveler = traveler;
        this.flight = flight;
        this.referenceNumber = referenceNumber;
    }

    @Override
    public String toString() {
        return "Ref#" + referenceNumber + " | " +
               flight.toString() + " | " +
               traveler.toString();
    }
}

/* Main system controller */
public class AirlineReservationSystem {
    private ArrayList<AirlineFlight> flightList = new ArrayList<>();
    private ArrayList<Reservation> reservationList = new ArrayList<>();
    private Scanner input = new Scanner(System.in);

    public AirlineReservationSystem() {
        flightList.add(new AirlineFlight("A301", "Dubai", "Tokyo", 80));
        flightList.add(new AirlineFlight("B404", "Tokyo", "Sydney", 60));
    }

    public void runSystem() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Airline Reservation Menu ===");
            System.out.println("1. Display Flights");
            System.out.println("2. Make Reservation");
            System.out.println("3. Show Reservations");
            System.out.println("4. Quit");
            System.out.print("Choose an option: ");

            int option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1:
                    showFlights();
                    break;
                case 2:
                    createReservation();
                    break;
                case 3:
                    listReservations();
                    break;
                case 4:
                    running = false;
                    System.out.println("System closed.");
                    break;
                default:
                    System.out.println("Invalid selection.");
            }
        }
    }

    private void showFlights() {
        System.out.println("\nFlights Available:");
        for (AirlineFlight flight : flightList) {
            System.out.println("- " + flight);
        }
    }

    private void createReservation() {
        System.out.print("Enter flight code: ");
        String code = input.nextLine();

        AirlineFlight chosenFlight = null;
        for (AirlineFlight flight : flightList) {
            if (flight.getCode().equalsIgnoreCase(code)) {
                chosenFlight = flight;
                break;
            }
        }

        if (chosenFlight == null) {
            System.out.println("Flight does not exist.");
            return;
        }

        if (chosenFlight.seatsLeft() == 0) {
            System.out.println("No seats available.");
            return;
        }

        System.out.print("Enter traveler name: ");
        String name = input.nextLine();
        System.out.print("Enter traveler email: ");
        String email = input.nextLine();

        Traveler traveler = new Traveler(name, email);

        if (chosenFlight.reserveSeat()) {
            String ref = "REF-" + (1000 + reservationList.size());
            Reservation reservation = new Reservation(traveler, chosenFlight, ref);
            reservationList.add(reservation);

            System.out.println("Reservation successful!");
            System.out.println(reservation);
        }
    }

    private void listReservations() {
        if (reservationList.isEmpty()) {
            System.out.println("No reservations recorded.");
            return;
        }

        System.out.println("\nReservation Records:");
        for (Reservation res : reservationList) {
            System.out.println("- " + res);
        }
    }

    public static void main(String[] args) {
        new AirlineReservationSystem().runSystem();
    }
}
