--Tables
CREATE TABLE Designation(
	designationID int NOT NULL PRIMARY KEY IDENTITY(1,1),
	designationName varchar(255),
	designationSalary money
);

CREATE TABLE Employee (
    employeeID int NOT NULL PRIMARY KEY IDENTITY(1,1),
    lastName varchar(255),
    firstName varchar(255),
    address varchar(255),
    city varchar(255),
	phoneNo varchar(10),
	designationID int FOREIGN KEY REFERENCES Designation(designationID),                                                                                                                                                                  
);

Create Table Reciepes(
	id int NOT NULL PRIMARY KEY IDENTITY(1,1),
	employeeID int FOREIGN KEY REFERENCES Employee(employeeID),  
	reciepeName varchar(255),
	ingredients xml,
	steps xml
);

--Insert Data from XML file to table
insert into Reciepes
Select
A.reciepe.query('employeeID').value('.','int') as ChiefID,
A.reciepe.query('reciepeName').value('.','varchar(50)') as reciepeName,
A.reciepe.query('ingredients/ingredient') as ingredients,
A.reciepe.query('steps/step') as steps

from 
(
select cast(r as xml) from
openrowset(bulk 'C:\Users\SARVESH\Documents\Documents\Advance Database\RTMS\Reciepe.xml', single_blob) as R(r)
) as S(r)

cross apply r.nodes('chefSpecialReciepes/reciepe') as A(reciepe)



--Views

Create or Alter View Employee_Details as Select e.employeeID,e.firstName, e.lastName, e.address,e.city ,e.phoneNo,d.designationSalary, d.designationName from Employee e INNER JOIN Designation d ON e.designationID = d.designationID;



--Employee Section

CREATE or ALTER PROCEDURE addEmployee @firstName varchar(255), @lastName varchar(255), @address varchar(255), @city varchar(255), @designationID int, @phoneNo varchar(10)
AS
insert into Employee(firstName, lastName, address, city, designationID , phoneNo) values (@firstName,@lastName,@address,@city,@designationID, @phoneNo);

CREATE or ALTER PROCEDURE getEmployees
AS
Select employeeID, firstName,lastName,address,city,phoneNo,designationName from Employee_Details;

Create or Alter procedure getEmployeeByID @id int
AS
Select * from Employee_Details where employeeID = @id

CREATE or ALTER PROCEDURE updateEmployee @employeeID int, @firstName varchar(255), @lastName varchar(255), @address varchar(255), @city varchar(255), @phoneNo varchar(10)
AS
update Employee set firstName=@firstName, lastName=@lastName, address=@address, city=@city, phoneNo= @phoneNo  where employeeID = @employeeID;

CREATE or ALTER PROCEDURE deleteEmployee @employeeID int
AS
delete from Employee where employeeID=@employeeID


--Designation Section

CREATE or ALTER PROCEDURE addDesignation @designationName varchar(255), @designationSalary money
AS
insert into Designation(designationName, designationSalary) values (@designationName,@designationSalary);

CREATE or ALTER PROCEDURE getDesignation
AS
Select * from Designation;

CREATE or ALTER PROCEDURE changeSalary @designationID int, @designationSalary money
AS
update Designation set designationSalary=@designationSalary where designationID = @designationID;


CREATE or ALTER PROCEDURE deleteDesignation @designationID int
AS
delete from Designation where designationID=@designationID;


--Reciepes

CREATE or ALTER PROCEDURE getReciepesXml
AS
Select r.reciepeName,r.employeeID,r.ingredients.query('ingredient') as ingridients, r.steps.query('step') as steps from Reciepes r;

exec getReciepesXml;

CREATE or ALTER PROCEDURE getReciepes
AS
Select r.reciepeName,r.employeeID,r.ingredients.query('ingredient').value('.','varchar(500)') as ingridients, r.steps.query('step').value('.','varchar(500)') as steps from Reciepes r;

exec getReciepes;