<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All Products</title>

    <!-- Include Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome for icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">

    <!-- Custom CSS for new styling -->
    <style>
        body {
            background: linear-gradient(to right, #74ebd5, #acb6e5); /* Added linear gradient background */
            font-family: Arial, sans-serif;
        }
        .product-card {
            background-color: #fff;
            border-radius: 25px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin-bottom: 30px;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            text-align: center;
            width: 320px; /* Increased width of the product card */
            opacity: 0;
            animation: fadeIn 0.6s forwards;
        }
        /* Hover effect on product card */
        .product-card:hover {
            transform: translateY(-10px) scale(1.05);
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.2);
        }
        /* Product image styling */
        .product-image {
            width: 100%;
            height: 180px;
            object-fit: contain;
            border-radius: 10px;
        }
        .product-details {
            padding: 15px 0;
        }
        .product-details h5 {
            font-weight: bold;
            color: #333;
        }
        .product-details p {
            margin: 0;
            color: #777;
            font-size: 14px;
        }
        .price {
            color: #ff4d4f;
            font-size: 20px;
            font-weight: bold;
            margin-top: 10px;
        }
        .action-buttons {
            display: flex;
            gap: 15px;
            justify-content: space-between;
        }
        .action-buttons a {
            flex: 1;
        }
        .home-button {
            position: absolute;
            top: 20px;
            left: 20px;
            display: inline-block;
            background-color: #39a4b8;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            border-radius: 25px;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }
        .home-button:hover {
            color: #0056b3;
        }
        .home-button i {
            margin-right: 8px;
        }
        .container h2 {
            color: #555;
            margin-bottom: 40px;
            font-size: 28px;
            font-weight: bold;
            text-align: center;
        }
        /* Entrance animation */
        @keyframes fadeIn {
            0% {
                opacity: 0;
                transform: scale(0.9);
            }
            100% {
                opacity: 1;
                transform: scale(1);
            }
        }
    </style>
</head>
<body>

<!-- Home Button with arrow icon, positioned at the top left -->
<a href="/sellerPage" class="home-button">
    <i class="fas fa-arrow-left"></i> Back to Home
</a>

<div class="container">
    <h2 class="text-center"><br>Explore All Products</h2>

    <!-- Loop through products and display each product as a card -->
    <div class="row justify-content-center">
        <c:forEach var="product" items="${products}">
            <div class="col-md-4">
                <div class="product-card">
                    <!-- Product Image -->
                    <img class="product-image" src="${product.imageurl}" alt="${product.name}">

                    <!-- Product Details -->
                    <div class="product-details">
                        <h5>${product.name}</h5>
                        <p><strong>ID:</strong> ${product.id}</p>
                        <p><strong>Description:</strong> ${product.description}</p>
                        <p><strong>Category:</strong> <c:out value="${product.category.name}" default="Unknown" /></p>
                        <p><strong>SKU Code:</strong> ${product.skuCode}</p>
                        <p class="price"><span>&#8377</span>${product.price}</p>
                    </div>

                    <!-- Action Buttons -->
                    <div class="action-buttons">
                        <a href="/editProduct/${product.id}" class="btn btn-outline-primary btn-sm">Edit</a>
                        <a href="/deleteProduct/${product.id}" class="btn btn-outline-danger btn-sm">Delete</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- Include Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
