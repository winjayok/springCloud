import java.io.Serializable;

/**
 * @PackageName: com.example.consumer.vo
 * @ClassName: MessageVo
 * @Description: MessageVo
 * @Author: Winjay
 * @Date: 2022-01-09 15:31:43
 */
public class MessageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String Body;

    private String value;

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
