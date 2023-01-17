import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final int flatRate = 10;
    public static Scanner scanObj = new Scanner(System.in);
    public static int count = 1000; // userid gen()
    public static void main(String[] args) throws CloneNotSupportedException, PolicyException, IOException, NameException {
        InsuranceCompany comp = new InsuranceCompany("Tata", "admin", "admin", flatRate);
        testObjects(comp); // get objects from sample data
        mainMenu(comp); // display the main menu

    }
    public static int genUserID() { count++; return count+1; }
    public static void displayMainMenu() {
//        System.out.print("\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\n1) Admin Login");
        System.out.println("2) User Login");
        System.out.println("3) Exit");
    }
    public static void mainMenu(InsuranceCompany comp) throws CloneNotSupportedException, PolicyException, IOException, NameException {
        int option = 0;
        // menu
        do {
            displayMainMenu();

            System.out.print("\nEnter between 1-3: ");
            option = takeInt();


            if (option == 1) {
                adminMenu(comp);
                pressEnter();  // press enter to continue()
            }
            else if (option == 2) {
                userMenu(comp);
                pressEnter();  // press enter to continue()
            }
            else if (option == 3) {
                System.out.println("\nThank you for using our services!");
            }
            else {
                System.out.println("\nDo you have eyes? Are you sure you are human? Please enter between 1-3(just for the laughs)!\n");
            }
        } while(option != 3);
    }

    public static void displayAdminMenu() {
//        System.out.print("\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\n1) Print the Test Code");
        System.out.println("2) Create a User");
        System.out.println("3) Create Third Party Policy");
        System.out.println("4) Create Comprehensive Policy");
        System.out.println("5) Print User Information");
        System.out.println("6) Filter by Car Model");
        System.out.println("7) Filter by Expiry Date");
        System.out.println("8) Update Address");
        System.out.println("9) Print report of payment per city");
        System.out.println("10) Remove Policy from user");
        System.out.println("11) Generate user ID!");
        System.out.println("12) Remove user");
        System.out.println("13) Change admin password");
        System.out.println("14) Report of Payments Per Car Model");
        System.out.println("15) Report car model for given user");
        System.out.println("16) Report city name across all users");
        System.out.println("17) Report car model across all users");
        System.out.println("0) Log Out");
    }

    public static void adminMenu(InsuranceCompany comp) throws CloneNotSupportedException, PolicyException, IOException, NameException {
        System.out.println("----------- Login ------------");

        scanObj.nextLine();

        System.out.print("Enter username: ");
        String usr = takeString();
        System.out.print("Enter password: ");
        String pwd = takeString();

        if (comp.validateAdmin(usr, pwd)) {
            System.out.println("Login was successful!");
        }
        else {
            System.out.println("Login was unsuccessful!");
            mainMenu(comp);
        }

        int option = -1;
        // menu
        do {
            displayAdminMenu();
            System.out.println(); // line break

            System.out.print("Enter between 0-15: ");
            option = takeInt();

            if (option == 1) {
                testCase(comp);
            }
            else if (option == 2) {
                // creating a user
                scanObj.nextLine(); // avoid skip

                System.out.print("Enter user name: ");
                String name = takeString();
                
                System.out.print("Enter user id:");
                int userId = takeInt();

                // address
                System.out.print("Enter street number:");
                int streetNum = takeInt();

                scanObj.nextLine(); // skip

                System.out.print("Enter street name:");
                String streetName = takeString();

                System.out.print("Enter suburb:");
                String suburb = takeString();

                System.out.print("Enter city:");
                String city = takeString();

                if (comp.addUser(new User(name, userId, new Address(streetNum, streetName, suburb, city)))) {
                    count++;
                    System.out.println("User added!");
                } else {
                    System.out.println("User not added!");
                }
            }
            else if (option == 3) {
                // create a third party policy
                System.out.print("Enter user id:");
                int userId = takeInt();

                if (comp.findUser(userId) == null) {
                    System.out.println("User does not exist!");
                    continue;
                }

                scanObj.nextLine(); // skip

                System.out.print("Enter policy holder name:");
                String polHN = takeString();

                System.out.print("Enter policy id:");
                int polId = takeInt(); // policy id

                Policy newId = null;
                try {
                    // Create a new Policy with an invalid ID
                    newId = new Policy(polId);
                } catch (PolicyException e) {
                    // Catch the PolicyException and print the error message
                    System.out.println(e);
                    newId = new Policy(e.getID());
                    polId = newId.getID();
                }

                System.out.print("Enter number of claims: ");
                int noc = takeInt();

                System.out.print("Enter car model:");
                String carModel = takeString();

                scanObj.nextLine(); // skip

                System.out.print("Enter car manufacturing year:");
                int carMFY = takeInt();

                scanObj.nextLine(); // skip

                System.out.print("Enter car type:");
                String carTypeT = takeString();
                CarType carType = null;

                if (carTypeT.equals("SUV")) {
                    carType = CarType.SUV;
                }
                else if (carTypeT.equals("SED")) {
                    carType = CarType.SED;
                }
                else if (carTypeT.equals("HATCH")){
                    carType = CarType.HATCH;
                }
                else if (carTypeT.equals("LUX")) {
                    carType = CarType.LUX;
                }

                System.out.print("Enter car price:");
                double carPrice = takeDouble();
                System.out.print("Please enter year: ");
                int year = takeInt();
                System.out.print("Please enter month: ");
                int month = takeInt();
                System.out.print("Please enter day: ");
                int day = takeInt();

                scanObj.nextLine(); // skip

                System.out.print("Enter comments:");
                String comments = takeString();

                if (comp.createThirdPartyPolicy(userId, polHN, polId, new Car(carModel, carMFY, carType, carPrice), noc, new MyDate(year, month, day), comments)) {
                    System.out.println("Policy added successfully!");
                }
                else {
                    System.out.println("Policy not added!");
                }
            }
            else if (option == 4) {
                // create a comprehensive policy
                System.out.print("Enter user id:");
                int userId = takeInt();

                scanObj.nextLine(); // skip

                System.out.print("Enter policy holder name:");
                String polHN = takeString();

                System.out.print("Enter policy id:");
                int polId = takeInt(); // policy id

                Policy newId = null;
                try {
                    // Create a new Policy with an invalid ID
                    newId = new Policy(polId);
                } catch (PolicyException e) {
                    // Catch the PolicyException and print the error message
                    System.out.println(e);
                    newId = new Policy(e.getID());
                    polId = newId.getID();
                }

                System.out.print("Enter number of claims: ");
                int noc = takeInt();

                scanObj.nextLine(); // skip

                System.out.print("Enter car model:");
                String carModel = takeString();

                System.out.print("Enter car manufacturing year:");
                int carMFY = takeInt();

                scanObj.nextLine(); // skip

                System.out.print("Enter car type:");
                String carTypeT = takeString();
                CarType carType = null;

                if (carTypeT.equals("SUV")) {
                    carType = CarType.SUV;
                }
                else if (carTypeT.equals("SED")) {
                    carType = CarType.SED;
                }
                else if (carTypeT.equals("HATCH")){
                    carType = CarType.HATCH;
                }
                else if (carTypeT.equals("LUX")) {
                    carType = CarType.LUX;
                }

                System.out.print("Enter car price:");
                int carPrice = takeInt();
                System.out.print("Please enter year: ");
                int year = takeInt();
                System.out.print("Please enter month: ");
                int month = takeInt();
                System.out.print("Please enter day: ");
                int day = takeInt();

                System.out.print("Please enter driver age: ");
                int driverAge = takeInt();

                System.out.print("Please enter driver level: ");
                int level = takeInt();

                scanObj.nextLine(); // skip

                comp.createComprehensivePolicy(userId, polHN, polId, new Car(carModel, carMFY, carType, carPrice), noc, new MyDate(year, month, day), driverAge, level) ;
                System.out.println("Policy added successfully!");
            }
            else if (option == 5) {
                // print policies for specific user
                System.out.print("Enter user ID: ");
                int userId = takeInt();
                if (comp.findUser(userId) != null) {
                    comp.printPolicies(userId);
                }
                else {
                    System.out.println("User does not exist!");
                }

            }
            else if (option == 6) {
                // filter by car model
                scanObj.nextLine(); // skip

                System.out.print("Enter car model: ");
                String carModel = takeString();

                ArrayList<InsurancePolicy> filteredList = new ArrayList<InsurancePolicy>(comp.filterByCarModel(carModel));
                for (InsurancePolicy pol: filteredList) {
                    System.out.println(pol);
                }
            }
            else if (option == 7) {
                // filter by expiry date
                System.out.print("Please enter year: ");
                int year = takeInt();
                System.out.print("Please enter month: ");
                int month = takeInt();
                System.out.print("Please enter day: ");
                int day = takeInt();
                MyDate newDate = new MyDate(year,month,day);
                ArrayList<InsurancePolicy> filteredList = new ArrayList<InsurancePolicy>(comp.filterByExpiryDate(newDate));
                for (InsurancePolicy pol: filteredList) {
                    System.out.println(pol);
                }
            }
            else if (option == 8) {
                // change address
                System.out.print("Please enter the user ID: ");
                int userID = takeInt();

                System.out.print("Enter the street number: ");
                int streetNum = takeInt();

                scanObj.nextLine(); // skip
                
                System.out.print("Enter only the street name: ");
                String streetName = takeString();
                System.out.print("Enter the suburb: ");
                String suburb = takeString();
                System.out.print("Enter the city: ");
                String city = takeString();

                if (comp.findUser(userID) != null) {
                    comp.findUser(userID).setAddress(new Address(streetNum, streetName, suburb, city));
                    System.out.println("Address changed!");
                }
                else {
                    System.out.println("Invalid user ID!");
                }
            }
            else if (option == 9) {
                // generate the report of payment per city
                System.out.println("----------- Report of Payment Per City -----------");
                comp.reportPaymentPerCity(comp.populateDistinctCityNames(), comp.getTotalPaymentPerCity(comp.populateDistinctCityNames()));
            }
            else if (option == 10) {
                // remove policy from user
                System.out.print("Enter user id: ");
                int userID = takeInt();
                System.out.print("Enter policy id: ");
                int policyId = takeInt();
                if (comp.findUser(userID).removePolicy(comp.findUser(userID).findPolicy(policyId))) {
                    System.out.println("Policy has been removed!");
                }else {
                    System.out.println("Policy could not be removed!");
                }
            }
            else if (option == 11) {
                // gen userid
                System.out.println(genUserID());
            }
            else if (option == 12) {
                // remove user
                System.out.print("Enter user id: ");
                int userID = takeInt();
                if (comp.removeUser(comp.findUser(userID))) {
                    System.out.println("User has been removed!");
                }
                else {
                    System.out.println("User could not be removed!");
                }
            }
            else if (option == 13) {
                // change password
                System.out.print("Enter new password: ");
                String pass = scanObj.next();
                System.out.print("Enter new password: ");
                String repass = scanObj.next();

                if (pass.equals(repass)) {
                    comp.setAdminPassword(pass);
                    System.out.println("Password has been changed!");
                }
            }
            else if (option == 14) {
                // generate the report of payment per car model
                comp.reportPaymentsPerCarModel(comp.populateDistinctCarModels(), comp.getTotalCountPerCarModel(comp.populateDistinctCarModels()), comp.getTotalPaymentPerCarModel(comp.populateDistinctCarModels()));
            }
            else if (option == 15) {
                // generate the report of payment per car model
                comp.reportPaymentsPerCarModel(comp.populateDistinctCarModels(), comp.getTotalCountPerCarModel(comp.populateDistinctCarModels()), comp.getTotalPaymentPerCarModel(comp.populateDistinctCarModels()));
            }
            else if (option == 16) {
                System.out.println("Enter user id: ");
                int userID = takeInt();
                User temp_usr = new User(comp.findUser(userID));
                temp_usr.reportPaymentsPerCarModel(temp_usr.getTotalCountPerCarModel(), temp_usr.getTotalPremiumPerCarModel(10000));
                comp.reportPaymentsPerCarModel(comp.getTotalCountPerCarModel(), comp.getTotalPremiumPerCarModel());

            }
            else if (option == 17) {
                comp.reportPaymentPerCity(comp.getTotalPremiumPerCity());
            }
            else if (option == 18) {
                comp.reportPaymentsPerCarModel(comp.getTotalCountPerCarModel(), comp.getTotalPremiumPerCarModel());
            }
            else if (option == 0) {
                System.out.println("\nThank you for using our services!");
            }
            else {
                System.out.println("\nDo you have eyes? Are you sure you are human? Please enter between 1-12(just for the laughs)!\n");
            }
        } while(option != 0);
    }

    public static void displayUserMenu() {
//        System.out.print("\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\n1) Add a policy");
        System.out.println("2) Find a policy");
        System.out.println("3) Print policies");
        System.out.println("4) Change Address");
        System.out.println("5) Calculate Total Premium");
        System.out.println("6) Print User info");
        System.out.println("7) Remove Policy");
        System.out.println("8) Report of Payments Per Car Model");
        System.out.println("0) Exit");
    }
    public static void userMenu(InsuranceCompany comp) {
        System.out.print("Enter user id: ");
        int usrID = takeInt();
        User usr = comp.findUser(usrID);
        if (usr != null) {
            int option = -1;

            // menu
            do {
                displayUserMenu();

                System.out.println(); // line break

                System.out.print("Enter between 0-8: ");
                option = takeInt();

                if(option == 1) {
                    // add policy
                    System.out.println("1) Comprehensive Policy");
                    System.out.println("2) Third Party Policy");
                    System.out.println("Select type of policy: ");
                    int polC = takeInt();
                    if (polC == 1) {
                        scanObj.nextLine(); // skip
                        System.out.print("Enter policy holder name:");
                        String polHN = takeString();

                        System.out.print("Enter policy id:");
                        int polId = takeInt();

                        System.out.print("Enter number of claims: ");
                        int noc = takeInt();

                        scanObj.nextLine(); // skip

                        System.out.print("Enter car model:");
                        String carModel = takeString();

                        System.out.print("Enter car manufacturing year:");
                        int carMFY = takeInt();

                        System.out.print("Enter car type:");
                        String carTypeT = scanObj.next();
                        CarType carType = null;

                        if (carTypeT.equals("SUV")) {
                            carType = CarType.SUV;
                        } else if (carTypeT.equals("SED")) {
                            carType = CarType.SED;
                        } else if (carTypeT.equals("HATCH")) {
                            carType = CarType.HATCH;
                        } else if (carTypeT.equals("LUX")) {
                            carType = CarType.LUX;
                        }

                        System.out.print("Enter car price:");
                        int carPrice = takeInt();
                        System.out.print("Please enter year: ");
                        int year = takeInt();
                        System.out.print("Please enter month: ");
                        int month = takeInt();
                        System.out.print("Please enter day: ");
                        int day = takeInt();

                        scanObj.nextLine(); // skip

                        System.out.print("Enter comments:");
                        String comments = takeString();

                        if (usr.addPolicy(new ThirdPartyPolicy(polHN, polId, new Car(carModel, carMFY, carType, carPrice), noc, new MyDate(year, month, day), comments))) {
                            System.out.println("Policy added successfully!");
                        } else {
                            System.out.println("Policy not added!");
                        }
                    }
                    else if(polC == 2) {
                        scanObj.nextLine(); // skip
                        System.out.print("Enter policy holder name:");
                        String polHN = takeString();


                        System.out.print("Enter policy id:");
                        int polId = takeInt();

                        System.out.print("Enter number of claims: ");
                        int noc = takeInt();

                        System.out.print("Enter car model:");
                        String carModel = takeString();

                        System.out.print("Enter car manufacturing year:");
                        int carMFY = takeInt();

                        System.out.print("Enter car type:");
                        String carTypeT = scanObj.next();
                        CarType carType = null;

                        if (carTypeT.equals("SUV")) {
                            carType = CarType.SUV;
                        }
                        else if (carTypeT.equals("SED")) {
                            carType = CarType.SED;
                        }
                        else if (carTypeT.equals("HATCH")) {
                            carType = CarType.HATCH;
                        }
                        else if (carTypeT.equals("LUX")) {
                            carType = CarType.LUX;
                        }

                        System.out.print("Enter car price:");
                        int carPrice = takeInt();
                        System.out.print("Please enter year: ");
                        int year = takeInt();
                        System.out.print("Please enter month: ");
                        int month = takeInt();
                        System.out.print("Please enter day: ");
                        int day = takeInt();

                        System.out.print("Please enter driver age: ");
                        int driverAge = takeInt();

                        System.out.print("Please enter driver level: ");
                        int level = takeInt();

                        scanObj.nextLine(); // skip
                        if (usr.addPolicy(new ComprehensivePolicy(polHN, polId, new Car(carModel, carMFY, carType, carPrice), noc, new MyDate(year, month, day), driverAge, level))) {
                            System.out.println("Policy added successfully!");
                        }
                        else {
                            System.out.println("Policy not added!");
                        }
                    }
                    else {
                        System.out.println("Enter 1 or 2!");
                    }
                }
                else if (option == 2) {
                    // find policy
                    System.out.print("Enter policy id: ");
                    int policyId = takeInt();
                    System.out.println(usr.findPolicy(policyId));;
                }
                else if (option == 3) {
                    // print the policies
                    usr.printPolicies(flatRate);
                }
                else if (option == 4) {
                    // change address
                    System.out.print("Enter the street number: ");
                    int streetNum = Integer.parseInt(scanObj.next());

                    scanObj.nextLine(); // skip

                    System.out.print("Enter only the street name: ");
                    String streetName = takeString();
                    System.out.print("Enter the suburb: ");
                    String suburb = scanObj.next();
                    System.out.print("Enter the city: ");
                    String city = scanObj.next();

                    usr.setAddress(new Address(streetNum, streetName, suburb, city));
                    System.out.println("Address changed!");
                }
                else if (option == 5) {
                    // Calculate Total Premium
                    System.out.println("Your total: "+usr.calcTotalPremiums(flatRate));
                }
                else if (option == 6) {
                    // print user info
                    System.out.println(usr);
                }
                else if (option == 7) {
                    // remove policy
                    System.out.print("Enter policy id: ");
                    int policyId = takeInt();
                    if (usr.removePolicy(usr.findPolicy(policyId))) {
                        System.out.println("Policy has been removed!");
                    } else {
                        System.out.println("Policy could not be removed!");
                    }
                }
                else if (option == 8) {
                    // generate the report for this user
                    System.out.println("----------- Report of Payment Per Car Model -----------");
                    usr.reportPaymentsPerCarModel(usr.populateDistinctCarModels(),usr.getTotalCountPerCarModel(usr.populateDistinctCarModels()), usr.getTotalPaymentPerCarModel(usr.populateDistinctCarModels(),flatRate));
                }
                else if (option == 0) {
                    System.out.println("\nThank you for using our user services!");
                }
                else {
                    // for all other numbers cases
                    System.out.println("\nDo you have eyes? Are you sure you are human? Please enter between 0-6(just for the laughs)!\n");
                }
            } while(option != 0);
        }
        else {
            // if the user id is incorrect
            System.err.println("User not found!");
        }
    }

    public static void pressEnter() {
        System.out.println("Press any key to continue...");
        /* ---------- press enter try-catch ---------- */
        try {
            System.in.read();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        /* ---------- press enter try-catch ---------- */
    }

    public static void testObjects(InsuranceCompany company) {
        /* ---------- Sample data ---------- */
        // cars
        Car car1 = new Car("BMW X5", 2012, CarType.SUV, 65000);  // create object of car
        Car car2 = new Car("KIA CERATO", 2005, CarType.SED, 25000);  // create object of car
        Car car3 = new Car("ROLLS ROYCE PHANTOM", 2017, CarType.LUX, 120000);  // create object of car

        // Addresses
        Address address1 = new Address(10, "George Street", "Parramatta", "Wollongong");
        Address address2 = new Address(11, "Beach Street", "Chatswood", "Sydney");
        Address address3 = new Address(12, "Evans Street", "Mascot", "Perth");

        // users
        User user1 = new User("Eve", 1001, address1); count++;
        User user2 = new User("Isaac", 1002, address2); count++;
        User user3 = new User("Aaron", 1003, address3); count++;

        // adding the users to the company
        company.addUser(user1);
        company.addUser(user2);
        company.addUser(user3);

        // policy for user 1
        company.createThirdPartyPolicy(1001, "Eve", 3001, car1, 1, new MyDate(2022, 12, 10), "Good");
        company.createComprehensivePolicy(1001, "Adam", 3002, car1, 2, new MyDate(2026, 3, 6), 31, 10);

        // policy for user 2
        company.createThirdPartyPolicy(1002, "Isaac", 3003, car2, 1, new MyDate(2023, 6, 31), "Very Good");
        company.createComprehensivePolicy(1002,"Lizzy", 3004, car2, 3, new MyDate(2027, 10, 16), 31, 1);

        // policy for user 3
        company.createThirdPartyPolicy(1003, "Aaron", 3003, car3, 1, new MyDate(2020, 2, 7), "Average");
        company.createComprehensivePolicy(1003,"Julie", 3004, car3, 3, new MyDate(2027, 1, 26), 31, 1);

    }

    public static void testCase(InsuranceCompany company) throws CloneNotSupportedException, IOException, PolicyException, NameException {
        // ----------- login ------------
        System.out.println("------------ Login ------------ ");
        do {
            String usr = "admin";
            String pwd = "admin";

            if (company.validateAdmin(usr,pwd)) {
                System.out.println("Login was successful!");
                break;
            }
            else {
                System.out.println("Login was unsuccessful!");
            }
        } while(true);

        System.out.println(); // line break

        // ----------- print policies ------------
        System.out.println(" ----------- print policies ------------ ");
        company.print();
        System.out.println(); // line break
        System.out.println(company); // print using toString()

        // ----------- find policies ------------
        System.out.println("----------- Finding policy using findPolicy() -----------");
        if(company.findPolicy(1001,3001) == null) {
            System.out.println("Couldn't find policy!");
        }
        else {
            System.out.println("Policy: \n" + company.findPolicy(1001,3001));
        }

        System.out.println(); // line break

        // ----------- find user ------------
        System.out.println("----------- Finding user using findUser() -----------");
        if(company.findUser(1003) == null) {
            System.out.println("Couldn't find User!");
        }
        else {
            System.out.println("User: " + company.findUser(1003));
        }

        System.out.println(); // line break

        // ----------- print all policies ------------
        System.out.println("----------- Printing all policies -----------");
        for (InsurancePolicy pol: company.allPolicies()) {
            System.out.println(pol);
        }

        System.out.println(); // line break

        // ----------- filter by car model ------------
        System.out.println("----------- Filter by Car Model using carModel -----------");
        ArrayList<InsurancePolicy> filteredList1 = new ArrayList<InsurancePolicy>(company.filterByCarModel("BMW X5"));
        for (InsurancePolicy pol: filteredList1) {
            System.out.println(pol);
        }

        System.out.println(); // line break

        System.out.println("----------- Filter by Car Model using userID and carModel -----------");
        ArrayList<InsurancePolicy> filteredList2 = new ArrayList<InsurancePolicy>(company.filterByCarModel(1002, "KIA CERATO"));
        for (InsurancePolicy pol: filteredList2) {
            System.out.println(pol);
        }

        System.out.println(); // line break

        // ----------- filter by expiry date ------------
        System.out.println("----------- Filter by Expiry Date for given userID -----------");
        ArrayList<InsurancePolicy> filteredList3 = new ArrayList<InsurancePolicy>(company.filterByExpiryDate(1001, new MyDate(2025,4,26)));
        for (InsurancePolicy pol: filteredList3) {
            System.out.println(pol);
        }

        System.out.println(); // line break

        System.out.println("----------- Filter by expiry date -----------");
        ArrayList<InsurancePolicy> filteredList4 = new ArrayList<InsurancePolicy>(company.filterByExpiryDate(new MyDate(2027,10,26)));
        for (InsurancePolicy pol: filteredList4) {
            System.out.println(pol);
        }

        System.out.println(); // line break

        // ----------- calculate payments ------------
        System.out.println("----------- Calculate Payments  -----------");
        System.out.println("Total for user(1001) = " + company.calcTotalPayments(1001));
        System.out.println("Total for all users = " + company.calcTotalPayments());

        System.out.println(); // line break

        System.out.println("----------- Car Price Rise  -----------");
        company.print();

        System.out.println(); // line break

        company.carPriceRise(0.1); // raise the price for all users(cars)
        System.out.println("----------- Price after rise  -----------");
        company.print();

        System.out.println(); // line break

        System.out.println("----------- Populating distinct city names  -----------");
        System.out.println(company.populateDistinctCityNames());

        System.out.println(); // line break

        System.out.println("----------- City Total Payments  -----------");
        System.out.println("Wollongong: "+company.getTotalPaymentForCity("Wollongong"));
        System.out.println("Sydney: "+company.getTotalPaymentForCity("Sydney"));
        System.out.println("Perth: "+company.getTotalPaymentForCity("Perth"));

        System.out.println(company.getTotalPaymentPerCity(company.populateDistinctCityNames()));
        System.out.println("----------- Table -----------");
        company.reportPaymentPerCity(company.populateDistinctCityNames(), company.getTotalPaymentPerCity(company.populateDistinctCityNames()));

        System.out.println(); // line break

        try {
            // deep copy
            ArrayList<User> deepUserList = company.deepCopyUsers();
            ArrayList<User> deepUserList1 = company.findUser(1001).deepCopy(deepUserList);
            ArrayList<InsurancePolicy> deepPolicyList = company.findUser(1001).deepCopyPolicies();

            // shallow copy
            ArrayList<User> shallowUserList = company.shallowCopyUsers();
            ArrayList<InsurancePolicy> shallowPolList = company.findUser(1001).shallowCopyPolicies();


            // print deep copy
            System.out.println("---------- Deep Copy Policies----------");
            for (InsurancePolicy policy : deepPolicyList) {
                System.out.println(policy);
            }
            System.out.println(); // line break
            System.out.println("---------- Deep Copy Users ----------");
            for (User user : deepUserList) {
                System.out.println(user);
            }

            System.out.println(); // line break

            // print shallow copy
            System.out.println("---------- Shallow Copy Policies ----------");
            for (InsurancePolicy policy : shallowPolList) {
                System.out.println(policy);
            }
            System.out.println("---------- Shallow Copy Users ----------");
            for (User user : shallowUserList) {
                System.out.println(user);
            }

            // Changing user city
            System.out.println("---------- Change City Name as New York ----------\n");
            company.findUser(1001).getAddress().setCity("New York");
            // Sort policies by expiryDate
            ArrayList<InsurancePolicy> sorted = company.findUser(1001).sortPoliciesByDate();

            System.out.println("---------- Sorted ----------");
            for (InsurancePolicy policy : sorted) {
                System.out.println(policy);
            }

            // Deep copy user
            ArrayList<User> deepUserListt = company.deepCopyUsers();
            System.out.println("---------- Deep copy User ----------");
            for (User u : deepUserListt) {
                System.out.println(u);
            }
            // shallow copy user
            ArrayList<User> shallowUserListt = company.shallowCopyUsers();
            // Add a new user after shallow copy
            company.addUser(new User("Bot",1005, new Address(80, "Evans","Illawara","Wollongong")));

            System.out.println(); // line break

            System.out.println("---------- shallow copy User ----------");
            for (User u : shallowUserList) {
                System.out.println(u);
            }

            System.out.println(); // line break

            // Sorting user
            ArrayList<User> sortedUser = company.sortUsers();
            System.out.println("---------- User in sorted form----------");
            for (User u : sortedUser) {
                System.out.println(u);
            }

            ThirdPartyPolicy clonedPolicy = (ThirdPartyPolicy)company.findPolicy(1001, 3001).clone();
            System.out.println("---------- Cloned policy ----------");
            System.out.println(clonedPolicy);
        }
        catch (CloneNotSupportedException ex) {
            System.out.println(ex);
        }

        System.out.println(); // line break


        System.out.println("------------------------------ Lab 6 ------------------------------");
        System.out.println("---------- testing binary file and list of policies ----------");
        HashMap<Integer,InsurancePolicy> policies = InsurancePolicy.load("policies.ser");
        InsurancePolicy.printPolicies(policies);

        policies.put(3004, new ThirdPartyPolicy("Paul",30000001,new Car("BMW",2018,CarType.LUX,50000.00), 1, new MyDate(2022, 10, 1), "GOOD"));
        InsurancePolicy.save(policies,"policies.ser");
        policies.clear();


        System.out.println("---------- Loading the saved policies ----------");
        policies = InsurancePolicy.load("policies.ser");
        InsurancePolicy.printPolicies(policies);

        System.out.println(); // line break;


        System.out.println("---------- Loading and Saving users ----------");
        HashMap<Integer,User> users = User.load("users.ser");
        User.printUsers(users);

        User user = new User("Paul", 1100, new Address(80, "Evans","Illawara","Wollongong"));
        user.addPolicy(new ThirdPartyPolicy("Paul",30000001,new Car("BMW",2018,CarType.LUX,50000.00), 1, new MyDate(2022, 10, 1), "GOOD"));
        users.put(1100,user);
        User.save(users,"users.ser");
        users.clear();

        System.out.println(); // line break
        users = User.load("users.ser");
        User.printUsers(users);

        System.out.println(); //line break


        System.out.println("---------- Loading and Saving Insurance Company ----------");
        InsuranceCompany insuranceCompany1 = new InsuranceCompany();//using default constructor
        insuranceCompany1.load("company.ser");//the whole company including all users and all policies are loaded
        System.out.println(insuranceCompany1);

        insuranceCompany1.addUser(user);
        insuranceCompany1.addPolicy(1100, new ThirdPartyPolicy("Paul",30000001,new Car("Tesla",2028,CarType.SED,70000.00), 1, new MyDate(2023, 10, 1), "GOOD"));
        insuranceCompany1.save("company.ser");
        InsuranceCompany insuranceCompany2=new InsuranceCompany();

        insuranceCompany2.load("company.ser");
        System.out.println(insuranceCompany2);

        System.out.println(); // line break

        // txt testing
        System.out.println("---------- save policy hash map to text file for testing ----------");
        InsurancePolicy.saveTextFile(policies, "policies.txt");

        System.out.println("---------- testing text file and list of policies with toDilimitedString ----------");
        HashMap<Integer,InsurancePolicy> policies0 = InsurancePolicy.loadTextFile("policies.txt");
        InsurancePolicy.printPolicies(policies0);

        policies0.put(3111111, new ThirdPartyPolicy("Paul",3111111,new Car("BMW",2018,CarType.LUX,50000.00), 1, new MyDate(2022, 10, 1), "GOOD"));
        InsurancePolicy.saveTextFile(policies0,"policies.txt");
        policies0.clear();

        policies0=InsurancePolicy.loadTextFile("policies.txt");
        InsurancePolicy.printPolicies(policies0);

        insuranceCompany2.saveTextFile("company.txt"); //ic
        InsurancePolicy.saveTextFile(policies, "policies.txt"); //ip
        User.saveTextFile(users, "users.txt"); //user

        HashMap<Integer,InsurancePolicy> textPolicies=InsurancePolicy.loadTextFile("policies.txt");
        InsurancePolicy.printPolicies(textPolicies);
        System.out.println();
        textPolicies.put(3111111, new ThirdPartyPolicy("Paul",3111111,new Car("BMW",2018,CarType.LUX,50000.00), 1, new MyDate(2022, 10, 1), "GOOD"));
        InsurancePolicy.saveTextFile(textPolicies,"policies.txt");

        textPolicies=InsurancePolicy.loadTextFile("policies.txt");
        InsurancePolicy.printPolicies(textPolicies);
        System.out.println();

        HashMap<Integer,User> textUsers=User.loadTextFile("users.txt");
        User.printUsers(textUsers);
        System.out.println();

        User user2=new User("Luke", 1100, new Address(80, "Bond Street", "Surry Hill", "Sydney"));
        user2.addPolicy(new ThirdPartyPolicy("Paul", 3333666, new Car("BMW",2018,CarType.HATCH,60000.00), 0, new MyDate(2022, 10, 10), "Very Good"));
        textUsers.put(user2.getUserID(),user2);
        User.saveTextFile(textUsers, "users.txt");
        textUsers.clear();

        textUsers=User.loadTextFile("users.txt");
        User.printUsers(textUsers);
        System.out.println();

        System.out.println("\n-----------------------Insurance Company Text File Test-----------------------------\n\n");

        //InsuranceCompany and text file
        InsuranceCompany insuranceCompany3=new InsuranceCompany();//using default constructor
        insuranceCompany3.loadTextFile("company.txt");//the whole company including all users and all policies are loaded
        System.out.println(insuranceCompany3);
        System.out.println();

        insuranceCompany3.addUser(user2);
        insuranceCompany3.addPolicy(user2.getUserID(), new ComprehensivePolicy("Dan", 3112223, new Car("BMW",2018,CarType.HATCH,60000), 0, new MyDate(2023, 1, 10), 36, 2));
        insuranceCompany3.saveTextFile("company.txt");
        InsuranceCompany insuranceCompany4=new InsuranceCompany();

        try {
            insuranceCompany4.loadTextFile("company.txt");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(insuranceCompany4);
        System.out.println();
    }

    public static int takeInt() {
        int inp = 0;
        boolean input = false;
        do {
            try {
                inp = scanObj.nextInt();;
                input = false;
                return inp;
            } catch (InputMismatchException ex) {
                scanObj.nextLine();
                input = true;
                System.out.println("Incorrect input, please try again");
            }
        } while (input);
        return inp;
    }
    public static String takeString() {
        String inp = "";
        boolean input = false;
        do {
            try {
                inp = scanObj.nextLine();
                return inp;
            }
            catch (InputMismatchException ex) {
                scanObj.nextLine();
                input = true;
                System.out.println("Incorrect input, please try again: ");
            }
        } while (input);
        return inp;

    }
    public static Double takeDouble() {
        double inp = 0;
        boolean input = false;

        do {
            try {
                inp = scanObj.nextDouble();
                return inp;
            }
            catch (InputMismatchException ex) {
                scanObj.nextLine();
                input = true;
                System.out.println("Incorrect input, please try again");
            }
        } while (input);
        return inp;
    }

}
