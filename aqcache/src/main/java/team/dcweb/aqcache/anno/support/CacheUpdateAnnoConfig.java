package team.dcweb.aqcache.anno.support;

import java.util.function.Function;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class CacheUpdateAnnoConfig extends CacheAnnoConfig {

    private String value;
    private boolean multi;

    private Function<Object, Object> valueEvaluator;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Function<Object, Object> getValueEvaluator() {
        return valueEvaluator;
    }

    public void setValueEvaluator(Function<Object, Object> valueEvaluator) {
        this.valueEvaluator = valueEvaluator;
    }

    public boolean isMulti() {
        return multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }
}