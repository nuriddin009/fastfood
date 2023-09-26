package uz.fastfood.dashboard.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import uz.fastfood.dashboard.entity.enums.SortTypeEnum;

import java.io.Serializable;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true, doNotUseGetters = true)
@ApiModel(description = "Параметры фильтра")
public class PageFilter implements Serializable {
    @ApiModelProperty(value = "The page number, by default it is set to 0")
    @Min(value = 0, message = "Page number cannot be smaller than 0")
    @ToString.Include
    private Integer page = 0;
    @ApiModelProperty(value = "The size of the page, by default it is set to 200")
    @Min(value = 0, message = "Page size cannot be smaller than 0")
    @ToString.Include
    private Integer size = 200;

    @ApiModelProperty(value = "The Ordering column")
    @ToString.Include
    private String orderBy = "id";

    @ApiModelProperty(value = "The order of sorting, available values are 'asc' and 'desc'")
    @ToString.Include
    private SortTypeEnum sortOrder = SortTypeEnum.desc;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public int getStart() {
        return this.getPage() * this.getSize();
    }

    public int getPage() {
        return Math.abs(this.page);
    }

    public int getSize() {
        return Math.abs(this.size);
    }

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public Sort getOrderedSortBy() {
        return sortOrder.equals(SortTypeEnum.asc) ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();
    }

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public Pageable getPageable() {
        return PageRequest.of(this.getPage(), this.getSize());
    }

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public Pageable getSortedPageable() {
        return PageRequest.of(this.getPage(), this.getSize(), getOrderedSortBy());
    }
}

