package com.ecommerce.fauzi.seeder;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ecommerce.fauzi.model.Role;
import com.ecommerce.fauzi.model.User;
import com.ecommerce.fauzi.repository.JpaAuthRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DummyDataSeeder implements CommandLineRunner{
    
    private final JpaAuthRepository repository;

    @Override
    public void run(String... args) throws Exception {
        if (repository.findByEmail("seller@example.com").isEmpty()) {
            User dummyUser = new User();
            dummyUser.setId(UUID.randomUUID());
            dummyUser.setName("Seller");
            dummyUser.setEmail("seller@example.com");
            dummyUser.setPassword("$2a$12$910VgITRLhNjXRCFKCaONuELxRIrfksRHMOF2EUxYJf1fFTFA6JW2");
            dummyUser.setRole(Role.ROLE_SELLER);

            repository.save(dummyUser);
        }
    }
}
