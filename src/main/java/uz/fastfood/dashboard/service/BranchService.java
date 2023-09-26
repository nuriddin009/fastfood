package uz.fastfood.dashboard.service;

import uz.fastfood.dashboard.dto.request.BranchDto;
import uz.fastfood.dashboard.dto.response.ApiResponse;
import uz.fastfood.dashboard.dto.response.BaseResponse;
import uz.fastfood.dashboard.entity.Branch;

import java.util.UUID;

public interface BranchService {
    ApiResponse getBranches(String search, Integer page);
    ApiResponse delete(UUID branchId);
    ApiResponse updateBranch(BranchDto branchDto);
    BaseResponse<Branch> createBranch(BranchDto branchDto,BaseResponse<Branch> response);
}
