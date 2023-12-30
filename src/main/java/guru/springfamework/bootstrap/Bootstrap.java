package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    CategoryRepository categoryRepository;
    CustomerRepository customerRepository;
    VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository,
                     VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadCustomers() {
        Customer jeff = new Customer();
        jeff.setFirstname("Jeff");
        jeff.setLastname("Buckley");

        Customer leo = new Customer();
        leo.setFirstname("Leonard");
        leo.setLastname("Cohen");

        Customer jack = new Customer();
        jack.setFirstname("Jack");
        jack.setLastname("Ripper");

        customerRepository.save(jeff);
        customerRepository.save(leo);
        customerRepository.save(jack);

        System.out.println("Customers loaded = " + customerRepository.count());
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Categories loaded = " + categoryRepository.count());
    }


    private void loadVendors() {
        Vendor nut = new Vendor();
        nut.setName("TheNuts");
        Vendor cat = new Vendor();
        cat.setName("TheCats");
        vendorRepository.save(nut);
        vendorRepository.save(cat);

        System.out.println("Vendors loaded = " + vendorRepository.count());
    }
}
