package org.example.shopvue.utils;

import io.jsonwebtoken.*;
import org.example.shopvue.model.Admin;
import org.example.shopvue.model.Power;
import org.example.shopvue.model.User;

import java.util.Date;


public class AddToken {

    private static long time = 1000 * 60 * 60 * 24; //1天
    private static String signature = "admin11";
    public static String AddToken(User user) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("username", user.getName())
                .claim("phone", user.getPhone())
                .claim("role", "user")
                .setSubject("admin-test")
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(user.getUid())
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
        return jwtToken;
    }


    public static boolean checkToken(String token) {
        if (token == null) {
            return false;
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //管理员token
    public static String AddToken(Admin admin) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("username", admin.getName())
                .claim("role", "manager")
                .setSubject("admin-test")
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(admin.getAdminid())
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
        return jwtToken;
    }


    //根据token得到用户信息
    public static Power getInformation(String token){
        JwtParser parser = Jwts.parser();
        try{
        Jws<Claims> claimsJws = parser.setSigningKey(signature).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Power power = new Power();
        power.setId(claims.getId());
        power.setName(claims.get("username", String.class));
        power.setRole(claims.get("role", String.class));
        return power;
        } catch (Exception e) {
            return null;
        }
    }


}
