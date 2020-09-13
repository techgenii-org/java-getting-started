package com.techgenii.iac.rqrs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Login Api Request Body
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRQ {
    private String forgotPasswordToken;
    private String newPassword;
}
