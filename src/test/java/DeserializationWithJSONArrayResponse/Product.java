package DeserializationWithJSONArrayResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private int id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private Rating rating;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Rating{
        private double rate;
        private int count;
    }

}
