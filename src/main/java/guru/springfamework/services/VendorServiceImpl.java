package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorsListDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public VendorsListDTO getAllVendors() {
        List<Vendor> vendorList = vendorRepository.findAll();
        List<VendorDTO> vendorDtoList = vendorList.stream()
                .map(vendorMapper::vendorToVendorDto)
                .toList();
        VendorsListDTO vendorsListDTO = new VendorsListDTO();
        vendorsListDTO.setVendors(vendorDtoList);
        return vendorsListDTO;
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        Vendor vendor = vendorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(vendor);
        vendorDTO.setVendorUrl(getVendorUrl(id));
        return vendorDTO;
    }

    @Override
    public VendorDTO createVendor(VendorDTO vendorDTO) {
        Vendor vendorToSave = vendorMapper.vendorDtoToVendor(vendorDTO);
        return saveAndReturnVendor(vendorToSave);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (vendorDTO.getName() != null) {
            vendor.setName(vendorDTO.getName());
        }
        VendorDTO savedVendor = vendorMapper.vendorToVendorDto(vendorRepository.save(vendor));
        savedVendor.setVendorUrl(getVendorUrl(vendor.getId()));
        return savedVendor;
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
        vendor.setId(id);
        return saveAndReturnVendor(vendor);
    }

    @Override
    public VendorDTO saveAndReturnVendor(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO savedDto = vendorMapper.vendorToVendorDto(savedVendor);
        savedDto.setVendorUrl(getVendorUrl(savedVendor.getId()));
        return savedDto;
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    @Override
    public String getVendorUrl(Long id) {
        return VendorController.BASE_URL + id;
    }
}
