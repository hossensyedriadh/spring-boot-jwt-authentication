package io.github.hossensyedriadh.springbootjwtauthentication.controller.v1.authentication;

import io.github.hossensyedriadh.springbootjwtauthentication.auth_mechanism.model.AccessTokenRequest;
import io.github.hossensyedriadh.springbootjwtauthentication.auth_mechanism.model.JwtRequest;
import io.github.hossensyedriadh.springbootjwtauthentication.auth_mechanism.model.JwtResponse;
import io.github.hossensyedriadh.springbootjwtauthentication.auth_mechanism.service.JwtService;
import io.github.hossensyedriadh.springbootjwtauthentication.exception.InvalidCredentialsException;
import io.github.hossensyedriadh.springbootjwtauthentication.exception.InvalidRefreshTokenException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = "/v1/authentication", produces = {MediaType.APPLICATION_JSON_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE})
public class AuthenticationController {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Value("${application.security.jwt.token-type}")
    private String tokenType;

    @Autowired
    public AuthenticationController(JwtService jwtService, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(method = "POST", summary = "Authenticate using username, password", description = "Returns JWTs for when authenticated successfully")
    @PostMapping(value = "/authenticate")
    public ResponseEntity<JwtResponse> authenticate(HttpServletRequest request, HttpServletResponse response, @RequestBody JwtRequest jwtRequest) {
        UserDetails userDetails;

        try {
            userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Incorrect username: " + jwtRequest.getUsername());
        }

        if (passwordEncoder.matches(jwtRequest.getPassword(), userDetails.getPassword())) {
            Map<String, String> claims = new HashMap<>();
            claims.put("username", userDetails.getUsername());
            claims.put("authority", String.valueOf(userDetails.getAuthorities().toArray()[0]));

            String accessToken = jwtService.getAccessToken(claims);
            String refreshToken = jwtService.getRefreshToken(userDetails.getUsername(), claims);

            response.addHeader(HttpHeaders.EXPIRES, String.valueOf(LocalDateTime.ofInstant(
                    Objects.requireNonNull(jwtService.jwtDecoder().decode(accessToken).getExpiresAt()),
                    ZoneId.of("Asia/Dhaka")
            )));

            return new ResponseEntity<>(new JwtResponse(accessToken, refreshToken, tokenType), HttpStatus.OK);
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        throw new InvalidCredentialsException("Invalid credentials", request.getRequestURI());
    }

    @Operation(method = "POST", summary = "Get new access token", description = "Returns new access token when provided valid refresh token")
    @PostMapping("/access-token")
    public ResponseEntity<JwtResponse> accessToken(HttpServletRequest request, HttpServletResponse response, @RequestBody AccessTokenRequest tokenRequest) {
        String receivedRefreshToken = tokenRequest.getRefresh_token();

        boolean isValid = jwtService.isRefreshTokenValid(receivedRefreshToken);

        if (isValid) {
            Jwt jwt = jwtService.jwtDecoder().decode(receivedRefreshToken);

            Map<String, Object> claims = jwt.getClaims();
            Map<String, String> convertedClaims = new HashMap<>();

            convertedClaims.put("username", claims.get("username").toString());
            convertedClaims.put("authority", claims.get("authority").toString());

            String accessToken = jwtService.getAccessToken(convertedClaims);

            response.addHeader(HttpHeaders.EXPIRES, String.valueOf(LocalDateTime.ofInstant(
                    Objects.requireNonNull(jwtService.jwtDecoder().decode(accessToken).getExpiresAt()),
                    ZoneId.of("Asia/Dhaka")
            )));

            return new ResponseEntity<>(new JwtResponse(accessToken, receivedRefreshToken, tokenType), HttpStatus.OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new InvalidRefreshTokenException("Invalid refresh token", request.getRequestURI());
        }
    }
}
