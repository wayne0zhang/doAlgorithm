package hot;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
public class LRUCache {
    LinkedHashMap<Integer,Integer> list = new LinkedHashMap<Integer,Integer>();
    int cap;

    public LRUCache(int capacity) {
        this.cap=capacity;
    }

    public int get(int key) {
        if (!list.containsKey(key)) {
            return -1;
        }
        Integer value = list.remove(key);
        list.put(key, value);
        return value;
    }

    public void put(int key, int value) {
        if (!list.containsKey(key)) {
            if (cap <= list.size()) {
                Integer firstKey = list.keySet().iterator().next();
                list.remove(firstKey);
            }
            list.put(key, value);
            return;
        }
        list.remove(key);
        list.put(key, value);
    }
}

class Node {
    int key,val;
    Node pre,nxt;

    public Node(int key, int val) {
        this.key = key;
        this.val = val;
    }
}

/**
 * 双向链表：访问后，需要把对应的 node 移动到尾节点，最旧到节点在 头节点
 */
class DoubleListNode{
    int size;
    Node head,tail;

    public DoubleListNode() {
        Node head = new Node(0, 0);
        Node tail = new Node(0, 0);
        this.head = head;
        this.tail = tail;
    }

    public Node removeFirst(){
        if (head.nxt == tail)
            return null;
        Node first = head.nxt;
        remove(first);
        return first;
    }

    public void remove(Node node){
        node.pre.nxt=node.nxt;
        node.nxt.pre= node.pre;
        size--;
    }

    public void addLast(Node node) {
        tail.pre.nxt = node;
        node.pre=tail.pre;
        node.nxt = tail;
        tail.pre = node;
        size++;
    }
}

class LRUCache1 {
    Map<Integer, Node> map = new HashMap<>();
    DoubleListNode list = new DoubleListNode();
    int cap;

    public LRUCache1(int capacity) {
        this.cap=capacity;
    }

    public int get(int key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            list.remove(node);
            list.addLast(node);
            return node.val;
        }
        return -1;
    }

    public void put(int key, int value) {
        Node node1 = new Node(key, value);
        if (map.containsKey(key)) {
            Node node = map.get(key);
            list.remove(node);
            list.addLast(node1);
            map.put(key, node1);
            return ;
        }
        if (list.size >= cap) {
            Node node = list.removeFirst();
            map.remove(node.key);
        }
        map.put(key, node1);
        list.addLast(node1);
    }
}

/**
 * Your hot.LRUCache object will be instantiated and called as such:
 * hot.LRUCache obj = new hot.LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
//leetcode submit region end(Prohibit modification and deletion)
