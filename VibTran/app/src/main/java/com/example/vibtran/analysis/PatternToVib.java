package com.example.vibtran.analysis;

import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;


public class PatternToVib {
    public static long[] encode(String otp, int power, int delay){
        otp = 'X'+otp+'Y';
        ArrayList<Long> result = new ArrayList<>();
        ArrayList<Integer> keepPattern = new ArrayList<>();
        for(int i=0 ; i< otp.length();i++){
            convert(keepPattern, result ,otp.charAt(i));
        }

        long[] tmp = new long[result.size()*2];
        tmp[0] =delay;
        for(int i =1;i<tmp.length;i+=2){
            tmp[i] = result.get(i/2)* power;//data
            if (i+1<tmp.length) {
                tmp[i + 1] =tmp[i]  > 0 ? 900 : delay;//break
            }
        }

        return tmp;
    }

    public static ArrayList<String> decode(long[] msg, int power) throws Exception{
        int decodeLength = 0;
        if(msg.length < 20){
            throw new Exception("message invalid");
        }else{
           decodeLength = msg.length - msg.length % 20;
        }

        ArrayList<String> result = new ArrayList<>();
         for(int i =0;i<decodeLength;i+=5){
             reverse(String.format("%d%d%d%d%d", msg[i+0]
                     ,msg[i+1]
                     ,msg[i+2]
                     ,msg[i+3]
                     ,msg[i+4]), result);
        }
         return result;
    }
    //reform the group of millisecond from k-mean
    public static long[] patternKReform(ArrayList<ArrayList<Long>> groupResult){
        long[] result;
        ArrayList<Long> resultKPattern = new ArrayList<>();
        ArrayList<Long> signalKAvg = new ArrayList<>();
        boolean haveX = false;
        int XPos = 0;


        if(groupResult != null) {
            for (int i = 0; i < groupResult.size(); i++) {
                ArrayList<Long> tmp = groupResult.get(i);
                signalKAvg.add(calMean(tmp));
            }
        }
        formPattern(resultKPattern,signalKAvg);
        result = new long[resultKPattern.size()];
            while (XPos < resultKPattern.size() - 5) {
                if (resultKPattern.get(XPos) == 1l && resultKPattern.get(XPos + 1) == 0l && resultKPattern.get(XPos + 2) == 0l && resultKPattern.get(XPos + 3) == 1l && resultKPattern.get(XPos + 4) == 1l) {
                    haveX = true;
                    break;
                }
                XPos++;
            }
            if(!haveX||XPos + 20 > resultKPattern.size()){
                XPos =0;
            }
            if(XPos + 20 < resultKPattern.size()) {
                for (int k = XPos; k < resultKPattern.size(); k++) {
                    result[k - XPos] = resultKPattern.get(k);
                    Log.d("result", String.valueOf(result[k - XPos]));
                }
            }
        return result;
    }
    public static long[] patternReform(long[] data){
        long[] result;
        ArrayList<Long> signalAvg = new ArrayList<>();
        ArrayList<Long> resultPattern = new ArrayList<>();
        boolean haveX = false;
        int XPos = 0;

            ArrayList<Long> calPatternResult = calPattern(data);
            for (int i = 0; i < calPatternResult.size(); i++) {
                signalAvg.add(calPatternResult.get(i));
                System.out.print("\n");
            }

            formPattern(resultPattern,signalAvg);
            Log.d("RPS", String.valueOf(resultPattern.size()));
            result = new long[resultPattern.size()];
            long[] tmp = new long[resultPattern.size()];
            while (XPos < resultPattern.size() - 5) {
                if (resultPattern.get(XPos) == 1l && resultPattern.get(XPos + 1) == 0l && resultPattern.get(XPos + 2) == 0l && resultPattern.get(XPos + 3) == 1l && resultPattern.get(XPos + 4) == 1l) {
                    Log.d("XPOS", String.valueOf(XPos));
                    haveX = true;
                    break;
                }
                XPos++;
            }
            if(!haveX||XPos + 20 > resultPattern.size()){
                    Log.d("X", String.valueOf(haveX));
                    XPos =0;
            }
            if(XPos + 20 < resultPattern.size()) {
                for (int k = XPos; k < resultPattern.size(); k++) {
                        result[k - XPos] = resultPattern.get(k);

                }
            }

        return result;
    }

