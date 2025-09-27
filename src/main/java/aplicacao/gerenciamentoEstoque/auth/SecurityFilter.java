package aplicacao.gerenciamentoEstoque.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SecurityFilter extends OncePerRequestFilter {

    // Rotas que só administradores podem acessar
    private static final List<String> ADMIN_ROUTES = Arrays.asList(
            "/produtos/create",
            "/produtos/edit",
            "/produtos/delete",
            "/usuarios/create",
            "/usuarios/edit",
            "/usuarios/delete"
    );

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        boolean isAdminRoute = ADMIN_ROUTES.stream()
                .anyMatch(requestURI::startsWith);

        if (isAdminRoute) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuário não autenticado");
                return;
            }

            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            if (!isAdmin) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado. Apenas administradores podem acessar esta página.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}

