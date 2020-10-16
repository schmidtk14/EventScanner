package DataTypes;

/**
 * Created by ks114 on 10/24/2017.
 */
public class Performer {
    private String name;
    private String genre;
    private String rating;
    private String dateFound;

    public Performer(){
        this.name="";
        this.genre="";
        this.rating="";
        this.dateFound="";
    }

    public String getName(){
        return this.name;
    }
    public String getGenre(){
        return this.genre;
    }
    public String getRating(){
        return this.rating;
    }
    public String getDateFound(){return this.dateFound;}

    public void setName(String s){
        this.name=s;
    }

    public void setGenre(String s){
        this.genre=s;
    }
    public void setRating(String s){
        this.rating=s;
    }
    public void setDateFound(String s){this.dateFound=s;}

    public void printToConsole(){
        System.out.println("Name:" + this.name);
        System.out.println("Genre:" + this.genre);
        System.out.println("Rating:" + this.rating);
        System.out.println("Datefound: " + this.dateFound);
    }

}
