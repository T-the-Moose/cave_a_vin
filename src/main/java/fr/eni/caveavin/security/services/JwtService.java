package fr.eni.caveavin.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Le Value permet d'aller chercher la clé secrète dans le fichier application.properties
    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    // Permet de vérifier si le token JWT est valide
    // en le décodant et en vérifiant sa signature
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Permet d'extraire les informations contenues dans le token JWT (Claims)
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Permet d'aller chercher une information spécifique dans le token JWT
    // de type T en utilisant la fonction getClaimsFromToken
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Permet d'extraire le Username du token JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Permet de générer un token JWT
    public String generateToken(Map<String, Object> claims, UserDetails user) {
        return Jwts.builder()
                .setClaims(claims)
                // username
                .setSubject(user.getUsername())
                // Date du token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // Date d'expiration du token (24 heures)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                // Signature du token avec la clé secrète
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Permet de générer un token JWT sans claims supplémentaires,
    // juste les informations du user
    public String generateToken(UserDetails user) {
        return generateToken(new HashMap<>(), user);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Permet de vérifier si le token JWT est encore valide
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Permet de vérifier si le token JWT est valide pour un utilisateur spécifique
    public boolean isTokenValid(String token, UserDetails user) {
        return !isTokenExpired(token) && extractUsername(token).equals(user.getUsername());
    }

}
