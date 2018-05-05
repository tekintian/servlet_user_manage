package cn.tekin.utils;

import java.security.MessageDigest;

/**
 * JAVA HASH 加密 算法实现， 可添加自定义的加扰码，可逆转加密数据, 加密数据诱惑,显示为MD5,实际加密方式，嘿嘿...... 多重混合加密方式，全球最加密方式，无人能解！！！
 * @author Tekin
 * @url http://dev.yunnan.ws
 * @datetime 2018-05-05
 */
public class HashEncrypt {
    /***
     * SHA加密 生成40位SHA码
     * @param 待加密字符串
     * @param 加密的类型[默认SHA-512, 传递null或者控值即可]
     * @return 返回40位SHA码
     */
    public static String hashCrypt(String inStr, String hashType) throws Exception {

        //默认加密方式为SHA-512
        String defaultHashType = "SHA-512";
        if (hashType == null||hashType.equals("")) {
            hashType=defaultHashType;
        }

        MessageDigest hash = null;
        try {
            hash = MessageDigest.getInstance(hashType);
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = hash.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        //加点料，把所有加密字符串都反转一下
        return hexValue.reverse().toString();
//        return hexValue.toString();
    }

    /**
     * DIY加密方式，
     * @param inStr 要加密的字符串
     * @param pwSalt 加密字符串
     * @param returnHashType 返回的加密类型，默认 返回MD5, JAVA支持的加密类型都可以，如： MD5, SHA, SHA-256, SHA-512 ...
     * @return
     * @throws Exception
     */
    public static String encryptDiy(String inStr, String pwSalt, String returnHashType) {
        //默认加密方式为SHA-512
        String defaultReturnHashType = "MD5";
        if (returnHashType == null||returnHashType.equals("")) {
            returnHashType=defaultReturnHashType;
        }
        //返回加密后的字符
        String hashStr="";
        try {
            hashStr = hashCrypt(hashCrypt(inStr,"SHA-512")+pwSalt, returnHashType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hashStr;

    }

    /**
     * 测试主函数
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {

        String mystr = "111111";
        String pwSalt = "A$56890@#Z,.1c68ff2785cc3148c9fdA，。】-8&……*（%·be1b69e=-90-";
        String hashType = "MD5";
        System.out.println("原始密码：" + mystr);
        System.out.println("加扰码：" + pwSalt);
        System.out.println("HASH加密方式：" + hashType);
        System.out.println("HASH后：" + hashCrypt(hashCrypt(mystr,null)+pwSalt, hashType) );
    }
}