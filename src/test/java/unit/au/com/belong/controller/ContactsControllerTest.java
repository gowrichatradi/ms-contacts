package unit.au.com.belong.controller;

import au.com.belong.controller.ContactsController;
import au.com.belong.dto.ContactsDto;
import au.com.belong.dto.CustomerDto;
import au.com.belong.dto.PhoneActivationRequest;
import au.com.belong.entity.Customer;
import au.com.belong.exception.ServiceException;
import au.com.belong.service.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class ContactsControllerTest {

    ContactsController contactsController;

    @Mock
    CustomerService customerService;

    @Autowired
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        contactsController = new ContactsController(customerService, modelMapper);
    }

    @AfterEach
    void tearDown() {
        contactsController = null;
    }


    @Test
    void return2xxWhenDataIsNotEmpty() {
        Customer randomCustomer = getRandomCustomer();
        Mockito.when(customerService.getAllCustomers()).thenReturn(List.of(randomCustomer));

        ResponseEntity<List<CustomerDto>> allCustomers = contactsController.getAllCustomers();
        Assertions.assertThat(allCustomers).isNotNull()
                .extracting(ResponseEntity::getStatusCode)
                .matches(HttpStatus::is2xxSuccessful);
    }

    @Test
    void return4xxWhenDataIsEmpty() {
        Mockito.when(customerService.getAllCustomers()).thenThrow(new ServiceException("random data", "rjct", HttpStatus.NOT_FOUND));
        Assertions.assertThatThrownBy(() -> contactsController.getAllCustomers()).isInstanceOf(ServiceException.class)
                .hasMessageContaining("random data")
                .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);

    }

    @Test
    void return2xxWhenPhoneNumbersNotEmpty() {
        Mockito.when(customerService.getAllContactNumbers()).thenReturn(Set.of("0407796163", "0407796164"));
        ResponseEntity<ContactsDto> allPhoneNumbers = contactsController.getAllPhoneNumbers();
        Assertions.assertThat(allPhoneNumbers)
                .isNotNull()
                .matches(response -> response.getBody().getPhoneNumberList().contains("0407796164"))
                .extracting(ResponseEntity::getStatusCode)
                .matches(HttpStatus::is2xxSuccessful);

    }


    @Test
    void return2xxWhenCustomerExists() {
        Customer randomCustomer = getRandomCustomer();
        Mockito.when(customerService.getCustomer(1)).thenReturn(randomCustomer);

        ResponseEntity<CustomerDto> allCustomers = contactsController.getPhoneNumberOfCustomer(1);
        Assertions.assertThat(allCustomers).isNotNull()
                .extracting(ResponseEntity::getStatusCode)
                .matches(HttpStatus::is2xxSuccessful);
    }


    @Test
    void return2xxWhenActivatePhoneNumber() {
        Customer randomCustomer = getRandomCustomer();
        Mockito.when(customerService.activatePhoneNumber(1, "0407796163")).thenReturn(randomCustomer);
        PhoneActivationRequest activationRequest = new PhoneActivationRequest(1l, "Gowri", "0407796163");
        ResponseEntity<CustomerDto> customer = contactsController.activatePhoneNumber(activationRequest);
        Assertions.assertThat(customer).isNotNull()
                .extracting(ResponseEntity::getStatusCode)
                .matches(HttpStatus::is2xxSuccessful);
    }


    private Customer getRandomCustomer() {
        Random random = new Random(5000);
        long randomNumber = random.nextLong();
        return Customer.builder()
                .id(randomNumber)
                .name("CustomerName" + randomNumber)
                .phoneNumberList(Set.of("0408899" + randomNumber))
                .build();


    }

}