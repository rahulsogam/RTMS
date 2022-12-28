CREATE TABLE customers(
	cust_id bigint,
	cust_name nvarchar(50),
	cust_email nvarchar(50),
	cust_no bigint,
	order_id int
	PRIMARY KEY (cust_id)
)

CREATE TABLE orders(
order_id int,
cust_id bigint,
date date,
itemName nvarchar(50),
itemId int,
categoryId int,
total_cost int,
payment_method nvarchar(50)
PRIMARY KEY (order_id)
FOREIGN KEY (cust_id) REFERENCES customers(cust_id),
FOREIGN KEY (itemId) REFERENCES foodItem(itemId),
FOREIGN KEY (categoryId) REFERENCES foodCategory(categoryId)
);

Create Table foodItem(
    itemId int NOT NULL IDENTITY(1,1) ,
    itemName varchar(255) NOT NULL,
    categoryId int NOT NULL,
    PRIMARY KEY (itemId),
    FOREIGN KEY (categoryId) REFERENCES foodCategory (categoryId)
);


CREATE TABLE foodCategory(
categoryId int,
categoryName varchar(255)
PRIMARY KEY (categoryId)
);

ALTER TABLE foodItem
ALTER COLUMN itemType varchar;

--INSERT INTO customers(cust_id,cust_name,cust_email,cust_no,order_id) VALUES----

--INSERT INTO orders(order_id,cust_id,date,items, categoryId, total_cost, payment_method) VALUES

Create or alter procedure addCustomer(@cust_id bigint , @cust_name nvarchar(50),
@cust_email nvarchar(60),@cust_no bigint,@order_id int)
AS
insert into customers (cust_id,cust_name ,cust_email,cust_no,order_id) 
values (@cust_id,@cust_name,@cust_email,@cust_no,@order_id);

EXEC addCustomer
006,Sagar,'Sagar7@gmail.com',1234567896,996


select * from customers;


DELETE FROM customers WHERE cust_id=2;


Create or alter procedure getCustomerbyID(@cust_id bigint )
AS
select * from customers where cust_id=@cust_id  ;

Create or alter procedure getCustomers
AS
select * from customers;


Create or alter procedure addOrder(@order_id int,@cust_id bigint, @date1 date,@itemName nvarchar(50),@itemId int, @categoryId int,@total_cost int, @payment_method nvarchar(50))
AS
INSERT INTO orders(order_id,cust_id,date,itemName,itemId,categoryId,total_cost,payment_method)
VALUES (@order_id,@cust_id,@date1,@itemName,@itemId,@categoryId,@total_cost,@payment_method);



select* from orders;

Create or alter procedure getOderbyID(@order_id int)
AS
select * from orders where order_id=@order_id  ;

Create or alter procedure getOrder
AS
select * from orders;
select * from Inventory;
select * from foodItem;
select * from foodCategory;

CREATE Table Inventory (
     inventoryId int NOT NULL IDENTITY(1,1) ,
     inventoryName varchar(255) NOT NULL,
     availableQuantity varchar(255) NOT NULL,
	 itemId int,
    PRIMARY KEY (inventoryId),
);

ALter Table Inventory
    Add  itemId int,
    FOREIGN KEY(itemId) REFERENCES foodItem(itemId);
;

Create or alter procedure addInventory
AS
INSERT INTO Inventory( inventoryName,availableQuantity,itemId) values
('Pizza Dough',30,1),
('Burger Bread',30,2),
('Drink Can',30,6),
('Ice-Cream Packs',30,3),
('Cookie',30,5),
('Fries',30,4);

CREATE or Alter PROCEDURE addItem (@itemName nvarchar(30),@categoryId int)
AS 
insert into foodItem (itemName,categoryId) values (@itemName,@categoryId)
;



CREATE or Alter PROCEDURE addCategory @categoryName nvarchar(30)
AS 
insert into foodCategory (categoryName) values (@categoryName)
;

select * from foodCategory;

CREATE OR ALTER PROCEDURE addfoodCategory(@categoryId int, @categoryName varchar(255))
AS 
INSERT INTO foodCategory(categoryId,categoryName)
VALUES (@categoryId,@categoryName)


EXEC addfoodCategory
5,'Starter'




CREATE OR ALTER PROCEDURE addfooditem (@itemName varchar(255),@categoryId int)
AS 
INSERT INTO foodItem(itemName,categoryId)
VALUES (@itemName,@categoryId)


EXEC addfooditem
'Coke',2

select * from foodItem;


CREATE OR ALTER PROCEDURE addInventory( @inventoryName varchar(255),@availableQuantity varchar(255), @itemId int)
AS 
INSERT INTO Inventory(inventoryName,availableQuantity,itemId)
VALUES (@inventoryName,@availableQuantity,@itemId)

select* from Inventory;

EXEC addInventory
'Fries',60,4




CREATE or ALTER TRIGGER orderInventory
    On orders
    After Insert 
AS
    declare @qty int
    declare @oId int
	declare @iId int
    
	select @oId = itemId from inserted
	select @qty = (select availableQuantity from Inventory where itemId=@oId)
	print 'HERE '+CAST(@qty as varchar(20))
	
	SET @qty=@qty-1
	Update inventory set availableQuantity=@qty where itemId=@oId
	;


EXEC addOrder
998,006,'2022-12-23','Coke',6,2,2.50,'card'



select * from customers;
select * from Inventory; --inventoryId,availableQuantity
select * from orders     --inventoryId,

Create OR ALTER View CustomerChoice
AS
select c.cust_name,o.itemName,o.date,o.total_cost from customers c 
Inner JOIN Orders o
ON c.cust_id=o.cust_id
;


select * from CustomerChoice