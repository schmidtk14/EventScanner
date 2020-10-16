package DataTypes;

public class Listing {
    private String performer;
    private String date;
    private String section;
    private String row;
    private String price;
    private String quantity;
    private String dateFound;

    public Listing(){
        this.performer="";
        this.date="";
        this.section="";
        this.row="";
        this.price="";
        this.quantity="";
        this.dateFound="";
    }

    public void setPerformer(String s){
        this.performer=s;
    }
    public void setDate(String s){
        this.date=s;
    }
    public void setSection(String s){
        this.section=s;
    }
    public void setRow(String s){
        this.row=s;
    }
    public void setPrice(String s){
        this.price=s;
    }
    public void setQuantity(String s){
        this.quantity=s;
    }
    public void setDateFound(String s){
        this.dateFound=s;
    }

    public String getPerformer(){
        return this.performer;
    }
    public String getDate(){
        return this.date;
    }
    public String getSection(){
        return this.section;
    }
    public String getRow(){
        return this.row;
    }
    public String getPrice(){
        return this.price;
    }
    public String getQuantity(){
        return this.quantity;
    }
    public String getDateFound(){
        return this.dateFound;
    }

    public void printToConsole(){
        System.out.println("DataTypes.Performer: "+this.performer);
        System.out.println("Date: "+this.date);
        System.out.println("Section: "+this.section);
        System.out.println("Row: "+this.row);
        System.out.println("Price: "+this.price);
        System.out.println("Quantity: "+this.quantity);
        System.out.println("DateFound: "+this.dateFound);
    }
}
