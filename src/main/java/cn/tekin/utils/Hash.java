package cn.tekin.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * hash 加密算法，支持所有JAVA默认支持的加密方式， 如： MD2, MD5, SHA1, SHA-128,SHA-256, SHA-512, etc.
 * @url http://dev.yunnan.ws
 */
public class Hash {

    public final static String enCrypt(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("SHA-512");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
//                	System.out.print("i="+i);
//                	System.out.println("    "+str[i]);
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * main test
     * @param args
     * @throws NoSuchAlgorithmException
     */
    public static void main(String args[]) throws NoSuchAlgorithmException{
        String msg = "郭XX-精品相声技术";
        String enStr =enCrypt(msg);
        System.out.println("密文是：" + enStr);
        System.out.println("明文是：" + msg);
    }

}  