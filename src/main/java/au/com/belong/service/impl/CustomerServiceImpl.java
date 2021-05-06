package au.com.belong.service.impl;

import au.com.belong.entity.Customer;
import au.com.belong.exception.ServiceException;
import au.com.belong.repository.CustomerRepository;
import au.com.belong.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;


    @Override
    public Customer getCustomer(long customerId) {
        return customerRepository.getCustomerData().entrySet().stream()
                .filter(entry -> customerId == entry.getKey())
                .findFirst()
                .map(Map.Entry::getValue)
                .orElseThrow(() -> new ServiceException("Customer with ID " + customerId + " not found", "RJCT", HttpStatus.NOT_FOUND));
    }

    @Override
    public Set<String> getAllContactNumbers() {
        Set<String> phoneNumbers = customerRepository.getCustomerData().entrySet()
                .parallelStream()
                .flatMap(entry -> entry.getValue().getPhoneNumberList().stream())
                .collect(Collectors.toSet());

        if (phoneNumbers.isEmpty()) {
            throw new ServiceException("Phone number list is empty", "RJCT", HttpStatus.NOT_FOUND);
        }

        return phoneNumbers;
    }

    @Override
    public Collection<Customer> getAllCustomers() {
        Collection<Customer> customerList = customerRepository.getCustomerData().values();
        if (customerList.isEmpty()) {
            throw new ServiceException("Customers list is empty", "RJCT", HttpStatus.NOT_FOUND);
        }

        return customerList;
    }

    @Override
    public Customer activatePhoneNumber(long customerId, String phoneNumber) {
        Customer customer = getCustomer(customerId);
        customer.getPhoneNumberList().add(phoneNumber);
        return customer;
    }
}
