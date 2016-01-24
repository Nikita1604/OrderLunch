package com.nikita.pischik.orderlunch.notification;


import com.nikita.pischik.orderlunch.model.User;
import com.nikita.pischik.orderlunch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.List;
import java.util.Properties;

public class SimpleNotificationManager implements NotificationManager {

    private JavaMailSender mailSender;

    private SimpleMailMessage templateMessage;


    public SimpleNotificationManager() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("orderlunchnotification@gmail.com");
        mailSender.setPassword("qwermnbv");
        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty("mail.transport.protocol", "smtp");
        javaMailProperties.setProperty("mail.smtp.auth", "true");
        javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
        javaMailProperties.setProperty("mail.debug", "true");
        mailSender.setJavaMailProperties(javaMailProperties);
        this.setMailSender(mailSender);
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void sendNotificationMessage(List<User> userList) {
        for (int i=0; i<userList.size(); i++) {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setSubject("Не останьтесь голодным!");
            msg.setTo(userList.get(i).getE_mail());
            msg.setText(userList.get(i).getName() + ", напоминаем вам о необходимости сделать заказ обеда " +
                    "на следующий рабочий день до 13.00 на сайте OrderLuch!");
            try {
                this.mailSender.send(msg);
            }
            catch (MailException e) {
                System.err.println(e.getMessage());
            }
        }
    }


    public void placeOrder(User user) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject("Регистрация на сайте OrderLunch!");
        //msg.setFrom("OrderLunch");

        //msg.setFrom("Администрация сайта OrderLunch.");
        msg.setTo(user.getE_mail());
        msg.setText("Поздравляем, " + user.getName() + ", вы были успешно " +
                "зарегестрированны на сайте OrderLunch.\n Login: " + user.getLogin() +
                "\n Password: " + user.getPassword());
        try {
            this.mailSender.send(msg);
        }
        catch (MailException e) {
            System.err.println(e.getMessage());
        }
    }
}
