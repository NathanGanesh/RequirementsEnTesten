package org.flyveleder.model;

import org.flyveleder.exceptions.FlyvelederModelException;
import org.flyveleder.support.ProjectMailer;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by abe23 on 08/11/17.
 */
public class Project {

    private final String id;
    private final String projectName;
    private final User customer;
    private final Timesheet timeSheet;          //reference to the timsheet ledger
    private String Adress;
    private String Notes;
    private static int maxId = 0;

//    public Project(String projectName, User customer, Timesheet timeSheet) {
//        this.id = "" + ++maxId;
//        assert projectName != null : "project name is null";
//        assert !projectName.isEmpty() : "project name is empty";
//        this.projectName = projectName;
//        assert customer != null : "customer is null";
//        this.customer = customer;
//        assert timeSheet != null : "time is null";
//        this.timeSheet = timeSheet;
//    }

    public Project(String projectName, Customer customer, Timesheet timeSheet) {
        this.id = "" + ++maxId;
        assert projectName != null : "project name is null";
        assert !projectName.isEmpty() : "project name is empty";
        this.projectName = projectName;
        assert customer != null : "customer is null";
        this.customer = customer;
        assert timeSheet != null : "time is null";
        this.timeSheet = timeSheet;
    }


    /**
     * @return the total time spend on a project
     * @throws FlyvelederModelException
     */
    public long getTotalTime() throws FlyvelederModelException {
        Duration totaltime = Duration.ofHours(0);
        assert id!=null:"id is null";
        HashMap<String, TimesheetItem> timesheet = timeSheet.getTimeSheetForProject(id);
        totaltime = timesheet.values().stream().map(unit ->
                Duration.between(unit.getStart(), unit.getEnd())
        )
                .reduce(totaltime, (sum, time) -> sum.plus(time));
        return totaltime.getSeconds();
    }

    public String getId() {
        return id;
    }

    public String getProjectName() {
        return projectName;
    }

    public User getCustomer() {
        return customer;
    }

    public String getAdress() {
        return Adress;
    }

    public String getNotes() {
        return Notes;
    }


    public void setAdress(String adress) {
        assert adress != null : "adress naam";
        assert !adress.isEmpty() : "adress naam";
        Adress = adress;
    }

    public void setNotes(String notes) {
        assert notes != null : "Null notes";
        assert !notes.isEmpty() : "Null notes";
        Notes = notes;
    }

    private String getMailTemplate() {
        InputStream is = null;
        String template = "";
        try {
            is = new FileInputStream("./databases/mailtemplate.tpl");
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
            template = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return template;
    }

    public ProjectMailer sendMail() {

        ProjectMailer mailer = new ProjectMailer(this);

        mailer.setTemplate(getMailTemplate());

        mailer.sendMail();
        //so we know who send the mail its possible to return a boolean from send mail
        return mailer;

    }

    /**
     * @return all the timesheet items belonging to this project.
     * @throws FlyvelederModelException
     */
    public ArrayList<TimesheetItem> getTimeSheet() throws FlyvelederModelException {
        return new ArrayList(timeSheet.getTimeSheetForProject(id).values());
    }

}