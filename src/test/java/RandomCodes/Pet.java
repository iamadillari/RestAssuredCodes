package RandomCodes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    private Integer id;
    private String name;
    private String status;
    private Category category;
    private List<String> photoUrls;
    private List<Tag> tags;


    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Category {
        private Integer id;
        private String name;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tag {
        private Integer id;
        private String name;
    }
}