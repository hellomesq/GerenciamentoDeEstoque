package aplicacao.gerenciamentoEstoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import aplicacao.gerenciamentoEstoque.model.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    void deleteByProdutoId(Long produtoId);
}
