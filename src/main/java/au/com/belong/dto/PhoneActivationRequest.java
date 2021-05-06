package au.com.belong.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneActivationRequest {
    @NotNull
    private Long id;
    private String customerName;
    @NotNull
    private String phoneNumber;
}
