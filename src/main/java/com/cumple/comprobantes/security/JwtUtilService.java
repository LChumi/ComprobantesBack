/*
 * Copyright (c) 2024 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3.
 * Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en luischumi.9@gmail.com
 */

package com.cumple.comprobantes.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtilService {

    private static final String JWT_SECRET_KEY="TExBVkVfU0VDUkVUQV9DVU1QTEU=";//LLAVE_SECRETA_CUMPLE
    public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60; // 1 hora 30min ->1000 * 60 * 30

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {return extractClaim(token,Claims::getExpiration);}

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){ return extractExpiration(token).before(new Date());}

    public  String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        //Agregando informacion adicional como claim
        var rol = userDetails.getAuthorities().stream().toList().get(0);
        System.out.println(rol);
        claims.put("rol", rol.getAuthority());
        return createToken(claims,userDetails.getUsername());
    }

    private String createToken(Map<String ,Object> claims, String subject){
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256,JWT_SECRET_KEY)
                .compact();
    }

    public boolean validarToken(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
