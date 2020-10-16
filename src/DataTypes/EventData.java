package DataTypes;

public class EventData {
    private String eventName;
    private String venue;
    private String performers;
    private String startDate;
    private String eventType;
    private double averagePrice;
    private double medianPrice;
    private double minPrice;
    private double maxPrice;
    private int listingCount;
    private double score;
    private double popularity;
    private String dateFound;

    public EventData() {
        this.eventName = "";
        this.venue = "";
        this.performers = "";
        this.startDate = "";
        this.eventType = "";
        this.averagePrice = .0;
        this.medianPrice = .0;
        this.minPrice = .0;
        this.maxPrice = .0;
        this.listingCount = 0;
        this.score = 0.0;
        this.popularity = 0.0;
    }

    public void setEventName(String s) {
        this.eventName = s;
    }

    public void setVenue(String s) {
        this.venue = s;
    }

    public void setPerformers(String s) {
        this.performers = s;
    }

    public void setStartDate(String s) {
        this.startDate = s;
    }
    public void setEventType(String s) {
        this.eventType = s;
    }

    public void setScore(double d){
        this.score = d;
    }

    public void setPopularity(double d){
        this.popularity = d;
    }

    public void setAveragePrice(double d){
        this.averagePrice = d;
    }

    public void setMedianPrice(double d){
        this.medianPrice = d;
    }

    public void setMinPrice(double d){
        this.minPrice = d;
    }

    public void setMaxPrice(double d){
        this.maxPrice = d;
    }

    public void setListingCount(int i){
        this.listingCount = i;
    }

    public void setDateFound(String s){
        this.dateFound = s;
    }

    public String getEventName(){
        return this.eventName;
    }

    public String getVenue(){
        return this.venue;
    }

    public String getPerformers(){
        return this.performers;
    }

    public String getStartDate(){
        return this.startDate;
    }

    public String getEventType(){
        return this.eventType;
    }

    public double getScore(){
        return this.score;
    }

    public double getPopularity(){
        return this.popularity;
    }

    public double getAveragePrice(){
        return this.averagePrice;
    }

    public double getMedianPrice(){
        return this.medianPrice;
    }

    public double getMinPrice(){
        return this.minPrice;
    }

    public double getMaxPrice(){
        return this.maxPrice;
    }

    public int getListingCount(){
        return this.listingCount;
    }

    public String getDateFound(){
        return this.dateFound;
    }

    public void printToConsole(){
        System.out.println("eventName: "+ eventName);
        System.out.println("venue: "+ venue);
        System.out.println("performers: "+ performers);
        System.out.println("startDate: "+ startDate);
        System.out.println(("eventType: "+ eventType));
        System.out.println("score: "+ score);
        System.out.println("popularity: "+ popularity);
        System.out.println("averagePrice: "+ averagePrice);
        System.out.println("medianPrice: "+ medianPrice);
        System.out.println("minPrice: "+ minPrice);
        System.out.println("maxPrice: "+ maxPrice);
        System.out.println("listingCount: "+ listingCount);
        System.out.println("dateFound: " + dateFound);
    }
}

