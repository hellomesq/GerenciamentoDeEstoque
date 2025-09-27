package aplicacao.gerenciamentoEstoque.controller;

import aplicacao.gerenciamentoEstoque.model.Produto;
import aplicacao.gerenciamentoEstoque.service.ProdutoService;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Controller
@RequestMapping("/produtos") // Prefixo para evitar conflito de rotas
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("produtos", produtoService.listarProdutos());
        return "formsProduto";
    }

    @GetMapping("/novo")
    public String novoProduto(Model model) {
        model.addAttribute("produto", new Produto());
        return "produtos/formsProduto";
    }

    @GetMapping("/listar")
    public String listarProdutos(Model model) {
        List<Produto> lista = produtoService.listarProdutos();
        model.addAttribute("produtos", lista);
        model.addAttribute("vazio", lista.isEmpty());
        return "produtos/listarProdutos";
    }

    @PostMapping("/salvar")
    public String salvarProduto(@ModelAttribute Produto produto, Model model) {
        try {
            produtoService.salvarProduto(produto);
            return "redirect:/produtos/listar";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao salvar: " + e.getMessage());
            return "produtos/formsProduto";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model) {
        Produto produto = produtoService.buscarPorId(id);
        if (produto == null) {
            return "redirect:/produtos/listar";
        }
        model.addAttribute("produto", produto);
        return "produtos/formsProduto";
    }

    @PostMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable Long id, Model model) {
        try {
            produtoService.excluirProduto(id); // chama o service
        } catch (Exception e) {
            model.addAttribute("erro", "Não foi possível excluir: " + e.getMessage());
        }
        return "redirect:/produtos/listar";
    }
    

}
