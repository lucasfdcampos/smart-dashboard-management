package br.com.dashboard.controller;

import br.com.dashboard.service.ExtractNFeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private ExtractNFeService extractNFeService;

    @GetMapping("/")
    public ModelAndView index(ModelMap model) {
        // Conteudo da pagina
        model.addAttribute("conteudo", "dashboard");

        // -- Cards --
        // Caixa
        model.addAttribute("caixa", "20.500,00");
        // Metas
        model.addAttribute("metas", "63");
        // Recebidos (Mes)
        model.addAttribute("recebido_mes", "42.000,00");
        // A Receber (Mes)
        model.addAttribute("receber_mes", "17.000,00");
        // A Pagar (Mes)
        model.addAttribute("contas_pendentes", "7");
        model.addAttribute("pagar_mes", "13.250,00");
        // A Pagar (Dia)
        model.addAttribute("contas_pendentes_dia", "2");
        model.addAttribute("pagar_dia", "1.930,00");
        // Pago (Mes)
        model.addAttribute("pago_mes", "11.750,00");
        // Pago (Dia)
        model.addAttribute("pago_dia", "630,00");
        // Vendas (Mes)
        model.addAttribute("vendas_mes", "23.300,00");
        // Vendas (Dia)
        model.addAttribute("vendas_dia", "1.350,00");

        return new ModelAndView("index2", model);
    }

    @RequestMapping(value = "/extract", method = RequestMethod.GET)
    public ModelAndView extract(ModelMap model) {
        String response = "OK";

        try {
            response = this.extractNFeService.sweepDirectory();
        } catch (Exception e) {
            response = e.toString();
        }

        // Conteudo da pagina
        model.addAttribute("conteudo", "dashboard");
        // Mensagem alert atualizar NFes
        model.addAttribute("mensagem", response);

        // -- Cards --
        // Caixa
        model.addAttribute("caixa", "20.500,00");
        // Metas
        model.addAttribute("metas", "63");
        // Recebidos (Mes)
        model.addAttribute("recebido_mes", "42.000,00");
        // A Receber (Mes)
        model.addAttribute("receber_mes", "17.000,00");
        // A Pagar (Mes)
        model.addAttribute("contas_pendentes", "7");
        model.addAttribute("pagar_mes", "13.250,00");
        // A Pagar (Dia)
        model.addAttribute("contas_pendentes_dia", "2");
        model.addAttribute("pagar_dia", "1.930,00");
        // Pago (Mes)
        model.addAttribute("pago_mes", "11.750,00");
        // Pago (Dia)
        model.addAttribute("pago_dia", "630,00");
        // Vendas (Mes)
        model.addAttribute("vendas_mes", "23.300,00");
        // Vendas (Dia)
        model.addAttribute("vendas_dia", "1.350,00");

        return new ModelAndView("index2", model);
    }
}