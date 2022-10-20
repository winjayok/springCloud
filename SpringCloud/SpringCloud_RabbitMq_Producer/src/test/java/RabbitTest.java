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

    private static final String DIRECT_ROUTING_KEY = "DIRECT_ROUTING_KEY";

    private static final String DIRECT_EXCHANGE = "DIRECT_EXCHANGE";

    @Test
    public void directSend() {
        try {
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            MessageVo messageVo = new MessageVo();
            messageVo.setBody("这是一个直连消息队列");
            messageVo.setValue(createTime);
            rabbitTemplate.convertAndSend(DIRECT_EXCHANGE,DIRECT_ROUTING_KEY,messageVo);
            System.out.println("消息发送成功");
        }catch (Exception e){
            System.out.println("消息发送失败");
        }
    }

    @Test
    public void topicSend() {
        try {
            MessageVo messageVo = new MessageVo();
            messageVo.setBody("这是一个短信类主题消息队列");
            messageVo.setValue( LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            rabbitTemplate.convertAndSend("TOPIC_EXCHANGE","topic.sms",messageVo);
            MessageVo messageVo1 = new MessageVo();
            messageVo1.setBody("这是一个邮件类主题消息队列");
            messageVo1.setValue( LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            rabbitTemplate.convertAndSend("TOPIC_EXCHANGE","topic.mail.test",messageVo1);
        }catch (Exception e){
            System.out.println("消息发送失败");
        }
        System.out.println("消息发送成功");
    }

    @Test
    public void fanoutSend() {
        try {
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            MessageVo messageVo = new MessageVo();
            messageVo.setBody("这是一个广播消息队列");
            messageVo.setValue(createTime);
            rabbitTemplate.convertAndSend("FANOUT_EXCHANGE",null,messageVo);
        }catch (Exception e){
            System.out.println("消息发送失败");
        }
        System.out.println("消息发送成功");
    }
}
