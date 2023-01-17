import java.util.ArrayList;
import java.util.Scanner;

public class Main_Old {
    private static int flatRate = 10;
    public static void main(String[] args) {
        Scanner scanObj = new Scanner(System.in);

        ArrayList<User> users = new ArrayList<User>();
        InsuranceCompany comp1 = new InsuranceCompany("InsureMe", "admin","password", 100);

        // cars
        Car car1 = new Car("BWM X5",2012, CarType.SUV, 10000.00);  // create object of car
        Car car2 = new Car("KIA CERATO",2005, CarType.SED, 10000.00);  // create object of car
        Car car3 = new Car("ROLLS ROYCE PHANTOM",2017, CarType.LUX, 10000.00);  // create object of car
        Car car4 = new Car("FORD FOCUS",2022, CarType.HATCH, 10000.00);  // create object of car

        // expiry dates
        MyDate expiryDate1 = new MyDate(2022,5,10);
        MyDate expiryDate2 = new MyDate(2025,4,27);
        MyDate expiryDate3 = new MyDate(2026,3,31);
        MyDate expiryDate4 = new MyDate(2023,2,8);

        // policies
        ThirdPartyPolicy TPPobj1 = new ThirdPartyPolicy("Don",101, car1,1, expiryDate1, "GOOD"); // create dat object
        ComprehensivePolicy CPPobj1 = new ComprehensivePolicy("Ron",102, car2,2, expiryDate2,31, 3);
        ThirdPartyPolicy TPPobj2 = new ThirdPartyPolicy("Matt",103, car3,1, expiryDate3, "Very Good");
        ComprehensivePolicy CPPobj2 = new ComprehensivePolicy("Moss",104, car4,3, expiryDate4,31, 3);

        // Addresses
        Address address1 = new Address(10, "George Street", "Albert","Wollongong");
        Address address2 = new Address(11, "Beach Street", "Plaza","Sydney");
        Address address3 = new Address(12, "Evans Street", "Hope","Perth");
        Address address4 = new Address(13, "Rose Street", "Nope","Brisbane");

        // creating a lot of users
        User user1 = new User(TPPobj1.getPolicyHolderName(), TPPobj1.getId(), address1);
        User user2 = new User(TPPobj2.getPolicyHolderName(), TPPobj2.getId(), address2);
        User user3 = new User(CPPobj1.getPolicyHolderName(), CPPobj1.getId(), address3);
        User user4 = new User(CPPobj2.getPolicyHolderName(), CPPobj2.getId(), address4);

        // adding policies to user
        user1.addPolicy(TPPobj1);
        user2.addPolicy(TPPobj2);
        user3.addPolicy(CPPobj1);
        user4.addPolicy(CPPobj2);

        // add to users list
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        comp1.addUser(user1);
        comp1.addUser(user2);
        comp1.addUser(user3);
        comp1.addUser(user4);



        // ----------- login ------------
        System.out.println("Please login- ");
        do {
            System.out.print("Enter username: ");
            String usr = scanObj.next();
            System.out.print("Enter password: ");
            String pwd = scanObj.next();

            if (comp1.validateAdmin(usr,pwd)) {
                System.out.println("Login was successful!");
                break;
            }
            else {
                System.out.println("Login was unsuccessful!");
            }
        }while(true);


        System.out.println(); // line break


        // ------------ adding user ------------
        System.out.println("Add user using new user creation-");
        if (comp1.addUser("Tyson",104,address1)) {
            // the userID already exists hence it will not add the user
            System.out.println("User added successfully!");
        }
        else {
            System.out.println("User could not be added!");
        }

        System.out.println(); // line break

        System.out.println("Adding user using object-");
        if(comp1.addUser(user1)) {
            System.out.println("User added successfully!");
        }
        else {
            System.out.println("User could not be added!");
        }


        System.out.println(); // line break

        // adding policy
        System.out.println("Policy added: "+comp1.addPolicy(105,TPPobj1)); // not successful with wrong userID

        if (comp1.findPolicy(102,CPPobj1.id) == null) {
            // first check using the findPolicy if the policyID and userID already exists
            // duplicate policy
            System.out.println("Policy added: "+ comp1.addPolicy(102,CPPobj1));
        }

        System.out.println(); // line break

        // adding policy using createTPP() and createCPP()
        System.out.println("Created policy for user: " + user1.createComprehensivePolicy("Dan",101,car1,1,expiryDate1,21,2)); // userID already exists

        if (comp1.findPolicy(102,105) == null) {
            // duplicate
            System.out.println("Created policy for another user: " + user1.createThirdPartyPolicy("Dan",105,car3,3,expiryDate3,"Average"));
        }

        System.out.println(); // line break

        // ask user to enter user ID and print all policies
        System.out.print("Please enter your user ID: ");
        int cust_id = scanObj.nextInt();
        comp1.printPolicies(cust_id);

        System.out.println(); // line break

        // printing the policy with the given userID and the policy id
        System.out.print("Please enter your policy ID: ");
        int cust_pol_id = scanObj.nextInt();
        System.out.println(comp1.findPolicy(cust_id,cust_pol_id));

        System.out.println(); // line break

        // print all the users in the insurance company
        comp1.print();

        System.out.println(); // line break

        // rise the price of all cars for all users and print them
        comp1.carPriceRise(0.1);
        comp1.print();

        System.out.println(); // line break

        // calculate the total payment for the given cust_id
        System.out.println("Total payment for the user id "+ cust_id + " is " +comp1.calcTotalPayments(cust_id));

        System.out.println(); // line break

        // calculate total premiums for all users
        System.out.println("Total premium for all users: "+comp1.calcTotalPayments());

        System.out.println(); // line break

        // create a new array list and then call the printPolicies to print all the policies
        System.out.println("================ List of All Policies ================");
        ArrayList<InsurancePolicy> arr1 = comp1.allPolicies();
//        InsurancePolicy.printPolicies(arr1);

        System.out.println(); // line break

        System.out.println("================ List of all policies which maybe expired with given date ================");
        MyDate anotherDate = new MyDate(2027,10,26);
        ArrayList<InsurancePolicy> arr2 = comp1.filterByExpiryDate(cust_id,anotherDate);
//        InsurancePolicy.printPolicies(arr2); // in our case none of the policies are going to be expired

        System.out.println(); // line break

        // filter by car model
        System.out.println("================ Filter by the model of the car ================");
        System.out.println(comp1.filterByCarModel("BWM X5"));

        System.out.println(); // line break

        System.out.println("================ Filter the cars by date ================");
        System.out.println("Enter the following date details");
        System.out.print("Please enter year: ");
        int year = scanObj.nextInt();
        System.out.print("Please enter month: ");
        int month = scanObj.nextInt();
        System.out.print("Please enter day: ");
        int day = scanObj.nextInt();
        MyDate newDate = new MyDate(year,month,day);
        System.out.println(comp1.filterByExpiryDate(newDate)); //  check if it is expired


        System.out.println(); // line break

        User user007 = comp1.findUser(cust_id);
        System.out.println("================ Provide a new address ================ ");
        System.out.print("Enter the street number: ");
        int streetNum = scanObj.nextInt();

        System.out.print("Enter only the street name: ");
        String streetName = scanObj.next();

        System.out.print("Enter the suburb: ");
        String suburb = scanObj.next();

        System.out.print("Enter the city: ");
        String city = scanObj.next();

        Address addr1 = new Address(streetNum,streetName,suburb,city);
        user007.setAddress(addr1);

        System.out.println(user007);
    }
}
