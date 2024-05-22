package com.cafe.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text, List<String> lst) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // set name of email
            helper.setFrom("cp864323@gmail.com", "Cafe Management");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            if (lst != null && lst.size() > 0)
                helper.setCc(getCcArray(lst));
            
            
            emailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] getCcArray(List<String> ccList) {
        String[] ccArray = new String[ccList.size()];
        for (int i = 0; i < ccList.size(); i++) {
            ccArray[i] = ccList.get(i);
        }
        return ccArray;
    }
}
