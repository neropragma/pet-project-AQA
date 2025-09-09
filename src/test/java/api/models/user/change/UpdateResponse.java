package api.models.user.change;

import lombok.Data;

@Data
public class UpdateResponse {
    private String name;
    private String job;
    private String updatedAt;
}
