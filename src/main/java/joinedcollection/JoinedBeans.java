package joinedcollection;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by KeyonX on 2016/12/17.
 */
public abstract class JoinedBeans {
    Collection beanData;
    String joinColumn;

    @SuppressWarnings("unchecked")
    public <T extends Collection> T getBeanData() {
        return (T)beanData;
    }

    void setJoinColumn(String joinColumn){
        this.joinColumn = joinColumn;
    }


    /**
     * 获取bean的joinColumn属性值
     * @param bean
     * @return
     * @throws Exception
     */
    String getJoinColumnValue(Object bean) throws Exception{
        if(bean instanceof Map){
            return (String)((Map) bean).get(joinColumn);
        }else{
            return BeanUtils.getProperty(bean,joinColumn);
        }
    }


    public JoinMatcher leftJoin(JoinedBeans rightJoinedCollection){
        return new JoinMatcher(this, rightJoinedCollection,JoinMatcher.LEFT_JOIN);
    }

    public JoinMatcher innerJoin(JoinedBeans rightJoinedCollection){
        return new JoinMatcher(this, rightJoinedCollection,JoinMatcher.INNER_JOIN);
    }

}
