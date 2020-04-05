package br.com.dashboard.controller;

import br.com.dashboard.model.StatusPagamento;
import br.com.dashboard.model.TipoPagamento;
import br.com.dashboard.repository.TipoPagamentoRepository;
import br.com.dashboard.service.TipoPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(path = "tipopagamento")
public class TipoPagamentoController {

    @Autowired
    private TipoPagamentoService tipoPagamentoService;

    @Autowired
    private TipoPagamentoRepository tipoPagamentoRepository;

    @GetMapping("/list")
    public ModelAndView listarTiposPagamento(ModelMap model, HttpServletRequest request) {

        int page = 0; // default page number is 0 (yes it is weird)
        int size = 10; // default page size is 10

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        Page<TipoPagamento> tipoPagamentoPage = tipoPagamentoRepository.findAll(PageRequest.of(page, size));

        model.addAttribute("tiposPagamento", tipoPagamentoPage);
        model.addAttribute("conteudo", "/payments/list");

        return new ModelAndView("index2", model);
    }

    @GetMapping("/descricao")
    public ModelAndView listarProdutosPorCodigo(ModelMap model, HttpServletRequest request,
                                                @RequestParam(value = "descricao") String descricao) {

        int page = 0; // default page number is 0 (yes it is weird)
        int size = 10; // default page size is 10

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        if (descricao == null) {
            return new ModelAndView("redirect:/payments/list");
        }

        model.addAttribute("tiposPagamento", this.tipoPagamentoService.search(descricao, page, size));
        model.addAttribute("conteudo", "/payments/list");

        return new ModelAndView("index2", model);
    }

    @GetMapping("/findOne")
    @ResponseBody
    public TipoPagamento getTipoPagamento(Long id) {

        return this.tipoPagamentoService.findById(id);
    }

    @GetMapping("/save")
    public ModelAndView cadastro(TipoPagamento tipoPagamento) {

        return new ModelAndView("index2", "conteudo", "/payments/add");
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public ModelAndView createTipoPagamento(@Valid TipoPagamento tipoPagamento, BindingResult result,
                                            RedirectAttributes attrib) {

        if (result.hasErrors()) {
            return new ModelAndView("index2", "conteudo", "/payments/add");
        }

        this.tipoPagamentoService.save(tipoPagamento);
        attrib.addFlashAttribute("mensagem", "Tipo de Pagamento inserido com sucesso.");
        return new ModelAndView("redirect:/tipopagamento/list");
    }

    @GetMapping("/update/{id}")
    public ModelAndView editar(@PathVariable("id") Long id, ModelMap model) {

        model.addAttribute("tipoPagamento", this.tipoPagamentoService.findById(id));
        model.addAttribute("conteudo", "/payments/add");
        return new ModelAndView("index2", model);
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public ModelAndView updateTipoPagamento(@Valid TipoPagamento tipoPagamento, BindingResult result,
                                            RedirectAttributes attrib) {

        if (result.hasErrors()) {
            return new ModelAndView("index2", "conteudo", "/payments/add");
        }

        this.tipoPagamentoService.update(tipoPagamento.getId(), tipoPagamento);
        attrib.addFlashAttribute("mensagem", "Tipo de Pagamento editado com sucesso.");
        return new ModelAndView("redirect:/tipopagamento/list");
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteTipoPagamento(@PathVariable("id") Long id, RedirectAttributes attrib) {
        this.tipoPagamentoService.delete(id);
        attrib.addFlashAttribute("mensagem", "Tipo de Pagamento removido com sucesso.");
        return new ModelAndView("redirect:/tipopagamento/list");
    }

    @ModelAttribute("statusList")
    public StatusPagamento[] statusPagamentos() {

        return StatusPagamento.values();
    }
}