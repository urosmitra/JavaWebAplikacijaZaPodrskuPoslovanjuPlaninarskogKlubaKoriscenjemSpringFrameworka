package com.urosmitrasinovic61017.planinarski_klub_webapp.controllers;


import com.urosmitrasinovic61017.planinarski_klub_webapp.config.FileUploadUtil;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Aranzman;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.VrstaPutovanja;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.AranzmanService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.VrstaPutovanjaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AranzmanController {

    @Autowired
    private AranzmanService aranzmanService;

    @Autowired
    private VrstaPutovanjaService vrstaPutovanjaService;


    @RequestMapping(value = "/admin/listaAranzmana")
    public String showListAranzmani(Model model){

        List<Aranzman> listaAranzmana = this.aranzmanService.getAllAranzmani();

        model.addAttribute("listaAranzmana", listaAranzmana);
        return "aranzman/all";

    }

    @RequestMapping(value = "/admin/aranzman/new")
    public String addNewAranzmanShowForm(Model model){

        List<String> listaZahtevnosti = this.aranzmanService.getListaZahtevnosti();
        model.addAttribute("listaZahtevnosti", listaZahtevnosti);

        List<VrstaPutovanja> listaVrstePutovanja = this.vrstaPutovanjaService.getAllVrstePutovanja();
        model.addAttribute("listaVrstePutovanja", listaVrstePutovanja);


        Aranzman aranzman = new Aranzman();
        model.addAttribute("noviAranzman", aranzman);


        return "aranzman/addNew";

    }


    @RequestMapping(value = "/admin/aranzman/new", method = RequestMethod.POST)
    public String addNewAranzman(@Valid @ModelAttribute(name = "noviAranzman") Aranzman noviAranzman, BindingResult result, Model model,
                                 @RequestParam(value = "slika", required = false) MultipartFile multipartFile,
                                 HttpServletRequest request, RedirectAttributes redirectAttributes)  throws IOException {


       if(result.hasErrors()) {

           List<String> listaZahtevnosti = this.aranzmanService.getListaZahtevnosti();
           model.addAttribute("listaZahtevnosti", listaZahtevnosti);

           List<VrstaPutovanja> listaVrstePutovanja = this.vrstaPutovanjaService.getAllVrstePutovanja();
           model.addAttribute("listaVrstePutovanja", listaVrstePutovanja);

           return "aranzman/addNew";
       }

        this.aranzmanService.save(noviAranzman);

       //Ukoliko smo dodali thumbnail fotografiju, ubaciti je u file sistem i update-ovati aranzman thumbnail tabelu
        this.aranzmanService.updateThumbnailPhoto_If_Added(noviAranzman, multipartFile);


        redirectAttributes.addFlashAttribute("messageSuccess", "Uspešno ste dodali novi aranžman u bazu podataka! (ID: " + noviAranzman.getAranzmanId() + ", Naziv: " + noviAranzman.getNaziv() + ")");
        return "redirect:/admin/listaAranzmana";
    }


    //BRISANJE ARANZMANA
    @RequestMapping(value = "/admin/aranzman/delete/{aranzmanId}")
    public String deleteAranzman(@PathVariable("aranzmanId") Integer aranzmanId,
                                 RedirectAttributes redirectAttributes) throws IOException{

        Optional<Aranzman> aranzmanZaBrisanje = this.aranzmanService.findById(aranzmanId);

        if(aranzmanZaBrisanje.isPresent()){
            Aranzman aranzman = aranzmanZaBrisanje.get();

            //pre brisanja treba da obrisemo direktorijum slika aranzmana iz file sistema

            String uploadDir = "slikeAranzmani/aranzmanId-" + aranzman.getAranzmanId();

            FileUploadUtil.deleteDirectory(uploadDir);


            this.aranzmanService.deleteById(aranzman.getAranzmanId());


            redirectAttributes.addFlashAttribute("messageSuccess", "Uspešno ste obrisali aranžman (ID: " + aranzman.getAranzmanId() + ", Naziv: " + aranzman.getNaziv() + ")");
            return "redirect:/admin/listaAranzmana";
        }


        redirectAttributes.addFlashAttribute("failMessage", "Aranžman koga ste probali da izbrišete nema u bazi.");
        return "redirect:/admin/listaAranzmana";

    }



    @RequestMapping(value = "/admin/aranzman/edit/{id}")
    public String updateAranzmanShowForm(@PathVariable("id") Integer aranzmanId,
                                         RedirectAttributes redirectAttributes,
                                         Model model){

        Optional<Aranzman> aranzmanOpt = this.aranzmanService.findById(aranzmanId);

        if(aranzmanOpt.isPresent()){
            Aranzman aranzman = aranzmanOpt.get();
            model.addAttribute("aranzman", aranzman);
            return "aranzman/edit";
        }

        redirectAttributes.addFlashAttribute("failMessage", "Aranžman koga ste probali da izmenite ne postoji u bazi.");
        return "redirect:/admin/listaAranzmana";

    }

    @RequestMapping(value = "/admin/aranzman/deleteThumbnailPhoto")
    public String deleteThumbnailPhotoAranzmana(@RequestParam(value = "aranzmanId") Integer aranzmanId,
                                                RedirectAttributes redirectAttributes) throws IOException{
        Aranzman aranzman = this.aranzmanService.findById(aranzmanId).get();


        //Sada treba obrisati thumbnail fotografiju iz file sistema aplikacije, pa zatim obrisati u tabeli u bazi
        String fileName = aranzman.getThumbnailPhoto();
        String uploadDir = "slikeAranzmani/aranzmanId-" + aranzman.getAranzmanId();
        FileUploadUtil.deleteSpecificFile(uploadDir, fileName);
        //KRAJ brisanja thumbnail fotografije iz file sistema

        aranzman.setThumbnailPhoto(null);
        this.aranzmanService.update(aranzman);


        redirectAttributes.addFlashAttribute("ThumbnailPhotoDeleteMsg", "Uspešno ste obrisali thumbnail fotografiju! Sada možete dodati novu.");
        return "redirect:/admin/aranzman/edit/" + aranzmanId;
    }



    @RequestMapping(value = "/admin/aranzman/update", method = RequestMethod.POST)
    public String updateAranzman(@Valid @ModelAttribute("aranzman") Aranzman aranzman,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam(value = "thumbnail", required = false) MultipartFile multipartFile) throws IOException{

        if(bindingResult.hasErrors()){
            return "aranzman/edit";
        }


        this.aranzmanService.update(aranzman);

        //Ukoliko smo dodali novu thumbnail fotografiju, ubaciti je u file sistem i update-ovati aranzman thumbnail tabelu
        this.aranzmanService.updateThumbnailPhoto_If_Added(aranzman, multipartFile);



        redirectAttributes.addFlashAttribute("messageSuccess", "Uspešno ste sačuvali izmene za aranžman (ID: " + aranzman.getAranzmanId() + ", Naziv: " + aranzman.getNaziv() + ")" );
        return "redirect:/admin/listaAranzmana";

    }








}
