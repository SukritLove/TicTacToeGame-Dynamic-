package com.example.tictactoegame;

public class Time {
    private String collected_time;
    public Time(){
        this.collected_time = null;
    }
    public Time(String collected_time){
        this.collected_time = collected_time;
    }
    public String getCollected_time(){
        return collected_time;
    }
}
