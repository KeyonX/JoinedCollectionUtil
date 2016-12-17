package joinedcollection;

import util.StringUtils;

import java.util.*;

/**
 * Created by KeyonX on 2016/11/02.
 */
public class JoinMatcher {
    private JoinedBeans leftColl;
    private JoinedBeans rightColl;
    private int joinType;
    static int LEFT_JOIN = 1;
    static int INNER_JOIN = 2;

    JoinMatcher(JoinedBeans leftColl, JoinedBeans rightColl, int joinType){
        this.leftColl = leftColl;
        this.rightColl = rightColl;
        this.joinType = joinType;
    }

    public JoinMatcher on(String leftColumn, String rightColumn) throws Exception{
        if(StringUtils.isBlank(leftColumn)||StringUtils.isBlank(rightColumn)){
            throw new IllegalArgumentException("连接字段不能为null或者空字符串!");
        }
        this.leftColl.setJoinColumn(leftColumn);
        this.rightColl.setJoinColumn(rightColumn);
        return this;
    }

    /**
     * 对匹配的数据进行转化。由调用者处理同名冲突
     * @param dataConverter 对匹配的数据进行转化
     * @return
     * @throws Exception
     */
    public JoinedResult select(DataConverter dataConverter) throws Exception{
        if(dataConverter==null){
            throw new RuntimeException("dataConverter cann't be null!");
        }
        return select0(dataConverter);
    }

    private JoinedResult select0(DataConverter dataConverter) throws Exception{
        List<Map<String,String>> resultList = new LinkedList<Map<String, String>>();
        if(leftColl.getBeanData()==null||leftColl.getBeanData().size()<=0){
            return new JoinedResult(resultList);
        }
        //将右表rightBean,以其连接字段rightColumn为key，value为rightBeanList，组装成Map
        Map<String,List> matchMap = transformToMap(rightColl);
        //遍历左表leftBean，匹配对应的右表记录
        for(Object leftData : this.leftColl.getBeanData()){
            List matchList = matchMap.get(this.leftColl.getJoinColumnValue(leftData));
            List<Map<String,String>> allMatchedList;
            if(matchList==null){
                //内连接时，舍弃当前数据
                if(joinType==INNER_JOIN){continue;}
                //只转换左实体
                allMatchedList = getAllMatchedList(leftData, null, dataConverter);
            }else{
                allMatchedList = getAllMatchedList(leftData,matchList,dataConverter);
            }
            if(allMatchedList!=null){
                resultList.addAll(allMatchedList);
            }
        }
        return new JoinedResult(resultList);
    }

    private Map<String,List> transformToMap(JoinedBeans joinedCollection) throws Exception{
        Map<String,List> matchMap = new HashMap<String,List>();
        if(joinedCollection.getBeanData()!=null&&joinedCollection.getBeanData().size()>0){
            for(Object bean : joinedCollection.getBeanData()){
                String key = joinedCollection.getJoinColumnValue(bean);
                List beanList = matchMap.get(key);
                if(beanList==null){
                    beanList = new LinkedList();
                    matchMap.put(key,beanList);
                }
                beanList.add(bean);
            }
        }
        return matchMap;
    }

    private List<Map<String,String>> getAllMatchedList(Object left, List rightList, DataConverter converter) throws Exception{
        List<Map<String, String>> resultList = new LinkedList<Map<String, String>>();
        Map<String,String> convertResult;
        if(rightList==null){
            convertResult = new HashMap<String, String>();
            if(converter.convert(new MatchedBeans(left,null),convertResult)){
                resultList.add(convertResult);
            }
        }else{
            for(Object right : rightList){
                convertResult = new HashMap<String, String>();
                if(converter.convert(new MatchedBeans(left,right),convertResult)){
                    resultList.add(convertResult);
                }
            }
        }
        return resultList;
    }

}