package com.urosmitrasinovic61017.planinarski_klub_webapp.controllers;


import com.urosmitrasinovic61017.planinarski_klub_webapp.config.FileUploadUtil;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Aranzman;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Klub;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.VrstaPutovanja;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.AranzmanService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.KlubService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.VrstaPutovanjaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class VrstaPutovanjaController {

    @Autowired
    private VrstaPutovanjaService vrstaPutovanjaService;

    @Autowired
    private AranzmanService aranzmanService;

    @Autowired
    private KlubService klubService;

    private final long klubId = 192450723;



    //ADMIN DEO MENDADZMENT VRSTE PUTOVANJA
    @RequestMapping(value = "/admin/listaVrstePutovanja")
    public String showAllVrstePutovanjaPage(Model model){
        List<VrstaPutovanja> listaVrstePutovanja = this.vrstaPutovanjaService.getAllVrstePutovanja();

        model.addAttribute("listaVrstePutovanja", listaVrstePutovanja);

        return "vrstaPutovanja/all";
    }


    @RequestMapping(value = "/admin/vrstaPutovanja/addNew")
    public String addNewVrstaPutovanjaShowForm(Model model){

        VrstaPutovanja novaVrstaPutovanja = new VrstaPutovanja();

        model.addAttribute("novaVrstaPutovanja", novaVrstaPutovanja);

        return "vrstaPutovanja/addNew";

    }

    @RequestMapping(value = "/admin/vrstaPutovanja/addNew", method = RequestMethod.POST)
    public String addNewVrstaPutovanja(@Valid @ModelAttribute(value = "novaVrstaPutovanja") VrstaPutovanja novaVrstaPutovanja,
                                       BindingResult result,
                                       @RequestParam(value = "slika", required = false) MultipartFile multipartFile,
                                       RedirectAttributes redirectAttributes) throws IOException {

        if(result.hasErrors()){
            return "vrstaPutovanja/addNew";
        }

        this.vrstaPutovanjaService.save(novaVrstaPutovanja);

        //Ukoliko smo dodali thumbnail fotografiju, ubaciti je u file sistem i update-ovati vrstaPutovanja thumbnail tabelu
        this.vrstaPutovanjaService.updateThumbnailPhoto_If_Added(novaVrstaPutovanja, multipartFile);

        redirectAttributes.addFlashAttribute("messageSuccess", true);
        redirectAttributes.addFlashAttribute("novaVrstaPutovanja", novaVrstaPutovanja);

        return "redirect:/admin/listaVrstePutovanja";

    }



    @RequestMapping(value = "/admin/vrstaPutovanja/edit")
    public String updateVrstaPutovanjaShowForm(@RequestParam(value = "vrstaPutovanjaId") Integer vrstaPutovanjaId,
                                               RedirectAttributes redirectAttributes,
                                               Model model){
        VrstaPutovanja vrstaPutovanja = this.vrstaPutovanjaService.findById(vrstaPutovanjaId).get();

        model.addAttribute("vrstaPutovanja", vrstaPutovanja);

        return "vrstaPutovanja/edit";

    }


    @RequestMapping(value = "/admin/vrstaPutovanja/update", method = RequestMethod.POST)
    public String updateVrstaPutovanja(@Valid @ModelAttribute(value = "vrstaPutovanja") VrstaPutovanja vrstaPutovanja,
                                       BindingResult result, RedirectAttributes redirectAttributes,
                                       @RequestParam(value = "thumbnail", required = false) MultipartFile multipartFile) throws IOException{

        if(result.hasErrors()){
            return "vrstaPutovanja/edit";
        }

        this.vrstaPutovanjaService.update(vrstaPutovanja);

        //Ukoliko smo dodali novu thumbnail fotografiju, ubaciti je u file sistem i update-ovati vrstaPutovanja thumbnail tabelu
        this.vrstaPutovanjaService.updateThumbnailPhoto_If_Added(vrstaPutovanja, multipartFile);


        redirectAttributes.addFlashAttribute("messageSuccessEdit", true);
        redirectAttributes.addFlashAttribute("vrstaPutovanjaEditovan", vrstaPutovanja);

        return "redirect:/admin/listaVrstePutovanja";

    }

    @RequestMapping(value = "/admin/vrstaPutovanja/deleteThumbnailPhoto")
    public String deleteThumbnailPhotoVrstePutovanja(@RequestParam(value = "vrstaPutovanjaId") Integer vrstaPutovanjaId,
                                                     RedirectAttributes redirectAttributes) throws IOException {

        VrstaPutovanja vrstaPutovanja = this.vrstaPutovanjaService.findById(vrstaPutovanjaId).get();

        //Sada treba obrisati thumbnail fotografiju iz file sistema aplikacije, pa zatim obrisati u tabeli u bazi
        String fileName = vrstaPutovanja.getThumbnailPhoto();
        String uploadDir = "vrstePutovanja/vrstaPutovanjaId-" + vrstaPutovanja.getVrstaPutovanjaId();
        FileUploadUtil.deleteSpecificFile(uploadDir, fileName);
        //KRAJ brisanja thumbnail fotografije iz file sistema

        //Sada jos da ispraznimo thumbnail photo odgovarajucu tabelu
        vrstaPutovanja.setThumbnailPhoto(null);
        this.vrstaPutovanjaService.update(vrstaPutovanja);

        redirectAttributes.addFlashAttribute("ThumbnailPhotoDeleteMsg", "Uspešno ste obrisali thumbnail fotografiju! Sada možete dodati novu.");

        redirectAttributes.addAttribute("vrstaPutovanjaId", vrstaPutovanja.getVrstaPutovanjaId());
        return "redirect:/admin/vrstaPutovanja/edit";

    }

    @RequestMapping(value = "/admin/vrstaPutovanja/delete")
    public String deleteVrstaPutovanja(@RequestParam(value = "vrstaPutovanjaId") Integer vrstaPutovanjaId,
                                       RedirectAttributes redirectAttributes) throws IOException{


        //da bismo obrisali vrstu putovanja, pored reda u tabeli, moramo da obrisemo i direktorijum vezan za tu vrstu putovanja u file sistemu!
        VrstaPutovanja vrstaPutovanja = this.vrstaPutovanjaService.findById(vrstaPutovanjaId).get();
        String uploadDir = "vrstePutovanja/vrstaPutovanjaId-" + vrstaPutovanja.getVrstaPutovanjaId();
        FileUploadUtil.deleteDirectory(uploadDir);

        this.vrstaPutovanjaService.deleteById(vrstaPutovanja.getVrstaPutovanjaId());

        redirectAttributes.addFlashAttribute("messageSuccessDelete", true);
        redirectAttributes.addFlashAttribute("vrstaPutovanjaObrisana", vrstaPutovanja);

        return "redirect:/admin/listaVrstePutovanja";


    }





    //-----DEO ZA GOSTA I KORISNIKA-----
    @RequestMapping(value = "/vrstaPutovanja/{vrstaPutovanjaId}")
    public String vrstaPutovanjaShowPage(@PathVariable(value = "vrstaPutovanjaId") Integer vrstaPutovanjaId,
                                         Model model){

        Klub klub = this.klubService.findById(klubId).get();
        model.addAttribute("klub", klub);

        VrstaPutovanja vrstaPutovanja = this. vrstaPutovanjaService.findById(vrstaPutovanjaId).get();

        model.addAttribute("vrstaPutovanja", vrstaPutovanja);

        //TO-DO: Treba da pronadjemo sve aranzmane koje su te vrste putovanja, mozda bez obzira na
        //broj putovanja koje imaju, za one koje nemaju nijedno putovanje, ispisacemo poruku u delu za predstojeca putovanja
        //da ih trenutno nema, i da moze da se kontaktira klub da se raspita u vezi sa tim kad ce biti novo putovanje za taj aranzman
        List<Aranzman> listaAranzmanaVrstePutovanja = this.aranzmanService.findByVrstaPutovanjaVrstaPutovanjaId(vrstaPutovanja.getVrstaPutovanjaId());

        model.addAttribute("listaAranzmanaVrstePutovanja", listaAranzmanaVrstePutovanja);

        return "vrstaPutovanja/vrstaPutovanjaFrontPage";


    }


}
