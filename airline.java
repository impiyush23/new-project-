import java.sql.*;
import java.util.Scanner;

public class AirlineReservationSystem {
    private static final String DB_URL = "jdbc:sqlite:airline.db";

    public static void main(String[] args) {
        createTable();
        
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nAirline Reservation System");
            System.out.println("1. Add Reservation");
            System.out.println("2. View Reservations");
            System.out.println("3. Update Reservation");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    addReservation(scanner);
                    break;
                case 2:
                    viewReservations();
                    break;
                case 3:
                    updateReservation(scanner);
                    break;
                case 4:
                    cancelReservation(scanner);
                    break;
                case 5:
                    System.out.println("Thank you for using the Airline Reservation System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS reservations (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "passenger_name TEXT NOT NULL," +
                     "flight_number TEXT NOT NULL," +
                     "seat_number TEXT NOT NULL)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addReservation(Scanner scanner) {
        System.out.print("Enter passenger name: ");
        String name = scanner.nextLine();
        System.out.print("Enter flight number: ");
        String flightNumber = scanner.nextLine();
        System.out.print("Enter seat number: ");
        String seatNumber = scanner.nextLine();

        String sql = "INSERT INTO reservations (passenger_name, flight_number, seat_number) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, flightNumber);
            pstmt.setString(3, seatNumber);
            pstmt.executeUpdate();
            System.out.println("Reservation added successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewReservations() {
        String sql = "SELECT * FROM reservations";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" + 
                                   rs.getString("passenger_name") + "\t" +
                                   rs.getString("flight_number") + "\t" +
                                   rs.getString("seat_number"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateReservation(Scanner scanner) {
        System.out.print("Enter reservation ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter new seat number: ");
        String newSeatNumber = scanner.nextLine();

        String sql = "UPDATE reservations SET seat_number = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newSeatNumber);
            pstmt.setInt(2, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Reservation updated successfully!");
            } else {
                System.out.println("Reservation not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void cancelReservation(Scanner scanner) {
        System.out.print("Enter reservation ID to cancel: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM reservations WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Reservation cancelled successfully!");
            } else {
                System.out.println("Reservation not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

