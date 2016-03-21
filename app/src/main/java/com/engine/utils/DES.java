package com.engine.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @ClassName: DES
 * @Description: 加密解密工具类
 * @date 2015年9月11日 下午3:18:19
 */
public class DES {
    /**
     * 密钥池
     */
    private static byte[] iv;
    /**
     * 加密Key
     */
    private static String key;
    /**
     * 静态加密口令
     */
    private static String staticKey = "123456";

    private DES() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    //public native String stringFromJNI();

//    static {
//        System.loadLibrary("hello-jni");
//    }

    /**
     * 这个加密用于简单加密使用（此处使用将二维码内容加密）
     */
    public static String encryption(String oldStr) {
        doKeyencrypt();
        String outStr = "";
        try {
            outStr = encryptDES(oldStr, key);
        } catch (Exception e) {
            outStr = "";
        }
        return outStr;
    }

    /**
     * 这个加密用于简单解密使用（此处使用将二维码内容加密）
     */
    public static String doEncryption(String newStr) {
        doKeyencrypt();
        String outStr = "";
        try {
            outStr = decryptDES(newStr, key);
        } catch (Exception e) {
            outStr = "";
        }
        return outStr;
    }

    /**
     * 将原字符串加密根据当前时间
     *
     * @param oldStr
     * @return
     */
    public static String makeDES(String oldStr) {
        doKeyCreate();
        String outStr = "";
        try {
            outStr = encryptDES(oldStr, key);
        } catch (Exception e) {
            outStr = "";
        }
        return outStr;
    }

    /**
     * 将加密过后的字符串进行解密
     *
     * @param newStr
     * @return
     */
    public static String unlockDES(String newStr) {
        doKeyCreate();
        String outStr = "";
        try {
            outStr = decryptDES(newStr, key);
        } catch (Exception e) {
            outStr = "";
        }
        return outStr;
    }

    /**
     * 操作密钥
     */
    private static void doKeyCreate() {
        /**使用当前时间前6位进行加密*/
        String time = (System.currentTimeMillis() + "").substring(0, 6);
        /**将前六位进行Md532位加密*/
        String md532 = encode32MD5(time);
        /**从32位密码串中取出第2位到第10位为8位加密密钥*/
        key = md532.substring(2, 10);
        /**从32位密码串中取出第22位到第30位为长度为8位的动态密钥池*/
        iv = md532.substring(22, 30).getBytes();
    }

    /**
     * 操作密钥
     */
    private static void doKeyencrypt() {
        /**使用静态密钥进行加密*/
        String oldKey = staticKey;
        /**将前六位进行Md532位加密*/
        String md532 = encode32MD5(oldKey);
        /**从32位密码串中取出第2位到第10位为8位加密密钥*/
        key = md532.substring(2, 10);
        /**从32位密码串中取出第22位到第30位为长度为8位的动态密钥池*/
        iv = md532.substring(22, 30).getBytes();
    }

