<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Title</title>
</head>
<%
    MemberRepository memberRepository = MemberRepository.getInstance();
    List<Member> members = memberRepository.findAll();
%>
<body>
<a href="/index.html">메인</a>
<table>
    <thead>
    <tr>
        <th>#</th>
        <th>이름</th>
        <th>나이</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Member member : members) {
    %>
    <tr>
        <th><%=member.getId()%></th>
        <th><%=member.getUsername()%></th>
        <th><%=member.getAge()%></th>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
</body>
</html>
