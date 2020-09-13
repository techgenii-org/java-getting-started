package com.techgenii.iac.rqrs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRQ {

    private String username;
    private String password;
    private String email;
}
