package ghkwhd.servlet.web.frontController.v3;

import ghkwhd.servlet.web.frontController.ModelView;
import ghkwhd.servlet.web.frontController.MyView;
import ghkwhd.servlet.web.frontController.v3.controller.MemberFormControllerV3;
import ghkwhd.servlet.web.frontController.v3.controller.MemberListControllerV3;
import ghkwhd.servlet.web.frontController.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    // 매핑 정보
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    // 요청 URL에 따른 Controller 호출
    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // URI에 해당하는 Controller 호출
        ControllerV3 controller = controllerMap.get(requestURI);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // request에 담긴 파라미터 정보를 Map으로 복사
        Map<String, String> paramMap = createParamMap(request);
        // Controller 동작 후 ModelView 반환 ( View 페이지 + Model )
        ModelView mv = controller.process(paramMap);

        String viewName = mv.getViewName(); // 반환된 ModelView에 담긴 논리 이름을
        MyView view = viewResolver(viewName); // 실제 물리 이름으로 변환 & 객체 생성

        // view 페이지 이동을 위한 render() 메소드 호출
        view.render(mv.getModel(), request, response);

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
