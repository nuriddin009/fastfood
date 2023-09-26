package uz.fastfood.dashboard.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Arrays;

@Getter
public enum SortTypeEnum implements Serializable {
    asc("asc"),
    desc("desc");

    private final String name;

    SortTypeEnum(String name) {
        this.name = name;
    }

    public static SortTypeEnum get(String name) {
        if (StringUtils.isEmpty(name)) {
            return SortTypeEnum.asc;
        }
        for (SortTypeEnum sortType : SortTypeEnum.values()) {
            if (name.equals(sortType.getName())) {
                return sortType;
            }
        }
        return SortTypeEnum.asc;
    }

    @JsonCreator
    @SuppressWarnings("unused")
    static SortTypeEnum findValue(@JsonProperty("name") String name) {
        return Arrays.stream(SortTypeEnum.values()).filter(pt -> pt.name().equals(name)).findFirst().get();
    }
}

