package uz.fastfood.dashboard.dto.response;

import lombok.Data;

@Data
public class DistanceInfo  {
    private int distance;
    private int time;
    private int source_index;
    private int target_index;
}
