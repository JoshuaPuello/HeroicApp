package com.co.ametech.heroicapp.Logic.model;

import java.util.List;

/**
 * Created by Alan M.E
 */

public class Bus {

    private int id;
    private String name;

    public Bus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Bus() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
