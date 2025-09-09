package ui.data;

import lombok.Getter;

@Getter
public class User {
    private String username;
    private String password = "secret_sauce";
    private String firstName;
    private String lastName;
    private String postalCode;

    public User(String username, String firstName, String lastName, String postalCode) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
    }

    public User(String username) {
        this.username = username;
    }
}
