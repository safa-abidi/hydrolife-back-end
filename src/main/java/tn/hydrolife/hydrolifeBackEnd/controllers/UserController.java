package tn.hydrolife.hydrolifeBackEnd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tn.hydrolife.hydrolifeBackEnd.MyUserDetails;
import tn.hydrolife.hydrolifeBackEnd.MyUserDetailsService;
import tn.hydrolife.hydrolifeBackEnd.models.AuthenticationRequest;
import tn.hydrolife.hydrolifeBackEnd.models.AuthenticationResponse;
import tn.hydrolife.hydrolifeBackEnd.repositories.UserRepository;
import tn.hydrolife.hydrolifeBackEnd.util.JwtUtil;


@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;

    public UserRepository userRepository;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authenticate);

        } catch (BadCredentialsException e) {
            throw new Exception("incorrect email or password", e);
        }
        final MyUserDetails myUserDetails = (MyUserDetails) userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtTokenUtil.generateToken(myUserDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt, authenticationRequest.getEmail()));
    }
}
