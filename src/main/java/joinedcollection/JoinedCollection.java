package joinedcollection;

import org.apache.commons.beanutils.BeanUtils;
import util.StringUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import static java.util.Locale.ENGLISH;

/**
 * Created by KeyonX on 2016/11/02.
 * 用于数据匹配。仅支持Map类型和属性有get方法的实体
 */
public class JoinedCollection extends JoinedBeans{
    public JoinedCollection(Collection beanData) {
        this.beanData = beanData;
    }

}
