package team.dcweb.aqcache.anno.support;

import team.dcweb.aqcache.anno.SerialPolicy;
import team.dcweb.aqcache.support.JavaValueDecoder;
import team.dcweb.aqcache.support.SpringJavaValueDecoder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.function.Function;

/**
 * Create On 2021/7/19
 *
 * @author hongkun
 * @version 1.0.0
 */
public class DefaultSpringEncoderParser extends DefaultEncoderParser implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    static String parseBeanName(String str) {
        final String beanPrefix = "bean:";
        int len = beanPrefix.length();
        if (str != null && str.startsWith(beanPrefix) && str.length() > len) {
            return str.substring(len);
        } else {
            return null;
        }
    }

    @Override
    public Function<Object, byte[]> parseEncoder(String valueEncoder) {
        String beanName = parseBeanName(valueEncoder);
        if (beanName == null) {
            return super.parseEncoder(valueEncoder);
        } else {
            Object bean = applicationContext.getBean(beanName);
            if (bean instanceof Function) {
                return (Function<Object, byte[]>) bean;
            } else {
                return ((SerialPolicy) bean).encoder();
            }
        }
    }

    @Override
    public Function<byte[], Object> parseDecoder(String valueDecoder) {
        String beanName = parseBeanName(valueDecoder);
        if (beanName == null) {
            return super.parseDecoder(valueDecoder);
        } else {
            Object bean = applicationContext.getBean(beanName);
            if (bean instanceof Function) {
                return (Function<byte[], Object>) bean;
            } else {
                return ((SerialPolicy) bean).decoder();
            }
        }
    }

    @Override
    JavaValueDecoder javaValueDecoder(boolean useIdentityNumber) {
        return new SpringJavaValueDecoder(useIdentityNumber);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}