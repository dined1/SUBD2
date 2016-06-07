package sample.Models;

import java.sql.Date;

/**
 * Created by Adm on 29.03.2016.
 */
public class Perfomance {
    private int id;
    private String Name;
    private String Year;
    private int Budget;
    private Date BeginTime;
    private Date EndTime;
    private String Genre;
    private int Income;

    public Perfomance(int id, String Name, String Year, int Budget, Date BT, Date ET, String Genre, int Income) {
        this.id = id;
        this.Name = new String(Name);
        this.Year = new String(Year);
        this.Budget = Budget;
        this.BeginTime = BT;
        this.EndTime = ET;
        this.Genre = new String(Genre);
        this.Income = Income;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public int getIncome() {
        return Income;
    }

    public void setIncome(int income) {
        Income = income;
    }

    public int getBudget() {
        return Budget;
    }

    public void setBudget(int budget) {
        Budget = budget;
    }
    public void setBeginTime(Date beginTime) {
        BeginTime = beginTime;
    }
    public Date getBeginTime() {
        return BeginTime;
    }
    public void setEndTime(Date endTime) {
        EndTime = endTime;
    }
    public Date getEndTime() {
        return EndTime;
    }
}
