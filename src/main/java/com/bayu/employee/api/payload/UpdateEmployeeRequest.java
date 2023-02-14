package com.bayu.employee.api.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeRequest {

    @NotBlank(message = "NIK must not be blank")
    @Size(max = 10, message = "NIK length maximum must be 10 characters")
    private String nik;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @Email(message = "Email must be formatted")
    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "Address must not be blank")
    private String address;
}
