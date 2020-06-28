package org.flyveleder.model;

import org.flyveleder.exceptions.FlyvelederModelException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProjectTest {
    private Timesheet timesheet;
    private User boss;
    private Project project;
    private TimesheetItem timesheetItem;
    private Customer customer;

    @BeforeEach
    public void startUp() throws FlyvelederModelException{
        boss = new Manager("coronaboss");
        customer = new Customer("coronacustomer");
        timesheet = new Timesheet();
        project = new Project("coronaproject", customer, timesheet);
        timesheetItem = new TimesheetItem(boss, project, LocalTime.of(11, 30), LocalTime.of(19, 30), LocalDate.now());
    }

    /**
     * good weather test
     */
    @Test
    public void testGoodProject() throws FlyvelederModelException {
        assertNotNull(project);
        assertEquals(project.getTotalTime(), 0);
        assertNotNull(timesheetItem);
        timesheet.addItem(timesheetItem);
        assertEquals(project.getTotalTime(),28800);

        //getters
        assertEquals(project.getProjectName(), "coronaproject");
        assertEquals(project.getCustomer(), customer);

        project.setAdress("coronadreef 19");
        assertEquals(project.getAdress(), "coronadreef 19");

        project.setNotes("1.5m social distance!!!");
        assertEquals(project.getNotes(), "1.5m social distance!!!");

        assertEquals(project.getTimeSheet().size(), 1);
        //comment so i dotn have to wait 3days
//        assertNotNull(project.sendMail());

    }

    /**
     * bad weather test
     */
    @Test
    public void badWeatherProject(){
        assertThrows(AssertionError.class, () ->{
            Project project = new Project(null, customer, timesheet);
        },"project with a null name");

        assertThrows(AssertionError.class, () ->{
            Project project = new Project("", customer, timesheet);
        },"project with no name");

        assertThrows(AssertionError.class, () ->{
            Project project = new Project("coronaproject", null, timesheet);
        },"project with no customer");

        assertThrows(AssertionError.class, () ->{
            Project project = new Project("coronaproject",customer, null);
        },"project with no timesheet");



    }
}