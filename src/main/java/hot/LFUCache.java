package hot;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class LFUCache {

    HashMap<Integer, Integer> kvMap;
    HashMap<Integer, Integer> kfMap;
    HashMap<Integer, LinkedHashSet<Integer>> fkSetMap;

    int cap;// 容量
    int minFre;// 最小频次
    public LFUCache(int capacity) {
        this.cap = capacity;
        kvMap = new HashMap<>();
        kfMap = new HashMap<>();
        fkSetMap = new HashMap<>();
    }

    public int get(int key) {
        if (!kvMap.containsKey(key)) {
            return -1;
        }
        return refreshKey(key);
    }

    private Integer refreshKey(int key) {
        Integer value = kvMap.get(key);
        Integer f = kfMap.get(key);
        Integer nf = f + 1;
        kfMap.put(key, nf);

        // 调整 频率
        LinkedHashSet<Integer> set = fkSetMap.get(f);
        set.remove(key);
        if (set.size() > 0) {
            fkSetMap.put(f, set);
        } else {
            fkSetMap.remove(f);
        }

        LinkedHashSet<Integer> set1 = fkSetMap.getOrDefault(nf, new LinkedHashSet<>());
        set1.add(key);
        fkSetMap.put(nf, set1);

        // 更新最小频次
        if (!fkSetMap.containsKey(minFre)) {
            minFre=minFre+1;
        }
        return value;
    }

    public void put(int key, int value) {
        if (kvMap.containsKey(key)) {
            kvMap.put(key, value);
            refreshKey(key);
            return;
        }
        if (kvMap.size() >= cap) {
            // 删除最小频次
            LinkedHashSet<Integer> sets = fkSetMap.get(minFre);
            Integer minKey = sets.iterator().next();
            kvMap.remove(minKey);
            kfMap.remove(minKey);
            if (sets.size() == 1) {
                fkSetMap.remove(minFre);
            } else {
                sets.remove(minKey);
                fkSetMap.put(minFre, sets);
            }
        }
        minFre=1;
        kvMap.put(key, value);
        kfMap.put(key, 1);
        LinkedHashSet<Integer> defaultValue = new LinkedHashSet<>();
        LinkedHashSet<Integer> orDefault = fkSetMap.getOrDefault(minFre, defaultValue);
        orDefault.add(key);
        fkSetMap.put(minFre, orDefault);
    }
}
