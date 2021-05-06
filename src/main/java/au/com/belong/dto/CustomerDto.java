package au.com.belong.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "Customer")
public class CustomerDto {
    private long id;
    private String name;
    private Set<String> phoneNumberList;
}
