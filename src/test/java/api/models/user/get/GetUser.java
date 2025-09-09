package api.models.user.get;

import lombok.Data;

@Data
public class GetUser {
    private Integer id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
}
