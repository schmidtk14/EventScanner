package SQL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import DataTypes.EventData;
import DataTypes.Listing;
import DataTypes.Location;
import DataTypes.Performance;
import DataTypes.Performer;
import DataTypes.Sale;


public class SQLWriter {
    SQLReader reader;
    Connection con;
    Statement stmt;
    String databasePath = "REMOVED FOR PORTFOLIO";
    String user = "REMOVED FOR PORTFOLIO";
    String password = "REMOVED FOR PORTFOLIO";

    public SQLWriter(){
        reader=new SQLReader();
        try {
            con = DriverManager.getConnection(databasePath, user, password);
            stmt = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Sale sale){
        try {
            if(!reader.isDuplicate(sale)){
                String s="INSERT INTO sale VALUES('"+sale.getPerformance()+"', '"+sale.getPerformer()+"', '"+sale.getLocation()+"', '"+sale.getPerfDate()+"', '"+sale.getSaleDate()+"', '"+sale.getLink()+"', '"+sale.getRowsDesired()+"', '"+sale.getFloor()+"', '"+sale.getLower()+"', '"+sale.getPassword()+"', '"+sale.getDateFound()+"', '"+sale.getOnSale()+"', '"+sale.getRating()+"', '"+sale.getNumRating()+"')";
                System.out.println(s);
                stmt.executeUpdate(s);
            }

        }catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void insert(Listing listing){

        try {
            String s="INSERT INTO listing VALUES('"+listing.getPerformer()+"', '"+listing.getDate()+"', '"+listing.getSection()+"', '"+listing.getRow()+"', '"+listing.getPrice()+"', '"+listing.getQuantity()+"', '"+listing.getDateFound()+"')";
            System.out.println(s);
            stmt.executeUpdate(s);

        }catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void insert(Location loc){
        try {
            if(!reader.isDuplicate(loc)){
                String s="INSERT INTO location VALUES('"+loc.getVenue()+"', '"+loc.getCity()+"', '"+loc.getState()+"', '"+loc.getRating()+"', '"+loc.getDateFound()+"')";
                System.out.println(s);
                stmt.executeUpdate(s);
            }
        }catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void insert(Performance perf){
        try {
            String s="INSERT INTO performance VALUES('"+perf.getName()+"', '"+perf.getVenue()+"', '"+perf.getDate()+"', '"+perf.getLink()+"')";
            System.out.println(s);
            stmt.executeUpdate(s);
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void insert(Performer perf){
        try {
            if(!reader.isDuplicate(perf)){
                String s="INSERT INTO performer VALUES('"+perf.getName()+"', '"+perf.getGenre()+"', '"+perf.getRating()+"', '"+perf.getDateFound()+"')";
                System.out.println(s);
                stmt.executeUpdate(s);
            }
        }catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void insert(EventData data){
        try {
            if(!reader.isDuplicate(data)){
                String s="INSERT INTO eventData VALUES('"+data.getEventName()+"', '"+data.getVenue()+"', '"+data.getPerformers()+"', '"+data.getStartDate()+"', '"+data.getEventType()+"', '"+data.getScore()+"', '"+data.getPopularity()+"', '"+data.getAveragePrice()+"', '"+data.getMedianPrice()+"', '"+data.getMinPrice()+"', '"+data.getMaxPrice()+"', '"+data.getListingCount()+"', '"+data.getDateFound()+"')";
                System.out.println(s);
                stmt.executeUpdate(s);
            }
        }catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}

