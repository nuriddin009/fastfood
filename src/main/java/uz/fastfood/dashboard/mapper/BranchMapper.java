package uz.fastfood.dashboard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uz.fastfood.dashboard.dto.request.BranchDto;
import uz.fastfood.dashboard.dto.request.OrderRequest;
import uz.fastfood.dashboard.entity.Branch;
import uz.fastfood.dashboard.entity.Order;

@Mapper(componentModel = "spring")
public interface BranchMapper extends BaseMapper<BranchDto, Branch> {
    @Mapping(target = "id", ignore = true)
    void updateEntity(BranchDto branchDto, @MappingTarget Branch branch);
    Branch toEntity(BranchDto branchDto);
}
