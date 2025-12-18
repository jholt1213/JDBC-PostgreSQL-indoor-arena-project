public class Arena {
    /**
     * this part creates the fields needed to read the text file
     */
       private String venueName;
       private String city;
       private String state;
       private int maxCapacity;
       private int yearOpened;
       private Tenant tenant;

    public Arena(){
    }//no args constructor
    public Arena(String v, String c, String s, int m, int y, Tenant tenant)// constructor with arguments
    {
        venueName = v;
        city = c;
        state = s;
        maxCapacity = m;
        yearOpened = y;
        this.tenant = tenant;
    }
    //setters
    public void setVenueName(String v){
        venueName = v;
    }
    public void setCity(String c){
        city = c;
    }

    public void setState(String s) {
        state = s;
    }

    public void setMaxCapacity(int m){
        maxCapacity = m;
    }
    public void setYearOpened(int y){
        yearOpened = y;
    }
    public void setTenant(Tenant t){
        tenant = t;
    }
    //getters
    public String getVenueName(){
        return venueName;
    }
    public String getCity(){
        return city;
    }
    public String getState(){
        return state;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getYearOpened() {
        return yearOpened;
    }

    public Tenant getTenant() {
        return tenant;
    }
    //toString method
    public String toString(){
        return "Venue:\t" + venueName
                + "\nLocation:\t" + city +
                "\nCapacity:\t" + maxCapacity +
                "\nYear Opened:\t" + yearOpened + tenant;
    }
}
