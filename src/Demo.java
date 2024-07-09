import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.Scanner;
public class Demo {
    private static final String URL = "jdbc:mysql://localhost:3306/DEPARTMENT";
    private static final String USER = "";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\n1. Department Operations");
                System.out.println("2. Department Locations Operations");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        departmentOperations(conn, scanner);
                        break;
                    case 2:
                        deptLocationsOperations(conn, scanner);
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void departmentOperations(Connection conn, Scanner scanner) throws SQLException {
        while (true) {
            System.out.println("\n1. Insert Department\n2. Update Department\n3. Delete Department\n4. View Departments\n5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    insertDepartment(conn, scanner);
                    break;
                case 2:
                    updateDepartment(conn, scanner);
                    break;
                case 3:
                    deleteDepartment(conn, scanner);
                    break;
                case 4:
                    viewDepartments(conn);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void deptLocationsOperations(Connection conn, Scanner scanner) throws SQLException {
        while (true) {
            System.out.println("\n1. Insert Department Location\n2. Update Department Location\n3. Delete Department Location\n4. View Department Locations\n5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    insertDeptLocation(conn, scanner);
                    break;
                case 2:
                    updateDeptLocation(conn, scanner);
                    break;
                case 3:
                    deleteDeptLocation(conn, scanner);
                    break;
                case 4:
                    viewDeptLocations(conn);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void insertDepartment(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Department Name: ");
        String dname = scanner.nextLine();
        System.out.print("Enter Department Number: ");
        int dnumber = scanner.nextInt();
        System.out.print("Enter Manager SSN: ");
        int mgrSsn = scanner.nextInt();
        System.out.print("Enter Manager Start Date (YYYY-MM-DD): ");
        String mgrStartDate = scanner.next();

        String sql = "INSERT INTO DEPARTMENT (Dname, Dnumber, Mgr_ssn, Mgr_start_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dname);
            pstmt.setInt(2, dnumber);
            pstmt.setInt(3, mgrSsn);
            pstmt.setDate(4, Date.valueOf(mgrStartDate));
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
    }

    private static void updateDepartment(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Department Number to update: ");
        int dnumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new Department Name: ");
        String dname = scanner.nextLine();

        String sql = "UPDATE DEPARTMENT SET Dname = ? WHERE Dnumber = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dname);
            pstmt.setInt(2, dnumber);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
        }
    }

    private static void deleteDepartment(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Department Number to delete: ");
        int dnumber = scanner.nextInt();

        String sql = "DELETE FROM DEPARTMENT WHERE Dnumber = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dnumber);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        }
    }

    private static void viewDepartments(Connection conn) throws SQLException {
        String sql = "SELECT * FROM DEPARTMENT";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("Department: %s, Number: %d, Manager SSN: %d, Manager Start Date: %s%n",
                        rs.getString("Dname"),
                        rs.getInt("Dnumber"),
                        rs.getInt("Mgr_ssn"),
                        rs.getDate("Mgr_start_date"));
            }
        }
    }

    private static void insertDeptLocation(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Department Number: ");
        int dnumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Department Location: ");
        String dlocation = scanner.nextLine();

        String sql = "INSERT INTO DEPT_LOCATIONS (Dnumber, Dlocation) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dnumber);
            pstmt.setString(2, dlocation);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        }
    }

    private static void updateDeptLocation(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Department Number: ");
        int dnumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter current Department Location: ");
        String currentLocation = scanner.nextLine();
        System.out.print("Enter new Department Location: ");
        String newLocation = scanner.nextLine();

        String sql = "UPDATE DEPT_LOCATIONS SET Dlocation = ? WHERE Dnumber = ? AND Dlocation = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newLocation);
            pstmt.setInt(2, dnumber);
            pstmt.setString(3, currentLocation);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
        }
    }

    private static void deleteDeptLocation(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Department Number: ");
        int dnumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Department Location to delete: ");
        String dlocation = scanner.nextLine();

        String sql = "DELETE FROM DEPT_LOCATIONS WHERE Dnumber = ? AND Dlocation = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dnumber);
            pstmt.setString(2, dlocation);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        }
    }

    private static void viewDeptLocations(Connection conn) throws SQLException {
        String sql = "SELECT * FROM DEPT_LOCATIONS";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("Department Number: %d, Location: %s%n",
                        rs.getInt("Dnumber"),
                        rs.getString("Dlocation"));
            }
        }
    }
}