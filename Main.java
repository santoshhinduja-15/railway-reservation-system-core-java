import java.util.Scanner;

public class Main {

    private static int getValidatedChoice(Scanner sc, int min, int max) {

        while (true) {

            System.out.print("Enter your choice (" + min + "-" + max + "): ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.next(); // clear invalid input
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            if (choice < min || choice > max) {
                System.out.println("Please enter a valid option between "
                        + min + " and " + max + ".");
            } else {
                return choice;
            }
        }
    }

    public static void main(String[] args) throws Exception {

        RailwayService service = new RailwayService();
        service.initializeFiles();

        Scanner sc = new Scanner(System.in);

        System.out.println("===== Railway Reservation System =====");

        try {

            while (true) {

                String role = null;

                // ================= LOGIN LOOP =================
                while (role == null) {
                    role = service.login();
                    if (role == null) {
                        System.out.println("Invalid credentials. Try again.\n");
                    }
                }

                System.out.println("Login Successful as " + role);

                // ================= ADMIN MENU =================
                if (role.equals("ADMIN")) {

                    boolean adminSession = true;

                    while (adminSession) {

                        System.out.println("\n========== ADMIN MENU ==========");
                        System.out.println("1. Add Train");
                        System.out.println("2. Update Train");
                        System.out.println("3. Delete Train");
                        System.out.println("4. Revenue Report");
                        System.out.println("5. Logout");
                        System.out.println("6. Exit");
                        System.out.println("================================");

                        int choice = getValidatedChoice(sc, 1, 6);

                        switch (choice) {

                            case 1:
                                service.addTrain();
                                break;

                            case 2:
                                service.updateTrain();
                                break;

                            case 3:
                                service.deleteTrain();
                                break;

                            case 4:
                                service.showRevenue();
                                break;

                            case 5:
                                adminSession = false;
                                System.out.println("Logged out successfully.\n");
                                break;

                            case 6:
                                System.out.println("Exiting system...");
                                return;
                        }
                    }
                }

                // ================= USER MENU =================
                else if (role.equals("USER")) {

                    boolean userSession = true;

                    while (userSession) {

                        System.out.println("\n========== USER MENU ==========");
                        System.out.println("1. Book Ticket");
                        System.out.println("2. Search Ticket");
                        System.out.println("3. Cancel Ticket");
                        System.out.println("4. Logout");
                        System.out.println("5. Exit");
                        System.out.println("================================");

                        int choice = getValidatedChoice(sc, 1, 5);

                        switch (choice) {

                            case 1:
                                service.bookTicket();
                                break;

                            case 2:
                                service.searchTicket();
                                break;

                            case 3:
                                service.cancelTicket();
                                break;

                            case 4:
                                userSession = false;
                                System.out.println("Logged out successfully.\n");
                                break;

                            case 5:
                                System.out.println("Exiting system...");
                                return;
                        }
                    }
                }
            }

        } finally {
            sc.close();
        }
    }
}
