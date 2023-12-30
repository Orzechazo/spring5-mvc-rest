package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorsListDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.ResourceNotFoundException;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(MockitoJUnitRunner.class)
public class VendorControllerTest {

    @InjectMocks
    private VendorController vendorController;
    @Mock
    private VendorService vendorService;

    private MockMvc mockMvc;
    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getAllVendors() throws Exception {
        VendorsListDTO vendorsListDTO = new VendorsListDTO();
        vendorsListDTO.setVendors(List.of(new VendorDTO(),new VendorDTO()));
        when(vendorService.getAllVendors()).thenReturn(vendorsListDTO);

        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }
    @Test
    public void getVendorById() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("test");
        vendorDTO.setVendorUrl(VendorController.BASE_URL + "1");
        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);

        mockMvc.perform(get(VendorController.BASE_URL + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo("test")))
                .andExpect(jsonPath("$.vendorUrl",equalTo(VendorController.BASE_URL + "1")));
    }
    @Test
    public void getVendorByIdNotFound() throws Exception {
        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(VendorController.BASE_URL + "1234")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateVendor() throws Exception{
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setVendorUrl(VendorController.BASE_URL + "5");
        vendorDTO.setName("test");
        when(vendorService.createVendor(any())).thenReturn(vendorDTO);

        mockMvc.perform(put(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",equalTo("test")))
                .andExpect(jsonPath("$.vendorUrl",equalTo(VendorController.BASE_URL + "5")));
    }
    @Test
    public void updateVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("updated");
        vendorDTO.setVendorUrl(VendorController.BASE_URL+"3");
        Long id = 3L;
        when(vendorService.updateVendor(anyLong(),any())).thenReturn(vendorDTO);

        mockMvc.perform(post(VendorController.BASE_URL + "3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo("updated")))
                .andExpect(jsonPath("$.vendorUrl",equalTo(VendorController.BASE_URL + "3")));
    }

    @Test
    public void testPatchVendor() throws Exception{
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("patched");
        vendorDTO.setVendorUrl(VendorController.BASE_URL+"4");
        Long id = 4L;
        when(vendorService.patchVendor(anyLong(),any())).thenReturn(vendorDTO);

        mockMvc.perform(patch(VendorController.BASE_URL + "4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo("patched")))
                .andExpect(jsonPath("$.vendorUrl",equalTo(VendorController.BASE_URL + "4")));
    }

    @Test
    public void testDeleteVendor() throws Exception {

        mockMvc.perform(delete(VendorController.BASE_URL + "7"))
                .andExpect(status().isOk());

        verify(vendorService,times(1)).deleteVendorById(anyLong());
    }
}