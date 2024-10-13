<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
    
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
            box-shadow: 0 15px 30px rgba(0, 0, 0, 0.3);
            text-align: left;
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
        }
        input[type="email"], input[type="password"] {
            width: calc(100% - 20px);
            padding: 12px;
            margin: 10px 0;
            border: 1px solid #ced4da;
            border-radius: 6px;
            transition: border-color 0.3s, box-shadow 0.3s;
        }
        input[type="email"]:focus, input[type="password"]:focus {
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
            outline: none;
        }
        .btn-primary {
            background-color: #007bff;
            border: none;
            padding: 14px; 
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s, transform 0.3s, box-shadow 0.3s;
            width: 100%;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
        }

        /* Home Button positioned to the top-left */
        .home-button {
            position: absolute;
            top: 20px;
            left: 20px;
            background-color: #0d47a1;
            color: white;
            padding: 10px; 
            text-align: center;
            text-decoration: none; 
            border-radius: 5px; 
            font-size: 18px; 
        }
        .home-button:hover {
            background-color: #1565c0; 
        }

        /* Additional styles for a more attractive UI */
        .form-control {
            transition: all .3s ease-in-out; /* Smooth transition for inputs */
        }
        
        .form-control:hover {
          border-color:#007bff; /* Change border color on hover */
          box-shadow:none; /* Remove shadow on hover */
        }

        .text-muted a {
          color:#007bff; /* Change link color */
          text-decoration:none; /* Remove underline */
          transition:.3s ease-in-out; /* Smooth transition for links */
      }

      .text-muted a:hover {
          text-decoration:none; /* Keep it clean on hover */
          color:#0056b3; /* Darker blue on hover */
      }

    </style>
</head>
<body>

<a href="/" class="home-button">
    <i class="fas fa-arrow-left"></i> Back to Home
</a>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <h2 class="text-center mb-4">Login</h2>

            <form action="/doLogin" method="post" class="shadow p-4 rounded bg-white">
                <!-- Email Field -->
                <div class="mb-3">
                    <label for="email" class="form-label">Email address</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="Enter your email" required>
                </div>

                <!-- Password Field -->
                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Enter your password" required>
                </div>

                <!-- Submit Button -->
                <div class="d-grid">
                    <button type="submit" class="btn btn-primary">Login</button>
                </div>

               <!-- Registration Link -->
                <div class="mt-3 text-center">
                    <span class="text-muted">Don't have an account? </span>
                    <a href="/register" class="text-primary">Register here</a>
                </div>
                
              </form>
          </div>
      </div>
</div>

<!-- Bootstrap JS (optional for responsive behaviors) -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>

</body>
</html>