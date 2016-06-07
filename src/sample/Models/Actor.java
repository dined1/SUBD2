package sample.Models;

/**
 * Created by Adm on 29.03.2016.
 */
public class Actor{
    private int Actorid;
    private String FIO;
    private int Age;
    private int Exper;
    private int Contractcost;
    private String Rang;
    private String Genre;
    private String Adress;
    private int Userid;
    private String Description;

    public Actor(int Actorid, String FIO, int Age, int Exper, int Contractcost, String Rang, String Genre, String Adress, int Userid, String Description){
        this.Actorid=Actorid;
        this.FIO=FIO;
        this.Age=Age;
        this.Exper=Exper;
        this.Contractcost=Contractcost;
        this.Rang=Rang;
        this.Genre=Genre;
        this.Adress=Adress;
        this.Userid=Userid;
        this.Description=Description;
    }

    public int getActorid() {
        return Actorid;
    }

    public void setActorid(int actorid) {
        Actorid = actorid;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getExper() {
        return Exper;
    }

    public void setExper(int exper) {
        Exper = exper;
    }

    public int getContractcost() {
        return Contractcost;
    }

    public void setContractcost(int contractcost) {
        Contractcost = contractcost;
    }

    public String getRang() {
        return Rang;
    }

    public void setRang(String rang) {
        Rang = rang;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String  getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public int getUserid() {
        return Userid;
    }

    public void setUserid(int userid) {
        Userid = userid;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}