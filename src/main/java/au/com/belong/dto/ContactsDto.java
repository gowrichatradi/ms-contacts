package au.com.belong.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactsDto {
    @JsonProperty(value = "phoneNumbers")
    private Set<String> phoneNumberList;
}
