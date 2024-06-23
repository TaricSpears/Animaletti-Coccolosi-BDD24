//LOGIN: trova password utente e vedi se matcha con quella inserita
select u.password
from utente u 
where u.email = email

//REGISTRA NUOVO UTENTE
insert into utente (email, password, nome, cognome, data_nascita)
values (email, password, nome, cognome, data_nascita)

//verifica se utente è proprietario, veterinario o amministratore
select *
from proprietario p
where p.email = email

select *
from veterinario v
where v.email = email

select *
from amministratore a
where a.email = email


