package org.flyveleder.model;

import org.flyveleder.exceptions.FlyvelederModelException;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by abe23 on 08/11/17.
 */
public class TimesheetItem {

    //Keep track of the next ID
    private static int maxId = 0;
    //Projects cannot change...
    private final Project project;
    private String id;
    private User trafficController;
    private LocalTime start, end;
    private LocalDate date;
    private boolean validated;


    /*
        Timesheet items are planned by planners
        When planning an item the date cannot be in the past!
     */
    public TimesheetItem(TrafficController trafficController, Project project, LocalTime start, LocalTime end, LocalDate date) throws FlyvelederModelException {
        if (date.isBefore(LocalDate.now())) {
            throw new FlyvelederModelException("You cannot plan items in the past");
        }
        assert trafficController != null: new FlyvelederModelException("trafficcontroller is null");
        this.trafficController = trafficController;
        assert !end.isBefore(start): new FlyvelederModelException("end is before start");
        this.start = start;
        this.end = end;
        this.date = date;
        assert project!=null: new FlyvelederModelException("project is null");
        this.project = project;
        this.id = Integer.valueOf(++maxId).toString();
    }


    public User getTrafficController() {
        return trafficController;
    }

    public void setTrafficController(User trafficController) {
        this.trafficController = trafficController;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public LocalDate getDate() {
        return date;
    }

    /*
        When planning only dates in the future can be used
     */
    public void setDate(LocalDate date) throws  FlyvelederModelException{
        if (date.isBefore(LocalDate.now())) {
            throw new FlyvelederModelException("You cannot plan items in the past");
        }
        this.date = date;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public Project getProject() {
        return project;
    }


    public String getId() {
        return id;
    }
}