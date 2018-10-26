drop function if exists searchReaderById;

delimiter $$

create function searchReaderById(searchedId int) returns varchar(60)
  begin
    declare result varchar(60) default '';
    declare name varchar(20) default '';
    declare lastaname varchar(38) default '';

    if searchedId <= 0 then
      set result = 'Searched Id can not be equal or less then 0';
    else
      select FIRSTNAME from readers where READER_ID = searchedId into name;
      select LASTNAME from readers where READER_ID = searchedId into lastaname;
      set result = concat(name, ' ', lastaname);
    end if;
    return result;
  end $$

delimiter ;