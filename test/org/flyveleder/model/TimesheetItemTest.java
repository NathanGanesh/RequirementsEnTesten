package org.flyveleder.model;

import org.flyveleder.exceptions.FlyvelederModelException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TimesheetItemTest {
    private Timesheet timesheet;
    private Customer customer;
    private Project project;
    private Manager boss;
    private TimesheetItem timesheetItem;
    private TimesheetItem timesheetItem3;
    private TrafficController trafficController;
    private TrafficController trafficController2;

    @BeforeEach
    public void startUp() throws FlyvelederModelException {
        timesheet = new Timesheet();
        customer = new Customer("coronaboss");
        boss = new Manager("coronaboss");
        project = new Project("Test", customer, timesheet);
        trafficController = new TrafficController("a name", boss, timesheet);
        trafficController2 = new TrafficController("trafficcontroller", boss, timesheet);
        timesheetItem = new TimesheetItem(trafficController, project, LocalTime.of(11, 30), LocalTime.of(19, 30), LocalDate.now());
        timesheetItem3 = new TimesheetItem(trafficController2, project, LocalTime.of(11, 30), LocalTime.of(17, 30), LocalDate.now());
    }

    @Test
    public void testTimeSheet() throws FlyvelederModelException {
//        assertNotNull(trafficController);
//        assertNull(timesheetItem);
        timesheet.addItem(timesheetItem);
        assertEquals(timesheet.getItems().size(), 1);
        assertEquals(timesheetItem.getTrafficController(), trafficController);

        timesheetItem.setTrafficController(trafficController2);
        assertEquals(timesheetItem.getTrafficController(), trafficController2);

        assertEquals(timesheetItem.getStart(), LocalTime.of(11, 30));
        assertEquals(timesheetItem.getEnd(), LocalTime.of(19, 30));
        timesheetItem.setStart(LocalTime.of(13, 30));
        assertEquals(timesheetItem.getStart(), LocalTime.of(13, 30));
        timesheetItem.setEnd(LocalTime.of(15, 30));
        assertEquals(timesheetItem.getEnd(), LocalTime.of(15, 30));

        assertEquals(timesheetItem.getDate(), LocalDate.now());

        timesheetItem.setDate(LocalDate.of(2020, 12, 12));
        assertEquals(timesheetItem.getDate(),LocalDate.of(2020, 12, 12));

        assertFalse(timesheetItem.isValidated());

        timesheetItem.setValidated(true);

        assertTrue(timesheetItem.isValidated());

        assertSame(timesheetItem.getProject(), project);
        assertEquals(timesheetItem.getId(), "1");


//        assertEquals(timesheetItem.getProject().sendMail(), timesheetItem.getTrafficController());
    }

    @Test
    public void badTimeSheet() {
        assertThrows(AssertionError.class, () -> {
            TimesheetItem timesheetItemTest = new TimesheetItem(trafficController, project, LocalTime.of(11, 30), LocalTime.of(10, 30), LocalDate.now());
        }, "cant add end time before start time");

        assertThrows(AssertionError.class, () -> {
            TimesheetItem timesheetItemTest = new TimesheetItem(trafficController, project, null, LocalTime.of(10, 30), LocalDate.now());
        }, "null start time");

        assertThrows(AssertionError.class, () -> {
            TimesheetItem timesheetItemTest = new TimesheetItem(trafficController, project, LocalTime.of(11, 30), null, LocalDate.now());
        }, "null end time");

        assertThrows(AssertionError.class, () -> {
            TimesheetItem timesheetItemTest = new TimesheetItem(null, project, LocalTime.of(11, 30), LocalTime.of(10, 30), LocalDate.now());
        }, "null trafficController");

        assertThrows(AssertionError.class, () -> {
            TimesheetItem timesheetItemTest = new TimesheetItem(trafficController, null, LocalTime.of(11, 30), LocalTime.of(10, 30), LocalDate.now());
        }, "project is null");

//        assertThrows(AssertionError.class, () ->{
//            TimesheetItem timesheetItemTest = new TimesheetItem(trafficController, null, LocalTime.of(11,30), LocalTime.of(10, 30), LocalDate.now());
//        },"project is null");

    }


}