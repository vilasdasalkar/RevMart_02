<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Wishlist</title>

<!-- Bootstrap CSS -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

<style>
body {
	background-color: #f8f9fa;
}
.container {
	margin-top: 20px;
}
.product-card {
	background-color: white;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding: 20px;
	margin-bottom: 20px;
	border: 2px solid #ddd;
	border-color: green;
    border-width:3px;
}
.product-image {
	height:200px;
	object-fit: cover;
	border-radius: 10px;
	margin-bottom: 15px;
}
.delete-button {
	background-color: #dc3545;
	color: white;
	border: none;
	padding: 10px 20px;
	border-radius: 5px;
	cursor: pointer;
}
.delete-button:hover {
	background-color: #c82333;
}
</style>
</head>
<body>

<div class="container">
	<h2>Your Wishlist</h2>
	<div id="wishlist" class="row"></div>
</div>

<script>
function loadWishlist() {
	const wishlist = JSON.parse(localStorage.getItem('wishlist')) || [];
	const wishlistContainer = document.getElementById('wishlist');
	wishlistContainer.innerHTML = ''; // Clear existing wishlist items

	if (wishlist.length === 0) {
		wishlistContainer.innerHTML = '<p>Your wishlist is empty.</p>';
		return;
	}

	wishlist.forEach(item => {
		const productCard = document.createElement('div');
		productCard.className = 'col-md-4';
		productCard.innerHTML = `
			<div class="product-card">
				<img class="product-image" src="${item.image}" alt="${item.name}">
				<h5>${item.name}</h5>
				<p>Price: &#8377 ${item.price}</p>
				<button class="delete-button" onclick="removeFromWishlist('${item.id}')">Delete</button>
			</div>
		`;
		wishlistContainer.appendChild(productCard);
	});
}

function removeFromWishlist(productId) {
	let wishlist = JSON.parse(localStorage.getItem('wishlist')) || [];
	wishlist = wishlist.filter(item => item.id !== productId);
	localStorage.setItem('wishlist', JSON.stringify(wishlist));
	loadWishlist(); // Reload the wishlist after deletion
}

// Load the wishlist on page load
window.onload = loadWishlist;
</script>

<!-- Bootstrap JS -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
