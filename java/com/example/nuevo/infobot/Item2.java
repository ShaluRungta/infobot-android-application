package com.example.nuevo.infobot;

/**
 * Created by shyam on 7/20/2016.
 */
public class Item2 {
    public String date,time,venue,subject,year;
    public Item2(){

    }
    public Item2(String subject,String year,String date,String time,String venue){
        this.subject=subject;
        this.year=year;
        this.date=date;
        this.time=time;
        this.venue=venue;
    }
    public String getSubject(){
        return subject;
    }
    public void setSubject(String subject){
        this.subject= subject;
    }
    public String getYear(){
        return year;
    }
    public void setYear(String year){
        this.year=year;
    }
    public  String getDate(){

        return date;
    }
    public void setDate(String date)
    {
        this.date=date;
    }
    public  String getTime()
    {
        return time;
    }
    public void setTime(String time){

        this.time=time;
    }
    public  String getVenue(){

        return venue;
    }
    public void setVenue(String venue){

        this.venue=venue;
    }
}

