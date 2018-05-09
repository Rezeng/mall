import org.junit.Test;
import redis.clients.jedis.*;

import javax.naming.Name;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Zeng Rui on 2018/3/9.
 */
public class utilsTest {
    @Test
    public void cluster() throws IOException {
        JedisPoolConfig config = new JedisPoolConfig();
        // 最大连接数
        config.setMaxTotal(30);
        // 最大连接空闲数
        config.setMaxIdle(2);

        //集群结点
        Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
        jedisClusterNode.add(new HostAndPort("118.24.17.130", 7001));
        jedisClusterNode.add(new HostAndPort("118.24.17.130", 7002));
        jedisClusterNode.add(new HostAndPort("118.24.17.130", 7003));
        jedisClusterNode.add(new HostAndPort("118.24.17.130", 7004));
        jedisClusterNode.add(new HostAndPort("118.24.17.130", 7005));
        jedisClusterNode.add(new HostAndPort("118.24.17.130", 7006));
        JedisCluster jc = new JedisCluster(jedisClusterNode, config);

        jc.set("name", "zhangsan");
        String value = jc.get("name");
        System.out.println(value);

    }



}

