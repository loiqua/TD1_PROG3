package model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Author {
    private int author_id;
    private String Name;
    private String sex;

    public Author() {

    }


}