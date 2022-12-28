Create Table foodCategory(
    categoryId int NOT NULL IDENTITY(1,1) ,
    categoryName varchar(255) NOT NULL,
     PRIMARY KEY (categoryId)
);

Create Table foodItem(
    itemId int NOT NULL IDENTITY(1,1) ,
    itemName varchar(255) NOT NULL,
	categoryId int NOT NULL,
	PRIMARY KEY (itemId),
	FOREIGN KEY (categoryId) REFERENCES foodCategory (categoryId)
);


CREATE Table inventoryOrders (
	 orderId int NOT NULL IDENTITY(1,1) ,
	 inventoryId int NOT NULL,
	 orderQuantity int NOT NULL,
	 orderStatus int NOT NULL, 
	 orderDate date NOT NULL,
	PRIMARY KEY (orderId),
	FOREIGN KEY (inventoryId) REFERENCES Inventory (inventoryId),
	FOREIGN KEY (orderStatus) REFERENCES orderStatus (statusNo)
);

Create Table orderStatus (
	statusId int NOT NULL IDENTITY(1,1),
	statusType varchar(20) NOT NULL,
	statusNo int NOT NULL,
	PRIMARY KEY (statusNo),
)
-------------------------------------------------------------------------------------------------------------------------------
--get food category
CREATE or Alter PROCEDURE getAllCategories AS
SELECT * FROM foodCategory
;
--Insert food category
CREATE or Alter PROCEDURE addCategory @categoryName nvarchar(30)
AS 
insert into foodCategory (categoryName) values (@categoryName)
;

-- get foodItem Data table
CREATE or Alter PROCEDURE getFoodItems
AS
SELECT i.*,c.categoryName
FROM foodCategory c,foodItem i 
where i.categoryId=c.categoryId ;

--addFooditem data 
CREATE or Alter PROCEDURE addItem (@itemName nvarchar(30),@categoryId int)
AS 
insert into foodItem (itemName,categoryId) values (@itemName,@categoryId)
;

--add Inventory
CREATE or Alter PROCEDURE addInventory(@inventoryName nvarchar(30),@availabelQuantity int)
AS
insert into Inventory (inventoryName,availabelQuantity) values (@inventoryName,@availabelQuantity);

--GET order detailsby order Status
CREATE or Alter PROCEDURE getOrderDetailsByStatus(@STATUS int)
AS
select inventoryName,status,orderDate 
from orderData 
group by status,inventoryName,orderDate,statusID
having statusID=@STATUS ;

EXEC getOrderDetailsByStatus @STATUS=0;


-- tirgger to order the inventory if low 
Create or ALTER TRIGGER orderInventory
	On Inventory
	AFTER Update 
AS
	declare @qty int
	declare @Count int
	declare @inventoryID int
	declare @status int
	
	select @inventoryID = inventoryId from inserted
	select @qty = availabelQuantity from inserted
	select @Count=(select count(orderStatus) from inventoryOrders 
					where orderStatus=0 and inventoryId=@inventoryID )
	select @status=(select orderStatus from inventoryOrders where inventoryId=@inventoryID )
	if(@Count=0)
	BEGIN
		if @qty <= 50 AND @status != 0 
			BEGIN
				print'Qty '+CAST(@qty as varchar(10))
				insert into inventoryOrders values (@inventoryID,100,0,GETDATE() )
			END;
	END;


--View For  food item-itemqty and order details
CREATE or ALTER VIEW orderData
AS
select i.inventoryName,i.availabelQuantity,
	   f.itemName,
	   o.orderQuantity,o.orderStatus as statusID,os.statusType as status,o.orderDate  
	from Inventory i 
    INNER JOIN inventoryOrders o
    ON o.inventoryId = i.inventoryId
	Inner Join foodItem f 
	ON i.itemId=f.itemId
	Inner Join  orderStatus os
	ON o.orderStatus=os.statusNo
GO


select * from orderData;