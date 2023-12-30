package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.Assert.*;

public class CustomerMapperTest {

    public static final String LASTNAME = "testLastName";
    public static final String FIRSTNAME = "testFirstName";

    CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);
    @Test
    public void customerToCustomerDto() {
        //given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setLastname(LASTNAME);
        customer.setFirstname(FIRSTNAME);
        //when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        //then
        assertEquals("1",customerDTO.getOrdersUrl());
        assertEquals(LASTNAME,customerDTO.getLastname());
        assertEquals(FIRSTNAME,customerDTO.getFirstname());
    }
}