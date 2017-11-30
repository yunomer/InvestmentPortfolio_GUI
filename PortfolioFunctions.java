package portfolio;

import javax.swing.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is the portfolio class that contains  methods that are used by the GUI class.
 * This Also holds the main ArrayList called funds and holds the Hashmap list and searching list.
 *
 * @author: M. Omer Ashfaq
 */
public class PortfolioFunctions {
    //This ArrayList will contain all our Investment instances.
    ArrayList<Investment> funds;
    //This HashMap will contain all the word keys and their location instances.
    HashMap<String, ArrayList<Integer>> map;
    //This ArrayList will be used to hold our filtered search results.
    ArrayList<Investment> searchFound;

    //**************************************************************************//
    //*****This section is for Functions that Initialize the Start program******//
    //**************************************************************************//

    /**
     * This constructor Initializes all the ArrayLists and Hashmaps
     */
    public PortfolioFunctions() {
        funds = new ArrayList();
        map = new HashMap<>();
        searchFound = new ArrayList();
    }

    //**************************************************************************//
    //*****This section is for Functions that are for the main program**********//
    //**************************************************************************//

    /**
     * This print function is used by the search method to print the user's filtered search criteria
     * @param print
     */
    public void printSearchArrayList(JTextArea print) {
        int resize = 0;
        if(searchFound.isEmpty()) {
            print.append(">> No Funds found with provided Criteria");
            return;
        }

        for (int i = 0; i < searchFound.size(); i++) {
            if (Stock.class.equals(searchFound.get(i).getClass())){
                Stock ptr = (Stock)searchFound.get(i);
                print.append( i+1 +". "+ptr.toString()+"\n");
            }
            if (MutualFund.class.equals(searchFound.get(i).getClass())){
                MutualFund ptr = (MutualFund)searchFound.get(i);
                print.append(i+1 +". "+ptr.toString()+"\n");
            }
        }
    }

    /**
     * This method basically adds new Objects to Funds ArrayList, if the user has not already added the list.
     * If the fund object is already added, then it updates that object's prices, quantity and book Value.
     * The parameter it takes is the user's option of either adding a Stock (which is 1) or a mutual Fund (which is 2)
     * @param print
     * @param selection
     * @param fundName
     * @param fundSymbol
     * @param fundQuantity
     * @param fundPrice
     * @throws Investment.priceException
     * @throws Investment.symbolException
     * @throws Investment.quantityException
     * @throws Investment.nameException
     */
    public void addFund(JTextArea print, int selection, String fundName, String fundSymbol, int fundQuantity, double fundPrice) throws Investment.priceException, Investment.symbolException, Investment.quantityException, Investment.nameException {
        String symbol = fundSymbol;
        String name = fundName;
        int quantity = fundQuantity;
        double price = fundPrice;
        double bookValue = 0;


        if (selection == 1) {
            bookValue = quantity * price + 9.99;
        }
        //Else the user is adding a Mutual fund, so there's no commission on that.
        else {
            bookValue = quantity * price;
        }
        //Now check to see if the user already has a Fund with the same Symbol, if so, update that and don't make a new Fund
        for (int i = 0; i < funds.size(); i++) {
            if ((symbol.toLowerCase().equals(funds.get(i).getSymbol().toLowerCase()))) {
                if ((funds.get(i).getClass().equals(Stock.class)) && selection == 1) {
                    funds.get(i).setPrice(price);
                    funds.get(i).setBookValue(funds.get(i).getBookValue() + bookValue);
                    funds.get(i).setQuantity(funds.get(i).getQuantity() + quantity);
                    stockUpdated(print);
                    return;
                }
                else if ((funds.get(i).getClass().equals(Stock.class)) && selection == 2) {
                    valuePresentError(print);
                    return;
                }
                else if ((funds.get(i).getClass().equals(MutualFund.class)) && selection == 2) {
                    funds.get(i).setPrice(price);
                    funds.get(i).setBookValue(funds.get(i).getBookValue() + bookValue);
                    funds.get(i).setQuantity(funds.get(i).getQuantity() + quantity);
                    mutualUpdated(print);
                    return;
                }
                else if ((funds.get(i).getClass().equals(MutualFund.class)) && selection == 1) {
                    valuePresentError(print);
                    return;
                }
            }
        }
        //If no Fund with similar Symbol found, create a new Fund for the user
        if (selection == 1) {
            Stock holding = new Stock(name, symbol, quantity, price, bookValue);
            funds.add(holding);
            addToHashmap(funds.size()-1);
            successResponse(print);
        }
        else {
            MutualFund holding = new MutualFund(name, symbol, quantity, price, bookValue);
            funds.add(holding);
            addToHashmap(funds.size()-1);
            successResponse(print);
        }
    }

