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

    private User boss;
    private Timesheet timesheet;
    private Project project;
    private User customer;
    private User planner;


    @BeforeAll
    public void startUp() {
        boss = new User("bossman",User.MANAGER,null,null);
        customer = new User("customer",User.CUSTOMER,null,null);
        planner = new User("planner",User.PLANNER,boss,null);
        timesheet = new Timesheet();
        project = new Project("Test",customer,timesheet);

    }

    /**
     * good weather test cases for the User class
     */

    @Test
    public void testUser() throws FlyvelederModelException {
        /** testing the constructor (String,String,User)   **/


        User user = new User("a name",User.TRAFFICCONTROLLER,boss,timesheet);
        assertNotNull(user,"User should not be null");
        assertEquals(user.getTypeOfUser(),User.TRAFFICCONTROLLER);
        assertEquals(user.getName(),"a name");
        assertSame(user.getBoss(),boss);
        assertEquals(user.getId(),"4"); // second user created... id should be 2

        /* Testing getters and setters to get 100% code coverage */

        assertEquals(customer.getTypeOfUser(),User.CUSTOMER);
        assertEquals(boss.getTypeOfUser(),User.MANAGER);
        assertEquals(planner.getTypeOfUser(),User.PLANNER);


        assertEquals(customer.getUserType(),"customer");
        assertEquals(boss.getUserType(),"manager");
        assertEquals(planner.getUserType(),"planner");


        TimesheetItem item = new TimesheetItem(user,project, LocalTime.of(12,0),LocalTime.of(14,0),LocalDate.now());

        assertEquals(user.getWorkList().size(),0);


        assertFalse(user.isCustomer());
        assertFalse(user.isManager());
        assertFalse(user.isPlanner());
        assertTrue(boss.isManager());
        assertTrue(customer.isCustomer());

        assertNotNull(user.getVacationDates());
        LocalDate today = LocalDate.now();
        user.getVacationDates().add(today);
        assertSame(user.getVacationDates().get(0),today);

        assertNotNull(boss.getEmployees());
        assertEquals(boss.getEmployees().size(),2);

        //test password management

        user.changePassword("hello");
        assertTrue(user.login("hello"));
        assertFalse(user.login("helloworld"));


    }


    /**
     * Bad weahter tests for the User class
     */
    @Test
    public void testBadUser() {
        //Creating a user with an unknown role should fail
        assertThrows(AssertionError.class,() -> {
            User user = new User("a name",12,boss,timesheet);
        },"Creating a user with an unknown role should fail");
        //todo: Creating a trafficcontroller without a boss should fail, create the test!

        //todo: Creating a trafficcontroller without a timesheet should fail

        //todo: Creating a trafficcontroller without a name should fail

        //todo: Creating a trafficcontroller with a null name should fail

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