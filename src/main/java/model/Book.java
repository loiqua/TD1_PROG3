package model;



import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Book {
    private int id; // Utilisation de "id" pour correspondre à "id SERIAL PRIMARY KEY" dans la table PostgreSQL
    private String bookId;
    private String bookName;
    private Topic topic;
    private int pageNumber;
    private LocalDateTime releaseDate;
    private int authorId;
    private boolean isBorrow;

    public Book() {

    }
}
