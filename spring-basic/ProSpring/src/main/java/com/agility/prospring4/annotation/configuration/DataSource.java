package com.agility.prospring4.annotation.configuration;

public class DataSource {
    String url;
    String user;

    public DataSource() {
    }

    public void setUrl() {
        this.url = "localhost:8000";
    }

    public void setUser() {
        this.user = "David";
    }

    @Override
    public String toString() {
        return this.url + "," + this.user;
    }

    public void print() {
        System.out.println(this.url + "," + this.user);
    }


}
