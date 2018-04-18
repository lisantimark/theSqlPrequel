

drop trigger if exists decrease_qty;

drop view if exists customers_cv;
drop view if exists admin_cv;

drop table if exists odetails;
drop table if exists products;
drop table if exists orders;
drop table if exists customers;
drop table if exists admins;
drop table if exists developers;

create table developers (
d_id numeric(6,0),
name varchar(30),
industry varchar(25),
phone numeric(10,0),
primary key(d_id)
) ENGINE=InnoDB;

create table customers(
c_id int(6) AUTO_INCREMENT,
email varchar(30) UNIQUE,
password varchar(30),
name varchar(30),
address varchar(50),
phone numeric(10, 0) UNIQUE,
c_card numeric(16, 0) UNIQUE,
expdate date,
secCode numeric(3,0),
zipcode numeric(5, 0),
primary key (c_id)
) ENGINE=InnoDB;

create view customers_cv as
select email, name, phone, zipcode
from customers;

create table admins(
a_id numeric(6,0),
email varchar(30),
password varchar(30),
name varchar(30),
phone numeric(10, 0) UNIQUE,
rating numeric(1, 0),
primary key (a_id)
) ENGINE=InnoDB;

create view admin_cv as
select email, name, phone, rating
from admins;

create table orders (
o_id int(4) AUTO_INCREMENT,
c_id int(6),
received date,
shipped date,
primary key (o_id),
foreign key (c_id) references customers(c_id)  on delete cascade
) ENGINE=InnoDB;

create table products (
p_id varchar(10),
format varchar(10),
pname varchar(30),
category varchar(25),
d_id numeric(6),
version numeric(3,1),
price numeric(6,2),
rating numeric(1),
released date,
qoh numeric(3,0) check (qoh > 0),
primary key(p_id,format),
foreign key (d_id) references developers(d_id) on delete cascade
) ENGINE=InnoDB;

create table odetails (
o_id int(4),
p_id varchar(10),
format varchar(10),
qty numeric(4,0) check (qty > 0),
primary key(o_id, p_id, format),
foreign key (o_id) references orders(o_id) on delete cascade,
foreign key (p_id) references products(p_id) on delete cascade)
 ENGINE=InnoDB;

delete from developers;
delete from customers;
delete from admins;
delete from orders;
delete from products;
delete from odetails;

insert into admins values(105062, "smithjay94@hotmail.com", "secure123", "Jay Smith", 9103451122, 4);
insert into admins values(101405, "markalisanti@yahoo.com", "CoolBeans2", "Mark Lisanti", 9195763316, 5);
insert into admins values(107662, "AlHitch@gmail.com", "PassWord61", "Alfred Hitchcock", 9102222344, 2);
insert into admins values(305538, "GeorgeL@gmail.com", "GLP314", "George Lopez", 2428581447, 5);

insert into developers values(112233, "Sony", "Editing", null);
insert into developers values(102030, "Adobe", "Editing", "8008336687");
insert into developers values(214151, "JetBrains", "IDE", "8123801641");
insert into developers values(213156, "Autodesk", "3D/CAD", "8553019562");
insert into developers values(569832, "Cisco", "Enterprise", "8005536387");

