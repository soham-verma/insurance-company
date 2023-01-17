import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.io.Serializable;

public class User implements Comparable<User>, Cloneable, Serializable {
    private String name;
    private int userID;
    private Address address;
//    public ArrayList<InsurancePolicy> policies = new ArrayList<InsurancePolicy>();
    HashMap<Integer, InsurancePolicy> policies;

    // integer = stores the policy id
    // InsurancePolicy = stores the policy object

    public User(String _name, int _userID, Address _address) {
        // default constructor
        name = _name;
        userID = _userID;
        address = _address;
        this.policies = new HashMap<Integer, InsurancePolicy>();
    }
    public User(User user) {
        // copy constructor
        this.name = user.name;
        this.userID = user.userID;
        this.address = user.address;
        this.policies = new HashMap<Integer, InsurancePolicy>();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        User cloned = (User)super.clone();
        cloned.setAddress((Address)cloned.getAddress().clone());
        return cloned;
    }
    @Override
    public int compareTo(User u) {
        return this.address.compareTo(u.address);
    }

    public int compareTo1(User u) {
        return Double.compare(calcTotalPremiums(10), u.calcTotalPremiums(10));
    }

    public void setAddress(Address address) { this.address = address; }
    public String getCity() { return address.city; }
    public int  getUserID() { return userID; }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    //    public ArrayList<InsurancePolicy> getPolicies() { return policies; }

    public HashMap<Integer, InsurancePolicy> getPolicies() { return policies; }
    public Address getAddress() { return address; }


    public InsurancePolicy findPolicy (int policyID) {
//        for (InsurancePolicy policy: policies) {
        for (InsurancePolicy policy: policies.values()) {
            if (policy.getId()==policyID) {
                return policy;
            }
        }
        return null;
    }
    public boolean addPolicy (InsurancePolicy policy) {
        if (findPolicy(policy.getId())==null) {
//            policies.add(policy);
            policies.put(policy.getId(), policy);
            return true;
        }
        else { return false; }
    }
    public boolean removePolicy (InsurancePolicy policy) {
        if (findPolicy(policy.getId()) != null) {
            policies.remove(policy.getId());
            return true;
        }
        else { return false; }
    }
    public void print() {
        System.out.println("Name: "+name + " User ID: "+userID + " Address: "+address.streetNum + ","+address.street+","+address.suburb+","+address.city);
        // using hashmap
        for (InsurancePolicy policy: policies.values()) {
            policy.print();
        }
    }

    @Override
    public String toString() {
        // using hashmap
        String s = "";
        for (InsurancePolicy policy:policies.values()) {
            s += name + " "+userID + " "+address.streetNum + ","+address.street + ","+address.suburb + "," + address.city + "\n"+policy;
        }
        return s;
    }


    public String toDelimitedString()
    {
        String output = name + "," + userID + "," + address.toDelimitedString() + "," + policies.size();
        for (InsurancePolicy policy : policies.values()) {
            output+= "," + policy.toDelimitedString();
        }

        return output;
    }
//    public String toDelimitedString() {
//        String policiesString = "";
//        for (InsurancePolicy policy : policies.values()) {
//            policiesString += policy.toDelimitedString() + ",";
//        }
//        policiesString = policiesString.substring(0, policiesString.length() - 1);
//        return name + "," + userID + "," + address.toDelimitedString() + "," + policiesString;
//    }

    public static void printUsers(HashMap<Integer, User> users) {
        for (User user : users.values()) {
            System.out.println(user);
        }
    }

