package ghkwhd.servlet.web.frontController.v5;

import ghkwhd.servlet.web.frontController.ModelView;
import ghkwhd.servlet.web.frontController.MyView;
import ghkwhd.servlet.web.frontController.v3.controller.MemberFormControllerV3;
import ghkwhd.servlet.web.frontController.v3.controller.MemberListControllerV3;
import ghkwhd.servlet.web.frontController.v3.controller.MemberSaveControllerV3;
import ghkwhd.servlet.web.frontController.v4.controller.MemberFormControllerV4;
import ghkwhd.servlet.web.frontController.v4.controller.MemberListControllerV4;
import ghkwhd.servlet.web.frontController.v4.controller.MemberSaveControllerV4;
import ghkwhd.servlet.web.frontController.v5.adapter.ControllerV3HandlerAdapter;
import ghkwhd.servlet.web.frontController.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    // 핸들러 매핑 정보
    // Controller가 아닌 Object를 Map의 value로
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    
    // 핸들러 어댑터 목록
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    // 핸들러 매핑 정보 초기화
    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    // 핸들러 어댑터 목록 초기화
    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 핸들러 찾기
        Object handler = getHandler(request);
        if(handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 핸들러 어댑터 찾기 (찾아진 Controller가 처리할 수 있는 Controller인지 확인)
        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        // 어댑터의 handle을 호출
        // 어댑터의 handle은 Controller를 호출해 process()를 수행하도록 함
        // 수행 결과로 ModelView가 반환 (V3)
        ModelView mv = adapter.handle(request, response, handler);

        // view 페이지 논리 이름 -> 물리 이름
        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        // modelView의 model(Map)을 전달하면서 view 페이지 렌더링
        view.render(mv.getModel(), request, response);
    }

    // 요청 URI로 어떤 Controller인지 조회 및 반환
    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        // 핸들러 어댑터 목록을 반복하면서
        // 처리할 수 있는 Controller이면 핸들러 어댑터 반환
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if(adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler = " + handler);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

}
