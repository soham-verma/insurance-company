import java.util.Random;
class Policy {
    protected int ID;

    public Policy(int ID) throws PolicyException {
        if (!isValidID(ID)) {
            // Generate a new ID with the required pattern
            ID = generateID();
            // Throw a PolicyException with the generated ID
            throw new PolicyException(ID);
        }
        this.ID = ID;
    }
    public int getID() {
        return ID;
    }

    private boolean isValidID(int ID) {
        // Check if the ID starts with 3 and has 6 digits
        return ID / 1000000 == 3 && ID >= 3000000 && ID <= 3999999;
    }

    private int generateID() {
        // Generate a random 6-digit ID starting with 3
        Random random = new Random();
        return 3000000 + random.nextInt(999999);
    }
}

public class PolicyException extends Exception {
    private int ID;

    public PolicyException(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "The Policy ID was not valid and a new ID (" + ID + ") is generated by admin and assigned for the Policy";
    }

    public int getID() {
        return ID;
    }
}