    public void printPolicies(int flatRate) {
        for (InsurancePolicy policy: policies.values()) {
            policy.print();
            policy.calcPayment(flatRate);
        }
    }
    public double calcTotalPremiums (int flatRate) {
        return InsurancePolicy.calcTotalPayments(policies, flatRate);
    }
    public void carPriceRiseAll (double risePercent) {
        InsurancePolicy.carPriceRiseAll(policies, risePercent);
    }
    public ArrayList<InsurancePolicy> filterByCarModel (String carModel) {
        return InsurancePolicy.filterByCarModel(policies,carModel);
    }
    public boolean createThirdPartyPolicy(String policyHolderName, int id, Car car, int numberOfClaims, MyDate expiryDate, String comments) {
        return addPolicy(new ThirdPartyPolicy(policyHolderName, id, car, numberOfClaims, expiryDate, comments));
    }
    public boolean createComprehensivePolicy(String policyHolderName, int id, Car car, int numberOfClaims, MyDate expiryDate, int driverAge, int level) {
        return addPolicy(new ComprehensivePolicy(policyHolderName, id, car, numberOfClaims, expiryDate, driverAge, level));
    }
    public ArrayList<InsurancePolicy> filterByExpiryDate (MyDate date) {
        return InsurancePolicy.filterByExpiryDate(policies,date);
    }
    public ArrayList<String> populateDistinctCarModels() {

        ArrayList<String> carModels =  new ArrayList<String>();
        for (InsurancePolicy pol: policies.values()) {
            if (!(carModels.contains(pol.getCar().getModel()))) {
                carModels.add(pol.getCar().getModel());
            }
        }
        return carModels;
    }
    public double getTotalCountForCarModel(String carModel)  {
        double carModelCount = 0;
        for (InsurancePolicy p:policies.values()) {
            if(Objects.equals(p.getCar().model, carModel)) {
                carModelCount++;
            }
        }
        return carModelCount;
    }
    public double getTotalPaymentForCarModel(String carModel, int flatRate) {
        double total = 0;
        for(InsurancePolicy p: policies.values()){
            if ((p.getCar().getModel()).equals(carModel)) {
                total+=p.calcPayment(flatRate);
            }
        }
        return total;
    }
    public ArrayList<Integer> getTotalCountPerCarModel (ArrayList<String> carModels) {
        ArrayList<Integer> count = new ArrayList<Integer>();
        for (String cm: carModels) {
            count.add((int)getTotalCountForCarModel(cm));
        }
        return count;
    }
    public ArrayList<Double> getTotalPaymentPerCarModel (ArrayList<String> carModels, int flatRate) {
        ArrayList<Double> carModelCount =  new ArrayList<Double>();
        for (String cm: carModels) {
            carModelCount.add(getTotalPaymentForCarModel(cm,flatRate));
        }
        return carModelCount;
    }
    public static void reportPaymentsPerCarModel (ArrayList<String> carModels, ArrayList<Integer>counts, ArrayList<Double> premiumPayments) {
        System.out.printf("%-20s %-15s %s %n", "Car Model", "Total Premium Payment", "Average Premium Payment");
        for(int i = 0; i < carModels.size(); i++) {
            System.out.printf("%-25s $%-23.2f $%.2f%n",carModels.get(i),premiumPayments.get(i),(premiumPayments.get(i)/counts.get(i)));
        }
    }
    public static ArrayList<User> shallowCopy(ArrayList<User> users) {
        return new ArrayList<User>(users);
    }
    public static ArrayList<User> deepCopy(ArrayList<User> users) throws CloneNotSupportedException {
        ArrayList<User> copyUsers = new ArrayList<User>();
        for (User i: users) {
            copyUsers.add((User) i.clone()); // or new User(user)
        }
        return copyUsers;
    }

    public static ArrayList<User> shallowCopy (HashMap <Integer, User> users) {
        return (ArrayList<User>) users.values();
    }
    public static ArrayList<User> deepCopy (HashMap <Integer, User> users) {
        ArrayList<User> users1 = new ArrayList<User>();
        for (User usr: users.values()){
            users1.add(usr);
        }
        return users1;
    }

    public ArrayList<InsurancePolicy> deepCopyPolicies() throws CloneNotSupportedException {
        ArrayList<InsurancePolicy>deepCopy=new ArrayList<InsurancePolicy>();
        for (InsurancePolicy pol: policies.values()) {
            deepCopy.add(pol.clone());
        }
        return deepCopy;
    }
    public ArrayList<InsurancePolicy> shallowCopyPolicies() {
        // assign a reference
        return new ArrayList<InsurancePolicy>(policies.values());
    }
    // hashmap
    public HashMap<Integer, InsurancePolicy> deepCopyPoliciesHashMap() throws CloneNotSupportedException {
        HashMap<Integer, InsurancePolicy> deepCopy = new HashMap<Integer, InsurancePolicy>();
        for (InsurancePolicy pol: policies.values()) {
            deepCopy.put(pol.getId(),(InsurancePolicy)pol.clone());
        }
        return deepCopy;
    }
    // hashmap
    public HashMap<Integer, InsurancePolicy> shallowCopyPoliciesHashMap() {
        //assign a reference
        return new HashMap<Integer, InsurancePolicy>(policies);
    }

