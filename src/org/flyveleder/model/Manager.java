package org.flyveleder.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Manager extends User {
    public ArrayList< TrafficController > employees;

    public Manager(String name){
        super(name);
        this.employees = new ArrayList<>();
    }

    public ArrayList<User> getEmployees() {
        return new ArrayList<>(employees);
    }

    public void addEmployeeToBoss(TrafficController trafficController){
        assert trafficController != null: "empty trafficcontroller";
        employees.add(trafficController);
    }
    public void removeEmployeeToBoss(TrafficController trafficController){
        assert trafficController != null: "empty trafficcontroller";
        employees.remove(trafficController);
    }

}
