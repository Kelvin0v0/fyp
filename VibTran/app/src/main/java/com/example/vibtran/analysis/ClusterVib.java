package com.example.vibtran.analysis;

import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.TreeMap;

public class ClusterVib {

    // loop k to k^2 times to find the encoded pattern
    public static long[] kmeanEncodedResult(long[] data, int k){
        if(data.length == 0 ){
            return new long[]{};
        }

        for(int i = k; i <=k*2 ; i++){
            ArrayList<ArrayList<Long>> d = kmean_patched(data, i);
            long[] pattern = PatternToVib.patternKReform(d);
            try {
                ArrayList<String> result = PatternToVib.decode(pattern, 200);
                if(result.get(1).matches("null") || result.get(2).matches("null")){
                    continue;
                }else{
                    return pattern;
                }
            }catch (Exception e){
                Log.e("data fail","message invalid");
            }
        }
        return new long[]{};
    }

    // find the best k-mean group result by the group length
    public static ArrayList<ArrayList<Long>> kmean_patched(long[] data, int k) {
        if(data.length == 0 ){
            ArrayList<ArrayList<Long>> empty = new ArrayList<>();
            return empty;
        }
        TreeMap<Integer, ArrayList<ArrayList<Long>>> cache = new TreeMap();

        for (int z = 0; z < data.length * 10; z++) {
            ArrayList<ArrayList<Long>> d = ClusterVib.kmean(data, k);

            if (!empty_group_exists(d)) {
                int bg = biggest_group_length(d);
                cache.put(bg, d);
            }
        }

        int min_bg = cache.firstKey();
        return cache.get(min_bg);
    }
    // check the empty group from the k-mean result
    public static boolean empty_group_exists(ArrayList<ArrayList<Long>> d) {
        if(d != null){
            for (int i = 0; i < d.size(); i++) {
                if (d.get(i).size() == 0) {
                    return true;
                }
            }
        }
        return false;
    }
    // find the biggest group length in k-mean result
    public static int biggest_group_length(ArrayList<ArrayList<Long>> d) {
        int return_val = 0;
        if(d != null) {
            for (int i = 0; i < d.size(); i++) {
                if (return_val < d.get(i).size()) {
                    return_val = d.get(i).size();
                }
            }
        }
        return return_val;
    }
    //1D size k-mean clustering
    public static ArrayList<ArrayList<Long>> kmean(long[] data, int k) {
        if (k == 0 || k >= data.length) {
            return null;
        }

        ArrayList<Double> pickNum = new ArrayList<Double>();
        ArrayList<Long> numbersCopy = new ArrayList<Long>();
        for (int i = 0; i < data.length; i++) {
            numbersCopy.add(data[i]);
        }

        // random k center
        do {
            pickNum.add((double) getRandom(numbersCopy));
        } while (pickNum.size() < k);
        Collections.sort(pickNum);

        ArrayList<ArrayList<Long>> groups = new ArrayList<ArrayList<Long>>();
        while (true) {
            groups = grouping(data, pickNum);

            ArrayList<Double> newMean = new ArrayList<>();
            for (int i = 0; i < groups.size(); i++) {
                newMean.add(mean(groups.get(i)));
            }
            Collections.sort(newMean);

            boolean is_same = array_same(pickNum, newMean);
            if (is_same) {
                break;
            }
            pickNum = newMean;
        }

        return groups;
    }
    // remove the selected index
    public static long getIndex(ArrayList<Long> array, int index) {
        long num = array.get(index);
        array.remove(index);
        return num;
    }

    public static long getRandom(ArrayList<Long> array) {
        int rnd = new Random().nextInt(array.size());
        return getIndex(array, rnd);
    }

    public static ArrayList<ArrayList<Long>> grouping(
            long[] data,
            ArrayList<Double> pickNum
    ) {
        ArrayList<ArrayList<Long>> grouping = new ArrayList<ArrayList<Long>>();
        for (int i = 0; i < pickNum.size(); i++) {
            grouping.add(new ArrayList<Long>());
        }

        for (int i = 0; i < data.length; i++) {
            long ele = data[i];

            double dist = Double.MAX_VALUE;
            double nearest = -1;
            int nearest_id = -1;
            for (int j = 0; j < pickNum.size(); j++) {
                double val = pickNum.get(j);
                double tmp_dist = Math.abs((double) ele - val);
                if (dist > tmp_dist) {
                    dist = tmp_dist;
                    nearest = val;
                    nearest_id = j;
                } else if (dist == tmp_dist) {
                    if (nearest > val) {
                        nearest = val;
                        nearest_id = j;
                    }
                }
            }

            grouping.get(nearest_id).add(ele);
        }

        return grouping;
    }

    public static double mean(ArrayList<Long> data) {
        int total = 0;
        for (int i = 0; i < data.size(); i++) {
            total += data.get(i);
        }
        return (double) total / (double) data.size();
    }

    public static boolean array_same(
            ArrayList<Double> data1,
            ArrayList<Double> data2
    ) {
        for (int i = 0; i < data1.size(); i++) {
            String d1 = String.format("%.4f", data1.get(i));
            String d2 = String.format("%.4f", data2.get(i));
            if (!d1.equals(d2)) {
                return false;
            }
        }
        return true;
    }
}