insert into products values("1970755369", "Digital", "Photoshop", "Editing", 102030, 6.1, 119.88, 3, "2013-06-17", 53);
insert into products values("3970755369", "Physical", "Photoshop", "Editing", 102030, 6.1, 119.88, 4, "2013-06-17", 19);
insert into products values("1970756697", "Digital", "Illustrator", "Editing", 102030, 6.1, 119.88, 5, "2013-06-17", 44);
insert into products values("3970756698", "Physical", "Illustrator", "Editing", 102030, 6.1, 119.88, 5, "2013-06-17", 33);
insert into products values("7970756699", "Digital", "InDesign", "Editing", 102030, 6.1, 119.88, 5, "2013-06-17", 95);
insert into products values("3970756610", "Physical", "InDesign", "Editing", 102030, 6.1, 119.88, 4, "2013-06-17", 93);
insert into products values("1970756699", "Digital", "After Effects", "Editing", 102030, 6.1, 119.88, 5, "2013-06-17", 352);
insert into products values("6420773391", "Physical", "Vegas Pro", "Editing", 112233, 15, 799.00, 5, "2011-10-17", 11);
insert into products values("6420773392", "Physical", "Sound Forge", "Editing", 112233, 15, 798.95, 5, "2016-05-20", 30);
insert into products values("1898524567", "Digital", "IntellJ", "IDE", 214151, 3.5, 499.00, 5, "2001-01-01", 90);
insert into products values("7898524568", "Digital", "Pycharm", "IDE", 214151, 3.7, 199.00, 4, "2015-11-01", 67);
insert into products values("7898524569", "Digital", "WebStorm", "IDE", 214151, 3.9, 129.00, 2, "2017-03-20", 35);
insert into products values("7898524570", "Digital", "RubyMine", "IDE", 214151, 4.1, 199.00, 5, "2016-03-31", 103);
insert into products values("7898524571", "Digital", "DataGrip", "IDE", 214151, 2.5, 199.00, 5, "2016-08-06", 111);
insert into products values("1624882649", "Digital", "Autocad", "3D/CAD", 213156, 3.7, 1470.00, 5, "2015-03-23", 77);
insert into products values("5624882649", "Physical", "Autocad", "3D/CAD", 213156, 3.7, 1470.00, 5, "2015-03-23", 12);
insert into products values("1648828898", "Digital", "Maya", "3D/CAD", 213156, 5.0, 1470.00, 5, "2017-11-20", 4);
insert into products values("5648828898", "Physical", "Maya", "3D/CAD", 213156, 5.0, 1470.00, 5, "2017-11-20", 15);
insert into products values("1648828474", "Digital", "3ds Max", "3D/CAD", 213156, 4.3, 1470.00, 5, "2016-07-15", 11);
insert into products values("5648828474", "Physical", "3ds Max", "3D/CAD", 213156, 4.3, 1470.00, 5, "2016-07-15", 26);
insert into products values("6369634565", "Digital", "Webex", "Enterprise", 569832, 1.4, 240.00, 3, "2015-11-09", 928);
insert into products values("6369634587", "Digital", "Jabber", "Enterprise", 569832, 1.3, 0.00, 2, "2017-11-13", 765);

insert into customers values(100001, "lisanmark@gmail.com", "Dog123", "Mark Lisanti", "1818 Fordham Road", "9198026745", 1234566543211596, "2018-02-14", 333, 28403);
insert into customers values(100002, "saroberts@yahoo.com", "roBlo99", "Sheila Roberts", "71 Pilgrim Avenue", "2269062721", 4875333041322847, "2019-03-05", 414, 27612);
insert into customers values(100003, "bcarr2002@hotmail.com", "coolstuff!", "Brandon Carr", "123 6th Street", "6719251352", 0575398028127637, "2020-11-02", 871, 36122);
insert into customers values(100004, "LambCharlotte@gmail.com", "1wQ241s", "Charlotte Lamb", "316 Glendale Street", "8351907669", 9486635072061821, "2019-11-02", 551, 80831);

insert into orders values(1111, 100001, "2017-10-14", "2017-10-14");
insert into orders values(1112, 100001, "2017-11-24", "2017-11-26");
insert into orders values(1001, 100002, "2015-09-13", "2015-09-13");
insert into orders values(1114, 100003, "2013-06-05", "2013-06-06");
insert into orders values(1115, 100003, "2016-04-26", "2016-04-29");
insert into orders values(1116, 100004, "2018-01-01", "2018-01-05");
insert into orders values(1117, 100004, "2017-11-05", null);

insert into odetails values(1111, "1970755369", "Digital", 1);
insert into odetails values(1112, "3970755369", "Physical", 1);
insert into odetails values(1112, "6420773391", "Physical", 3);
insert into odetails values(1001, "6369634587", "Digital", 25);
insert into odetails values(1001, "6369634565", "Digital", 25);
insert into odetails values(1114, "1898524567", "Digital", 1);
insert into odetails values(1114, "7898524570", "Digital", 1);
insert into odetails values(1114, "7898524569", "Digital", 1);
insert into odetails values(1114, "5648828898", "Digital", 1);
insert into odetails values(1115, "3970756698", "Physical", 1);
insert into odetails values(1115, "1970756699", "Digital", 1);
insert into odetails values(1116, "5648828474", "Physical", 10);
insert into odetails values(1117, "6369634565", "Digital", 350);

delimiter $$
create trigger decrease_qty
    before insert on odetails
    for each row
begin
    update products
    set products.qoh = products.qoh - NEW.qty
    where products.p_id = NEW.p_id;
end
$$

drop procedure IF EXISTS deleteCust$$
CREATE PROCEDURE deleteCust(in TEST varchar(30))
BEGIN
   DELETE FROM customers WHERE email = TEST;
