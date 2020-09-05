package com.techgenii.iac.controller;


import com.techgenii.iac.rqrs.LoginRQ;
import com.techgenii.iac.rqrs.LoginRS;
import com.techgenii.iac.rqrs.TokenDTO;
import com.techgenii.iac.service.IdentityAndAccessService;
import com.techgenii.iac.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/iac")
public class IdentityAndAccessController {

    @Autowired
    private IdentityAndAccessService identityAndAccessService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginRS> login(@RequestBody LoginRQ loginRQ) {

        return ResponseEntity.ok(identityAndAccessService.login(loginRQ)) ;
    }

    @PostMapping("/getDetail")
    public ResponseEntity getDetail(@RequestBody TokenDTO tokenDTO) {
        return ResponseEntity.ok(jwtUtil.parseToken(tokenDTO.getToken()));
    }
}
