package api.models.resource;

import api.models.resource.Resource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceList {
    private Integer page;
    private Integer per_page;
    private Integer total;
    private Integer total_pages;
    private List<Resource> data;
}
