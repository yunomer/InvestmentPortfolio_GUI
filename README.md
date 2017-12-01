# InvestmentPortfolio_GUI
Investment Portfolio with a GUI implemented (CIS*2430 Object Orientated Programming)
In this program, I implement a GUI on top of a previous assignmet for the course that lets 
users manage their Investments funds (Stocks and Mutual funds).

******************
Assignment 3

Test file I used is included in the Folder
*****************
---------------------
The General Problem:
---------------------
Create an OOP program that manages a stock inverster's portfolio, and to make it easier for the Investor, a GUI is implemented for ease of use.

-------------------------------------------
Assumptions and limitations of my Solution:
-------------------------------------------
It is assumed that the user will read the on screen command properly and follow instructions. It is also assumed that the user understands how to navigate
through the menu bar in the program and that they understand the basics of running a program.
A limitation of the program is that the user cannot search the Investments by the "type", as in they cannot select that they want to search for a mutual fund or
a stock.

----------------------------------
Building and testing the function:
----------------------------------
The user will have to compile and run the program on an IDE of their choice.
Recommended IDE would be Netbeans or IntelliJ.

The user can also compile from console:

javac filename.java

java file

-------------------------------
Running/Loading/Saving program:
-------------------------------
When the user runs the program from the Command line or from an IDE, the user has the option to enter a file name to 
load investments, or if they do not, the program creates a new File for them called "my_Investments.txt".
The Program saves the Investments to the file before exiting the program every time.

-------------------------------
Program tested for correctness:
-------------------------------
The program was run using multiple test cases:

Type of Fund: Stock, Name: Google Technology Company, Symbol: GOOGLE, Quantity: 450, Price: 80.6, Book Value: 36279.99
Type of Fund: Mutual Fund, Name: CI Signature Select Canadian, Symbol: CIG677, Quantity: 450, Price: 20.0, Book Value: 9000.0
Type of Fund: Stock, Name: Facebook Inc, Symbol: FB, Quantity: 1000, Price: 205.0, Book Value: 205009.99
Type of Fund: Stock, Name: Intel Corporation, Symbol: INTC, Quantity: 350, Price: 58.99, Book Value: 20656.49
Type of Fund: Mutual Fund, Name: Oracle Corporation, Symbol: ORCL, Quantity: 800, Price: 150.45, Book Value: 120359.99999999999
Type of Fund: Stock, Name: Activision Blizzard Inc, Symbol: ATVI, Quantity: 320, Price: 12.88, Book Value: 4131.59
Type of Fund: Stock, Name: Adobe Systems Incorporated, Symbol: ADBE, Quantity: 670, Price: 90.12, Book Value: 60390.39
Type of Fund: Mutual Fund, Name: Applied Materials Inc, Symbol: AMAT, Quantity: 335, Price: 59.17, Book Value: 19821.95
Type of Fund: Stock, Name: Real Canadian, Symbol: RC, Quantity: 120, Price: 45.0, Book Value: 5409.99
Type of Fund: Stock, Name: Autodeak Inc, Symbol: ADSK, Quantity: 945, Price: 26.84, Book Value: 25373.79
Type of Fund: Mutual Fund, Name: Analog Devices Inc, Symbol: ADI, Quantity: 62, Price: 33.0, Book Value: 2046.0
Type of Fund: Mutual Fund, Name: 2U Inc, Symbol: TWOU, Quantity: 266, Price: 3.32, Book Value: 883.12
Type of Fund: Stock, Name: University of Guelph, Canada, Symbol: UOG, Quantity: 780, Price: 23.67, Book Value: 18472.590000000004
Type of Fund: Stock, Name: Toronto Dominion Bank, Symbol: TD, Quantity: 500, Price: 50.0, Book Value: 25009.99
Type of Fund: Stock, Name: University of Toronto, Symbol: UOT, Quantity: 800, Price: 150.0, Book Value: 120009.99
Type of Fund: Mutual Fund, Name: Aaron's Inc, Symbol: AAN, Quantity: 100, Price: 26.6, Book Value: 2660.0
Type of Fund: Stock, Name: Air Lease Corporation, Symbol: AL, Quantity: 227, Price: 44.6, Book Value: 10134.19
Type of Fund: Stock, Name: Acxiom Corporation, Symbol: ACXM, Quantity: 1050, Price: 550.0, Book Value: 577509.99
Type of Fund: Mutual Fund, Name: Bank of the Ozarks, Symbol: OZRK, Quantity: 552, Price: 56.01, Book Value: 30917.52
Type of Fund: Mutual Fund, Name: Canadian Blackhawk Network Holdings Inc, Symbol: HAWK, Quantity: 540, Price: 54.0, Book Value: 29160.0
Type of Fund: Stock, Name: Bank of Hawaii Corporation, Symbol: BOH, Quantity: 335, Price: 52.8, Book Value: 17697.99

The program was tested for Searching, Creating the HashMap properly and keeping track of the book value.

For example, searching for:
Keyword: INC
Low price: 100
high price: EMPTY
Gives the user:

**************************************************
Type of Fund: Stock, Name: Facebook Inc, Symbol: FB, Quantity: 1000, Price: 205.0, Book Value: 205009.99
**************************************************

-------------
Imporvements:
-------------
1. It would be recommended to create a "Help" or instructions panel for the program so the user can refer to that to learn how to use the program.
2. It would be recommeded to create a equles method inside the sub-classes for the investment class.
3. When loading from a file, if the load file has been tampered with, it might crash the program. So need to develop better way to save and load file so that Program knows if file data being read is not in order.
