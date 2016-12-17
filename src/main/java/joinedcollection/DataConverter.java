package joinedcollection;

import org.apache.commons.beanutils.BeanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KeyonX on 2016/11/07.
 */
public abstract class DataConverter {
    /**
     * 将bean中封装的已匹配数据进行处理，放入beanMap中
     * @param bean
     * @return false表示拒绝转化后结果。匹配结果中将去掉相关数据
     */
    public abstract boolean convert(MatchedBeans bean, Map<String, String> convertResult);

    public Map<String,String> toMap(Object bean){
        try{
            return BeanUtils.describe(bean);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new HashMap<String, String>();
    }
}
