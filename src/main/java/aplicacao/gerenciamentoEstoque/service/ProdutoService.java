package aplicacao.gerenciamentoEstoque.service;

import java.util.List;

import org.springframework.stereotype.Service;

import aplicacao.gerenciamentoEstoque.model.Produto;
import aplicacao.gerenciamentoEstoque.repository.MovimentacaoRepository;
import aplicacao.gerenciamentoEstoque.repository.ProdutoRepository;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {
    private final ProdutoRepository repository;
    private final MovimentacaoRepository movimentacaoRepository;

    public ProdutoService(ProdutoRepository repository, MovimentacaoRepository movimentacaoRepository) {
        this.repository = repository;
        this.movimentacaoRepository = movimentacaoRepository;
    }


    public List<Produto> listarProdutos() {
        return repository.findAll();
    }

    public Produto salvarProduto(Produto produto) {
        return repository.save(produto);
    }

    public Produto buscarPorId(Long id) {
    return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + id));
}


    @Transactional
    public void excluirProduto(Long id) {
        movimentacaoRepository.deleteByProdutoId(id); // exclui movimentações
        repository.deleteById(id); // exclui produto
    }

}
