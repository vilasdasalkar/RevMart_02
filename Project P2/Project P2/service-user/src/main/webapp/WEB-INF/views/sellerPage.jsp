<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Seller Dashboard</title>

    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <style>
        /* Navbar Styling */
        .navbar {
            background: linear-gradient(90deg, #ff6a00, #ee0979);
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        }
        .navbar-brand {
            color: white !important;
            font-size: 1.7rem;
            font-weight: bold;
            text-transform: uppercase;
            letter-spacing: 2px;
        }

        /* Main Content Styling */
        body {
            background: linear-gradient(135deg, #ffafbd 0%, #ffc3a0 100%);
            background-size: cover;
            background-position: center;
            font-family: 'Poppins', sans-serif;
            color: #333;
        }
        .dashboard-content {
            margin-top: 50px;
            padding: 30px;
            background-color: rgba(255, 255, 255, 0.85); /* Light opacity */
            border-radius: 20px;
            box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2);
            backdrop-filter: blur(10px); /* Blurred background for added depth */
        }

        /* Title Styling */
        .dashboard-title {
            text-align: center;
            font-size: 2.7rem;
            color: #ff6a00; /* Updated vibrant title color */
            margin-bottom: 40px;
            text-transform: uppercase;
            font-weight: bold;
            letter-spacing: 2px;
        }

        /* Card Styles */
        .card {
            border: none;
            border-radius: 20px; 
            overflow: hidden;
            transition: all 0.4s ease;
            background: linear-gradient(135deg, #ffafbd 0%, #ffc3a0 100%); /* Gradient background for cards */
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
        }
        .card:hover {
            transform: scale(1.08);
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.25);
        }
        .card::before {
            content: "";
            position: absolute;
            top: -4px; 
            left: -4px; 
            width: calc(100% + 8px); 
            height: calc(100% + 8px); 
            border-radius: 25px; 
            background-color: rgba(255,255,255,0.15); /* Slight overlay */
            z-index: -1; 
        }

        .card-body h5 {
            font-size: 1.5rem;
            color: #333; /* Updated text color for visibility */
            font-weight: bold;
        }
        .card-body p {
            color: #555; /* Muted text */
        }

        /* Button Styling */
        .btn {
            border-radius: 50px;
            padding: 12px 30px;
            font-size: 1.1rem;
            font-weight: bold;
            text-transform: uppercase;
            transition: background-color 0.4s ease, transform 0.3s ease;
            color: white;
        }
        .btn:hover {
            transform: translateY(-5px);
        }

        .btn-danger { background-color:#ff6a00; border-color:#ff6a00;}
        .btn-success { background-color:#00c851; border-color:#00c851;}
        .btn-info { background-color:#0099cc; border-color:#0099cc;}
        .btn-primary { background-color:#6610f2; border-color:#6610f2;}

        /* Responsive adjustments */
        @media (max-width:768px) {
           .dashboard-content {
               padding :15px;
           }
           .card { 
               margin-bottom :20px;
           }
       }

       /* Custom styles for card arrangement in a grid layout with elliptical shapes */
       .custom-card-layout {
           display:flex;
           flex-wrap : wrap;
           justify-content : space-between; 
           align-items : center;
       }
       .custom-card-layout > div {
           flex-basis : calc(48% -20px); /* Two cards per row with spacing */
           margin-bottom :30px; 
       }

       @media (max-width:768px) { 
           .custom-card-layout > div { 
               flex-basis : 100%; /* Full width on small screens */
           } 
       }

       /* Subtle hover effect for card buttons */
       .btn-danger:hover { background-color: #e65c00; }
       .btn-success:hover { background-color: #00b34a; }
       .btn-info:hover { background-color: #007bb5; }
       .btn-primary:hover { background-color: #520cb4; }

    </style>
</head>
<body>

   
    <!-- Main Content -->
    <div class="container dashboard-content">
        <h1 class="dashboard-title">Seller Dashboard</h1>
        
        <p class="text-center text-muted">Manage your products and orders with ease and style.</p>

        <div class="custom-card-layout">
            
             <!-- Add Category Card -->
             <div class="col-md-6 mb-4">
                <div class="card text-center">
                    <div class="card-body">
                        <h5 class="card-title">Add Category</h5>
                        <p class="card-text">Add new categories for your products.</p>
                        <a href="/addCategory" class="btn btn-danger">Add Category</a>
                    </div>
                </div>
             </div>

             <!-- Add Products Card -->
             <div class="col-md-6 mb-4">
                <div class="card text-center">
                    <div class="card-body">
                        <h5 class="card-title">Add Products</h5>
                        <p class="card-text">Add new products to your inventory.</p>
                        <a href="/addProduct" class="btn btn-success">Add Product</a>
                    </div>
                </div>
             </div>

             <!-- View All Products Card -->
             <div class="col-md-6 mb-4">
                <div class="card text-center">
                    <div class="card-body">
                        <h5 class="card-title">All Products</h5>
                        <p class="card-text">View all products in your store.</p>
                        <a href="/allProduct" class="btn btn-info">View Products</a>
                    </div>
                </div>
             </div>

             <!-- View Orders Card -->
             <div class="col-md-6 mb-4">
                <div class="card text-center">
                    <div class="card-body">
                        <h5 class="card-title">View Orders</h5>
                        <p class="card-text">Check all the orders placed by customers.</p>
                        <a href="/viewOrder" class="btn btn-primary">View Orders</a>
                    </div>
                </div>
             </div>

             <!-- Logout Card -->
             <div class="col-md-6 mb-4">
                <div class="card text-center">
                    <div class="card-body">
                        <h5 class="card-title">Logout</h5>
                        <p class="card-text">Logout from your account.</p>
                        <a href="/logout" class="btn btn-danger">Logout</a>
                    </div>
                </div>
             </div>

         </div> 
     </div>

     <!-- Bootstrap JS, Popper.js, and jQuery -->
     <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
     <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
     <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body> 
</html>