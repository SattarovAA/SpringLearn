package com.example.contactslist.controller;

import com.example.contactslist.dto.ContactDto;
import com.example.contactslist.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/contacts")
@Slf4j
public class ContactController {
    private final ContactService contactService;

    @GetMapping("")
    public String getAll(Model model) {
        log.info("getAll");
        model.addAttribute("contacts", contactService.findAll());
        return "contacts/index";
    }

    @GetMapping(path = "/edit")
    public String add(Model model) {
        log.info("add");
        model.addAttribute("contact", new ContactDto());
        return "contacts/edit";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        log.info("edit");
        model.addAttribute("contact", contactService.findById(id));
        return "contacts/edit";
    }

    @PostMapping()
    public String create(@ModelAttribute ContactDto contactDto) {
        log.info("create");
        System.out.println(contactDto);
        if (contactDto.getId() != null) {
            contactService.update(contactDto.getId(), contactDto);
        } else {
            contactService.save(contactDto);
        }
        return "redirect:/contacts";
    }

    @PatchMapping("/{id}")
    public String replace(@PathVariable int id,
                          @ModelAttribute ContactDto contactDto) {
        log.info("replace");
        System.out.println(contactDto);
        contactService.update(id, contactDto);
        return "redirect:/contacts";
    }

    @DeleteMapping(path = "/{id}")
    public String deleteById(@PathVariable Long id) {
        log.info("delete");
        contactService.deleteById(id);
        return "redirect:/contacts";
    }
}
