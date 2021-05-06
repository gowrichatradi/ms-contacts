package au.com.belong.repository;


import au.com.belong.entity.Customer;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@Getter
public class CustomerRepository {

    Map<Long, Customer> customerData = new HashMap<>();

    @PostConstruct
    public void initCustomerData() {
        for (long i = 0; i < 10; i++) {
            customerData.put(i, Customer.builder()
                    .id(i)
                    .name("CustomerName-" + i)
                    .phoneNumberList(Stream.of("040779616" + i, "030779616" + i, "031779616" + i).collect(Collectors.toSet()))
                    .build());
        }
    }


}
