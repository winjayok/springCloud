import java.io.Serializable;

/**
 * @PackageName: PACKAGE_NAME
 * @ClassName: DemoEntity
 * @Description: DemoEntity
 * @Author: Winjay
 * @Date: 2022-04-10 18:46:11
 */
public class DemoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DemoEntity{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
