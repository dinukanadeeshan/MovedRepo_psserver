drop database ps;
create database ps;
use ps;
create table category(
	categoryId varchar(10) not null primary key,
	description varchar(200) not null
);

create table item(
	categoryId varchar(10) not null,
	barcode varchar(50) not null,
	description varchar(200) not null,
	reOrderLevel decimal(10,2) null,
	constraint primary key(barcode),
	constraint foreign key(categoryId) references category(categoryId)
);

create table batchItem(
	barcode varchar(50) not null,
	itemCode varchar(200) not null,
	batchNo varchar(100) not null,
	qtyOnHand decimal(10,2) not null,
	expDate date,
	unitPrice decimal(10,2) not null,
	constraint primary key(itemCode),
	constraint foreign key(barcode) references item(barcode) on delete cascade on update cascade
);

create table customer(
	custId varchar(50) not null primary key,
	name varchar(200) not null,
	address varchar(500) not null
);

create table orders(
	custId varchar(50) not null ,
	orderId varchar(100) not null primary key,
	orderDate date not null,
	balance decimal(10,2) not null,
	totalAmount decimal(10,2) not null,
	discount decimal(10,2) not null,
	constraint foreign key(custId) references customer(custId) 
);

create table orderDetail(
	itemCode varchar(200) not null,
	orderId varchar(100) not null,
	qty decimal(10,2) not null,
	unitPrice decimal(10,2) not null,
	constraint primary key(itemCode,orderId),
	constraint foreign key(itemCode) references batchItem(itemCode) on delete cascade on update cascade,
	constraint foreign key(orderId) references orders(orderId) on delete cascade on update cascade
);

create table payment(
	orderId varchar(100) not null ,
	paymentId varchar(300) not null primary key,
	amount decimal(10,2) not null,
	paymentDate date not null,
	constraint foreign key (orderId) references orders(orderId) on delete cascade on update cascade
);

create table user(
	userName varchar(300) not null primary key,
	password varchar(300) not null,
	privilage int not null
);

insert into category values('CT000', 'Others');
insert into category values('CT001', 'Drug - Tablets');
insert into category values('CT002', 'Drug - Capsules');

insert into item values('CT001','100002', 'Panadol', 200);
insert into item values('CT002','100032', 'Amoxiline', 200);
insert into item values('CT001','100004', 'Citrezine', 200);

insert into customer values('C0000', 'Default', '-');
