<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Category</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #ffafbd 0%, #ffc3a0 100%);
            background-size: cover;
            margin: 0;
            padding: 0;
            color: #333;
        }

        .container {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            max-width: 1200px;
            margin: 40px auto;
            padding: 20px;
            background-color: rgba(255, 255, 255, 0.9);
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
            animation: fadeIn 0.8s ease-in;
        }

        .form-container {
            flex: 1 1 400px;
            padding: 30px;
            border-radius: 10px;
            background-color: rgba(255, 255, 255, 0.95);
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
            position: relative; /* To position the home button */
        }

        .form-container h1 {
            color: #007bff;
            margin-bottom: 20px;
            font-size: 28px;
            font-weight: bold;
            text-align: center;
        }

        label {
            font-weight: 600;
            margin-bottom: 5px;
            display: block;
            font-size: 16px;
            color: #555;
        }

        input[type="text"] {
            width: calc(100% - 24px);
            padding: 12px;
            margin-bottom: 20px;
            border: 1px solid #007bff;
            border-radius: 5px;
            font-size: 16px;
            transition: border 0.3s, box-shadow 0.3s;
            background-color: #f8f9fa; /* Light background for input */
        }

        input[type="text"]:focus {
            border-color: #0056b3;
            box-shadow: 0 0 5px rgba(0, 86, 179, 0.3);
            outline: none;
        }

        input[type="submit"] {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 12px 30px;
            cursor: pointer;
            border-radius: 5px;
            font-size: 16px;
            font-weight: bold;
            transition: background-color 0.3s, transform 0.3s;
            width: 100%;
            margin-top: 10px;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
        }

        .category-list {
            flex: 1 1 400px;
            padding: 30px;
            background-color: rgba(255, 255, 255, 0.95);
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
            margin-left: 20px; /* Space between containers */
        }

        .category-list h2 {
            color: #007bff;
            margin-bottom: 20px;
            font-size: 24px;
            text-align: center;
        }

        #categoryList {
            max-height: 300px;
            overflow-y: auto;
            margin-top: 20px;
        }

        #categoryList ul {
            list-style: none;
            padding: 0;
        }

        #categoryList ul li {
            background-color: #ffffff;
            margin-bottom: 10px;
            padding: 12px;
            border: 1px solid #eee;
            border-radius: 5px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            transition: background-color 0.3s, transform 0.2s;
        }

        #categoryList ul li:hover {
            background-color: #f1f1f1;
            transform: translateX(3px);
        }

        a.home-link {
            position: absolute; /* Position in the top-left corner */
            top: 20px;
            left: 20px;
            background-color: #28a745;
            color: white;
            text-decoration: none;
            padding: 10px 15px;
            border-radius: 5px;
            font-size: 14px;
            display: flex;
            align-items: center;
            transition: background-color 0.3s;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

        a.home-link:hover {
            background-color: #218838;
        }

        a.home-link i {
            margin-right: 5px;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @media (max-width: 768px) {
            .container {
                flex-direction: column;
                padding: 10px;
            }
            .form-container, .category-list {
                flex: 1 1 100%;
                margin-left: 0; /* Reset margin for smaller screens */
            }
        }
    </style>
</head>
<body>
    <a href="/sellerPage" class="home-link"><i class="fas fa-arrow-left"></i>Back to Home</a>
    <div class="container">
        <div class="form-container">
            <h1>Add Category</h1>
            <form id="categoryForm">
                <label for="name">Category Name:</label>
                <input type="text" id="name" name="name" required>

                <label for="imageName">Image URL:</label>
                <input type="text" id="imageName" name="imageName" required>

                <input type="submit" value="Add Category">
            </form>
        </div>
        <div class="category-list">
            <h2>Categories</h2>
            <div id="categoryList">
                <!-- List of categories will be populated here via AJAX -->
            </div>
        </div>
    </div>

    <script type="text/javascript">
        $(document).ready(function() {
            $('#categoryForm').submit(function(e) {
                e.preventDefault(); // Prevents the form from refreshing the page

                // Get form data
                var categoryData = {
                    "name": $('#name').val(),
                    "imageName": $('#imageName').val()
                };

                // Send AJAX request
                $.ajax({
                    url: 'http://localhost:8082/categories', // Directly to the REST API
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(categoryData),
                    success: function(response) {
                        alert('Category added successfully!');
                        loadCategories(); // Call function to reload categories
                    },
                    error: function(xhr, status, error) {
                        console.log(xhr.responseText); // Log error for more details
                        alert('Error occurred: ' + xhr.status + ' - ' + xhr.responseText);
                    }
                });
            });

            // Function to load categories
            function loadCategories() {
                $.ajax({
                    url: 'http://localhost:8082/categories', // URL to fetch categories
                    type: 'GET',
                    success: function(data) {
                        var categoryList = '';
                        $.each(data, function(index, category) {
                            categoryList += '<li>' + category.name + '</li>';
                        });
                        $('#categoryList').html('<ul>' + categoryList + '</ul>');
                    },
                    error: function() {
                        $('#categoryList').html('Failed to load categories.');
                    }
                });
            }

            // Load categories on page load
            loadCategories();
        });
    </script>
</body>
</html>