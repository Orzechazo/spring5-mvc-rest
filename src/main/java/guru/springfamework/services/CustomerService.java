package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long customerId);

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);
    CustomerDTO saveCustomerByDto(Long Id, CustomerDTO customerDTO);
    CustomerDTO patchCustomer (Long id, CustomerDTO customerDTO);
    void deleteCustomerById(Long id);
}
