package com.example.nuevo.infobot;

/**
 * Created by shyam on 7/2/2016.
 */
public class Item {
    public String name_e,date,time,venue;
    public Item(){

    }
    public Item(String name_e,String date,String time,String venue){
        this.name_e=name_e;
        this.date=date;
        this.time=time;
        this.venue=venue;
    }

    public  String getName_e(){

        return name_e;
    }
    public void setName_e(String name_e){

        this.name_e=name_e;
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
