package api.models.register;

import lombok.Data;

@Data
public class SuccessResponse {
    private Integer id;
    private String token;
}
