package api.models.user.change;

import lombok.Data;

@Data
public class CreateResponse {
    private String name;
    private String job;
    private String id;
    private String createdAt;
}
