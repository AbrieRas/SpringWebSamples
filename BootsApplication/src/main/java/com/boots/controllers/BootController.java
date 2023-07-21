package com.boots.controllers;

import java.lang.Iterable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.boots.entities.Boot;
import com.boots.enums.BootType;
import com.boots.repositories.BootRepository;

@RestController
@RequestMapping("/api/v1/boots")
public class BootController {
    private final BootRepository bootRepository;

    public BootController(final BootRepository bootRepository) {
        this.bootRepository = bootRepository;
    }

    @GetMapping("/")
    public Iterable<Boot> getAllBoots() {
        return this.bootRepository.findAll();
    }

    @GetMapping("/types")
    public List<BootType> getBootTypes() {
        return Arrays.asList(BootType.values());
    }

    @PostMapping("/")
    public Boot addBoot(@RequestBody Boot boot) {
        return this.bootRepository.save(boot);
    }

    @DeleteMapping("/{id}")
    public Boot deleteBoot(@PathVariable("id") Integer id) {
        Optional<Boot> bootOptional = this.bootRepository.findById(id);
        if (!bootOptional.isPresent()) {
            return null;
        }

        Boot bootToDelete = bootOptional.get();
        this.bootRepository.delete(bootToDelete);
        return bootToDelete;
    }

    @PutMapping("/{id}/quantity/increment")
    public Boot incrementQuantity(@PathVariable("id") Integer id) {
        Optional<Boot> bootOptional = this.bootRepository.findById(id);
        if (!bootOptional.isPresent()) {
            return null;
        }

        Boot bootToUpdate = bootOptional.get();
        int newQuantity = bootToUpdate.getQuantity() + 1;
        bootToUpdate.setQuantity(newQuantity);

        Boot bootToSave = this.bootRepository.save(bootToUpdate);
        return bootToSave;
    }

    @PutMapping("/{id}/quantity/decrement")
    public Boot decrementQuantity(@PathVariable("id") Integer id) {
        Optional<Boot> bootOptional = this.bootRepository.findById(id);
        if (!bootOptional.isPresent()) {
            return null;
        }

        Boot bootToUpdate = bootOptional.get();
        int newQuantity = bootToUpdate.getQuantity() - 1;
        bootToUpdate.setQuantity(newQuantity);

        Boot bootToSave = this.bootRepository.save(bootToUpdate);
        return bootToSave;
    }

    @GetMapping("/search")
    public List<Boot> searchBoots(
            @RequestParam(required = false) String material,
            @RequestParam(required = false) BootType type,
            @RequestParam(required = false) Float size,
            @RequestParam(required = false, name = "quantity") Integer minQuantity
    ) {
        if (Objects.nonNull(material)) {
            if (Objects.nonNull(type) && Objects.nonNull(size) && Objects.nonNull(minQuantity)) {
                // [DONE] call the repository method that accepts a material, type, size, and minimum
                // [DONE] quantity
                return this.bootRepository.findByMaterialAndTypeAndSizeAndQuantityGreaterThan(material, type, size, minQuantity);
            } else if (Objects.nonNull(type) && Objects.nonNull(size)) {
                // [DONE] call the repository method that accepts a material, size, and type
                return this.bootRepository.findByMaterialAndSizeAndType(material, size, type);
            } else if (Objects.nonNull(type) && Objects.nonNull(minQuantity)) {
                // [DONE] call the repository method that accepts a material, a type, and a minimum
                // [DONE] quantity
                return this.bootRepository.findByMaterialAndTypeAndQuantityGreaterThan(material, type, minQuantity);
            } else if (Objects.nonNull(type)) {
                // [DONE] call the repository method that accepts a material and a type
                return this.bootRepository.findByMaterialAndType(material, type);
            } else {
                // [DONE] call the repository method that accepts only a material
                return this.bootRepository.findByMaterial(material);
            }
        } else if (Objects.nonNull(type)) {
            if (Objects.nonNull(size) && Objects.nonNull(minQuantity)) {
                // [DONE] call the repository method that accepts a type, size, and minimum quantity
                return this.bootRepository.findByTypeAndSizeAndQuantityGreaterThan(type, size, minQuantity);
            } else if (Objects.nonNull(size)) {
                // [DONE] call the repository method that accepts a type and a size
                return this.bootRepository.findByTypeAndSize(type, size);
            } else if (Objects.nonNull(minQuantity)) {
                // [DONE] call the repository method that accepts a type and a minimum quantity
                return this.bootRepository.findByTypeAndQuantityGreaterThan(type, minQuantity);
            } else {
                // [DONE] call the repository method that accept only a type
                return this.bootRepository.findByType(type);
            }
        } else if (Objects.nonNull(size)) {
            if (Objects.nonNull(minQuantity)) {
                // [DONE] call the repository method that accepts a size and a minimum quantity
                return this.bootRepository.findBySizeAndQuantityGreaterThan(size, minQuantity);
            } else {
                // [DONE] call the repository method that accepts only a size
                return this.bootRepository.findBySize(size);
            }
        } else if (Objects.nonNull(minQuantity)) {
            // [DONE] call the repository method that accepts only a minimum quantity
            return this.bootRepository.findByQuantityGreaterThan(minQuantity);
        } else {
            return new ArrayList<>();
        }
    }

}