package osrs.flipping.tool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class FlipTool {  
    private String proxyAddress;
    private int proxyPort;
    private boolean useProxy;
    
    //default constructor
    public FlipTool(){
        useProxy = false;
    }
    
    //constructor if your using a proxy
    public FlipTool(String proxyAddress, int proxyPort){
        this.proxyAddress = proxyAddress;
        this.proxyPort = proxyPort;
        useProxy = true;
    }
    
    //get item list as JSONObject from url without proxy (default)
    //this is where we get the osbuddy data from their site
    private JSONObject getItemListNoProxy(String url) throws Exception{
        URL target = new URL(url);
        HttpURLConnection uc = (HttpURLConnection)target.openConnection();
        uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        uc.connect();

        String line;
        StringBuffer tmp = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        while ((line = in.readLine()) != null) {
          tmp.append(line);
        }
       
        return new JSONObject(tmp.toString());
    }      
    
    //get item list as JSONObject from url if you are using a proxy (probably not applicable to most people)
    //this is where we get the osbuddy data from their site
    private JSONObject getItemListViaProxy(String url)throws Exception{
        URL target = new URL(url);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyAddress,proxyPort));
        HttpURLConnection uc = (HttpURLConnection)target.openConnection(proxy);
        uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        uc.connect();

        String line;
        StringBuffer tmp = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        while ((line = in.readLine()) != null) {
          tmp.append(line);
        }

        return new JSONObject(tmp.toString());
    }
    
    //this is where we go through the list and compare prices and stuff
    public List<GEItem> generateFlipList(int maxItemPrice, int minPercentMargin) throws Exception{
        //declare result variable to be returned
        List<GEItem> result = new ArrayList<>();
        JSONObject itemSummary;
        
        //get OSBuddy summary.json(list of all ge items) list as JSONObject
        if(useProxy == false){
            itemSummary = getItemListNoProxy("https://rsbuddy.com/exchange/summary.json");
        }else{
            itemSummary = getItemListViaProxy("https://rsbuddy.com/exchange/summary.json");
        }
        
        //loop through all keys in JSONObject
        for(String itemId : itemSummary.keySet()){
            
            //get JSONObject of current key from itemSummary
            JSONObject item = itemSummary.getJSONObject(itemId);
            
            //get average sell price and buy price and calculate the difference
            double sell = item.getInt("sell_average"),
                buy = item.getInt("buy_average"),
                diff = sell-buy,
                profitPercent;
            
            //if item price is less than or equal to max price set in parameters 
            if(buy <= maxItemPrice){
                
                //calculate profit made per cent on each item as long as buy and sell price are not equal
                profitPercent = (buy > 0 && diff > 0) ? (diff/buy)*100 : -1;
                
                //if the percentage margin per item is more than or equal too the minimum set in param minPercentMargin
                if(profitPercent >= minPercentMargin){
                    
                    //create new GEItem
                    GEItem geitem = new GEItem(item.getString("name"),item.getInt("id"),(int)buy,(int)sell,item.getInt("sp"),item.getBoolean("members"));
                    
                    //add to result
                    result.add(geitem);
                
                }
            }
        }
        
        //return list of items
        return result;
    }
    
}
