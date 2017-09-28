package it.szyszka.datamodel.user;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rafal on 26.09.17.
 */
@RequiredArgsConstructor
public class UserDTO {

    public class Details {
        @Getter @Setter private String email;
        @Getter @Setter private String mailingAddress;
        @Getter @Setter private String city;
        @Getter @Setter private String phoneNumber;
        @Getter @Setter private String about;
        @Getter @Setter private String preferredContact;
    }

    public enum ActivationStatus {
        INACTIVE, ACTIVE
    }

    @NonNull @Getter @Setter private Long id;

    @NonNull @Getter @Setter private String nickname;
    @NonNull @Getter @Setter private String fullName;

    @NonNull @Getter @Setter private String email;

    @NonNull @Getter @Setter private String mailingAddress;
    @NonNull @Getter @Setter private String city;
    @NonNull @Getter @Setter private String phoneNumber;

    @NonNull @Getter @Setter private String about;
    @NonNull @Getter @Setter private String preferredContact;

    @Getter @Setter private Set<User> friends;
    @Getter @Setter private Set<User> trusted;

    public UserDTO(User user) {
        id = user.getId();
        nickname = user.getNickname();
        fullName = user.getFullName();
        email = user.getEmail();
        mailingAddress = user.getMailingAddress();
        phoneNumber = user.getPhoneNumber();
        about = user.getAbout();
        preferredContact = user.getPreferredContact();
        friends = user.getFriends();
        trusted = user.getTrusted();
    }

    public static UserDTO getSimpleUser(User user) {
        return new UserDTO(user.getId(),
                user.getNickname(),
                user.getFullName(),
                user.getEmail(),
                user.getMailingAddress() == null ? "" : user.getMailingAddress(),
                user.getCity(),
                user.getPhoneNumber() == null ? "" : user.getPhoneNumber(),
                user.getAbout() == null ? "" : user.getAbout(),
                user.getPreferredContact() == null ? "" : user.getPreferredContact()
        );
    }

    public static Set<UserDTO> convertToSimpleSet(Set<User> set) {
        Set<UserDTO> simpleSet = new HashSet<>();
        set.forEach(user -> simpleSet.add(getSimpleUser(user)));
        return simpleSet;
    }

}
