package com.wj.module.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis 连接工具类
 * @PackageName: com.example.utils
 * @ClassName: RedisUtil
 * @Description: RedisUtil
 * @Author: Winjay
 * @Date: 2022-04-10 15:06:17
 */

@Component
public class RedisUtil {

    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    private static RedisTemplate redisTemplate;

    private static String errorGetKey="get key:{} error";

    private static String errorDelKey="del key:{} error";

    private static String errorSetKeyValue="set key:{} value:{} error";

    private static String errorExSetKeyValue="setex key:{} value:{} error";

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }


    public static RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }


    /**
     * 设置key的有效期，单位是秒
     *
     * @param key
     * @param exTime
     * @return
     */
    public static Long expire(String key, int exTime) {
        Long result = null;
        try {
            redisTemplate.expire(key, exTime, TimeUnit.SECONDS);
            result = 1L;
        } catch (Exception e) {
            logger.error("expire key:{} error", key, e);
        }
        return result;
    }

    /**
     * 设置最大范围
     * exTime的单位是秒
     * @param key
     * @param value
     * @param exTime
     * @return
     */
    public static String setEx(String key, String value, int exTime) {
        String result = null;
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, exTime, TimeUnit.SECONDS);
            result = "1";
        } catch (Exception e) {
            logger.error(errorExSetKeyValue, key, value, e);
            return result;
        }
        return result;
    }

    /**
     * 设置值最大获取时间
     *
     * exTime的单位是秒
     * @param key
     * @param value
     * @param exTime
     * @return
     */
    public static String setExByte(byte[] key, byte[] value, int exTime) {
        String result = null;
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, exTime, TimeUnit.SECONDS);
            result = "1";
        } catch (Exception e) {
            logger.error(errorExSetKeyValue, key, value, e);
        }
        return result;
    }

    /**
     * 设置值最大值
     * @param key
     * @param value
     * @param exTime
     * @return
     */
    public static String setExByte(String key, byte[] value, int exTime) {
        String result = null;
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, exTime, TimeUnit.SECONDS);
            result = "1";
        } catch (Exception e) {
            logger.error(errorExSetKeyValue, key, value, e);
        }
        return result;
    }

    /**
     * 根据key插入数据
     * @param key
     * @param value
     * @return
     */
    public static String setString(String key, String value) {
        String result = null;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = "1";
        } catch (Exception e) {
            logger.error(errorSetKeyValue, key, value, e);
        }
        return result;
    }

    /**
     * 根据key插入数据，并定义有效期
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public static String setString(String key, String value, int timeout) {
        String result = null;

        try {
            if (timeout != -1) {
                redisTemplate.opsForValue().set(key, value);
                redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
            result = "1";
        } catch (Exception e) {
            logger.error(errorSetKeyValue, key, value, e);
            return result;
        }
        return result;
    }

    /**
     * 根据key设置值和有效时间
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public static String setObjectByStringKey(String key, Object value, int timeout) {
        String result = null;
        try {
            if (timeout != -1) {
                redisTemplate.opsForValue().set(key, value);
                redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
            result = "1";
        } catch (Exception e) {
            logger.error(errorSetKeyValue, key, value, e);
        }
        return result;
    }

    /**
     * 以byte插入数组数据
     * @param key
     * @param value
     * @return
     */
    public static String setByte(byte[] key, byte[] value) {
        String result = null;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = "1";
        } catch (Exception e) {
            logger.error(errorSetKeyValue, key, value, e);
        }
        return result;
    }

    /**
     * 以key插入数组数据
     * @param key
     * @param value
     * @return
     */
    public static String setByte(String key, byte[] value) {
        String result = null;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = "1";
        } catch (Exception e) {
            logger.error(errorSetKeyValue, key, value, e);
        }
        return result;
    }

    /**
     * 根据key以字符串形式读取数据
     * @param key
     * @return
     */
    public static String getString(String key) {
        String result = null;
        try {
            Object obj = redisTemplate.opsForValue().get(key);
            if (obj != null) {
                result=obj.toString();
            }
        } catch (Exception e) {
            logger.error(errorGetKey, key, e);
        }
        return result;
    }

    /**
     * 根据key以object读取数据
     * @param key
     * @return
     */
    public static Object getObjectByStringKey(String key) {
        Object result = null;
        try {
            Object obj = redisTemplate.opsForValue().get(key);
            if (obj != null) {
                result=obj;
            }
        } catch (Exception e) {
            logger.error(errorGetKey, key, e);
        }
        return result;
    }

    /**
     * 获取key的有效时间
     *
     * @param key 需要获取数据的key
     * @return 返回key对应的有效时间
     * @throws Exception
     */
    public static Long getExpire(String key){
        Long expire=null;
        try {
            expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error(errorGetKey, key, e);
        }
        return expire;
    }

    /**
     * 保存对象到Redis 对象不过期
     *
     * @param key    待缓存的key
     * @param object 待缓存的对象
     * @return 返回是否缓存成功
     */
//    public static boolean setObject(String key, Object object) {
//        return setObject(key, object, -1);
//    }

    /**
     * 保存对象到Redis 并设置超时时间
     *
     * @param key     缓存key
     * @return 返回是否缓存成功
     * @throws Exception 异常上抛
     */
