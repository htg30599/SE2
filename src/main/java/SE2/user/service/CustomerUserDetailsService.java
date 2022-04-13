package SE2.user.service;

import SE2.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import SE2.user.model.User;

public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepo.findByEmail(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return new CustomerUserDetails(user);

    }
}
