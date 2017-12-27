package it.szyszka.security.mails.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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