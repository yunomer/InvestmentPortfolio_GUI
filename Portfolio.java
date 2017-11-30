/*
 * M. Omer Ashfaq
 * 0884798
 * An investment portfolio program that is easy to use as it presents the user with a GUI
 */
package portfolio;


/**
 * This is the portfolio class that contains the main method and the methods that are used to do the various tasks required
 * This creates the GUI that then runs the rest of the program.
 * @author: M. Omer Ashfaq
 */
public class Portfolio{

    /**
     * The main function calls and creates an instance of the GUI class.
     * The GUI Class then creates an instance of Portfolio functions that is used to store the Investments
     * @param args
     * @exception Investment.priceException Investment.symbolException Investment.quantityException Investment.nameException
     */

    public static void main(String[] args) {
        String file;
        if (args.length == 0) {
            System.out.println("************************************************");
            System.out.println("******No file name used, creating new file******");
            System.out.println("************************************************");
            file = "my_Investments.txt";
            try {
                GuiClass gui = new GuiClass(file);
            } catch (Investment.priceException | Investment.symbolException | Investment.quantityException | Investment.nameException e) {
                System.out.println("Error loading File Member...");
            }
        }
        else {
            try {
                GuiClass gui = new GuiClass(args[0]);
            } catch (Investment.priceException | Investment.symbolException | Investment.quantityException | Investment.nameException e) {
                System.out.println("Error loading File Member...");
            }
        }
    }
}
