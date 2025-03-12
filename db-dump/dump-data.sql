USE `pet-supermarket` ;

-- Insert Roles
INSERT INTO `roles` (role_id, role_name) VALUES
(1, 'ADMIN'),
(2, 'CUSTOMER');

-- Insert Users
INSERT INTO `users` (user_id, username, password, email, full_name) VALUES
(1, 'admin_user', 'adminpass', 'admin@example.com', 'Admin User'),
(2, 'customer_user', 'customerpass', 'customer@example.com', 'Customer User');

-- Assign Roles to Users
INSERT INTO `user_role` (user_id, role_id) VALUES
(1, 1), -- Admin User
(2, 2); -- Customer User

-- Insert Manufacturers
INSERT INTO `manufacturers` (manufacturer_id, manufacture_name) VALUES
(1, 'Acme Pet Supplies'),
(2, 'Happy Pets Co.');

-- Insert Discounts
INSERT INTO `discounts` (discount_id, discount_percentage, discount_amount, dicount_desc, discount_expiry, discount_updated) VALUES
(1, 10, 5.00, '10% Off on Pet Food', '2025-12-31 23:59:59', NOW()),
(2, 20, 10.00, '20% Off on Toys', '2025-11-30 23:59:59', NOW());

-- Insert Products
INSERT INTO `product_details` (product_id, product_name, product_desc, unit_price, manufacturer_id, current_discount) VALUES
(1, 'Dog Food', 'Premium dog food', 50.00, 1, 1),
(2, 'Cat Toy', 'Interactive toy for cats', 20.00, 2, 2);

-- Insert Product Types
INSERT INTO `products` (product_id, type_name) VALUES
(1, 'Food'),
(2, 'Toy');

-- Insert Images
INSERT INTO `images` (img_id, img_url, product_id, user_id, discount_id) VALUES
(1, 'https://example.com/dogfood.jpg', 1, NULL, 1),
(2, 'https://example.com/cattoy.jpg', 2, NULL, 2);

-- Insert Orders
INSERT INTO `orders` (order_id, total_price, user_id, quantity, purchased, order_updated) VALUES
(1, 45.00, 2, '1', 1, '2025-03-12 16:46:53');

-- Insert Order Products
INSERT INTO `order_prod` (product_id, type_name, order_id, subQuantity, subPrice, applied_discount) VALUES
(1, 'Food', 1, 1, 45.00, 1);

-- Insert Pets
INSERT INTO `pets` (pet_id, pet_name) VALUES
(1, 'Dog'),
(2, 'Cat');

-- Insert Product-Pet Relationship
INSERT INTO `prod_pet` (product_id, pet_id) VALUES
(1, 1),
(2, 2);

-- Insert Favorites
INSERT INTO `favorites` (user_id, product_id) VALUES
(2, 1), -- Customer likes Dog Food
(2, 2); -- Customer likes Cat Toy