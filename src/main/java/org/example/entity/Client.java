package org.example.entity;

import java.sql.Date;
import java.time.LocalDate;

import static org.example.utils.MyFormat.fillLeft;

public class Client {
    private int id;
    private String name;
    private final String fieldList = "\n          ID  Name";
    public String getFieldList() { return fieldList; }
    public Client(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Client(String name) {
        this.name = name;
    }

    public Client() {
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(fillLeft(Integer.toString(id),10, true));
        sb.append(fillLeft(name,25, false));
        return sb.toString();
//        return "Client{"+"id="+id+", name='"+name+'}';
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
