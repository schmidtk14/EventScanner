package DataTypes;

/**
 * Created by ks114 on 10/24/2017.
 */
public class Sale {
    private String performer;
    private String location;
    private String perfDate;
    private String saleDate;
    private String link;
    private String rowsDesired;
    private String floor;
    private String lower;
    private String password;
    private String dateFound;
    private String onSale;
    private String rating;
    private String numRating;
    private String performance;

    public Sale(){
        this.performer="";
        this.location="";
        this.perfDate="";
        this.saleDate="";
        this.link="";
        this.rowsDesired="";
        this.floor="";
        this.lower="";
        this.password="";
        this.dateFound="";
        this.onSale="";
        this.rating="";
        this.numRating="";
        this.performance="";
    }

    public String getPerformer(){
        return this.performer;
    }
    public String getLocation(){
        return this.location;
    }
    public String getPerfDate(){
        return this.perfDate;
    }
    public String getSaleDate(){
        return this.saleDate;
    }
    public String getLink(){
        return this.link;
    }
    public String getRowsDesired(){
        return this.rowsDesired;
    }
    public String getFloor(){
        return this.floor;
    }
    public String getLower(){
        return this.lower;
    }
    public String getPassword(){
        return this.password;
    }
    public String getDateFound(){
        return this.dateFound;
    }
    public String getOnSale(){return this.onSale;}
    public String getRating(){return this.rating;}
    public String getNumRating(){return this.numRating;}
    public String getPerformance(){return this.performance;}

    public void setPerformer(String s){
        this.performer=s;
    }

    public void setLocation(String s){
        this.location=s;
    }

    public void setPerfDate(String s){
        this.perfDate=s;
    }

    public void setSaleDate(String s){
        this.saleDate=s;
    }

    public void setLink(String s){
        this.link=s;
    }

    public void setRowsDesired(String s){
        this.rowsDesired=s;
    }

    public void setFloor(String s){
        this.floor=s;
    }

    public void setLower(String s){
        this.lower=s;
    }

    public void setPassword(String s){
        this.password=s;
    }

    public void setDateFound(String s){
        this.dateFound=s;
    }

    public void setOnSale(String s){
        this.onSale=s;}

    public void setRating(String s){
        this.rating=s;}

    public void setNumRating(String s){
        this.numRating=s;}

    public void setPerformance(String s){
        this.performance=s;}

    public void printToConsole(){
        System.out.println("DataTypes.Performer: " + this.performer);
        System.out.println("DataTypes.Location: " + this.location);
        System.out.println("PerfDate: " + this.perfDate);
        System.out.println("SaleDate: " + this.saleDate);
        System.out.println("Link: " + this.link);
        System.out.println("RowsDesired: " + this.rowsDesired);
        System.out.println("Floor: " + this.floor);
        System.out.println("Lower: " + this.lower);
        System.out.println("Password: " + this.password);
        System.out.println("dateFound: " + this.dateFound);
        System.out.println("Rating: " + this.rating);
        System.out.println("NumRating: " + this.numRating);
        System.out.println("DataTypes.Performance: " + this.performance);
        System.out.println("OnSale: "+this.onSale);

    }




}
