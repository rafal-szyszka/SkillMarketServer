package it.szyszka.controllers.user;

import it.szyszka.datamodel.user.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by rafal on 08.09.17.
 */
@Repository
public interface UserRepository extends GraphRepository<User> {

    User findByEmail(String email);
    User findByNickname(String nickname);

    @Query("MATCH (u {email: {userEmail}}) SET u.password = {newPassword} RETURN u")
    User updateUserPassword(@Param("userEmail") String userEmail, @Param("newPassword") String newPassword);

    @Query("MATCH (u {email: {userEmail}}) " +
                "SET u.city = {city} " +
                "SET u.mailingAddress = {mailingAddress} " +
                "SET u.phoneNumber = {phoneNumber} " +
                "SET u.about = {about} " +
                "SET u.preferredContact = {preferredContact} " +
            "RETURN u")
    User updateUserDetails(
            @Param("userEmail") String userEmail,
            @Param("city") String city,
            @Param("mailingAddress") String mailingAddress,
            @Param("phoneNumber") String phoneNumber,
            @Param("about") String about,
            @Param("preferredContact") String preferredContact
    );

}
