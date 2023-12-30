package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controllers.v1.CustomerController;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDto)
                .peek(customerDTO -> customerDTO.setOrdersUrl("/api/v1/customers/" + customerDTO.getOrdersUrl()))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(ResourceNotFoundException::new);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setOrdersUrl(getCustomerUrl(customerId));
        return customerDTO;
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return saveAndReturnDto(customerMapper.customerDtoToCustomer(customerDTO));

    }
    private CustomerDTO saveAndReturnDto(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO returnedCustomerDto = customerMapper.customerToCustomerDto(savedCustomer);
        returnedCustomerDto.setOrdersUrl(getCustomerUrl(customer.getId()));
        return returnedCustomerDto;
    }

    @Override
    public CustomerDTO saveCustomerByDto(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);
        return saveAndReturnDto(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {

            if(customerDTO.getFirstname() != null){
                customer.setFirstname(customerDTO.getFirstname());
            }

            if(customerDTO.getLastname() != null){
                customer.setLastname(customerDTO.getLastname());
            }
            CustomerDTO returnedDTO = customerMapper.customerToCustomerDto(
                    customerRepository.save(customer));
            returnedDTO.setOrdersUrl(getCustomerUrl(id));

            return returnedDTO;
        }).orElseThrow(RuntimeException::new); //todo implement better exception handling;
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
    private String getCustomerUrl(Long id) {
        return CustomerController.BASE_URL + id;
    }
}
