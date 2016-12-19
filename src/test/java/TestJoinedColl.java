import joinedcollection.DataConverter;
import joinedcollection.JoinedCollection;
import joinedcollection.MatchedBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by KeyonX on 2016/12/16.
 */
public class TestJoinedColl {
    public static void main(String[] args) throws Exception{
        JoinedCollection orderList = new JoinedCollection(getOrders());
        JoinedCollection consumerList = new JoinedCollection(getConsumers());
        JoinedCollection productList = new JoinedCollection(getProducts());
        List<Map<String,String>> result = orderList.innerJoin(consumerList).on("consumerId","id").select(new DataConverter() {
            @Override
            public boolean convert(MatchedBeans bean, Map<String, String> convertResult) {
                convertResult.putAll(toMap(bean.getLeft()));
                Consumer consumer = bean.getRight();
                convertResult.put("consumerName", consumer.getName());
                convertResult.put("sex", consumer.getSex() == 1 ? "男" : "女");
                return true;
            }
        }).innerJoin(productList).on("productId","id").select(new DataConverter() {
            @Override
            public boolean convert(MatchedBeans bean, Map<String, String> convertResult) {
                Map<String,String> left = bean.getLeft();
                Product product = bean.getRight();
                convertResult.putAll(left);
                convertResult.put("productName",product.getName());
                convertResult.put("price",String.valueOf(product.getPrice()));
                return true;
            }
        }).getBeanData();
        System.out.println(result);
    }

    public static List<Order> getOrders() {
        List<Order> orderList = new ArrayList<Order>();
        Order order = new Order();
        order.setId(1);
        order.setProductId(1);
        order.setConsumerId(1);
        orderList.add(order);
        order = new Order();
        order.setId(2);
        order.setProductId(2);
        order.setConsumerId(2);
        orderList.add(order);
        order = new Order();
        order.setId(3);
        order.setProductId(3);
        order.setConsumerId(3);
        orderList.add(order);
        return orderList;
    }
    public static List<Consumer> getConsumers(){
        List<Consumer> consumerList = new ArrayList<Consumer>();
        Consumer con = new Consumer();
        con.setId(1);
        con.setName("Bob");
        con.setSex(1);
        consumerList.add(con);
        con = new Consumer();
        con.setId(2);
        con.setName("Mary");
        con.setSex(2);
        consumerList.add(con);
        con = new Consumer();
        con.setId(3);
        con.setName("Klein");
        con.setSex(1);
        consumerList.add(con);
        return consumerList;
    }

    public static List<Product> getProducts() {
        List<Product> productList = new ArrayList<Product>();
        Product product = new Product();
        product.setId(1);
        product.setName("老人与海");
        product.setPrice(12);
        productList.add(product);
        product = new Product();
        product.setId(2);
        product.setName("战争与和平");
        product.setPrice(14);
        productList.add(product);
        product = new Product();
        product.setId(3);
        product.setName("了不起的盖茨比");
        product.setPrice(18);
        productList.add(product);
        return productList;
    }



}
