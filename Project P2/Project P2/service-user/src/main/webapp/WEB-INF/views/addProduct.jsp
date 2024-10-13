<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- Include Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome for icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
    
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #f6d365 0%, #fda085 100%);
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 120vh;
        }

        .container {
            position: relative;
            background: #ffffff;
            border-radius: 15px;
            box-shadow: 0 15px 25px rgba(0, 0, 0, 0.2);
            padding: 40px;
            width: 700px;
            transition: transform 0.3s, box-shadow 0.3s;
            margin-top: 150px;
        }

        .container:hover {
            transform: translateY(-10px);
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3);
        }

        h1 {
            text-align: center;
            color: #ff5722;
            font-size: 2.5rem;
            margin-bottom: 30px;
            text-transform: uppercase;
            letter-spacing: 2px;
            font-weight: bold;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }

        label {
            font-size: 1rem;
            color: #333;
            text-transform: uppercase;
            font-weight: bold;
            margin-bottom: 5px;
        }

        input[type="text"], input[type="number"], select {
            padding: 12px;
            font-size: 1rem;
            border: 1px solid #ddd;
            border-radius: 8px;
            outline: none;
            transition: border-color 0.3s, box-shadow 0.3s;
            background-color: #f0f0f0;
            font-weight: bold;
            color: #333;
        }

        input[type="text"]:focus, input[type="number"]:focus, select:focus {
            border-color: #ff5722;
            box-shadow: 0 0 5px rgba(255, 87, 34, 0.3);
        }

        input[type="submit"] {
            background: #ff5722;
            color: #fff;
            border: none;
            padding: 12px;
            border-radius: 8px;
            font-size: 1.2rem;
            cursor: pointer;
            transition: background 0.3s, transform 0.3s;
            font-weight: bold;
        }

        input[type="submit"]:hover {
            background: #e64a19;
            transform: translateY(-2px);
        }

        input[type="submit"]:active {
            transform: scale(0.98);
        }

        .home-button {
            position: absolute;
            top: 20px;
            left: 20px;
            background-color: #3f51b5;
            color: white;
            padding: 12px;
            text-align: center;
            text-decoration: none;
            border-radius: 8px;
            font-size: 1.1rem;
            display: block;
            transition: background 0.3s;
            font-weight: bold;
            letter-spacing: 1px;
        }

        .home-button:hover {
            background-color: #303f9f;
        }

        .error-message {
            color: #d32f2f;
            font-size: 0.85rem;
            margin-top: -10px;
            margin-bottom: 10px;
            display: none;
            font-weight: bold;
        }

        .success-message {
            color: #388e3c;
            font-size: 1.2rem;
            font-weight: bold;
            text-align: center;
            margin-top: 20px;
        }

        @media (max-width: 768px) {
            .container {
                width: 90%;
                padding: 20px;
            }
            input[type="submit"], .home-button {
                font-size: 1rem;
            }
        }
    </style>

    
</head>

<body>
    <div class="container">
        <h1>Add Product</h1>
        <form id="productForm">
            <label for="name">Product Name:</label>
            <input type="text" id="name" name="name" required>
            <div class="error-message">Product name is required</div>

            <label for="description">Description:</label>
            <input type="text" id="description" name="description" required>
            <div class="error-message">Description is required</div>

            <label for="skuCode">SKU Code:</label>
            <input type="text" id="skuCode" name="skuCode" required>
            <div class="error-message">SKU Code is required</div>

            <label for="price">Price:</label>
            <input type="number" id="price" name="price" required>
            <div class="error-message">Price is required</div>

            <label for="categoryDropdown">Category:</label>
            <select id="categoryDropdown" name="categoryDropdown" required></select>
            <div class="error-message">Please select a category</div>

            <input type="hidden" id="categoryId" name="categoryId">
            <input type="hidden" id="categoryName" name="categoryName">

            <label for="imageurl">Image URL:</label>
            <input type="text" id="imageurl" name="imageurl" required>
            <div class="error-message">Image URL is required</div>

            <input type="submit" value="Add Product">
        </form>

        <div class="success-message"></div>
    </div>

    <a href="/sellerPage" class="home-button">
        <i class="fas fa-arrow-left"></i> Back to Home
    </a>
</body>





























<script type="text/javascript">
        $(document).ready(function() {
            // Function to fetch categories and populate the dropdown
            function loadCategories() {
                $.ajax({
                    url: 'http://localhost:8082/categories',
                    type: 'GET',
                    contentType: 'application/json',
                    success: function(categories) {
                        var dropdown = $('#categoryDropdown');
                        dropdown.empty();
                        dropdown.append('<option value="">Select a category</option>');
                        $.each(categories, function(index, category) {
                            dropdown.append('<option value="' + category.id + '" data-name="' + category.name + '">' + category.name + '</option>');
                        });
                    },
                    error: function(xhr, status, error) {
                        console.log("Error fetching categories: " + xhr.responseText);
                    }
                });
            }

            loadCategories();

            $('#categoryDropdown').change(function() {
                var selectedOption = $(this).find('option:selected');
                var categoryId = selectedOption.val();
                var categoryName = selectedOption.data('name');
                $('#categoryId').val(categoryId);
                $('#categoryName').val(categoryName);
            });

            $('#productForm').submit(function(e) {
                e.preventDefault();

                var isValid = true;
                $('.error-message').hide();

                if (!$('#name').val()) {
                    $('#name').next('.error-message').show();
                    isValid = false;
                }
                if (!$('#description').val()) {
                    $('#description').next('.error-message').show();
                    isValid = false;
                }
                if (!$('#skuCode').val()) {
                    $('#skuCode').next('.error-message').show();
                    isValid = false;
                }
                if (!$('#price').val()) {
                    $('#price').next('.error-message').show();
                    isValid = false;
                }
                if (!$('#categoryId').val()) {
                    $('#categoryDropdown').next('.error-message').show();
                    isValid = false;
                }

                if (!isValid) return;

                var productData = {
                    "name": $('#name').val(),
                    "description": $('#description').val(),
                    "skuCode": $('#skuCode').val(),
                    "price": parseFloat($('#price').val()),
                    "category": {
                        "id": parseInt($('#categoryId').val()),
                        "name": $('#categoryName').val()
                    },
                    "imageurl": $('#imageurl').val()
                };

                $.ajax({
                    url: 'http://localhost:8082/products',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(productData),
                    success: function(response) {
                        $('.success-message').text('Product added successfully!').show();
                    },
                    error: function(xhr, status, error) {
                        console.log(xhr.responseText);
                        alert('Error occurred: ' + xhr.status + ' - ' + xhr.responseText);
                    }
                });
            });
        });
    </script>
</html>