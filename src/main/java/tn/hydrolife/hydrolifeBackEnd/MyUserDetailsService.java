package tn.hydrolife.hydrolifeBackEnd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.hydrolife.hydrolifeBackEnd.entities.User;
import tn.hydrolife.hydrolifeBackEnd.exceptions.HydroLifeException;
import tn.hydrolife.hydrolifeBackEnd.repositories.UserRepository;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        user.orElseThrow(() -> new HydroLifeException("Not found " + email));

        return user.map(MyUserDetails::new).get();

    }
}