    /**
     * 加密
     */
    private static String encryptDES(String encryptString, String encryptKey)
            throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(getKey(encryptKey.getBytes()), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes("UTF-8"));
        return Base64.encode(encryptedData);
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     *
     * @param arrBTmp 构成该字符串的字节数组
     * @return 生成的密钥
     * @throws Exception
     */
    private static byte[] getKey(byte[] arrBTmp) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];
        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        return arrB;
    }

    /**
     * 解密
     */
    private static String decryptDES(String decryptString, String decryptKey)
            throws Exception {
        byte[] byteMi = Base64.decode(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(getKey(decryptKey.getBytes()), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = cipher.doFinal(byteMi);
        return new String(decryptedData, "UTF-8");
    }

    /**
     * *生成32位MD5
     */
    private static String encode32MD5(String str) {
        String mdStr = "";
        String tempStr = "";
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            byte[] strByte = messageDigest.digest();
            for (int n = 0; n < strByte.length; n++) {
                tempStr = Integer.toHexString(strByte[n] & 0XFF);
                if (tempStr.length() == 1) {
                    mdStr = mdStr + 0 + tempStr;
                } else {
                    mdStr = mdStr + tempStr;
                }
                if (n < strByte.length - 1) {
                    mdStr = mdStr + "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mdStr;
    }

    private static String encode16Md5(String oldStr) {
        String enStr = encode32MD5(oldStr);
        enStr = enStr.substring(8, 24);
        return enStr;
    }

    /**
     * 文件file进行加密并保存目标文件destFile中
     *
     * @param file     要加密的文件 如c:/test/srcFile.txt
     * @param destFile 加密后存放的文件名 如c:/加密后文件.txt
     */
    public static void encrypt(String file, String destFile) throws Exception {
        doKeyencrypt();
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key1 = new SecretKeySpec(getKey(key.getBytes()), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key1, zeroIv);
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(destFile);
        CipherInputStream cis = new CipherInputStream(is, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = cis.read(buffer)) > 0) {
            out.write(buffer, 0, r);
        }
        cis.close();
        is.close();
        out.close();
    }

    /**
     * 文件采用DES算法解密文件
     *
     * @param file 已加密的文件 如c:/加密后文件.txt
     *             * @param destFile
     *             解密后存放的文件名 如c:/ test/解密后文件.txt
     */
    public static void decrypt(String file, String dest) throws Exception {
        doKeyencrypt();
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key1 = new SecretKeySpec(getKey(key.getBytes()), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key1, zeroIv);
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(dest);
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = is.read(buffer)) >= 0) {
            cos.write(buffer, 0, r);
        }
        cos.close();
        out.close();
        is.close();
    }


    /**
     * Base64内部操作类
     */
    private static class Base64 {
        private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
                .toCharArray();

        /**
         * data[]进行编码
         *
         * @param data
         * @return
         */
        private static String encode(byte[] data) {
            int start = 0;
            int len = data.length;
            StringBuffer buf = new StringBuffer(data.length * 3 / 2);

            int end = len - 3;
            int i = start;
            int n = 0;
            while (i <= end) {
                int d = ((((int) data[i]) & 0x0ff) << 16)
                        | ((((int) data[i + 1]) & 0x0ff) << 8)
                        | (((int) data[i + 2]) & 0x0ff);

                buf.append(legalChars[(d >> 18) & 63]);
                buf.append(legalChars[(d >> 12) & 63]);
                buf.append(legalChars[(d >> 6) & 63]);
                buf.append(legalChars[d & 63]);
                i += 3;
                if (n++ >= 14) {
                    n = 0;
                    buf.append(" ");
                }
            }

            if (i == start + len - 2) {
                int d = ((((int) data[i]) & 0x0ff) << 16)
                        | ((((int) data[i + 1]) & 255) << 8);

                buf.append(legalChars[(d >> 18) & 63]);
                buf.append(legalChars[(d >> 12) & 63]);
                buf.append(legalChars[(d >> 6) & 63]);
                buf.append("=");
            } else if (i == start + len - 1) {
                int d = (((int) data[i]) & 0x0ff) << 16;

                buf.append(legalChars[(d >> 18) & 63]);
                buf.append(legalChars[(d >> 12) & 63]);
                buf.append("==");
            }
            return buf.toString();
        }

        private static byte[] decode(String s) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                decode(s, bos);
            } catch (IOException e) {
                throw new RuntimeException();
            }
            byte[] decodedBytes = bos.toByteArray();
            try {
                bos.close();
                bos = null;
            } catch (IOException ex) {
                System.err.println("Error while decoding BASE64: "
                        + ex.toString());
            }
            return decodedBytes;
        }

        private static void decode(String s, OutputStream os)
                throws IOException {
            int i = 0;

            int len = s.length();

            while (true) {
                while (i < len && s.charAt(i) <= ' ')
                    i++;

                if (i == len)
                    break;

                int tri = (decode(s.charAt(i)) << 18)
                        + (decode(s.charAt(i + 1)) << 12)
                        + (decode(s.charAt(i + 2)) << 6)
                        + (decode(s.charAt(i + 3)));

                os.write((tri >> 16) & 255);
                if (s.charAt(i + 2) == '=')
                    break;
                os.write((tri >> 8) & 255);
                if (s.charAt(i + 3) == '=')
                    break;
                os.write(tri & 255);

                i += 4;
            }
        }

        private static int decode(char c) {
            if (c >= 'A' && c <= 'Z')
                return ((int) c) - 65;
            else if (c >= 'a' && c <= 'z')
                return ((int) c) - 97 + 26;
            else if (c >= '0' && c <= '9')
                return ((int) c) - 48 + 26 + 26;
            else
                switch (c) {
                    case '+':
                        return 62;
                    case '/':
                        return 63;
                    case '=':
                        return 0;
                    default:
                        throw new RuntimeException("unexpected code: " + c);
                }
        }
    }
}