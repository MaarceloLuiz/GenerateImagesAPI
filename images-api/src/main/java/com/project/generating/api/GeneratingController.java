package com.project.generating.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class GeneratingController {


    //CRUD (Create, Read, Update e Delete)
    @Autowired
    private ImagesRepository repository;

    @GetMapping(value="/helloworld-test")
    public String processing() {
        return "Hello World!";
    }

    @GetMapping("/generating")
    public List<Informations> getInformations() {
        List<Informations> informations = repository.findByOrderByRanking();
        return informations;
    }

    @PostMapping("/generating")
    public ResponseEntity<Informations> registerImage(@RequestBody Informations information ){
        Informations informationSave = repository.save(information);
        return new ResponseEntity<>(informationSave, HttpStatus.CREATED);
    }

    @GetMapping("/generating/{id}")
    public Informations gettingImageById(@PathVariable String id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/generating/{id}")
    public Informations updateImages(@PathVariable String id, @RequestBody Informations information){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        information.setId(id);
        Informations informationSave = repository.save(information);
        return informationSave;
    }

    @DeleteMapping("/generating/{id}")
    public void deleteImages(@PathVariable String id){
        repository.deleteById(id);
    }
}