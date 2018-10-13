package com.bdmc.myjpa.Utils;

import java.util.Base64;
import java.util.Date;
import org.slf4j.Logger;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class WebTokenUtil {

    public static final Logger logger = LoggerFactory.getLogger((WebTokenUtil.class));

    public static final String JWT_SECERT = "b3d4e546a7a94da59cb193203116c06f3acff0e258054ea0a7bce8717e44b27a";

    public static String createJWT(String id, String subject, long expireMillis) {
        // id，issuer，subject，ttlMillis都是放在payload中的，可根据自己的需要修改
        // 签名的算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 当前的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 签名算法的秘钥，解析token时的秘钥需要和此时的一样      
		SecretKey secretKey = generalKey();
   
        // 自定义声明(可以定义多个,也可以不定义)
        JwtBuilder builder = Jwts.builder().claim("name", "huanglu").claim("mobile", "18602720906") 
                .setId(id).setSubject(subject).setIssuedAt(now) // jwt的签发时间
                .signWith(signatureAlgorithm, secretKey);

        logger.info("---token生成---");
        // 给token设置过期时间
        if (expireMillis >= 0) {
            long expMillis = nowMillis + expireMillis;
            Date exp = new Date(expMillis);
            logger.info("过期时间：" + exp);
            builder.setExpiration(exp);
        }
        // 压缩
        return builder.compact();
    }



	/**
	 * 
	 * 解析JWT字符串
	 * 
	 * @param jwt
	 * @return claims,包括公告声明,自定义声明
	 * @throws ExpiredJwtException,SignatureException,Exception token已过期,签名校验失败,其它错误
	 */
	public static Claims parseJWT(String jwt) throws ExpiredJwtException,SignatureException,Exception {
		SecretKey secretKey = generalKey();
		return Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(jwt)
			.getBody();
    }


        /**
	 * 校验jwtStr
	 * 
	 * @param jwtStr
	 * @return
	 * @throws ExpiredJwtException,SignatureException,Exception token已过期,签名校验失败,其它错误
	 */
	public static boolean validateJWT(String jwtStr) {
		boolean flag = false;
		try {
            Claims c = parseJWT(jwtStr);
            
			flag = true;
		} catch (ExpiredJwtException e) {
            logger.error(e.getMessage());
			// TODO 可以用日志来记录错误信息
		} catch (SignatureException e) {
            logger.error(e.getMessage());
			// TODO 可以用日志来记录错误信息
		} catch (Exception e) {
            logger.error(e.getMessage());
			// TODO 可以用日志来记录错误信息
		}
		return flag;
    }
    
    /**
	 * 创建key
	 * @return
	 */
	public static SecretKey generalKey() {
		byte[] encodeKey = Base64.getDecoder().decode(JWT_SECERT);
		SecretKey key = new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
        return key;
    }
}