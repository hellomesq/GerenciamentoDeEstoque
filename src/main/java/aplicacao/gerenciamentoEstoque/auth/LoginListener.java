package aplicacao.gerenciamentoEstoque.auth;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import aplicacao.gerenciamentoEstoque.model.Usuario;
import aplicacao.gerenciamentoEstoque.service.UsuarioService;
import org.springframework.lang.NonNull;
import java.util.logging.Logger;

@Component
public class LoginListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private static final Logger logger = Logger.getLogger(LoginListener.class.getName());
    private final UsuarioService usuarioService;

    public LoginListener(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        logger.info("LoginListener inicializado para GerenciamentoDeEstoque");
    }

    @Override
    public void onApplicationEvent(@NonNull AuthenticationSuccessEvent event) {
        logger.info("Evento de autenticação recebido: " + event);

        try {
            // Verifica se é OAuth2
            if (event.getAuthentication() instanceof OAuth2AuthenticationToken token) {
                logger.info("Autenticação OAuth2 detectada. Provedor: " + token.getAuthorizedClientRegistrationId());

                // Aqui você pode limitar a apenas Google se quiser
                if ("google".equals(token.getAuthorizedClientRegistrationId())) {
                    logger.info("Login via Google detectado!");
                    OAuth2User oauth2User = (OAuth2User) event.getAuthentication().getPrincipal();

                    String email = (String) oauth2User.getAttributes().get("email");
                    String nome = (String) oauth2User.getAttributes().get("name");

                    logger.info("Usuário logado: " + nome + " <" + email + ">");

                    // Cria ou atualiza o usuário no banco
                    Usuario usuario = usuarioService.registerOAuthUser(email, nome);
                    logger.info("Usuário persistido: " + (usuario != null ? usuario.getEmail() : "NULL"));
                }
            } else {
                logger.warning("Autenticação não é do tipo OAuth2: " + event.getAuthentication().getClass().getName());
            }
        } catch (Exception e) {
            logger.severe("Erro ao processar evento de login: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
