package SQL;
import DataTypes.EventData;
import DataTypes.Listing;
import DataTypes.Location;
import DataTypes.Performance;
import DataTypes.Performer;
import DataTypes.Sale;

import java.sql.*;
import java.util.ArrayList;

public class SQLReader {
    Connection con = null;
    Statement stmt = null;
    String databasePath = "REMOVED FOR PORTFOLIO";
    String user = "REMOVED FOR PORTFOLIO";
    String password = "REMOVED FOR PORTFOLIO";
    static ArrayList listings;

    public SQLReader() {
        listings = new ArrayList();
        try {
            con = DriverManager.getConnection(databasePath, user, password);
            stmt = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isDuplicate(EventData data){
        boolean isDup=false;
        ResultSet result = null;
        try {
            result = stmt.executeQuery(
                    "SELECT * FROM eventData WHERE eventName=\'"+data.getEventName()+"\' AND venue = \'"+data.getVenue()+"\' AND startDate = \'"+data.getStartDate()+"\' AND score = \'" + data.getScore()+"\' AND popularity = \'"+data.getPopularity()+"\'");
            while (result.next()) {
                isDup = true;
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDup;
    }
    public boolean isDuplicate(Location newLoc){
        boolean isDup=false;
        ResultSet result = null;
        try {
            result = stmt.executeQuery(
                    "SELECT * FROM location WHERE venue=\'"+newLoc.getVenue()+"\' AND city = \'"+newLoc.getCity()+"\' AND state = \'"+newLoc.getState()+"\'");
            while (result.next()) {
                isDup = true;
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDup;
    }

    public boolean isDuplicate(Performer performer){
        boolean isDup=false;
        ResultSet result = null;
        try {
            result = stmt.executeQuery(
                    "SELECT * FROM performer WHERE name=\'"+performer.getName()+"\' AND rating = \'"+performer.getRating()+"\' AND genre = \'"+ performer.getGenre()+"\'");
            //System.out.println("SELECT * FROM performer WHERE performer=\'"+performer.getName()+"\' AND rating = \'"+performer.getRating()+"\'");
            while (result.next()) {
                isDup = true;
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isDup;
    }



    public boolean isDuplicate(Sale newSale){
        boolean isDup=false;
        ResultSet result = null;
        try {
            result = stmt.executeQuery(
                    "SELECT * FROM sale WHERE performer=\'"+newSale.getPerformer()+"\' AND location = \'"+newSale.getLocation()+"\' AND onSale = \'"+newSale.getOnSale()+"\'");
            while (result.next()) {
                isDup = true;
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return isDup;
    }



    public void selectAllListings() {

        ResultSet result;
        result = null;
        try {
            result = stmt.executeQuery(
                    "SELECT * FROM listing");

            while (result.next()) {
                Listing listing = new Listing();
                listing.setPerformer(result.getString("performer"));
                listing.setDate(result.getString("date"));
                listing.setSection((result.getString("section")));
                listing.setRow(result.getString("rowvalue"));
                listing.setPrice(result.getString("price"));
                listing.setQuantity(result.getString("quantity"));
                listing.setDateFound(result.getString("dateFound"));
                listings.add(listing);


            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    public ArrayList<Listing> selectContainsPerformer(String performer) {
        ArrayList<Listing> returnListings = new ArrayList<Listing>();
        Connection con = null;
        Statement stmt = null;
        ResultSet result;
        result = null;
        try {

            String query ="Select * From listings where performer like ?";
            PreparedStatement stmnt;
            stmnt = con.prepareStatement(query);
            stmnt.setString(1, "%"+performer+"%");
            result=stmnt.executeQuery();

            while (result.next()) {
                Listing listing = new Listing();
                listing.setPerformer(result.getString("performer"));
                listing.setDate(result.getString("date"));
                listing.setSection((result.getString("section")));
                listing.setRow(result.getString("rowvalue"));
                listing.setPrice(result.getString("price"));
                listing.setQuantity(result.getString("quantity"));
                listing.setDateFound(result.getString("dateFound"));
                returnListings.add(listing);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return returnListings;
    }

    public ArrayList<Listing> selectPerformer(String performer) {
        ArrayList<Listing> returnListings = new ArrayList<Listing>();
        ResultSet result;
        result = null;
        try {
            String query ="Select * From listings where performer= ?";
            PreparedStatement stmnt;
            stmnt = con.prepareStatement(query);
            stmnt.setString(1, "THERESA CAPUTO");
            result=stmnt.executeQuery();

            while (result.next()) {
                Listing listing = new Listing();
                listing.setPerformer(result.getString("performer"));
                listing.setDate(result.getString("date"));
                listing.setSection((result.getString("section")));
                listing.setRow(result.getString("rowvalue"));
                listing.setPrice(result.getString("price"));
                listing.setQuantity(result.getString("quantity"));
                listing.setDateFound(result.getString("dateFound"));
                returnListings.add(listing);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return returnListings;
    }

    public ArrayList<Location> selectAllLocations(){
        ArrayList<Location> allLocations=new ArrayList<>();
        ResultSet result;
        result = null;
        try {
            result = stmt.executeQuery(
                    "SELECT * FROM locations");

            while (result.next()) {
                Location loc = new Location();
                loc.setVenue(result.getString("venue"));
                loc.setCity(result.getString("city"));
                loc.setState((result.getString("state")));
                loc.setRating(result.getString("rating"));
                loc.setDateFound(result.getString(("dateFound")));
                allLocations.add(loc);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return allLocations;
    }

    public ArrayList<Performance> selectAllPerformances(){
        ArrayList<Performance> allPerformaces=new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet result;
        result = null;
        try {

            result = stmt.executeQuery(
                    "SELECT * FROM performances");

            while (result.next()) {
                Performance perf = new Performance();
                perf.setVenue(result.getString("venue"));
                perf.setName(result.getString("name"));
                perf.setDate((result.getString("dateName")));
                perf.setLink(result.getString("link"));
                allPerformaces.add(perf);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return allPerformaces;
    }
    public Performance selectPerformances(){
        Performance result=new Performance();
        //not finished
        return result;
    }

    public ArrayList<Performer> selectAllPerformers(){
        ArrayList<Performer> allPerformers=new ArrayList<>();
        ResultSet result;
        result = null;
        try {
            result = stmt.executeQuery(
                    "SELECT * FROM performer");
            while (result.next()) {
                Performer perf = new Performer();
                perf.setName(result.getString("name"));
                perf.setGenre(result.getString("genre"));
                perf.setRating(result.getString("rating"));
                perf.setDateFound(result.getString(("dateFound")));
                allPerformers.add(perf);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return allPerformers;
    }

    public ArrayList<Sale> selectAllSales(){
        ArrayList<Sale> allSales=new ArrayList<>();
        ResultSet result;
        result = null;
        try {
            result = stmt.executeQuery(
                    "SELECT * FROM sale");
            while (result.next()) {
                Sale sale = new Sale();
                sale.setPerformer(result.getString("performer"));
                sale.setLocation(result.getString("location"));
                sale.setPerfDate((result.getString("perfDate")));
                sale.setRowsDesired(result.getString("rowsDesired"));
                sale.setFloor(result.getString("floor"));
                sale.setLower(result.getString("lower"));
                sale.setPassword(result.getString("password"));
                sale.setDateFound(result.getString("dateFound"));
                sale.setOnSale(result.getString("onSale"));
                sale.setRating(result.getString("rating"));
                sale.setNumRating(result.getString("numRating"));
                sale.setPerformance(result.getString("performance"));
                allSales.add(sale);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return allSales;
    }

    public ArrayList<Sale> selectSales(String suffix){
        ArrayList<Sale> sales=new ArrayList<Sale>();
        ResultSet result;
        result = null;
        try {
            result = stmt.executeQuery(
                    "SELECT * FROM sale"+" "+suffix);

            while (result.next()) {
                Sale sale = new Sale();
                sale.setPerformer(result.getString("performer"));
                sale.setLocation(result.getString("location"));
                sale.setPerfDate((result.getString("perfDate")));
                sale.setRowsDesired(result.getString("rowsDesired"));
                sale.setFloor(result.getString("floor"));
                sale.setLower(result.getString("lower"));
                sale.setPassword(result.getString("password"));
                sale.setDateFound(result.getString("dateFound"));
                sale.setOnSale(result.getString("onSale"));
                sale.setRating(result.getString("rating"));
                sale.setNumRating(result.getString("numRating"));
                sale.setPerformance(result.getString("performance"));
                sales.add(sale);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return sales;
    }
}
