package org.flyveleder.model;

import org.flyveleder.exceptions.FlyvelederModelException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserTest {

    //    private User boss;
    private Timesheet timesheet;
    private Project project;
    //    private User customer;
//    private User planner;
    private TimesheetItem timesheetItem;
    private Manager boss;
    private Customer customer;
    private Planner planner;
    private TrafficController trafficController1;

    @BeforeAll
    public void startUp() throws FlyvelederModelException {
//        boss = new User("bossman", User.MANAGER, null, null);
//        customer = new User("customer", User.CUSTOMER, null, null);
//        planner = new User("planner", User.PLANNER, boss, null);

        boss = new Manager("bossman");
        timesheet = new Timesheet();
        customer = new Customer("customer");
        project = new Project("Test", customer, timesheet);
        planner = new Planner("planner");

        trafficController1 = new TrafficController("trafficcontroller",boss, timesheet);
        timesheetItem = new TimesheetItem(trafficController1, project, LocalTime.of(11, 30), LocalTime.of(13, 30), LocalDate.now());
        timesheet.addItem(timesheetItem);



    }

    /**
     * good weather test cases for the User class
     */

    @Test
    public void testUser() throws FlyvelederModelException {
        /** testing the constructor (String,String,User)   **/

        assertEquals(timesheet.getItems().size(), 1);
        TrafficController user = new TrafficController("a name", boss, timesheet);
        assertNotNull(user, "User should not be null");
        assertEquals(user.getName(), "a name");
        assertSame(user.getBoss().getName(), "bossman");
        assertEquals(user.getId(), "5");
//        assertEquals(user.getTypeOfUser(), User.TRAFFICCONTROLLER);
        assertEquals(user.getName(), "a name");
        assertSame(user.getBoss(), boss);
        assertEquals(user.getId(), "5"); // second user created... id should be 2

        /* Testing getters and setters to get 100% code coverage */
        assertTrue(customer instanceof Customer);
        assertTrue(planner instanceof Planner);
        assertTrue(boss instanceof Manager);

//        assertEquals(customer.getTypeOfUser(), User.CUSTOMER);
//        assertEquals(boss.getTypeOfUser(), User.MANAGER);
//        assertEquals(planner.getTypeOfUser(), User.PLANNER);
//
//
//        assertEquals(customer.getUserType(), "customer");
//        assertEquals(boss.getUserType(), "manager");
//        assertEquals(planner.getUserType(), "planner");


        TimesheetItem item = new TimesheetItem(user, project, LocalTime.of(12, 0), LocalTime.of(14, 0), LocalDate.now());

        assertEquals(user.getWorkList().size(), 0);
//        assertFalse(user instanceof Customer);
//        assertFalse(user instanceof Customer);
//        assertFalse(user instanceof Customer);

//
//        assertFalse(user.isCustomer());
//        assertFalse(user.isManager());
//        assertFalse(user.isPlanner());
//        assertTrue(user.isTrafficControler());
//        assertTrue(boss.isManager());
//        assertTrue(customer.isCustomer());

        assertNotNull(user.getVacationDates());
        LocalDate today = LocalDate.now();
        user.addVacationToUser(today);
        assertSame(user.getVacationDates().get(0), today);
        assertEquals(user.getVacationDates().size(), 1);
        user.removeVacationToUser(today);
        assertEquals(user.getVacationDates().size(), 0);

        assertNotNull(boss.getEmployees());

        assertEquals(boss.getEmployees().size(), 2);
        boss.removeEmployeeToBoss(user);
        assertEquals(boss.getEmployees().size(), 1);


        //test password management

        user.changePassword("hello");
        assertTrue(user.login("hello"));
        assertFalse(user.login("helloworld"));
        assertEquals(user.toString(), "userid 5, name a name");

//        assertEquals(user.getPassword(), "$2a$10$JiWDEI9Y0ZrNbcyPwHEUleIdOB5kuhyeK74hJ6krENIxdvk7x5Biy");
        assertNotEquals(user.getPassword(), "hello");
        assertFalse(user.isBHVer());
//        assertFalse(boss.isBHVer());
//        assertFalse(customer.isBHVer());
//        assertFalse(planner.isBHVer());
        user.setBHVLicense("B012695458");

        assertTrue(user.isBHVer());
        assertEquals(user.getBHVLicense(), "B012695458");

        user.setUserName("coronaController");
        assertEquals(user.getUserName(), "coronaController");

        user.setName("hans kazan");
        assertEquals(user.getName(), "hans kazan");

        assertEquals(user.getProjects().size(), 0);

    }


    /**
     * Bad weahter tests for the User class
     */
    @Test
    public void testBadUser() {
        //Creating a user with an unknown role should fail
//        assertThrows(AssertionError.class, () -> {
//            User user = new User("a name", 5, boss, timesheet);
//        }, "Creating a user with an unknown role should fail");
//
//        assertThrows(AssertionError.class, () -> {
//            User user = new User("a name", 0, boss, timesheet);
//        }, "Creating a user with an unknown role should fail");
//
//        assertThrows(AssertionError.class, () -> {
//            User user = new User("a name", 4, null, timesheet);
//        }, "Creating a trafficcontroller without a boss should fail");
//
//
//        assertThrows(AssertionError.class, () -> {
//            User user = new User("a name", 4, boss, null);
//        }, "Creating a trafficcontroller without timesheet should fail");
//
//
//        assertThrows(AssertionError.class, () -> {
//            User user = new User("", 4, boss, timesheet);
//        }, "Creating a trafficcontroller without a name should fail");
//
//        assertThrows(AssertionError.class, () -> {
//            User user = new User(null, 4, boss, timesheet);
//        }, "Creating a trafficcontroller with a null name should fail");
//
//
//        assertThrows(AssertionError.class, () -> {
//            Timesheet timesheet2 = new Timesheet();
//            User user = new User("TrafficController", User.TRAFFICCONTROLLER, boss, timesheet2);
//        }, "Creating a trafficcontroller with a empty timesheet should fail");
//
//        assertThrows(AssertionError.class, () -> {
//            User user = new User("TrafficController", User.TRAFFICCONTROLLER, boss, timesheet);
//            user.setBHVLicense("A124587963");
//        }, "first letter needs to B");
//
//
//        assertThrows(AssertionError.class, () -> {
//            User user = new User("TrafficController", User.TRAFFICCONTROLLER, boss, timesheet);
//            user.setBHVLicense("B12458796663");
//        }, "length longer then 10");

        //2

        assertThrows(AssertionError.class, () -> {
            TrafficController trafficController = new TrafficController(null, boss, timesheet);
        }, "creating traffic controller without a name");

        assertThrows(AssertionError.class, () -> {
            TrafficController trafficController = new TrafficController("coronacontroller", null, timesheet);
        }, "creating traffic controller without a boss");
        assertThrows(AssertionError.class, () -> {
            TrafficController trafficController = new TrafficController("coronacontroller", boss, null);
        }, "timesheet is null");
        assertThrows(AssertionError.class, () -> {
            TrafficController trafficController = new TrafficController("", boss, null);
        }, "trafficonctoller with empty name");

        assertThrows(AssertionError.class, () -> {
            TrafficController user = new TrafficController("coronacontroller", boss, timesheet);
            user.setBHVLicense("B12458796663");
        });
        assertThrows(AssertionError.class, () -> {
            TrafficController user = new TrafficController("coronacontroller", boss, timesheet);
            user.setBHVLicense("A124587963");
        }, "first letter needs to B");




    }


    // ==========================================
    // Code to check the 'asserts-enabled' status
    // (Otherwise all the bad-weather tests will fail even if assertions have been added)
    static {
        boolean ea = false;
        assert ea = true; // mis-using a side-effect !
        if (!ea)
            System.err.println("** WARNING: ASSERTS ARE NOT ENABLED **");
    }


}