create table BOOKS_STATS (
  ID int(11) not null  auto_increment primary key,
  RANGE_FROM char(4),
  RANGE_TO char(4),
  QUANTITY_FIRSTNAMES int(11),
  QUANTITY_LASTNAMES int(11)
);
commit;

create table READERS_STATS (
  ID int(11) not null  auto_increment primary key,
  RANGE_FROM char(4),
  RANGE_TO char(4),
  QUANTITY int(11)
);
commit;

drop procedure if exists CalcBooksAndReadersStats;

delimiter $$

create procedure CalcBooksAndReadersStats()
  begin
  declare counter int;
  declare secondCounter int;
  select count(*) from books where title between 'a' and 'f' into counter;
    insert into BOOKS_STATS (RANGE_FROM, RANGE_TO, QUANTITY)
    values ('a', 'f' , counter);

  select count(*) from books where title between 'g' and 'r' into counter;
    insert into BOOKS_STATS (RANGE_FROM, RANGE_TO, QUANTITY)
    values ('g', 'r' , counter);

  select count(*) from books where title between 's' and 'z' into counter;
    insert into BOOKS_STATS (RANGE_FROM, RANGE_TO, QUANTITY)
    values ('s', 'z' , counter);

  select count(*) from readers where LASTNAME between 'a' and 'f' into counter;
  select count(*) from readers where FIRSTNAME between 'a' and 'f' into secondCounter;
    insert into READERS_STATS (RANGE_FROM, RANGE_TO, QUANTITY_LASTNAMES, QUANTITY_FIRSTNAMES)
    values ('a', 'f' , counter, secondCounter);

  select count(*) from readers where LASTNAME between 'g' and 'r' into counter;
  select count(*) from readers where FIRSTNAME between 'a' and 'f' into secondCounter;
    insert into READERS_STATS (RANGE_FROM, RANGE_TO, QUANTITY_LASTNAMES, QUANTITY_FIRSTNAMES)
    values ('g', 'r' , counter, secondCounter);

  select count(*) from readers where LASTNAME between 's' and 'z' into counter;
    select count(*) from readers where FIRSTNAME between 's' and 'z' into secondCounter;
    insert into READERS_STATS (RANGE_FROM, RANGE_TO, QUANTITY_LASTNAMES, QUANTITY_FIRSTNAMES)
    values ('s', 'z' , counter, secondCounter);

  commit;
end $$

delimiter ;

call CalcBooksAndReadersStats();

explain select count(*) from readers where FIRSTNAME = 'John';;

create index BOOKS_TITLE_IDX ON books(TITLE);
create index READERS_FNLN_IDX ON readers(FIRSTNAME, LASTNAME);

explain select count(*) from readers where FIRSTNAME  = 'John';