    // from the pattern to 0 and 1 by list of mean
    public static void formPattern(ArrayList<Long> resultPattern, ArrayList<Long>signalAvg){
        int i = 1;
        resultPattern.add(1l);
        while (i < signalAvg.size()) {
            int addZero = (int) ((signalAvg.get(i) - signalAvg.get(i - 1) - 900) / 1000);
            if (addZero < 0) {
                addZero = 0;
            }
            for (int j = 0; j < addZero; j++) {
                resultPattern.add(0l);
            }
            resultPattern.add(1l);
            i++;
        }
    }
    private static long calMean(ArrayList<Long> data){
        if(data.size() != 0) {
            long sum = 0;
            for (int i = 0; i < data.size(); i++) {
                sum += data.get(i);
            }
            return sum / data.size();
        }else{
            return 0;
        }
    }
    //threshold based grouping method
    private static ArrayList<Long> calPattern(long[] data){
        ArrayList<Long> avgResult = new ArrayList<>();
        long[] caLResult = new long[data.length];
        if(data.length != 0 ) {
            int i = 0;
            int num = 1;
            long signalSum = data[0];
            int signalCount = 0;
            while (i < data.length) {
                if (i != data.length - 1) {

                    if (data[i + 1] - data[i] < 250) {

                        signalSum += data[i + 1];
                        num++;
                        i++;
                    } else {
                        caLResult[signalCount] = signalSum / num;
                        signalCount++;
                        signalSum = data[i + 1];
                        num = 1;
                        i++;
                    }
                } else {
                    caLResult[signalCount] = signalSum / num;
                    signalCount++;
                    num = 1;
                    i++;
                }
            }
        }
            for(int j=0;j<caLResult.length;j++){

              if(caLResult[j] != 0 ){
                  avgResult.add(caLResult[j]);
              }
            }

        return avgResult;
    }

    private static void reverse(String s, ArrayList<String> sb){
        HashMap<String,String> mapping = new HashMap<String, String>();
        mapping.put ("10011","X");
        mapping.put ("11001","Y");
        mapping.put ("10001","0");
        mapping.put ("10010","1");
        mapping.put ("10100","2");
        mapping.put ("11000","3");
        mapping.put ("01001","4");
        mapping.put ("00101","5");
        mapping.put ("00011","6");
        mapping.put ("01100","7");
        mapping.put ("00110","8");
        mapping.put ("01010","9");
        mapping.put ("01101","A");
        mapping.put ("10110","B");
        mapping.put ("10101","C");
        mapping.put ("01110","D");
        mapping.put ("00111","E");
        mapping.put ("11100","F");
        sb.add(mapping.get(s));
    }
    private static void convert(ArrayList<Integer> calBit,ArrayList<Long> l, char a){
        StringBuffer sb = new StringBuffer();
        long[][] mapping = new long[][]{
                new long[]{1,0,0,1,1},
                new long[]{1,1,0,0,1},
                new long[]{1,0,0,0,1},
                new long[]{1,0,0,1,0},
                new long[]{1,0,1,0,0},
                new long[]{1,1,0,0,0},
                new long[]{0,1,0,0,1},
                new long[]{0,0,1,0,1},
                new long[]{0,0,0,1,1},
                new long[]{0,1,1,0,0},
                new long[]{0,0,1,1,0},
                new long[]{0,1,0,1,0},
                new long[]{0,1,1,0,1},
                new long[]{1,0,1,1,0},
                new long[]{1,0,1,0,1},
                new long[]{0,1,1,1,0},
                new long[]{0,0,1,1,1},
                new long[]{1,1,1,0,0}
        };
         String chars = "XY0123456789ABCDEF";
         long[] temp = mapping[chars.indexOf(a)];
         for(int i =0;i<temp.length;i++){
             l.add(temp[i]);
         }
    }
}
