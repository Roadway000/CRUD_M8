package org.example.entity;

import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.example.utils.MyFormat.fillLeft;

@Data
public class Worker {
    private int id;
    private String name;
    private LocalDate birthday;
    private String level;
    private int salary;
    private final String fieldList = "\n          ID  Name                       Birthday    Level      Salary";
    public String getFieldList() { return fieldList; }

    public Worker(int id, String name, LocalDate birthday, String level, int salary) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.level = level;
        this.salary = salary;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(fillLeft(Integer.toString(id),10, true));
        sb.append(fillLeft(name,25, false));
        sb.append(fillLeft(birthday.toString(), 10, true));
        sb.append(fillLeft(level,8, false));
        sb.append(fillLeft(Integer.toString(salary),5, true));
        return sb.toString();
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public LocalDate getBirthday() { return birthday; }
    public String getLevel() { return level; }
    public int getSalary() { return salary; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }
    public void setLevel(String level) { this.level = level; }
    public void setSalary(int salary) { this.salary = salary; }

}
