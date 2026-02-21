package com.urosmitrasinovic61017.planinarski_klub_webapp.controllers;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.*;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.KlubService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.KorisnikService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.PutovanjeService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.RezervacijaPutovanjeService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class RezervacijaController {

    @Autowired
    private RezervacijaPutovanjeService rezervacijaService;

    @Autowired
    private PutovanjeService putovanjeService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private KlubService klubService;

    private final long klubId = 192450723;


    @RequestMapping(value = "/admin/putovanje/rezervacijePutovanja")
    public String showAllRezervacijePutovanja(@RequestParam(value = "putovanjeId") Integer putovanjeId,
                                              Model model){
        showRezervacijePopuniModel(putovanjeId, model);

        return "rezervacija/allRezervacijePutovanja";


    }

    @RequestMapping(value = "/admin/putovanje/rezervacijePutovanjaSamoPregled")
    public String showAllRezervacijeSamoPregled(@RequestParam(value = "putovanjeId") Integer putovanjeId, Model model,
                                                HttpSession session, HttpServletRequest request){

        showRezervacijePopuniModel(putovanjeId, model);

        String referer = request.getHeader("Referer");
        session.setAttribute("previous_page_od_RezervacijeSamoPregled", referer);

        return "rezervacija/allRezervacijeSamoPregled";

    }

    private void showRezervacijePopuniModel(Integer putovanjeId, Model model) {
        Putovanje putovanje = this.putovanjeService.findById(putovanjeId).get();

        Aranzman aranzman = putovanje.getAranzman();
        model.addAttribute("putovanje", putovanje);
        model.addAttribute("aranzman", aranzman);

        List<RezervacijaPutovanja> listaRezervacija = this.rezervacijaService.getAllRezervacijePutovanja(putovanje.getPutovanjeId());

        model.addAttribute("listaRezervacija", listaRezervacija);
    }

    @RequestMapping(value = "/admin/rezervacija/delete")
    public String deleteRezervacija(@RequestParam(value = "rezervacijaId") Integer rezervacijaId,
                                    @RequestParam(value = "putovanjeId") Integer putovanjeId,
                                    RedirectAttributes redirectAttributes){
        Optional<RezervacijaPutovanja> rezervacijaOpt = this.rezervacijaService.findById(rezervacijaId);

        if (rezervacijaOpt.isPresent()) {
            RezervacijaPutovanja rezervacija = rezervacijaOpt.get();

            this.rezervacijaService.deleteById(rezervacija.getRezervacijaId());

            //posle delete-a moramo da smanjimo broj prijavljenih za 1
            this.putovanjeService.smanjiBrojPrijavljenih(putovanjeId);

            //slucaj kada je broj prijavljenih bio jednak minimum broju putnika i smanjio se, onda treba podesiti status na NEPOTVRDJENO
            boolean statusPutovanjaPromenjen = this.putovanjeService.podesavanjeStatusaPutovanjaNepotvrdjeno(rezervacija.getPutovanje());

            if(statusPutovanjaPromenjen){
                redirectAttributes.addFlashAttribute("putovanjeNepotvrdjeno", "Broj prijavljenih za ovo putovanja je postao manji od minimuma broja putnika, status putovanja (ID: " + putovanjeId + ") promenjen u NEPOTVRDJENO.");
            }


            redirectAttributes.addFlashAttribute("messageSuccess", "Uspešno ste obrisali rezervaciju za putovanje korisnika. (Korisnik ID: " + rezervacija.getKorisnik().getKorisnikId() + ", Email Kor: " + rezervacija.getKorisnik().getEmail() + ")");

        }
        else{
            redirectAttributes.addFlashAttribute("failMessage", "Rezervaciju koju ste pokušali da obrišete nema u bazi.");
        }

        redirectAttributes.addAttribute("putovanjeId", putovanjeId);
        return "redirect:/admin/putovanje/rezervacijePutovanja";

    }

    //dodaj novu rezervaciju prikazi formu
    @RequestMapping(value = "/admin/putovanje/rezervacije/addNew")
    public String showNewRezervacijaForm(@RequestParam(value = "putovanjeId") Integer putovanjeId, Model model){

        Putovanje putovanje = this.putovanjeService.findById(putovanjeId).get();
        Aranzman aranzman = putovanje.getAranzman();

        model.addAttribute("putovanje", putovanje);
        model.addAttribute("aranzman", aranzman);

        RezervacijaPutovanja novaRezervacija = new RezervacijaPutovanja();
        novaRezervacija.setPutovanje(putovanje);

        model.addAttribute("novaRezervacija", novaRezervacija);

        return "rezervacija/addNew";

    }

    @RequestMapping(value = "/admin/provera-emaila-za-rezervaciju", method = RequestMethod.POST)
    public String proveraEmailaZaRezervaciju(RedirectAttributes redirectAttributes,
                                             HttpServletRequest request){

        String emailKorisnika = request.getParameter("emailKorisnika");

        Optional<Korisnik> korisnikOpt = this.korisnikService.findByEmail(emailKorisnika);

        String putovanjeIdStr = request.getParameter("putovanjeId");
        Integer putovanjeId = Integer.valueOf(putovanjeIdStr);

        if(korisnikOpt.isPresent()){
            Korisnik korisnik = korisnikOpt.get();

            //provera da li postoji vec korisnik sa rezervacijom za dato putovanje
            Putovanje putovanje = this.putovanjeService.findById(putovanjeId).get();

            List<RezervacijaPutovanja> listaRezervacija = this.rezervacijaService.getAllRezervacijePutovanja(putovanje.getPutovanjeId());

            boolean korisnikImaRezervaciju = this.rezervacijaService.daLiKorisnikImaRezervaciju(listaRezervacija, korisnik);

            if(korisnikImaRezervaciju){ //korisnik ima rezervaciju znaci
                redirectAttributes.addFlashAttribute("korisnikImaRezervaciju", "DA");
                redirectAttributes.addFlashAttribute("unetEmailKorisnika", emailKorisnika);
            }else {
                redirectAttributes.addFlashAttribute("korisnikImaRezervaciju", "NE");
                redirectAttributes.addFlashAttribute("korisnikRezervacije", korisnik);
                if(putovanje.getBrojPrijavljenih() >= putovanje.getAranzman().getMaxPutnika()){
                    redirectAttributes.addFlashAttribute("putovanjePopunjeno", true);
                }
            }
            redirectAttributes.addAttribute("putovanjeId", putovanjeId);
            return "redirect:/admin/putovanje/rezervacije/addNew";
        }

        redirectAttributes.addFlashAttribute("korisnikNePostoji", "Korisnik sa unetim email-om ne postoji (Unet email: " + emailKorisnika + ")");
        redirectAttributes.addAttribute("putovanjeId", putovanjeId);
        return "redirect:/admin/putovanje/rezervacije/addNew";

    }

    @RequestMapping(value = "/admin/putovanje/rezervacije/addNew", method = RequestMethod.POST)
    public String addNewReservation(@Valid @ModelAttribute(value = "novaRezervacija") RezervacijaPutovanja novaRezervacija,
                                    BindingResult result, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request){

        //String datumVremeRezParam = request.getParameter("datumVremeRez");
        //podesiti cen

        if(result.hasErrors()){
            Integer putovanjeId = novaRezervacija.getPutovanje().getPutovanjeId();
            Putovanje putovanje = this.putovanjeService.findById(putovanjeId).get();
            Aranzman aranzman = putovanje.getAranzman();
            model.addAttribute("putovanje", putovanje);
            model.addAttribute("aranzman", aranzman);
            model.addAttribute("korisnikImaRezervaciju", "NE");
            model.addAttribute("korisnikRezervacije", novaRezervacija.getKorisnik());
            return "rezervacija/addNew";
        }

        this.rezervacijaService.save(novaRezervacija);



        //kada sacuvamo rezervaciju onda moramo i da update-ujemo broj prijavljenih
        Putovanje putovanje = novaRezervacija.getPutovanje();
        this.putovanjeService.incrementBrojPrijavljenih(putovanje.getPutovanjeId());


        boolean statusPutovanjaPromenjen = this.putovanjeService.podesavanjeStatusaPutovanjaPotvrdjeno(putovanje);

        if(statusPutovanjaPromenjen){
            redirectAttributes.addFlashAttribute("putovanjePotvrdjeno", "Prijavljen je minimalan broj putnika za aranzman, putovanje (ID: " + putovanje.getPutovanjeId() + ") je POTVRDJENO.");
        } //prikazati ovu poruku u view-u za rezervacije putovanja

        redirectAttributes.addFlashAttribute("messageSuccess", "Uspešno ste dodali novu rezervaciju za ovo putovanje (ID rezervacije: " + novaRezervacija.getRezervacijaId() + ", Korisnik ID: " + novaRezervacija.getKorisnik().getKorisnikId() +  ", Email: " + novaRezervacija.getKorisnik().getEmail() + ")" );
        redirectAttributes.addAttribute("putovanjeId", novaRezervacija.getPutovanje().getPutovanjeId());
        return "redirect:/admin/putovanje/rezervacijePutovanja";


    }


    //KORISNIKOVE RADNJE REZERVACIJE
    @RequestMapping(value = "/korisnik/rezervacija")
    public String rezervacijaPutovanjaShowPage(@RequestParam(value = "putovanjeId") Integer putovanjeId,
                                               Model model, Authentication authentication,
                                               RedirectAttributes redirectAttributes){

        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        String emailKorisnika = authentication.getName();
        Korisnik korisnik = this.korisnikService.findByEmail(emailKorisnika).get();

        Putovanje putovanje = this.putovanjeService.findById(putovanjeId).get();
        Aranzman aranzman = putovanje.getAranzman();

        List<RezervacijaPutovanja> listaRezervacija = this.rezervacijaService.getAllRezervacijePutovanja(putovanje.getPutovanjeId());

        boolean korisnikImaRezervaciju = this.rezervacijaService.daLiKorisnikImaRezervaciju(listaRezervacija, korisnik);

        if(korisnikImaRezervaciju){
            redirectAttributes.addFlashAttribute("KorisnikImaRezervaciju", true);
            redirectAttributes.addFlashAttribute("rezervisanoPutovanje", putovanje);
            return "redirect:/pregledAranzmana/" + aranzman.getAranzmanId() + "#polasciSection";
        }


        model.addAttribute("putovanje", putovanje);
        model.addAttribute("aranzman", aranzman);

        RezervacijaPutovanja novaRezervacija = new RezervacijaPutovanja();
        novaRezervacija.setPutovanje(putovanje);
        novaRezervacija.setKorisnik(korisnik);

        model.addAttribute("novaRezervacija", novaRezervacija);

        return "rezervacija/korisnikNapraviRezervaciju";

    }

    @RequestMapping(value = "/korisnik/rezervacija/addNew", method = RequestMethod.POST)
    public String rezervacijaPutovanja(@Valid @ModelAttribute(value = "novaRezervacija") RezervacijaPutovanja novaRezervacija,
                                       BindingResult result, RedirectAttributes redirectAttributes, Model model){


        //podesavanje datuma i vremena rezervacije
        LocalDateTime datumVremeRezervacije = LocalDateTime.now();
        DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String stringFormatDatumVremeRez = datumVremeRezervacije.format(df2);

        LocalDateTime datumVremeRez = LocalDateTime.parse(stringFormatDatumVremeRez, df2);
        novaRezervacija.setDatumVremeRez(datumVremeRez);
        //KRAJ podesavanja datuma i vremena rezervacije




        if(result.hasErrors()){

            Integer putovanjeId = novaRezervacija.getPutovanje().getPutovanjeId();
            redirectAttributes.addAttribute("putovanjeId", putovanjeId);
            return "redirect:/korisnik/rezervacija";
        }


        this.rezervacijaService.save(novaRezervacija);


        //kada sacuvamo rezervaciju onda moramo i da update-ujemo broj prijavljenih
        Putovanje putovanje = novaRezervacija.getPutovanje();
        this.putovanjeService.incrementBrojPrijavljenih(putovanje.getPutovanjeId());

        //podesavanje statusa putovanja na 'POTVRDJENO' ukoliko je broj prijavljenih postao jednak minimalnom broju putnika
        boolean statusPutovanjaPromenjen = this.putovanjeService.podesavanjeStatusaPutovanjaPotvrdjeno(putovanje);

        redirectAttributes.addAttribute("rezervacijaKorisnikaId", novaRezervacija.getRezervacijaId());
        return "redirect:/rezervacija/potvrdaORezervaciji";

    }

    //Da bismo preventovali DOUBLE SUBMISSION
    @RequestMapping(value = "rezervacija/potvrdaORezervaciji")
    public String potvrdaORezervacijiShowPage(@RequestParam(value = "rezervacijaKorisnikaId") Integer rezervacijaKorisnikaId,
                                              Model model){
        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        RezervacijaPutovanja rezervacijaKorisnika = this.rezervacijaService.findById(rezervacijaKorisnikaId).get();
        model.addAttribute("rezervacijaKorisnika", rezervacijaKorisnika);

        return "rezervacija/potvrdaORezervaciji";

    }


    //PREGLED KORISNIKOVIH REZERVACIJA
    @RequestMapping(value = "/korisnik/pregledRezervacija")
    public String pregledRezervacijaKorisnikaShowPage(Model model, Authentication authentication,
                                                      RedirectAttributes redirectAttributes){

        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        String emailKorisnika = authentication.getName();
        Korisnik korisnik = this.korisnikService.findByEmail(emailKorisnika).get();

        List<RezervacijaPutovanja> sveRezervacijeKorisnika = korisnik.getListaRezervacija();

        model.addAttribute("sveRezervacijeKorisnika", sveRezervacijeKorisnika);

        List<RezervacijaPutovanja> listaAktivnihRezervacija = this.rezervacijaService.getAllRezervacijePutovanjaKorisnikaAktivne(korisnik.getKorisnikId());

        model.addAttribute("listaAktivnihRezervacija", listaAktivnihRezervacija);

        List<RezervacijaPutovanja> listaIstorijeRezervacija = this.rezervacijaService.getAllRezervacijaPutovanjaKorisnikaIstorija(korisnik.getKorisnikId());

        model.addAttribute("listaIstorijeRezervacija", listaIstorijeRezervacija);

        return "rezervacija/pregledRezervacijaKorisnika";

    }


    //KORISNIK BRIŠE REZERVACIJU
    @RequestMapping(value = "/korisnik/deleteReservation")
    public String korisnikBrisanjeRezervacije(@RequestParam(value = "rezervacijaId") Integer rezervacijaId,
                                              Model model, RedirectAttributes redirectAttributes){

        RezervacijaPutovanja rezervacijaZaBrisanje = this.rezervacijaService.findById(rezervacijaId).get();



        Putovanje putovanje = rezervacijaZaBrisanje.getPutovanje();

        //brisanje rezervacije
        this.rezervacijaService.deleteById(rezervacijaZaBrisanje.getRezervacijaId());

        //posle delete-a moramo da smanjimo broj prijavljenih za 1
        this.putovanjeService.smanjiBrojPrijavljenih(putovanje.getPutovanjeId());


        //slucaj kada je broj prijavljenih bio jednak minimum broju putnika i smanjio se, onda treba podesiti status na NEPOTVRDJENO
        boolean statusPutovanjaPromenjen = this.putovanjeService.podesavanjeStatusaPutovanjaNepotvrdjeno(putovanje);

        redirectAttributes.addFlashAttribute("rezervacijaObrisana", true);
        redirectAttributes.addFlashAttribute("putovanjeRezObrisane", putovanje);
        return "redirect:/korisnik/pregledRezervacija";


    }



}
