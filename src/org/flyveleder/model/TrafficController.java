package org.flyveleder.model;

import org.flyveleder.exceptions.FlyvelederModelException;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class TrafficController extends User {
    private ArrayList<LocalDate> vacationDates;
    private Timesheet timeSheet;
    private Manager boss;
    private String BHVLicense;


    public TrafficController(String name, Manager boss, Timesheet timesheet) {
        super(name);
        this.boss = boss;

        vacationDates = new ArrayList<>();
        if (boss != null) {
            boss.addEmployeeToBoss(this);
        }
        assert boss != null : "trafficcontroller must need an manager";

        this.timeSheet = timesheet;
        assert timeSheet != null : "no time sheet";
//        assert !timeSheet.getItems().isEmpty() : "time sheet is empty for trafficController";
    }


    public boolean isBHVer() {
        return BHVLicense != null;
    }


    /**
     * Some trafficcontrollers can have a BHV license.
     * The BHV license is a 10 position string starting
     * with the Letter B and consisting of 9 number.
     * hint: you ccould use a regular expression to check this, see the String.Matches function
     */
    public void setBHVLicense(String BHVLicense) {
        String pattern = "^[B][0-9]{9}";
        assert BHVLicense != null : "Null naam";
        assert !BHVLicense.isEmpty() : "Null naam";
        assert BHVLicense.matches(pattern) : "First letter b and then 9 digits";
        assert !(BHVLicense.length() > 10) : "Length is bigger then 10";
        this.BHVLicense = BHVLicense;
    }

    public ArrayList<LocalDate> getVacationDates() {
        return new ArrayList<>(vacationDates);
    }

    public void addVacationToUser(LocalDate localDate){
        assert localDate != null: "localdate value is empty";
        vacationDates.add(localDate);
    }
    public void removeVacationToUser(LocalDate localDate){
        assert localDate != null: "localdate value is empty";
        vacationDates.remove(localDate);
    }



    public String getBHVLicense() {
        return BHVLicense;
    }

    public Manager getBoss() {
        return boss;
    }


    /**
     * @return Projects the user has been planned in, in the future
     * @throws FlyvelederModelException
     */
    public ArrayList<Project> getProjects() throws FlyvelederModelException {
        // TODO: 26-6-2020 test case
        ArrayList<Project> projects = new ArrayList<>();
        HashMap<String, TimesheetItem> timesheet = timeSheet.getTimeSheetForUser(getId());
        ArrayList<TimesheetItem> items = new ArrayList(timesheet.values());
        //sort work on the date
        items.sort((w1, w2) -> w1.getDate().isBefore(w2.getDate()) ? -1 : 1);
        items.forEach(item -> {
            // TODO: 28-6-2020 write test case
            if (!projects.contains(item.getProject())) projects.add(item.getProject());
        });
        return projects;
    }

    /**
     * @return A list of all timesheet items for the user.
     * @throws FlyvelederModelException
     */
    public ArrayList<TimesheetItem> getWorkList() throws FlyvelederModelException {
        // TODO: 26-6-2020 testcase
        HashMap<String, TimesheetItem> timesheet = timeSheet.getTimeSheetForUser(getId());
        ArrayList<TimesheetItem> items = new ArrayList(timesheet.values());
        return items;
    }
}
