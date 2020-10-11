insert into note(id, title, content, version, modified, created) values (1, 'nazwa', 'zawartosc', 1, '2007-12-03', '2007-12-03');
insert into note(id, title, content, version, modified, created) values (2, 'nazwa2', 'zawartosc2', 1, '2007-12-03', '2007-12-03');
insert into note(id, title, content, version, modified, created) values (3, 'nazwa3', 'zawartosc3', 1, '2007-12-03', '2007-12-03');

insert into note_archive(id, note_id, title, content, version, modified, created) values (1, 3, 'nazwa3', 'zawartosc3ZmienionaPierwszy', 2, '2007-12-03', '2007-12-03');
insert into note_archive(id, note_id, title, content, version, modified, created) values (2, 3, 'nazwa3', 'zawartosc3ZmienionaDrugi', 3, '2007-12-03', '2007-12-03');
insert into note_archive(id, note_id, title, content, version, modified, created) values (3, 3, 'nazwa3ZmienionaPierwszy', 'zawartosc3ZmienionaDrugi', 4, '2007-12-03', '2007-12-03');
insert into note_archive(id, note_id, title, content, version, modified, created) values (4, 3, 'nazwa3ZmienionaDrugi', 'zawartosc3ZmienionaTrzeci', 5, '2007-12-03', '2007-12-03');
