import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Flight {
    String flightNumber;
    String source;
    String destination;
    int totalSeats;
    int availableSeats;

    // Constructor to initialize flight details
    public Flight(String flightNumber, String source, String destination, int totalSeats) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats; // Initially, all seats are available
    }

    // Method to display flight details
    public String getFlightInfo() {
        return "Flight Number: " + flightNumber + "\nSource: " + source + "\nDestination: " + destination +
                "\nTotal Seats: " + totalSeats + "\nAvailable Seats: " + availableSeats + "\n";
    }

    // Method to book a seat
    public boolean bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
            return true;
        }
        return false;
    }

    // Method to cancel a booking
    public void cancelBooking() {
        if (availableSeats < totalSeats) {
            availableSeats++;
        }
    }
}

public class AirlineReservationSystemGUI extends JFrame implements ActionListener {

    // GUI components
    private JTextArea flightDetailsArea;
    private JTextField txtFlightNumber, txtSource, txtDestination;
    private JButton btnViewFlights, btnBookFlight, btnCancelBooking, btnExit;
    private Flight[] flights = new Flight[3];
    private JTextArea myBookingsArea;

    // Constructor to setup the GUI
    public AirlineReservationSystemGUI() {
        // Initialize flights
        flights[0] = new Flight("AA101", "New York", "Los Angeles", 100);
        flights[1] = new Flight("AA102", "Chicago", "San Francisco", 150);
        flights[2] = new Flight("AA103", "Miami", "Dallas", 120);

        // Setting up JFrame properties
        setTitle("Airline Reservation System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for flight info
        JPanel flightPanel = new JPanel();
        flightPanel.setLayout(new BoxLayout(flightPanel, BoxLayout.Y_AXIS));

        // Text area to display flight details
        flightDetailsArea = new JTextArea(10, 50);
        flightDetailsArea.setEditable(false);
        flightPanel.add(new JScrollPane(flightDetailsArea));

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Buttons for actions
        btnViewFlights = new JButton("View Flights");
        btnBookFlight = new JButton("Book Flight");
        btnCancelBooking = new JButton("Cancel Booking");
        btnExit = new JButton("Exit");

        // Add action listeners to buttons
        btnViewFlights.addActionListener(this);
        btnBookFlight.addActionListener(this);
        btnCancelBooking.addActionListener(this);
        btnExit.addActionListener(this);

        // Add buttons to the panel
        buttonPanel.add(btnViewFlights);
        buttonPanel.add(btnBookFlight);
        buttonPanel.add(btnCancelBooking);
        buttonPanel.add(btnExit);

        // Panel for user bookings
        myBookingsArea = new JTextArea(5, 50);
        myBookingsArea.setEditable(false);
        JPanel myBookingsPanel = new JPanel();
        myBookingsPanel.setLayout(new BorderLayout());
        myBookingsPanel.add(new JScrollPane(myBookingsArea), BorderLayout.CENTER);

        // Add panels to the frame
        add(flightPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(myBookingsPanel, BorderLayout.SOUTH);
    }

    // Display all available flights
    private void displayAvailableFlights() {
        flightDetailsArea.setText(""); // Clear current text
        for (Flight flight : flights) {
            flightDetailsArea.append(flight.getFlightInfo());
            flightDetailsArea.append("--------------------------------------\n");
        }
    }

    // Book a flight
    private void bookFlight() {
        String flightNumber = JOptionPane.showInputDialog(this, "Enter Flight Number to Book:");

        for (Flight flight : flights) {
            if (flight.flightNumber.equals(flightNumber)) {
                if (flight.bookSeat()) {
                    JOptionPane.showMessageDialog(this, "Booking successful!");
                    displayAvailableFlights();
                    return;
                } else {
                    JOptionPane.showMessageDialog(this, "Sorry, no available seats on this flight.");
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(this, "Flight not found.");
    }

    // Cancel a booking
    private void cancelBooking() {
        String flightNumber = JOptionPane.showInputDialog(this, "Enter Flight Number to Cancel:");

        for (Flight flight : flights) {
            if (flight.flightNumber.equals(flightNumber)) {
                flight.cancelBooking();
                JOptionPane.showMessageDialog(this, "Booking canceled successfully!");
                displayAvailableFlights();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Flight not found.");
    }

    // View current bookings
    private void viewMyBookings() {
        myBookingsArea.setText(""); // Clear current text
        for (Flight flight : flights) {
            if (flight.totalSeats > flight.availableSeats) {
                myBookingsArea.append("Flight Number: " + flight.flightNumber + "\n");
                myBookingsArea.append("Source: " + flight.source + "\n");
                myBookingsArea.append("Destination: " + flight.destination + "\n");
                myBookingsArea.append("Seats Booked: " + (flight.totalSeats - flight.availableSeats) + "\n");
                myBookingsArea.append("--------------------------------------\n");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnViewFlights) {
            displayAvailableFlights();
        } else if (e.getSource() == btnBookFlight) {
            bookFlight();
        } else if (e.getSource() == btnCancelBooking) {
            cancelBooking();
        } else if (e.getSource() == btnExit) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        // Run the GUI application
        SwingUtilities.invokeLater(() -> {
            new AirlineReservationSystemGUI().setVisible(true);
        });
    }
}
