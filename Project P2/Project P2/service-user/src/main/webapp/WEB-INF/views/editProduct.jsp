<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Product</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        /* General Body Styling */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #e0f7fa;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            height: 100vh;
            justify-content: center;
        }

        h2 {
            color: #00796b;
            margin-bottom: 20px;
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
        }

        /* Back to Home Button */
        .back-button {
            position: absolute;
            top: 20px;
            left: 20px;
            background-color: transparent;
            border: none;
            cursor: pointer;
            font-size: 20px;
            color: #00796b;
            transition: color 0.3s;
        }
        
        .back-button:hover {
            color: #004d40;
        }

        /* Form Styling */
        form {
            max-width: 400px;
            width: 100%;
            background-color: #ffffff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            animation: slide-in 0.5s ease;
        }

        @keyframes slide-in {
            from {
                transform: translateY(-20px);
                opacity: 0;
            }
            to {
                transform: translateY(0);
                opacity: 1;
            }
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #004d40;
        }

        input[type="text"],
        input[type="number"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 15px;
            border: 2px solid #00796b;
            border-radius: 5px;
            font-size: 16px;
            transition: border-color 0.3s;
        }

        input[type="text"]:focus,
        input[type="number"]:focus {
            border-color: #004d40;
            outline: none;
        }

        /* Button Styling */
        #updateProductBtn {
            background-color: #00796b;
            color: white;
            padding: 12px;
            border: none;
            border-radius: 5px;
            font-size: 18px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.2s;
            width: 100%;
        }

        #updateProductBtn:hover {
            background-color: #004d40;
            transform: translateY(-2px);
        }

        /* Result Message Styling */
        #result {
            margin-top: 20px;
            text-align: center;
            font-size: 16px;
        }

        #result p {
            padding: 10px;
            border-radius: 5px;
            display: inline-block;
        }

        /* Success and Error Messages */
        .success-message {
            background-color: #4caf50;
            color: white;
        }

        .error-message {
            background-color: #f44336;
            color: white;
        }
    </style>
</head>
<body>
    <button class="back-button"><i class="fas fa-arrow-left"></i></button>
    <h2>Edit Product</h2>
    
    <form id="editProductForm">
        <input type="hidden" id="productId" value="${product.id}" />

        <label for="name">Product Name:</label>
        <input type="text" id="name" name="name" value="${product.name}" required />

        <label for="description">Description:</label>
        <input type="text" id="description" name="description" value="${product.description}" required />

        <label for="skuCode">SKU Code:</label>
        <input type="text" id="skuCode" name="skuCode" value="${product.skuCode}" required />

        <label for="price">Price:</label>
        <input type="number" id="price" name="price" value="${product.price}" required />

        <label for="categoryId">Category ID:</label>
        <input type="number" id="categoryId" name="categoryId" value="${product.category.id}" required />

        <label for="imageUrl">Image URL:</label>
        <input type="text" id="imageUrl" name="imageUrl" value="${product.imageurl}" required />

        <button type="button" id="updateProductBtn">Update Product</button>
    </form>

    <div id="result"></div>

    <script>
        $(document).ready(function() {
            $('#updateProductBtn').click(function() {
                var productId = $('#productId').val();
                var name = $('#name').val();
                var description = $('#description').val();
                var skuCode = $('#skuCode').val();
                var price = $('#price').val();
                var categoryId = $('#categoryId').val();
                var imageUrl = $('#imageUrl').val();

                // Create the JSON data to be sent
                var jsonData = JSON.stringify({
                    "id": productId,
                    "name": name,
                    "description": description,
                    "skuCode": skuCode,
                    "price": price,
                    "category": {
                        "id": categoryId
                    },
                    "imageurl": imageUrl
                });

                // AJAX call to update the product
                $.ajax({
                    url: 'http://localhost:8082/products/' + productId,
                    type: 'PUT',
                    contentType: 'application/json',
                    data: jsonData,
                    success: function(response) {
                        $('#result').html('<p class="success-message">Product updated successfully!</p>');
                    },
                    error: function(error) {
                        $('#result').html('<p class="error-message">Error updating product: ' + error.responseText + '</p>');
                    }
                });
            });

            // Redirect back to home on back button click
            $('.back-button').click(function() {
                window.location.href = '/sellerPage';
            });
        });
    </script>
</body>
</html>
