insert into AUTHOR(ID, FULL_NAME) values (1, 'Richard Bergman');
insert into AUTHOR(ID, FULL_NAME) values (2, 'Ivan Ivanov');
insert into AUTHOR(ID, FULL_NAME) values (3, 'John Snow');

insert into GENRE(ID, NAME) values (1, 'Mystic');
insert into GENRE(ID, NAME) values (2, 'Fantasy');

insert into BOOK(ID, NAME, AUTHOR_ID, GENRE_ID) values (1, 'Who are you?', 1, 1);
insert into BOOK(ID, NAME, AUTHOR_ID, GENRE_ID) values (2, '2020 is the past', 1, 2);
insert into BOOK(ID, NAME, AUTHOR_ID, GENRE_ID) values (3, 'Goblins parade', 2, 2);
insert into BOOK(ID, NAME, AUTHOR_ID, GENRE_ID) values (4, 'My life is done', 3, 1);

insert into COMMENT(ID, "VALUE", BOOK_ID) values (1, 'So cooolll', 1);
insert into COMMENT(ID, "VALUE", BOOK_ID) values (2, 'So cooolll 2', 1);