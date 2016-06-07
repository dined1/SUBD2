package sample.Models;

/**
 * Created by Adm on 29.03.2016.
 */
public class User{
    private int Userid;
    private String Login;
    private String Password;
    private String Theatreid;
    private String Role;

    public User(int Userid, String Login, String Password, String Theatreid, String Role){
        this.Userid=Userid;
        this.Login=Login;
        this.Password=Password;
        this.Theatreid=Theatreid;
        this.Role=Role;
    }

    public int getUserid() {
        return Userid;
    }

    public void setUserid(int userid) {
        Userid = userid;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getTheatreid() {
        return Theatreid;
    }

    public void setTheatreid(String theatreid) {
        Theatreid = theatreid;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}