import java.io.Serializable;

public class NameException extends Exception implements Serializable
{
    @Override
    public String toString()
    {
        return "Policy Holder Name invalid!\n";
    }
}