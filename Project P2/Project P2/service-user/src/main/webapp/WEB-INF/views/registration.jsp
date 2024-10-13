<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />
    <style>
        body {
            font-family: 'Arial', sans-serif;
            height: 100vh;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            background-image: url('https://images.unsplash.com/photo-1517336714731-489689fd1ca8'); /* Replace with your image URL */
            background-size: cover; /* Cover the entire viewport */
            background-position: center; /* Center the image */
            color: #333;
            
        }

        .container {
            max-width: 450px;
            width: 100%;
            padding: 30px;
            background-color: rgba(255, 255, 255, 0.9);
            border-radius: 12px;
            box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2);
            text-align: left;
            transition: transform 0.3s, box-shadow 0.3s;
            animation: slideDown 1s ease-out; /* Animation for page load */
            margin-top: 400px;
        }

        @keyframes slideDown {
            0% {
                opacity: 0;
                transform: translateY(-100%);
            }
            100% {
                opacity: 1;
                transform: translateY(0);
            }
        }

        h2 {
            margin-bottom: 20px;
            color: #007bff;
            text-transform: uppercase;
            letter-spacing: 1px;
            text-align: center;
        }

        label {
            font-weight: bold;
            margin-top: 10px;
            display: block;
        }

        input[type="text"], input[type="password"], input[type="email"], select {
            width: calc(100% - 20px);
            padding: 12px;
            margin: 10px 0;
            border: 1px solid #ced4da;
            border-radius: 6px;
            transition: border-color 0.3s, box-shadow 0.3s, transform 0.3s;
        }

        input[type="text"]:focus, input[type="password"]:focus, input[type="email"]:focus, select:focus {
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
            outline: none;
            transform: scale(1.05); /* Hover effect on focus */
        }

        input[type="submit"] {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 14px;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s, transform 0.3s, box-shadow 0.3s;
            width: 100%;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
        }

        .login-link {
            margin-top: 20px;
            text-align: center;
            color: #007bff;
        }

        .login-link a {
            text-decoration: none;
            font-weight: bold;
            transition: color 0.3s;
        }

        .login-link a:hover {
            color: #0056b3;
            text-decoration: underline;
        }

        .footer-text {
            font-size: 12px;
            color: #777;
            margin-top: 10px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>User Registration</h2>
        <form id="registrationForm">
            <label for="name">Name</label>
            <input type="text" id="name" name="name" placeholder="Enter your name" required>
            
            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="Enter your email" required>
            
            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="Enter your password" required>
            
            <label for="mobileNumber">Mobile Number</label>
            <input type="text" id="mobileNumber" name="mobileNumber" placeholder="Enter your mobile number" required>
            
            <label for="address">Address</label>
            <input type="text" id="address" name="address" placeholder="Enter your address" required>
            
            <label for="city">City</label>
            <input type="text" id="city" name="city" placeholder="Enter your city" required>
            
            <label for="state">State</label>
            <input type="text" id="state" name="state" placeholder="Enter your state" required>
            
            <label for="country">Country</label>
            <input type="text" id="country" name="country" placeholder="Enter your country" required>
            
            <label for="roles">Role</label>
            <select id="roles" name="roles" required>
                <option value="" disabled selected>Select Role</option>
                <option value="BUYER">Buyer</option>
                <option value="SELLER">Seller</option>
            </select>
            
            <input type="submit" value="Register">
        </form>
        <p class="login-link">Already have an account? <a href="/login">Log in</a></p>
        <p id="responseMessage"></p>
        <p class="footer-text">By registering, you agree to our terms and conditions.</p>
    </div>
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    <script>
        $(document).ready(function() {
            $('#registrationForm').submit(function(event) {
                event.preventDefault(); // Prevent the form from submitting via the browser

                var formData = {
                    name: $('#name').val(),
                    email: $('#email').val(),
                    password: $('#password').val(),
                    mobileNumber: $('#mobileNumber').val(),
                    address: $('#address').val(),
                    city: $('#city').val(),
                    state: $('#state').val(),
                    country: $('#country').val(),
                    roles: [$('#roles').val()] // Send role as an array
                };

                $.ajax({
                    url: '/api/users', // The API endpoint
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(formData),
                    success: function(response) {
                        $('#responseMessage').text('User registered successfully.').css('color', 'green');
                    },
                    error: function(error) {
                        $('#responseMessage').text('Error occurred during registration.').css('color', 'red');
                    }
                });
            });
        });
    </script>
</body>
</html>
