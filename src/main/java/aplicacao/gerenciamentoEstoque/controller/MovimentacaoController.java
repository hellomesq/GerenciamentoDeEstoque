package aplicacao.gerenciamentoEstoque.controller;

import aplicacao.gerenciamentoEstoque.model.Movimentacao;
import aplicacao.gerenciamentoEstoque.service.MovimentacaoService;
import aplicacao.gerenciamentoEstoque.service.ProdutoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import aplicacao.gerenciamentoEstoque.model.Produto;

@Controller
@RequestMapping("/movimentacoes") // Prefixo das rotas
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;
    private final ProdutoService produtoService;

    public MovimentacaoController(MovimentacaoService movimentacaoService, ProdutoService produtoService) {
        this.movimentacaoService = movimentacaoService;
        this.produtoService = produtoService;
    }

    // Mostrar formulário para nova movimentação
    @GetMapping("/nova")
    public String mostrarFormularioCadastro(
        @RequestParam(value = "produtoId", required = false) Long produtoId,
        Model model) {

    Movimentacao movimentacao = new Movimentacao();

    if (produtoId != null) {
            Produto produto = produtoService.buscarPorId(produtoId);
            movimentacao.setProduto(produto);
    }

    model.addAttribute("movimentacao", movimentacao);
    model.addAttribute("produtos", produtoService.listarProdutos());

    return "movimentacoes/formulario";
    }

    // Salvar movimentação
    @PostMapping("/salvar")
    public String registrarMovimentacao(@ModelAttribute Movimentacao movimentacao) {
        movimentacaoService.registrarMovimentacao(movimentacao);
        return "redirect:/produtos/listar"; // volta para a lista de produtos
    }

}
