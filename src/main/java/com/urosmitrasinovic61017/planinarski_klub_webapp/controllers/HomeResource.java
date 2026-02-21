package com.urosmitrasinovic61017.planinarski_klub_webapp.controllers;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.*;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeResource {

    @Autowired
    private KlubService klubService;

    @Autowired
    private PutovanjeService putovanjeService;

    @Autowired
    private AranzmanService aranzmanService;

    @Autowired
    private SlikaAranzmanService slikaAranzmanService;

    @Autowired
    private KorisnikService korisnikService;

    private final long klubId = 192450723;

    @GetMapping("/index")
    public String home(Model model){

        Klub klub = this.klubService.findById(klubId).get();

        model.addAttribute("klub", klub);

        List<Putovanje> listaPredstojecihPutovanja = this.putovanjeService.getBuducaPutovanjaLimitirano();

        model.addAttribute("listaPredstojecihPutovanja", listaPredstojecihPutovanja);

        return "homeResource/index";
    }


    @GetMapping("/")
    public String home2(){
        return "redirect:/index";
    }


    @GetMapping("/user")
    public String user(){
        return "redirect:/index";
    }

    @GetMapping("/admin")
    public String admin(){
        return "redirect:/admin/listaKorisnika";
    }

    @GetMapping("/manager")
    public String manager(){
        return "redirect:/manager/listaProizvoda";
    }


    @RequestMapping(value = "/pregledAranzmana/{aranzmanId}")
    public String showPregledAranzmana(@PathVariable("aranzmanId") Integer aranzmanId, Model model,
                                       RedirectAttributes redirectAttributes){

        Klub klub = this.klubService.findById(klubId).get();

        model.addAttribute("klub", klub);


        Optional<Aranzman> aranzmanOpt = this.aranzmanService.findById(aranzmanId);

        if(aranzmanOpt.isPresent()){
            Aranzman aranzman = aranzmanOpt.get();

            model.addAttribute("aranzman", aranzman);


            List<Putovanje> listaPutovanjaAvailableZaKorisnika = this.putovanjeService.getAllBuducaPutovanjaAranzmanaAvailableZaKorisnika(aranzman.getAranzmanId());

            model.addAttribute("listaPutovanjaAvailableZaKorisnika", listaPutovanjaAvailableZaKorisnika);


            List<SlikaAranzman> listaSlikaAranzmana = this.slikaAranzmanService.getAllSlikeAranzmana(aranzman.getAranzmanId());

            model.addAttribute("listaSlikaAranzmana", listaSlikaAranzmana);

            return "aranzman/pregledAranzmanaPage";

        }

        redirectAttributes.addFlashAttribute("messageAranzmanFail","Nismo uspeli da nadjemo aranzman za izabrano putovanje.");
        return "redirect:/index";

    }


}
