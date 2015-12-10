<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%--
  Created by IntelliJ IDEA.
  User: like
  Date: 11/19/15
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>

  <%--<%--%>
    <%--Map map = new HashMap<>();--%>
    <%--map.put("",);--%>
  <%--%>--%>

    <script type="text/javascript" src="${pageContext.request.contextPath}/project/tracking_statistics/report/aa.js"></script>

</head>
<body>
    <input type="text" id="showText"/>

    <svg width="1024" height="768">
      <defs>
        <marker id="circleMarkerID">
          <circle id="gc1" r="4" cx="10" cy="10" style="stroke: black;fill: white;"  />
        </marker>
      </defs>


      <line id="gl1" x1="10" y1="10" x2="45" y2="35" stroke-width="0.5" stroke="black" />

      <path d="M 10 10 L 10 60 L 45 60 L 45 10 M 10 10 " style="fill: black;" marker-start = url(#circleMarkerID) />



      <%--<c:forEach items="${}" var="">--%>
        <%--<use xlink:href="#lineBasic" />--%>
      <%--</c:forEach>--%>

    </svg>
</body>
</html>
