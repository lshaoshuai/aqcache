package team.dcweb.aqcache.support;

import java.io.Serializable;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class CacheMessage implements Serializable {
    private static final long serialVersionUID = -462475561129953207L;

    public static final int TYPE_PUT = 1;
    public static final int TYPE_PUT_ALL = 2;
    public static final int TYPE_REMOVE = 3;
    public static final int TYPE_REMOVE_ALL = 4;

    private int type;

    private Object[] keys;

    /**
     * this field is reserved.
     */
    private Object[] values;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object[] getKeys() {
        return keys;
    }

    public void setKeys(Object[] keys) {
        this.keys = keys;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }
}
