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

Create Table Attendance(
	attendanceID int NOT NULL PRIMARY KEY IDENTITY(1,1),
	attendanceDate date NOT NULL DEFAULT (GETDATE()),
	employeeID int FOREIGN KEY REFERENCES Employee(employeeID),
	attendanceStatus varchar(1),
	punchInTime time,
	punchOutTime time,
	totalHours int
)

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

exec addEmployee @firstName = 'Sarvesh' , @lastName = 'Bhosale', @address = 'The Hardwicke', @city = 'Dublin', @designationID = 1, @phoneNo = 0899756487

CREATE or ALTER PROCEDURE getEmployees
AS
Select employeeID, firstName,lastName,address,city,phoneNo,designationName from Employee_Details;

exec getEmployees

Create or Alter procedure getEmployeeByID @id int
AS
Select * from Employee_Details where employeeID = @id

exec getEmployeeByID @id=1

CREATE or ALTER PROCEDURE updateEmployee @employeeID int, @firstName varchar(255), @lastName varchar(255), @address varchar(255), @city varchar(255), @phoneNo varchar(10)
AS
update Employee set firstName=@firstName, lastName=@lastName, address=@address, city=@city, phoneNo= @phoneNo  where employeeID = @employeeID;

exec updateEmployee @employeeID=8, @firstName = 'Sarvesh' , @lastName = 'Bhosale', @address = 'The Hardwicke', @city = 'Dublin', @phoneNo = 0899756487

CREATE or ALTER PROCEDURE deleteEmployee @employeeID int
AS
delete from Employee where employeeID=@employeeID

exec deleteEmployee @employeeID = 8

--Designation Section

CREATE or ALTER PROCEDURE addDesignation @designationName varchar(255), @designationSalary money
AS
insert into Designation(designationName, designationSalary) values (@designationName,@designationSalary);

exec addDesignation @designationName = 'Manager', @designationSalary = 20000

CREATE or ALTER PROCEDURE getDesignation
AS
Select * from Designation;

exec getDesignation 

CREATE or ALTER PROCEDURE changeSalary @designationID int, @designationSalary money
AS
update Designation set designationSalary=@designationSalary where designationID = @designationID;

exec changeSalary @designationID = 1, @designationSalary = 20000

CREATE or ALTER PROCEDURE deleteDesignation @designationID int
AS
delete from Designation where designationID=@designationID;

exec deleteDesignation @designationID=3

--Reciepes

CREATE or ALTER PROCEDURE getReciepesXml
AS
Select r.reciepeName,r.employeeID,r.ingredients.query('ingredient') as ingridients, r.steps.query('step') as steps from Reciepes r;

exec getReciepesXml;

CREATE or ALTER PROCEDURE getReciepes
AS
Select r.reciepeName,r.employeeID,r.ingredients.query('ingredient').value('.','varchar(500)') as ingridients, r.steps.query('step').value('.','varchar(500)') as steps from Reciepes r;

exec getReciepes;


--Attendance

CREATE or ALTER PROCEDURE addPunchin @employeeID int 
AS
Insert into Attendance(employeeID,attendanceStatus,punchInTime) values( @employeeID,'p',GETDATE());

exec addPunchin @employeeID = 3;


Create or Alter Procedure addPunchout @attendanceID int
As
update Attendance set punchOutTime = GETDATE() where attendanceID = @attendanceID;

exec addPunchout @attendanceID = 3;

Create or Alter Trigger punchout on Attendance
AFTER Update 
AS
declare @punchInTime time
declare @punchOutTime time
declare @attendanceID int

select @attendanceID = attendanceID from inserted;
select @punchOutTime = punchOutTime from inserted;
select @punchInTime = punchInTime from inserted;
print('reached')
update Attendance set totalHours = dbo.getTotalHours(@punchInTime,@punchOutTime) where attendanceID = @attendanceID




Create or Alter function getTotalHours(@punchInTime as time,@punchOutTime as time)
returns int
AS
BEGIN
	DECLARE @totalHours as int
	set @totalHours = DateDiff(hour, @punchInTime, @punchOutTime)
	return @totalHours
END
