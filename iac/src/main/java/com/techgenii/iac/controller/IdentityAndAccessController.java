package com.techgenii.iac.controller;


import com.techgenii.iac.rqrs.LoginRQ;
import com.techgenii.iac.rqrs.LoginRS;
import com.techgenii.iac.rqrs.RegisterUserRQ;
import com.techgenii.iac.rqrs.ResetPasswordRQ;
import com.techgenii.iac.service.IdentityAndAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/iac")
public class IdentityAndAccessController {

    @Autowired
    private IdentityAndAccessService identityAndAccessService;

    @PostMapping("/login")
    public ResponseEntity<LoginRS> login(@RequestBody LoginRQ loginRQ) {

        return ResponseEntity.ok(identityAndAccessService.login(loginRQ)) ;
    }

    @GetMapping(value ="/me", produces = "application/json")
    public ResponseEntity<Object> getMe(@RequestHeader("x-auth-token") String token) {
        return ResponseEntity.ok(identityAndAccessService.getMe(token));
    }

    @GetMapping(value ="/forgot/password", produces = "application/json")
    public ResponseEntity<Object> forgotPassword(String email) {
        return ResponseEntity.ok(identityAndAccessService.forgotPassword(email));
    }

    @PostMapping(value ="/reset/password", produces = "application/json")
    public ResponseEntity<LoginRS> resetPassword(@RequestBody ResetPasswordRQ resetPasswordRQ) {
        return ResponseEntity.ok(identityAndAccessService.resetPassword(resetPasswordRQ));
    }

    @PostMapping(value ="/register/user", produces = "application/json")
    public ResponseEntity<LoginRS> registerUser(@RequestBody RegisterUserRQ registerUserRQ) {
        return ResponseEntity.ok(identityAndAccessService.registerUser(registerUserRQ));
    }

}
