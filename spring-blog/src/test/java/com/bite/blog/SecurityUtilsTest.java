package com.bite.blog;

import org.springframework.util.DigestUtils;

import java.util.UUID;

public class SecurityUtilsTest {
    public static void main(String[] args) {
        //加密
        String md5Str = DigestUtils.md5DigestAsHex("123456".getBytes());
        System.out.println(md5Str);
        //
//        String uuid = UUID.randomUUID().toString();// 随机生成一个字符串, 且不会重复
//        System.out.println(uuid.replace("-",""));
//
        System.out.println(encry("123456"));
        boolean verify = verify("123456", "123456");
        System.out.println(verify);
    }

    /**
     * 加密
     * @param password  明文密码
     * @return  盐值+密文
     */
    public static String encry(String password){
        //生成随机盐值
        String salt = UUID.randomUUID().toString().replace("-","");
        System.out.println(salt);
        //加密  盐值+明文
        String securityPassword = DigestUtils.md5DigestAsHex((salt+password).getBytes());
        //数据库中存储   盐值+密文
        return salt+securityPassword;
    }

    /**
     * 校验
     * @return
     */
    public static boolean verify(String inputPassword, String sqlPassword){
        //取出盐值
        if (sqlPassword ==null || sqlPassword.length()!=64){
            return false;
        }
        String salt = sqlPassword.substring(0,32);
        //得到密文
        String securityPassword = DigestUtils.md5DigestAsHex((salt+inputPassword).getBytes());
        return (salt+securityPassword).equals(sqlPassword);
    }
}