    public ArrayList<InsurancePolicy> sortPoliciesByDate() {
        ArrayList<InsurancePolicy> sortedPolicies = new ArrayList<InsurancePolicy>(policies.values());
        Collections.sort(sortedPolicies);
        return sortedPolicies;
    }



    public HashMap<String, Integer> getTotalCountPerCarModel () {
        HashMap<String, Integer> count = new HashMap<String, Integer>();
        for (InsurancePolicy pol: policies.values()) {
            count.put(pol.car.getModel(), (int)getTotalCountForCarModel(pol.car.getModel()));
        }
        return count;
    }

    public HashMap<String, Double> getTotalPremiumPerCarModel (int flatRate) {
        HashMap<String,Double> carModelCount =  new HashMap<String, Double>();
        for (InsurancePolicy pol: policies.values()) {
            carModelCount.put(pol.car.getModel(), getTotalPaymentForCarModel(pol.car.getModel(), flatRate));
        }
        return carModelCount;
    }


    public void reportPaymentsPerCarModel(HashMap<String, Integer> counts, HashMap<String, Double> payments) {
        System.out.printf("%-20s %-15s %s %n", "Car Model", "Total Premium Payment", "Average Premium Payment");
        for (InsurancePolicy policy: policies.values()) {
            System.out.printf("%-25s $%-23.2f $%.2f%n",policy.car.getModel(), payments.get(policy.car.getModel()),(payments.get(policy.car.getModel()))/(counts.get(policy.car.getModel())) );
        }
    }

    public static HashMap<Integer, User> load(String fileName) {
        HashMap<Integer, User> users = new HashMap<>();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            users = (HashMap<Integer, User>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static boolean save(HashMap<Integer, User> users, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(users);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean saveTextFile(HashMap<Integer, User> users, String fileName) throws IOException
    {
        BufferedWriter output = new BufferedWriter(new FileWriter(fileName)); // create object to write data to file

        for (User user : users.values())
        {
            output.write(user.toDelimitedString() + "\n");
        }
        output.close();
        return true;
    }

    public static HashMap<Integer, User> loadTextFile(String fileName) throws IOException, PolicyException, NameException {
        HashMap<Integer, User> output = new HashMap<>(); // create new hash map to store and return loaded data

        BufferedReader input = new BufferedReader(new FileReader(fileName)); // create object to read in data
        String line = input.readLine(); // read in first line
        while(line!=null)
        {
            line = line.trim(); // trim white space from either end of string
            String[] field = line.split(","); // split string into array using comma seperators

            // store values from field array
            String userName = field[0];
            int userID = Integer.parseInt(field[1]);
            int streetNum = Integer.parseInt(field[2]);
            String street = field[3];
            String suburb = field[4];
            String city = field[5];
            int numPolicies = Integer.parseInt(field[6]);

            Address address = new Address(streetNum, street, suburb, city); // create address object
            User user = new User(userName, userID, address); // create user object
            user.setUserID(userID); // set user ID from file

            int index = 7;
            for (int i=0 ; i<numPolicies; i++) {
                String policyHolderName = field[index+1];
                int id = Integer.parseInt(field[index+2]);
                CarType type = CarType.valueOf(field[index+3]);
                String model = field[index+4];
                int manufacturingYear = Integer.parseInt(field[index+5]);
                double price = Double.parseDouble(field[index+6]);
                int numberOfClaims = Integer.parseInt(field[index+7]);
                int year = Integer.parseInt(field[index+8]);
                int month = Integer.parseInt(field[index+9]);
                int day = Integer.parseInt(field[index+10]);

                if(field[index].equals("TP")) {
                    String comments = field[index+11];

                    Car car = new Car(type, model, manufacturingYear, price);
                    MyDate date = new MyDate(year, month, day);

                    user.addPolicy(new ThirdPartyPolicy(policyHolderName, id, car, numberOfClaims, date, comments));
                    index+=10;
                }
                else {
                    int driverAge = Integer.parseInt(field[index+11]);
                    int level = Integer.parseInt(field[index+12]);

                    Car car = new Car(type, model, manufacturingYear, price);
                    MyDate date = new MyDate(year, month, day);

                    user.addPolicy(new ComprehensivePolicy(policyHolderName, id, car, numberOfClaims, date, driverAge, level));
                    index+=11;
                }
            }
            output.put(user.getUserID(), user);
            line = input.readLine();
        }
        input.close();
        return output;
    }


}
