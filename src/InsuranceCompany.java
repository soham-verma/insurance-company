import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.io.Serializable;

public class InsuranceCompany implements Comparable<User>, Cloneable, Serializable {
    public String name;
//    private ArrayList<User> users; // list of all the users having a policy with the company
    private HashMap<Integer, User> users; // list of all the users having a policy with the company
//    private HashMap<Integer, InsurancePolicy> policies;
    /*
    Integer = User ID
    User = User Object
    */
 	private String adminUsername;
	private String adminPassword;
	private int flatRate;

    public InsuranceCompany(String _name, String _adminUsername, String _adminPassword, int _flatRate) {
        name = _name;
        adminUsername = _adminUsername;
        adminPassword = _adminPassword;
        flatRate = _flatRate;
        this.users = new HashMap<Integer, User>();
    }
    public InsuranceCompany(InsuranceCompany company) {
        this.name = company.name;
        this.adminUsername = company.adminUsername;
        this.adminPassword = company.adminPassword;
        this.flatRate = company.flatRate;
        this.users = new HashMap<Integer, User>();
        for (User i:company.users.values()) { users.put(i.getUserID(),i); }
    }
    public InsuranceCompany() {
        this.name = "";
        this.users = new HashMap<>();
        this.adminUsername = "";
        this.adminPassword = "";
        this.flatRate = 0;
    }

    public HashMap<Integer, User> getUsers() {
        return users;
    }

//    public HashMap<Integer, InsurancePolicy> getPolicies() {
//        return policies;
//    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        User cloned = (User)super.clone();
        cloned.setAddress((Address)cloned.getAddress().clone());
        return cloned;
    }
    @Override
    public int compareTo(User user) {
        return user.compareTo1(user);
    }

    // set methods()
    public void setAdminPassword(String adminPassword) { this.adminPassword = adminPassword; }
    public void setAdminUsername(String adminUsername) { this.adminUsername = adminUsername; }

    public boolean validateAdmin(String username, String password) {
        return username.equals(adminUsername) && password.equals(adminPassword);
    }
    public boolean addUser(User user) {
        if(findUser(user.getUserID()) == null) {
//            users.add(user);
            users.put(user.getUserID(), user);
            return true;
        }
        else { return false; }
    }
    public Boolean addUser(String name, int userID, Address address) {
        if(findUser(userID) == null) {
            addUser(new User(name, userID, address));
            return true;
        }
        else { return false; }
    }
    public boolean removeUser(User user) {
        if(findUser(user.getUserID()) != null) {
            users.remove(user.getUserID());
        }
        return true;
    }
    public User findUser(int userID) {
        for (User user : users.values()) {
            if (user.getUserID() == userID) {
                return user;
            }
        }
        return null;
    }
    public boolean addPolicy (int userID, InsurancePolicy policy) {
        if (findUser(userID) != null){
            findUser(userID).addPolicy(policy);
            return true;
        }
        return false;
    }
    public InsurancePolicy findPolicy (int userID, int policyID) {
        for (User i:users.values()) {
            if (findUser(userID) == null) {
                return null;
            }
            else if (findUser(userID).findPolicy(policyID) == null) {
                return null;
            }
            else { return i.findPolicy(policyID); }
        }
        return null;
    }
    public void printPolicies(int userID) {
//        System.out.println("Name: "+ name+ " Flat rate: "+flatRate);
        findUser(userID).print();
    }
    public void print() {

        for (User user: users.values()) {
            System.out.print(user);
            user.printPolicies(flatRate);
        }
    }
    @Override
    public String toString() {
        String local = "";
        for(User user:users.values()) {
            local += user.toString() + "\n";
        }
        return local;
    }
    public boolean createThirdPartyPolicy(int userID, String policyHolderName, int id, Car car, int numberOfClaims, MyDate expiryDate, String comments) {
        return findUser(userID).addPolicy(new ThirdPartyPolicy(policyHolderName, id, car, numberOfClaims, expiryDate, comments));
    }
    public boolean createComprehensivePolicy(int userID, String policyHolderName, int id, Car car, int numberOfClaims, MyDate expiryDate, int driverAge, int level) {
        return findUser(userID).addPolicy(new ComprehensivePolicy(policyHolderName, id, car, numberOfClaims, expiryDate, driverAge, level));
    }

    public double calcTotalPayments(int userID) {
        return findUser(userID).calcTotalPremiums(flatRate);
    }
    public double calcTotalPayments() {
        double total = 0;
        for (User i:users.values()) {
            total += calcTotalPayments(i.getUserID());
        }
        return total;
    }
    public boolean carPriceRise(int userID, double risePercent) {
        if(findUser(userID)==null) {
            return false;
        }
        else {
            findUser(userID).carPriceRiseAll(risePercent);
            return true;
        }
    }
    public void carPriceRise(double risePercent) {
        for(User i:users.values()) {
            i.carPriceRiseAll(risePercent);
        }
    }
