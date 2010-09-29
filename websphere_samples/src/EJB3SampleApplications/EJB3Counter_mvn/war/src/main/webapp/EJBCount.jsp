<%@page session="false"%>
<HTML>
<HEAD>
<TITLE>IBM WebSphere EJB3 and JPA1 Counter Sample</TITLE>
<BODY bgcolor="cornsilk">
<H1>EJB 3.0 and JPA 1.0 Counter Sample</H1>
<P>
<B>
This application communicates with the WebSphere Application Server using http requests to increment a stateless EJB 3.0 counter bean which is using a JPA 1.0 entity (ie. keeps a persistent counter in a Derby database table).
</B>
<FORM METHOD=POST ACTION="counter">
</BR>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires",0);
    String msg = (String) request.getAttribute("msg");
    if (msg == null) msg = "";
%>
<B>Click on the Increment button to increment the count</B>
</BR></BR>
<INPUT TYPE=SUBMIT VALUE="Increment">
</FORM>
<H3><%=msg%></H3>
</INSERT>
</BODY>
</HTML>
