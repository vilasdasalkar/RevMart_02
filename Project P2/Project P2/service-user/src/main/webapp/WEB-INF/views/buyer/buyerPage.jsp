<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Buyer Dashboard</title>

    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome CDN for icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #e3f2fd, #f8bbd0);
            font-family: 'Arial', sans-serif;
            color: #777;
        }

        /* Navbar Styling */
        .navbar {
            background-color: #2196f3;
        }

        .navbar-brand {
            color: white !important;
            font-weight: bold;
        }

        .navbar-brand:hover {
            color: #ffeb3b !important;
        }

        .navbar-nav {
            margin-left: auto;
            color: white !important;
        }

        /* Featured Products Title */
        .featured-title {
            text-align: center;
            font-size: 36px;
            font-weight: bold;
            color: #333;
            margin: 40px 0;
            opacity: 0;
            animation: fadeInTitle 1s forwards;
        }

        @keyframes fadeInTitle {
            0% {
                opacity: 0;
                transform: translateY(-20px);
            }
            100% {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* Product Card Styling with Gradient */
        .product-card {
            background: linear-gradient(135deg, #f9fbe7, #e0f7fa); /* Gradient for card */
            border-radius: 15px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin-bottom: 30px;
            transition: transform 0.3s, box-shadow 0.3s;
            height: 680px; /* Set equal height */
            width: 100%;
            opacity: 0;
            animation: fadeInCards 1s ease-in-out forwards; /* Animation for card load */
        }

        .product-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 30px rgba(0, 0, 0, 0.2);
        }
        

        /* Animation for Product Cards */
        @keyframes fadeInCards {
            0% {
                opacity: 0;
                transform: translateY(-50px);
            }
            100% {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* Fix Image Fit */
        .product-image {
            height: 200px;
            width: 100%;
            object-fit: cover; /* Ensure image fits properly */
            border-radius: 15px;
            margin-bottom: 15px;
        }

        /* Card Title, Description, and Price Styling */
        .product-details h5 {
            font-weight: bold;
            color: #e91e63; /* Pink color for title */
            text-align: center;
        }

        .product-details p {
            color: #555;
        }

        .price {
            color: #4caf50; /* Green for price */
            font-size: 24px; /* Increase font size for price */
            font-weight: bold;
            text-align: center; /* Center price */
        }

        /* Action Buttons Styling with Gradient */
        .action-buttons {
            position: absolute;
            bottom: 15px; /* Keep buttons at the bottom of the card */
            width: 90%;
            display: flex;
            justify-content: space-around;
        }

        .btn-wishlist, .btn-cart {
            padding: 10px 20px;
            border-radius: 5px;
            font-size: 14px;
            transition: transform 0.3s;
            width: 45%; /* Make buttons take up equal space */
        }

        .btn-wishlist {
            background: linear-gradient(135deg, #ff8a65, #ff7043); /* Gradient for wishlist */
            color: white;
            border: none;
        }

        .btn-wishlist:hover {
            background: linear-gradient(135deg, #d84315, #ff5722);
            transform: scale(1.05);
        }

        .btn-cart {
            background: linear-gradient(135deg, #66bb6a, #43a047); /* Gradient for cart */
            color: white;
            border: none;
        }

        .btn-cart:hover {
            background: linear-gradient(135deg, #388e3c, #4caf50);
            transform: scale(1.05);
        }

        /* Footer */
        footer {
            text-align: center;
            padding: 20px 0;
            background-color: #2196f3;
            color: white;
            margin-top: 20px;
        }
    </style>
</head>
<body>

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark">
        <a class="navbar-brand" href="/buyerPage"> <i class="fas fa-tachometer-alt"></i> Buyer Dashboard </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item"><a class="nav-link" href="/buyerAllProduct"> <i class="fas fa-box-open"></i> All Products </a></li>
                <li class="nav-item"><a class="nav-link" href="/frontend/orders"> <i class="fas fa-shopping-bag"></i> View Orders </a></li>
                <li class="nav-item"><a class="nav-link" href="/cartPage"> <i class="fas fa-shopping-cart"></i> Cart </a></li>
                <li class="nav-item"><a class="nav-link" href="/wishlist"> <i class="fas fa-heart"></i> Wishlist </a></li>
                <li class="nav-item"><a class="nav-link" href="/logout"> <i class="fas fa-sign-out-alt"></i> Logout </a></li>
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

    <!-- Featured Products Title -->
    <h2 class="featured-title">Featured Products</h2>

    <div class="container mt-4">
        <div class="row">
            <c:forEach var="product" items="${products}">
                <div class="col-md-4 mb-4">
                    <div class="product-card">
                        <img class="product-image" src="${product.imageurl}" alt="${product.name}">
                        <div class="product-details">
                            <h5>${product.name}</h5>
                            <p><strong>ID:</strong> ${product.id}</p>
                            <p><strong>Description:</strong> ${product.description}</p>
                            <p><strong>Category:</strong> <c:out value="${product.category.name}" default="Unknown" /></p>
                            <p><strong>SKU Code:</strong> ${product.skuCode}</p>
                            <p class="price"><span>&#8377</span> ${product.price}</p>
                        </div>
                        <div class="action-buttons">
                            <a href="javascript:void(0);" class="btn-wishlist" onclick="addToWishlist('${product.id}', '${product.name}', '${product.price}', '${product.imageurl}')">Add to Wishlist</a>
                            <button class="btn-cart" onclick="addToCart(${product.id}, ${product.price}, '${product.name}', '${product.imageurl}')">Add to Cart</button>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <!-- Footer -->
    <footer>
        <p>&copy; 2024 Revature. All rights reserved.</p>
    </footer>

    <!-- jQuery and AJAX to Fetch Email -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        let userId = null;  // Store the user ID dynamically

        $(document).ready(function() {
            $.ajax({
                url: 'http://localhost:8080/api/users',  // API URL
                type: 'GET',
                success: function(users) {
                    if (users.length > 0) {
                        $('#profile-link').text(users[0].email);
                        userId = users[0].id; // Store the user ID
                    } else {
                        $('#profile-link').text('No Profile');
                    }
                },
                error: function(error) {
                    console.log('Error fetching user email:', error);
                    $('#profile-link').text('Error');
                }
            });
        });

        function addToWishlist(productId, productName, productPrice, productImage) {
            let wishlist = JSON.parse(localStorage.getItem('wishlist')) || [];
            const product = { productId, productName, productPrice, productImage };
            
            if (!wishlist.some(item => item.productId === productId)) {
                wishlist.push(product);
                localStorage.setItem('wishlist', JSON.stringify(wishlist));
                alert('Product added to wishlist!');
            } else {
                alert('Product is already in your wishlist.');
            }
        }

        function addToCart(productId, productPrice, productName, productImage) {
            if (userId === null) {
                alert("User not logged in.");
                return;
            }

            const quantity = 1; // Default quantity

            const cartData = {
                "userId": userId,
                "cartItems": [
                    {
                        "productId": productId,
                        "productName": productName,
                        "image": productImage,
                        "quantity": quantity,
                        "price": productPrice
                    }
                ]
            };

            $.ajax({
                url: 'http://localhost:8083/cart/add',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(cartData),
                success: function(response) {
                    alert('Product added to cart successfully!');
                },
                error: function(error) {
                    console.log('Error adding product to cart:', error);
                    alert('Failed to add product to cart.');
                }
            });
        }
    </script>

    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
