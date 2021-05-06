package unit.au.com.belong.service.impl;

import au.com.belong.entity.Customer;
import au.com.belong.exception.ServiceException;
import au.com.belong.repository.CustomerRepository;
import au.com.belong.service.CustomerService;
import au.com.belong.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;


    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository);
    }


    @AfterEach
    void tearDown() {
        customerService = null;
    }


    @Test
    void getCustomerReturnsSuccessWhenCustomerDataHasValues() {
        Mockito.when(customerRepository.getCustomerData()).thenReturn(mockedCustomerData());
        Customer customer = customerService.getCustomer(1);

        assertThat(customer)
                .isNotNull()
                .isInstanceOf(Customer.class)
                .extracting(Customer::getPhoneNumberList)
                .hasNoNullFieldsOrProperties()
                .matches(phoneNumbers -> phoneNumbers.size() == 3)
                .matches(phoneNumbers -> phoneNumbers.contains("0407796161"));

    }


    @Test
    void getCustomerFailsWhenCustomerDataHasNoValues() {
        Mockito.when(customerRepository.getCustomerData()).thenReturn(new HashMap<>());

        assertThatThrownBy(() -> customerService.getCustomer(1))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("Customer with ID 1 not found")
                .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND)
                .hasFieldOrPropertyWithValue("errorCode", "RJCT");

    }


    @Test
    void getAllCustomersReturnsSuccessWhenCustomerDataHasValues() {

        Mockito.when(customerRepository.getCustomerData()).thenReturn(mockedCustomerData());
        Collection<Customer> customers = customerService.getAllCustomers();

        assertThat(customers)
                .isNotNull()
                .isInstanceOf(Collection.class)
                .hasOnlyElementsOfType(Customer.class)
                .hasSize(10)
                .doesNotContainNull();

    }


    @Test
    void getAllCustomersFailWhenCustomerDataIsEmpty() {
        Mockito.when(customerRepository.getCustomerData()).thenReturn(new HashMap<>());

        assertThatThrownBy(() -> customerService.getAllCustomers())
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("Customers list is empty")
                .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND)
                .hasFieldOrPropertyWithValue("errorCode", "RJCT");

    }


    @Test
    void getAllPhoneNumbersReturnsSuccessWhenCustomerDataHasValues() {

        Mockito.when(customerRepository.getCustomerData()).thenReturn(mockedCustomerData());
        Set<String> contactNumbers = customerService.getAllContactNumbers();

        assertThat(contactNumbers)
                .isNotNull()
                .isInstanceOf(Set.class)
                .hasOnlyElementsOfType(Set.class)
                .hasSize(30)
                .doesNotContainNull();
    }


    @Test
    void getAllPhoneNumbersFailWhenCustomerDataIsEmpty() {

        Mockito.when(customerRepository.getCustomerData()).thenReturn(new HashMap<>());

        assertThatThrownBy(() -> customerService.getAllContactNumbers())
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("Phone number list is empty")
                .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND)
                .hasFieldOrPropertyWithValue("errorCode", "RJCT");

    }


    @Test
    void activatePhoneNumberIsSuccessWhenCustomerFound() {

        Mockito.when(customerRepository.getCustomerData()).thenReturn(mockedCustomerData());
        Customer customer = customerService.activatePhoneNumber(1, "1234567");

        assertThat(customer)
                .isNotNull()
                .isInstanceOf(Customer.class)
                .extracting(Customer::getPhoneNumberList)
                .hasNoNullFieldsOrProperties()
                .matches(phoneNumbers -> phoneNumbers.size() == 4)
                .matches(phoneNumbers -> phoneNumbers.contains("1234567"));

    }


    @Test
    void activatePhoneNumberFailsWhenCustomerNotFound() {

        Mockito.when(customerRepository.getCustomerData()).thenReturn(new HashMap<>());

        assertThatThrownBy(() -> customerService.activatePhoneNumber(1, "456788999"))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("Customer with ID 1 not found")
                .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND)
                .hasFieldOrPropertyWithValue("errorCode", "RJCT");

    }

    private Map<Long, Customer> mockedCustomerData() {

        Map<Long, Customer> customerData = new HashMap<>();
        for (long i = 0; i < 10; i++) {
            customerData.put(i, Customer.builder()
                    .id(i)
                    .name("MockedCustomerName-" + i)
                    .phoneNumberList(Stream.of("040779616" + i, "030779616" + i, "031779616" + i).collect(Collectors.toSet()))
                    .build()
            );
        }

        return customerData;
    }
}