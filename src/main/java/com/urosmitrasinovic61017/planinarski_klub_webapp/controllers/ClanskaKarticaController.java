package com.urosmitrasinovic61017.planinarski_klub_webapp.controllers;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.ClanskaKartica;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Korisnik;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.ClanskaKarticaService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.KorisnikService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class ClanskaKarticaController {

    @Autowired
    private ClanskaKarticaService clanKarticaService;

    @Autowired
    private KorisnikService korisnikService;



    @RequestMapping(value = "/admin/clanskaKartica/edit/{id}")
    public String showEditFormClanskaKartica(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes){

        Optional<ClanskaKartica> clanKOpt = this.clanKarticaService.findById(id);

        if(clanKOpt.isPresent()){
            ClanskaKartica clanKart = clanKOpt.get();
            Korisnik korisnik = this.korisnikService.findById(clanKart.getClanskaKarticaId()).get();

            model.addAttribute("clanskaKartica", clanKart);
            model.addAttribute("korisnik", korisnik);

            List<String> statusiKartice = this.clanKarticaService.getListaStatusaKartice();
            model.addAttribute("listaStatusaKartice", statusiKartice);

            return "clanskaKartica/edit";
        }
        else {

            redirectAttributes.addFlashAttribute("failMessage", "Članska kartica korisnika ne postoji u bazi!");
            return "redirect:/admin/listaKorisnika";
        }


    }



    @RequestMapping(value = "/admin/clanskaKartica/update", method = RequestMethod.POST)
    public String updateClanskaKartica(@ModelAttribute(value = "clanskaKartica") @Valid ClanskaKartica clanskaKartica,
                                       BindingResult result, RedirectAttributes redirectAttributes,
                                       HttpServletRequest request, Model model){


        String btnOtkazi = request.getParameter("btnOtkazi");

        if(btnOtkazi != null){
            return "redirect:/admin/listaKorisnika";
        }


        String datumIzdavanjaParam = request.getParameter("datumIzdavanja");

        String datumIstekaParam = request.getParameter("datumIsteka");

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate datumIzdavanja = LocalDate.parse(datumIzdavanjaParam);
        clanskaKartica.setDatumIzdavanja(datumIzdavanja);

        LocalDate datumIsteka = LocalDate.parse(datumIstekaParam);
        clanskaKartica.setDatumIsteka(datumIsteka);


        if(result.hasErrors()) {
            Korisnik korisnik = this.korisnikService.findById(clanskaKartica.getClanskaKarticaId()).get();
            model.addAttribute("korisnik", korisnik);

            List<String> statusiKartice = this.clanKarticaService.getListaStatusaKartice();

            model.addAttribute("listaStatusaKartice", statusiKartice);

            return "clanskaKartica/edit";
        }

        this.clanKarticaService.update(clanskaKartica);

        return "redirect:/admin/listaKorisnika";


    }


}
