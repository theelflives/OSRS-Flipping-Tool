package osrs.flipping.tool;

import java.awt.Desktop;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class OSRSFlippingTool {

    public static void main(String[] args) throws Exception{   
        //create instance of FlipTool class (where all the logic is)
        FlipTool fliptool = new FlipTool();
        
        //declare List that results will be stored in
        List<String> flipList;
        
        //when this = true we'll exit the application
        boolean quit = false;
        
        //scanner to get input from user
        Scanner inp = new Scanner(System.in);
        
        //ints where we store user criteri (max item price and minimum % profit)
        int maxPrice,minProfit;
        
        //introduction
        System.out.println("OSRS FLIPPING TOOL");
        System.out.println();
        System.out.println("This is a tool to help with flipping in oldschool runescape.");
        System.out.println("At the moment it is very very basic , i havn't even made a user interface but");
        System.out.println("if people are interested i can develop it further");      
        System.out.println();
        System.out.println("HOW IT WORKS :");
        System.out.println();
        System.out.println("1) first you are prompted to set a max price.");
        System.out.println("no items with a buy price above this will be included in the search");
        System.out.println();
        System.out.println("2) next you will be prompted to set a minimum percentage profit per item ");
        System.out.println("e.g if you buy an item for 100gp and sell for 110gp you made a 10% profit");
        System.out.println();
        System.out.println("3) then the program searches for items in the ge matching your criteria");
        System.out.println("and prints the results to a text file in the same folder as itself");
        System.out.println();
        System.out.print("press enter to begin...");
        inp.nextLine();
        System.out.println();
        
        //talk to the user
        while(quit == false){
            //get user input (not bothering with validation for the moment if people want to break it they can feel free)
            System.out.println("Flip Search : ");
            System.out.println();
            System.out.print("Enter Max Item Price (gp) : ");
            maxPrice = inp.nextInt();
            System.out.println();
            System.out.print("Enter minimum profit per item (%) : ");
            minProfit = inp.nextInt();
            
            //get list of all items matching criteria
            flipList = fliptool.generateFlipList(maxPrice,minProfit);
            
            System.out.println();
            
            //declare variables used in writing to results file
            PrintWriter resultsPrinter;
            String filename,timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
            
            if(!flipList.isEmpty()){
                //if flipList has items in it
                
                //name of file results will be save too
                filename = maxPrice + "gp" + minProfit + "%profit.txt";
                
                //print info at top of file
                resultsPrinter = new PrintWriter(filename);
                resultsPrinter.println("Created at : " + timeStamp);
                resultsPrinter.println("Criteria -  Max Item Price : " + maxPrice + "gp Minimum Profit : " + minProfit + "%");
                resultsPrinter.println(flipList.size() + " items found matching your criteria");
                
                //loop through items and print to file
                for(String item : flipList){
                    resultsPrinter.println();
                    resultsPrinter.println(item);
                }
                
                //close file
                resultsPrinter.close();
                
                //let user know it worked
                System.out.println(flipList.size() + " items found matching your criteria");
                System.out.println("results saved to file : " + new File(filename).getPath());
                
                //open results file in default text editor of users computer
                Desktop.getDesktop().edit(new File(filename));
                System.out.println();
            }else{
                //otherwise give them the bad news
                System.out.println("Sorry there were no items that matched your search :(");
            }
            
            //String where we store users decision to quit or retry at the end
            String whatNext="";
            
            //keep asking them till they enter either option
            while(!whatNext.equals("Q")&&!whatNext.equals("R")){
                System.out.println();
                System.out.print("Type q to quit or r to retry :");
                whatNext = inp.next();
                whatNext = whatNext.toUpperCase();
                
                //set quit = true so loop exits if the enter q
                if(whatNext.equals("Q"))
                    quit = true;
            }
        }
        
        //say farewell :(
        System.out.println("Bye :(");
    }
    
}
