package portfolio;

/**
 * This Class will hold all the methods that are used for Investment objects
 * It holds the Getter and setters, and the Constructor.
 */
public abstract class Investment {

    protected String symbol;
    protected String name;
    protected int quantity = 0;
    protected double price = 0;
    protected double bookValue = 0;

    /**
     * Abstract method, This method basically does the math and puts it into the object when a Fund gets sold
     * @param newQ
     * @param newP
     * @throws quantityException
     * @throws priceException
     */
    public abstract void calcBookValue(int newQ, double newP) throws quantityException, priceException;

    /**
     *  This is a Constructor for this Class that creates an instance for the class to store the variables sent to it.
     * It calls on setters to do the job.
     * @param name
     * @param symbol
     * @param quantity
     * @param price
     * @param bookValue
     * @throws symbolException
     * @throws nameException
     * @throws quantityException
     * @throws priceException
     */
    public Investment(String name, String symbol, int quantity, double price, double bookValue) throws symbolException, nameException, quantityException, priceException {

        if ((name.toLowerCase().equals("")) || name.trim().isEmpty()) {
            throw new nameException(">> Name is required to add the Investment!\n");
        }
        if ((symbol.toLowerCase().equals("")) || symbol.trim().isEmpty()) {
            throw new symbolException(">> Symbol is required to add the Investment!\n");
        }
        if (quantity <= 0) {
            throw new quantityException(">> Quantity cannot be less than 0!\n");
        }
        if (price <= 0.0) {
            throw new priceException(">> Price cannot be less than 0!\n");
        }

        setName(name);
        setSymbol(symbol);
        setQuantity(quantity);
        setPrice(price);
        setBookValue(bookValue);
    }

    /**
     * This is a copy constructor for an empty Investment call
     */
    public Investment() {
        this.symbol = "EMPTY";
        this.name = "EMPTY";
        this.quantity = 0;
        this.price = 0.0;
        this.bookValue = 0;
    }

    /**
     * This is the Copy constructor used to keep privacy
     * @param copy
     */
    public Investment(Investment copy) {
        this.symbol = copy.getSymbol();
        this.name = copy.getName();
        this.quantity = copy.getQuantity();
        this.price = copy.getPrice();
        this.bookValue = copy.getBookValue();
    }

    /**
     * This is the Get method for the attribute Symbol
     * @return Symbol String
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * This is the set method for the attribute Symbol
     */
    public void setSymbol(String symbol) throws symbolException {
        if ((symbol.toLowerCase().equals("")) || symbol.trim().isEmpty()) {
            throw new symbolException(">> Symbol is required to add the Investment!\n");
        }
        this.symbol = symbol;
    }

    /**
     * This is the Get method for the attribute Name
     * @return name String
     */
    public String getName() {
        return name;
    }

    /**
     * This is the set method for the attribute name
     */
    public void setName(String name) throws nameException {
        if ((name.toLowerCase().equals("")) || name.trim().isEmpty()) {
            throw new nameException(">> Name is required to add the Investment!\n");
        }
        this.name = name;
    }

    /**
     * This is the Get method for the attribute Quantity
     * @return Quantity String
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * This is the set method for the attribute quantity.
     */
    public void setQuantity(int quantity) throws quantityException {
        if (quantity <= 0) {
            throw new quantityException(">> Quantity cannot be less than 0!\n");
        }
        this.quantity = quantity;
    }

    /**
     * This is the Get method for the attribute Price
     * @return price double
     */
    public double getPrice() {
        return price;
    }

    /**
     * This is the set method for the attribute price
     */
    public void setPrice(double price) throws priceException {
        if (price <= 0.0) {
            throw new priceException(">> Price cannot be less than 0!\n");
        }
        this.price = price;
    }

    /**
     * This is the Get method for the attribute bookValue
     * @return bookValue double
     */
    public double getBookValue() {
        return bookValue;
    }

    /**
     * This is the set method for the attribute bookValue
     */
    public void setBookValue(double bookValue) {
        this.bookValue = bookValue;
    }

    /**
     * This s a custom exception created to check validity of Symbol names
     */
    public class symbolException extends Exception {
        public symbolException (String string) {
            super(string);
        }
    }
    /**
     * This s a custom exception created to check validity of fund names
     */
    public class nameException extends Exception {
        public nameException (String string) {
            super(string);
        }
    }

    /**
     * This s a custom exception created to check validity of fund quantity
     */
    public class quantityException extends Exception {
        public quantityException (String string) {
            super(string);
        }
    }

    /**
     * This s a custom exception created to check validity of fund prices
     */
    public class priceException extends Exception {
        public priceException (String string) {
            super(string);
        }
    }

}
