package uz.fastfood.dashboard.filter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.fastfood.dashboard.entity.enums.OrderStatus;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@ApiModel(description = "Параметры фильтра")
@ToString(onlyExplicitlyIncluded = true, doNotUseGetters = true)
public class OrderFilter extends PageFilter {




    @ApiModelProperty("С")
    @ToString.Include
    private LocalDateTime from;

    @ApiModelProperty("По")
    @ToString.Include
    private LocalDateTime to;

    @ApiModelProperty("Status")
    @ToString.Include
    private OrderStatus orderStatus;

    public LocalDateTime getFrom() {
        return from != null ? from.with(LocalTime.MIN) : null;
    }

    public LocalDateTime getFromDateTime() {
        return this.from;
    }

    public LocalDateTime getTo() {
        return to != null ? to.with(LocalTime.MAX) : null;
    }




}


