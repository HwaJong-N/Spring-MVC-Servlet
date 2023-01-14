package ghkwhd.servlet.web.frontController.v4;

import ghkwhd.servlet.web.frontController.ModelView;
import ghkwhd.servlet.web.frontController.MyView;
import ghkwhd.servlet.web.frontController.v4.controller.MemberFormControllerV4;
import ghkwhd.servlet.web.frontController.v4.controller.MemberListControllerV4;
import ghkwhd.servlet.web.frontController.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    // 매핑 정보
    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    // 요청 URL에 따른 Controller 호출
    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // URI에 해당하는 Controller 호출
        ControllerV4 controller = controllerMap.get(requestURI);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // request에 담긴 파라미터 정보를 Map으로 복사
        Map<String, String> paramMap = createParamMap(request);

        // Controller에서 담을 정보들을 위한 model
        Map<String, Object> model = new HashMap<>();

        // Controller 동작 & view 페이지 이름 반환
        // Controller의 process에서 model에 정보들이 담기게 됨
        String viewName = controller.process(paramMap, model);

        // 실제 물리 이름으로 변환 & 객체 생성
        MyView view = viewResolver(viewName);

        // view 페이지 이동을 위한 render() 메소드 호출
        view.render(model, request, response);

    }

    // 논리 이름을 받아 물리 이름으로 변환하는 메소드
    // MyView에 실제 물리 주소를 담아서 객체를 생성
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
