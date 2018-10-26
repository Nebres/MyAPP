create table STATS (
  STAT_ID int(11) auto_increment primary key,
  STAT_DATE datetime not null,
  STAT varchar(20) not null,
  VALUE int(11) not null
);

create view BESTSELLERS_COUNT as
select count(*) bestseller from books
where bestseller = true;

delimiter $$

drop event If exists BESTSELLER_COUNTER;

create event BESTSELLER_COUNTER
  on schedule every 1 minute
do
  begin
    declare result int;
    call updateBestsellers();
    select bestseller from BESTSELLERS_COUNT into result;
    INSERT INTO STATS (STAT_DATE, STAT, VALUE)
    VALUES (curtime(), "BESTSELLERS", result);
  end $$

delimiter ;