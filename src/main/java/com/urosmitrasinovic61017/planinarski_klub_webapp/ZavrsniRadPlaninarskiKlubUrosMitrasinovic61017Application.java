package com.urosmitrasinovic61017.planinarski_klub_webapp;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Korisnik;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Uloga;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.KorisnikRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.UlogaRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.KorisnikService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.UlogaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class ZavrsniRadPlaninarskiKlubUrosMitrasinovic61017Application implements CommandLineRunner {

    @Autowired
    private UlogaService ulogaService;

    @Autowired
    private KorisnikService korisnikService;

    public static void main(String[] args) {
        SpringApplication.run(ZavrsniRadPlaninarskiKlubUrosMitrasinovic61017Application.class, args);
    }



    @Override
    public void run(String... args) throws Exception {

       //pravljenje i insert korisnika u bazu

        /*Korisnik korisnik1 = new Korisnik();
        korisnik1.setIme("Stefan");
        korisnik1.setPrezime("Petrovic");
        korisnik1.setEmail("admin1@gmail.com");
        korisnik1.setPassword("123456");
        korisnik1.setBrojTelefona("0642223331");
        korisnik1.setClanKluba("NE");

        Uloga adminUloga = this.ulogaService.findByNaziv("ADMIN").get();
        korisnik1.setUloga(adminUloga);

        this.korisnikService.save(korisnik1);*/


        //sledece testirati email validator regex, i mozda staviti u nasu custom validaciju
        //da proverava da li postoji vec korisnik sa tim email-om

        /*LocalDateTime myDateObj = LocalDateTime.now();
        System.out.println(myDateObj);*/








    }
}
