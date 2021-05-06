package au.com.belong.service;

import au.com.belong.entity.Customer;

import java.util.Collection;
import java.util.Set;

public interface CustomerService {

    Customer getCustomer(long customerId);

    Set<String> getAllContactNumbers();

    Collection<Customer> getAllCustomers();

    Customer activatePhoneNumber(long customerId, String phonenumber);


}
