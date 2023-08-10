package hot;

// k个一组反转链表
public class ReverseKListNode {
    public SingleListNode reverseKGroup(SingleListNode head, int k) {
        /**
         * 采用递归手段，先反转第一组k，然后，递归剩下的节点
         */
        SingleListNode a,b;
        a = b = head;
        for (int i = 0; i < k; i++) {
            // 不足k，不处理
            if (b == null) {
                return head;
            }
            b = b.next;
        }
        SingleListNode newHead = reverse(a, b);
        // 递归下一个k组
        a.next = reverseKGroup(b, k);
        return newHead;
    }

    /**
     * 从 a 反转到 b
     * 反转过程中需要 之前/当前/之后 临时节点来保存变量
     *
     * 另外，如果是反转全部链表，则可以通过 b=null 来完成。
     */
    private SingleListNode reverse(SingleListNode a, SingleListNode b) {
        SingleListNode cur=a;
        SingleListNode pre,nxt;
        pre = nxt = null;
        while (cur != b) {
            nxt = cur.next;

            cur.next = pre;
            pre = cur;

            cur = nxt;
        }
        return pre;
    }

}

/**
 * 单链表对象
 */
class SingleListNode {
    int val;
    SingleListNode next;

    public SingleListNode() {
    }

    public SingleListNode(int val) {
        this.val = val;
    }

    public SingleListNode(int val, SingleListNode next) {
        this.val = val;
        this.next = next;
    }
}