package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendorMapperTest {

    VendorMapper vendorMapper = VendorMapper.INSTANCE;
    @Test
    public void vendorToVendorDto() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("test");
        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(vendor);
        //then
        assertEquals("test",vendorDTO.getName());
        assertEquals("1",vendorDTO.getVendorUrl());
    }

    @Test
    public void vendorDtoToVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setVendorUrl("1");
        vendorDTO.setName("test");
        //when
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
        //then
        assertEquals("test",vendor.getName());
    }
}