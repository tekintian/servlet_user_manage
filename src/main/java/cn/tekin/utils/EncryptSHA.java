package cn.tekin.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptSHA {

    public static final char[] hexChar = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    //执行加密：可支持的加密方式 "MD2", "MD5", "SHA1", "SHA-256", "SHA-384", "SHA-512"等
    public byte[] encrypt(String info, String hashType) throws NoSuchAlgorithmException{
        //默认加密方式为SHA-512
        String defaultHashType = "SHA-512";
        if (hashType == null||hashType.equals("")) {
            hashType=defaultHashType;
        }

        MessageDigest md5 = MessageDigest.getInstance(hashType);
        byte[] srcBytes = info.getBytes();
        //使用srcBytes更新摘要
        md5.update(srcBytes);
        //完成哈希计算，得到result
        byte[] resultBytes = md5.digest();
        return resultBytes;
    }

    /**
     * JAVA HASH HASH 加扰多重加密实现
     * @param args
     * @throws NoSuchAlgorithmException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String msg = "111111";
        String pwSalt = "999";//加扰码，这个是你密码绝对安全的保障，请设置10位以上的高强度加扰码，并牢记你的加扰码
        EncryptSHA sha = new EncryptSHA();
        byte[] resultBytes = sha.encrypt(toHexString(sha.encrypt(msg,""))+pwSalt,"MD5");

        System.out.println("明文是：" + msg);
        System.out.println("密文是：" + toHexString(resultBytes) );

    }

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0x0f]);
        }
        return sb.toString();
    }

}