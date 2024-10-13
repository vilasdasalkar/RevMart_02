<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>Order Details</h2>
<p>Order Number: ${order.orderNumber}</p>
<p>Status: ${order.status}</p>
<p>User ID: ${order.userId}</p>
<p>Billing Address: ${order.billingAddress}</p>
<p>Shipping Address: ${order.shippingAddress}</p>
<h3>Order Line Items</h3>
<ul>
    <c:forEach items="${order.orderLineItems}" var="item">
        <li>${item.name} - ${item.quantity} x ${item.price}</li>
    </c:forEach>
</ul>

</body>
</html>