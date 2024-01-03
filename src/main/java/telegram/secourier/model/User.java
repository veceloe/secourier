package telegram.secourier.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Users")
@Table(name="users")
public class User {

    @Id
    private Long id;

    private Long chatId;

    private String name;

    private String email;

    private String appPassword;

    public String[] toArray() {
        return new String[] {id.toString(), chatId.toString(), name, email, appPassword};
    }

    public static User fromArray(String[] array) {
        return new User(Long.parseLong(array[0]), Long.parseLong(array[1]), array[2], array[3], array[4]);
    }

    public static User fromString(String string) {
        return fromArray(string.split(","));
    }

    public static String toString(User user) {
        return String.join(",", user.toArray());
    }

}
