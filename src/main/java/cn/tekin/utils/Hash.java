package cn.tekin.utils;

import java.security.MessageDigest;

/**
 * hash 加密算法，支持所有JAVA默认支持的加密方式， 如： MD2, MD5, SHA1, SHA-128,SHA-256, SHA-512, etc.
 * 使用方法：
 * 传递2个参数，默认加密方式为 SHA-512,或者传递的加密方式
 * Hash.enCrypt("123",null)
 * 只传递1个参数，默认加密方式为 MD5
 * Hash.enCrypt("123")
 * @Author TekinTian <tekintian@gmail.com>
 * @url http://dev.yunnan.ws
 */
public class Hash {

    /**
     * hash 加密算法实现
     * 默认加密方式为 SHA-512,或者传递的加密方式
     *
     * @param msg      要加密的字符串
     * @param hashType 加密类型 支持所有JAVA默认支持的加密方式， 如： MD2, MD5, SHA1, SHA-128,SHA-256, SHA-512, etc.
     * @return 加密后的字符串
     */
    public final static String enCrypt(String msg, String hashType) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        //默认加密方式为SHA-512
        if (hashType == null || hashType.equals("")) hashType = "SHA-512";

        try {
            byte[] strTemp = msg.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance(hashType);
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
     * 不传加密类型，默认 MD5,
     *
     * @param msg
     * @return
     */
    private static String enCrypt(String msg) {
        return enCrypt(msg, "MD5");
    }


    /**
     * main test
     * @param args
     */
    public static void main(String args[]) {

        String msg = "888";

        String enStr = Hash.enCrypt(msg);//加密

        System.out.println("明文是：" + msg);
        System.out.println("密文是：" + enStr);
    }


}  