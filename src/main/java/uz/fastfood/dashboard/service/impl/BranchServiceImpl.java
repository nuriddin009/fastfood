package uz.fastfood.dashboard.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.fastfood.dashboard.dto.request.BranchDto;
import uz.fastfood.dashboard.dto.response.ApiResponse;
import uz.fastfood.dashboard.dto.response.BaseResponse;
import uz.fastfood.dashboard.entity.Branch;
import uz.fastfood.dashboard.mapper.BranchMapper;
import uz.fastfood.dashboard.projection.BranchProjection;
import uz.fastfood.dashboard.repository.BranchRepository;
import uz.fastfood.dashboard.service.BranchService;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    @Override
    public ApiResponse getBranches(String search, Integer page) {
        Page<BranchProjection> branches = branchRepository.getBranches(search, PageRequest.of(0, page - 1));
        return new ApiResponse(true, branches, "Branches");
    }

    @Override
    public ApiResponse delete(UUID branchId) {
        Branch branch = branchRepository.findById(branchId).orElseThrow(EntityNotFoundException::new);
        branch.setDeleted(true);
        branchRepository.save(branch);
        return new ApiResponse(true, String.format("%s branch deleted", branch.getNameUz()));
    }

    @Override
    public ApiResponse updateBranch(BranchDto branchDto) {
        Branch branch = branchRepository.findById(branchDto.getBranchId())
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + branchDto.getBranchId()));
        branchMapper.updateEntity(branchDto, branch);
        branchRepository.save(branch);
        return new ApiResponse(true, "Branch updated");
    }

    @Override
    public BaseResponse<Branch> createBranch(BranchDto branchDto, BaseResponse<Branch> response) {
        try {
            Branch branch = branchMapper.toEntity(branchDto);
            branchRepository.save(branch);
            response.setMessage("Branch created");
            response.setResponseData(branch);
        } catch (Exception e) {
            response.setError(true);
            response.setMessage(e.getMessage());
        }

        return response;
    }


}
