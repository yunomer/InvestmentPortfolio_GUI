package portfolio;

/**
 * This is the Mutual Funds class that is a sub class for the Investment Class.
 */
public class MutualFund extends Investment {

    /**
     * This is a Constructor for this Class that creates an instance for the class to store the variables sent to it.
     * It calls on setters to do the job.
     * @pram Basically String name, String symbol, int quantity, double price, double bookValue
     */
    public MutualFund(String name, String symbol, int quantity, double price, double bookValue) throws priceException, quantityException, symbolException, nameException {
        super(name, symbol, quantity, price, bookValue);
    }

    /**
     * This is a custom constructor
     * @throws priceException
     * @throws quantityException
     * @throws symbolException
     * @throws nameException
     */
    public MutualFund() throws priceException, quantityException, symbolException, nameException {
        super("EMPTY", "EMPTY", 0, 0, 0);
    }

    /**
     * This is a custom constructor to keep privacy as it creates copies of the original.
     * @param copy
     * @throws priceException
     * @throws quantityException
     * @throws symbolException
     * @throws nameException
     */
    public MutualFund (MutualFund copy) throws priceException, quantityException, symbolException, nameException {
        super(copy.getSymbol(), copy.getName(), copy.getQuantity(), copy.getPrice(), copy.getBookValue());
    }

    /**
     * This method basically does the math and puts it into the object when a Fund gets sold
     * @param newQ
     * @param newP
     * @throws quantityException
     * @throws priceException
     */
    public void calcBookValue(int newQ, double newP) throws quantityException, priceException {
        super.setBookValue(super.getBookValue() * ((double)newQ / (double)super.getQuantity()));
        super.setQuantity(newQ);
        super.setPrice(newP);
    }

    /**
     * To string method
     * @return string
     */
    @Override
    public String toString() {
        return "Type of Fund: Mutual Fund, Name: " + super.getName()
                + ", Symbol: " + super.getSymbol()
                + ", Quantity: " + super.getQuantity()
                + ", Price: " + super.getPrice()
                + ", Book Value: " + super.getBookValue();
    }

}