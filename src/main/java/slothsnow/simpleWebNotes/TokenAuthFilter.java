package slothsnow.simpleWebNotes;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class TokenAuthFilter extends OncePerRequestFilter {
    
    private final TokenRepository tokenRepository;

    @Autowired
    public TokenAuthFilter(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws IOException, ServletException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ") && !authHeader.substring(7).isBlank()) {
            String accessToken = authHeader.substring(7);
            Optional<User> user = isTokenValid(accessToken);

            if (user.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                user.get().getId(), 
                null, 
                user.get().getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    private Optional<User> isTokenValid(String token) {
        Optional<OauthToken> oauthToken = tokenRepository.findById(token);
        
        if (oauthToken.isEmpty() || oauthToken.get().getExpirationTime().isBefore(LocalDateTime.now())) {
            return Optional.empty();
        }
        
        return Optional.of(oauthToken.get().getUser());
    }
}