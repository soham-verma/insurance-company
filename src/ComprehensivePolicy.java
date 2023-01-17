import java.io.Serializable;

public class ComprehensivePolicy extends InsurancePolicy implements Serializable {
    protected int driverAge;
    protected int level;

    public ComprehensivePolicy(String _policyHolderName, int _id, Car _car, int _numberOfClaims, MyDate _expiryDate, int _driverAge, int _level) {
        // default constructor
        super(_policyHolderName, _id, _car, _numberOfClaims, _expiryDate);
        driverAge = _driverAge;
        level = _level;
    }
    public ComprehensivePolicy(ComprehensivePolicy CPP) {
        // copy constructor
        super(CPP);
        this.driverAge = CPP.driverAge;
        this.level = CPP.level;
    }
    public void print () {
        super.print();
        System.out.println (" Driver Age: " + driverAge + " Level: " + level);
    }
    @Override
    public double calcPayment(double flatRate) {
        if (driverAge < 30) {
            return car.price / 50 + numberOfClaims * 200 + flatRate + ((30 - driverAge) * 50);
        }
        else {
            return car.price / 50 + numberOfClaims * 200 + flatRate;
        }
    }
    public String toString() {
        return super.toString() + " Driver Age: " + driverAge + " Level: " + level;
    }

    @Override
    public String toDelimitedString()
    {
        return "CP" + "," + policyHolderName + "," + id + "," + car.toDelimitedString() + "," + numberOfClaims + "," + expiryDate.toDelimatedString() + "," + driverAge + "," + level;
    }
}
