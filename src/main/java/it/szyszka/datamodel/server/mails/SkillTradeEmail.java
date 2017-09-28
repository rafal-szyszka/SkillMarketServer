package it.szyszka.datamodel.server.mails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.mail.internet.InternetAddress;

/**
 * Created by rafal on 28.09.17.
 */
@NoArgsConstructor
@AllArgsConstructor
public abstract class SkillTradeEmail {

    @Getter @Setter protected String subject;
    @Getter @Setter protected String content;
    @Getter @Setter protected MailRecipient recipient;
    @Getter @Setter protected Object[] extras;

}