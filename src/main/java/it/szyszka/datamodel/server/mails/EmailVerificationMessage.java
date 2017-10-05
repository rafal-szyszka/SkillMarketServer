package it.szyszka.datamodel.server.mails;

import it.szyszka.utils.PropertiesReader;
import lombok.NoArgsConstructor;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Properties;

/**
 * Created by rafal on 28.09.17.
 */
@NoArgsConstructor
public class EmailVerificationMessage extends SkillTradeEmail {

    private static final int TOKEN_INDEX = 0;
    private static final String VERIFY_HOST = "http://192.168.0.5:8080/api/user/verifyEmail";
    public static final String PROPERTIES_FILE = "verify_email_template.properties";

    public static EmailVerificationMessage createMessage(String fullName, String userEmail, String... token) throws AddressException {

        PropertiesReader reader = new PropertiesReader(new Properties());
        Properties messageTemplate = reader.readMyProperties(PROPERTIES_FILE);

        EmailVerificationMessage msg = new EmailVerificationMessage();
        msg.setSubject(messageTemplate.getProperty("subject"));
        msg.setContent(messageTemplate.getProperty("content_template"));

        msg.setRecipient(new MailRecipient(fullName, new InternetAddress(userEmail)));
        msg.setExtras(token);

        msg.setContent(msg.write());

        return msg;
    }

    public EmailVerificationMessage(String subject, String content, MailRecipient recipient, Object... token) {
        super(subject, content, recipient, token);
    }

    public String write() {
        return content
                .replaceAll("<name>", recipient.getName())
                .replaceAll("<host>", VERIFY_HOST)
                .replaceAll("<sha256>", extras[TOKEN_INDEX].toString());
    }

}