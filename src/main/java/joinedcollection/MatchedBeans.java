package joinedcollection;

/**
 * Created by KeyonX on 2016/11/24.
 */
public class MatchedBeans {
    private Object left;
    private Object right;//当使用左连接时，可能会为null
    MatchedBeans(Object left,Object right){
        this.left=left;
        this.right = right;
    }
    public <T> T getLeft() {
        return (T)left;
    }
    public <T> T getRight() {
        return (T)right;
    }
}