    /**
     * This method basically sells Objects in Funds ArrayList.
     * If the fund object goes to 0, it removes it from the list.
     * The parameter it takes is the user's option of either adding a Stock (which is 1) or a mutual Fund (which is 2)
     * @param print
     * @param symbolName
     * @param quantityRead
     * @param priceRead
     * @throws Investment.quantityException
     * @throws Investment.priceException
     */
    public void sellFund(JTextArea print, String symbolName, int quantityRead, double priceRead) throws Investment.quantityException, Investment.priceException {
        String symbol;
        int selection = -1;
        int oldQuantity = 0;
        int quantity = 0;
        int newQuantity = 0;
        double price = 0;
        int arrayPosition = -1;
        double bookValue = 0;
        int exitLoop = 0;

        if (funds.isEmpty()) {
            print.append("There are no Funds available to sell! Add some to sell.\n");
            return;
        }
        do {
            //Getting the Fund Symbol
            symbol = symbolName;
            //Check to see if there is a similar symbol already in the Array List
            for (int i = 0; i < funds.size(); i++) {
                //If found, record the position
                if ((symbol.toLowerCase().equals(funds.get(i).getSymbol().toLowerCase()))) {
                    arrayPosition = i;
                    if(Stock.class.equals(funds.get(i).getClass())) {
                        selection = 1;
                        exitLoop = 1;
                    } else {
                        selection = 2;
                        exitLoop = 1;
                    }
                }
            }
            //If the symbol does not exist...return to main menu
            if (arrayPosition == -1) {
                print.append("No such Fund is available to sell.");
                return;
            }
        } while (exitLoop != 1);

        do {
            try {
                //Getting Quantity of Funds
                quantity = quantityRead;
                if (quantity < 1) {
                    generalError(print);
                }
                else if ((funds.get(arrayPosition).getQuantity() - quantity) < 0) {
                    generalError(print);
                    System.out.println("You do not have enough Fund Quantity to sell!");
                } else {
                    exitLoop = 1;
                }
            } catch (Exception e) {
                generalError(print);
            }
        } while (exitLoop != 1);

        //This is the amount of funds before the user decided to sell
        oldQuantity = funds.get(arrayPosition).getQuantity();
        //This is the amount the user will now have after selling
        newQuantity = oldQuantity - quantity;

        //Price is not used?
        price = priceRead;

        //If the value of the funds is 0, that in, the person owns no funds for that fund, delete.
        if (newQuantity == 0) {
            funds.remove(arrayPosition);
            removeFromHashmap();
            successResponse(print);
            return;
        }
        else {
            if (selection == 1) {
                if (Stock.class.equals(funds.get(arrayPosition).getClass())){
                    funds.get(arrayPosition).calcBookValue(newQuantity, price);
                    successResponse(print);
                }
            }
            if (selection == 2) {
                if (MutualFund.class.equals(funds.get(arrayPosition).getClass())) {
                    funds.get(arrayPosition).calcBookValue(newQuantity, price);
                    successResponse(print);
                }

            }

        }
    }


