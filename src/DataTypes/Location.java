package DataTypes;

public class Location {
    private String venue;
    private String city;
    private String state;
    private String rating;
    private String dateFound;

    public Location(){
        this.venue="";
        this.city="";
        this.state="";
        this.rating="";
        this.dateFound="";
    }

    public String getVenue(){
        return this.venue;
    }
    public String getCity(){
        return this.city;
    }
    public String getState(){
        return this.state;
    }
    public String getRating(){
        return this.rating;
    }
    public String getDateFound(){return this.dateFound;}

    public void setVenue(String s){this.venue=s;}
    public void setCity(String s){this.city=s;}
    public void setState(String s){
        this.state=s;
    }
    public void setRating(String s){this.rating=s;}
    public void setDateFound(String s){this.dateFound=s;}
    public void printToConsole(){
        System.out.println("Venue: " + this.venue);
        System.out.println("City: " + this.city);
        System.out.println("State: " + this.state);
        System.out.println("Rating: " + this.rating);
        System.out.println("DateFound: " + this.dateFound);
    }



}
