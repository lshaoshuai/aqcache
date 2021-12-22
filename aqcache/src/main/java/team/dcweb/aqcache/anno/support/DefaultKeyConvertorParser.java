package team.dcweb.aqcache.anno.support;

import team.dcweb.aqcache.anno.KeyConvertor;
import team.dcweb.aqcache.exception.CacheConfigException;
import team.dcweb.aqcache.support.FastjsonKeyConvertor;

import java.util.function.Function;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class DefaultKeyConvertorParser implements KeyConvertorParser {
    @Override
    public Function<Object, Object> parseKeyConvertor(String convertor) {
        if (convertor == null) {
            return null;
        }
        if (KeyConvertor.FASTJSON.equalsIgnoreCase(convertor)) {
            return FastjsonKeyConvertor.INSTANCE;
        } else if (KeyConvertor.NONE.equalsIgnoreCase(convertor)) {
            return null;
        }
        throw new CacheConfigException("not supported:" + convertor);
    }
}