//    public ArrayList<InsurancePolicy> allPolicies() {
    public ArrayList<InsurancePolicy> allPolicies() {
        ArrayList<InsurancePolicy> polList = new ArrayList<InsurancePolicy>();
        // users hashmap loop
        for (User i:users.values()) {
            polList.addAll(i.getPolicies().values());
        }
        return polList;
    }
    public ArrayList<InsurancePolicy> filterByCarModel (int userID, String carModel) {
        return findUser(userID).filterByCarModel(carModel);
    }
    public ArrayList<InsurancePolicy> filterByExpiryDate (int userID, MyDate date) {
        return findUser(userID).filterByExpiryDate(date);
    }
    public ArrayList<InsurancePolicy> filterByCarModel (String carModel) {
        for(User i:users.values()) {
            return i.filterByCarModel(carModel);
        }
        return null;
    }
    public ArrayList<InsurancePolicy> filterByExpiryDate (MyDate date) {
        ArrayList<InsurancePolicy> pol = new ArrayList<InsurancePolicy>();
        for(User i:users.values()) {
            pol.addAll(findUser(i.getUserID()).filterByExpiryDate(date));
        }
        return pol;
    }
    public ArrayList<String> populateDistinctCityNames() {
        ArrayList<String> cities =  new ArrayList<String>();
        for (User i: users.values()) {
            if (!cities.contains(i.getCity())) { cities.add(i.getCity()); }
        }
        return cities;
    }
    public double getTotalPaymentForCity(String city) {
        double total = 0;
        for (User i: users.values()) {
            if (i.getCity().equals(city)) { total += i.calcTotalPremiums(flatRate); }
        }
        return total;
    }
    public ArrayList<Double> getTotalPaymentPerCity(ArrayList<String> cities) {
        ArrayList<Double> cityPay = new ArrayList<Double>();
        for (String city: cities) {
            cityPay.add(getTotalPaymentForCity(city));
        }
        return cityPay;
    }
    public void reportPaymentPerCity(ArrayList<String> cities, ArrayList<Double> payments) {
        System.out.printf("%-15s %s %n", "City Name", "Total Premium Payment");
        for(int i = 0; i < cities.size(); i++) {
            System.out.printf("%-15s $%.2f%n",cities.get(i),payments.get(i));
        }
    }
    public ArrayList<String> populateDistinctCarModels() {
        ArrayList<String> carModels = new ArrayList<String>();
        for(User i: users.values()) {
            carModels.addAll(i.populateDistinctCarModels());
        }
        return carModels;
    } // works good (don't touch)

    public ArrayList<Integer> getTotalCountPerCarModel (ArrayList<String> carModels) {
        ArrayList<Integer> carModelCount =  new ArrayList<Integer>();
        for (String car: carModels) {
            int total = 0;
            for (User user: users.values()) {
                total+=user.getTotalCountForCarModel(car);
            }
            carModelCount.add(total);
        }
        return carModelCount;
    }
    public ArrayList<Double> getTotalPaymentPerCarModel (ArrayList<String> carModels) {
        ArrayList<Double> carModelTotal =  new ArrayList<Double>();
        for (String car: carModels) {
            double total = 0.0;
            for (User user: users.values()) {
                total+=user.getTotalPaymentForCarModel(car,flatRate);
            }
            carModelTotal.add(total);
        }
        return carModelTotal;
    }
    public void reportPaymentsPerCarModel (ArrayList<String> carModels, ArrayList<Integer> counts, ArrayList<Double> premiumPayments) {
        User.reportPaymentsPerCarModel(carModels, counts, premiumPayments);
    }
    public ArrayList<User> deepCopyUsers() throws CloneNotSupportedException {
        ArrayList<User> copy = new ArrayList<User>();
        for (User i:users.values()) {
            copy.add((User) i.clone());
        }
        return copy;
    }
    public ArrayList<User> shallowCopyUsers() {
        return new ArrayList<User>(users.values());
    }

    public HashMap <Integer, User> shallowCopyUsersHashMap() {
        return new HashMap <Integer,User>(users);
    }
    public HashMap <Integer, User> deepCopyUsersHashMap() {
        HashMap <Integer,User> users1 = new HashMap <Integer,User>();
        for (User usr: this.users.values()) {
            users.put(usr.getUserID(), usr);
        }
        return users1;
    }

    public HashMap<String, Integer> getTotalCountPerCarModel() {
        HashMap<String, Integer> carModelCount =  new HashMap<String, Integer>();
        for (User user: users.values()) {
            carModelCount.putAll(user.getTotalCountPerCarModel());
        }
        return carModelCount;
    }
    public HashMap<String,Double> getTotalPremiumPerCarModel() {
        HashMap<String,Double> carModelTotal =  new HashMap<String,Double>();
        for (User user: users.values()) {
            carModelTotal.putAll(user.getTotalPremiumPerCarModel(flatRate));
        }
        return carModelTotal;
    }

    public HashMap<String, Double> getTotalPremiumPerCity() {
        HashMap<String, Double> totalPerCity = new HashMap<String, Double>();
        for(String str: populateDistinctCityNames()) {
            totalPerCity.put(str, getTotalPaymentForCity(str));
        }
        return totalPerCity;
    }

    public void reportPaymentPerCity(HashMap<String, Double> cities) {
        System.out.printf("%-15s %s %n", "City Name", "Total Premium Payment");
        for (String city: cities.keySet()) {
            System.out.printf("%-15s $%.2f%n",city, cities.get(city));
        }
    }
    public void reportPaymentsPerCarModel (HashMap<String, Integer> counts, HashMap<String,Double> premiumPayments) {
        for (User u: users.values()) {
            u.reportPaymentsPerCarModel(counts, premiumPayments);
        }
    }

    public ArrayList<User> sortUsers() {
        ArrayList<User> usersList = new ArrayList<>(users.values());
        Collections.sort(usersList);
        return usersList;
    }

    // saving and loading ser
    public boolean load(String fileName)
    {
        ObjectInputStream inputStream = null;
        boolean value = false;

        try // open file
        {
            inputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)));
        }
        catch (IOException e)
        {
            System.out.println("Error opening file. Terminating.");
            return false;
        }
        try // load file contents into object
        {
            InsuranceCompany insuranceCompany = (InsuranceCompany)inputStream.readObject();
            name = insuranceCompany.name;
            adminUsername = insuranceCompany.adminUsername;
            adminPassword = insuranceCompany.adminPassword;
            flatRate = insuranceCompany.flatRate;
            users = insuranceCompany.users;
        }
        catch (IOException e)
        {
            System.out.println("Error reading from file. Terminating.");
            return false;
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Error, class not found. Terminating.");
            return false;
        }
        try // close file
        {
            inputStream.close();
            value = true;
        }
        catch (IOException e)
        {
            System.out.println("Error closing file. Terminating.");
            return false;
        }
        return value;
    }

    public boolean save(String fileName)
    {
        ObjectOutputStream output = null;
        boolean value = false;

        try // open file
        {
            output = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)));
        }
        catch (IOException e)
        {
            System.out.println("Error opening file. Terminating.");
            return false;
        }
        try // save/write object to file
        {
            output.writeObject(this);
            value = true;
        }
        catch (IOException e)
        {
            System.out.println("Error in writing to file. Terminating.");
            return false;
        }
        try
        {
            output.close();
        }
        catch (IOException e)
        {
            System.err.println("Error closing file. Terminating.");
            return false;
        }
        return value;
    }

    public String toDelimitedString()
    {
        String output = name + "," + adminUsername + "," + adminPassword + "," + flatRate;
        for (User user : users.values())
        {
            output += "\n" + user.toDelimitedString() + ",";
        }
        return output;
    }

    // save/write insurance company data to text file

    public boolean saveTextFile(String fileName) throws IOException
    {
        BufferedWriter output = new BufferedWriter(new FileWriter(fileName));

        output.write(toDelimitedString() + "\n");
        output.close();
        return true;
    }

    // load insurance company data from text file

    public boolean loadTextFile(String fileName) throws IOException, PolicyException, NameException {
        BufferedReader input = new BufferedReader(new FileReader(fileName));

        String line = input.readLine();
        line = line.trim();
        String[] field = line.split(",");
        this.name = field[0];
        this.adminUsername = field[1];
        this.adminPassword = field[2];
        this.flatRate = Integer.parseInt(field[3]);

        this.users = new HashMap<>(); // create hash map to store loaded users

        line = input.readLine();
        while (line!=null)
        {
            line = line.trim();
            field = line.split(",");
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

            users.put(userID, user); // add user to insurance company

            try {
                int index = 7;
                for (int i=0 ; i<numPolicies; i++) {
                    if (field[index].equals("TP")) {
                        String policyHolderName = field[index + 1];
                        int id = Integer.parseInt(field[index + 2]);
                        CarType type = CarType.valueOf(field[index + 3]);
                        String model = field[index + 4];
                        int manufacturingYear = Integer.parseInt(field[index + 5]);
                        double price = Double.parseDouble(field[index + 6]);
                        int numberOfClaims = Integer.parseInt(field[index + 7]);
                        int year = Integer.parseInt(field[index + 8]);
                        int month = Integer.parseInt(field[index + 9]);
                        int day = Integer.parseInt(field[index + 10]);
                        String comments = field[index + 11];

                        Car car = new Car(type, model, manufacturingYear, price);
                        MyDate date = new MyDate(year, month, day);

                        user.addPolicy(new ThirdPartyPolicy(policyHolderName, id, car, numberOfClaims, date, comments));
                        index += 10;
                    } else {
                        String policyHolderName = field[index + 1];
                        int id = Integer.parseInt(field[index + 2]);
                        CarType type = CarType.valueOf(field[index + 3]);
                        String model = field[index + 4];
                        int manufacturingYear = Integer.parseInt(field[index + 5]);
                        double price = Double.parseDouble(field[index + 6]);
                        int numberOfClaims = Integer.parseInt(field[index + 7]);
                        int year = Integer.parseInt(field[index + 8]);
                        int month = Integer.parseInt(field[index + 9]);
                        int day = Integer.parseInt(field[index + 10]);
                        int driverAge = Integer.parseInt(field[index + 11]);
                        int level = Integer.parseInt(field[index + 12]);

                        Car car = new Car(type, model, manufacturingYear, price);
                        MyDate date = new MyDate(year, month, day);

                        user.addPolicy(new ComprehensivePolicy(policyHolderName, id, car, numberOfClaims, date, driverAge, level));
                        index += 11;
                    }
                }
            } catch (Exception e) {}
            line = input.readLine();
        }
        input.close();
        return true;
    }

}
