
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Zeng Rui on 2018/3/30.
 */
public class Arraytest {


    public static void main(String[] args) {
       printListFromTailToHead(null);


    }


    public static  ArrayList<Integer> printListFromTailToHead(ListNode listNode) {

        ArrayList<Integer> arrays = new ArrayList<>();
        while(listNode != null){
            arrays.add(listNode.val);
            listNode =listNode.next;

        }

        if(arrays.isEmpty()){
            return null;
        }

        return arrays;



    }



    public static String replaceSpace(StringBuffer str) {
        String a = str.toString();
        char[] chars = a.toCharArray();
        Vector<Integer> vector = new Vector<>();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                vector.add(i);
            }

        }

        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            if ( count<vector.size() &&i == vector.get(count)) {
                buffer.append("%20");
                count++;
            } else
                buffer.append(chars[i]);
        }


        return buffer.toString();

    }

    class ListNode{
        int val;
        ListNode next;
    }


}




