import java.io.Serializable;

public class Address implements Comparable<Address>, Cloneable, Serializable {
    protected int streetNum;
    protected String street;
    protected String suburb;
    protected String city;

    public Address(int streetNum, String street, String suburb, String city) {
        // default constructor
        this.streetNum = streetNum;
        this.street = street;
        this.suburb = suburb;
        this.city = city;
    }
    public Address(Address address) {
        // copy constructor
        this.streetNum = address.streetNum;
        this.street = address.street;
        this.suburb = address.suburb;
        this.city = address.city;
    }

    // set methods()
    public void setStreetNum(int streetNum) { this.streetNum = streetNum; }
    public void setStreet(String street) { this.street = street; }
    public void setSuburb(String suburb) { this.suburb = suburb; }
    public void setCity(String city) { this.city = city; }

    // get methods()
    public int getStreetNum() { return streetNum; }
    public String getStreet() { return street; }
    public String getSuburb() { return suburb; }
    public String getCity() { return city; }

    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        return (Address)super.clone();
    }


    @Override
    public int compareTo(Address o) {
        return city.toLowerCase().compareTo(o.city.toLowerCase());
    }

    public String toDelimitedString() {
        return streetNum + "," + street + "," + suburb + "," + city;
    }
}
