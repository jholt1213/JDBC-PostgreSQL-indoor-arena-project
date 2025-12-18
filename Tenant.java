public class Tenant {
    private String teamName;
    private String sport;
    private String league;
    public Tenant()//no args constructor
    {
    }
    public Tenant(String t, String s, String l)//construct accepting arguments
    {
        teamName = t;
        sport = s;
        league = l;
    }
    //setter methods
    public void setTeamName(String t){
        teamName = t;
    }

    public void setSport(String s) {
        sport = s;
    }

    public void setLeague(String l) {
        league = l;
    }
    //getter methods
    public String getTeamName(){
        return teamName;
    }

    public String getSport() {
        return sport;
    }
    public String getLeague() {
        return league;
    }
    //toString method
    public String toString(){
        return "\nTeam Name:\t" + teamName + "\nSport:\t" + sport + "\nLeague:\t" + league;
    }
}
