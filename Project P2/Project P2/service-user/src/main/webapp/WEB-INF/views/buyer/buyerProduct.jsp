<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Categories</title>

    <!-- Bootstrap CSS for styling -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Custom CSS for Sidebar, Navbar, and Product List -->
    <style>
    body {
        font-family: 'Arial', sans-serif;
        background: linear-gradient(135deg, #f0f4f8, #e1bee7); /* Linear gradient for the full page background */
        overflow-x: hidden;
    }

    /* Navbar styling */
    .navbar {
        background-color: #007bff;
        position: fixed;
        top: 0;
        width: 100%;
        z-index: 1000;
        padding: 10px 90px;
    }

    .navbar-brand {
        font-size: 1.5rem;
        color: white;
    }

    .navbar-nav .nav-link, .navbar-text {
        color: white !important;
        padding-left: 15px;
    }

    .nav-link:hover {
        color: #ffc107 !important;
    }

    /* Sidebar with gradient background and hover effects */
    #sidebar {
        height: 100vh;
        width: 220px;
        position: fixed;
        top: 60px;
        left: 0;
        background: linear-gradient(135deg, #3e3a62, #512da8); /* Gradient for sidebar */
        padding: 20px;
        color: white;
        overflow-y: auto;
        transition: background 0.3s ease;
    }
    

    #sidebar h3 {
        text-align: center;
        color: #ffc107;
    }

    #sidebar ul {
        list-style-type: none;
        padding-left: 0;
    }

    #sidebar ul li a {
        color: #fff;
        text-decoration: none;
        font-size: 15px;
        padding: 10px;
        display: block;
        transition: background-color 0.3s ease, color 0.3s ease;
    }

    #sidebar ul li a:hover {
        background-color: #ffc107;
        color: #343a40;
        transform: scale(1.05); /* Hover effect for categories */
    }

    /* Main content styling */
    .container {
        margin-left: 240px;
        padding-top: 80px;
        padding: 20px;
    }

    /* Product list grid */
    .product-list {
        display: grid;
        grid-template-columns: repeat(3, 1fr); /* 3 cards per row */
        gap: 20px;
        justify-items: center;
        margin-top: 70px;
    }

    /* Equal card size with advanced layout */
    .product {
        background-color: #fff;
        border: 1px solid #dee2e6;
        border-radius: 10px;
        padding: 15px;
        text-align: center;
        box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
        transition: transform 0.3s ease, box-shadow 0.3s ease;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        width: 100%;
        max-width: 300px; /* Set equal width */
        height: 520px; /* Set equal height */
        position: relative;
    }

    .product:hover {
        transform: scale(1.03);
        box-shadow: 0 12px 24px rgba(0, 0, 0, 0.2);
    }

    /* Ensure equal size for images inside the card */
    .product img {
        height: 150px;
        object-fit: cover; /* Fit image properly */
        margin-bottom: 10px;
    }

    /* Styling for product title, description, and price */
    .product h3 {
        font-size: 16px;
        color: #007bff;
        margin-top: 10px;
    }

    .product p {
        font-size: 12px;
        color: #6c757d;
        display: -webkit-box;
        -webkit-line-clamp: 3; /* Show 3 lines only */
        -webkit-box-orient: vertical;
        text-overflow: ellipsis;
        transition: all 0.3s ease;
    }

    .product .price {
        font-weight: bold;
        color: #28a745;
        margin-top:10px;
        margin-bottom: 15px;
        font-size: 15px;
    }

    /* Flexbox layout for action buttons */
    .action-buttons {
        display: flex;
        justify-content: space-between;
    }

    .btn-wishlist, .btn-cart {
        width: 48%;
        padding: 8px;
        border-radius: 5px;
        transition: all 0.3s ease;
        background: linear-gradient(135deg, #e0f7fa, #80deea); /* Gradient for buttons */
        color: #007bff;
    }

    .btn-wishlist:hover, .btn-cart:hover {
        transform: scale(1.05);
        background: linear-gradient(135deg, #00acc1, #00838f); /* Hover gradient */
        color: #fff;
    }

    </style>

    <!-- jQuery for AJAX -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>

    <!-- Fixed Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark">
        <a class="navbar-brand" href="buyerPage">Shop</a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto"></ul>
            <!-- Right side: Wishlist, Cart, User Name, Logout -->
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/wishlist"><i class="fas fa-heart"></i> Wishlist</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/cartPage"><i class="fas fa-shopping-cart"></i> Cart</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#"><i class="fas fa-user"></i> Welcome, <span id="userName">User</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/logout"><i class="fas fa-sign-out-alt"></i> Logout</a>
                </li>
            </ul>
        </div>
    </nav>

    <!-- Sidebar with dynamically fetched categories -->
    <div id="sidebar">
        <h3>Categories</h3>
        <ul id="categoryList">
            <li><a href="javascript:void(0)" id="allProductsLink">All Products</a></li>
            <!-- Categories will be dynamically populated here -->
        </ul>
    </div>

    <!-- Main Section where products will be displayed -->
    <div class="container">
        <div id="productList" class="product-list text-center"></div>
    </div>

    <script type="text/javascript">
        let userId = null;

        $(document).ready(function() {
            // Fetch user ID and name
            $.ajax({
                url: 'http://localhost:8080/api/users',  // API URL to fetch user data
                type: 'GET',
                success: function(users) {
                    if (users.length > 0) {
                        userId = users[0].id;
                        $('#userName').text(users[0].name);
                    } else {
                        alert('No logged-in user found.');
                    }
                },
                error: function(error) {
                    console.log('Error fetching user data:', error);
                }
            });

            // Function to display products
            function displayProducts(products) {
                $('#productList').empty(); // Clear the current product list
                $.each(products, function(index, product) {
                    $('#productList').append(
                        '<div class="product">' +
                            '<img src="' + product.imageurl + '" alt="' + product.name + '" class="img-fluid"/>' +
                            '<h3>' + product.name + '</h3>' +
                            '<p>' + product.description + '</p>' +
                            '<p class="price">Price: Rs. &#8377;' + product.price + '</p>' +
                            '<div class="action-buttons">' +
                                '<button class="btn-cart" onclick="addToCart(' + product.id + ', \'' + product.name + '\', \'' + product.imageurl + '\', ' + product.price + ')">Add to Cart</button>' +
                                '<button class="btn-wishlist" onclick="addToWishlist(' + product.id + ')">Add to Wishlist</button>' +
                            '</div>' +
                        '</div>'
                    );
                });
            }

            // Fetch and display all products on page load
            $.ajax({
                url: 'http://localhost:8082/products',
                type: 'GET',
                success: function(products) {
                    displayProducts(products);
                },
                error: function() {
                    alert('Failed to fetch products.');
                }
            });

            // Fetch categories dynamically from the backend
            $.ajax({
                url: 'http://localhost:8082/categories',
                type: 'GET',
                success: function(categories) {
                    $.each(categories, function(index, category) {
                        $('#categoryList').append(
                            '<li><a href="#" class="category-link" data-category-id="' + category.id + '">' + category.name + '</a></li>'
                        );
                    });

                    $('.category-link').on('click', function(e) {
                        e.preventDefault();
                        var categoryId = $(this).data('category-id');
                        
                        $.ajax({
                            url: 'http://localhost:8082/products/categoryProduct/' + categoryId,
                            type: 'GET',
                            success: function(products) {
                                displayProducts(products);
                            },
                            error: function() {
                                alert('Failed to fetch products for this category.');
                            }
                        });
                    });

                    $('#allProductsLink').on('click', function(e) {
                        e.preventDefault();
                        $.ajax({
                            url: 'http://localhost:8082/products',
                            type: 'GET',
                            success: function(products) {
                                displayProducts(products);
                            },
                            error: function() {
                                alert('Failed to fetch all products.');
                            }
                        });
                    });
                },
                error: function() {
                    alert('Failed to fetch categories.');
                }
            });
        });

        // Add to Cart Function
        function addToCart(productId, productName, productImage, productPrice) {
            if (userId === null) {
                alert("User not logged in.");
                return;
            }

            const quantity = 1;

            const cartData = {
                "userId": userId,
                "cartItems": [
                    {
                        "productId": productId,
                        "productName": productName,
                        "quantity": quantity,
                        "price": productPrice,
                        "image": productImage
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

    <!-- Bootstrap JS for responsive features -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
</body>
</html>
