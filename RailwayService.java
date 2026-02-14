import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class RailwayService {

    private static final String TRAIN_FILE = "trains.txt";
    private static final String PASSENGER_FILE = "passengers.txt";
    private static final String TICKET_FILE = "tickets.txt";
    private static final String ID_FILE = "id_counter.txt";

    private Scanner sc = new Scanner(System.in);

    // ================= LOGIN =================
    public String login() {

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        if (username.equals("admin") && password.equals("admin123"))
            return "ADMIN";

        if (username.equals("user") && password.equals("user123"))
            return "USER";

        return null;
    }

    // ================= INITIALIZE FILES =================
    public void initializeFiles() throws Exception {

        if (!new File(TRAIN_FILE).exists())
            new File(TRAIN_FILE).createNewFile();

        new File(PASSENGER_FILE).createNewFile();
        new File(TICKET_FILE).createNewFile();

        if (!new File(ID_FILE).exists()) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(ID_FILE));
            bw.write("1000");
            bw.close();
        }
    }

    // ================= ID GENERATOR =================
    private String generateId(String prefix) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(ID_FILE));
        int id = Integer.parseInt(br.readLine());
        br.close();

        id++;

        BufferedWriter bw = new BufferedWriter(new FileWriter(ID_FILE));
        bw.write(String.valueOf(id));
        bw.close();

        return prefix + id;
    }

    // ================= HELPER =================
    private int getNonNegativeSeatInput(String message) {

        int value;

        while (true) {

            System.out.print(message);

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Enter a valid number.");
                sc.next();
                continue;
            }

            value = sc.nextInt();

            if (value < 0) {
                System.out.println("Seat count cannot be negative.");
            } else {
                sc.nextLine();
                return value;
            }
        }
    }

    private List<String> loadTrainLines() throws Exception {
        List<String> trains = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(TRAIN_FILE));
        String line;
        while ((line = br.readLine()) != null) {
            trains.add(line);
        }
        br.close();
        return trains;
    }

    // ================= ADD TRAIN =================
    public void addTrain() throws Exception {

        System.out.print("Train No: ");
        String trainNo = sc.nextLine();

        List<String> trains = loadTrainLines();

        for (String line : trains) {
            if (line.split("\\|")[0].equals(trainNo)) {
                System.out.println("Train No already exists!");
                return;
            }
        }

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Source: ");
        String source = sc.nextLine();

        System.out.print("Destination: ");
        String dest = sc.nextLine();

        int sleeper = getNonNegativeSeatInput("Sleeper Seats: ");
        int ac = getNonNegativeSeatInput("AC Seats: ");

        BufferedWriter bw = new BufferedWriter(new FileWriter(TRAIN_FILE, true));
        if (!trains.isEmpty())
            bw.newLine();
        bw.write(trainNo + "|" + name + "|" + source + "|" + dest + "|"
                + sleeper + "|" + sleeper + "|" + ac + "|" + ac);
        bw.close();

        System.out.println("Train Added Successfully.");
    }

    // ================= BOOK TICKET =================
    public void bookTicket() throws Exception {

        System.out.print("Train No: ");
        String trainNo = sc.nextLine();

        List<String> trains = loadTrainLines();
        String[] trainData = null;

        for (String line : trains) {
            String[] d = line.split("\\|");
            if (d[0].equals(trainNo)) {
                trainData = d;
                break;
            }
        }

        if (trainData == null) {
            System.out.println("Invalid Train No.");
            return;
        }

        LocalDate journeyDate;

        while (true) {
            System.out.print("Journey Date (YYYY-MM-DD): ");
            String dateInput = sc.nextLine();

            try {
                journeyDate = LocalDate.parse(dateInput);

                if (journeyDate.isBefore(LocalDate.now())) {
                    System.out.println("Cannot book for past date.");
                } else {
                    break;
                }

            } catch (Exception e) {
                System.out.println("Invalid date format.");
            }
        }

        int sleeperAvailable = Integer.parseInt(trainData[5]);
        int acAvailable = Integer.parseInt(trainData[7]);

        System.out.println("1. Sleeper (Rs. 500)");
        System.out.println("2. AC (Rs. 1200)");

        int choice = sc.nextInt();
        sc.nextLine();

        String seatClass;
        int fare;

        if (choice == 1) {

            if (sleeperAvailable <= 0) {
                System.out.println("No Sleeper seats available.");
                return;
            }

            seatClass = "SLEEPER";
            fare = 500;
            sleeperAvailable--;
            trainData[5] = String.valueOf(sleeperAvailable);

        } else {

            if (acAvailable <= 0) {
                System.out.println("No AC seats available.");
                return;
            }

            seatClass = "AC";
            fare = 1200;
            acAvailable--;
            trainData[7] = String.valueOf(acAvailable);
        }

        // Update train file
        BufferedWriter bwTrain = new BufferedWriter(new FileWriter(TRAIN_FILE));
        for (String line : trains) {
            if (line.split("\\|")[0].equals(trainNo)) {
                bwTrain.write(String.join("|", trainData));
            } else {
                bwTrain.write(line);
            }
            bwTrain.newLine();
        }
        bwTrain.close();

        String passengerId = generateId("P");
        String ticketId = generateId("T");

        BufferedWriter bw1 = new BufferedWriter(new FileWriter(PASSENGER_FILE, true));
        bw1.write(passengerId + "|User|25|NA");
        bw1.newLine();
        bw1.close();

        BufferedWriter bw2 = new BufferedWriter(new FileWriter(TICKET_FILE, true));
        bw2.write(ticketId + "|" + passengerId + "|" + trainNo + "|"
                + journeyDate + "|" + seatClass + "|CONFIRMED|" + fare);
        bw2.newLine();
        bw2.close();

        System.out.println("Ticket Confirmed. Fare: Rs. " + fare);
    }

    // ================= DELETE TRAIN =================
    public void deleteTrain() throws Exception {

        System.out.print("Enter Train No to delete: ");
        String trainNo = sc.nextLine();

        BufferedReader br = new BufferedReader(new FileReader(TICKET_FILE));
        String ticketLine;

        while ((ticketLine = br.readLine()) != null) {
            String[] t = ticketLine.split("\\|");
            if (t.length >= 7 && t[2].equals(trainNo)
                    && t[5].equals("CONFIRMED")) {
                br.close();
                System.out.println("Cannot delete train. Active bookings exist.");
                return;
            }
        }
        br.close();

        List<String> trains = loadTrainLines();
        trains.removeIf(line -> line.split("\\|")[0].equals(trainNo));

        BufferedWriter bw = new BufferedWriter(new FileWriter(TRAIN_FILE));
        for (String line : trains) {
            bw.write(line);
            bw.newLine();
        }
        bw.close();

        System.out.println("Train Deleted Successfully.");
    }

    // ================= REVENUE =================
    public void showRevenue() throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(TICKET_FILE));
        String line;
        int total = 0;

        while ((line = br.readLine()) != null) {
            String[] d = line.split("\\|");
            if (d.length >= 7 && d[5].equals("CONFIRMED")) {
                total += Integer.parseInt(d[6]);
            }
        }

        br.close();
        System.out.println("Total Revenue: Rs. " + total);
    }

    // ================= UPDATE TRAIN =================
    public void updateTrain() throws Exception {

        System.out.print("Enter Train No to update: ");
        String trainNo = sc.nextLine();

        List<String> trains = loadTrainLines();
        boolean found = false;

        for (int i = 0; i < trains.size(); i++) {

            String[] d = trains.get(i).split("\\|");

            if (d[0].equals(trainNo)) {

                found = true;

                System.out.print("New Name: ");
                String name = sc.nextLine();

                System.out.print("New Source: ");
                String source = sc.nextLine();

                System.out.print("New Destination: ");
                String dest = sc.nextLine();

                int sleeper = getNonNegativeSeatInput("New Sleeper Seats: ");
                int ac = getNonNegativeSeatInput("New AC Seats: ");

                trains.set(i, trainNo + "|" + name + "|" + source + "|" + dest
                        + "|" + sleeper + "|" + sleeper + "|" + ac + "|" + ac);

                break;
            }
        }

        if (!found) {
            System.out.println("Train not found.");
            return;
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(TRAIN_FILE));
        for (String line : trains) {
            bw.write(line);
            bw.newLine();
        }
        bw.close();

        System.out.println("Train Updated Successfully.");
    }

    // ================= SEARCH TICKET =================
    public void searchTicket() throws Exception {

        System.out.print("Enter Ticket ID: ");
        String id = sc.nextLine();

        BufferedReader br = new BufferedReader(new FileReader(TICKET_FILE));
        String line;

        while ((line = br.readLine()) != null) {

            String[] d = line.split("\\|");

            if (d.length >= 7 && d[0].equals(id)) {

                System.out.println("Train: " + d[2]);
                System.out.println("Date: " + d[3]);
                System.out.println("Class: " + d[4]);
                System.out.println("Status: " + d[5]);
                System.out.println("Fare: Rs. " + d[6]);

                br.close();
                return;
            }
        }

        br.close();
        System.out.println("Ticket Not Found.");
    }

    // ================= CANCEL TICKET =================
    public void cancelTicket() throws Exception {

        System.out.print("Enter Ticket ID: ");
        String id = sc.nextLine();

        List<String> tickets = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(TICKET_FILE));
        String line;

        boolean found = false;
        boolean alreadyCancelled = false;

        while ((line = br.readLine()) != null) {

            String[] d = line.split("\\|");

            if (d.length >= 7 && d[0].equals(id)) {

                found = true;

                if (d[5].equals("CONFIRMED")) {

                    LocalDate journey = LocalDate.parse(d[3]);
                    long days = LocalDate.now().until(journey).getDays();

                    int fare = Integer.parseInt(d[6]);
                    int refund;

                    if (days >= 2)
                        refund = (int) (fare * 0.8);
                    else if (days == 1)
                        refund = (int) (fare * 0.5);
                    else
                        refund = 0;

                    System.out.println("Refund Amount: Rs. " + refund);

                    d[5] = "CANCELLED";
                    tickets.add(String.join("|", d));

                } else {
                    alreadyCancelled = true;
                    tickets.add(line);
                }

            } else {
                tickets.add(line);
            }
        }

        br.close();

        if (!found) {
            System.out.println("Invalid Ticket ID.");
            return;
        }

        if (alreadyCancelled) {
            System.out.println("Ticket already cancelled.");
            return;
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(TICKET_FILE));
        for (String t : tickets) {
            bw.write(t);
            bw.newLine();
        }
        bw.close();

        System.out.println("Ticket cancelled successfully.");
    }
}