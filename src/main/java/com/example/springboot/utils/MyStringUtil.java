package com.example.springboot.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyStringUtil {

    /**
     *
     * @Title: convertStringFromFullWidthToHalfWidth.
     * @Description: Convert a String from half width to full width.
     *
     * @param string input string
     * @return the converted String
     */
    public static String full2Half(String string) {
        if (StringUtils.isEmpty(string)) {
            return string;
        }

        char[] charArray = string.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == 12288) {
                charArray[i] =' ';
            } else if (charArray[i] >= ' ' &&
                    charArray[i]  <= 65374) {
                charArray[i] = (char) (charArray[i] - 65248);
            } else {

            }
        }


        return new String(charArray);
    }



    /**
     * this is used to convert half to full-widths charaters.
     * @Title: half2Full
     * @param value input value
     * @return converted value
     */
    public static String half2Full(String value) {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        char[] cha = value.toCharArray();

        /**
         * full blank space is 12288, half blank space is 32
         * others :full is 65281-65374,and half is 33-126.
         */
        for (int i = 0; i < cha.length; i++) {
            if (cha[i] == 32) {
                cha[i] = (char) 12288;
            } else if (cha[i] < 127) {
                cha[i] = (char) (cha[i] + 65248);
            }
        }
        return new String(cha);
    }

    static String regEx = "[`~!@#$%^&*()\\-+={}':;,\\[\\].<>/?￥%…（）_+|【】‘；：”“’。，、？\\s]";
    static Pattern p = Pattern.compile(regEx);

    public static  String  replaceAllChar(String str){
        Matcher m = p.matcher(str);
         return m.replaceAll("").trim();
    }


}