//    public static boolean setObject(String key, Object object, int timeout) {
//        String value = SerializeUtil.serialize(object);
//        boolean result = false;
//        try {
//            // 为-1时不设置超时时间
//            if (timeout != -1) {
//                setString(key, value, timeout);
//            } else {
//                setString(key, value);
//            }
//        } catch (Exception e) {
//            logger.error(errorGetKey, key, e);
//        }
//        return result;
//
//    }

//    public static Object getObject(String key) {
//        String result = null;
//        Object object = null;
//        try {
//            Object obj = redisTemplate.opsForValue().get(key);
//            if (obj != null) {
//                result = obj.toString();
//                if (StringUtils.isNotBlank(result)) {
//                    object = SerializeUtil.deserialize(result);
//                }
//                return object;
//            }
//        } catch (Exception e) {
//            logger.error(errorGetKey, key, e);
//        }
//        return result;
//
//    }

    public static byte[] getByte(byte[] key) {
        byte[] result = null;
        try {
            Object obj = redisTemplate.opsForValue().get(key);
            if (obj instanceof byte[] || obj instanceof Byte[]) {
                result = (byte[]) obj;
            } else if (obj != null) {
                result = obj.toString().getBytes();
            }
        } catch (Exception e) {
            logger.error(errorGetKey, key, e);
        }
        return result;
    }

    public static byte[] getByte(String key) {
        byte[] result = null;
        try {
            Object obj = redisTemplate.opsForValue().get(key);
            if (obj instanceof byte[] || obj instanceof Byte[]) {
                result = (byte[]) obj;
            } else if (obj != null) {
                result = obj.toString().getBytes();
            }
        } catch (Exception e) {
            logger.error(errorGetKey, key, e);
        }
        return result;
    }

    /**
     * redis批量获取以某字符串前缀的所有key
     *
     * @param prefix 字符串前缀
     */
    public static List<String> getByPrefix(String prefix) {
        List<String> list = new ArrayList<>();
        try {
            Set<String> set = redisTemplate.keys(prefix + "*");
            if (!set.isEmpty()) {
                for (String key : set) {
                    Object obj = redisTemplate.opsForValue().get(key);
                    if (obj != null) {
                        list.add(obj.toString());
                    }
                }
            }
        } catch (Exception e) {
            logger.error(errorGetKey, prefix, e);
        }
        return list;
    }

    public static Set<byte[]> keyByte(byte[] key) {
        Set<byte[]> result = null;
        try {
            result = redisTemplate.keys(key);
        } catch (Exception e) {
            logger.error(errorGetKey, key, e);
        }
        return result;
    }

    public static Set<String> keyByte(String key) {
        Set<String> result = null;
        try {
            result = redisTemplate.keys(key);
        } catch (Exception e) {
            logger.error(errorGetKey, key, e);
        }
        return result;
    }

    public static Long dbSize() {
        Long result = null;
        try {
            redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.dbSize();
                }
            });
            return 1L;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void flushDb() {
        try {
            redisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.flushDb();
                    return "ok";
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Long expireByte(byte[] key, int exprie) {
        Long result = null;
        try {
            redisTemplate.expire(key, exprie, TimeUnit.SECONDS);
            result = 1L;
        } catch (Exception e) {
            logger.error(errorGetKey, key, e);
        }
        return result;
    }

    public static Long expireByte(String key, int exprie) {
        Long result = null;
        try {
            redisTemplate.expire(key, exprie, TimeUnit.SECONDS);
            result = 1L;
        } catch (Exception e) {
            logger.error(errorGetKey, key, e);
        }
        return result;
    }

    public static Long del(String key) {
        Long result = null;
        try {
            redisTemplate.delete(key);
            result = 1L;
        } catch (Exception e) {
            logger.error(errorDelKey, key, e);
        }
        return result;
    }

    public static Long delByte(String key) {
        return del(key);
    }

    /**
     * redis批量删除以某字符串前缀的所有key
     *
     * @param prefix 字符串前缀
     */
    public static boolean delByPrefix(String prefix) {
        Boolean result = true;
        try {
            Set<String> set = redisTemplate.keys(prefix + "*");
            if (!set.isEmpty()) {
                for (String key : set) {
                    redisTemplate.delete(key);
                }
            }
        } catch (Exception e) {
            logger.error(errorDelKey, prefix, e);
        }
        return result;
    }

    /**
     * 获取redis 服务器信息
     *
     * @return
     */
    public static String getRedisInfos() {
        return "";
    }

    /**
     * 获取日志条数
     */
    public static Long getLogsLen() {
        return Long.parseLong("0");
    }

    /**
     * 判断是否存在
     */
    public static boolean redisHasKey(String key) {

        return redisTemplate.hasKey(key);
    }

    /**
     * 获取元素分值
     */
    public static Double zSetScore(String key, Object v) {
        return redisTemplate.opsForZSet().score(key, v);
    }

    /**
     * 获取元素位置
     */
    public static Long zSetRank(String key, Object v) {
        return redisTemplate.opsForZSet().rank(key, v);
    }

    /**
     * 删除集合元素
     */
    public static Long zSetRemove(String key, Object v) {
        return redisTemplate.opsForZSet().remove(key, v);
    }

    /**
     * 获取有序集合大小
     */
    public static Long zSetZCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 添加有序集元素
     */
    public static boolean zSetAdd(String key, Object v, Double s) {
        return redisTemplate.opsForZSet().add(key, v, s);
    }

    /**
     * 集合列表
     */
    public static Set<Object> zSetList(String key) {

        return redisTemplate.opsForZSet().range(key, 0, -1);
    }
}
