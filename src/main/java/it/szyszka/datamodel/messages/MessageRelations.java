package it.szyszka.datamodel.messages;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class MessageRelations {

    @NonNull @Getter @Setter private SendMessage send;
    @NonNull @Getter @Setter private ReceivedMessage received;

}
