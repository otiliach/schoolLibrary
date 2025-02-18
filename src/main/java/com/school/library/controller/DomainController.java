package com.school.library.controller;

import com.school.library.model.Domain;
import com.school.library.service.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/library")
public class DomainController {
    private final LibraryService libraryService;

    public DomainController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/domain/save")
    public @ResponseBody Domain saveDomain(@RequestBody Domain domain) {
        return libraryService.saveDomain(domain);
    }

    @PutMapping("/domain/save/{id}")
    public @ResponseBody Domain updateDomain (@RequestBody Domain domain, @PathVariable(value = "id") Long  domainId) {
        return libraryService.updateDomain(domain, domainId);
    }
    @GetMapping("/domain/{id}")
    public @ResponseBody Domain getDomainById(@PathVariable(value = "id") String  domainId) {
        return libraryService.getDomainById(Long.valueOf(domainId));
    }

    @GetMapping("/all-domains")
    public @ResponseBody List<Domain> getAllDomains() {
        return libraryService.getAllDomains();
    }

    @DeleteMapping("/domain/delete/{id}")
    public @ResponseBody void deleteDomain(@PathVariable(value = "id") String  domainId) {
        libraryService.deleteDomain(Long.valueOf(domainId));
    }
}
