package org.flyveleder.model;

import org.flyveleder.exceptions.FlyvelederModelException;
import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by abe23 on 08/11/17.
 */

public class User implements Serializable {

    public final static int             CUSTOMER = 1;
    public final static int             PLANNER = 2;
    public final static int             MANAGER = 3;
    public final static int             TRAFFICCONTROLLER = 4;

    private static int                  maxId = 0;                  //static variable for the creation of ids

    private int                         typeOfUser;
    private String                      id;                         //Unique id of the user
    private String                      name;                       // Login name for the user
    private String                      userName;
    private String                      password;                   //Hashed password default = testing

    //Only for the manager
    public ArrayList < User >           employees;

    //Only for the controllers
    private ArrayList < LocalDate >     vacationDates;              //The vacation dates of a controller
    private Timesheet                   timeSheet;                  //reference to the Timesheet ledger
    private User                        boss;
    private String                      BHVLicense;


    /**
     *
     * @param name
     * @param typeOfUser
     * @param boss
     * @param timeSheet The global timeSheet ledger. This ledger should shared by all traficcontrollers
     *                  So every trafficcontroller has a reference to the same ledger!
     *
     * TODO: each User that is a traffic controller needs a boss
     * this is the user who approves the hours of a traffic contoller
     */
    public User(String name, int typeOfUser, User boss, Timesheet timeSheet)   {
        assert name != null : "Name cannot be null";
        employees = new ArrayList < > ();
        vacationDates = new ArrayList < > ();
        this.id = Integer.valueOf(++maxId).toString();
        this.boss = boss;
        if (boss != null) {
            boss.getEmployees().add(this);
        }
        if (typeOfUser == TRAFFICCONTROLLER ) {
            this.timeSheet = timeSheet;
        }
        this.name = name;
        changePassword("testing");
        this.typeOfUser = typeOfUser;
        this.BHVLicense = null;
    }

    public int getTypeOfUser() {
        return typeOfUser;
    }


    /**
     *
     * @return String representation of the USER.
     * This is needed for the interface
     */
    public String getUserType() {
        switch (typeOfUser) {
            case CUSTOMER:
                return "customer";
            case TRAFFICCONTROLLER:
                return "trafficcontroller";
            case PLANNER:
                return "planner";
            case MANAGER:
                return "manager";
            default:
                assert false: "Unknown type of user!!";
                break;
        }
        return "unknown";
    }


    /**
     * Helper function that checks if the supplied password is valid
     * @param password: hashed password
     * @return true of false
     */
    public boolean login(String password) {
        return BCrypt.checkpw(password, this.password);
    }

    /**
     * Change the password
     * @param newPassword: password that will be hashed
     */
    public void changePassword(String newPassword) {
        this.password = BCrypt.hashpw(newPassword, BCrypt.gensalt());
    }


    /** GETTERS **/

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public User getBoss() {
        return boss;
    }


    public String getPassword() {
        return password;
    }


    public String getUserName() {
        return userName;
    }

    public String getBHVLicense() {
        return BHVLicense;
    }

    /**
     *
     * @return Projects the user has been planned in, in the future
     * @throws FlyvelederModelException
     */
    public ArrayList < Project > getProjects() throws FlyvelederModelException {
        ArrayList < Project > projects = new ArrayList < > ();
        HashMap <String,TimesheetItem> timesheet = timeSheet.getTimeSheetForUser(id);
        ArrayList<TimesheetItem> items = new ArrayList(timesheet.values());
        //sort work on the date
        items.sort((w1, w2) -> w1.getDate().isBefore(w2.getDate()) ? -1 : 1);
        items.forEach(item -> {
            if (!projects.contains(item.getProject())) projects.add(item.getProject());
        });
        return projects;
    }


    /**
     *
     * @return A list of all timesheet items for the user.
     * @throws FlyvelederModelException
     */
    public ArrayList <TimesheetItem> getWorkList() throws FlyvelederModelException {
        HashMap <String,TimesheetItem> timesheet = timeSheet.getTimeSheetForUser(id);
        ArrayList<TimesheetItem> items = new ArrayList(timesheet.values());
        return items;
    }


    public ArrayList<User> getEmployees() {
        return employees;
    }

    public boolean isTrafficControler() {
        return this.typeOfUser == TRAFFICCONTROLLER;
    }

    public boolean isPlanner() {
        return this.typeOfUser == PLANNER;
    }

    public boolean isManager() {
        return this.typeOfUser == MANAGER;
    }

    public boolean isCustomer() {
        return this.typeOfUser == CUSTOMER;
    }

    /** SETTERS **/


    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /*
        Some trafficcontrollers can have a BHV license.
        The BHV license is a 10 position string starting
        with the Letter B and consisting of 9 number.
        hint: you ccould use a regular expression to check this, see the String.Matches function
     */
    public void setBHVLicense(String BHVLicense) {
        this.BHVLicense = BHVLicense;
    }

    public boolean isBHVer() {
        return BHVLicense == null;
    }

    //todo : This is dangerous. Refactor the code so that the defensive programming guidlines are adhered to
    public ArrayList < LocalDate > getVacationDates() {
        return vacationDates;
    }


    @Override
    public String toString() {
        return getUserType() + ", userid " + id + ", name " + name;
    }

}