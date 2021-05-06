package au.com.belong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class Customer {
    private long id;
    private String name;
    private Set<String> phoneNumberList;
}
