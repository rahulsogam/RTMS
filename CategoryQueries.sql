Create Table foodCategory(
    categoryId int NOT NULL IDENTITY(1,1) ,
    categoryName varchar(255) NOT NULL,
     PRIMARY KEY (categoryId)
);


CREATE or Alter PROCEDURE getAllCategories
AS
SELECT * FROM foodCategory;

CREATE or Alter PROCEDURE addCategory @categoryName nvarchar(30)
AS 
insert into foodCategory (categoryName) values (@categoryName)
;
