package ghkwhd.servlet.web.frontController;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
// Model과 view 논리 이름을 가진 클래스
public class ModelView {
    private String viewName;

    // Model을 담기 위한 Map
    private Map<String, Object> model = new HashMap<>();

    public ModelView(String viewName) {
        this.viewName = viewName;
    }
}
