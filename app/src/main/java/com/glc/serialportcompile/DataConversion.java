package com.glc.serialportcompile;
/**
 *  数据转换工具类
 *  @author frey
 */
public class DataConversion {

    /**
     * 判断奇数或偶数，位运算，最后一位是1则为奇数，为0是偶数
     * @param num
     * @return
     */
    public static int isOdd(int num) {
        return num & 0x1;
    }

    /**
     * 将int转成byte
     * @param number
     * @return
     */
    public static byte intToByte(int number){
        return hexToByte(intToHex(number));
    }

    /**
     * 将int转成byte
     * @param number
     * @param len  字节长度
     * @return
     */
    public static byte[] intToByte(int number,int len){
        return decodeHexString(intToHex(number,len));
    }

    /**
     * 将int转成hex字符串
     * @param number
     * @return
     */
    public static String intToHex(int number){
        String st = Integer.toHexString(number).toUpperCase();
        return String.format("%2s",st).replaceAll(" ","0");
    }

    /**
     * 将int转成hex字符串
     * @param number
     * @return
     */
    public static String intToHex(int number, int len){
        String st = Integer.toHexString(number).toUpperCase();
        return String.format("%"+len+"s",st).replaceAll(" ","0");
    }



    /**
     * 字节转十进制
     * @param b
     * @return
     */
    public static int byteToDec(byte b){
        String s = byteToHex(b);
        return (int) hexToDec(s);
    }

    /**
     * 字节数组转十进制
     * @param bytes
     * @return
     */
    public static int bytesToDec(byte[] bytes){
        String s = encodeHexString(bytes,bytes.length);
        return (int)  hexToDec(s);
    }

    /**
     * Hex字符串转int
     *
     * @param inHex
     * @return
     */
    public static int hexToInt(String inHex) {
        return Integer.parseInt(inHex, 16);
    }

    /**
     * 字节转十六进制字符串
     * @param num
     * @return
     */
    public static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits).toUpperCase();
    }

    /**
     * 十六进制转byte字节
     * @param hexString
     * @return
     */
    public static byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    private static  int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if(digit == -1) {
            throw new IllegalArgumentException(
                    "Invalid Hexadecimal Character: "+ hexChar);
        }
        return digit;
    }

    /**
     * 字节数组转十六进制
     * @param byteArray
     * @return
     */
    public static String encodeHexString(byte[] byteArray, int len) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = 0; i < len; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString().toUpperCase();
    }

    /**
     * 字节数组转十六进制
     * @param byteArray
     * @return
     */
    public static String encodeHexString(byte[] byteArray, int startIndex, int endIndex) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = startIndex; i < endIndex + 1; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString().toUpperCase();
    }

    /**
     * 十六进制转字节数组
     * @param hexString
     * @return
     */
    public static byte[] decodeHexString(String hexString) {
        if (hexString.length() % 2 == 1) {
            throw new IllegalArgumentException(
                    "Invalid hexadecimal String supplied.");
        }
        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
        }
        return bytes;
    }

    /**
     * 十进制转十六进制
     * @param dec
     * @return
     */
    public static String decToHex(int dec){
        String hex = Integer.toHexString(dec);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return hex.toLowerCase();
    }

    /**
     * 十六进制转十进制
     * @param hex
     * @return
     */
    public static long hexToDec(String hex){
        return Long.parseLong(hex, 16);
    }

    /**
     * 十六进制转十进制，并对卡号补位
     */
    public static String setCardNum(String cardNun){
        String cardNo1= cardNun;
        String cardNo=null;
        if(cardNo1!=null){
            Long cardNo2= Long.parseLong(cardNo1,16);
            //cardNo=String.format("%015d", cardNo2);
            cardNo = String.valueOf(cardNo2);
        }
        return cardNo;
    }


    /**
     * 16进制转换成为string类型字符串
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }
}
