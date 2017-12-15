package it.szyszka.utils.security.mails.service;

import it.szyszka.utils.security.mails.model.SkillTradeEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;

/**
 * Created by rafal on 28.09.17.
 */
@Service
public class MailServiceImpl {

    public static final String SENDER_MAIL = "skill.trade.verify@gmail.com";

    @Autowired
    JavaMailSender mailSender;

    public void sendMail(SkillTradeEmail email) {
        MimeMessagePreparator preparator = getMessagePreparator(email);
        try {
            mailSender.send(preparator);
        } catch(MailException e) {
            e.printStackTrace();
        }
    }

    private MimeMessagePreparator getMessagePreparator(SkillTradeEmail email) {

        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setFrom(SENDER_MAIL);
            mimeMessage.setRecipient(Message.RecipientType.TO, email.getRecipient().getEmail());
            mimeMessage.setText(email.getContent());
            mimeMessage.setSubject(email.getSubject());
        };
        return preparator;
    }

}
