import java.io.Serializable;

enum CarType {SUV, SED, LUX, HATCH};
    public class Car implements Cloneable, Serializable {
        // fields
        protected String model;
        protected CarType type; //enum
        protected int ManufacturingYear;
        protected double price;

        public Car(String _model, int _manufacturingYear, CarType _type, double _price) {
            // default constructor
            model = _model;
            type = _type;
            ManufacturingYear = _manufacturingYear;
            price = _price;
        }
        public Car(CarType type, String model, int manufacturingYear, double price)
        {
            this.type = type;
            this.model = model;
            this.ManufacturingYear = manufacturingYear;
            this.price = price;
        }
        public Car(Car car) {
            // copy constructor
            this.model = car.model;
            this.type = car.type;
            this.ManufacturingYear = car.ManufacturingYear;
            this.price = car.price;
        }
        @Override
        public Car clone() throws CloneNotSupportedException {
            return (Car)super.clone();
        }
        public String toDelimitedString()
        {
            return type + "," + model + "," + ManufacturingYear + "," + price;
        }

        public String toString () {
            // Override the toString method
            return " Model: "+model + " Manufacturing Year: "+ManufacturingYear + " Car Type: " + type + " Price: "+price;
        }
        // set and get methods()
        public void setModel(String model) { this.model = model; }
        // get methods()
        public String getModel() { return model; }
        public void priceRise(double rise) {
            price *= (1+rise);
        }
    }
