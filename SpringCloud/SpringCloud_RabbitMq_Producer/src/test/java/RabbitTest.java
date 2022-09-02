import com.wj.RabbitMqProducerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package: PACKAGE_NAME
 * @ClassName: RabbitTest
 * @Auther: Winjay
 * @Date: 2022/9/2 11:06
 * @ProjectName: SpringCloud
 * @Des:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMqProducerApplication.class)
public class RabbitTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String DIRECT_ROUTING_KEY = "direct_routing_key";

    private static final String DIRECT_EXCHANGE = "direct_exchange";


    @Test
    public void send() {
        try {
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Map<String,Object> map=new HashMap<>();
            map.put("messageId","1");
            map.put("messageData","hello world");
            map.put("createTime",createTime);
          //  Message message = new Message("你过来啊".getBytes(), new MessageProperties());
            rabbitTemplate.convertAndSend(DIRECT_EXCHANGE,DIRECT_ROUTING_KEY,map);
        }catch (Exception e){
            System.out.println("消息发送失败");
        }
        System.out.println("消息发送成功");
    }
}
