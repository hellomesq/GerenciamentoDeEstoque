package aplicacao.gerenciamentoEstoque.service;

import org.springframework.stereotype.Service;

import aplicacao.gerenciamentoEstoque.auth.AuthUtils;
import aplicacao.gerenciamentoEstoque.model.Usuario;
import aplicacao.gerenciamentoEstoque.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario registerOAuthUser(String email, String nome) {
        return usuarioRepository.findByEmail(email)
                .orElseGet(() -> {
                    Usuario novo = new Usuario();
                    novo.setEmail(email);
                    novo.setNome(nome != null ? nome : "Usuário Google");

                    // Verifica se email é admin
                    if (AuthUtils.isAdminEmail(email)) {
                        novo.setRole("ROLE_ADMIN");
                    } else {
                        novo.setRole("ROLE_USER");
                    }

                    return usuarioRepository.save(novo);
                });

    }
}