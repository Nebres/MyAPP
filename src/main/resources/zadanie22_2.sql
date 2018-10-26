drop function if exists BestsellerChecker;

delimiter $$

create function BestsellerChecker(rentCounter int) returns boolean
begin
	declare result boolean default false;
  if rentCounter > 2 then
		set result = true;
	end if;
	return result;
end $$

delimiter ;

delimiter $$

drop procedure if exists updateBestsellers;

delimiter $$

create procedure updateBestsellers()
begin
	declare rentCounter, currentBookId int;
  declare finished int default 0;
  declare all_Books cursor for select book_id from books;
  declare continue handler for not found set finished = 1;
	open all_Books;
		while(finished = 0) do fetch all_Books into currentBookId;
			if (finished = 0) then
				select count(*) from rents
			  where rent_date between curdate() - interval 30 day and curdate()
			  and book_id = currentBookId
			  into rentCounter;
			  update books set bestseller =  BestsellerChecker(rentCounter)
        	where book_id = currentBookId;
				commit;
			end if;
		end while;
	close All_Books;
end $$

delimiter ;

