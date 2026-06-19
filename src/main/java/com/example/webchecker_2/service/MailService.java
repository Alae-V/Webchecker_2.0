package com.example.webchecker_2.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import com.example.webchecker_2.model.ContentItem;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendSummaryMail(String to, String websiteName,List<ContentItem> contentItems){

        if (contentItems.isEmpty()) {
            return;
        }

        StringBuilder contentBuilder = new StringBuilder();

        for(ContentItem item : contentItems){
            contentBuilder.append("- ").append(item.getContentText())
                    .append("\n URL: ").append(item.getContentUrl())
                    .append("\n\n");
        }

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Neue Inhalte auf "+ websiteName);
        message.setText("Auf der Website "+ websiteName + "wurden neue Inhalte gefunden"
        +contentBuilder.toString());

        mailSender.send(message);

        log.info("Mail an {} erfolgreich gesendet",to);


    }

}
