package br.com.dashboard.controller;

import br.com.dashboard.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("message", "Lucas");
        model.addAttribute("clientes", this.clienteService.findAll());





        return "index";
    }
}