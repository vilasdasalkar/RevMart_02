<%@include file="buyerHeader.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Orders Page</title>

    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background:#ede8d8; /* Soft orange to yellow gradient */
            color: #333;
            font-family: 'Arial', sans-serif;
        }

        .orders-container {
            margin-top: 50px;
            text-align: center;
            background-color: rgba(255, 255, 255, 0.9); /* Slightly transparent white */
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
        }

        .orders-header {
            margin-bottom: 30px;
            font-size: 2.5rem;
            font-weight: bold;
            color: #ff6f61; /* Coral color */
        }

        .order-list {
            list-style-type: none;
            padding: 0;
        }

        .order-item {
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: rgba(255, 245, 220, 0.9); /* Light beige with transparency */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
            text-align: left;
            transition: transform 0.2s;
        }

        .order-item:hover {
            transform: scale(1.02);
            background-color: rgba(255, 228, 196, 1); /* Lighter beige on hover */
        }

        .order-status {
            color: #28a745; /* Green for status */
            font-weight: bold;
        }

        .total-amount {
            color: #dc3545; /* Red for total amount */
            font-weight: bold;
        }

        .items-list {
            margin-top: 10px;
            padding-left: 15px;
            color: #333; /* Dark color for item text */
        }

        .items-list li {
            margin-bottom: 5px;
        }

        /* Responsive design */
        @media (max-width: 768px) {
            .orders-header {
                font-size: 2rem;
            }
        }
    </style>
</head>
<body>

<div class="container orders-container">
    <h1 class="orders-header">Your Orders</h1>

    <!-- Orders will be displayed here -->
    <ul id="orders-list" class="order-list">
        <!-- Orders will be loaded using JavaScript -->
    </ul>
</div>

<!-- jQuery and JavaScript to Fetch Orders from localStorage -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function() {
    // Fetch orders from localStorage
    let orders = JSON.parse(localStorage.getItem('orders')) || [];

    if (orders.length > 0) {
        // Loop through each order and display it
        orders.forEach(order => {
            appendOrderItem(order);
        });
    } else {
        $('#orders-list').append('<li style="color: #ff6f61;">No orders found.</li>'); // Contrast text for empty message
    }

    // Helper function to append order items to the list
    function appendOrderItem(order) {
        // Create the order item container
        let orderItem = $('<li class="order-item"></li>');

        // Create elements for order details
        let orderId = $('<h5></h5>').text('Order ID: ' + Math.floor(Math.random() * 1000000));
        let orderStatus = $('<p></p>').html('<strong>Status:</strong> <span class="order-status">' + order.status + '</span>');
        let totalAmount = $('<p></p>').html('<strong>Total Amount:</strong> <span class="total-amount">Rs.' + order.totalAmount.toFixed(2) + '</span>');

        // Create the list for cart items
        let itemsList = $('<ul class="items-list"></ul>');
        order.cartItems.forEach(item => {
            let itemElement = $('<li></li>').text(item.productName + ' - Quantity: ' + item.quantity + ' - Price: Rs.' + item.price);
            itemsList.append(itemElement);
        });

        // Append all elements to the order item container
        orderItem.append(orderId);
        orderItem.append(orderStatus);
        orderItem.append(totalAmount);
        orderItem.append('<p><strong>Items:</strong></p>');
        orderItem.append(itemsList);

        // Append the order item container to the orders list
        $('#orders-list').append(orderItem);
    }
});
</script>

<!-- Bootstrap JS -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