END $$ 

drop procedure IF EXISTS updateTrans$$
CREATE PROCEDURE updateTrans(in id int(4), in p varchar(10), in format varchar(10), in q numeric(4,0))
BEGIN
IF EXISTS (select p_id from odetails where o_id = id and p_id = p)THEN
	update odetails set qty = qty + q where p_id = p and o_id = id;
ELSE
	insert into odetails values(id, p, format, q);
END IF;
END $$


drop procedure IF EXISTS newAccount$$
CREATE PROCEDURE newAccount(in email varchar(30), in password varchar(30), in name varchar(30), in address varchar(50),
in phone numeric(10, 0), in c_card numeric(16, 0), in expdate date, in secCode numeric(3,0), 
in zipcode numeric(5, 0))
BEGIN
	insert into customers values(null, email, password, name, address, phone, c_card, expdate, secCode, zipcode);
END $$

drop procedure IF EXISTS newOrder$$
CREATE PROCEDURE newOrder(in c_id int(6),
in received date)
BEGIN
	insert into orders values(null, c_id, received, null);
END $$


drop procedure IF EXISTS sendOrder$$
CREATE PROCEDURE sendOrder(in id int(4))
BEGIN
	update orders set shipped = Cast(NOW() as date) where o_id = id;
END $$


drop function IF EXISTS checkUser$$
CREATE FUNCTION checkUser (n varchar(30), p varchar(30))
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE test INT;
	IF EXISTS (select email, password from customers where email = n and password = p)THEN
		SET test = 1;
	ELSE
		SET test = 0;	
END IF;
RETURN test;
END $$

drop function IF EXISTS checkAdmin$$
CREATE FUNCTION checkAdmin(n varchar(30), p varchar(30))
RETURNS INT
DETERMINISTIC
BEGIN
	DECLARE test INT;
	IF EXISTS (select email, password from admins where email = n and password = p)THEN
		SET test = 1;
	ELSE
		SET test = 0;	
END IF;
RETURN test;
END $$



drop procedure IF EXISTS viewOrder$$
CREATE PROCEDURE viewOrder(in id int(4))
BEGIN
	select result3.name, result3.address, result3.product, result3.format, result3.price, result3.qty, result3.qoh from
	(select customers.name, customers.address, result2.* from 
	(select result1.c_id, result1.o_id, pname as product, format, price, result1.qty, qoh from products join
	(select orders.o_id, p_id, c_id, qty from odetails join orders where orders.o_id = odetails.o_id) as result1
	where result1.p_id = products.p_id) as result2 join
	customers where customers.c_id = result2.c_id) as result3
	where result3.o_id = id;
END $$ 

drop procedure IF EXISTS compileHistory$$
CREATE PROCEDURE compileHistory(in id int(6))
BEGIN
  	select o_id, p_id, received from orders natural join odetails
  	where c_id =id order by shipped desc;
END $$

drop procedure IF EXISTS search_results;


CREATE PROCEDURE search_results(in i_dev varchar(30), in i_cat varchar(30), in i_format varchar(8), 
in min_date date, in max_date date, in i_rating numeric(2,1), in min_price numeric(7, 2), in max_price numeric(7, 2))
BEGIN
/* collect all data that satisfies comparisons */
SELECT p_id, pname, name, results_1.category as pcat, price, format, rating
FROM (select * from products where price <= max_price and price >= max_price
      and rating > 1 and released >= min_date and released <= max_date) as results_1 
	natural join developers as results_2
      /* take the set difference from table of any data that does not match strings */
      where p_id not in (select p_id from products natural join developers
      where format != i_format or category != i_cat)
      and name not in (select name from developers where name != i_dev)
      and i_format != "none" and i_cat != "none" and i_dev != "none"
      order by rating desc;

END $$

delimiter ;

CALL search_results('Adobe', 'Editing', 'Physical', '1994-01-01', '2018-01-01', 5, 0.00, 1000.00);








#QUERIES
#Top 3 sold products
select products.name Product_name, sum(qty) as Total_Sold from products, odetails 
where products.p_id=odetails.p_id 
group by name 
order by sum(qty) 
desc limit 3;

#Customers who have outstanding order
select name, orders.o_id, orders.received 
from customers join orders on customers.c_id = orders.c_id 
where shipped is null;


#Year old orders already shipped, also delete
select o_id, shipped  from orders 
where year(received) < year(curdate()) and shipped is not null;




















