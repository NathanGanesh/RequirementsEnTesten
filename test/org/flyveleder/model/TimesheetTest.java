package org.flyveleder.model;

import org.flyveleder.exceptions.FlyvelederModelException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TimesheetTest {

    private User boss_manger;
    private Timesheet timesheet;
    private Project project;
    private User customer;
//    private User

    private User trafficController;
    private TimesheetItem timesheetItem1;
    private TimesheetItem timesheetItem2;
    private TimesheetItem timesheetItem3;
    private HashMap<String, TimesheetItem> timesheetItemHashMap;

    @BeforeAll
    public void startUp() throws FlyvelederModelException {
        boss_manger = new User("bossman", User.MANAGER, null, null);
        timesheet = new Timesheet();
        timesheetItem1 = new TimesheetItem(boss_manger, project, LocalTime.of(11, 0), LocalTime.of(17, 0), LocalDate.now());
        timesheet.addItem(timesheetItem1);
        customer = new User("customer", User.CUSTOMER, boss_manger, null);
        project = new Project("Test", customer, timesheet);
        trafficController = new User("trafficcontroller", User.TRAFFICCONTROLLER, boss_manger, timesheet);
        timesheetItem2 = new TimesheetItem(trafficController, project, LocalTime.of(14, 0), LocalTime.of(13, 0), LocalDate.now());
        timesheetItem3 = new TimesheetItem(trafficController, project, LocalTime.of(11, 30), LocalTime.of(17, 30), LocalDate.now());
    }

    @Test
    public void goodTimeSheetTest() throws FlyvelederModelException {

        assertEquals(timesheet.getItems().size(), 1);


        assertEquals(timesheet.getTimeSheetItem("3"), timesheetItem1);
        //timesheet item
        timesheet.removeItem(timesheetItem1);
        assertEquals(timesheet.getItems().size(), 0);

        timesheet.addItem(timesheetItem1);
        assertEquals(timesheet.getItems().size(), 1);
        //timesheet item id
        timesheet.removeItem(timesheetItem1.getId());
        assertEquals(timesheet.getItems().size(), 0);

        timesheet.addItem(timesheetItem1);
        Project project = new Project("corona", boss_manger, timesheet);
        assertEquals(timesheet.getTimeSheetForProject("1").size(), 1);

        assertEquals(timesheet.getTimeSheetForProject("1").get("3"), timesheetItem1);
        timesheet.removeItem(timesheetItem1.getId());
        assertEquals(timesheet.getItems().size(), 0);
//        assertEquals(timesheet.getTimeSheetForProject("1").get());
//        System.out.println(timesheet.getTimeSheetForUser(trafficController.getId()).get("1"));
//        TimesheetItem timesheetItem = timesheet.getTimeSheetForUser(trafficController.getId()).get("1");
//        assertEquals(timesheetItem, timesheetItem1);

    }

    /**
     * Bad weather tests for the timesheet class
     */
    @Test
    public void testBadTimeSheet() throws FlyvelederModelException {
        System.out.println(timesheet);
        timesheet.addItem(timesheetItem2);

        assertThrows(AssertionError.class, () ->
                timesheet.addItem(timesheetItem2),
                "cant add double values");

        assertThrows(AssertionError.class, () ->
                timesheet.addItem(timesheetItem2),
                "Value already exist");

        assertThrows(AssertionError.class, () -> timesheet.removeItem(timesheetItem3),
                "cant remove item that hasnt been added");

        assertThrows(AssertionError.class, () ->
                timesheet.removeItem(timesheetItem3.getId()),
                "cant remove item that hasnt been added");

        assertThrows(AssertionError.class, () -> timesheet.removeItem(""),
                "cant remove item that is empty string");

        assertThrows(AssertionError.class, () ->
                        timesheet.removeItem(""),
                "cant remove item that is empty value");

        assertThrows(AssertionError.class, () ->timesheet.getTimeSheetItem("ok id"),
                "cant find item with incorrect specified sheet item id");
    }
}