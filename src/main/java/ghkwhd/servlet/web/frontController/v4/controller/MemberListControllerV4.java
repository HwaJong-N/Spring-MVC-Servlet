package ghkwhd.servlet.web.frontController.v4.controller;

import ghkwhd.servlet.domain.Member;
import ghkwhd.servlet.domain.MemberRepository;
import ghkwhd.servlet.web.frontController.ModelView;
import ghkwhd.servlet.web.frontController.v4.ControllerV4;

import java.util.List;
import java.util.Map;

public class MemberListControllerV4 implements ControllerV4 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {

        List<Member> members = memberRepository.findAll();
        model.put("members", members);
        return "members";
    }
}
