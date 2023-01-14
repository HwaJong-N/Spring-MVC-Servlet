<%@ page import="ghkwhd.servlet.domain.Member" %>
<%@ page import="ghkwhd.servlet.domain.MemberRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  MemberRepository memberRespository = MemberRepository.getInstance();

  // jsp도 servlet으로 변환되기 때문에 request, response 사용 가능
  // form에서 데이터가 오면 getParameter를 통해 꺼낸다
  String username = request.getParameter("username");
  int age = Integer.parseInt(request.getParameter("age"));

  // Member 객체로 만들어서 저장(save)
  Member member = new Member(username, age);
  memberRespository.save(member);
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
  <li>id=<%=member.getId()%></li>
  <li>username=<%=member.getUsername()%></li>
  <li>age=<%=member.getAge()%></li>
</ul>
</body>
</html>
