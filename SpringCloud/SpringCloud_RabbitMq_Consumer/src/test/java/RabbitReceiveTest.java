import com.google.gson.Gson;
import com.wj.consumer.RabbitMqConsumerApplication;
import com.wj.consumer.vo.MessageVo;
import org.junit.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.record.RecordMetaData;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Package: PACKAGE_NAME
 * @ClassName: RabbitTest
 * @Auther: Winjay
 * @Date: 2022/9/2 11:06
 * @ProjectName: SpringCloud
 * @Des:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMqConsumerApplication.class)
public class RabbitReceiveTest {



    @Test
    public void tet(){

    }
}
