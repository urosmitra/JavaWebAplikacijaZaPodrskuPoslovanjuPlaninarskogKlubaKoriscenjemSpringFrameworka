package com.urosmitrasinovic61017.planinarski_klub_webapp.controllers;

import com.google.gson.*;
import com.urosmitrasinovic61017.planinarski_klub_webapp.config.FileUploadUtil;
import com.urosmitrasinovic61017.planinarski_klub_webapp.config.MyExclusionStrategy;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.Proizvod;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.VrstaProizvoda;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.ProizvodService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.VrstaProizvodaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ProizvodController {

    @Autowired
    private ProizvodService proizvodService;

    @Autowired
    private VrstaProizvodaService vrstaProizvodaService;

    @RequestMapping(value = "/admin/listaProizvoda")
    public String showAllProizvodi(Model model){

        List<Proizvod> listaSvihProizvoda = this.proizvodService.getAllProizvodi();

        model.addAttribute("listaSvihProizvoda", listaSvihProizvoda);

        return "proizvod/all";

    }

    @RequestMapping(value = "/admin/proizvod/addNew")
    public String addNewProizvodShowForm(Model model){

        List<VrstaProizvoda> listaVrsteProizvoda = this.vrstaProizvodaService.getAllVrsteProizvoda();
        model.addAttribute("listaVrsteProizvoda", listaVrsteProizvoda);


        Proizvod noviProizvod = new Proizvod();
        model.addAttribute("noviProizvod", noviProizvod);

        return "proizvod/addNew";

    }

    @RequestMapping(value = "/admin/proizvod/addNew", method = RequestMethod.POST)
    public String addNewProizvod(@Valid @ModelAttribute(value = "noviProizvod") Proizvod noviProizvod,
                                 BindingResult result, Model model,
                                 @RequestParam(value = "slika" ,required = false) MultipartFile multipartFile,
                                 RedirectAttributes redirectAttributes) throws IOException {

        if(result.hasErrors()){

            List<VrstaProizvoda> listaVrsteProizvoda = this.vrstaProizvodaService.getAllVrsteProizvoda();
            model.addAttribute("listaVrsteProizvoda", listaVrsteProizvoda);

            return "proizvod/addNew";
        }

        this.proizvodService.save(noviProizvod);

        //Ukoliko smo dodali thumbnail fotografiju, ubaciti je u file sistem i update-ovati Proizvod thumbnailPhoto tabelu
        this.proizvodService.updateThumbnailPhoto_If_Added(noviProizvod, multipartFile);

        redirectAttributes.addFlashAttribute("messageSuccess", true);
        redirectAttributes.addFlashAttribute("noviProizvod", noviProizvod);

        return "redirect:/admin/listaProizvoda";

    }


    @RequestMapping(value = "/admin/proizvod/edit")
    public String updateProizvodShowForm(@RequestParam(value = "proizvodId") Integer proizvodId,
                                         Model model){

        Proizvod proizvod = this.proizvodService.findById(proizvodId).get();

        model.addAttribute("proizvod", proizvod);

        return "proizvod/edit";

    }


    @RequestMapping(value = "/admin/proizvod/update", method = RequestMethod.POST)
    public String updateProizvod(@Valid @ModelAttribute(value = "proizvod") Proizvod proizvod,
                                 BindingResult result, RedirectAttributes redirectAttributes,
                                 @RequestParam(value = "thumbnail", required = false) MultipartFile multipartFile) throws  IOException{


        if(result.hasErrors()){
            return "proizvod/edit";
        }

        this.proizvodService.update(proizvod);

        //Ukoliko smo dodali novu thumbnail fotografiju, ubaciti je u file sistem i update-ovati Proizvod thumbnail tabelu
        this.proizvodService.updateThumbnailPhoto_If_Added(proizvod, multipartFile);

        redirectAttributes.addFlashAttribute("messageSuccessEdit", true);
        redirectAttributes.addFlashAttribute("proizvodEditovan", proizvod);

        return "redirect:/admin/listaProizvoda";

    }


    @RequestMapping(value = "/admin/proizvod/deleteThumbnailPhoto")
    public String deleteThumbnailPhotoProizvoda(@RequestParam(value = "proizvodId") Integer proizvodId,
                                                RedirectAttributes redirectAttributes) throws IOException {

        Proizvod proizvod = this.proizvodService.findById(proizvodId).get();

        //obirisi fotografiju iz file sistema (samo file) i update-ovati u bazi tabelu ThumbnailPhoto na null
        String fileName = proizvod.getThumbnailPhoto();
        String uploadDir = "slikeProizvodi/proizvodId-" + proizvod.getProizvodId();
        FileUploadUtil.deleteSpecificFile(uploadDir, fileName);
        //KRAJ brisanja thumbnail fotografije iz file sistema

        proizvod.setThumbnailPhoto(null);
        this.proizvodService.update(proizvod);

        redirectAttributes.addFlashAttribute("ThumbnailPhotoDeleteMsg", "Uspešno ste obrisali thumbnail fotografiju! Sada možete dodati novu.");

        redirectAttributes.addAttribute("proizvodId", proizvod.getProizvodId());
        return "redirect:/admin/proizvod/edit";

    }


    @RequestMapping(value = "/admin/proizvod/delete")
    public String deleteProizvod(@RequestParam(value = "proizvodId") Integer proizvodId,
                                 RedirectAttributes redirectAttributes) throws IOException{

        Proizvod proizvodZaBrisanje = this.proizvodService.findById(proizvodId).get();

        String uploadDir = "slikeProizvodi/proizvodId-" + proizvodZaBrisanje.getProizvodId();
        FileUploadUtil.deleteDirectory(uploadDir);

        this.proizvodService.deleteById(proizvodZaBrisanje.getProizvodId());

        redirectAttributes.addFlashAttribute("messageSuccessDelete", true);
        redirectAttributes.addFlashAttribute("proizvodObrisan", proizvodZaBrisanje);

        return "redirect:/admin/listaProizvoda";


    }

    @ResponseBody
    @RequestMapping(value = {"/ucitajFiltriraneProizvode/{keyword}", "/ucitajFiltriraneProizvode/"})
    public String ucitajFiltriraneProizvode(@PathVariable(name = "keyword", required = false) String keyword){

//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(LocalDate.class, new DateTimeSerializer())
//                .registerTypeAdapter(LocalDate.class, new DateTimeDeserializer()).create();

        Gson gson = new GsonBuilder().setExclusionStrategies(new MyExclusionStrategy()).create(); //Gson is an open-source Java library to serialize and deserialize (converting between) Java objects to JSON.

        List<Proizvod> filtriraniProizvodi = this.proizvodService.findByKeyword(keyword);

        List<Proizvod> sviProizvodi = this.proizvodService.getAllProizvodi();

        if(keyword != null) { //mozda ovako, a mozda i drugacije ako se uopste ni ne poziva ajax kada obrisemo polje za search
            return gson.toJson(filtriraniProizvodi);
        }
        return gson.toJson(sviProizvodi);
    }

    private class DateTimeSerializer implements JsonSerializer<LocalDate>{

        @Override
        public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(localDate.toString());
        }
    }

    private class DateTimeDeserializer implements JsonDeserializer<LocalDate>{

        @Override
        public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            String jsonString = jsonElement.getAsJsonPrimitive().getAsString();
            return LocalDate.parse(jsonString);
        }
    }




}
