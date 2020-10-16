package Scanners;

import SQL.SQLWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import DataTypes.Location;
import DataTypes.Sale;

public class TMScan {
    private static SQLWriter writer;

    public TMScan(){
        writer=new SQLWriter();
    }


    //Performs keyword scan on all keywords in provided file path and parses them to SQL
    public static void scanAll(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String keyword;
            while ((keyword = br.readLine()) != null) {
                ArrayList<String> allPages = getEventsByKeyword(keyword);
                for(String page: allPages){
                    parseALL(page);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //scans random selection of keywords from provided file path and parses them to SQL
    public static void scanRandom(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String keyword;
            ArrayList<String> inputArray=new ArrayList<>();
            while ((keyword = br.readLine()) != null) {
                inputArray.add(keyword);
            }
            Random r = new Random();
            int rand = 0;
            if(inputArray.size()<=100){
                rand = inputArray.size();
            }
            else{
                rand=r.nextInt((inputArray.size()/4)-100)+100;
            }
            Collections.shuffle(inputArray);
            for(int i=0; i<rand; i++){
                ArrayList<String> allPages = getEventsByKeyword(inputArray.get(i));
                for(String page: allPages){
                    parseALL(page);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ticketmaster api called with specified keyword and returns ArrayList of all data returned
    public static ArrayList<String> getEventsByKeyword(String keyword) throws Exception {
        ArrayList<String> pageList = new ArrayList<>();
        keyword = keyword.toLowerCase();
        keyword = keyword.replaceAll(" ", "+");

        String request = "https://app.ticketmaster.com/discovery/v2/events.json?keyword=" + keyword + "&" + "apikey=GiwPzvBlaotCACREGQQ2q7XEQKxOerpx&size=199";
        StringBuilder allText = new StringBuilder();
        URL url = new URL(request);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        Thread.sleep(900);

        String line;
        while ((line = rd.readLine()) != null) {
            allText.append(line);
        }

        rd.close();
        String firstPage = allText.toString();
        pageList.add(firstPage);
        JSONObject data = new JSONObject(firstPage);
        int numPages = data.getJSONObject("page").getInt("totalPages");
        for(int i=0; (i<numPages)&&(i<5); i++){
            allText = new StringBuilder();
            request = "https://app.ticketmaster.com/discovery/v2/events.json?keyword=" + keyword + "&" + "apikey=GiwPzvBlaotCACREGQQ2q7XEQKxOerpx&size=199"+"&page="+String.valueOf(i);
            url = new URL(request);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Thread.sleep(900);
            while ((line = rd.readLine()) != null) {
                allText.append(line);
            }
            String nextPage = allText.toString();
            pageList.add(nextPage);
        }
        return pageList;
    }

    //Parses json string and uses SQLWriter to enter into database
    public static void parseALL(String json) {
        JSONObject data = new JSONObject(json);
        if(data.has("_embedded")){
            JSONArray events = data.getJSONObject("_embedded").getJSONArray("events");
            for (int i = 0; i < events.length(); i++) {
                JSONObject event = events.getJSONObject(i);
                Sale onSale = new Sale();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();

                //parse performer name
                if(event.has("name")){
                    String performers = event.getString("name");
                    onSale.setPerformer(performers);
                }

                //parse sale url
                if (event.has("url")) {
                    String link = event.getString("url");
                    onSale.setLink(link);
                }

                //parse sale date
                if((event.has("sales"))&&(event.getJSONObject("sales").has("public"))&&(event.getJSONObject("sales").getJSONObject("public").has("startDateTime"))){
                    String onSaleDate = event.getJSONObject("sales").getJSONObject("public").getString("startDateTime");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime localDate = LocalDateTime.parse(onSaleDate.replaceAll("T", " ").replaceAll("Z", ""), formatter);
                    localDate = localDate.plusHours(-5);
                    onSale.setSaleDate(localDate.format(formatter));
                }

                //parse performance date
                if(event.getJSONObject("dates").getJSONObject("start").has("dateTime")){
                    String perfDate = event.getJSONObject("dates").getJSONObject("start").getString("dateTime");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime localDate = LocalDateTime.parse(perfDate.replaceAll("T", " ").replaceAll("Z", ""), formatter);
                    localDate = localDate.plusHours(-5);
                    onSale.setPerfDate(localDate.format(formatter));
                }

                //parse location data from json string
                if((event.has("_embedded"))&&(event.getJSONObject("_embedded").has("venues"))&&(event.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).has("name"))){
                    if((event.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).has("city"))&&(event.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).has("state"))){
                        String venue = event.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("name");
                        String city = event.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("city").getString("name");
                        String state = event.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getJSONObject("state").getString("name");
                        Location loc = new Location();
                        loc.setCity(city);
                        loc.setState(state);
                        loc.setVenue(venue);
                        loc.setDateFound(dateFormat.format(date));
                        onSale.setLocation(venue);
                        writer.insert(loc);
                    }
                }
                onSale.setDateFound(dateFormat.format(date));
                onSale.setOnSale("public");
                writer.insert(onSale);

                //parse presale data
                if (event.getJSONObject("sales").has("presales")) {
                    JSONArray presales = event.getJSONObject("sales").getJSONArray("presales");
                    for (int j = 0; j < presales.length(); j++) {
                        JSONObject preObj = presales.getJSONObject(j);
                        Sale presale = new Sale();
                        presale.setPerformer(onSale.getPerformer());
                        presale.setDateFound(dateFormat.format(date));
                        presale.setLocation(onSale.getLocation());
                        presale.setLink(onSale.getLink());
                        presale.setPerfDate(onSale.getPerfDate());
                        if(preObj.has("startDateTime")){
                            String presaleDate = preObj.getString("startDateTime");
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            LocalDateTime localDate = LocalDateTime.parse(presaleDate.replaceAll("T", " ").replaceAll("Z", ""), formatter);
                            localDate = localDate.plusHours(-5);
                            presale.setSaleDate(localDate.format(formatter));
                        }
                        if(preObj.has("name")){
                            presale.setOnSale(preObj.getString("name"));
                        }
                        writer.insert(presale);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        TMScan tm = new TMScan();

        //inorder to not hit ticket masters api too hard at once with very large list of performers a random selection is chosen and
        //scanned multiple times per day
        scanRandom("venues_large.txt");
        scanAll("performers.txt");
    }

}
