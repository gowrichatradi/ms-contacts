package au.com.belong.controller;

import au.com.belong.dto.ContactsDto;
import au.com.belong.dto.CustomerDto;
import au.com.belong.dto.PhoneActivationRequest;
import au.com.belong.entity.Customer;
import au.com.belong.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class ContactsController {

    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    @PostMapping(value = "/v1/contacts/activate", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> activatePhoneNumber(@RequestBody PhoneActivationRequest activationRequest) {
        Customer customer = customerService.activatePhoneNumber(activationRequest.getId(), activationRequest.getPhoneNumber());
        return new ResponseEntity<>(modelMapper.map(customer, CustomerDto.class), HttpStatus.ACCEPTED);
    }


    @GetMapping(value = "/v1/contacts", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ContactsDto> getAllPhoneNumbers() {
        Set<String> contactNumbers = customerService.getAllContactNumbers();
        ContactsDto contactsDto = new ContactsDto(contactNumbers);
        return new ResponseEntity<>(contactsDto, HttpStatus.OK);
    }


    @GetMapping(value = "/v1/customers", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        Collection<Customer> customersList = customerService.getAllCustomers();
        List<CustomerDto> customerDtos = customersList.stream()
                .map(customer -> modelMapper.map(customer, CustomerDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(customerDtos, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/v1/customers/{customerId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> getPhoneNumberOfCustomer(@PathVariable("customerId") long customerId) {
        Customer customer = customerService.getCustomer(customerId);
        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }


}
