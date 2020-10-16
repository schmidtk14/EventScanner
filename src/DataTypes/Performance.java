package DataTypes;

/**
 * Created by ks114 on 10/24/2017.
 */
public class Performance {
    private String name;
    private String venue;
    private String date;
    private String link;

    public Performance(){
        this.name="";
        this.venue="";
        this.date="";
        this.link="";
    }

    public String getName(){
        return this.name;
    }
    public String getVenue(){
        return this.venue;
    }
    public String getDate(){
        return this.date;
    }
    public String getLink(){
        return this.link;
    }

    public void setName(String s){
        this.name=s;
    }
    public void setVenue(String s){
        this.venue=s;
    }
    public void setDate(String s){
        this.date=s;
    }
    public void setLink(String s){
        this.link=s;
    }

    public void printToConsole(){
        System.out.println("Name: " + this.name);
        System.out.println("Venue: " + this.venue);
        System.out.println("Date: " + this.date);
        System.out.println("Link: " + this.link);
    }


}
