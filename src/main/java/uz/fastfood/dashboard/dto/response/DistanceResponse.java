package uz.fastfood.dashboard.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DistanceResponse {
    @JsonProperty("destination_addresses")
    private String[] destinationAddresses;

    @JsonProperty("origin_addresses")
    private String[] originAddresses;

    private Row[] rows;

    private String status;

    @Data
    public static class Row {
        private Element[] elements;
    }


    @Data
    public static class Element {
        private Distance distance;
        private Duration duration;
        private String status;
    }

    @Data
    public static class Distance {
        private String text;
        private int value;
    }

    @Data
    public static class Duration {
        private String text;
        private int value;
    }

}
