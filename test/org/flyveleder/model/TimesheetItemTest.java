package org.flyveleder.model;

import org.flyveleder.exceptions.FlyvelederModelException;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TimesheetItemTest {
    private Timesheet timesheet;
    private Customer customer;
    private Project project;
    private Manager boss;
    private TimesheetItem timesheetItem;
    private TrafficController trafficController;

    @BeforeEach
    public void startUp() throws FlyvelederModelException {
        timesheet = new Timesheet();
        customer = new Customer("coronaboss");
        project = new Project("Test", customer, timesheet);
        TrafficController user = new TrafficController("a name", boss, timesheet);
        timesheetItem = new TimesheetItem(trafficController, project, LocalTime.of(11, 30), LocalTime.of(19, 30), LocalDate.now());

    }