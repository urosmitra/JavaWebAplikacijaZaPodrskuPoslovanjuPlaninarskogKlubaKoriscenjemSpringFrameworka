package com.urosmitrasinovic61017.planinarski_klub_webapp.controllers;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.*;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.ClanskaKarticaService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.KlubService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.KorisnikService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class KorisnikController {

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private ClanskaKarticaService clanKarticaService;

    @Autowired
    private KlubService klubService;

    private final long klubId = 192450723;


    @RequestMapping(value = "/admin/listaKorisnika")
    public String showListKorisnici(Model model, @RequestParam(value = "keyword", required = false) String keyword){

        List<Korisnik> listaKorisnika = this.korisnikService.getAllKorisnici();

        Korisnik glavniAdmin = this.korisnikService.findById(4).get();
        model.addAttribute("glavniAdmin", glavniAdmin);

        if(keyword != null){
            model.addAttribute("listaKorisnika", this.korisnikService.findAllByKeyword(keyword));
            model.addAttribute("keyword", keyword);
        }
        else{
            model.addAttribute("listaKorisnika", listaKorisnika);
        }


        return "korisnik/all";
    }

    @RequestMapping(value = "/admin/korisnik/edit/{id}")
    public String updateKorisnikShowForm(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes){

        Optional<Korisnik> k = this.korisnikService.findById(id);

        if(k.isPresent()){
            Korisnik kor = k.get();
            model.addAttribute("korisnik", kor);
            return "korisnik/edit";
        }
        else {
            redirectAttributes.addFlashAttribute("failMessage", "Korisnika koga ste pokusali da izmenite, nema u bazi!");
            return "redirect:/admin/listaKorisnika";
        }

    }

    @RequestMapping(value = "/admin/korisnik/update", method = RequestMethod.POST)
    public String updateKorisnik(@ModelAttribute(value = "korisnik") @Valid Korisnik korisnik,
                                 BindingResult result, RedirectAttributes redirectAttributes,
                                 HttpServletRequest request, HttpServletResponse response, Model model, Authentication authentication) {

        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        String btnOtkazi = request.getParameter("btnOtkazi");

        if(btnOtkazi != null){
            return "redirect:/admin/listaKorisnika";
        }

        if(result.hasErrors()) {
            return "korisnik/edit";
        }


        try{
            this.korisnikService.izmenaKorisnickogNaloga(korisnik);

            if(!authentication.getName().equals(korisnik.getEmail())){ //ako je promenjen email korisnik mora da se odjavi
                //prvo pa da se sa novim email-om uloguje

                this.customLogout(request, response); //custom logout

                HttpSession session = request.getSession();
                session.setAttribute("changedMail", korisnik.getEmail());
                session.setAttribute("oldMail",  authentication.getName());
                session.setAttribute("klub", klub);
                return "redirect:/changed-mail-require-login";

            }
        }catch (UserAlreadyExistsException ex){
            model.addAttribute("porukaAccExists", ex.getMessage());
            model.addAttribute("unetEmail", korisnik.getEmail());
            return "korisnik/edit";
        }
        redirectAttributes.addFlashAttribute("porukaEditSuccess", "Uspešno ste izmenili podatke naloga!");
        redirectAttributes.addFlashAttribute("izmenjenKorisnik", korisnik);
        return "redirect:/admin/listaKorisnika";


    }


    @RequestMapping(value = "admin/korisnik/delete/{id}")
    public String deleteKorisnik(@PathVariable(name = "id") Integer idKorisnika, Model model, RedirectAttributes redirectAttributes){
        Optional<Korisnik> optKor = this.korisnikService.findById(idKorisnika);

        if(optKor.isPresent()){
            Korisnik korisnik = optKor.get();

            this.korisnikService.deleteById(korisnik.getKorisnikId());

            redirectAttributes.addFlashAttribute("messageSuccessObrisan", "Korisnik (ID: " + korisnik.getKorisnikId() + ", Email: " + korisnik.getEmail() + ") je uspešno obrisan.");
            return "redirect:/admin/listaKorisnika";

        }

        redirectAttributes.addFlashAttribute("failMessage", "Korisnika koga ste probali da izbrišete nema u bazi.");
        return "redirect:/admin/listaKorisnika";

    }

    //sledece metode su vezane za REGISRACIJU
    @RequestMapping(value = "/registracija")
    public String showRegistrationForm(Model model){

        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        RegistracijaDTO registracijaDTO = new RegistracijaDTO();

        model.addAttribute("registracijaDTO", registracijaDTO);

        return "korisnik/registracija";
    }

    @RequestMapping(value = "/newKorisnik/save", method = RequestMethod.POST)
    public String registrujKorisnika(@Valid @ModelAttribute(value = "registracijaDTO") RegistracijaDTO registracijaDTO,
                                     BindingResult result, Model model, RedirectAttributes redirectAttributes){


        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        if(result.hasErrors()){

            return "korisnik/registracija";
        }

        Korisnik noviKorisnik;

        try{
            noviKorisnik = this.korisnikService.registracijaKorisnika(registracijaDTO);
        }catch (UserAlreadyExistsException ex){
            model.addAttribute("porukaAccExists", ex.getMessage());
            model.addAttribute("unetEmail", registracijaDTO.getEmail());
            return "korisnik/registracija";
        }

        redirectAttributes.addFlashAttribute("porukaRegistracija", "Uspešno ste se registrovali sa email-om: " + noviKorisnik.getEmail() + "!");
        return "redirect:/registracija";

    }


    @RequestMapping(value = "/korisnik/pregledNaloga")
    public String showPregledNalogaKorisnika(Model model, Authentication authentication){

        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        String emailKorisnika = authentication.getName();
        Korisnik korisnik = this.korisnikService.findByEmail(emailKorisnika).get();

        model.addAttribute("korisnik", korisnik);

        ClanskaKartica clanskaKarticaKor = this.clanKarticaService.findById(korisnik.getKorisnikId()).get();
        model.addAttribute("clanskaKarticaKorisnika", clanskaKarticaKor);



        return "korisnik/pregledNalogaKorisnika";
    }

    @RequestMapping(value = "/korisnik/editAcc")
    public String showEditFormNalogaKorisnika(Model model, @RequestParam(value = "korisnikId") Integer korisnikId){
        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        Korisnik korisnik = this.korisnikService.findById(korisnikId).get();
        model.addAttribute("korisnik", korisnik);

//        KorisnikDto korisnikDto = new KorisnikDto();
//        korisnikDto.setKorisnikId(korisnik.getKorisnikId());
//        model.addAttribute("korisnikDto", korisnikDto);

        return "korisnik/editPersonalInfo";

    }

    @RequestMapping(value = "/korisnik/editAcc", method = RequestMethod.POST)
    public String editPersonalInfo(@Valid @ModelAttribute(value = "korisnik") Korisnik korisnik,BindingResult result,
                                   Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Authentication authentication){

        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        String btnOtkazi = request.getParameter("btnOtkazi");

        if(btnOtkazi != null){
            return "redirect:/korisnik/pregledNaloga";
        }

        if(result.hasErrors()){

            return "korisnik/editPersonalInfo";
        }

        try{
            this.korisnikService.izmenaKorisnickogNaloga(korisnik);

            if(!authentication.getName().equals(korisnik.getEmail())){ //ako je promenjen email korisnik mora da se odjavi
                //prvo pa da se sa novim email-om uloguje

                this.customLogout(request, response); //custom logout

                HttpSession session = request.getSession();
                session.setAttribute("changedMail", korisnik.getEmail());
                session.setAttribute("oldMail",  authentication.getName());
                session.setAttribute("klub", klub);
                return "redirect:/changed-mail-require-login";

            }
        }catch (UserAlreadyExistsException ex){
            model.addAttribute("porukaAccExists", ex.getMessage());
            model.addAttribute("unetEmail", korisnik.getEmail());
            return "korisnik/editPersonalInfo";
        }
        redirectAttributes.addFlashAttribute("porukaEditSuccess", "Uspešno ste izmenili podatke naloga!");
        return "redirect:/korisnik/pregledNaloga";
    }

    @RequestMapping(value = "/changed-mail-require-login")
    public String showPageChangedMail(Model model, HttpSession session){
        Klub klub = (Klub) session.getAttribute("klub");
        String oldMail = (String) session.getAttribute("oldMail");
        String changedMail = (String) session.getAttribute("changedMail");

        model.addAttribute("klub", klub);
        model.addAttribute("oldMail", oldMail);
        model.addAttribute("changedMail", changedMail);

        return "korisnik/changedMailRequireLogin";
    }


    //custom logout method
    public void customLogout(HttpServletRequest request, HttpServletResponse response) {
        boolean isSecure = false;
        String contextPath = null;
        if (request != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            isSecure = request.isSecure();
            contextPath = request.getContextPath();
        }
        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.clearContext();
        context.setAuthentication(null);
        if (response != null) {
            Cookie cookie = new Cookie("JSESSIONID", null);
            String cookiePath = StringUtils.hasText(contextPath) ? contextPath : "/";
            cookie.setPath(cookiePath);
            cookie.setMaxAge(0);
            cookie.setSecure(isSecure);
            response.addCookie(cookie);
        }
    }

    // /korisnik/editAccChangePass?korisnikId=5

    @RequestMapping(value = "/korisnik/editAccChangePass")
    public String showChangePassForm(@RequestParam("korisnikId") Integer korisnikId, Model model){

        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        Korisnik korisnik = this.korisnikService.findById(korisnikId).get();
        model.addAttribute("korisnik", korisnik);

        KorisnikChangePassDto korChangePassDto = new KorisnikChangePassDto();
        korChangePassDto.setOldPassword(korisnik.getPassword());

        model.addAttribute("korisnikChangePassDto", korChangePassDto);

        return "korisnik/changePassPage";

    }

    @RequestMapping(value = "/korisnik/editAccChangePass", method = RequestMethod.POST)
    public String changeKorisnikPassword(@Valid @ModelAttribute(value = "korisnikChangePassDto") KorisnikChangePassDto korisnikChangePassDto,
                                         BindingResult result, Model model, RedirectAttributes redirectAttributes, Authentication authentication){

        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);
        String emailKor = authentication.getName();
        Korisnik korisnik = this.korisnikService.findByEmail(emailKor).get();
        model.addAttribute("korisnik", korisnik);

        if(result.hasErrors()){

            return "korisnik/changePassPage";
        }

        this.korisnikService.changeKorisnikPassword(korisnik, korisnikChangePassDto);

        redirectAttributes.addFlashAttribute("passwordChanged", true);
        return "redirect:/korisnik/pregledNaloga";



    }













}
