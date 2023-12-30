package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorsListDTO;
import guru.springfamework.controllers.v1.CustomerController;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VendorServiceImplTest {

    @InjectMocks
    private VendorServiceImpl vendorService;
    @Mock
    private VendorRepository vendorRepository;
    @Mock
    private VendorMapper vendorMapper;

    @Test
    public void getAllVendors() {
        //given
        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO1.setName("test1");
        VendorDTO vendorDTO2 = new VendorDTO();
        vendorDTO2.setName("test2");
        Vendor vendor1 = new Vendor();
        vendor1.setName("test1");
        Vendor vendor2 = new Vendor();
        vendor2.setName("test2");
        when(vendorRepository.findAll()).thenReturn(List.of(vendor1,vendor2));
        when(vendorMapper.vendorToVendorDto(vendor1)).thenReturn(vendorDTO1);
        when(vendorMapper.vendorToVendorDto(vendor2)).thenReturn(vendorDTO2);
        //when
        VendorsListDTO returnedList = vendorService.getAllVendors();
        //then
        assertEquals(2,returnedList.getVendors().size());
        assertEquals("test1",returnedList.getVendors().get(0).getName());
    }

    @Test
    public void getVendorById() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setVendorUrl(VendorController.BASE_URL + "1");
        vendorDTO.setName("test");
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(new Vendor()));
        when(vendorMapper.vendorToVendorDto(any())).thenReturn(vendorDTO);
        //when
        VendorDTO returnedDto = vendorService.getVendorById(1L);
        //then
        assertEquals("test",returnedDto.getName());
        assertEquals(VendorController.BASE_URL + "1",returnedDto.getVendorUrl());
    }

    @Test
    public void createVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("test");
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("test");
        when(vendorRepository.save(any())).thenReturn(vendor);
        when(vendorMapper.vendorToVendorDto(any())).thenReturn(vendorDTO);
        when(vendorMapper.vendorDtoToVendor(any())).thenReturn(vendor);
        //when
        VendorDTO returnedDTO = vendorService.createVendor(vendorDTO);
        //then
        assertEquals("test",returnedDTO.getName());
        assertEquals(VendorController.BASE_URL + "1",returnedDTO.getVendorUrl());
    }
    @Test
    public void updateVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("test");
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("test");
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
        when(vendorMapper.vendorToVendorDto(any())).thenReturn(vendorDTO);
        when(vendorMapper.vendorDtoToVendor(any())).thenReturn(vendor);
        //when
        VendorDTO updatedDto = vendorService.updateVendor(1L,vendorDTO);
        //then
        assertEquals("test",updatedDto.getName());
        assertEquals(VendorController.BASE_URL+"1",updatedDto.getVendorUrl());
    }

//    @Test
//    public void saveAndReturnVendor() {
//        //given
//        VendorDTO vendorDTO = new VendorDTO();
//        vendorDTO.setName("test");
//        vendorDTO.setVendorUrl(VendorController.BASE_URL + "1");
//        Vendor vendor = new Vendor();
//        vendor.setName("test");
//        vendor.setId(1L);
//        when(vendorRepository.save(any())).thenReturn(vendor);
//        when(vendorMapper.vendorToVendorDto(any())).thenReturn(vendorDTO);
//        when(vendorMapper.vendorDtoToVendor(any())).thenReturn(vendor);
//        //when
//        VendorDTO savedDto = vendorService.saveAndReturnVendor(vendor);
//        //then
//        verify(vendorRepository).save(any());
//        assertEquals("test",savedDto.getName());
//        assertEquals(VendorController.BASE_URL + "1",savedDto.getVendorUrl());
//    }

    @Test
    public void deleteVendorById() {
        vendorService.deleteVendorById(1L);

        verify(vendorRepository,times(1)).deleteById(anyLong());
    }
}