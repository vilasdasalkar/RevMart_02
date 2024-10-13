<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="buyerHeader.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cart Page</title>

    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #f8f9fa;
        }

        /* Cart Container */
        .cart-container {
            margin-top: 50px;
            padding: 20px;
            border-radius: 10px;
            background-color: white;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .cart-header {
            text-align: center;
            margin-bottom: 30px;
            font-size: 2rem;
            font-weight: bold;
            color: #343a40;
        }

        /* Table Styling */
        .cart-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        .cart-table th, .cart-table td {
            padding: 15px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }

        .cart-table th {
            background-color: #007bff;
            color: white;
            font-size: 1.1rem;
        }

        .cart-table td {
            font-size: 1rem;
            color: #555;
        }

        /* Table Hover Effect */
        .cart-table tr:hover {
            background-color: #f1f1f1;
            transform: scale(1.02);
            transition: transform 0.3s ease, background-color 0.3s ease;
        }

        .cart-table img {
            width: 100px;
            height: 100px;
            object-fit: cover;
            border-radius: 10px;
        }

        /* Quantity buttons styling */
        .quantity-control {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }

        .btn-quantity {
        margin-top:25px;
            padding: 5px 10px;
            font-size: 1rem;
            font-weight: bold;
            border: none;
            background-color: #007bff;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .btn-quantity:hover {
            background-color: #0056b3;
        }

        .btn-delete {
            background-color: #e74c3c;
            color: white;
            border: none;
            padding: 7px 15px;
            font-size: 0.85rem;
            border-radius: 5px;
            transition: background-color 0.3s ease;
            cursor: pointer;
            display: inline-block;
            margin-top: 10px;
        }

        .btn-delete:hover {
            background-color: #c0392b;
        }

        .cart-total {
            font-weight: bold;
            text-align: right;
            margin-top: 20px;
            font-size: 1.2rem;
        }

        /* Checkout Button */
        a[id="checkout-link"] {
            display: block;
            width: 250px;
            margin: 30px auto;
            padding: 15px;
            background: linear-gradient(90deg, #ff7e5f, #feb47b); /* Gradient */
            color: white;
            text-align: center;
            font-size: 1.25rem;
            font-weight: bold;
            border-radius: 30px;
            text-decoration: none;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
        }

        a[id="checkout-link"]:hover {
            transform: translateY(-3px);
            box-shadow: 0 6px 15px rgba(0, 0, 0, 0.3);
        }

        /* Responsive styling */
        @media (max-width: 768px) {
            .cart-table th, .cart-table td {
                font-size: 0.85rem;
                padding: 10px;
            }

            .cart-table img {
                width: 80px;
                height: 80px;
            }
        }
    </style>
</head>
<body>

<div class="container cart-container">
    <h1 class="cart-header">Your Cart Items</h1>

    <!-- Cart Items will be displayed here in table format -->
    <table class="cart-table">
        <thead>
            <tr>
                <th>Image</th>
                <th>Product Name</th>
                <th>Product ID</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Total</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody id="cart-items">
            <!-- Items will be loaded using AJAX -->
        </tbody>
    </table>

    <!-- Cart Total -->
    <p id="cart-total" class="cart-total"></p>
    <!-- Checkout Button -->
    <a id="checkout-link" href="#" style="color:black">Checkout</a>
</div>

<!-- jQuery and AJAX to Fetch Cart Items -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
let userId = null; 
let totalAmount = 0;

$(document).ready(function() {
    $.ajax({
        url: 'http://localhost:8080/api/users', 
        type: 'GET',
        success: function(users) {
            if (users.length > 0) {
                userId = users[0].id; 
                fetchCartItems(userId); 
            } else {
                alert("No user logged in.");
            }
        },
        error: function(error) {
            console.log('Error fetching user ID:', error);
        }
    });

    function fetchCartItems(userId) {
        $.ajax({
            url: 'http://localhost:8083/cart/user/' + userId,  
            type: 'GET',
            success: function(cart) {
                $('#cart-items').empty(); 
                totalAmount = 0;

                if (cart && cart.length > 0) {
                    cart.forEach(item => {
                        item.cartItems.forEach(cartItem => {
                            appendCartItem(cartItem, cartItem.image || 'https://via.placeholder.com/250', cartItem.productName || 'Unknown Product', item.id);
                            totalAmount += cartItem.price * cartItem.quantity;
                        });
                    });

                    updateTotalAmount();
                } else {
                    $('#cart-items').append('<tr><td colspan="7" class="text-center">No items in cart.</td></tr>');
                }
            },
            error: function(error) {
                console.log('Error fetching cart items:', error);
                $('#cart-items').append('<tr><td colspan="7" class="text-center">No items in cart.</td></tr>');
            }
        });
    }

    function appendCartItem(cartItem, productImage, productName, cartId) {
        const row = $('<tr></tr>');

        const productImageElement = $('<td></td>').append($('<img>').attr('src', productImage).attr('alt', productName));
        const productNameElement = $('<td></td>').text(productName);
        const productIdElement = $('<td></td>').text(cartItem.productId);
        const priceElement = $('<td></td>').text('Rs. ' + cartItem.price.toFixed(2));
        const totalElement = $('<td></td>').text('Rs. ' + (cartItem.price * cartItem.quantity).toFixed(2));

        // Quantity control
        const quantityElement = $('<td></td>').addClass('quantity-control');
        const decreaseButton = $('<button></button>').text('-').addClass('btn-quantity').click(function() {
            updateQuantity(cartItem, -1, totalElement, cartId);
        });
        const quantityDisplay = $('<span></span>').addClass('quantity-display').text(cartItem.quantity);
        const increaseButton = $('<button></button>').text('+').addClass('btn-quantity').click(function() {
            updateQuantity(cartItem, 1, totalElement, cartId);
        });
        quantityElement.append(decreaseButton, quantityDisplay, increaseButton);

        // Delete button
        const deleteButton = $('<td></td>').append(
            $('<button></button>').text('Delete').addClass('btn-delete').click(function() {
                deleteCartItem(cartId);
            })
        );

        // Append all elements to the row
        row.append(productImageElement, productNameElement, productIdElement, priceElement, quantityElement, totalElement, deleteButton);

        // Append the row to the table
        $('#cart-items').append(row);
    }

    function updateQuantity(cartItem, change, totalElement, cartId) {
        let newQuantity = cartItem.quantity + change;
        if (newQuantity <= 0) return; // Prevent quantity going below 1

        // Update the cart item's quantity
        cartItem.quantity = newQuantity;

        // Update the specific quantity display of this cart item
        totalElement.text('Rs. ' + (cartItem.price * newQuantity).toFixed(2));

        // Find the quantity display element and update its text
        const quantityDisplay = totalElement.closest('tr').find('.quantity-display');
        quantityDisplay.text(newQuantity); // Update the quantity displayed

        // Recalculate total amount
        recalculateTotalAmount();
    }


    function deleteCartItem(cartId) {
        if (confirm('Are you sure you want to delete this item from the cart?')) {
            $.ajax({
                url: 'http://localhost:8083/cart/delete/' + cartId,  
                type: 'DELETE',
                success: function(response) {
                    alert('Cart item deleted successfully!');
                    fetchCartItems(userId);
                },
                error: function(error) {
                    console.log('Error deleting cart item:', error);
                    alert('Failed to delete cart item.');
                }
            });
        }
    }

    function recalculateTotalAmount() {
        totalAmount = 0;
        $('#cart-items tr').each(function() {
            const priceText = $(this).find('td:nth-child(6)').text().replace('Rs. ', '');
            totalAmount += parseFloat(priceText);
        });
        updateTotalAmount();
    }

    function updateTotalAmount() {
        $('#cart-total').text('Total: Rs. ' + totalAmount.toFixed(2));
        $('#checkout-link').click(function(e) {
            e.preventDefault();
            if (userId && totalAmount > 0) {
                const orderData = {
                    userId: userId,
                    totalAmount: totalAmount,
                    status: "Order Placed",
                    cartItems: []
                };

                $('#cart-items tr').each(function() {
                    const cartItem = {
                        productId: $(this).find('td:nth-child(3)').text(),
                        productName: $(this).find('td:nth-child(2)').text(),
                        quantity: parseInt($(this).find('.quantity-display').text()),
                        price: parseFloat($(this).find('td:nth-child(4)').text().replace('Rs. ', ''))
                    };
                    orderData.cartItems.push(cartItem);
                });

                let existingOrders = JSON.parse(localStorage.getItem('orders')) || [];
                existingOrders.push(orderData);
                localStorage.setItem('orders', JSON.stringify(existingOrders));

                window.location.href = '/processPayment?totalPrice=' + totalAmount.toFixed(2);
            }
        });
    }
});
</script>

<!-- Bootstrap JS -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
