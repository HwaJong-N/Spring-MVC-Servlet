package ghkwhd.servlet.web.frontController.v3.controller;

import ghkwhd.servlet.domain.Member;
import ghkwhd.servlet.domain.MemberRepository;
import ghkwhd.servlet.web.frontController.ModelView;
import ghkwhd.servlet.web.frontController.v3.ControllerV3;

import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {

        List<Member> members = memberRepository.findAll();

        // ModelView는 view 페이지 논리 이름과 Model을 가짐
        ModelView mv = new ModelView("members");
        // Model 추가
        mv.getModel().put("members", members);
        return mv;
    }
}
