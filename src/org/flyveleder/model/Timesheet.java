package org.flyveleder.model;

import org.flyveleder.exceptions.FlyvelederModelException;

import java.util.HashMap;
import java.util.stream.Collectors;

public class Timesheet {


    private final HashMap<String,TimesheetItem> timeSheet;

    public Timesheet() {
        timeSheet = new HashMap<>();
    }


    /**
     * Add an timesheet item to the ledger
     * @param item
     * @throws FlyvelederModelException
     *
     * pre-condition: The item should not cause a double entry for the trafficcontroller. e.g.
     * if the trafficcontroller is working on 2020-10-1 from 12:00 to 18:00 an attempt to add
     * an item for this trafficcontroler for 2020-10-1 from 17:00 to 21:00 should fail with a
     * FlyvelederModelException!
     *
     * post-condition: the new item should be appendend in the ledger.
     */
    public void addItem(TimesheetItem item) throws FlyvelederModelException {
        //todo: make sure businesss rules are correctly implemented
        timeSheet.put(item.getId(),item);
    }

    public void removeItem(TimesheetItem item) throws FlyvelederModelException {
        timeSheet.remove(item.getId());
    }

    public void removeItem(String itemid) throws FlyvelederModelException {
        timeSheet.remove(itemid);
    }


    public TimesheetItem getTimeSheetItem(String id) {
        return timeSheet.get(id);
    }

    /**
     *
     * @return all items in the time sheet.
     * todo: This method is not save...
     * Explain why this is the case and change it so is is save
     */
    public HashMap<String,TimesheetItem> getItems() {
        return timeSheet;
    }


    /**
     *
     * @param userid
     * @return all the timesheet items for a specific user
     * @throws FlyvelederModelException
     */
    public HashMap<String,TimesheetItem> getTimeSheetForUser(String userid) throws FlyvelederModelException {
        return (HashMap<String, TimesheetItem>) timeSheet.entrySet().stream()
                .filter(u -> u.getValue().getTrafficController().getId().equals(userid))
                .collect(Collectors.toMap(u -> u.getKey(), u -> u.getValue()));
    }

    /**
     *
     * @param projectid
     * @return all the timesheet items for a specific project
     * @throws FlyvelederModelException
     */
    public HashMap<String,TimesheetItem> getTimeSheetForProject(String projectid) throws FlyvelederModelException {
        return (HashMap<String, TimesheetItem>) timeSheet.entrySet().stream()
                .filter(u -> u.getValue().getProject().getId().equals(projectid))
                .collect(Collectors.toMap(u -> u.getKey(), u -> u.getValue()));
    }

}
