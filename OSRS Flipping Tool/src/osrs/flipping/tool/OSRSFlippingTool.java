////RIP the old console app
//
//package osrs.flipping.tool;
//
//import java.util.List;
//import java.util.Scanner;
//
//public class OSRSFlippingTool {
//    
//    public static void main(String[] args) throws Exception{   
//        //create instance of FlipTool class (where all the logic is)
//        FlipTool fliptool = new FlipTool();
//        
//        //declare List that results will be stored in
//        List<String> flipList;
//        
//        //when this = true we'll exit the application
//        boolean quit = false;
//        
//        //scanner to get input from user
//        Scanner inp = new Scanner(System.in);
//        
//        //ints where we store user criteri (max item price and minimum % profit)
//        int maxPrice,minProfit;
//        
//        //introduction
//        System.out.println("OSRS FLIPPING TOOL");
//        System.out.println();
//        System.out.println("This is a tool to help with flipping in oldschool runescape.");
//        System.out.println("At the moment it is very very basic (i havn't even made a user interface) but if people are interested i can develop it further");      
//        System.out.println();
//        System.out.println("HOW IT WORKS :");
//        System.out.println();
//        System.out.println("1) first you are prompted to set a max price. no items with a buy price above this will be included in the search");
//        System.out.println();
//        System.out.println("2) next you will be prompted to set a minimum percentage profit per item e.g if you buy an item for 100gp");
//        System.out.println("and sell for 110gp you made a 10% profit");
//        System.out.println();
//        System.out.println("3) then the program does the rest comparing your criteria to a complete list of ge items and buy/sell prices from");
//        System.out.println("the OSBuddy Exchange (they update it every 15 mins) and printing the results on the screen for you too see");
//        System.out.println();
//        System.out.print("press enter to begin...");
//        inp.nextLine();
//        System.out.println();
//        
//        //talk to the user
//        while(quit == false){
//            //get user input (not bothering with validation for the moment if people want to break it they can feel free)
//            System.out.println("Flip Search : ");
//            System.out.println();
//            System.out.print("Enter Max Item Price (gp) : ");
//            maxPrice = inp.nextInt();
//            System.out.println();
//            System.out.print("Enter minimum profit per item (%) : ");
//            minProfit = inp.nextInt();
//            
//            flipList = fliptool.generateFlipList(maxPrice,minProfit);
//            
//            System.out.println();
//            
//          
//            if(!flipList.isEmpty()){
//                //if flipList has items in it
//                System.out.println(flipList.size() + " items found matching your criteria");
//                
//                //display each item on screen
//                for(String item : flipList){
//                    System.out.println();
//                    System.out.println(item);
//                }
//                
//            }else{
//                //otherwise give them the bad news
//                System.out.println("Sorry there were no items that matched your search :(");
//            }
//            
//            //String where we store users decision to quit or retry at the end
//            String whatNext="";
//            
//            //keep asking them till they enter either option
//            while(!whatNext.equals("Q")&&!whatNext.equals("R")){
//                System.out.println();
//                System.out.print("Type q to quit or r to retry :");
//                whatNext = inp.next();
//                whatNext = whatNext.toUpperCase();
//                
//                //set quit = true so loop exits if the enter q
//                if(whatNext.equals("Q"))
//                    quit = true;
//            }
//        }
//        
//        //say farewell :(
//        System.out.println("Bye :(");
//    }
//    
//}
