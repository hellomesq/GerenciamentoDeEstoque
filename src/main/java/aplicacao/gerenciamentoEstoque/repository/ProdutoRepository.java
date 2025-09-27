package aplicacao.gerenciamentoEstoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import aplicacao.gerenciamentoEstoque.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
}
