package dbms;
import java.sql.*;
import java.util.*;

public class DBMS {
    private static final String URL = "jdbc:mysql://localhost:3306/blood_donation";
    private static final String USER = "root";
    private static final String PASS = "your_password";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println(" Connected to database!");
            //insertAllData(con);

            while (true) {
                System.out.println("\n==== BLOOD BANK MANAGEMENT ====");
                System.out.println("1. Insert Data");
                System.out.println("2. View Data");
                System.out.println("3. Update Data");
                System.out.println("4. Delete Data");
                System.out.println("5.run analysis and view report");
                System.out.println("6. Exit");
                System.out.println("----------------------------------");

                int choice = readInt(sc, "Choose option: ");
                switch (choice) {
                    case 1 -> insertData(con, sc);
                    case 2 -> viewData(con, sc);
                    case 3 -> updateData(con, sc);
                    case 4 -> deleteData(con, sc);
                    case 5 ->runQueries(con, sc);
                    case 6 -> {
                        System.out.println(" Exiting...");
                        con.close();
                        System.exit(0);
                    }
                    default -> System.out.println(" Invalid option!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper methods
    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int n = sc.nextInt();
                sc.nextLine();
                return n;
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid number!");
                sc.nextLine();
            }
        }
    }

    private static String readString(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
    
    public static void insertAllData(Connection con) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            System.out.println("Connected to database. Starting inserts...");

            // ---------- HOSPITAL ----------
            String insertHospital = """
                INSERT INTO Hospital (hospital_id, email, name, contact_no, street, pincode, city) VALUES
                (1, 'apollo@gmail.com', 'Apollo Hospital', '9876543210', 'MG Road', '560001', 'Bangalore'),
                (2, 'fortis@gmail.com', 'Fortis Hospital', '9823456789', 'Link Road', '400052', 'Mumbai'),
                (3, 'aiims@gmail.com', 'AIIMS', '9812345678', 'Ansari Nagar', '110029', 'Delhi'),
                (4, 'columbia@gmail.com', 'Columbia Asia', '9898989898', 'Bannerghatta', '560076', 'Bangalore'),
                (5, 'manipal@gmail.com', 'Manipal Hospital', '9876509876', 'Old Airport Rd', '560017', 'Bangalore'),
                (6, 'care@gmail.com', 'Care Hospital', '9811199111', 'Road No.10', '500034', 'Hyderabad'),
                (7, 'kims@gmail.com', 'KIMS', '9700044000', 'Secunderabad', '500003', 'Hyderabad'),
                (8, 'srm@gmail.com', 'SRM Hospital', '9444332211', 'Kattankulathur', '603203', 'Chennai'),
                (9, 'cmc@gmail.com', 'CMC Hospital', '9345645656', 'Vellore', '632004', 'Tamil Nadu'),
                (10, 'apollohyd@gmail.com', 'Apollo Hyderabad', '9700055000', 'Jubilee Hills', '500033', 'Hyderabad'),
                (11, 'lifeline@gmail.com', 'Lifeline Hospital', '9340022001', 'Anna Nagar', '600040', 'Chennai'),
                (12, 'global@gmail.com', 'Global Hospital', '9822002222', 'Perumbakkam', '600100', 'Chennai'),
                (13, 'sahara@gmail.com', 'Sahara Hospital', '9801010101', 'Gomti Nagar', '226010', 'Lucknow'),
                (14, 'apollo_pune@gmail.com', 'Apollo Pune', '9833300000', 'Baner', '411045', 'Pune'),
                (15, 'nanavati@gmail.com', 'Nanavati Hospital', '9777007777', 'Vile Parle', '400056', 'Mumbai'),
                (16, 'medico@gmail.com', 'Medicover Hospital', '9876506506', 'HiTech City', '500081', 'Hyderabad'),
                (17, 'ashwini@gmail.com', 'Ashwini Hospital', '9123456789', 'Erandwane', '411004', 'Pune'),
                (18, 'inlaks@gmail.com', 'Inlaks Hospital', '9000009999', 'Chembur', '400074', 'Mumbai'),
                (19, 'mayo@gmail.com', 'Mayo Hospital', '9500095000', 'M G Road', '560025', 'Bangalore'),
                (20, 'cureplus@gmail.com', 'CurePlus Hospital', '9001190011', 'Indiranagar', '560038', 'Bangalore');
                """;
            stmt.executeUpdate(insertHospital);
            System.out.println("Inserted 20 Hospital rows.");

            // ---------- BLOODBANK ----------
            String insertBloodBank = """
                INSERT INTO BloodBank (bloodbank_id, bag_id, name, location, contact_no, email, capacity) VALUES
                (1, 101, 'Apollo Blood Bank', 'Bangalore', '9887766554', 'apollo.bb@gmail.com', 500),
                (2, 102, 'Fortis Blood Bank', 'Mumbai', '9776655443', 'fortis.bb@gmail.com', 600),
                (3, 103, 'AIIMS Blood Bank', 'Delhi', '9665544332', 'aiims.bb@gmail.com', 800),
                (4, 104, 'KIMS Blood Bank', 'Hyderabad', '9554433221', 'kims.bb@gmail.com', 700),
                (5, 105, 'CMC Blood Bank', 'Vellore', '9443322110', 'cmc.bb@gmail.com', 400),
                (6, 106, 'Global Blood Bank', 'Chennai', '9332211009', 'global.bb@gmail.com', 650),
                (7, 107, 'Manipal Blood Bank', 'Bangalore', '9221100098', 'manipal.bb@gmail.com', 550),
                (8, 108, 'SRM Blood Bank', 'Chennai', '9110098765', 'srm.bb@gmail.com', 500),
                (9, 109, 'Care Blood Bank', 'Hyderabad', '9009988776', 'care.bb@gmail.com', 450),
                (10, 110, 'Nanavati Blood Bank', 'Mumbai', '8998877665', 'nanavati.bb@gmail.com', 700),
                (11, 111, 'Ashwini Blood Bank', 'Pune', '8887766554', 'ashwini.bb@gmail.com', 500),
                (12, 112, 'Apollo Pune Blood Bank', 'Pune', '8776655443', 'apollo.pune.bb@gmail.com', 400),
                (13, 113, 'Mayo Blood Bank', 'Bangalore', '8665544332', 'mayo.bb@gmail.com', 300),
                (14, 114, 'Sahara Blood Bank', 'Lucknow', '8554433221', 'sahara.bb@gmail.com', 550),
                (15, 115, 'CurePlus Blood Bank', 'Bangalore', '8443322110', 'cureplus.bb@gmail.com', 250),
                (16, 116, 'Medicover Blood Bank', 'Hyderabad', '8332211009', 'medicover.bb@gmail.com', 650),
                (17, 117, 'Lifeline Blood Bank', 'Chennai', '8221100098', 'lifeline.bb@gmail.com', 700),
                (18, 118, 'Global Pune Blood Bank', 'Pune', '8110098765', 'global.pune.bb@gmail.com', 450),
                (19, 119, 'Inlaks Blood Bank', 'Mumbai', '8009988776', 'inlaks.bb@gmail.com', 600),
                (20, 120, 'Fortis Hyd Blood Bank', 'Hyderabad', '7998877665', 'fortis.hyd.bb@gmail.com', 800);
                """;
            stmt.executeUpdate(insertBloodBank);
            System.out.println("Inserted 20 BloodBank rows.");

            // ---------- DONOR ----------
            String insertDonor = """
                INSERT INTO Donor (donor_id, donor_name, gender, blood_group, contact_no, last_dn, age, email, street, city, pincode, house_no) VALUES
                (1, 'Rahul Sharma', 'M', 'B+', '9998877665', '2025-02-02', 28, 'rahul@gmail.com', 'MG Road', 'Bangalore', '560001', 10),
                (2, 'Neha Verma', 'F', 'O+', '8887766554', '2025-01-15', 25, 'neha@gmail.com', 'Link Road', 'Mumbai', '400052', 22),
                (3, 'Amit Kumar', 'M', 'A+', '7776655443', '2024-12-10', 32, 'amit@gmail.com', 'DLF Phase 2', 'Delhi', '110029', 56),
                (4, 'Priya Nair', 'F', 'B-', '9665544332', '2025-03-05', 29, 'priya@gmail.com', 'HSR Layout', 'Bangalore', '560102', 78),
                (5, 'Rohit Das', 'M', 'O-', '9554433221', '2024-10-15', 35, 'rohit@gmail.com', 'Ameerpet', 'Hyderabad', '500016', 11),
                (6, 'Simran Kaur', 'F', 'A+', '9443322110', '2025-01-10', 27, 'simran@gmail.com', 'Gomti Nagar', 'Lucknow', '226010', 9),
                (7, 'Deepak Jain', 'M', 'AB+', '9332211009', '2024-12-20', 31, 'deepak@gmail.com', 'Anna Nagar', 'Chennai', '600040', 4),
                (8, 'Sneha Iyer', 'F', 'O+', '9221100098', '2025-02-05', 26, 'sneha@gmail.com', 'Velachery', 'Chennai', '600042', 7),
                (9, 'Kiran Rao', 'M', 'B+', '9110098765', '2024-11-18', 40, 'kiran@gmail.com', 'Jubilee Hills', 'Hyderabad', '500033', 18),
                (10, 'Anjali Singh', 'F', 'A-', '9009988776', '2024-12-30', 23, 'anjali@gmail.com', 'Koramangala', 'Bangalore', '560034', 12),
                (11, 'Mohit Mehra', 'M', 'O+', '8998877665', '2025-02-12', 36, 'mohit@gmail.com', 'Vashi', 'Mumbai', '400703', 99),
                (12, 'Pooja Patel', 'F', 'B+', '8887766554', '2024-12-05', 30, 'pooja@gmail.com', 'Baner', 'Pune', '411045', 15),
                (13, 'Vikram Desai', 'M', 'A+', '8776655443', '2025-01-22', 34, 'vikram@gmail.com', 'HiTech City', 'Hyderabad', '500081', 42),
                (14, 'Aarti Gupta', 'F', 'AB-', '8665544332', '2025-02-20', 29, 'aarti@gmail.com', 'Chembur', 'Mumbai', '400074', 17),
                (15, 'Rajesh Kumar', 'M', 'O-', '8554433221', '2024-11-05', 41, 'rajesh@gmail.com', 'MG Road', 'Delhi', '110001', 101),
                (16, 'Divya Reddy', 'F', 'A-', '8443322110', '2025-01-30', 28, 'divya@gmail.com', 'Indiranagar', 'Bangalore', '560038', 23),
                (17, 'Sunil Menon', 'M', 'B+', '8332211009', '2025-03-02', 33, 'sunil@gmail.com', 'Erandwane', 'Pune', '411004', 14),
                (18, 'Meena Thomas', 'F', 'O+', '8221100098', '2025-02-18', 24, 'meena@gmail.com', 'Perumbakkam', 'Chennai', '600100', 6),
                (19, 'Aditya Jain', 'M', 'A+', '8110098765', '2024-12-22', 37, 'aditya@gmail.com', 'Banashankari', 'Bangalore', '560070', 31),
                (20, 'Riya Sharma', 'F', 'AB+', '8009988776', '2025-03-01', 26, 'riya@gmail.com', 'M G Road', 'Pune', '411001', 8);
                """;
            stmt.executeUpdate(insertDonor);
            System.out.println("Inserted 20 Donor rows.");

            // ---------- DONATIONCAMP ----------
            String insertDonationCamp = """
                INSERT INTO DonationCamp (camp_id, hospital_id, name, location, date, contact_no) VALUES
                (1, 1, 'Life Drive', 'MG Road', '2025-03-10', '9876543210'),
                (2, 2, 'Blood for Life', 'Andheri', '2025-03-11', '9823456789'),
                (3, 3, 'Save Life Camp', 'AIIMS Campus', '2025-03-12', '9812345678'),
                (4, 4, 'Hope Camp', 'Bannerghatta', '2025-03-13', '9898989898'),
                (5, 5, 'Red Drop', 'Airport Road', '2025-03-14', '9876509876'),
                (6, 6, 'Give Blood Save Life', 'Jubilee Hills', '2025-03-15', '9811199111'),
                (7, 7, 'Blood Connect', 'Secunderabad', '2025-03-16', '9700044000'),
                (8, 8, 'Life Stream', 'SRM Grounds', '2025-03-17', '9444332211'),
                (9, 9, 'Donate Today', 'Vellore CMC', '2025-03-18', '9345645656'),
                (10, 10, 'Save Humanity', 'Gachibowli', '2025-03-19', '9700055000'),
                (11, 11, 'Helping Hands', 'Perumbakkam', '2025-03-20', '9822002222'),
                (12, 12, 'Blood Unity', 'Baner', '2025-03-21', '9833300000'),
                (13, 13, 'Life Givers', 'Vile Parle', '2025-03-22', '9777007777'),
                (14, 14, 'Hope for Life', 'HiTech City', '2025-03-23', '9876506506'),
                (15, 15, 'Care for All', 'Anna Nagar', '2025-03-24', '9340022001'),
                (16, 16, 'Vital Drops', 'Chembur', '2025-03-25', '9000009999'),
                (17, 17, 'Unity Camp', 'Indiranagar', '2025-03-26', '9001190011'),
                (18, 18, 'Blood Link', 'MG Road', '2025-03-27', '9500095000'),
                (19, 19, 'Save Heart', 'Gomti Nagar', '2025-03-28', '9801010101'),
                (20, 20, 'Life Bloom', 'Erandwane', '2025-03-29', '9123456789');
                """;
            stmt.executeUpdate(insertDonationCamp);
            System.out.println("Inserted 20 DonationCamp rows.");

            // ---------- DONATION ----------
            String insertDonation = """
                INSERT INTO Donation (donation_id, donor_id, camp_id, donation_date, quantity) VALUES
                (1, 1, 1, '2025-03-10', 450),
                (2, 2, 2, '2025-03-11', 500),
                (3, 3, 3, '2025-03-12', 480),
                (4, 4, 4, '2025-03-13', 460),
                (5, 5, 5, '2025-03-14', 490),
                (6, 6, 6, '2025-03-15', 500),
                (7, 7, 7, '2025-03-16', 470),
                (8, 8, 8, '2025-03-17', 480),
                (9, 9, 9, '2025-03-18', 490),
                (10, 10, 10, '2025-03-19', 450),
                (11, 11, 11, '2025-03-20', 460),
                (12, 12, 12, '2025-03-21', 500),
                (13, 13, 13, '2025-03-22', 480),
                (14, 14, 14, '2025-03-23', 470),
                (15, 15, 15, '2025-03-24', 460),
                (16, 16, 16, '2025-03-25', 490),
                (17, 17, 17, '2025-03-26', 500),
                (18, 18, 18, '2025-03-27', 470),
                (19, 19, 19, '2025-03-28', 480),
                (20, 20, 20, '2025-03-29', 460);
                """;
            stmt.executeUpdate(insertDonation);
            System.out.println("Inserted 20 Donation rows.");

            // ---------- BLOODBAG ----------
            String insertBloodBag = """
                INSERT INTO BloodBag (bag_id, bloodbank_id, donation_id, blood_group, collected_date, expiry_date, quantity) VALUES
                (1, 1, 1, 'O+', '2025-03-10', '2025-06-10', 450),
                (2, 2, 2, 'A+', '2025-03-11', '2025-06-11', 500),
                (3, 3, 3, 'B+', '2025-03-12', '2025-06-12', 480),
                (4, 4, 4, 'O-', '2025-03-13', '2025-06-13', 460),
                (5, 5, 5, 'AB+', '2025-03-14', '2025-06-14', 490),
                (6, 6, 6, 'A-', '2025-03-15', '2025-06-15', 500),
                (7, 7, 7, 'O+', '2025-03-16', '2025-06-16', 470),
                (8, 8, 8, 'B-', '2025-03-17', '2025-06-17', 480),
                (9, 9, 9, 'O+', '2025-03-18', '2025-06-18', 490),
                (10, 10, 10, 'A+', '2025-03-19', '2025-06-19', 450),
                (11, 11, 11, 'O-', '2025-03-20', '2025-06-20', 460),
                (12, 12, 12, 'B+', '2025-03-21', '2025-06-21', 500),
                (13, 13, 13, 'AB-', '2025-03-22', '2025-06-22', 480),
                (14, 14, 14, 'A+', '2025-03-23', '2025-06-23', 470),
                (15, 15, 15, 'O+', '2025-03-24', '2025-06-24', 460),
                (16, 16, 16, 'AB+', '2025-03-25', '2025-06-25', 490),
                (17, 17, 17, 'O+', '2025-03-26', '2025-06-26', 500),
                (18, 18, 18, 'B-', '2025-03-27', '2025-06-27', 470),
                (19, 19, 19, 'O+', '2025-03-28', '2025-06-28', 480),
                (20, 20, 20, 'A-', '2025-03-29', '2025-06-29', 460);
                """;
            stmt.executeUpdate(insertBloodBag);
            System.out.println("Inserted 20 BloodBag rows.");

            // ---------- RECIPIENT ----------
            String insertRecipient = """
                INSERT INTO Recipient (recipient_id, hospital_id, name, contact_no, email, gender, age, blood_group) VALUES
                (1, 1, 'Suresh Rao', '9876501234', 'suresh@gmail.com', 'M', 45, 'O+'),
                (2, 2, 'Anita Singh', '9823012345', 'anita@gmail.com', 'F', 32, 'A+'),
                (3, 3, 'Rohan Mehta', '9812212345', 'rohan@gmail.com', 'M', 28, 'B+'),
                (4, 4, 'Priya Das', '9898981234', 'priyadas@gmail.com', 'F', 40, 'O-'),
                (5, 5, 'Arjun Nair', '9876508765', 'arjun@gmail.com', 'M', 50, 'AB+'),
                (6, 6, 'Kavita Reddy', '9811198123', 'kavita@gmail.com', 'F', 36, 'A-'),
                (7, 7, 'Deepa Menon', '9700044123', 'deepa@gmail.com', 'F', 25, 'O+'),
                (8, 8, 'Karthik Iyer', '9444332312', 'karthik@gmail.com', 'M', 29, 'B+'),
                (9, 9, 'Sonia Sharma', '9345645123', 'sonia@gmail.com', 'F', 33, 'A+'),
                (10, 10, 'Rahul Das', '9700055123', 'rahuldas@gmail.com', 'M', 42, 'O+'),
                (11, 11, 'Meena Pillai', '9340022123', 'meena@gmail.com', 'F', 27, 'A-'),
                (12, 12, 'Anand Gupta', '9822002234', 'anand@gmail.com', 'M', 38, 'B+'),
                (13, 13, 'Rekha Patel', '9801010123', 'rekha@gmail.com', 'F', 31, 'AB+'),
                (14, 14, 'Manoj Jain', '9833300123', 'manoj@gmail.com', 'M', 48, 'O+'),
                (15, 15, 'Vijaya Rao', '9777007123', 'vijaya@gmail.com', 'F', 37, 'O-'),
                (16, 16, 'Siddharth', '9876506512', 'siddharth@gmail.com', 'M', 30, 'B+'),
                (17, 17, 'Kiran Kumar', '9123456712', 'kiran@gmail.com', 'M', 43, 'A+'),
                (18, 18, 'Sneha Das', '9000009912', 'sneha@gmail.com', 'F', 26, 'O+'),
                (19, 19, 'Manish Yadav', '9500095123', 'manish@gmail.com', 'M', 39, 'AB+'),
                (20, 20, 'Priyanka', '9001190012', 'priyanka@gmail.com', 'F', 29, 'A-');
                """;
            stmt.executeUpdate(insertRecipient);
            System.out.println("Inserted 20 Recipient rows.");

            // ---------- REQUEST ----------
            String insertRequest = """
                INSERT INTO Request (request_id, recipient_id, bloodbank_id, request_date, status, quantity) VALUES
                (1, 1, 1, '2025-04-01', 'Pending', 2),
                (2, 2, 2, '2025-04-02', 'Approved', 3),
                (3, 3, 3, '2025-04-03', 'Pending', 1),
                (4, 4, 4, '2025-04-04', 'Rejected', 2),
                (5, 5, 5, '2025-04-05', 'Approved', 3),
                (6, 6, 6, '2025-04-06', 'Pending', 2),
                (7, 7, 7, '2025-04-07', 'Approved', 1),
                (8, 8, 8, '2025-04-08', 'Pending', 2),
                (9, 9, 9, '2025-04-09', 'Approved', 3),
                (10, 10, 10, '2025-04-10', 'Pending', 1),
                (11, 11, 11, '2025-04-11', 'Approved', 2),
                (12, 12, 12, '2025-04-12', 'Rejected', 2),
                (13, 13, 13, '2025-04-13', 'Pending', 3),
                (14, 14, 14, '2025-04-14', 'Approved', 1),
                (15, 15, 15, '2025-04-15', 'Pending', 2),
                (16, 16, 16, '2025-04-16', 'Approved', 3),
                (17, 17, 17, '2025-04-17', 'Pending', 2),
                (18, 18, 18, '2025-04-18', 'Approved', 1),
                (19, 19, 19, '2025-04-19', 'Rejected', 2),
                (20, 20, 20, '2025-04-20', 'Pending', 3);
                """;
            stmt.executeUpdate(insertRequest);
            System.out.println("Inserted 20 Request rows.");

            System.out.println("All inserts completed successfully!");

        } catch (SQLException e) {
            System.out.println("SQL error during inserts:");
            e.printStackTrace();
        }
    }

    // ================== INSERT ==================
    private static void insertData(Connection con, Scanner sc) {
        System.out.println("\nInsert into which table?");
        System.out.println("1. Hospital  2. Donor  3. DonationCamp  4. Donation  5. BloodBank  6. BloodBag  7. Recipient  8. Request");
        int choice = readInt(sc, "Choose table: ");

        try {
            switch (choice) {
                case 1 -> insertHospital(con, sc);
                case 2 -> insertDonor(con, sc);
                case 3 -> insertDonationCamp(con, sc);
                case 4 -> insertDonation(con, sc);
                case 5 -> insertBloodBank(con, sc);
                case 6 -> insertBloodBag(con, sc);
                case 7 -> insertRecipient(con, sc);
                case 8 -> insertRequest(con, sc);
                default -> System.out.println("Invalid table choice!");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(" Duplicate or constraint violation: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println(" SQL Error: " + e.getMessage());
        }
    }

    // ---------- INSERT METHODS ----------
    private static void insertHospital(Connection con, Scanner sc) throws SQLException {
        int hospital_id = readInt(sc, "Hospital ID: ");
        String email = readString(sc, "Email: ");
        String name = readString(sc, "Name: ");
        String contact_no = readString(sc, "Contact: ");
        String street = readString(sc, "Street: ");
        String pincode = readString(sc, "Pincode: ");
        String city = readString(sc, "City: ");

        String q = "INSERT INTO Hospital (hospital_id, email, name, contact_no, street, pincode, city) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, hospital_id);
            ps.setString(2, email);
            ps.setString(3, name);
            ps.setString(4, contact_no);
            ps.setString(5, street);
            ps.setString(6, pincode);
            ps.setString(7, city);
            ps.executeUpdate();
            System.out.println("Hospital inserted!");
        }
    }

    private static void insertDonor(Connection con, Scanner sc) throws SQLException {
        int donor_id = readInt(sc, "Donor ID: ");
        String donor_name = readString(sc, "Name: ");
        String gender = readString(sc, "Gender (M/F/O): ");
        String blood_group = readString(sc, "Blood Group: ");
        String contact_no = readString(sc, "Contact: ");
        String last_dn = readString(sc, "Last Donation Date (yyyy-mm-dd or leave blank): ");
        int age = readInt(sc, "Age: ");
        String email = readString(sc, "Email: ");
        String street = readString(sc, "Street: ");
        String city = readString(sc, "City: ");
        String pincode = readString(sc, "Pincode: ");
        int house_no = readInt(sc, "House No: ");

        String q = "INSERT INTO Donor (donor_id, donor_name, gender, blood_group, contact_no, last_dn, age, email, street, city, pincode, house_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, donor_id);
            ps.setString(2, donor_name);
            ps.setString(3, gender);
            ps.setString(4, blood_group);
            ps.setString(5, contact_no);
            if (last_dn.isEmpty()) ps.setNull(6, java.sql.Types.DATE);
            else ps.setDate(6, java.sql.Date.valueOf(last_dn));
            ps.setInt(7, age);
            ps.setString(8, email);
            ps.setString(9, street);
            ps.setString(10, city);
            ps.setString(11, pincode);
            ps.setInt(12, house_no);
            ps.executeUpdate();
            System.out.println(" Donor inserted!");
        }
    }

    private static void insertDonationCamp(Connection con, Scanner sc) throws SQLException {
        int camp_id = readInt(sc, "Camp ID: ");
        int hospital_id = readInt(sc, "Hospital ID: ");
        String name = readString(sc, "Camp Name: ");
        String location = readString(sc, "Location: ");
        String date = readString(sc, "Date (yyyy-mm-dd): ");
        String contact_no = readString(sc, "Contact: ");

        String q = "INSERT INTO DonationCamp (camp_id, hospital_id, name, location, date, contact_no) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, camp_id);
            ps.setInt(2, hospital_id);
            ps.setString(3, name);
            ps.setString(4, location);
            ps.setDate(5, java.sql.Date.valueOf(date));
            ps.setString(6, contact_no);
            ps.executeUpdate();
            System.out.println(" Donation Camp inserted!");
        }
    }

    private static void insertDonation(Connection con, Scanner sc) throws SQLException {
        int donation_id = readInt(sc, "Donation ID: ");
        int donor_id = readInt(sc, "Donor ID: ");
        int camp_id = readInt(sc, "Camp ID: ");
        String donation_date = readString(sc, "Donation Date (yyyy-mm-dd): ");
        int quantity = readInt(sc, "Quantity: ");

        String q = "INSERT INTO Donation (donation_id, donor_id, camp_id, donation_date, quantity) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, donation_id);
            ps.setInt(2, donor_id);
            ps.setInt(3, camp_id);
            ps.setDate(4, java.sql.Date.valueOf(donation_date));
            ps.setInt(5, quantity);
            ps.executeUpdate();
            System.out.println(" Donation inserted!");
        }
    }

    private static void insertBloodBank(Connection con, Scanner sc) throws SQLException {
        int bloodbank_id = readInt(sc, "BloodBank ID: ");
        int bag_id = readInt(sc, "Bag ID (or 0 if none): ");
        String name = readString(sc, "Name: ");
        String location = readString(sc, "Location: ");
        String contact_no = readString(sc, "Contact: ");
        String email = readString(sc, "Email: ");
        int capacity = readInt(sc, "Capacity: ");

        String q = "INSERT INTO BloodBank (bloodbank_id, bag_id, name, location, contact_no, email, capacity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, bloodbank_id);
            if (bag_id == 0) ps.setNull(2, java.sql.Types.INTEGER);
            else ps.setInt(2, bag_id);
            ps.setString(3, name);
            ps.setString(4, location);
            ps.setString(5, contact_no);
            ps.setString(6, email);
            ps.setInt(7, capacity);
            ps.executeUpdate();
            System.out.println(" BloodBank inserted!");
        }
    }

    private static void insertBloodBag(Connection con, Scanner sc) throws SQLException {
        int bag_id = readInt(sc, "Bag ID: ");
        int bloodbank_id = readInt(sc, "BloodBank ID: ");
        int donation_id = readInt(sc, "Donation ID: ");
        String blood_group = readString(sc, "Blood Group: ");
        String collected_date = readString(sc, "Collected Date (yyyy-mm-dd): ");
        String expiry_date = readString(sc, "Expiry Date (yyyy-mm-dd): ");
        int quantity = readInt(sc, "Quantity: ");

        String q = "INSERT INTO BloodBag (bag_id, bloodbank_id, donation_id, blood_group, collected_date, expiry_date, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, bag_id);
            ps.setInt(2, bloodbank_id);
            ps.setInt(3, donation_id);
            ps.setString(4, blood_group);
            ps.setDate(5, java.sql.Date.valueOf(collected_date));
            ps.setDate(6, java.sql.Date.valueOf(expiry_date));
            ps.setInt(7, quantity);
            ps.executeUpdate();
            System.out.println(" BloodBag inserted!");
        }
    }

    private static void insertRecipient(Connection con, Scanner sc) throws SQLException {
        int recipient_id = readInt(sc, "Recipient ID: ");
        int hospital_id = readInt(sc, "Hospital ID: ");
        String name = readString(sc, "Name: ");
        String contact_no = readString(sc, "Contact: ");
        String email = readString(sc, "Email: ");
        String gender = readString(sc, "Gender (M/F/O): ");
        int age = readInt(sc, "Age: ");
        String blood_group = readString(sc, "Blood Group: ");

        String q = "INSERT INTO Recipient (recipient_id, hospital_id, name, contact_no, email, gender, age, blood_group) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, recipient_id);
            ps.setInt(2, hospital_id);
            ps.setString(3, name);
            ps.setString(4, contact_no);
            ps.setString(5, email);
            ps.setString(6, gender);
            ps.setInt(7, age);
            ps.setString(8, blood_group);
            ps.executeUpdate();
            System.out.println(" Recipient inserted!");
        }
    }

    private static void insertRequest(Connection con, Scanner sc) throws SQLException {
        int request_id = readInt(sc, "Request ID: ");
        int recipient_id = readInt(sc, "Recipient ID: ");
        int bloodbank_id = readInt(sc, "BloodBank ID: ");
        String request_date = readString(sc, "Request Date (yyyy-mm-dd): ");
        String status = readString(sc, "Status (Pending/Approved/Rejected): ");
        int quantity = readInt(sc, "Quantity: ");

        String q = "INSERT INTO Request (request_id, recipient_id, bloodbank_id, request_date, status, quantity) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, request_id);
            ps.setInt(2, recipient_id);
            ps.setInt(3, bloodbank_id);
            ps.setDate(4, java.sql.Date.valueOf(request_date));
            ps.setString(5, status);
            ps.setInt(6, quantity);
            ps.executeUpdate();
            System.out.println(" Request inserted!");
        }
    }

    // ================== VIEW ==================
    private static void viewData(Connection con, Scanner sc) {
        System.out.println("\nChoose table to view:");
        System.out.println("1. Hospital  2. Donor  3. DonationCamp  4. Donation  5. BloodBank  6. BloodBag  7. Recipient  8. Request");
        int choice = readInt(sc, "Choose table: ");

        String table = switch (choice) {
            case 1 -> "Hospital";
            case 2 -> "Donor";
            case 3 -> "DonationCamp";
            case 4 -> "Donation";
            case 5 -> "BloodBank";
            case 6 -> "BloodBag";
            case 7 -> "Recipient";
            case 8 -> "Request";
            default -> null;
        };
        if (table == null) {
            System.out.println(" Invalid table choice!");
            return;
        }

        String query = "SELECT * FROM " + table;
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();

            // Store data and find max column widths
            List<String[]> rows = new ArrayList<>();
            int[] widths = new int[cols];
            for (int i = 1; i <= cols; i++)
                widths[i - 1] = md.getColumnName(i).length();

            while (rs.next()) {
                String[] row = new String[cols];
                for (int i = 1; i <= cols; i++) {
                    String val = rs.getString(i);
                    if (val == null) val = "NULL";
                    row[i - 1] = val;
                    widths[i - 1] = Math.max(widths[i - 1], val.length());
                }
                rows.add(row);
            }

            if (rows.isEmpty()) {
                System.out.println("⚠ No records found.");
                return;
            }

            // Print top border
            printBorder(widths);

            // Print headers
            System.out.print("|");
            for (int i = 1; i <= cols; i++)
                System.out.printf(" %-" + widths[i - 1] + "s |", md.getColumnName(i));
            System.out.println();

            // Header underline
            printBorder(widths);

            // Print each row
            for (String[] row : rows) {
                System.out.print("|");
                for (int i = 0; i < cols; i++)
                    System.out.printf(" %-" + widths[i] + "s |", row[i]);
                System.out.println();
            }

            // Bottom border
            printBorder(widths);

        } catch (SQLException e) {
            System.out.println(" SQL Error: " + e.getMessage());
        }
    }

    private static void printBorder(int[] widths) {
        StringBuilder sb = new StringBuilder("+");
        for (int w : widths)
            sb.append("-".repeat(w + 2)).append("+");
        System.out.println(sb);
    }



    // ================== UPDATE ==================
    private static void updateData(Connection con, Scanner sc) throws SQLException {
        System.out.println("\nUpdate in which table?");
        System.out.println("1. Hospital  2. Donor  3. DonationCamp  4. Donation  5. BloodBank  6. BloodBag  7. Recipient  8. Request");
        int t = sc.nextInt();
        sc.nextLine();

        String table = "";
        String idColumn = "";

        switch (t) {
            case 1 -> { table = "Hospital"; idColumn = "hospital_id"; }
            case 2 -> { table = "Donor"; idColumn = "donor_id"; }
            case 3 -> { table = "DonationCamp"; idColumn = "camp_id"; }
            case 4 -> { table = "Donation"; idColumn = "donation_id"; }
            case 5 -> { table = "BloodBank"; idColumn = "bloodbank_id"; }
            case 6 -> { table = "BloodBag"; idColumn = "bag_id"; }
            case 7 -> { table = "Recipient"; idColumn = "recipient_id"; }
            case 8 -> { table = "Request"; idColumn = "request_id"; }
            default -> {
                System.out.println("Invalid choice!");
                return;
            }
        }

        System.out.print("Enter ID value to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter column name to update: ");
        String col = sc.next();
        sc.nextLine();
        System.out.print("Enter new value: ");
        String value = sc.nextLine();

        String query = "UPDATE " + table + " SET " + col + "=? WHERE " + idColumn + "=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, value);
        ps.setInt(2, id);
        int rows = ps.executeUpdate();
        System.out.println(rows > 0 ? " Updated successfully!" : " ID not found.");
    }

    // ================== DELETE ==================
    private static void deleteData(Connection con, Scanner sc) {
        System.out.println("\nDelete from which table?");
        System.out.println("1. Hospital  2. Donor  3. DonationCamp  4. Donation  5. BloodBank  6. BloodBag  7. Recipient  8. Request");
        int choice = readInt(sc, "Choose table: ");

        try {
            switch (choice) {
                case 1 -> deleteById(con, sc, "Hospital", "hospital_id");
                case 2 -> deleteById(con, sc, "Donor", "donor_id");
                case 3 -> deleteById(con, sc, "DonationCamp", "camp_id");
                case 4 -> deleteById(con, sc, "Donation", "donation_id");
                case 5 -> deleteById(con, sc, "BloodBank", "bloodbank_id");
                case 6 -> deleteById(con, sc, "BloodBag", "bag_id");
                case 7 -> deleteById(con, sc, "Recipient", "recipient_id");
                case 8 -> deleteById(con, sc, "Request", "request_id");
                default -> System.out.println("Invalid table choice!");
            }
        } catch (SQLException e) {
            System.out.println(" SQL Error: " + e.getMessage());
        }
    }

    private static void deleteById(Connection con, Scanner sc, String table, String column) throws SQLException {
        int id = readInt(sc, "Enter " + column + " to delete: ");
        String q = "DELETE FROM " + table + " WHERE " + column + "=?";
        try (PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? " Record deleted!" : "ID not found.");
        }
    }

    // =================== NEW SECTION: RUN QUERIES ===================
    private static void runQueries(Connection con, Scanner sc) {
        while (true) {
            System.out.println("\n==== QUERY & PROCEDURE MENU ====");
            System.out.println("1. Find all donors with blood group O+");
            System.out.println("------------------------------------------------------");
            System.out.println("2. List all donations made in 2025");
            System.out.println("------------------------------------------------------");
            System.out.println("3. Find donors who haven't donated in over a year");
            System.out.println("------------------------------------------------------");
            System.out.println("4. Count total blood bags per blood bank");
            System.out.println("------------------------------------------------------");
            System.out.println("5. Find recipients and their associated hospitals");
            System.out.println("------------------------------------------------------");
            System.out.println("6. Show donation camps held in a specific city");
            System.out.println("------------------------------------------------------");
            System.out.println("7. Find blood bags expiring this month");
            System.out.println("------------------------------------------------------");
            System.out.println("8. List donors by city with count");
            System.out.println("------------------------------------------------------");
            System.out.println("9. Donors who participated in multiple camps");
            System.out.println("------------------------------------------------------");
            System.out.println("10. Display blood banks and total blood quantity");
            System.out.println("------------------------------------------------------");
            System.out.println("11. Show most recent donation for each donor");
            System.out.println("------------------------------------------------------");
            System.out.println("12. Find donors matching recipients in same city");
            System.out.println("------------------------------------------------------");
            System.out.println("13. List hospitals handling A+ blood requests");
            System.out.println("------------------------------------------------------");
            System.out.println("14. Show pending requests older than 30 days");
            System.out.println("------------------------------------------------------");
            System.out.println("15. Find top 3 donors with highest donations");
            System.out.println("------------------------------------------------------");
            System.out.println("16. Total donations from each camp with hospital");
            System.out.println("------------------------------------------------------");
            System.out.println("17. Blood bags expiring within next 15 days");
            System.out.println("------------------------------------------------------");
            System.out.println("18. Count requests by status");
            System.out.println("------------------------------------------------------");
            System.out.println("19.Add new donor (Create Procedure)");
            System.out.println("------------------------------------------------------");
            System.out.println("20.Total blood by camp (Create Function) ");
            System.out.println("------------------------------------------------------");
            System.out.println("21. Update last donation date(Create Trigger)");
            System.out.println("------------------------------------------------------");
            System.out.println("22. Update request status(Create Procedure)");
            System.out.println("------------------------------------------------------");
            System.out.println("23.Show donor totals (Cursor) ");
            System.out.println("------------------------------------------------------");
            System.out.println("24.Prevent expired blood bags (Create Trigger) ");
            System.out.println("------------------------------------------------------");
            System.out.println("25.Count pending requests (Create Procedure) ");
            System.out.println("------------------------------------------------------");
            System.out.println("26. Back to Main Menu");

            int ch = readInt(sc, "\nChoose option (1-26): ");

            if (ch == 26) return;

            try {
                switch (ch) {
                    case 1 -> runSelect(con, "SELECT donor_name, contact_no, city FROM donor WHERE blood_group='O+'");
                    case 2 -> runSelect(con, "SELECT donation_id, donor_id, donation_date, quantity FROM donation WHERE YEAR(donation_date)=2025");
                    case 3 -> runSelect(con, "SELECT donor_name, last_dn FROM donor WHERE last_dn < DATE_SUB(CURDATE(), INTERVAL 1 YEAR)");
                    case 4 -> runSelect(con, "SELECT b.name, COUNT(bg.bag_id) AS total_bags FROM bloodbank b LEFT JOIN bloodbag bg ON b.bloodbank_id=bg.bloodbank_id GROUP BY b.bloodbank_id");
                    case 5 -> runSelect(con, "SELECT r.name AS recipient_name, h.name AS hospital_name FROM recipient r JOIN hospital h ON r.hospital_id=h.hospital_id");
                    case 6 -> {
                        String city = readString(sc, "Enter city: ");
                        runPrepared(con, "SELECT name, location, date FROM donationcamp WHERE location=?", city);
                    }
                    case 7 -> runSelect(con, "SELECT bag_id, blood_group, expiry_date FROM bloodbag WHERE MONTH(expiry_date)=MONTH(CURDATE()) AND YEAR(expiry_date)=YEAR(CURDATE())");
                    case 8 -> runSelect(con, "SELECT city, COUNT(donor_id) AS total_donors FROM donor GROUP BY city");
                    case 9 -> runSelect(con, "SELECT d.donor_name, COUNT(DISTINCT o.camp_id) AS camp_count FROM donor d JOIN donation o ON d.donor_id=o.donor_id GROUP BY d.donor_id HAVING camp_count>1");
                    case 10 -> runSelect(con, "SELECT b.name AS bloodbank_name, IFNULL(SUM(bg.quantity),0) AS total_blood_quantity FROM bloodbank b LEFT JOIN bloodbag bg ON b.bloodbank_id=bg.bloodbank_id GROUP BY b.bloodbank_id");
                    case 11 -> runSelect(con, "SELECT d.donor_name, MAX(o.donation_date) AS last_donation FROM donor d JOIN donation o ON d.donor_id=o.donor_id GROUP BY d.donor_name");
                    case 12 -> runSelect(con, "SELECT DISTINCT d.donor_name, d.city, d.blood_group FROM donor d JOIN recipient r ON d.city=r.city AND d.blood_group=r.blood_group");
                    case 13 -> runSelect(con, "SELECT DISTINCT h.name AS hospital_name FROM hospital h JOIN recipient r ON h.hospital_id=r.hospital_id JOIN request rq ON r.recipient_id=rq.recipient_id WHERE r.blood_group='A+'");
                    case 14 -> runSelect(con, "SELECT rq.request_id, r.name AS recipient_name, rq.request_date, rq.status FROM request rq JOIN recipient r ON rq.recipient_id=r.recipient_id WHERE rq.status='Pending' AND rq.request_date<DATE_SUB(CURDATE(), INTERVAL 30 DAY)");
                    case 15 -> runSelect(con, "SELECT d.donor_name, SUM(o.quantity) AS total_quantity FROM donor d JOIN donation o ON d.donor_id=o.donor_id GROUP BY d.donor_name ORDER BY total_quantity DESC LIMIT 3");
                    case 16 -> runSelect(con, "SELECT c.name AS camp_name, h.name AS hospital_name, SUM(o.quantity) AS total_collected FROM donationcamp c JOIN hospital h ON c.hospital_id=h.hospital_id JOIN donation o ON c.camp_id=o.camp_id GROUP BY c.camp_id,h.name");
                    case 17 -> runSelect(con, "SELECT bag_id, blood_group, expiry_date FROM bloodbag WHERE expiry_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 15 DAY)");
                    case 18 -> runSelect(con, "SELECT status, COUNT(*) AS total_requests FROM request GROUP BY status");
                    case 19 -> runProcedure(con, "CREATE PROCEDURE add_donor(IN p_name VARCHAR(100), IN p_gender CHAR(1), IN p_blood_group VARCHAR(5), IN p_contact VARCHAR(15), IN p_age INT, IN p_email VARCHAR(100)) BEGIN INSERT INTO donor(donor_name, gender, blood_group, contact_no, age, email) VALUES(p_name,p_gender,p_blood_group,p_contact,p_age,p_email); END");
                    case 20 -> runProcedure(con, "CREATE FUNCTION total_blood_by_camp(p_camp_id INT) RETURNS INT DETERMINISTIC BEGIN DECLARE total INT; SELECT SUM(quantity) INTO total FROM donation WHERE camp_id=p_camp_id; RETURN IFNULL(total,0); END");
                    case 21 -> runProcedure(con, "CREATE TRIGGER update_last_donation AFTER INSERT ON donation FOR EACH ROW BEGIN UPDATE donor SET last_dn=NEW.donation_date WHERE donor_id=NEW.donor_id; END");
                    case 22 -> runProcedure(con, "CREATE PROCEDURE update_request_status(IN p_request_id INT, IN p_status VARCHAR(15)) BEGIN UPDATE request SET status=p_status WHERE request_id=p_request_id; END");
                    case 23 -> runProcedure(con, "CREATE PROCEDURE show_donor_totals() BEGIN DECLARE done INT DEFAULT 0; DECLARE d_name VARCHAR(100); DECLARE total INT; DECLARE cur CURSOR FOR SELECT d.donor_name, SUM(o.quantity) FROM donor d JOIN donation o ON d.donor_id=o.donor_id GROUP BY d.donor_name; DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1; OPEN cur; read_loop: LOOP FETCH cur INTO d_name, total; IF done THEN LEAVE read_loop; END IF; SELECT CONCAT(d_name,' has donated ',total,' units.') AS info; END LOOP; CLOSE cur; END");
                    case 24 -> runProcedure(con, "CREATE TRIGGER check_expiry_before_insert BEFORE INSERT ON bloodbag FOR EACH ROW BEGIN IF NEW.expiry_date < CURDATE() THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Cannot insert expired blood bag.'; END IF; END");
                    case 25 -> runProcedure(con, "CREATE PROCEDURE count_pending_requests() BEGIN DECLARE total_pending INT; SELECT COUNT(*) INTO total_pending FROM request WHERE status='Pending'; SELECT CONCAT('Total pending requests: ', total_pending) AS message; END");
                    default -> System.out.println(" Invalid option!");
                }
            } catch (SQLException e) {
                System.out.println("SQL Error: " + e.getMessage());
            }
        }
    }

    // ---------- HELPER METHODS FOR RUNNING QUERIES ----------
    private static void runSelect(Connection con, String query) throws SQLException {
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();

            // Store rows and calculate max widths
            List<String[]> rows = new ArrayList<>();
            int[] widths = new int[cols];
            for (int i = 1; i <= cols; i++)
                widths[i - 1] = md.getColumnName(i).length();

            while (rs.next()) {
                String[] row = new String[cols];
                for (int i = 1; i <= cols; i++) {
                    String val = rs.getString(i);
                    if (val == null) val = "NULL";
                    row[i - 1] = val;
                    widths[i - 1] = Math.max(widths[i - 1], val.length());
                }
                rows.add(row);
            }

            if (rows.isEmpty()) {
                System.out.println(" No data found.");
                return;
            }

            printLine(widths);
            System.out.print("|");
            for (int i = 1; i <= cols; i++)
                System.out.printf(" %-" + widths[i - 1] + "s |", md.getColumnName(i));
            System.out.println();
            printLine(widths);

            for (String[] row : rows) {
                System.out.print("|");
                for (int i = 0; i < cols; i++)
                    System.out.printf(" %-" + widths[i] + "s |", row[i]);
                System.out.println();
            }
            printLine(widths);
        }
    }

    private static void runPrepared(Connection con, String query, String param) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData md = rs.getMetaData();
                int cols = md.getColumnCount();

                List<String[]> rows = new ArrayList<>();
                int[] widths = new int[cols];
                for (int i = 1; i <= cols; i++)
                    widths[i - 1] = md.getColumnName(i).length();

                while (rs.next()) {
                    String[] row = new String[cols];
                    for (int i = 1; i <= cols; i++) {
                        String val = rs.getString(i);
                        if (val == null) val = "NULL";
                        row[i - 1] = val;
                        widths[i - 1] = Math.max(widths[i - 1], val.length());
                    }
                    rows.add(row);
                }

                if (rows.isEmpty()) {
                    System.out.println("⚠ No data found.");
                    return;
                }

                printLine(widths);
                System.out.print("|");
                for (int i = 1; i <= cols; i++)
                    System.out.printf(" %-" + widths[i - 1] + "s |", md.getColumnName(i));
                System.out.println();
                printLine(widths);

                for (String[] row : rows) {
                    System.out.print("|");
                    for (int i = 0; i < cols; i++)
                        System.out.printf(" %-" + widths[i] + "s |", row[i]);
                    System.out.println();
                }
                printLine(widths);
            }
        }
    }

    private static void runProcedure(Connection con, String sql) throws SQLException {
        try (Statement st = con.createStatement()) {
            st.execute(sql);

            System.out.println("+-----------------------------------------------+");
            System.out.println("|   Procedure/Trigger/Function created!         |");
            System.out.println("+-----------------------------------------------+");

            String lower = sql.toLowerCase();

            // Automatically run example call for demonstration
            if (lower.contains("procedure add_donor")) {
                System.out.println("\nExecuting: CALL add_donor('DemoUser','F','O+','9999999999',25,'demo@example.com');");
                try (CallableStatement cs = con.prepareCall("{CALL add_donor(?,?,?,?,?,?)}")) {
                    cs.setString(1, "DemoUser");
                    cs.setString(2, "F");
                    cs.setString(3, "O+");
                    cs.setString(4, "9999999999");
                    cs.setInt(5, 25);
                    cs.setString(6, "demo@example.com");
                    cs.execute();
                    System.out.println("+-----------------------------------------------+");
                    System.out.println("|   Sample donor inserted successfully!       |");
                    System.out.println("+-----------------------------------------------+");
                }
            }
            else if (lower.contains("function total_blood_by_camp")) {
                System.out.println("\nExecuting: SELECT total_blood_by_camp(1);");
                try (ResultSet rs = st.executeQuery("SELECT total_blood_by_camp(1) AS total_blood;")) {
                    if (rs.next()) {
                        System.out.println("+-----------------------------------------------------------------------------+");
                        System.out.printf("|   Total Blood for Camp 1: %-16s|\n", rs.getString("total_blood"));
                        System.out.println("+-----------------------------------------------------------------------------+");
                    }
                }
            }
            else if (lower.contains("trigger update_last_donation")) {
                System.out.println("+-----------------------------------------------+");
                System.out.println("|   Trigger 'update_last_donation' is active. |");
                System.out.println("|  It will auto-update donor last donation date.|");
                System.out.println("+-----------------------------------------------+");
            }
            else if (lower.contains("procedure update_request_status")) {
                System.out.println("\nExecuting: CALL update_request_status(1,'Completed');");
                try (CallableStatement cs = con.prepareCall("{CALL update_request_status(?,?)}")) {
                    cs.setInt(1, 1);
                    cs.setString(2, "Completed");
                    cs.execute();
                    System.out.println("+-----------------------------------------------+");
                    System.out.println("|   Request status updated for ID 1!          |");
                    System.out.println("+-----------------------------------------------+");
                }
            }
            else if (lower.contains("trigger check_expiry_before_insert")) {
                System.out.println("+-----------------------------------------------+");
                System.out.println("|   Trigger 'check_expiry_before_insert' ready.|");
                System.out.println("|  It prevents expired blood bag insertion.     |");
                System.out.println("+-----------------------------------------------+");
            }
            else if (lower.contains("procedure count_pending_requests")) {
                System.out.println("\nExecuting: CALL count_pending_requests();");
                try (CallableStatement cs = con.prepareCall("{CALL count_pending_requests()}")) {
                    boolean hasResult = cs.execute();
                    if (hasResult) {
                        try (ResultSet rs = cs.getResultSet()) {
                            while (rs.next()) {
                                System.out.println("+-----------------------------------------------+");
                                System.out.printf("|   %s\n", rs.getString(1));
                                System.out.println("+-----------------------------------------------+");
                            }
                        }
                    }
                }
            }
            else if (lower.contains("procedure show_donor_totals")) {
                System.out.println("\nExecuting: CALL show_donor_totals();");
                try (CallableStatement cs = con.prepareCall("{CALL show_donor_totals()}")) {
                    boolean hasResult = cs.execute();
                    if (hasResult) {
                        try (ResultSet rs = cs.getResultSet()) {
                            while (rs.next()) {
                                System.out.println("+-----------------------------------------------+");
                                System.out.printf("|  %s\n", rs.getString(1));
                                System.out.println("+-----------------------------------------------+");
                            }
                        }
                    }
                }
            }
            else {
                System.out.println("|  No immediate output to show.                 |");
                System.out.println("+-----------------------------------------------+");
            }
        }
    }


    private static void printLine(int[] widths) {
        StringBuilder sb = new StringBuilder("+");
        for (int w : widths)
            sb.append("-".repeat(w + 2)).append("+");
        System.out.println(sb);
    }
}
