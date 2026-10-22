package com.korai.study.Q;

public class q01 {
    public static void main(String[] args) {
        System.out.println(MathUtil.max(3,9,5));
        System.out.println(TempConverter.toFahrenheit(36.5));
        System.out.println(TempConverter.toCelsius(212));
        System.out.println(StringUtil.isPalindrome("level"));
        System.out.println(StringUtil.isPalindrome("java"));
        System.out.printf("%.1f%n", ArrayUtil.average(new int[]{80, 95, 70}));
        System.out.println(Validator.isValidPassword("abc12345")); // true
        System.out.println(Validator.isValidPassword("abcdefgh")); // false
        System.out.println(Validator.isValidPassword("a1b2")); // false

    }
}

class MathUtil{
    static int max(int a, int b, int c){
        int max=0;

        if(a<b){
            max=b;
        }
        else{
            max=a;
        }
        if(b<c){
            max=c;
        }

        return max;
    }

}

class TempConverter{
    static double toFahrenheit(double cel){
        double fah = cel*9/5+32;
    return fah;
    }

    static double toCelsius(double fah){
        double cel = (fah-32)*5/9;
    return cel;
    }
}

class StringUtil{
    static boolean isPalindrome(String a){
        int left= 0;
        int right = a.length()-1;

        while(left<right){
            if(a.charAt(left)!=a.charAt(right)){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}

class ArrayUtil{
    static double average(int[] a){
        double aver=0;
        if(a.length==0){
            return 0.0;
        }
        else{
            for(int i=0;i<a.length;i++){
                aver+=a[i];
            }
            aver= aver/3;
        }

        return aver;
    }
}

class Validator{
    static boolean isValidPassword(String pass){
        if(pass.length()<8){
            return false;
        }
        for(int i =0;i<pass.length();i++){
            char c = pass.charAt(i);
            if(Character.isDigit(c)){
                return true;
            }
        }
        return false;
    }
}