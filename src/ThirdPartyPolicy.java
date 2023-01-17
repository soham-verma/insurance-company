import java.io.Serializable;

public class ThirdPartyPolicy extends InsurancePolicy implements Serializable {
    protected String comments; //fields
    public ThirdPartyPolicy(String _policyHolderName, int _id, Car _car, int _numberOfClaims, MyDate _expiryDate, String _comments) {
        // default constructor
        super(_policyHolderName, _id, _car, _numberOfClaims, _expiryDate); // get fields from IP
        comments = _comments;
    }
    public ThirdPartyPolicy(ThirdPartyPolicy TPP) {
        // copy constructor
        super(TPP);
        this.comments = TPP.comments;
    }
    public void print () {
        // print method for TPP
        super.print(); // get previous print from IP
        System.out.println (" Comments: "+comments);
    }
    public double calcPayment(double flatRate) {
        // payment calculation
        return car.price / 100 + numberOfClaims * 200 + flatRate;
    }

    @Override
    public String toString() {
        return super.toString() + " Comments: "+comments;
    }


    @Override
    public String toDelimitedString() {
        return "TP" + "," + policyHolderName + "," + id + "," + car.toDelimitedString() + "," + numberOfClaims + "," + expiryDate.toDelimatedString() + "," + comments;
    }
}
