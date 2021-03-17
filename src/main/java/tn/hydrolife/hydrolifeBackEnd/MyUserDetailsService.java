package tn.hydrolife.hydrolifeBackEnd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.hydrolife.hydrolifeBackEnd.entities.Centre;
import tn.hydrolife.hydrolifeBackEnd.exceptions.HydroLifeException;
import tn.hydrolife.hydrolifeBackEnd.repositories.CentreRepository;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    CentreRepository centreRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Centre> centre = centreRepository.findByEmail(email);

        centre.orElseThrow(()-> new HydroLifeException("Not found "+ email));

        return centre.map(MyUserDetails::new).get();

    }
}