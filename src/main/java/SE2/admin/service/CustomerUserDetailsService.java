package SE2.admin.service;

import SE2.admin.entity.User;
import SE2.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepo.findByEmail(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return new CustomUserDetails(user);
    }
}
