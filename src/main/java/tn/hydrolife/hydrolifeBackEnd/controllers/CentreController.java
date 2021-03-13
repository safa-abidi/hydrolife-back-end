package tn.hydrolife.hydrolifeBackEnd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.hydrolife.hydrolifeBackEnd.MyUserDetails;
import tn.hydrolife.hydrolifeBackEnd.MyUserDetailsService;
import tn.hydrolife.hydrolifeBackEnd.entities.Centre;
import tn.hydrolife.hydrolifeBackEnd.models.AuthenticationRequest;
import tn.hydrolife.hydrolifeBackEnd.models.AuthenticationResponse;
import tn.hydrolife.hydrolifeBackEnd.services.CentreService;
import tn.hydrolife.hydrolifeBackEnd.util.JwtUtil;

import java.util.List;

@RestController
@RequestMapping("/centre")
public class CentreController {
    private final CentreService centreService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;

    //constructor
    public CentreController(CentreService centreService) {
        this.centreService = centreService;
    }

    //trouver tous les centres
    @GetMapping("/all")
    public ResponseEntity<List<Centre>> getAllCentres(){
        List<Centre> centres = centreService.findAllCentres();
        return new ResponseEntity<>(centres, HttpStatus.OK);
    }

    //trouver un centre avec id
    @GetMapping("/find/{id}")
    public ResponseEntity<Centre> getCentreById(@PathVariable("id") Long id){
        Centre centre = centreService.findCentre(id);
        return new ResponseEntity<Centre>(centre, HttpStatus.OK);
    }


//    //trouver un centre avec email
//    @GetMapping("/findbyemail/{email}")
//    public ResponseEntity<Centre> getCentreByEmail(@PathVariable("email") String email){
//        Centre centre = centreService.findCentreByEmail(email);
//        return new ResponseEntity<Centre>(centre, HttpStatus.OK);
//    }

    //ajouter un centre
    @PostMapping("/add")
    public ResponseEntity<Centre> addCentre(@RequestBody Centre centre){
        //password encoding
        centre.setPassword(new BCryptPasswordEncoder().encode(centre.getPassword()));

        Centre newCentre = centreService.addCentre(centre);
        return new ResponseEntity<>(newCentre, HttpStatus.CREATED);
    }

    //modifier un centre
    @PutMapping("/update")
    public ResponseEntity<Centre> updateCentre(@RequestBody Centre centre){
        Centre updateCentre = centreService.updateCentre(centre);
        return new ResponseEntity<>(updateCentre, HttpStatus.OK);
    }

    //supprimer un centre
    @PreAuthorize("hasRole('CENTRE')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCentre(@PathVariable("id") Long id){
        centreService.deleteCentre(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //authentication endpoint
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
                );

        }catch(BadCredentialsException e){
            throw new Exception("incorrect email or password", e);
        }
        final MyUserDetails myUserDetails = (MyUserDetails) userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtTokenUtil.generateToken(myUserDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
