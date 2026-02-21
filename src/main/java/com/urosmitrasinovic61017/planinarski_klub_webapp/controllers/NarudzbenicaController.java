package com.urosmitrasinovic61017.planinarski_klub_webapp.controllers;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Klub;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Korisnik;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Narudzbenica;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.KlubService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.KorisnikService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.NarudzbenicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class NarudzbenicaController {


    @Autowired
    private NarudzbenicaService narudzbenicaService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private KlubService klubService;

    private final long klubId = 192450723;


    //PREGLED KORISNIKOVIH NARUDZBENICA
    @RequestMapping(value = "/korisnik/pregled-porudzbina")
    public String pregledPorudzbinaKorisnikaShowPage(Model model, Authentication authentication){

        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        String emailKorisnika = authentication.getName();
        Korisnik korisnik = this.korisnikService.findByEmail(emailKorisnika).get();

        //uzeti sve narudzbenice u poslednjih 30 dana, i order by most recent
        List<Narudzbenica> listaSvihNarudzbenicaUPoslednjihMesecDana = this.narudzbenicaService.getAllNarudzbeniceKorisnikaUPoslednjihMesecDana(korisnik.getKorisnikId());

        model.addAttribute("listaSvihNarudzbenicaUPoslednjihMesecDana", listaSvihNarudzbenicaUPoslednjihMesecDana);

        List<Narudzbenica> listaSvihNarudzbenicaStarijihOdMesecDana = this.narudzbenicaService.getAllNarudzbeniceKorisnikaStarijihOdMesecDana(korisnik.getKorisnikId());

        model.addAttribute("listaSvihNarudzbenicaStarijihOdMesecDana", listaSvihNarudzbenicaStarijihOdMesecDana);

        return "narudzbenica/pregledNarudzbenicaKorisnika";


    }

}
