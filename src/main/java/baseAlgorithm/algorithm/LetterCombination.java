package baseAlgorithm.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LetterCombination {
    public List<String> letterCombinations(String digits) {

        Map<Integer, List<String>> dictMap = new HashMap<>();
        List<String> dictList = new ArrayList<>();
        dictList.add("a");
        dictList.add("b");
        dictList.add("c");
        dictMap.put(2, dictList);
        dictList = new ArrayList<>();
        dictList.add("d");
        dictList.add("e");
        dictList.add("f");
        dictMap.put(3, dictList);
        dictList = new ArrayList<>();
        dictList.add("g");
        dictList.add("h");
        dictList.add("i");
        dictMap.put(4, dictList);
        dictList = new ArrayList<>();
        dictList.add("j");
        dictList.add("k");
        dictList.add("l");
        dictMap.put(5, dictList);
        dictList = new ArrayList<>();
        dictList.add("m");
        dictList.add("n");
        dictList.add("o");
        dictMap.put(6, dictList);
        dictList = new ArrayList<>();
        dictList.add("p");
        dictList.add("q");
        dictList.add("r");
        dictList.add("s");
        dictMap.put(7, dictList);
        dictList = new ArrayList<>();
        dictList.add("t");
        dictList.add("u");
        dictList.add("v");
        dictMap.put(8, dictList);
        dictList = new ArrayList<>();
        dictList.add("w");
        dictList.add("x");
        dictList.add("y");
        dictList.add("z");
        dictMap.put(9, dictList);

        List<String> res = new ArrayList<>();
        int len = digits.length();
        if (len == 0) {
            return null;
        }
        for (int i = 0; i < len; ++i) {
            List<String> dict = dictMap.get(Integer.parseInt(digits.substring(i, i + 1)));
            if (i == 0) {
                for (int j = 0; j < dict.size(); ++j) {
                    res.add(dict.get(j));
                }
            } else {
                List<String> tempRes = res;
                res = new ArrayList<>();
                for (String tr : tempRes) {
                    for (String d : dict) {
                        String c = tr + d;
                        res.add(c);
                    }
                }
            }
        }
        return res;
    }
}
