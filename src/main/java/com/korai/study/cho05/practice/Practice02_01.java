package com.korai.study.cho05.practice;

public class Practice02_01 {
    public static void main(String[] args) {


        /*
        for(int j=2 ; j<10 ; j++){
            System.out.println("["+j+"단]");
            for(int i=1 ; i<10 ; i++){
                System.out.print(j + " x "+ i + " = " + j * i);
                if(i%2==0) System.out.print("\n");
                else System.out.print("\t");
            }
            System.out.println("\n");
        }
*/
        /*
        String numb="";

        for(int j=2 ; j<10 ; j++){
            numb += "["+j+"단]\n";
            for(int i=1 ; i<10 ; i++) {
                numb += j + " x "+ i + " = " + j * i;
                if(i%2==0) numb += "\n"; // 띄우기를 삼항연사자로 아래와같이
                else numb+="\t";    // == i%2==0 || num == 9 ? "\n" : "\t";
            }
            numb+="\n";
        }
        System.out.println(numb);
*/

        int[][][] gugudanArray = new int[8][9][3];

        String gugudanString = "";
        for(int i = 0;i<gugudanArray.length; i++){
            int dan = i + 2;
            for(int j=0;j<gugudanArray[i].length;j++){
                int num = j + 1;
                int result = dan * num;
                gugudanArray[i][j][0] = dan;
                gugudanArray[i][j][1] = num;
                gugudanArray[i][j][2] = result;
                     gugudanString = String.format(
                            "%d x %d = %d%s",
                            gugudanArray[i][j][0],
                            gugudanArray[i][j][1],
                            gugudanArray[i][j][2],
                            gugudanArray[i][j][1] % 2 == 0 || gugudanArray[i][j][1] == 9 ? "\n" : "\t");
                    System.out.print(gugudanString);

            }
        }



    }
}
