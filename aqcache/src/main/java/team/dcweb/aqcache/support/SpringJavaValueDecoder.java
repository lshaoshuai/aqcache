package team.dcweb.aqcache.support;

import org.springframework.core.ConfigurableObjectInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Create On 2021/7/17
 *
 * @author hongkun
 * @version 1.0.0
 */
public class SpringJavaValueDecoder extends JavaValueDecoder {

    public static final SpringJavaValueDecoder INSTANCE = new SpringJavaValueDecoder(true);

    public SpringJavaValueDecoder(boolean useIdentityNumber) {
        super(useIdentityNumber);
    }

    @Override
    protected ObjectInputStream buildObjectInputStream(ByteArrayInputStream in) throws IOException {
        return new ConfigurableObjectInputStream(in, Thread.currentThread().getContextClassLoader());
    }
}