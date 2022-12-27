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
	designationID int FOREIGN KEY REFERENCES Designation(designationID),                                                                                                                                                                  
);


--Designation Section
CREATE or ALTER PROCEDURE addDesignation @designationName varchar(255), @designationSalary money
AS
insert into Designation(designationName, designationSalary) values (@designationName,@designationSalary);


CREATE or ALTER PROCEDURE changeSalary @designationID int, @designationSalary money
AS
update Designation set designationSalary=@designationSalary where designationID = @designationID;


CREATE or ALTER PROCEDURE deleteDesignation @designationID int
AS
delete from Designation where designationID=@designationID