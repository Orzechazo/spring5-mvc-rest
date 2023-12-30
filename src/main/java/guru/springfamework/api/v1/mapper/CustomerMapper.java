package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    @Mapping(target = "ordersUrl", source = "id")
    CustomerDTO customerToCustomerDto(Customer customer);

//    @Mapping(target = "id", source = "ordersUrl")
    Customer customerDtoToCustomer(CustomerDTO customerDTO);
}
