package com.urosmitrasinovic61017.planinarski_klub_webapp.config;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Korisnik;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private KorisnikRepository korisnikRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //u nasem slucaju username je zapravo email
        Optional<Korisnik> korisnik = this.korisnikRepository.findByEmail(email);

        korisnik.orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));

        //sada treba da od korisnika napravimo objekat UserDetails i vratimo
        return korisnik.map(MyUserDetails::new).get();


    }
}
