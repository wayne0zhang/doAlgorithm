package array;

/**
 * 差分数组策略
 * 适用于对同一个数组进行多次，且连续区间的等值操作，通过差分数组可以快速修改，然后只在最后需要结果的时候再遍历一次得到结果。
 *
 * 差分数组可以通过自身的值，遍历之后得到原数组
 */
public class Difference {
    int[] diffs;

    public Difference(int[] nums) {
        this.diffs = new int[nums.length];
        this.diffs[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            this.diffs[i] = nums[i] - nums[i - 1];
        }
    }

    public void increment(int i, int j, int val) {
        // 差分数组，第 i 个会增加 val
        // 第 j+1 个会减小 val
        diffs[i] += val;
        // 如果 j+1 超过了，则不需要处理
        if (j + 1 < diffs.length) {
            diffs[j + 1] -= val;
        }
    }

    /**
     * 从差分数组得到原数组
     */
    public int[] result() {
        int[] ret = new int[diffs.length];
        ret[0] = diffs[0];
        for (int i = 1; i < diffs.length; i++) {
            ret[i] = ret[i - 1] + diffs[i - 1];
        }

        return ret;
    }
}
