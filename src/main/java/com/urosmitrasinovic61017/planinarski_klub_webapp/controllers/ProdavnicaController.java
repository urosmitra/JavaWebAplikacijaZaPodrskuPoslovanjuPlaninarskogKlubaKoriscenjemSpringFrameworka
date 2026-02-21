package com.urosmitrasinovic61017.planinarski_klub_webapp.controllers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.urosmitrasinovic61017.planinarski_klub_webapp.config.MyExclusionStrategy;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.*;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.model.IModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class ProdavnicaController {


    @Autowired
    private KlubService klubService;

    @Autowired
    private ProizvodService proizvodService;

    @Autowired
    private VrstaProizvodaService vrstaProizvodaService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private StavkaKorpeService stavkaKorpeService;

    @Autowired
    private NarudzbenicaService narudzbenicaService;

    @Autowired
    private StavkaNarudzbeniceService stavkaNarudzbeniceService;

    @Autowired
    private SlikaProizvodService slikaProizvodService;

    @Autowired
    private ClanskaKarticaService clanskaKarticaService;

    @Autowired
    private ClanskaKarticaDiscountService clanKartDiscountService;

    private final long klubId = 192450723;


    //Prodavnica Front Page
    @RequestMapping(value = "/prodavnica")
    public String prodavnicaShowPage(Model model){

        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);


        List<VrstaProizvoda> listaVrsteProizvoda = this.vrstaProizvodaService.getAllVrsteProizvoda();
        model.addAttribute("listaVrsteProizvoda", listaVrsteProizvoda);

        List<Proizvod> listaSvihProizvoda = this.proizvodService.getAllProizvodi();
        model.addAttribute("listaSvihProizvoda", listaSvihProizvoda);

        return "proizvod/prodavnicaFrontPage";

    }

    @ResponseBody
    @RequestMapping(value = {"/orderProductsByPrice/{orderDir}", "/orderProductsByPrice/"})
    public String orderedProizvodiByPrice(@PathVariable(name = "orderDir", required = false) String orderDir){

        Gson gson = new GsonBuilder().setExclusionStrategies(new MyExclusionStrategy()).create();

        List<Proizvod> listaProizvodaNotOrdered = this.proizvodService.getAllProizvodi();

        if(orderDir == null){
            return gson.toJson(listaProizvodaNotOrdered);
        }
        List<Proizvod> proizvodiOrdered = this.proizvodService.orderByPriceAscDesc(orderDir);
        return gson.toJson(proizvodiOrdered);
    }

    //Prikaz svih proizvoda odredjene vrste proizvoda page
    @RequestMapping(value = "/prodavnica/vrstaProizvoda/{vrstaProizvodaId}")
    public String vrstaProizvodaShowPage(@PathVariable("vrstaProizvodaId") Integer vrstaProizvodaId,
                                         Model model){


        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        VrstaProizvoda vrstaProizvoda = this.vrstaProizvodaService.findById(vrstaProizvodaId).get();

        model.addAttribute("vrstaProizvoda", vrstaProizvoda);

        List<Proizvod> listaProizvodaVrste = this.proizvodService.findByVrstaProizvodaVrstaProizvodaId(vrstaProizvoda.getVrstaProizvodaId());

        model.addAttribute("listaProizvodaVrste", listaProizvodaVrste);

        return "vrstaProizvoda/vrstaProizvodaFrontPage";


    }

    //Proizvod stranica sa detaljima Front page
    @RequestMapping(value = "/prodavnica/proizvod")
    public String proizvodDetailsFrontShowPage(@RequestParam(value = "proizvodId") Integer proizvodId,
                                               Model model, Authentication authentication, HttpSession session) {
        //solved problem: SOLVED BY ADDING parameter of HttpSession to controller method for that template
        // Caused by: org.thymeleaf.exceptions.TemplateProcessingException: Error during execution of processor 'org.thymeleaf.spring5.processor.SpringActionTagProcessor' (template: "proizvod/detaljiProizvodaFrontPage" - line 135, col 38)
        //Caused by: java.lang.IllegalStateException: Cannot create a session after the response has been committed

        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        Proizvod proizvod = this.proizvodService.findById(proizvodId).get();

        model.addAttribute("proizvod", proizvod);

        if (authentication != null) {
            String emailKorisnika = authentication.getName();
            Korisnik korisnik = this.korisnikService.findByEmail(emailKorisnika).get();
            model.addAttribute("korisnik", korisnik);
        }

        StavkaKorpe novaStavkaKorpe = new StavkaKorpe();
        novaStavkaKorpe.setProizvod(proizvod);

        model.addAttribute("novaStavkaKorpe", novaStavkaKorpe);

        List<SlikaProizvod> listaSlikaProizvoda = this.slikaProizvodService.getAllSlikeProizvoda(proizvod);
        model.addAttribute("listaSlikaProizvoda", listaSlikaProizvoda);

        // napravi listu integer vrednosti za korpu, zajedno sa proverom da li je kolicina veca od 10, ako jeste puni do 10, ako nije, puni do broja proizvoda u korpi
        //List<Integer> listaBrojevaKolicine = this.proizvodService.napuniBrojUKoliciniDropDown(proizvod);


        return "proizvod/detaljiProizvodaFrontPage";


    }



    @RequestMapping(value = "/korisnik/korpaStavke/addNew", method = RequestMethod.POST)
    public String addToCartProizvod(@Valid @ModelAttribute(value = "novaStavkaKorpe") StavkaKorpe novaStavkaKorpe,
                                    BindingResult result, RedirectAttributes redirectAttributes, Model model,
                                    Authentication authentication){

        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        String emailKorisnika = authentication.getName();
        Korisnik korisnik = this.korisnikService.findByEmail(emailKorisnika).get();
        model.addAttribute("korisnik", korisnik);


        Proizvod proizvod = novaStavkaKorpe.getProizvod();

        model.addAttribute("proizvod", proizvod);

        if(result.hasErrors()){
            return "proizvod/detaljiProizvodaFrontPage";
        }


        StavkaKorpe stavkaKorpePostoji = this.stavkaKorpeService.findByKorisnikAndProizvod(korisnik, proizvod);


        //Dodavanje proizvoda u korpu
        Integer dodataKolicina = this.stavkaKorpeService.dodajProizvodUKorpu(novaStavkaKorpe);
        redirectAttributes.addFlashAttribute("dodataKolicina", dodataKolicina);


        if(dodataKolicina == 0){
            redirectAttributes.addFlashAttribute("maksBrojKolicineUKorpi", true);
        }

        //System.out.println(stavkaKorpePostoji);

        if(stavkaKorpePostoji != null  && dodataKolicina > 0){
            redirectAttributes.addFlashAttribute("stavkaKorpePostoji", true);
        }

        if(stavkaKorpePostoji == null){
            redirectAttributes.addFlashAttribute("novaStavkaKorpeDodata", true);
        }




        redirectAttributes.addAttribute("proizvodId", proizvod.getProizvodId());
        return "redirect:/prodavnica/proizvod";
    }



    @RequestMapping(value = "/korisnik/shopping-cart")
    public String showShoppingCartPage(Model model, Authentication authentication){

        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        String emailKorisnika = authentication.getName();
        Korisnik korisnik = this.korisnikService.findByEmail(emailKorisnika).get();

        ClanskaKartica clanskaKarticaKorisnika = this.clanskaKarticaService.findById(korisnik.getKorisnikId()).get();

        model.addAttribute("clanskaKarticaKorisnika", clanskaKarticaKorisnika);


        List<StavkaKorpe> listaStavkiKorpeKorisnika = this.stavkaKorpeService.getAllStavkeKorpeKorisnika(korisnik);

        model.addAttribute("listaStavkiKorpeKorisnika", listaStavkiKorpeKorisnika);

        Narudzbenica novaNarudzbenica = new Narudzbenica();
        novaNarudzbenica.setKorisnik(korisnik);
        model.addAttribute("novaNarudzbenica", novaNarudzbenica);

        return "stavkaKorpe/shoppingCartKorisnika";


    }


    @RequestMapping(value = "/korisnik/checkout")
    public String napraviNarudzbenicuShowPage(Model model, Authentication authentication,
                                              RedirectAttributes redirectAttributes, HttpServletRequest request){

        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        //PROVERA da li su svi proizvodi sa izabranim kolicinama na stanju i dalje
        //odnosno da li moze izabrana kolicina da se naruci

        String emailKorisnika = authentication.getName();
        Korisnik korisnik = this.korisnikService.findByEmail(emailKorisnika).get();


        List<StavkaKorpe> listaStavkiUnavailable = this.stavkaKorpeService.getAllStavkeKorpeNemaNaStanju(korisnik);


        if(!listaStavkiUnavailable.isEmpty()) {
            redirectAttributes.addFlashAttribute("listaStavkiUnavailable", listaStavkiUnavailable);
            return "redirect:/korisnik/shopping-cart";
        }

        //Provera je prosla...

        Narudzbenica novaNarudzbenica = new Narudzbenica();
        novaNarudzbenica.setKorisnik(korisnik);
        model.addAttribute("novaNarudzbenica", novaNarudzbenica);

        List<StavkaKorpe> listaStavkiKorpeKorisnika = this.stavkaKorpeService.getAllStavkeKorpeKorisnika(korisnik);
        model.addAttribute("listaStavkiKorpeKorisnika", listaStavkiKorpeKorisnika);


        popuniModelSaPodacimaOTotalIDiscountCeni(model, request);


        return "narudzbenica/korisnikAddNew";

    }

    private void popuniModelSaPodacimaOTotalIDiscountCeni(Model model, HttpServletRequest request){

        List<StavkaKorpe> listaStavkiKorpeKorisnika = (List<StavkaKorpe>) model.getAttribute("listaStavkiKorpeKorisnika");

        Integer totalCenaKorpe = this.stavkaKorpeService.izracunajTotalCenuKorpe(listaStavkiKorpeKorisnika);
        model.addAttribute("totalCenaKorpe", totalCenaKorpe);

        //ukoliko je clan izabrao da iskoristi nagradni popust
        String discountNagradaParam = request.getParameter("radioDiscountPackage");
        if(discountNagradaParam != null && !discountNagradaParam.isBlank()){
            Integer discountId = Integer.valueOf(discountNagradaParam);
            ClanskaKarticaDiscount clanKarticaDiscount = this.clanKartDiscountService.findById(discountId).get();
            model.addAttribute("izabranaNagrada", clanKarticaDiscount);

            Integer totalCenaDiscounted = this.clanKartDiscountService.izracunajDiscountedValueOfPrice(clanKarticaDiscount.getPopust(), totalCenaKorpe);
            model.addAttribute("totalCenaDiscounted", totalCenaDiscounted);

        }
    }



    @RequestMapping(value = "/korisnik/shopping-cart/nova-narudzbenica", method = RequestMethod.POST)
    public String korisnikAddNewNarudzbenica(@Valid @ModelAttribute("novaNarudzbenica") Narudzbenica novaNarudzbenica,
                                             BindingResult result, RedirectAttributes redirectAttributes, Model model,
                                             Authentication authentication, HttpServletRequest request){

        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        String emailKorisnika = authentication.getName();
        Korisnik korisnik = this.korisnikService.findByEmail(emailKorisnika).get();


        //podesavanje datuma i vremena narudzbenice
        LocalDateTime datumVremeNarucivanja = LocalDateTime.now();
        DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String stringFormatDatumVremeNarucivanja = datumVremeNarucivanja.format(df2);

        LocalDateTime datumVremeNarucivanjaFinal = LocalDateTime.parse(stringFormatDatumVremeNarucivanja, df2);
        novaNarudzbenica.setDatumVreme(datumVremeNarucivanjaFinal);
        // KRAJ podesavanja datuma i vremena narudzbenice

        List<StavkaKorpe> listaStavkiKorpeKorisnika = this.stavkaKorpeService.getAllStavkeKorpeKorisnika(korisnik);
        model.addAttribute("listaStavkiKorpeKorisnika", listaStavkiKorpeKorisnika);

        if(result.hasErrors()){
            popuniModelSaPodacimaOTotalIDiscountCeni(model, request);
            return "narudzbenica/korisnikAddNew";
        }


        this.narudzbenicaService.save(novaNarudzbenica);

        this.stavkaNarudzbeniceService.saveStavkeNarudzbeniceAndUpdateKolicinuProizvoda(novaNarudzbenica.getNarudzbenicaId(), listaStavkiKorpeKorisnika);

        ClanskaKartica clanskaKarticaKorisnika = this.clanskaKarticaService.findById(korisnik.getKorisnikId()).get();

        String discountNagradaParam = request.getParameter("radioDiscountPackage");
        if(discountNagradaParam != null && !discountNagradaParam.isBlank()){
            Integer discountId = Integer.valueOf(discountNagradaParam);
            ClanskaKarticaDiscount clanKarticaDiscount = this.clanKartDiscountService.findById(discountId).get();
            this.clanskaKarticaService.smanjiBrojBodovaNaKartici(clanskaKarticaKorisnika, clanKarticaDiscount.getVrednostBodovi());
        }
        else{
            //povecati broj bodova na kartici
            this.clanskaKarticaService.povecajBrojBodovaNaKarticiPriPorudzbini(clanskaKarticaKorisnika);

        }



        redirectAttributes.addAttribute("narudzbenicaId", novaNarudzbenica.getNarudzbenicaId());
        return "redirect:/korisnik/shopping-cart/potvrda-porudzbine";
    }


    //Da bismo preventovali DOUBLE SUBMISSION
    @RequestMapping(value = "/korisnik/shopping-cart/potvrda-porudzbine")
    public String potvrdaPorudzbineShowPage(@RequestParam(value = "narudzbenicaId") Integer narudzbenicaId,
                                     Model model){
        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        Narudzbenica narudzbenica = this.narudzbenicaService.findById(narudzbenicaId).get();
        model.addAttribute("narudzbenica", narudzbenica);

        return "narudzbenica/potvrdaPorudzbine";

    }

    //Korisnik briše stavku korpe
    // /korisnik/stavkaKorpe/delete?stavkaKorpeId=10
    @RequestMapping(value = "/korisnik/stavkaKorpe/delete")
    public String korisnikBrisanjeStavkeKorpe(@RequestParam(value = "stavkaKorpeId") Integer stavkaKorpeId,
                                              RedirectAttributes redirectAttributes){

        StavkaKorpe stavkaKorpe = this.stavkaKorpeService.findById(stavkaKorpeId).get();

        Proizvod proizvodUKorpi = stavkaKorpe.getProizvod();

        this.stavkaKorpeService.deleteById(stavkaKorpe.getStavkaKorpeId());

        redirectAttributes.addFlashAttribute("stavkaKorpeObrisana", true);
        redirectAttributes.addFlashAttribute("proizvodObrisaneKorpe", proizvodUKorpi);
        return "redirect:/korisnik/shopping-cart";

    }






}
