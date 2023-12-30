package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorsListDTO;
import guru.springfamework.domain.Vendor;

public interface VendorService {
    VendorsListDTO getAllVendors();
    VendorDTO getVendorById(Long id);
    VendorDTO createVendor(VendorDTO vendorDTO);
    VendorDTO patchVendor(Long id,VendorDTO vendorDTO);
    VendorDTO updateVendor(Long id, VendorDTO vendorDTO);
    VendorDTO saveAndReturnVendor(Vendor vendor);
    void deleteVendorById(Long id);
    String getVendorUrl(Long id);
}
