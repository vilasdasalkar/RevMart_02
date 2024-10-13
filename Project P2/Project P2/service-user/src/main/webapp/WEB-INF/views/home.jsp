<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home Page</title>

    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <style>
        /* Custom CSS for navbar */
        .navbar {
            background: linear-gradient(to right, #3498db, #8e44ad);
            padding: 1rem;
            transition: background-color 0.3s ease;
        }
        .navbar-brand {
            font-size: 1.8rem;
            color: #ecf0f1 !important;
            font-weight: bold;
            text-transform: uppercase;
            transition: color 0.3s ease;
        }
        .navbar-brand:hover {
            color: #f39c12 !important;
        }
        .nav-link {
            color: #ecf0f1 !important;
            font-size: 1.1rem;
            transition: color 0.3s ease;
        }
        .nav-link:hover {
            color: #f39c12 !important;
        }

        /* Styling for mobile view */
        @media (max-width: 768px) {
            .navbar-collapse {
                justify-content: space-between;
            }
        }
    </style>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark">
        <a class="navbar-brand" href="#">RevMart</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/products">Products</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/registration">Register</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/login">Login</a>
                </li>
            </ul>
        </div>
    </nav>

    <!-- Carousel Section -->
    <div id="carouselExampleIndicators" class="carousel slide mt-4" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="https://www.jdmedia.co.za/images/carousel/Ecommerce-Banner-1920.jpg" class="d-block w-100" alt="Image 1">
            </div>
            <div class="carousel-item">
                <img src="https://mindstacktechnologies.com/wordpress/wp-content/uploads/2018/01/ecommerce-banner.jpg" class="d-block w-100" alt="Image 2">
            </div>
            <div class="carousel-item">
                <img src="https://previews.123rf.com/images/arrow/arrow1508/arrow150800011/43834601-online-shopping-e-commerce-flat-design-concept-banner-background.jpg" class="d-block w-100" alt="Image 3">
            </div>
        </div>
        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>

    <!-- Product Cards Section -->
    <div class="container mt-5">
        <h1 class="text-center">Welcome to RevMart!</h1>
        <p class="text-center">Browse through a variety of products and find the best deals.</p>

        <div class="row">
            <!-- Dynamically render product cards using JSTL -->
            <c:forEach var="product" items="${products}">
                <div class="col-lg-3 col-md-6 product-card">
                    <div class="card h-100">
                        <img src="${product.imageUrl}" class="card-img-top" alt="${product.name}">
                        <div class="card-body text-center">
                            <h5 class="card-title">${product.name}</h5>
                            <p class="card-text">${product.price}</p>
                            <a href="/product/${product.id}" class="btn btn-primary">Buy Now</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <!-- Bootstrap JS, Popper.js, and jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
