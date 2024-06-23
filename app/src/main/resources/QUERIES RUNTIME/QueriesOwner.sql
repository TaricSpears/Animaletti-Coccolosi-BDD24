/*registra nuovo animale: inserire campi + visualizza elenco 
specie registrate + creazione cartella clinica 
dove puoi inserire condizioni cliniche
infine ricalcola peso medio della specie*/
inserisci campi
select *
from specie

insert into animale(nome,data_nascita,peso,email,app_nome)
values(nome,data_nascita,peso,email,app_nome)

select last_insert_id()
from animale

insert into cartella_clinica(id_animale,data_creazione)
values(id_animale,date())

select *
from condizione_clinica

ciclo per inserire condizioni cliniche
insert into assegnazione(id_condizione,id_animale)
values(id_condizione,id_animale)

/*ricalcolo peso medio specie*/

//visualizza generalita tuoi animali
select *
from animale
where email = email