    /**
     * Basically this method does simple math to find out how much profit you've made with your stock/mutual fund buying career.
     * It prints out what amount you've gained.
     * @param totalGainRead
     * @param print
     */
    public void totalGains(JTextField totalGainRead, JTextArea print) {
        double totalGain = 0;
        int i;
        double instant = 0;
        if (!(funds.isEmpty())) {
            for (i = 0; i < funds.size(); i++) {
                if (funds.get(i).getClass().equals(Stock.class)) {
                    //The 9.99 is $ value for the commission of selling the stock
                    instant = (((funds.get(i).getQuantity() * funds.get(i).getPrice()) - 9.99) - funds.get(i).getBookValue());
                    DecimalFormat df = new DecimalFormat("##########.##");
                    print.append("Gain for Stock with Symbol: " + funds.get(i).getSymbol() + " is: " + df.format(instant) + "\n");
                    totalGain = totalGain + instant;
                    instant = 0;
                }
                if (funds.get(i).getClass().equals((MutualFund.class))) {
                    //The 45 is the $ value for the commission of selling the Mutual funds
                    instant = (((funds.get(i).getQuantity() * funds.get(i).getPrice()) - 45.00) - funds.get(i).getBookValue());
                    DecimalFormat df = new DecimalFormat("##########.##");
                    print.append("Gain for Mutual Fund with Symbol: " + funds.get(i).getSymbol() + " is: " + df.format(instant) + "\n");
                    totalGain = totalGain + instant;
                    instant = 0;
                }
            }
        }
        DecimalFormat df = new DecimalFormat("##########.##");
        totalGainRead.setText(df.format(totalGain) +"\n");
    }

