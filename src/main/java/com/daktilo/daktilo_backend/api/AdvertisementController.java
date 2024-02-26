package com.daktilo.daktilo_backend.api;

import com.daktilo.daktilo_backend.entity.Advertisement;
import com.daktilo.daktilo_backend.repository.AdvertisementRepository;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("advertisement")
public class AdvertisementController {

    @Autowired
    AdvertisementRepository advertisementRepository;

    @GetMapping
    @Transactional
    public ResponseEntity getAll(
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size
    ){
        try{
            Pageable pageRequest = PageRequest.of(page,size);
            Page<Advertisement> ads = advertisementRepository.findAll(pageRequest);

            if (ads!=null && !ads.isEmpty()){
                return ResponseEntity.ok(ads);
            }else{
                return ResponseEntity.badRequest().body("Reklamlar bulunamadı.");
            }
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Reklamları gösterirken bir problem oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Reklamları gösterirken beklenmedik bir problem oluştu");
        }
    }

    @GetMapping("/{status}")
    @Transactional
    public ResponseEntity getAllByStatus(
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size,
            @PathVariable(name="status") boolean status
    ){
        try{
            Pageable pageRequest = PageRequest.of(page,size);
            Page<Advertisement> activeAds = advertisementRepository.findAllByIsActive(pageRequest,status);

            if (activeAds!=null && !activeAds.isEmpty()){
                return ResponseEntity.ok(activeAds);
            }else{
                return ResponseEntity.badRequest().body("Aktif reklamlar bulunamadı.");
            }
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Aktif reklamları gösterirken bir problem oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Aktif reklamları gösterirken beklenmedik bir problem oluştu");
        }
    }

    @GetMapping("/{location}")
    @Transactional
    public ResponseEntity getAllByLocation(
            @RequestParam(name="page", defaultValue="0") int page,
            @RequestParam(name="size", defaultValue="3") int size,
            @PathVariable(name="location") String location
    ){
        try{
            Pageable pageRequest = PageRequest.of(page,size);
            Page<Advertisement> activeAds = advertisementRepository.findAllByPlace(pageRequest,location);

            if (activeAds!=null && !activeAds.isEmpty()){
                return ResponseEntity.ok(activeAds);
            }else{
                return ResponseEntity.badRequest().body("Bölgedeki Reklamlar bulunamadı.");
            }
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Bölgedeki reklamları gösterirken bir problem oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Bölgedeki reklamları gösterirken beklenmedik bir problem oluştu");
        }
    }

    @PostMapping("/add")
    @Transactional
    public ResponseEntity addAdvertisement(@RequestBody Advertisement advertisement){
        try {
            Advertisement ad = advertisementRepository.save(advertisement);

            return ResponseEntity.ok(advertisement);
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Reklamı eklerken bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Reklamı eklerken beklenmedik bir hata oluştu.");
        }
    }

    @PutMapping("/edit/{id}")
    @Transactional
    public ResponseEntity editAdvertisement(@RequestBody Advertisement advertisement,
                                            @PathVariable("id") UUID id){
        try {
            Advertisement ad = advertisementRepository.findById(id).orElse(null);
            if(ad == null){
                return ResponseEntity.badRequest().body("Aranan reklam bulunamadı");
            }
            ad = advertisementRepository.save(ad);
            return ResponseEntity.ok(ad);
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Reklamı güncellerken bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Reklamı güncellerken beklenmedik bir hata oluştu.");
        }
    }

    @DeleteMapping(path="/delete/{id}")
    @Transactional
    public ResponseEntity deleteAdvertisement(@PathVariable(name="id") UUID id){
        try {
            Advertisement advertisement = advertisementRepository.findById(id).orElse(null);
            if(advertisement == null){
                return ResponseEntity.badRequest().body("Silinmek istenen reklam bulunamadı");
            }
            advertisementRepository.deleteById(id);
            return ResponseEntity.ok().body("Silme işlemi başarılı");
        }catch(PersistenceException p){
            p.printStackTrace();
            return ResponseEntity.badRequest().body("Silme işlemi sırasında bir hata oluştu");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Silme işlemi sırasında beklenmedik bir hata oluştu.");
        }
    }

}
