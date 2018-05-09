package com.zengrui.utils;

/**
 * Created by Zeng Rui on 2018/5/3.
 */
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * 类描述:
 *
 * @author fengyong
 * @version 1.0
 * @since 1.0
 * Created by fengyong on 16/10/29 下午4:07.
 */
public class SimpleJedisCluster implements FactoryBean<JedisCluster>, InitializingBean {

    private Resource addressConfig;
    private String addressKeyPrefix ;
    private JedisCluster jedisCluster;
    private Integer timeout;
    private Integer maxRedirections;
    private GenericObjectPoolConfig poolConfig;
    private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");

    public JedisCluster getObject() throws Exception {
        return jedisCluster;
    }

    public Class<? extends JedisCluster> getObjectType() {
        return ((this.jedisCluster != jedisCluster) ? this.jedisCluster.getClass() : JedisCluster.class);
    }

    public boolean isSingleton() {
        return true;
    }

    private Set<HostAndPort> parseHostAndPort() {
        Set<HostAndPort> haps = new HashSet<HostAndPort>();
        try {
            Properties prop = new Properties();
            prop.load(this.addressConfig.getInputStream());
            for (Object key : prop.keySet()) {

                if (!((String) key).startsWith(addressKeyPrefix)) {
                    continue;
                }

                String val = (String) prop.get(key);

                boolean isIpPort = p.matcher(val).matches();

                if (!isIpPort) {
                    throw new IllegalArgumentException("ip 或 port 不合法");
                }
                String[] ipAndPort = val.split(":");

                HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
                haps.add(hap);
            }

            return haps;
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return haps;
    }

    public void afterPropertiesSet() throws Exception {
        Set<HostAndPort> haps = this.parseHostAndPort();
        jedisCluster = new JedisCluster(haps, timeout, maxRedirections,poolConfig);
    }
    public void setAddressConfig(Resource addressConfig) {
        this.addressConfig = addressConfig;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setMaxRedirections(int maxRedirections) {
        this.maxRedirections = maxRedirections;
    }

    public void setAddressKeyPrefix(String addressKeyPrefix) {
        this.addressKeyPrefix = addressKeyPrefix;
    }

    public void setGenericObjectPoolConfig(GenericObjectPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }
}