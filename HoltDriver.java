import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;//scanner class
public class HoltDriver {

    public static void main(String[] args) throws FileNotFoundException {
        try{
        // Read data from file into ArrayList
        ArrayList<Arena> arenas = readArenasFromFile("IndoorArenas.txt");

        // 1. create the connection to the database
        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ArenasDB", "postgres", "Schecter98");
        System.out.println("Connected to the database successfully!");
        // 2. drop table if exists
        dropTable(conn);
        // 3. Creates Class Schedule table and populates it
        buildArenasTable(conn, arenas);
        // 4. Query the table to display all the courses you are currently taking
        displayAllRows(conn);
        // 5. Query table to display total credits
       // totalCreditHours(conn);
        //close connection
        conn.close();
        }
        catch(Exception e)
        {
        e.printStackTrace();
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }// end main method
    //Method to read data from IndoorArenas.txt
    public static ArrayList<Arena> readArenasFromFile(String fileName) {
        ArrayList<Arena> arenas = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String venue = scanner.nextLine().trim();
                String city = scanner.nextLine().trim();
                String state = scanner.nextLine().trim();
                int maxCapacity = Integer.parseInt(scanner.nextLine().trim());
                int yearOpened = Integer.parseInt(scanner.nextLine().trim());
                String teamName = scanner.nextLine().trim();
                String sport = scanner.nextLine().trim();
                String league = scanner.nextLine().trim();
                if (scanner.hasNextLine()) scanner.nextLine(); // skip empty line

                Tenant tenant = new Tenant(teamName, sport, league);
                Arena arena = new Arena(venue, city, state, maxCapacity, yearOpened, tenant);
                arenas.add(arena);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return arenas;
    }
    //method to drop the arenas table if it exists
    public static void dropTable(Connection conn) {
        //message to check for tables
        System.out.println("Checking for existing Arenas table in the ArenasDB Database...");
        try {
            //statement object
            Statement stmt = conn.createStatement();
            //execute statement to drop the table
            //use DROP TABLE IF EXISTS to drop a postgres table
            stmt.execute("DROP TABLE IF EXISTS Arenas");
            // print confirmation that the table was dropped
            System.out.println("Arenas table dropped");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }//end drop table method
    //method to build Arenas table
    public static void buildArenasTable(Connection conn, ArrayList<Arena> arenas) {
            System.out.println("Building the Arenas table in the ArenaDB Database...");
            try {
                Statement stmt = conn.createStatement();
                stmt.execute("CREATE TABLE Arenas (Venue CHAR(45) PRIMARY KEY," +
                        "City CHAR(15)," +
                        "State CHAR(15)," +
                        "MaxCapacity INT," +
                        "YearOpened INT," +
                        "TeamName CHAR(30)," +
                        "Sport CHAR(30)," +
                        "League CHAR(30));");

                String insertSQL = "INSERT INTO Arenas (Venue, City, State, MaxCapacity, YearOpened, TeamName, Sport, League) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                    for (Arena arena : arenas) {
                        pstmt.setString(1, arena.getVenueName());
                        pstmt.setString(2, arena.getCity());
                        pstmt.setString(3, arena.getState());
                        pstmt.setInt(4, arena.getMaxCapacity());
                        pstmt.setInt(5, arena.getYearOpened());
                        pstmt.setString(6, arena.getTenant().getTeamName());
                        pstmt.setString(7, arena.getTenant().getSport());
                        pstmt.setString(8, arena.getTenant().getLeague());
                        pstmt.executeUpdate();
                    }
                }
                System.out.println("Arenas table populated.");
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }

    }// end buildArenasTable method
    //method to display the rows from the table
    public static void displayAllRows(Connection conn)
    {
        //message to display Arena table rows
        System.out.println("Display Arenas table in the ArenaDB Database...");
        try
        {
            //statement object
            Statement stmt = conn.createStatement();
            //SQL statement to execute the query
            String sqlStatement = "SELECT * FROM Arenas;";

            //Statement to be sent to the DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);

            //Display the results of the query
            while (result.next()) {
                System.out.printf("%s \t %s \t %s \t %d \t %d \t %s \t %s \t %s \n",
                        result.getString("Venue"),
                        result.getString("City"),
                        result.getString("State"),
                        result.getInt("MaxCapacity"),
                        result.getInt("YearOpened"),
                        result.getString("TeamName"),
                        result.getString("Sport"),
                        result.getString("League"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }//end displayAllRows method
}
