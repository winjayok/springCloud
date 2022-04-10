import com.example.demo.ServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @PackageName: PACKAGE_NAME
 * @ClassName: DemoTest
 * @Description: DemoTest
 * @Author: Winjay
 * @Date: 2022-04-10 14:57:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServerApplication.class})
public class DemoTest{
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    public void set(){
        DemoEntity demoEntity = new DemoEntity();
        demoEntity.setId("23");
        demoEntity.setName("你好");
        redisTemplate.opsForValue().set("myKey1",demoEntity);
        redisTemplate.opsForValue().set("myKey2","sa");
        System.out.println(redisTemplate.opsForValue().get("myKey1").toString());
       // Set<String> keys = redisTemplate.keys("myKe*");
     //   System.out.println(redisTemplate.delete(keys));
    }
}
