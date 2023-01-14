package ghkwhd.servlet.web.frontController.v5;

import ghkwhd.servlet.web.frontController.ModelView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MyHandlerAdapter {

    //어댑터가 전달된 컨트롤러를 처리할 수 있는지 판단하는 메소드
    boolean supports(Object handler);

    // Controller를 호출하는 메소드
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;


}
