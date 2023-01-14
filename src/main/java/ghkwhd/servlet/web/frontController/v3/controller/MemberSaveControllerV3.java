package ghkwhd.servlet.web.frontController.v3.controller;

import ghkwhd.servlet.domain.Member;
import ghkwhd.servlet.domain.MemberRepository;
import ghkwhd.servlet.web.frontController.ModelView;
import ghkwhd.servlet.web.frontController.MyView;
import ghkwhd.servlet.web.frontController.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    public ModelView process(Map<String, String> paramMap) {

        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        // ModelView는 view 페이지 논리 이름과 Model을 가짐
        ModelView mv = new ModelView("save-result");
        // Model 추가
        mv.getModel().put("member", member);
        return mv;
    }
}
