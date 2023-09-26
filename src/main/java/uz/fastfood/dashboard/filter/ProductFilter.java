package uz.fastfood.dashboard.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ApiModel(description = "Параметры фильтра")
@ToString(onlyExplicitlyIncluded = true, doNotUseGetters = true)
public class ProductFilter extends PageFilter {

    private UUID categoryId;

    private List<UUID> categoryIds;

    @ApiModelProperty(value = "The Search Key filter")
    @ToString.Include
    private String search = "";

    @ApiModelProperty("С")
    @ToString.Include
    private LocalDateTime from;

    @ApiModelProperty("По")
    @ToString.Include
    private LocalDateTime to;


    public LocalDateTime getFrom() {
        return from != null ? from.with(LocalTime.MIN) : null;
    }

    public LocalDateTime getFromDateTime() {
        return this.from;
    }

    public LocalDateTime getTo() {
        return to != null ? to.with(LocalTime.MAX) : null;
    }


    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public String getSearchForQuery() {
        return StringUtils.isNotEmpty(search) ? "%" + search.toLowerCase().replace("_", "\\_") + "%" : search;
    }

}


