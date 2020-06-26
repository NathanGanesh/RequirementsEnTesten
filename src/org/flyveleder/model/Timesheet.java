package org.flyveleder.model;

import org.flyveleder.exceptions.FlyvelederModelException;

import java.util.HashMap;
import java.util.stream.Collectors;

public class Timesheet {


    private final HashMap<String, TimesheetItem> timeSheet;

    public Timesheet() {
        timeSheet = new HashMap<>();
    }


    /**
     * Add an timesheet item to the ledger
     *
     * @param item
     * @throws FlyvelederModelException pre-condition: The item should not cause a double entry for the trafficcontroller. e.g.
     *                                  if the trafficcontroller is working on 2020-10-1 from 12:00 to 18:00 an attempt to add
     *                                  an item for this trafficcontroler for 2020-10-1 from 17:00 to 21:00 should fail with a
     *                                  FlyvelederModelException!
     *                                  <p>
     *                                  post-condition: the new item should be appendend in the ledger.
     */
    public void addItem(TimesheetItem item) throws FlyvelederModelException {
        boolean doubleEntry = false;
        assert !timeSheet.containsValue(item) : "item already exist";
        HashMap<String, TimesheetItem> timesheetItemHashMap = getTimeSheetForUser(item.getTrafficController().getId());
        for (TimesheetItem value : timesheetItemHashMap.values()) {
            //if there is another value for an already existing item it means double planned
            if (value.getStart().isBefore(item.getEnd()) && value.getEnd().isAfter(item.getStart())) {
                doubleEntry = true;
                break;
            }

        }
        assert !doubleEntry : new FlyvelederModelException("User is planned double which cant");

        timeSheet.put(item.getId(), item);
    }

    public void removeItem(TimesheetItem item) throws FlyvelederModelException {
        assert timeSheet.containsValue(item) : "Key doesnt exist";
        timeSheet.remove(item.getId());
    }

    public void removeItem(String itemid) throws FlyvelederModelException {
        assert timeSheet.containsKey(itemid) : "Key doesnt exist";
        timeSheet.remove(itemid);
    }


    public TimesheetItem getTimeSheetItem(String id) {
        assert id != null : "Null naam";
        assert !id.isEmpty() : "Null naam";
        assert timeSheet.containsKey(id) : "Key doesnt exist";
        return timeSheet.get(id);
    }

    /**
     * @return all items in the time sheet.
     * todo: This method is not save...
     * Explain why this is the case and change it so is is save
     */
    public HashMap<String, TimesheetItem> getItems() {
        return timeSheet;
    }


    /**
     * @param userid
     * @return all the timesheet items for a specific user
     * @throws FlyvelederModelException
     */
    public HashMap<String, TimesheetItem> getTimeSheetForUser(String userid) throws FlyvelederModelException {
        // TODO: 26-6-2020 assert used id --> so it assert to the hashmap
        assert userid != null : "Null naam";
        assert !userid.isEmpty() : "Null naam";
        return (HashMap<String, TimesheetItem>) timeSheet.entrySet().stream()
                .filter(u -> u.getValue().getTrafficController().getId().equals(userid))
                .collect(Collectors.toMap(u -> u.getKey(), u -> u.getValue()));
    }

    /**
     * @param projectid
     * @return all the timesheet items for a specific project
     * @throws FlyvelederModelException
     */
    public HashMap<String, TimesheetItem> getTimeSheetForProject(String projectid) throws FlyvelederModelException {
        // TODO: 26-6-2020 assert used id --> so it assert to the hashmap
        assert projectid != null : "Null naam";
        assert !projectid.isEmpty() : "Null naam";
        return (HashMap<String, TimesheetItem>) timeSheet.entrySet().stream()
                .filter(u -> u.getValue().getProject().getId().equals(projectid))
                .collect(Collectors.toMap(u -> u.getKey(), u -> u.getValue()));
    }

}
