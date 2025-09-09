package api.models.user.get;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUserList {
    private Integer page;
    private Integer per_page;
    private Integer total;
    private Integer total_pages;
    private List<GetUser> data;
}
