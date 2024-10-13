<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/frontend/orders" method="post">
    <label>Order Number:</label>
    <input type="text" name="orderNumber"/>
    <label>User ID:</label>
    <input type="text" name="userId"/>
    <label>Billing Address:</label>
    <input type="text" name="billingAddress"/>
    <label>Shipping Address:</label>
    <input type="text" name="shippingAddress"/>
    <button type="submit">Create Order</button>
</form>

</body>
</html>