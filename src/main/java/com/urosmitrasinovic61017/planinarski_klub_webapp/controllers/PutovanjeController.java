package com.urosmitrasinovic61017.planinarski_klub_webapp.controllers;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Aranzman;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.IzvestajGroup;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Putovanje;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.PutovanjeGroup;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.AranzmanService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.PutovanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class PutovanjeController {

    @Autowired
    private PutovanjeService putovanjeService;

    @Autowired
    private AranzmanService aranzmanService;

    @RequestMapping(value = "/admin/aranzman/putovanja")
    public String showAllPutovanjaAranzmana(
            @RequestParam(value = "aranzmanId") Integer aranzmanId,
            Model model){

        List<Putovanje> listaPutovanjaAranzmana = this.putovanjeService.getAllPutovanjaAranzmana(aranzmanId);
        model.addAttribute("listaPutovanjaAranzmana", listaPutovanjaAranzmana);

        Aranzman aranzman = this.aranzmanService.findById(aranzmanId).get();
        model.addAttribute("aranzman", aranzman);

        return "putovanje/allPutovanjaAranzmana";
    }

    @RequestMapping(value = "/admin/putovanje/pregledProslihPutovanja")
    public String showAllProteklaPutovanja(@RequestParam(value = "aranzmanId") Integer aranzmanId, Model model){

        List<Putovanje> listaProteklihPutovanjaOdrzanih = this.putovanjeService.getIstorijaPutovanjaOdrzanih(aranzmanId);


        model.addAttribute("listaProteklihPutovanjaOdrzanih", listaProteklihPutovanjaOdrzanih);

        Aranzman aranzman = this.aranzmanService.findById(aranzmanId).get();
        model.addAttribute("aranzman", aranzman);

        return "putovanje/listaIstorijePutovanja";
    }

    @RequestMapping(value = "/admin/putovanje/pregledOtkazanihPutovanja")
    public String showOtkazanaPutovanja(@RequestParam(value = "aranzmanId") Integer aranzmanId, Model model){

        List<Putovanje> listaOtkazanihPutovanja = this.putovanjeService.getOtkazanaPutovanjaAranzmana(aranzmanId);

        model.addAttribute("listaOtkazanihPutovanja", listaOtkazanihPutovanja);

        Aranzman aranzman = this.aranzmanService.findById(aranzmanId).get();
        model.addAttribute("aranzman", aranzman);

        return "putovanje/listaOtkazanihPutovanja";

    }

    @RequestMapping(value = "/admin/putovanje/pregledPutovanjaUToku")
    public String showPutovanjeUToku(@RequestParam(value = "aranzmanId") Integer aranzmanId, Model model){

        List<Putovanje> listaPutovanjaUTokuPotvrdjeno = this.putovanjeService.getAllPutovanjaUTokuPotvrdjeno(aranzmanId);

        model.addAttribute("listaPutovanjaUTokuPotvrdjeno", listaPutovanjaUTokuPotvrdjeno);

        Aranzman aranzman = this.aranzmanService.findById(aranzmanId).get();

        model.addAttribute("aranzman", aranzman);

        return "putovanje/prikazPutovanjaUToku";
    }

    @RequestMapping(value = "/admin/putovanje/new")
    public String showNewPutovanjeForm(@RequestParam("aranzmanId") Integer aranzmanId, Model model){

        Aranzman aranzman = this.aranzmanService.findById(aranzmanId).get();

        Putovanje novoPutovanje = new Putovanje();
        novoPutovanje.setAranzman(aranzman);

        model.addAttribute("novoPutovanje", novoPutovanje);
        model.addAttribute("aranzman", aranzman);


        return "putovanje/addNew";

    }

    @RequestMapping(value = "/admin/putovanje/new", method = RequestMethod.POST)
    public String addNewPutovanje(@Validated(PutovanjeGroup.class) @ModelAttribute("novoPutovanje") Putovanje novoPutovanje, BindingResult result,
                                  RedirectAttributes redirectAttributes, Model model, HttpServletRequest request){

        String datumVremePolaskaParam = request.getParameter("datumVremePolaska");
        String datumPovratkaParam = request.getParameter("datumPovratka");

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        LocalDateTime datumVremePolaskaForma = LocalDateTime.parse(datumVremePolaskaParam, df);

        DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String datumVremePolaskaFormat = datumVremePolaskaForma.format(df2);


        LocalDateTime datumVremePolaskaBaza = LocalDateTime.parse(datumVremePolaskaFormat, df2);
        novoPutovanje.setDatumVremePolaska(datumVremePolaskaBaza);

        //ovde ne moramo da pravimo formatter jer LocalDate automatski moze da parsira datum oblika yyyy-MM-dd
        LocalDate datumPovratka = LocalDate.parse(datumPovratkaParam);
        novoPutovanje.setDatumPovratka(datumPovratka);

        if(result.hasErrors()){
            model.addAttribute("aranzman", novoPutovanje.getAranzman());
            return "putovanje/addNew";
        }

        Aranzman aranzman = novoPutovanje.getAranzman();

        this.putovanjeService.save(novoPutovanje);



        redirectAttributes.addFlashAttribute("messageSuccess", "Uspešno ste uneli novo putovanje za ovaj aranžman (ID putovanja: " + novoPutovanje.getPutovanjeId() + ")" );

        redirectAttributes.addAttribute("aranzmanId", aranzman.getAranzmanId());
        return "redirect:/admin/aranzman/putovanja";
    }



    @RequestMapping(value = "/admin/putovanje/delete")
    public String deletePutovanje(@RequestParam(value = "putovanjeId") Integer putovanjeId,
                                  @RequestParam(value = "aranzmanId") Integer aranzmanId,
                                  RedirectAttributes redirectAttributes, HttpServletRequest request){

        Optional<Putovanje> putovanjeOpt = this.putovanjeService.findById(putovanjeId);

        String referer = request.getHeader("Referer"); //get previous page url from the Header

        if(putovanjeOpt.isPresent()){
            Putovanje putovanje = putovanjeOpt.get();

            this.putovanjeService.deleteById(putovanje.getPutovanjeId());

            redirectAttributes.addFlashAttribute("messageSuccess", "Uspešno ste obrisali željeno putovanje.");

            //redirectAttributes.addAttribute("aranzmanId", aranzmanId);

            return "redirect:" + referer;
        }

        redirectAttributes.addFlashAttribute("failMessage", "Putovanje koje ste probali da obrišete nema u bazi.");

        //redirectAttributes.addAttribute("aranzmanId", aranzmanId);
        return "redirect:" + referer;
    }


    @RequestMapping(value = "/admin/putovanje/edit/{id}")
    public String updatePutovanjeShowForm(@PathVariable(value = "id") Integer putovanjeId,
                                          RedirectAttributes redirectAttributes,
                                          Model model, HttpServletRequest request){


        Optional<Putovanje> putovanjeOpt = this.putovanjeService.findById(putovanjeId);

        String referer = request.getHeader("Referer");

        if(putovanjeOpt.isPresent()){
            Putovanje putovanje = putovanjeOpt.get();

            model.addAttribute("putovanje", putovanje);
            model.addAttribute("aranzman", putovanje.getAranzman());
            return "putovanje/edit";

        }

        redirectAttributes.addFlashAttribute("failMessage", "Putovanje koga ste probali da izmenite ne postoji u bazi.");
        return "redirect:" + referer;
    }

    @RequestMapping(value = "/admin/putovanje/update", method = RequestMethod.POST)
    public String updatePutovanje(@Validated(PutovanjeGroup.class) @ModelAttribute(name = "putovanje") Putovanje putovanje,
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {


        Aranzman aranzman = putovanje.getAranzman();

        if(result.hasErrors()){
            model.addAttribute("aranzman", putovanje.getAranzman());
            return "putovanje/edit";
        }

        this.putovanjeService.update(putovanje);


        redirectAttributes.addAttribute("aranzmanId", aranzman.getAranzmanId());
        redirectAttributes.addFlashAttribute("messageSuccess", "Uspešno ste sačuvali izmene za putovanje (ID putovanja: " + putovanje.getPutovanjeId() + ")");
        return "redirect:/admin/aranzman/putovanja";
        
        

    }

    @RequestMapping(value = "/admin/putovanje/editIzvestaj/{id}")
    public String updateIzvestajPutovanjaShowPage(@PathVariable("id") Integer putovanjeId, RedirectAttributes redirectAttributes, Model model,
                                                  HttpSession session, HttpServletRequest request){

        Putovanje putovanje = this.putovanjeService.findById(putovanjeId).get();
        model.addAttribute("putovanje", putovanje);

        Aranzman aranzman = putovanje.getAranzman();
        model.addAttribute("aranzman", aranzman);

        String referer = request.getHeader("Referer");


        session.setAttribute("previous_page_izvestaj_form", referer);


        return "putovanje/editIzvestaj";


    }


    @RequestMapping(value = "/admin/putovanje/editIzvestaj", method = RequestMethod.POST)
    public String updateIzvestajPutovanja(@Validated(IzvestajGroup.class) @ModelAttribute(name = "putovanje") Putovanje putovanje,
                                          BindingResult result,
                                          RedirectAttributes redirectAttributes,
                                          Model model,
                                          HttpSession session){


        Aranzman aranzman = putovanje.getAranzman();

        if(result.hasErrors()){
            model.addAttribute("aranzman", aranzman);
            return "putovanje/editIzvestaj";
        }


        this.putovanjeService.update(putovanje);

        String previous_page = (String)session.getAttribute("previous_page_izvestaj_form");




        redirectAttributes.addFlashAttribute("messageSuccess", "Uspešno ste izmenili izveštaj putovanja. (Putovanje ID: " + putovanje.getPutovanjeId() + ")");

        return "redirect:" + previous_page;

    }






}
