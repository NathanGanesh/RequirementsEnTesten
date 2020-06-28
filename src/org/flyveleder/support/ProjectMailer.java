package org.flyveleder.support;

import org.flyveleder.model.Project;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class ProjectMailer {

    private final Project project;
    private String mailTemplate;


    public ProjectMailer(Project project) {
        this.project = project;
    }



    public void sendMail() {

        final String username = "nathangemieee@gmail.com"; //fill in your own google emailaddress
        final String password = "nathanj1k"; //fill in your own password

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        //to enable sending over TLS you need to enable less secure aps:
        //https://myaccount.google.com/lesssecureapps

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("bart.flyveleder@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("nathangemieee@gmail.com")
            );
            message.setSubject("Flyveleder project informatiion");
            message.setText(mailTemplate);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();

        }
    }

    public void setTemplate(String mailTemplate) {
        this.mailTemplate = mailTemplate;
    }
}
