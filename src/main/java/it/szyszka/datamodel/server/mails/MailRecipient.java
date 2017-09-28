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
public class MailRecipient {

    @Getter @Setter private String name;
    @Getter @Setter private InternetAddress email;

}
