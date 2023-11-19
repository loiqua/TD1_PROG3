package model;


import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Subscriber {
    private int subscriberId; // Utilisation de "subscriberId" pour correspondre à "Subscriber_id SERIAL PRIMARY KEY" dans la table PostgreSQL
    private String subscriberName;
    private char sex;

    public Subscriber() {

    }
}

