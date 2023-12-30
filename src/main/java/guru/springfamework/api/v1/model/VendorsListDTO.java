package guru.springfamework.api.v1.model;

import lombok.Data;

import java.util.List;

@Data
public class VendorsListDTO {
    List<VendorDTO> vendors;
}
