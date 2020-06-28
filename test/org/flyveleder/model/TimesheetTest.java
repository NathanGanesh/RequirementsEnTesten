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

//    private User boss_manger;
    private Manager boss_manger;
    private Timesheet timesheet;
    private Project project;
    private Customer customer;
//    private User

    private User trafficController;
    private TrafficController trafficController1;
    private TrafficController trafficController2;
    private TimesheetItem timesheetItem1;
    private TimesheetItem timesheetItem2;
    private TimesheetItem timesheetItem3;
    private HashMap<String, TimesheetItem> timesheetItemHashMap;

    @BeforeAll
    public void startUp() throws FlyvelederModelException {
//        boss_manger = new User("bossman", User.MANAGER, null, null);
        timesheet = new Timesheet();
        boss_manger = new Manager("coronaboss");
        customer = new Customer("customer");

//        customer = new User("customer", User.CUSTOMER, boss_manger, null);
        project = new Project("Test", customer, timesheet);
        trafficController2 = new TrafficController("trafficcontroller",boss_manger, timesheet);
        timesheetItem1 = new TimesheetItem(trafficController2, project, LocalTime.of(11, 0), LocalTime.of(17, 0), LocalDate.now());
        timesheet.addItem(timesheetItem1);
//        trafficController = new User("trafficcontroller", User.TRAFFICCONTROLLER, boss_manger, timesheet);
        trafficController1 = new TrafficController("trafficcontroller",boss_manger, timesheet);
        timesheetItem2 = new TimesheetItem(trafficController1, project, LocalTime.of(14, 0), LocalTime.of(15, 0), LocalDate.now());
        timesheetItem3 = new TimesheetItem(trafficController1, project, LocalTime.of(11, 30), LocalTime.of(17, 30), LocalDate.now());
    }

    @Test
    public void goodTimeSheetTest() throws FlyvelederModelException {

        assertEquals(timesheet.getItems().size(), 1);


        assertEquals(timesheet.getTimeSheetItem("1"), timesheetItem1);
        //timesheet item
        timesheet.removeItem(timesheetItem1);
        assertEquals(timesheet.getItems().size(), 0);

        timesheet.addItem(timesheetItem1);
        assertEquals(timesheet.getItems().size(), 1);
        //timesheet item id
        timesheet.removeItem(timesheetItem1.getId());
        assertEquals(timesheet.getItems().size(), 0);

        timesheet.addItem(timesheetItem1);
        assertEquals(timesheet.getTimeSheetForProject("1").size(), 1);

        assertEquals(timesheet.getTimeSheetForProject("1").get("1"), timesheetItem1);
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

        assertThrows(AssertionError.class, () ->{
                        timesheet.addItem(timesheetItem2);
                        },
                "cant add double values");

        assertThrows(AssertionError.class, () ->{
                        timesheet.addItem(timesheetItem2);},
                "Value already exist");

        assertThrows(AssertionError.class, () -> {timesheet.removeItem(timesheetItem3);
        },
                "cant remove item that hasnt been added");

        assertThrows(AssertionError.class, () ->{
                        timesheet.removeItem(timesheetItem3.getId());
                        },
                "cant remove item that hasnt been added");

        assertThrows(AssertionError.class, () ->{
                        timesheet.removeItem("");
                        },
                "cant remove item that is empty string");

        assertThrows(AssertionError.class, () ->{
                        timesheet.removeItem("");
                        },
                "cant remove item that is empty value");

        assertThrows(AssertionError.class, () -> {
            timesheet.getTimeSheetItem("ok id");
            },
        "cant find item with incorrect specified sheet item id");

        assertThrows(AssertionError.class, () -> {
            TimesheetItem timesheetItem = new TimesheetItem(trafficController1, project, LocalTime.of(12, 0), LocalTime.of(14, 0 ), LocalDate.now());
            TimesheetItem timesheetItem2 = new TimesheetItem(trafficController1, project, LocalTime.of(13, 30), LocalTime.of(15, 30 ), LocalDate.now());
                    timesheet.addItem(timesheetItem);
                    timesheet.addItem(timesheetItem2);
                },
                "cant find item with incorrect specified sheet item id");


    }


}