package team.dcweb.aqcache.anno;

import java.util.function.Function;

/**
 * @version 1.0.0
 * @since 1.8
 **/
public interface SerialPolicy {
    String JAVA = "JAVA";

    String KRYO = "KRYO";

    Function<Object, byte[]> encoder();

    Function<byte[], Object> decoder();
}