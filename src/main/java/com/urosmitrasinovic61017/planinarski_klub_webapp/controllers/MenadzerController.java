package com.urosmitrasinovic61017.planinarski_klub_webapp.controllers;

import com.urosmitrasinovic61017.planinarski_klub_webapp.models.AranzmanIzvestajDto;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.BuducaPutovanjaIzvestajDto;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.ProizvodIzvestajDto;
import com.urosmitrasinovic61017.planinarski_klub_webapp.models.ProslaPutovanjaIzvestajDto;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.AranzmanService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.ProizvodService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.PutovanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MenadzerController {

    @Autowired
    private ProizvodService proizvodService;

    @Autowired
    private PutovanjeService putovanjeService;

    @Autowired
    private AranzmanService aranzmanService;

    @RequestMapping(value = "/manager/listaProizvoda")
    public String pocetnaMenadzerListaProizvoda(Model model){

        //List<ProizvodIzvestajDto> listaProizvodaIzvestaja = this.proizvodService.getProizvodIzvestajZaMenadzera();

        //model.addAttribute("listaIzvestajaProizvoda", listaProizvodaIzvestaja);



        //default values of pageNo, sortField etc.
        return this.findPaginatedProizvodIzvestaj(1, "proizvodId", "asc", model);
    }

    @RequestMapping(value = "/manager/listaProizvoda/page/{pageNo}")
    public String findPaginatedProizvodIzvestaj(@PathVariable(value = "pageNo") int pageNo,
                                                @RequestParam(value = "sortField") String sortField,
                                                @RequestParam(value = "sortDir") String sortDir,
                                                Model model){
        int pageSize = 5;

        Page<ProizvodIzvestajDto> page = this.proizvodService.findPaginatedProizvodIzvestaje(pageNo, pageSize, sortField, sortDir);
        List<ProizvodIzvestajDto> listaIzvestajaProizvoda = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listaIzvestajaProizvoda", listaIzvestajaProizvoda);



        return "menadzer/proizvodi";

    }

    @RequestMapping(value = "/manager/izvestajBuducaPutovanja")
    public String izvestajBuducaPutovanja(Model model){

        return this.findPaginatedBuducaPutovanjaIzvestaje(1, "putovanjeId", "asc", model);
    }

    @RequestMapping(value = "manager/izvestajBuducaPutovanja/page/{pageNo}")
    public String findPaginatedBuducaPutovanjaIzvestaje(@PathVariable(value = "pageNo") Integer pageNo,
                                                        @RequestParam(value = "sortField") String sortField,
                                                        @RequestParam(value = "sortDir") String sortDir,
                                                        Model model){
        int pageSize = 5;

        Page<BuducaPutovanjaIzvestajDto> page = this.putovanjeService.findPaginatedBuducaPutovanjaIzvestaje(pageNo, pageSize, sortField, sortDir);

        List<BuducaPutovanjaIzvestajDto> listaIzvestajaBuducihPutovanja = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listaIzvestajaBuducihPutovanja", listaIzvestajaBuducihPutovanja);

        return "menadzer/izvestaj-buducih-putovanja";

    }


    @RequestMapping(value = "/manager/izvestajProslaPutovanja")
    public String izvestajProslaPutovanja(Model model){

        return this.findPaginatedProslaPutovanjaIzvestaje(1, "putovanjeId", "asc", model);
    }

    @RequestMapping(value = "/manager/izvestajProslaPutovanja/page/{pageNo}")
    public String findPaginatedProslaPutovanjaIzvestaje(@PathVariable(value = "pageNo") int pageNo,
                                                        @RequestParam(value = "sortField") String sortField,
                                                        @RequestParam(value = "sortDir") String sortDir,
                                                        Model model){
        int pageSize = 5;

        Page<ProslaPutovanjaIzvestajDto> page = this.putovanjeService.findPaginatedProslaPutovanjaIzvestaje(pageNo, pageSize, sortField, sortDir);

        List<ProslaPutovanjaIzvestajDto> listaProslihPutovanjaIzvestaja = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listaProslihPutovanjaIzvestaja", listaProslihPutovanjaIzvestaja);


        return "menadzer/izvestaj-prosla-putovanja";
    }

    @RequestMapping(value = "/manager/aranzmani")
    public String izvestajAranzmani(Model model){
        return this.findPaginatedIzvestajeAranzmana(1, "aranzmanId", "asc", model);
    }

    @RequestMapping(value = "/manager/aranzmani/page/{pageNo}")
    public String findPaginatedIzvestajeAranzmana(@PathVariable(value = "pageNo") int pageNo,
                                                  @RequestParam(value = "sortField") String sortField,
                                                  @RequestParam(value = "sortDir") String sortDir,
                                                  Model model){

        int pageSize = 5;

        Page<AranzmanIzvestajDto> page = this.aranzmanService.findPaginatedAranzmanIzvestaje(pageNo, pageSize, sortField, sortDir);

        List<AranzmanIzvestajDto> listaIzvestajaAranzmana = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listaIzvestajaAranzmana", listaIzvestajaAranzmana);

        return "menadzer/izvestaj-aranzmani";

    }










}
