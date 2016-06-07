package sample.Models;

/**
 * Created by Adm on 29.03.2016.
 */
public class Adress{
    private int Adressid;
    private String Country;
    private String City;
    private String Street;
    private int House;
    private int Flat;

    public Adress(int Adressid, String Country, String City, String Street, int House, int Flat){
        this.Adressid=Adressid;
        this.Country=Country;
        this.City=City;
        this.Street=Street;
        this.House=House;
        this.Flat=Flat;
    }

    public int getAdressid() {
        return Adressid;
    }

    public void setAdressid(int adressid) {
        Adressid = adressid;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public int getHouse() {
        return House;
    }

    public void setHouse(int house) {
        House = house;
    }

    public int getFlat() {
        return Flat;
    }

    public void setFlat(int flat) {
        Flat = flat;
    }
}
