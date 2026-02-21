package com.urosmitrasinovic61017.planinarski_klub_webapp.services.impl;


import com.urosmitrasinovic61017.planinarski_klub_webapp.models.*;
import com.urosmitrasinovic61017.planinarski_klub_webapp.repositories.PutovanjeRepository;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.AranzmanService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.PutovanjeService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.services.RezervacijaPutovanjeService;
import com.urosmitrasinovic61017.planinarski_klub_webapp.validation.PutovanjeIzmedjuDatumaAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PutovanjeServiceImpl implements PutovanjeService {

    @Autowired
    private PutovanjeRepository putovanjeRepository;

    @Autowired
    private RezervacijaPutovanjeService rezervacijaService;

    @Autowired
    private AranzmanService aranzmanService;

    @Override
    public List<Putovanje> getAllPutovanja() {
        return this.putovanjeRepository.findAll();
    }

    @Override
    public Putovanje save(Putovanje putovanje){
        return this.putovanjeRepository.save(putovanje);
    }

    @Override
    public Putovanje update(Putovanje putovanje){
            return this.putovanjeRepository.save(putovanje);
    }
    
    @Override
    public void deleteById(Integer id) {
        this.putovanjeRepository.deleteById(id);
    }

    @Override
    public Optional<Putovanje> findById(Integer id) {
        return this.putovanjeRepository.findById(id);
    }

    @Override
    public List<PutovanjeStatus> getListaStatusaPutovanja() {
        List<PutovanjeStatus> listaPutovanjaStatusa = Arrays.asList(PutovanjeStatus.values());
        return listaPutovanjaStatusa;
    }

    @Override
    public void incrementBrojPrijavljenih(Integer putovanjeId) {
        this.putovanjeRepository.incrementBrojPrijavljenih(putovanjeId);
    }

    @Override
    public void smanjiBrojPrijavljenih(Integer putovanjeId) {
        this.putovanjeRepository.smanjiBrojPrijavljenih(putovanjeId);
    }

    @Override
    public boolean podesavanjeStatusaPutovanjaPotvrdjeno(Putovanje putovanje) {
        Integer brojPrijavljenihUpdateovan = putovanje.getBrojPrijavljenih() + 1;
        if(brojPrijavljenihUpdateovan.equals(putovanje.getAranzman().getMinPutnika()) || brojPrijavljenihUpdateovan > putovanje.getAranzman().getMinPutnika()){
            this.putovanjeRepository.podesavanjeStatusaPutovanjaPotvrdjeno(putovanje.getPutovanjeId());
            return true;
        }
        return false;
    }

    @Override
    public boolean podesavanjeStatusaPutovanjaNepotvrdjeno(Putovanje putovanje) {
        Integer brojPrijavljenihUpdetovan = putovanje.getBrojPrijavljenih() - 1;
        if(brojPrijavljenihUpdetovan < putovanje.getAranzman().getMinPutnika()){
            this.putovanjeRepository.podesavanjeStatusaPutovanjaNepotvrdjeno(putovanje.getPutovanjeId());
            return true;
        }
        return false;
    }

    @Override
    public List<Putovanje> getBuducaPutovanjaLimitirano() {
        return this.putovanjeRepository.getBuducaPutovanjaLimitirano();
    }

    @Override
    public List<Putovanje> getAllBuducaPutovanjaAranzmanaAvailableZaKorisnika(Integer aranzmanId) {
        return this.putovanjeRepository.getAllBuducaPutovanjaAranzmanaAvailableZaKorisnika(aranzmanId);
    }

    @Override
    public Integer getBrojSlobodnihMestaPutovanja(Putovanje putovanje) {
        Aranzman aranzman = putovanje.getAranzman();
        Integer maxBrojMesta = aranzman.getMaxPutnika();
        Integer brojPrijavljenih = putovanje.getBrojPrijavljenih();
        return maxBrojMesta - brojPrijavljenih;
    }



    @Override
    public List<Putovanje> getAllPutovanjaAranzmana(Integer aranzmanId) {
        return this.putovanjeRepository.findByAranzmanAranzmanId(aranzmanId);
    }

    @Override
    public List<Putovanje> getAllBuducaPutovanjaAranzmana(Integer aranzmanId) {
        return this.putovanjeRepository.getAllBuducaPutovanjaAranzmana(aranzmanId);
    }

    @Override
    public List<Putovanje> getAllPutovanjaUTokuPotvrdjeno(Integer aranzmanId) {

        List<Putovanje> listaPutovanjaUToku =  this.putovanjeRepository.getAllPutovanjaAranzmanaUTokuPotvrdjeno(aranzmanId);

        this.podesavanjeStatusaPutovanjaUToku(listaPutovanjaUToku);


        return listaPutovanjaUToku;
    }

    @Override
    public Integer cenaPutovanjaAranzmana (Integer cenaAranzmana, Integer popustPutovanja) {
        Integer cena = cenaAranzmana;
        Integer popust;
        if(popustPutovanja != null && popustPutovanja != 0){
            popust = cena * popustPutovanja/100;
            cena -= popust;
        }
        return cena;
    }

    @Override
    public Integer getNajmanjaCenaPutovanjaAranzmana(Integer aranzmanId) {

        Aranzman aranzman = this.aranzmanService.findById(aranzmanId).get();

        //uzeti sve putovanje aranzmana
        List<Putovanje> listaPutovanjaAranzmanaPonudaZaKorisnika = this.putovanjeRepository.getAllBuducaPutovanjaAranzmanaAvailableZaKorisnika(aranzmanId);

        List<Integer> listaCenePutovanja = new ArrayList<>();

        //izracunati im cene svima cene i ubaciti u listu cena
        for(Putovanje p : listaPutovanjaAranzmanaPonudaZaKorisnika){
            Integer cenaPutovanja = this.cenaPutovanjaAranzmana(aranzman.getCena(), p.getPopust());

            listaCenePutovanja.add(cenaPutovanja);

        }

        //pronaci najmanju vrednost iz lista cene putovanja
        Integer minCena = aranzman.getCena();

        for(Integer cena : listaCenePutovanja){
            if (cena < minCena){
                minCena = cena;
            }
        }

        return minCena;

    }

    @Override
    public boolean daLiImaPopustaZaAranzman(Integer aranzmanId) {
        Aranzman aranzman = this.aranzmanService.findById(aranzmanId).get();

        List<Putovanje> listaPutovanjaAranzmanaPonudaZaKorisnika = this.putovanjeRepository.getAllBuducaPutovanjaAranzmanaAvailableZaKorisnika(aranzmanId);

        for(Putovanje p : listaPutovanjaAranzmanaPonudaZaKorisnika){
            if(p.getPopust() != 0){
                return true;
            }
        }

        return false;
    }




    @Override
    public List<Putovanje> getIstorijaPutovanjaOdrzanih(Integer aranzmanId) {

        List<Putovanje> listaProteklihPutovanja = this.putovanjeRepository.getIstorijaPutovanjaOdrzanih(aranzmanId);

        this.podesavanjeStatusaPutovanjaOdrzanih(listaProteklihPutovanja);

        //postavljanje statusa rezervacija na PUTOVANJE_ODRZANO, ukoliko nije postavljen taj status
        for(Putovanje p : listaProteklihPutovanja){
            List<RezervacijaPutovanja> listaRezervacija = this.rezervacijaService.getAllRezervacijePutovanja(p.getPutovanjeId());
            this.rezervacijaService.setStatusRezervacijaPutovanjeOdrzano(listaRezervacija);
        }

        return listaProteklihPutovanja;
    }

    @Override
    public List<Putovanje> getOtkazanaPutovanjaAranzmana(Integer aranzmanId) {

        this.podesavanjePutovanjaNepotvrdjenihProteklih(aranzmanId);

        List<Putovanje> listaPutovanjaOtkazanih = this.putovanjeRepository.getOtkazanaPutovanjaAranzmana(aranzmanId);

        //sledece moramo da postavimo status rezervacija na PUTOVANJE_OTKAZANO, ukoliko nije takav status vec tu
        for(Putovanje p : listaPutovanjaOtkazanih){
            List<RezervacijaPutovanja> listaRezervacijaPutovanja = this.rezervacijaService.getAllRezervacijePutovanja(p.getPutovanjeId());
            this.rezervacijaService.setStatusRezervacijaPutovanjeOtkazano(listaRezervacijaPutovanja);
        }

        return listaPutovanjaOtkazanih;
    }

    private void podesavanjePutovanjaNepotvrdjenihProteklih(Integer aranzmanId){
        List<Putovanje> listaPutovanjaNepotvrdjenihProslih = this.putovanjeRepository.getAllPutovanjaNepotvrdjenaProsla(aranzmanId);
        //sada svim tim putovanjima podesimo status na OTKAZANO
        this.podesavanjeStatusaPutovanjaOtkazano(listaPutovanjaNepotvrdjenihProslih);

    }

//    @Override
//    public boolean daLiSuSlobodniDatumiPutovanja(Putovanje putovanje) { //TO-DO: IZBACICEMO FUNKCIONALNOST DA DVA PUTOVANJA ISTIH ARANZMANA NE SMEJU BITI U RANGE-U SLICNIH ILI ISTIH DATUMA!!!!!!
//
//        LocalDate datumPolaska = putovanje.getDatumVremePolaska().toLocalDate();
//        LocalDate datumPovratka = putovanje.getDatumPovratka();
//
//        Aranzman aranzman = putovanje.getAranzman();
//
//        List<Putovanje> listaPutovanja = this.getAllBuducaPutovanjaAranzmana(aranzman.getAranzmanId());
//
//        //listaPutovanja.remove(putovanje); //removuj ovo putovanje iz liste ukoliko postoji, da ne bi proveravao svoje datume
//
//        for(Putovanje p : listaPutovanja){
//            LocalDate datumPolP = p.getDatumVremePolaska().toLocalDate();
//            if((datumPolaska.isAfter(datumPolP) && datumPolaska.isBefore(p.getDatumPovratka()))
//            || (datumPovratka.isAfter(datumPolP) && datumPovratka.isBefore(p.getDatumPovratka()))
//            || (datumPolaska.isEqual(datumPolP) && datumPovratka.isEqual(p.getDatumPovratka()))){
//                return false;
//            }
//        }
//
//        return true;
//
//    }



    //proveriti status ukoliko nije ODRZANO, onda postaviti na taj status i sacuvati u bazi
    private void podesavanjeStatusaPutovanjaOdrzanih(List<Putovanje> listaPutovanja){
        for(Putovanje putovanje : listaPutovanja){
            if(!(putovanje.getStatus().equals(PutovanjeStatus.ODRZANO))){
                this.putovanjeRepository.podesavanjeStatusaPutovanjaOdrzanih(putovanje.getPutovanjeId());
            }
        }
    }
    private void podesavanjeStatusaPutovanjaUToku(List<Putovanje> listaPutovanja){
        for(Putovanje putovanje : listaPutovanja){
            if(!(putovanje.getStatus().equals(PutovanjeStatus.U_TOKU))){
                this.putovanjeRepository.podesavanjeStatusaPutovanjaUToku(putovanje.getPutovanjeId());
            }
        }
    }

    private void podesavanjeStatusaPutovanjaOtkazano(List<Putovanje> listaPutovanja){
        for(Putovanje putovanje : listaPutovanja){
            if(!(putovanje.getStatus().equals(PutovanjeStatus.OTKAZANO))){
                this.putovanjeRepository.podesavanjeStatusaPutovanjaOtkazano(putovanje.getPutovanjeId());
            }
        }
    }


    //Pozivamo prilikom pravljenja novog putovanja,
    // isto i prilikom edita aranzmana, provera za trajanjeDana polje da li se promenilo
    private LocalDate podesavanjeDatumaPovratka(Integer trajanjeDani, LocalDateTime datumVremePolaska){
        LocalDate datumPolaska = datumVremePolaska.toLocalDate();
        LocalDate datumPovratka = datumPolaska.plusDays(trajanjeDani);
        return datumPovratka;
    }

    //MANAGER IZVESTAJI
    @Override
    public Page<BuducaPutovanjaIzvestajDto> getBuducaPutovanjaIzvestajePagination(Pageable pageable) {
        Page<BuducaPutovanjaIzvestajDto> buducaPutovanjaIzvestaji = this.putovanjeRepository.getAllBuducaPutovanjaIzvestajZaMenadzera(pageable);

        //cena sa popustom formula: (p.cena * (1-p.popust/100))
//        Integer cena;
//        Integer popust;
//        Integer popustVrednost;
//        for(BuducaPutovanjaIzvestajDto pid : buducaPutovanjaIzvestaji){
//            cena = pid.getCena();
//            popust = pid.getPopust();
//            if (popust != 0) {
//                popustVrednost = cena * popust/100;
//                cena -= popustVrednost;
//            }
//           pid.setCenaSaPopustom(cena);
//
//        }

        return buducaPutovanjaIzvestaji;
    }

    @Override
    public Page<BuducaPutovanjaIzvestajDto> findPaginatedBuducaPutovanjaIzvestaje(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.getBuducaPutovanjaIzvestajePagination(pageable);
    }

    @Override
    public Page<ProslaPutovanjaIzvestajDto> getProslaPutovanjaIzvestajePagination(Pageable pageable) {
        Page<ProslaPutovanjaIzvestajDto> proslaPutovanjaIzvestaji =  this.putovanjeRepository.getAllProslaPutovanjaIzvestajZaMenadzera(pageable);

        return proslaPutovanjaIzvestaji;
    }

    @Override
    public Page<ProslaPutovanjaIzvestajDto> findPaginatedProslaPutovanjaIzvestaje(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.getProslaPutovanjaIzvestajePagination(pageable);
    }


}
