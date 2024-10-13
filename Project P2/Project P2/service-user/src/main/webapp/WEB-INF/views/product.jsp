<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
    <title>Product List</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Custom CSS -->
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/style.css'/>">
    <style>
        /* Body Styling with Gradient */
body {
    background: linear-gradient(135deg, #f0f4f8, #d9e7ff);
    font-family: 'Roboto', sans-serif;
    color: #333;
    overflow-x: hidden;
}

/* Header Styling */
h2 {
    color: #2c3e50;
    font-weight: 700;
    margin-bottom: 40px;
    text-align: center;
    font-size: 2.5rem;
    text-transform: uppercase;
}

/* Product Card Styling */
.card {
    border: none;
    transition: transform 0.4s ease, box-shadow 0.4s ease;
    border-radius: 15px;
    width: 100%;
    max-width: 350px;
    opacity: 0;
    animation: fadeIn 0.7s forwards; /* Fade-in animation */
    background: #ffffff;
    margin: 0 20px; /* Increased gap between cards */
}

/* Image Styling: Ensure it fits properly */
.card-img-top {
    max-height: 200px;
    object-fit: contain; /* Ensures the image maintains its aspect ratio */
    border-top-left-radius: 15px;
    border-top-right-radius: 15px;
    margin-bottom: 15px; /* Added space between the image and text */
}

/* Product Content Styling */
.card-body {
    padding: 20px; /* Added padding for better spacing */
    text-align: center; /* Center align all content */
}

.card-title {
    font-size: 20px;
    font-weight: 700;
    color: #e67e22;
    margin-bottom: 10px; /* Added space below title */
}

.card-text {
    font-size: 16px;
    color: #7f8c8d;
    margin-bottom: 10px; /* Space between description and price */
}

.card-text strong {
    color: #2c3e50;
}

.card-footer {
    background-color: #f9fafb;
    border-top: none;
    border-bottom-left-radius: 15px;
    border-bottom-right-radius: 15px;
    padding: 15px;
    box-shadow: inset 0 -3px 10px rgba(0, 0, 0, 0.05);
}

/* Button Styling */
.btn-info {
    background: linear-gradient(90deg, #26c6da, #80deea);
    border: none;
    font-weight: 600;
    width: 100%; /* Full width button */
    transition: background 0.3s ease, transform 0.3s ease;
}

.btn-info:hover {
    background: linear-gradient(90deg, #00bcd4, #4dd0e1);
    transform: scale(1.05);
}

/* Increase gap between rows */
.row {
    margin-bottom: 40px; /* Larger margin between rows */
}

/* Responsive Design */
@media (max-width: 768px) {
    .col-md-3 {
        flex: 0 0 100%;
        max-width: 100%;
    }

    .card {
        margin: 20px 0; /* Margin for smaller screens */
    }
}

/* Fade-in Animation for cards */
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

/* General Button Style */
.home-button {
    display: inline-flex;
    align-items: center;
    font-size: 16px;
    font-weight: bold;
    text-decoration: none;
    padding: 10px 20px;
    border-radius: 8px;
    background-color: #4CAF50; /* Main button color */
    color: #fff;
    transition: background-color 0.3s ease, box-shadow 0.3s ease, transform 0.2s ease;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

/* Icon styling */
.home-button i {
    margin-right: 8px;
    transition: transform 0.3s ease;
}

/* Hover Effect */
.home-button:hover {
    background-color: #45A049;
    box-shadow: 0 6px 20px rgba(0, 0, 0, 0.25);
    transform: translateY(-2px);
}

/* Icon Animation on Hover */
.home-button:hover i {
    transform: translateX(-4px);
}

/* Focus Effect */
.home-button:focus {
    outline: none;
    box-shadow: 0 0 0 4px rgba(72, 180, 97, 0.4);
}

/* Active Effect */
.home-button:active {
    transform: translateY(2px);
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.15);
}

/* Responsive styling for smaller screens */
@media (max-width: 768px) {
    .home-button {
        font-size: 14px;
        padding: 8px 16px;
    }
}


    </style>
</head>
<body>
<div class="container mt-5">
<a href="/" class="home-button">
    <i class="fas fa-arrow-left"></i> Back to Home
</a>
    <h2 class="text-center mb-4">Product List</h2>

    <!-- Product Tiles (Bootstrap Cards) -->
    <div class="row justify-content-center">
        <c:forEach var="product" items="${products}">
            <div class="col-md-3 mb-4">
                <div class="card h-100 shadow-sm">
                    <img src="${product.imageurl}" class="card-img-top" alt="${product.name}">
                    <div class="card-body">
                        <h5 class="card-title">${product.name}</h5>
                        <p class="card-text">${product.description}</p>
                        <p class="card-text"><strong>Price: &#8377</strong>${product.price}</p>
                        <p class="card-text"><small class="text-muted">Category: ${product.category.name}</small></p>
                    </div>
                    <div class="card-footer">
                        <div class="d-flex justify-content-around">
                            <!-- View Details Button -->
                            <a href="/login" class="btn btn-info position-relative">View Details</a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>

</body>
</html>
