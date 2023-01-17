import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class InsurancePolicy implements Comparable<InsurancePolicy>, Cloneable, Serializable {
    // fields
    protected String policyHolderName;
    protected int id;
    protected Car car; // car object
    protected int numberOfClaims;

    // part2
    protected MyDate expiryDate;

    private String type;

    public InsurancePolicy(String _policyHolderName, int _id, Car _car, int _numberOfClaims, MyDate _expiryDate, String _type) {
        // constructor
        policyHolderName = _policyHolderName;
        id = _id;
        car = _car;
        numberOfClaims = _numberOfClaims;
        expiryDate = _expiryDate;
        type = _type;
    }

    public String toDelimitedString()
    {
        return policyHolderName + "," + id + "," + car.toDelimitedString() + "," + numberOfClaims + "," + expiryDate.toDelimatedString();
    }

    public InsurancePolicy(String _policyHolderName, int _id, Car _car, int _numberOfClaims, MyDate _expiryDate) {
        // default constructor
        policyHolderName = _policyHolderName;
        id = _id;
        car = _car;
        numberOfClaims = _numberOfClaims;
        expiryDate = _expiryDate;
    }

    public InsurancePolicy(InsurancePolicy policy) {
        //  copy constructor
        this.policyHolderName = policy.policyHolderName;
        this.id = policy.id;
        this.car = policy.car;
        this.numberOfClaims = policy.numberOfClaims;
        this.expiryDate = policy.expiryDate;
    }

    @Override
    public InsurancePolicy clone() throws CloneNotSupportedException {
        InsurancePolicy ouput = (InsurancePolicy) super.clone();
        ouput.car = car.clone();
        ouput.expiryDate = (MyDate) expiryDate.clone();
        return ouput;
    }

    @Override
    public int compareTo(InsurancePolicy o) {
        return this.expiryDate.compareTo(o.expiryDate);
    }

    public void print() {
        System.out.print("Policy Holder Name: " + policyHolderName + " ID: " + id + " Car: " + car + " Number of Claims: " + numberOfClaims);
    }
    // print method

    public abstract double calcPayment(double flatRate); //abstract calcPayment which is involved with different policies

    @Override
    // Override the toString method
    public String toString() {
        return " " + policyHolderName + " " + id + " " + car + " " + numberOfClaims;
    }



    // get methods()
    public String getPolicyHolderName() {
        return policyHolderName;
    }

    public int getId() {
        return id;
    }

    public Car getCar() {
        return car;
    }

    // loop through each policy and print that policy
//    public static void printPolicies(ArrayList<InsurancePolicy> policies) {
    public static void printPolicies(HashMap<Integer, InsurancePolicy> policies) {
        for (InsurancePolicy currentPolicy : policies.values()) {
            currentPolicy.print();  // For-loop for the ArrayList -> print()
        }
    }

    // calculate the total cost of the policies
//    public static double calcTotalPayments (ArrayList< InsurancePolicy> policies, int flatRate) {
    public static double calcTotalPayments(HashMap<Integer, InsurancePolicy> policies, int flatRate) {
        double total = 0;
        for (InsurancePolicy currentPolicy : policies.values()) {
            total += currentPolicy.calcPayment(flatRate);
        }
        return total;
    }

    public void carPriceRise(double risePercent) {
        car.priceRise(risePercent);
    }

    //    public static void carPriceRiseAll(ArrayList< InsurancePolicy > policies, double risePercent) {
    public static void carPriceRiseAll(HashMap<Integer, InsurancePolicy> policies, double risePercent) {
        for (InsurancePolicy policy : policies.values()) {
            policy.carPriceRise(risePercent);
        }
    }

    //    public static ArrayList<InsurancePolicy> filterByCarModel (ArrayList<InsurancePolicy> policies, String carModel) {
    public static ArrayList<InsurancePolicy> filterByCarModel(HashMap<Integer, InsurancePolicy> policies, String carModel) {
        ArrayList<InsurancePolicy> filterByModel = new ArrayList<InsurancePolicy>();
        for (InsurancePolicy policy : policies.values()) {
            if ((policy.car.getModel()).equals(carModel)) {
                filterByModel.add(policy);
            }
        }
        return filterByModel;
    }

    //    public static ArrayList<InsurancePolicy> filterByExpiryDate (ArrayList<InsurancePolicy> policies, MyDate date) {
    public static ArrayList<InsurancePolicy> filterByExpiryDate(HashMap<Integer, InsurancePolicy> policies, MyDate date) {
        ArrayList<InsurancePolicy> filterByExpiry = new ArrayList<InsurancePolicy>();
        for (InsurancePolicy policy : policies.values()) {
            if (policy.expiryDate.isExpired(date)) {
                filterByExpiry.add(policy);
            }
        }
        return filterByExpiry;
    }

    public static ArrayList<InsurancePolicy> shallowCopy(ArrayList<InsurancePolicy> policies) {
        return new ArrayList<InsurancePolicy>(policies);
    }

    public static ArrayList<InsurancePolicy> deepCopy(ArrayList<InsurancePolicy> policies) throws CloneNotSupportedException {
        ArrayList<InsurancePolicy> deepCopy = new ArrayList<InsurancePolicy>();
        for (InsurancePolicy pol : policies) {
            deepCopy.add(pol.clone());
        }
        return deepCopy;
    }

    // hashmap
    public static ArrayList<InsurancePolicy> shallowCopy(HashMap<Integer, InsurancePolicy> policies) {
        return new ArrayList<InsurancePolicy>(policies.values());
    }

    public static ArrayList<InsurancePolicy> deepCopy(HashMap<Integer, InsurancePolicy> policies) throws CloneNotSupportedException {
        ArrayList<InsurancePolicy> deepCopy = new ArrayList<InsurancePolicy>();
        for (InsurancePolicy pol : policies.values()) {
            deepCopy.add(pol.clone());
        }
        return deepCopy;
    }

    public static HashMap<Integer, InsurancePolicy> shallowCopyHashMap(HashMap<Integer, InsurancePolicy> policies) {
        HashMap<Integer, InsurancePolicy> copy = new HashMap<Integer, InsurancePolicy>();
        for (InsurancePolicy pol : policies.values()) {
            copy.put(pol.getId(), pol);
        }
        return copy;
    }

    public static HashMap<Integer, InsurancePolicy> deepCopyHashMap(HashMap<Integer, InsurancePolicy> policies) throws CloneNotSupportedException {
        HashMap<Integer, InsurancePolicy> deepCopy = new HashMap<Integer, InsurancePolicy>();
        for (InsurancePolicy pol : policies.values()) {
            deepCopy.put(pol.getId(), pol.clone());
        }
        return deepCopy;
    }

    // loading data from a file
    public static HashMap<Integer, InsurancePolicy> load(String fileName) {
        HashMap<Integer, InsurancePolicy> policies = new HashMap<>();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fis);

            policies = (HashMap<Integer, InsurancePolicy>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return policies;
    }

    public static Boolean save(HashMap<Integer, InsurancePolicy> policies, String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(policies);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static HashMap<Integer, InsurancePolicy> loadTextFile(String fileName) throws IOException, PolicyException, NameException {
        HashMap<Integer, InsurancePolicy> output = new HashMap<>();

        BufferedReader input = new BufferedReader(new FileReader(fileName)); // create object to read in data
        String line = input.readLine(); // read in first line
        while(line!=null)
        {
            line = line.trim(); // remove white space form either end of string
            String[] field = line.split(","); // split line into array using the commas
            switch (field[0])
            {
                case "TP": // if third party policy read in values
                    String policyHolderName = field[1];
                    int id = Integer.parseInt(field[2]);
                    CarType type = CarType.valueOf(field[3]);
                    String model = field[4];
                    int manufacturingYear = Integer.parseInt(field[5]);
                    double price = Double.parseDouble(field[6]);
                    int numberOfClaims = Integer.parseInt(field[7]);
                    int year = Integer.parseInt(field[8]);
                    int month = Integer.parseInt(field[9]);
                    int day = Integer.parseInt(field[10]);
                    String comments = field[11];

                    Car car = new Car(type, model, manufacturingYear, price);
                    MyDate date = new MyDate(year, month, day);

                    output.put(id, new ThirdPartyPolicy(policyHolderName, id, car, numberOfClaims, date, comments));
                    break;
                case "CP": // if comprehensive policy read in values
                    policyHolderName = field[1];
                    id = Integer.parseInt(field[2]);
                    type = CarType.valueOf(field[3]);
                    model = field[4];
                    manufacturingYear = Integer.parseInt(field[5]);
                    price = Double.parseDouble(field[6]);
                    numberOfClaims = Integer.parseInt(field[7]);
                    year = Integer.parseInt(field[8]);
                    month = Integer.parseInt(field[9]);
                    day = Integer.parseInt(field[10]);
                    int driverAge = Integer.parseInt(field[11]);
                    int level = Integer.parseInt(field[12]);

                    car = new Car(type, model, manufacturingYear, price);
                    date = new MyDate(year, month, day);

                    output.put(id, new ComprehensivePolicy(policyHolderName, id, car, numberOfClaims, date, driverAge, level));
                    break;
            }
            line = input.readLine(); // read in next line
        }
        input.close();
        return output;
    }

    // save data to text file

    public static Boolean saveTextFile(HashMap<Integer, InsurancePolicy> policies, String fileName) throws IOException {
        BufferedWriter output = new BufferedWriter(new FileWriter(fileName)); // create object to write data to file

        for (InsurancePolicy policy : policies.values()) // go through policies in policies hash map
        {
            output.write(policy.toDelimitedString() + "\n"); // write each policy to text file using delimited string method
        }
        output.close();
        return true;
    }
}
