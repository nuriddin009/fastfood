package uz.fastfood.dashboard.mapper;

import org.mapstruct.Mapper;
import uz.fastfood.dashboard.dto.UserDto;
import uz.fastfood.dashboard.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDto, User>{
}
