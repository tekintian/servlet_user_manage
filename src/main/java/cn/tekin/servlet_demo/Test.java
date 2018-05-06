package cn.tekin.servlet_demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        ArrayList al = new ArrayList();
        al.add(null);
        al.add(null);
        al.add(null);
        for (int i = 0; i < al.size(); i++) System.out.println(al.get(i));
        System.out.println();

        Map mp = new HashMap();
        mp.put(null, null);
        mp.put(null, null);
        mp.put(null, null);

        System.out.println(mp.get(null));
    }
}
