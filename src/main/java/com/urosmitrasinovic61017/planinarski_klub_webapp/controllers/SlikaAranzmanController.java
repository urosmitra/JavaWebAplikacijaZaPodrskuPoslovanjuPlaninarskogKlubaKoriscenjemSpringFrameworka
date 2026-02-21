package com.urosmitrasinovic61017.planinarski_klub_webapp.controllers;


import com.urosmitrasinovic61017.planinarski_klub_webapp.config.FileUploadUtil;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Aranzman;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.SlikaAranzman;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.AranzmanService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.SlikaAranzmanService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.NazivSlikeAranzmanaAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class SlikaAranzmanController {

    @Autowired
    private SlikaAranzmanService slikaAranzmanService;

    @Autowired
    private AranzmanService aranzmanService;


    @RequestMapping(value = "/admin/aranzman/slikeAranzmana")
    public String showManagementSlikeAranzmanaPage(
            @RequestParam(value = "aranzmanId") Integer aranzmanId,
            Model model
            ){

        List<SlikaAranzman> listaSlikaAranzmana = this.slikaAranzmanService.getAllSlikeAranzmana(aranzmanId);
        model.addAttribute("listaSlikaAranzmana", listaSlikaAranzmana);

        Aranzman aranzman = this.aranzmanService.findById(aranzmanId).get();
        model.addAttribute("aranzman", aranzman);

        return "slikaAranzman/allSlikeAranzmana";
    }

    @RequestMapping(value = "/admin/aranzman/slikeAranzmana/new", method = RequestMethod.POST)
    public String saveNewImageAranzmana (
            @RequestParam(value = "aranzmanId") Integer aranzmanId,
            @RequestParam(value = "slika") MultipartFile multipartFile,
            RedirectAttributes redirectAttributes)  throws IOException {

        Aranzman aranzman = this.aranzmanService.findById(aranzmanId).get();

        SlikaAranzman novaSlikaAranzmana = new SlikaAranzman();

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        novaSlikaAranzmana.setNazivSlike(fileName);

        novaSlikaAranzmana.setAranzman(aranzman);

        try{
            this.slikaAranzmanService.save(novaSlikaAranzmana);
        } catch (NazivSlikeAranzmanaAlreadyExists ex){
            redirectAttributes.addFlashAttribute("slikaAranzmanAlreadyExists", ex.getMessage());
            redirectAttributes.addAttribute("aranzmanId", aranzman.getAranzmanId());
            return "redirect:/admin/aranzman/slikeAranzmana";
        }

        String uploadDir = "slikeAranzmani/aranzmanId-" + aranzman.getAranzmanId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);


        redirectAttributes.addAttribute("aranzmanId", aranzman.getAranzmanId()); //ovo ce se proslediti kao request param u redirect url-u
        //za razliku od metode addFlashAttribute koja dati objekat storuje u session user-a

        return "redirect:/admin/aranzman/slikeAranzmana";
    }


    @RequestMapping(value = "/admin/slikaAranzman/delete")
    public String deleteSlikaAranzman(@RequestParam(value = "slikaId") Integer slikaId,
                                      @RequestParam(value = "aranzmanId") Integer aranzmanId,
                                      RedirectAttributes redirectAttributes) throws IOException{

        Aranzman aranzman = this.aranzmanService.findById(aranzmanId).get();

        SlikaAranzman slikaAranzmanZaBrisanje = this.slikaAranzmanService.findById(slikaId).get();


        //Izbrisi sliku iz file sistema aplikacije
        String fileName = slikaAranzmanZaBrisanje.getNazivSlike();

        String uploadDir = "slikeAranzmani/aranzmanId-" + aranzman.getAranzmanId();

        FileUploadUtil.deleteSpecificFile(uploadDir, fileName);
        //Izbrisi sliku iz file sistema aplikacije KRAJ

        this.slikaAranzmanService.deleteById(slikaAranzmanZaBrisanje.getSlikaId());


        redirectAttributes.addAttribute("aranzmanId", aranzman.getAranzmanId());
        redirectAttributes.addFlashAttribute("slikaAranzmanaObrisana", true);
        return "redirect:/admin/aranzman/slikeAranzmana";



    }




}
