package Scanners;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import SQL.SQLWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import DataTypes.EventData;
import DataTypes.Location;
import DataTypes.Performer;
import DataTypes.Sale;


public class GeekScan {
    static SQLWriter writer;

    public GeekScan(){
        writer = new SQLWriter();
    }

    //runs get getEventsByVenueState for all states and parses resulting data to database using SQLWriter
    public void runALLStates(){
        ArrayList<String> stateList= new ArrayList<>();
        stateList.add("al");stateList.add("ak");
        stateList.add("az");stateList.add("ar");
        stateList.add("ca");stateList.add("co");
        stateList.add("ct");stateList.add("de");
        stateList.add("fl");stateList.add("ga");
        stateList.add("hi");stateList.add("id");
        stateList.add("il");stateList.add("in");
        stateList.add("ia");stateList.add("ks");
        stateList.add("ky");stateList.add("la");
        stateList.add("me");stateList.add("md");
        stateList.add("ma");stateList.add("mi");
        stateList.add("mn");stateList.add("ms");
        stateList.add("mo");stateList.add("mt");
        stateList.add("ne");stateList.add("nv");
        stateList.add("nh");stateList.add("nj");
        stateList.add("nm");stateList.add("ny");
        stateList.add("nc");stateList.add("nd");
        stateList.add("oh");stateList.add("ok");
        stateList.add("or");stateList.add("pa");
        stateList.add("ri");stateList.add("sc");
        stateList.add("sd");stateList.add("tn");
        stateList.add("tx");stateList.add("ut");
        stateList.add("vt");stateList.add("va");
        stateList.add("wa");stateList.add("wv");
        stateList.add("wi");stateList.add("wy");

        for(String state: stateList){
            try {
                parseEventsToSQL(getEventsByVenueState(state));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //performs api call to seat geek by performer and returns json string
    private String getEventsByPerformer(String performer) throws Exception {
        performer = performer.toLowerCase();
        performer = performer.replaceAll(" ", "-");
        String request = "https://api.seatgeek.com/2/events?performers.slug="+performer+"&client_id=NDg5NDAyNXwxNTM3MzIwOTcyLjky&client_secret=d8e97b490925b37c912cac3ab1cc04a696d171025d3421491fe28e9fe6db35fa&format=json&per_page=2500";
        StringBuilder result = new StringBuilder();
        URL url = new URL(request);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        parseEventsToSQL(result.toString());
        return result.toString();
    }

    //performs api call to seat geek using keyword and returns json string
    private String getEventsByKeyword(String keyword) throws Exception {
        keyword = keyword.toLowerCase();
        keyword = keyword.replaceAll(" ", "-");
        String request = "https://api.seatgeek.com/2/events?q="+keyword+"&client_id=NDg5NDAyNXwxNTM3MzIwOTcyLjky&client_secret=d8e97b490925b37c912cac3ab1cc04a696d171025d3421491fe28e9fe6db35fa&format=json&per_page=5000";
        StringBuilder result = new StringBuilder();
        URL url = new URL(request);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    //performs api call to seat geek by state and returns json string
    private String getEventsByVenueState(String stateAbbrev) throws Exception {
        stateAbbrev = stateAbbrev.toUpperCase();
        String request = "https://api.seatgeek.com/2/events?venue.state="+stateAbbrev+"&client_id=NDg5NDAyNXwxNTM3MzIwOTcyLjky&client_secret=d8e97b490925b37c912cac3ab1cc04a696d171025d3421491fe28e9fe6db35fa&format=json&per_page=5000";
        StringBuilder result = new StringBuilder();
        URL url = new URL(request);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    //performs api call to seat geek by eventID and returns json string
    private String getEvent(String eventId) throws Exception {
        String request = "https://api.seatgeek.com/2/events/"+eventId+"?client_id=NDg5NDAyNXwxNTM3MzIwOTcyLjky&client_secret=d8e97b490925b37c912cac3ab1cc04a696d171025d3421491fe28e9fe6db35fa&format=json";
        StringBuilder result = new StringBuilder();
        URL url = new URL(request);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    //takes json string as input and parses data to SQL database using SQLWriter
    private void parseEventsToSQL(String json){
        JSONObject data = new JSONObject(json);
        JSONArray events = data.getJSONArray("events");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        for(int i=0; i<events.length(); i++){
            Sale sale =  new Sale();
            Location location = new Location();
            Performer performer = new Performer();
            EventData eventData = new EventData();
            JSONObject event = events.getJSONObject(i);

            sale.setDateFound(formatter.format(date));
            String startDate = event.getString("datetime_local");
            DateTimeFormatter fmat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDate = LocalDateTime.parse(startDate.replaceAll("T", " "), fmat);
            sale.setPerfDate(localDate.format(fmat));
            sale.setPerformer(event.getJSONArray("performers").getJSONObject(0).getString("name"));
            sale.setLocation(event.getJSONObject("venue").getString("name"));
            sale.setRating(String.valueOf(event.getDouble("score")));

            location.setVenue(event.getJSONObject("venue").getString("name"));
            location.setCity(event.getJSONObject("venue").getString("city"));
            location.setState(event.getJSONObject("venue").getString("state"));
            location.setRating(String.valueOf(event.getJSONObject("venue").getDouble("score")));
            location.setDateFound(formatter.format(date));

            performer.setName(event.getJSONArray("performers").getJSONObject(0).getString("name"));
            performer.setRating(String.valueOf(event.getJSONArray("performers").getJSONObject(0).getDouble("score")));
            performer.setGenre(event.getJSONArray("performers").getJSONObject(0).getString("type"));
            performer.setDateFound(formatter.format(date));

            eventData.setEventName(event.getString("title"));
            eventData.setVenue(event.getJSONObject("venue").getString("name"));
            String performers = "";

            for(int k=0; k<event.getJSONArray("performers").length()-1; k++){
                performers += event.getJSONArray("performers").getJSONObject(k).getString("name")+", ";
            }

            performers += event.getJSONArray("performers").getJSONObject(event.getJSONArray("performers").length()-1).getString("name");
            eventData.setPerformers(performers);
            startDate = event.getString("datetime_local");
            fmat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            localDate = LocalDateTime.parse(startDate.replaceAll("T", " "), fmat);
            eventData.setStartDate(localDate.format(fmat));
            eventData.setEventType(event.getString("type"));
            eventData.setScore(event.getDouble("score"));
            eventData.setPopularity(event.getDouble("popularity"));
            eventData.setDateFound(formatter.format(date));

            if(event.has("stats")) {
                if ((event.getJSONObject("stats").has("average_price") && (!event.getJSONObject("stats").isNull("average_price")))) {
                    eventData.setAveragePrice(event.getJSONObject("stats").getDouble("average_price"));
                }
                if ((event.getJSONObject("stats").has("median_price") && (!event.getJSONObject("stats").isNull("median_price")))) {
                    eventData.setMedianPrice(event.getJSONObject("stats").getDouble("median_price"));
                }
                if ((event.getJSONObject("stats").has("lowest_price") && (!event.getJSONObject("stats").isNull("lowest_price")))) {
                    eventData.setMinPrice(event.getJSONObject("stats").getDouble("lowest_price"));
                }
                if ((event.getJSONObject("stats").has("highest_price") && (!event.getJSONObject("stats").isNull("highest_price")))) {
                    eventData.setMaxPrice(event.getJSONObject("stats").getDouble("highest_price"));
                }
                if ((event.getJSONObject("stats").has("listing_count") && (!event.getJSONObject("stats").isNull("listing_count")))) {
                    eventData.setListingCount(event.getJSONObject("stats").getInt("listing_count"));
                }
            }
            writer.insert(sale);
            writer.insert(location);
            writer.insert(performer);
            writer.insert(eventData);
        }
    }

    public static void main(String[] args) throws Exception
    {
        //currently only using runAllStates and not the more precise api calls because SeatGeek allows very large
        //queries, I am still keeping the non used methods because they could be useful in the future
        GeekScan gs = new GeekScan();
        gs.runALLStates();
    }
}