    /**
     * This method is called by the openPortfolio method. This basically loads information
     * from a user provided file that is passed to this method from the openPortfolio method.
     * This method runs as soon as the program starts.
     * @param file
     * @throws Investment.priceException
     * @throws Investment.symbolException
     * @throws Investment.quantityException
     * @throws Investment.nameException
     */
    public void loadInvestment(String file) throws Investment.priceException, Investment.symbolException, Investment.quantityException, Investment.nameException {
        String fileName = file;
        String symbol;
        String name;
        int quantity;
        double price;
        double bookValue;

        BufferedReader reader = null;
        try {
            //Checking to see of file exits, if not, we go back
            File f = new File(fileName);
            if (!(f.exists())){
                return;
            }
            reader = new BufferedReader(new FileReader(fileName));
            //This is to check the type of Fund
            String line = reader.readLine();
            while (line != null) {
                //To get the symbol
                symbol = reader.readLine();
                //To get the name
                name = reader.readLine();
                //To get the Quantity
                try {
                    quantity = Integer.parseInt(reader.readLine());
                } catch (Exception e) {
                    return;
                }
                //To get Price
                try {
                    price = Double.parseDouble(reader.readLine());
                } catch (Exception e) {
                    return;
                 }
                //To get Book Value
                try {
                    bookValue = Double.parseDouble(reader.readLine());
                } catch (Exception e) {
                    return;
                }

                if (line.equalsIgnoreCase("Stock")) {
                    try {
                        Stock holding = new Stock(name, symbol, quantity, price, bookValue);
                        funds.add(holding);
                        addToHashmap(funds.size()-1);
                    } catch (Investment.priceException | Investment.nameException | Investment.quantityException | Investment.symbolException e) {
                        //FAILED TO LOAD
                    }

                }
                if (line.equalsIgnoreCase("MutualFund")) {
                    try {
                        MutualFund holding = new MutualFund(name, symbol, quantity, price, bookValue);
                        funds.add(holding);
                        addToHashmap(funds.size() - 1);
                    } catch (Investment.priceException | Investment.nameException | Investment.quantityException | Investment.symbolException e) {
                        //FAILED TO LOAD
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException error) {
            return;
        }
    }

    /**
     * This method is called inside the openPortfolio method. This basically saves all the
     * information on the Funds Arraylist to a file that the user specifies.
     * This method only runs as the user exits from the program
     * @param file
     */
    public void saveInvestment(String file) {
        String fileName = file;

        if (funds.isEmpty()) {
            return;
        }
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));

            for (int i = 0; i < funds.size(); i++) {
                if (funds.get(i).getClass().equals(Stock.class)) {
                    writer.write("Stock");
                }
                if (funds.get(i).getClass().equals(MutualFund.class)) {
                    writer.write("MutualFund");
                }
                writer.newLine();
                writer.write(funds.get(i).getSymbol());
                writer.newLine();
                writer.write(funds.get(i).getName());
                writer.newLine();
                writer.write("" + funds.get(i).getQuantity());
                writer.newLine();
                writer.write("" + funds.get(i).getPrice());
                writer.newLine();
                writer.write("" + funds.get(i).getBookValue());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving to file...");
        }
    }

    /**
     * This method adds user the Fund's name section to a hashmap that is used to filter the user's
     * search criteria faster.
     * The Parameter it takes is the ArrayList position to add to the Hashmap
     * @param placement
     */
    public void addToHashmap (int placement) {

        //Copying the String that Needs to be Split into a new Object
        String str = funds.get(placement).getName();
        //Now creating an Empty Array of Strings
        String[] words;
        //Now try Catch
        try {
            //create an Integer object too add to the location.
            Integer instanceNumber = new Integer(placement);

            //Now Split the words
            words = str.split("[^a-zA-Z-0-9]+");

            //In a for loop, evaluate all the Words individually and check if they are Unique to the Hashmap or already present.
            for (int i = 0; i < words.length; i++) {

                //Check if the word is Unique...
                if (!(map.containsKey(words[i].toLowerCase()))) {

                    //Creating an ArrayList to Store locations of the words
                    ArrayList<Integer> location = new ArrayList<>();

                    //Adding the instanceNumber to the Location ArrayList
                    location.add(instanceNumber);

                    //Now Put in Map.
                    map.put(words[i].toLowerCase(), location);
                }
                //Else If the word is not Unique...
                else {
                    //Check to see if the Word in the Map already has the InstanceNumber recorded in its ArrayList If it doesn't, add the new instanceNumber to that Key's Value ArrayList.
                    if (!(map.get(words[i].toLowerCase()).contains(instanceNumber))) {
                        map.get(words[i].toLowerCase()).add(instanceNumber);
                    }
                }
            }
        } catch(Exception e) {
            System.out.println(">>>>>Oops! Error in Adding Hashmap (Add to Hashmap)<<<<<");
            return;
        }
    }

    /**
     * This method is only called when the program has to delete an Investment.
     * It basically re-Hashes the whole Funds ArrayList.
     */
    public void removeFromHashmap() {
        map.clear();
        try {
            for (int i = 0; i < funds.size(); i++) {
                addToHashmap(i);
            }
        } catch (Exception e) {
            System.out.println("Oops! Error in Removing Fund from Hashmap (Remove from Hashmap)");
            return;
        }
    }

    /**
     * This method is called from openPortfolio method. This is the method that lets users search
     * VIA the 3 criteria of Symbol, price range and Keywords.
     * Inside the Method all 3 are asked and then the function Zero's down the fund objects that match
     * the user's search criteria
     * @param print
     * @param symbolRead
     * @param nameKeys
     * @param minNumRead
     * @param maxNumRead
     * @param onlyNumRead
     * @throws Investment.priceException
     * @throws Investment.symbolException
     * @throws Investment.quantityException
     * @throws Investment.nameException
     */
    public void searchList(JTextArea print, String symbolRead, String nameKeys, Double minNumRead, Double maxNumRead, Double onlyNumRead) throws Investment.priceException, Investment.symbolException, Investment.quantityException, Investment.nameException {

        //*************************************************//
        //*This section is for searching using the Symbol *//
        //*************************************************//

        String symbolSearch = symbolRead;

        //***************************************************************//
        //*This section is for filtering the Search range the user gives*//
        //***************************************************************//

        double onlyNum = onlyNumRead;
        double minNum = minNumRead;
        double maxNum = maxNumRead;

        //****************************************************************//
        //*This section is for searching using Key Words the user Enters *//
        //****************************************************************//

        String userSearchSentence;
        String[] keyWords;
        int numKeywords = -1;
        ArrayList<Integer> location = new ArrayList<>();

        userSearchSentence = nameKeys;

        //If the user decided to use the Search my Key words method
        if(!(userSearchSentence.trim().isEmpty())) {
            try {
                //Split the String
                keyWords = userSearchSentence.toLowerCase().split("[^a-zA-Z-0-9]+");

                //Count the Number of key words that are assumed to be words/Numbers.
                numKeywords = keyWords.length;
                //If there are Keywords that the user entered...
                if (numKeywords > 0) {

                    //Now we need to find the Key words in the HashMap. If we have a match, we record the number down.
                    //Once we have all the numbers from all the different Keywords, we copy those specific locations
                    //to the searchFound array.

                    //Initializing the ArrayList location
                    for (int u = 0; u < funds.size() ; u++) {
                        Integer contains = new Integer(u);
                        location.add(contains);
                    }

                    for (int i = 0; i < numKeywords; i++) {
                        //If the word is found in the Hashmap
                        if (map.containsKey(keyWords[i].toLowerCase())) {
                            //Go through the funds ArrayList
                            for (int j = 0; j < funds.size(); j++) {
                                Integer J = new Integer(j);
                                //If the location does not contain the same numbers as the Keyword's location, remove that number from location.
                                if ((location.contains(J)) && (!(map.get(keyWords[i].toLowerCase()).contains(J)))) {
                                    location.remove(J);
                                }

                            }
                        }
                    }
                    //Now our location ArrayList should be populated with all intersection numbers.
                }

            }catch (Exception e) {
                print.append("Error in Searching...");
                generalError(print);
            }
        }

        //*************************************************************************************************************//
        //*This section is used to subtract objects that don't fall withing the user's search range (From searchFound)*//
        //*************************************************************************************************************//

        //**************************************************************************//
        //*First Using the KeyWords to isolate the Objects that match user's search*//
        //**************************************************************************//
        //If the user decided to search using KeyWords, Isolate the locations and move to searchFound

        if (numKeywords > 0) {
            int foundKeys = 0;
            for (int i = 0; i < location.size(); i++) {
                if (funds.get(location.get(i)).getClass().equals(Stock.class)) {
                    Investment holding = new Stock(funds.get(location.get(i)).getName(), funds.get(location.get(i)).getSymbol(), funds.get(location.get(i)).getQuantity(), funds.get(location.get(i)).getPrice(), funds.get(location.get(i)).getBookValue());
                    searchFound.add(holding);
                    foundKeys++;
                }
                if (funds.get(location.get(i)).getClass().equals(MutualFund.class)) {
                    Investment holding = new MutualFund(funds.get(location.get(i)).getName(), funds.get(location.get(i)).getSymbol(), funds.get(location.get(i)).getQuantity(), funds.get(location.get(i)).getPrice(), funds.get(location.get(i)).getBookValue());
                    searchFound.add(holding);
                    foundKeys++;
                }
            }
            if ((foundKeys == 0) && (!(symbolSearch.trim().isEmpty())) && (( minNum != -1) || (maxNum != -1) || (onlyNum != -1)))   {
                populateSupportArray();
            }
        }
        //Else, copy the while funds ArrayList to searchFound
        else {
            populateSupportArray();
        }


        //*******************************************************************************************************//
        //*Now if the User decided to use the Symbol search option, remove objects from holding that don't match*//
        //*******************************************************************************************************//

        //If the Symbol section is populated, the find all the Symbols that are in the ArrayList for Stock and Mutual fund and copy into a new array list.
        if (!(symbolSearch.trim().isEmpty())) {
            ArrayList<Investment> tempArray = new ArrayList<>();
            //Adding the Stock with the same Symbol as user requested to an array.
            int size = searchFound.size();
            for (int i = 0; i < size; i++) {
                if ((symbolSearch.toLowerCase().equals(searchFound.get(i).getSymbol().toLowerCase()))) {
                    if (searchFound.get(i).getClass().equals(Stock.class)) {
                        Investment holding = new Stock(searchFound.get(i).getName(), searchFound.get(i).getSymbol(), searchFound.get(i).getQuantity(), searchFound.get(i).getPrice(), searchFound.get(i).getBookValue());
                        tempArray.add(holding);
                    }
                    if (searchFound.get(i).getClass().equals(MutualFund.class)) {
                        Investment holding = new MutualFund(searchFound.get(i).getName(), searchFound.get(i).getSymbol(), searchFound.get(i).getQuantity(), searchFound.get(i).getPrice(), searchFound.get(i).getBookValue());
                        tempArray.add(holding);
                    }
                }
            }
            searchFound.clear();
            for (int i = 0; i < tempArray.size(); i++) {
                if (tempArray.get(i).getClass().equals(Stock.class)) {
                    Investment holding = new Stock(tempArray.get(i).getName(), tempArray.get(i).getSymbol(), tempArray.get(i).getQuantity(), tempArray.get(i).getPrice(), tempArray.get(i).getBookValue());
                    searchFound.add(holding);
                }
                if (tempArray.get(i).getClass().equals(MutualFund.class)) {
                    Investment holding = new MutualFund(tempArray.get(i).getName(), tempArray.get(i).getSymbol(), tempArray.get(i).getQuantity(), tempArray.get(i).getPrice(), tempArray.get(i).getBookValue());
                    searchFound.add(holding);
                }
            }
        }
        //*****************************************************************************************//
        //*Now we know what the Max, Min and Only values are... We can filter our searchFound list*//
        //*****************************************************************************************//
        //If all the values are -1, that means that the user skipped this search
        if (( minNum != -1) || (maxNum != -1) || (onlyNum != -1)) {
            ArrayList<Investment> extra = new ArrayList<>();
            //If there is a Min value AND max value
            if (( minNum != -1) || (maxNum != -1)) {
                //Delete the funds that are not within that range
                for (int i = 0; i < searchFound.size(); i++) {
                    if ((searchFound.get(i).getPrice() >= minNum) && (searchFound.get(i).getPrice() <= maxNum)) {
                        if (searchFound.get(i).getClass().equals(Stock.class)) {
                            Investment holding = new Stock(searchFound.get(i).getName(), searchFound.get(i).getSymbol(), searchFound.get(i).getQuantity(), searchFound.get(i).getPrice(), searchFound.get(i).getBookValue());
                            extra.add(holding);
                        }
                        if (searchFound.get(i).getClass().equals(MutualFund.class)) {
                            Investment holding = new MutualFund(searchFound.get(i).getName(), searchFound.get(i).getSymbol(), searchFound.get(i).getQuantity(), searchFound.get(i).getPrice(), searchFound.get(i).getBookValue());
                            extra.add(holding);
                        }
                    }
                }
            }
            //If only Min value is present
            if (( minNum != -1) && (maxNum == 0)) {
                for (int i = 0; i < searchFound.size(); i++) {
                    if ((searchFound.get(i).getPrice() >= minNum)) {
                        if (searchFound.get(i).getClass().equals(Stock.class)) {
                            Investment holding = new Stock(searchFound.get(i).getName(), searchFound.get(i).getSymbol(), searchFound.get(i).getQuantity(), searchFound.get(i).getPrice(), searchFound.get(i).getBookValue());
                            extra.add(holding);
                        }
                        if (searchFound.get(i).getClass().equals(MutualFund.class)) {
                            Investment holding = new MutualFund(searchFound.get(i).getName(), searchFound.get(i).getSymbol(), searchFound.get(i).getQuantity(), searchFound.get(i).getPrice(), searchFound.get(i).getBookValue());
                            extra.add(holding);
                        }
                    }
                }
            }
            //If only Max value is present
            if (( minNum == 0) && (maxNum != -1)) {
                for (int i = 0; i < searchFound.size(); i++) {
                    if ((searchFound.get(i).getPrice() <= maxNum)) {
                        if (searchFound.get(i).getClass().equals(Stock.class)) {
                            Investment holding = new Stock(searchFound.get(i).getName(), searchFound.get(i).getSymbol(), searchFound.get(i).getQuantity(), searchFound.get(i).getPrice(), searchFound.get(i).getBookValue());
                            extra.add(holding);
                        }
                        if (searchFound.get(i).getClass().equals(MutualFund.class)) {
                            Investment holding = new MutualFund(searchFound.get(i).getName(), searchFound.get(i).getSymbol(), searchFound.get(i).getQuantity(), searchFound.get(i).getPrice(), searchFound.get(i).getBookValue());
                            extra.add(holding);
                        }
                    }
                }
            }
            //If only have to search for one variable.
            if (( minNum == -1) || (maxNum == -1) || (onlyNum != -1)) {
                for (int i = 0; i < searchFound.size(); i++) {
                    if ((searchFound.get(i).getPrice() == onlyNum)) {
                        if (searchFound.get(i).getClass().equals(Stock.class)) {
                            Investment holding = new Stock(searchFound.get(i).getName(), searchFound.get(i).getSymbol(), searchFound.get(i).getQuantity(), searchFound.get(i).getPrice(), searchFound.get(i).getBookValue());
                            extra.add(holding);
                        }
                        if (searchFound.get(i).getClass().equals(MutualFund.class)) {
                            Investment holding = new MutualFund(searchFound.get(i).getName(), searchFound.get(i).getSymbol(), searchFound.get(i).getQuantity(), searchFound.get(i).getPrice(), searchFound.get(i).getBookValue());
                            extra.add(holding);
                        }
                    }
                }
            }
            searchFound.clear();
            for (int i = 0; i < extra.size(); i++) {
                if (extra.get(i).getClass().equals(Stock.class)) {
                    Investment holding = new Stock(extra.get(i).getName(), extra.get(i).getSymbol(), extra.get(i).getQuantity(), extra.get(i).getPrice(), extra.get(i).getBookValue());
                    searchFound.add(holding);
                }
                if (extra.get(i).getClass().equals(MutualFund.class)) {
                    Investment holding = new MutualFund(extra.get(i).getName(), extra.get(i).getSymbol(), extra.get(i).getQuantity(), extra.get(i).getPrice(), extra.get(i).getBookValue());
                    searchFound.add(holding);
                }
            }
            extra.clear();
        }
        //Else the user decided to skip searching by price, so just don't touch the searchFound ArrayList.

        //*************************************************************************************************************//
        //*Now the searchFound ArrayList should only contain the Objects that the user wanted to search for, Print now*//
        //*************************************************************************************************************//
        printSearchArrayList(print);
        searchFound.clear();
        return;
    }

    //**************************************************************************//
    //*This section is for Supporting Functions that are used by main functions*//
    //**************************************************************************//

    /**
     * This is a Support Method that adds all the Funds ArrayList to the Search ArrayList
     * @throws Investment.priceException
     * @throws Investment.symbolException
     * @throws Investment.quantityException
     * @throws Investment.nameException
     */
    public void populateSupportArray() throws Investment.priceException, Investment.symbolException, Investment.quantityException, Investment.nameException {
        for (int i = 0; i < funds.size(); i++) {
            if (funds.get(i).getClass().equals(Stock.class)) {
                Investment holding = new Stock(funds.get(i).getName(), funds.get(i).getSymbol(), funds.get(i).getQuantity(), funds.get(i).getPrice(), funds.get(i).getBookValue());
                searchFound.add(holding);
            }
            if (funds.get(i).getClass().equals(MutualFund.class)) {
                Investment holding = new MutualFund(funds.get(i).getName(), funds.get(i).getSymbol(), funds.get(i).getQuantity(), funds.get(i).getPrice(), funds.get(i).getBookValue());
                searchFound.add(holding);
            }
        }
    }

    //**************************************************************************//
    //***This section is for the Print messages (Errors and Successes)**********//
    //**************************************************************************//

     /**
     *  Print message for Stock being updated
     * @param print
     */
    protected void stockUpdated(JTextArea print) {
        print.append("****The Stock values have been updated!****\n");
    }

    /**
     *  Print message for mutual fund values being updated
     * @param print
     */
    protected void mutualUpdated(JTextArea print) {
        print.append("****The Mutual Fund values have been updated!****\n");
    }

    /**
     *  Print message for General errors
     * @param print
     */
    protected void generalError(JTextArea print) {
        print.append("An Error occurred! Please try again...\n");
    }

    /**
     *  Print message for telling user Value present error
     * @param print
     */
    protected void valuePresentError(JTextArea print) {
        print.append("Error! This is already in the Portfolio.\n");
    }

    /**
     * Print message for Success response
     * @param print
     */
    protected void successResponse(JTextArea print) {
        print.append(">> Success! New member added/Edited in your Portfolio.\n");
    }

}
