import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Serializable;

public class MyDate implements Comparable<MyDate>, Cloneable, Serializable {
    protected int year;
    protected int month;
    protected int day;

    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    public MyDate(MyDate date) {
        // copy constructor
        this.year = date.year;
        this.month = date.month;
        this.day = date.day;
    }

    @Override
    public MyDate clone() {
        try {
            return (MyDate) super.clone();
        } catch (CloneNotSupportedException e) {
            // This should never happen since MyDate implements Cloneable
            throw new RuntimeException(e);
        }
    }


    public boolean isExpired(MyDate expiryDate) {
//        if(expiryDate.year>this.year) {
//            if (expiryDate.month > this.month) {
//                if (expiryDate.day > this.day) {
//                    return true;
//                }
//                else {
//                    return false;
//                }
//            }
//            else {
//                return false;
//            }
//        }
//        else {
//            return false;
//        }

        SimpleDateFormat dtf = new SimpleDateFormat("yyyyMMdd");
        Date d1 = new Date(this.year, this.month, this.day); //1
        Date d2 = new Date(expiryDate.year,expiryDate.month, expiryDate.day); //2
        dtf.format(d1);
        dtf.format(d2);
        if(d1.compareTo(d2) >= 0) {
            // Date 1 occurs after Date 2
            return false;
        } else if(d1.compareTo(d2) < 0) {
            return true;
            // Date 1 occurs before Date 2
        }
        else {
            return false;
        }

    }

    @Override
    public int compareTo(MyDate date) {
        if (date.year > this.year){ return 1; }
        else if (date.year < this.year) { return -1; }
        else {
            if (date.month > this.month) { return 1; }
            else if (date.month < this.month) { return -1; }
            else {
                if (date.day > this.day) { return 1; }
                else if (date.day < this.day) { return -1; }
                else { return 0; }
            }
        }
    }

    public String toDelimatedString(){
        return this.year+","+this.month+","+this.day;
    }


}

