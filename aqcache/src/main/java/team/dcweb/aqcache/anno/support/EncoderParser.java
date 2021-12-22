package team.dcweb.aqcache.anno.support;

import java.util.function.Function;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public interface EncoderParser {
    Function<Object, byte[]> parseEncoder(String valueEncoder);

    Function<byte[], Object> parseDecoder(String valueDecoder);
}
