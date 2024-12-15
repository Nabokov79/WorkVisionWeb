package ru.nabokovsg.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.company.mapper.BranchMapper;
import ru.nabokovsg.company.repository.BranchRepository;
import ru.nabokovsg.company.dto.branch.NewBranchDto;
import ru.nabokovsg.company.dto.branch.ResponseBranchDto;
import ru.nabokovsg.company.dto.branch.ResponseShortBranchDto;
import ru.nabokovsg.company.dto.branch.UpdateBranchDto;
import ru.nabokovsg.company.exceptions.BadRequestException;
import ru.nabokovsg.company.exceptions.NotFoundException;
import ru.nabokovsg.company.model.Branch;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository repository;
    private final BranchMapper mapper;
    private final OrganizationService organizationService;
    private final AddressService addressService;

    @Override
    public ResponseShortBranchDto save(NewBranchDto branchDto) {
        if (repository.existsByFullName(branchDto.getFullName())) {
            throw new BadRequestException(String.format("Branch with full name=%s is found", branchDto.getFullName()));
        }
        return mapper.mapToShortBranchDto(repository.save(
                map(mapper.mapToBranch(branchDto)
                        , branchDto.getOrganizationId()
                        , branchDto.getAddressId()))
        );
    }

    @Override
    public ResponseShortBranchDto update(UpdateBranchDto branchDto) {
        if (repository.existsById(branchDto.getId())) {
            return mapper.mapToShortBranchDto(repository.save(
                    map(mapper.mapToUpdateBranch(branchDto)
                            , branchDto.getOrganizationId()
                            , branchDto.getAddressId())));

        }
        throw new NotFoundException(
                               String.format("Branch wth name=%s not found for update", branchDto.getFullName()));
    }

    @Override
    public ResponseBranchDto get(Long id) {
        return mapper.mapToBranchDto(getById(id));
    }

    @Override
    public List<ResponseShortBranchDto> getAll(Long id, String name) {
        if (id != null && id > 0) {
            return repository.findAllByOrganizationId(id)
                             .stream()
                             .map(mapper::mapToShortBranchDto)
                             .toList();
        }
        List<Branch> branches = repository.findAll();
        if (name != null) {
            String fullName = name.toLowerCase();
            branches = branches.stream()
                               .filter(v -> v.getFullName().toLowerCase().contains(fullName))
                               .toList();
        }
        return branches.stream()
                       .map(mapper::mapToShortBranchDto)
                       .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Branch wth id=%s not found for delete", id));
    }

    @Override
    public Branch getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Branch wth id=%s not found", id)));

    }

    private Branch map(Branch branch, Long organizationId, Long addressId) {
        return mapper.mapWithData(branch
                                , addressService.getAddressString(addressId)
                                , organizationService.getById(organizationId));
    }
}