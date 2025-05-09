package ro.amihalcea.ecommerce_app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.amihalcea.ecommerce_app.model.User;
import ro.amihalcea.ecommerce_app.model.UserPrincipal;
import ro.amihalcea.ecommerce_app.repository.UserRepository;

@Service
@Slf4j
public class UserDetailsServiceImp implements UserDetailsService {


    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user== null){
            log.warn("User not found by username '{}'",username);
            throw new UsernameNotFoundException("User not found.");
        }

        log.info("User with id {} found.\n",user.getId());

        return new UserPrincipal(user);
    }
}
