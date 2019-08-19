package com.example.springboot.utils;

import com.example.springboot.prefix.CrawlerKey;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class G_Code {



    public static String jy(char[] a) {
        int[] ww = {3, 7, 9, 10, 5, 8, 4, 2};

        int[] cc = new int[8];
        int DD = 0;
        int C9 = 0;

        for (int i = 0; i < 8; i++) {
            cc[i] = a[i];
            if (47 < cc[i] && cc[i] < 58) {
                cc[i] = cc[i] - 48;
            } else {
                cc[i] = cc[i] - 65;
            }
        }
        for (int i = 0; i < 8; i++) {
            DD += cc[i] * ww[i];
        }
        C9 = 11 - DD % 11;
        if (C9 == 10) {
            for (int i = 0; i < 8; i++) {
                return new String(a) + "X";
            }
        } else if (C9 == 11) {
            for (int i = 0; i < 8; i++) {

                return new String(a) + (char) (48);
            }
        } else {
            for (int i = 0; i < 8; i++) {
                return new String(a) + (char) (C9 + 48);
            }
        }
        return null;
    }

    public static void main(String[] args) {
//        String a = "69609919";
//        System.out.printf(G_Code.jy(a.toCharArray()));
//        char [] b = {'A', '1','E','6','9','H','B','5'};
//        System.out.printf(G_Code.jy(b));
        String org = G_Code.orgCode();
        System.out.printf(org);

    }


    public static synchronized String orgCode() {
        long count = 0L;
        String countStr = RedisUtils.get(CrawlerKey.orgCode, "String");
        if(StringUtils.isNumeric(countStr)){
            count = Long.parseLong(countStr );
        }
        count ++;
        RedisUtils.set(CrawlerKey.orgCode, "String",count);
        String org = Long.toString(count, 36).toUpperCase();
        if(StringUtils.length(org)<8){
            org = "00000000"+org;
            org = StringUtils.substring(org, org.length() - 8, org.length());
        }
        String jyStr = G_Code.jy(org.toCharArray());
        return jyStr;
    }
    public static List<String> allOrgCode(){
        boolean flag = true;
        List<String> resultList = new ArrayList<>();
        while(flag){
            String resultStr = G_Code.orgCode();
            resultList.add(resultStr);
            if(StringUtils.startsWith(resultStr,"ZZZZZZZZ")){
                flag = false;
            }
        }
        return resultList;
    }





    /*
[函数名]checkUSCC
[功能]校验18位统一社会信用代码正确性
[参数]testUSCC:待校验的统一社会信用代码（要求字母已经保持大写）
[返回值]boolean类型，0(false)表示验证未通过，1(true)表示验证通过
*/
    public static boolean checkUSCC(String testUSCC)
    {
        if(testUSCC.length()!=18)
        {
            System.out.println("统一社会信用代码长度错误");
            return false;
        }

        int Weight[]= {1,3,9,27,19,26,16,17,20,29,25,13,8,24,10,30,28};		//用于存放权值
        int index=0;														//用于计算当前判断的统一社会信用代码位数
        char testc;															//用于存放当前位的统一社会信用代码
        int tempSum=0;														//用于存放代码字符和加权因子乘积之和
        int tempNum=0;
        for(index=0;index<=16;index++)
        {
            testc=testUSCC.charAt(index);

            if(index==0)
            {
                if(testc!='1'&&testc!='5'&&testc!='9'&&testc!='Y')
                {
                    System.out.println("统一社会信用代码中登记管理部门代码错误");
                    return false;
                }
            }

            if(index==1)
            {
                if(testc!='1'&&testc!='2'&&testc!='3'&&testc!='9')
                {
                    System.out.println("统一社会信用代码中机构类别代码错误");
                    return false;
                }
            }

            tempNum=charToNum(testc);
            if(tempNum!=-1)								//验证代码中是否有错误字符
            {
                tempSum+=Weight[index]*tempNum;
            }
            else
            {
                System.out.println("统一社会信用代码中出现错误字符");
                return false;
            }
        }
        tempNum=31-tempSum%31;
        if(tempNum==31)  tempNum=0;
        if(charToNum(testUSCC.charAt(17))==tempNum)			//按照GB/T 17710标准对统一社会信用代码前17位计算校验码，并与第18位校验位进行比对
            return true;
        else
            return false;
    }
    /*
    [函数名]charToNum
    [功能]按照GB32100-2015标准代码字符集将用于检验的字符变为相应数字
    [参数]c:待转换的字符
    [返回值]转换完成后对应的数字,若无法找到字符集中字符，返回-1
    */
    public static int charToNum(char c)
    {
        switch (c)
        {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'A':
                return 10;
            case 'B':
                return 11;
            case 'C':
                return 12;
            case 'D':
                return 13;
            case 'E':
                return 14;
            case 'F':
                return 15;
            case 'G':
                return 16;
            case 'H':
                return 17;
            case 'J':
                return 18;
            case 'K':
                return 19;
            case 'L':
                return 20;
            case 'M':
                return 21;
            case 'N':
                return 22;
            case 'P':
                return 23;
            case 'Q':
                return 24;
            case 'R':
                return 25;
            case 'T':
                return 26;
            case 'U':
                return 27;
            case 'W':
                return 28;
            case 'X':
                return 29;
            case 'Y':
                return 30;
            default:
                return -1;
        }
    }

}
