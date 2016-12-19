# JoinedCollectionUtil
以简洁易读的方式实现集合的连接(模拟SQL连接操作)。Join collections with a simple and readable way(just like joining operation with SQL)
### 背景
针对数据库单表数据量过大或者已分库分表类似场景中，无法使用数据库的join操作进行查询拼接数据的情况。
### 目的
对于多个表单独进行查询需要的数据，然后在内存中，进行join(leftJoin和inner join)操作，以简洁易读的方式，方便维护，提高开发效率
### 示例说明
存在以下3个表，订单Order，用户Consumer，商品Product。获取订单列表，然后根据其中的consumeId和productId批量查询用户信息和商品信息。最后将3个集合进行join操作。其中DataConverter提供对匹配的数据进行回调处理(例如常用枚举字段转换对应文本)功能   

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
