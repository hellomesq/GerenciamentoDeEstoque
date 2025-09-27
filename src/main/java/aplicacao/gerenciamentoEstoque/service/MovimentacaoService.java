package aplicacao.gerenciamentoEstoque.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import aplicacao.gerenciamentoEstoque.model.Movimentacao;
import aplicacao.gerenciamentoEstoque.model.Produto;
import aplicacao.gerenciamentoEstoque.repository.MovimentacaoRepository;
import aplicacao.gerenciamentoEstoque.repository.ProdutoRepository;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository, ProdutoRepository produtoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.produtoRepository = produtoRepository;
    }

    public void registrarMovimentacao(Movimentacao movimentacao) {
        Produto produto = produtoRepository.findById(movimentacao.getProduto().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if ("SAIDA".equalsIgnoreCase(movimentacao.getTipo())) {
            if (produto.getQuantidadeAtual() < movimentacao.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente");
            }
            produto.setQuantidadeAtual(produto.getQuantidadeAtual() - movimentacao.getQuantidade());
        } else {
            produto.setQuantidadeAtual(produto.getQuantidadeAtual() + movimentacao.getQuantidade());
        }

        produtoRepository.save(produto);
        movimentacao.setProduto(produto);
        movimentacao.setDataMovimentacao(LocalDateTime.now());
        movimentacaoRepository.save(movimentacao);
    }

    public List<Movimentacao> listarTodas() {
        return movimentacaoRepository.findAll();
    }
}
