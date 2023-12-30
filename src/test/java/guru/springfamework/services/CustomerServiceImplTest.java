package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
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
public class CustomerServiceImplTest {

    public static final String FIRSTNAME = "testFirstName";
    public static final String LASTNAME = "testLastName";
    @InjectMocks
    CustomerServiceImpl customerService;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    CustomerMapper customerMapper;
    @Test
    public void getAllCustomers() {
        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstname(FIRSTNAME);
        customer1.setLastname(LASTNAME);
        when(customerRepository.findAll()).thenReturn(List.of(new Customer(),new Customer()));
        when(customerMapper.customerToCustomerDto(any())).thenReturn(customer1);
        //when
        List<CustomerDTO> customers = customerService.getAllCustomers();
        //then
        assertEquals(2,customers.size());
        assertEquals(FIRSTNAME,customers.get(0).getFirstname());
        assertEquals(LASTNAME,customers.get(1).getLastname());
    }

    @Test
    public void getCustomerById() {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setLastname(LASTNAME);
        customer.setFirstname(FIRSTNAME);
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer()));
        when(customerMapper.customerToCustomerDto(any())).thenReturn(customer);
        //when
        CustomerDTO foundCustomer = customerService.getCustomerById(3L);
        //then
        assertEquals(FIRSTNAME,foundCustomer.getFirstname());
        assertEquals(LASTNAME,foundCustomer.getLastname());
    }

    @Test
    public void createCustomerTest() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);
        customerDTO.setOrdersUrl("1");
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerMapper.customerDtoToCustomer(any())).thenReturn(customer);
        when(customerRepository.save(any())).thenReturn(customer);
        when(customerMapper.customerToCustomerDto(any())).thenReturn(customerDTO);
        //when
        CustomerDTO returnedCustomerDTO = customerService.createNewCustomer(customerDTO);
        //then
        assertEquals(FIRSTNAME,returnedCustomerDTO.getFirstname());
        assertEquals(LASTNAME,returnedCustomerDTO.getLastname());
        assertEquals("/api/v1/customers/1",returnedCustomerDTO.getOrdersUrl());
    }

    @Test
    public void saveCustomerByDto() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        when(customerMapper.customerDtoToCustomer(any())).thenReturn(savedCustomer);
        when(customerMapper.customerToCustomerDto(any())).thenReturn(customerDTO);
        when(customerRepository.save(any())).thenReturn(savedCustomer);
        //when
        CustomerDTO savedCustomerDto = customerService.saveCustomerByDto(1L,customerDTO);
        //then
        assertEquals(FIRSTNAME,savedCustomerDto.getFirstname());
        assertEquals("/api/v1/customers/1",savedCustomerDto.getOrdersUrl());

    }

    @Test
    public void testDeleteCustomerById() throws Exception {
        Long id = 1L;
        customerService.deleteCustomerById(id);

        verify(customerRepository,times(1)).deleteById(anyLong());
    }
}