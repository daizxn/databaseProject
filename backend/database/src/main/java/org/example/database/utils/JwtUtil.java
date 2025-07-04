package org.example.database.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT工具类 - 使用Auth0 java-jwt库
 */
@Component
public class JwtUtil {

    // JWT密钥，实际生产环境应该放在配置文件中
    private static final String SECRET_KEY = "mySecretKeyForJWTTokenGenerationAndValidation123456789";

    // Token过期时间（24小时）
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    // 获取算法
    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(SECRET_KEY);
    }

    /**
     * 生成JWT Token
     * @param refId 用户关联ID
     * @param roles 用户角色
     * @param username 用户名
     * @return JWT Token
     */
    public String generateToken(String refId, String roles, String username) {
        try {
            return JWT.create()
                    .withSubject(username)
                    .withClaim("refId", refId)
                    .withClaim("roles", roles)
                    .withClaim("username", username)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(getAlgorithm());
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error creating JWT token: " + e.getMessage());
        }
    }

    /**
     * 从Token中提取用户名
     * @param token JWT Token
     * @return 用户名
     */
    public String extractUsername(String token) {
        return getDecodedJWT(token).getSubject();
    }

    /**
     * 从Token中提取refId
     * @param token JWT Token
     * @return refId
     */
    public String extractRefId(String token) {
        return getDecodedJWT(token).getClaim("refId").asString();
    }

    /**
     * 从Token中提取roles
     * @param token JWT Token
     * @return roles
     */
    public String extractRoles(String token) {
        return getDecodedJWT(token).getClaim("roles").asString();
    }

    /**
     * 从Token中提取过期时间
     * @param token JWT Token
     * @return 过期时间
     */
    public Date extractExpiration(String token) {
        return getDecodedJWT(token).getExpiresAt();
    }

    /**
     * 获取解码后的JWT
     * @param token JWT Token
     * @return DecodedJWT对象
     */
    private DecodedJWT getDecodedJWT(String token) {
        try {
            JWTVerifier verifier = JWT.require(getAlgorithm()).build();
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid JWT token: " + e.getMessage());
        }
    }

    /**
     * 检查Token是否过期
     * @param token JWT Token
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Date expiration = extractExpiration(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 验证Token
     * @param token JWT Token
     * @param username 用户名
     * @return 是否有效
     */
    public Boolean validateToken(String token, String username) {
        try {
            String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证Token（不需要用户名）
     * @param token JWT Token
     * @return 是否有效
     */
    public Boolean validateToken(String token) {
        try {
            // 尝试解码和验证Token
            getDecodedJWT(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从Token中提取所有信息
     * @param token JWT Token
     * @return TokenInfo对象
     */
    public TokenInfo extractTokenInfo(String token) {
        try {
            DecodedJWT decodedJWT = getDecodedJWT(token);
            TokenInfo tokenInfo = new TokenInfo();
            tokenInfo.setUsername(decodedJWT.getSubject());
            tokenInfo.setRefId(decodedJWT.getClaim("refId").asString());
            tokenInfo.setRoles(decodedJWT.getClaim("roles").asString());
            tokenInfo.setIssuedAt(decodedJWT.getIssuedAt());
            tokenInfo.setExpiresAt(decodedJWT.getExpiresAt());
            return tokenInfo;
        } catch (Exception e) {
            throw new RuntimeException("Error extracting token info: " + e.getMessage());
        }
    }

    /**
     * Token信息封装类
     */
    public static class TokenInfo {
        private String username;
        private String refId;
        private String roles;
        private Date issuedAt;
        private Date expiresAt;

        // getters and setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getRefId() { return refId; }
        public void setRefId(String refId) { this.refId = refId; }
        public String getRoles() { return roles; }
        public void setRoles(String roles) { this.roles = roles; }
        public Date getIssuedAt() { return issuedAt; }
        public void setIssuedAt(Date issuedAt) { this.issuedAt = issuedAt; }
        public Date getExpiresAt() { return expiresAt; }
        public void setExpiresAt(Date expiresAt) { this.expiresAt = expiresAt; }
    }
}
