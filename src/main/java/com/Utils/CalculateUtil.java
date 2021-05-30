package com.Utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CalculateUtil {
    public static boolean isSetEqual(Set set1, Set set2) {

        if (set1 == null && set2 == null) {
            return true; // Both are null
        }

        if (set1 == null || set2 == null || set1.size() != set2.size()
                || set1.size() == 0 || set2.size() == 0) {
            return false;
        }

        Iterator ite1 = set1.iterator();
        Iterator ite2 = set2.iterator();

        boolean isFullEqual = true;

        while (ite2.hasNext()) {
            if (!set1.contains(ite2.next())) {
                isFullEqual = false;
            }
        }

        return isFullEqual;
    }

    public static void main(String[] args) {
        Set<Integer> a = new HashSet<>();
        Set<Integer> b = new HashSet<>();
        a.add(1);
        a.add(2);
        a.add(3);
        b.add(1);
        b.add(2);
//        b.add(3);
        System.out.println(isSetEqual(a,b));
    }

}
