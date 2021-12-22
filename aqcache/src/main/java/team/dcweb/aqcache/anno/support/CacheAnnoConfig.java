package team.dcweb.aqcache.anno.support;

import team.dcweb.aqcache.AqCache;

import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class CacheAnnoConfig {
    private String area;
    private String name;
    private String key;
    private String condition;

    private Function<Object, Boolean> conditionEvaluator;
    private Function<Object, Object> keyEvaluator;
    private AqCache<?, ?> aqCache;
    private Method defineMethod;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Function<Object, Boolean> getConditionEvaluator() {
        return conditionEvaluator;
    }

    public void setConditionEvaluator(Function<Object, Boolean> conditionEvaluator) {
        this.conditionEvaluator = conditionEvaluator;
    }

    public Function<Object, Object> getKeyEvaluator() {
        return keyEvaluator;
    }

    public void setKeyEvaluator(Function<Object, Object> keyEvaluator) {
        this.keyEvaluator = keyEvaluator;
    }

    public AqCache<?, ?> getCache() {
        return aqCache;
    }

    public void setCache(AqCache<?, ?> aqCache) {
        this.aqCache = aqCache;
    }

    public Method getDefineMethod() {
        return defineMethod;
    }

    public void setDefineMethod(Method defineMethod) {
        this.defineMethod = defineMethod;
    }
}
