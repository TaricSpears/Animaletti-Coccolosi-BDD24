insert into utente(Nome,Cognome,Email,Data_di_Nascita,Data_di_Iscrizione,Password)
	values('Pietro','Sbaraccani','pietrone03@gmail.com','2003-07-28','2024-06-01','i<3furries');

insert into utente(Nome,Cognome,Email,Data_di_Nascita,Data_di_Iscrizione,Password)
	values('Lorenzo','Bergami','lorenzino69@gmail.com','2003-08-24','2024-06-01','i<3dbb');

insert into utente(Nome,Cognome,Email,Data_di_Nascita,Data_di_Iscrizione,Password)
	values('Francesco','Bergami','franchino68@gmail.com','2003-08-24','2024-06-01','i<3dbb');

insert into utente(Nome,Cognome,Email,Data_di_Nascita,Data_di_Iscrizione,Password)
	values('Marco','Ambrogi','marcoambro3@gmail.com','2001-07-08','2024-06-10','mavfies');

insert into utente(Nome,Cognome,Email,Data_di_Nascita,Data_di_Iscrizione,Password)
	values('Pietro','Guidi','asghsa@gmail.com','1994-03-14','2024-06-17','dfsgdsg');
    
insert into utente(Nome,Cognome,Email,Data_di_Nascita,Data_di_Iscrizione,Password)
	values('Giovanni','Bianchi','giovabianchi@hotmail.com','2000-02-28','2024-06-18','spaiasg');

insert into utente(Nome,Cognome,Email,Data_di_Nascita,Data_di_Iscrizione,Password)
	values('Mauro','Astolfi','maurito@gmail.com','1982-07-20','2024-06-12','zmxnvow');
    
insert into utente(Nome,Cognome,Email,Data_di_Nascita,Data_di_Iscrizione,Password)
	values('Ambrogio','Rossi','redbro@gmail.com','2002-12-11','2024-06-11','qiorew');

insert into utente(Nome,Cognome,Email,Data_di_Nascita,Data_di_Iscrizione,Password)
	values('Luca','Verdi','luconexx@gmail.com','1995-03-20','2024-06-09','zxljvosif');
    
insert into utente(Nome,Cognome,Email,Data_di_Nascita,Data_di_Iscrizione,Password)
	values('Maurizio','Bianchi','mauritius@tiscali.com','1992-02-10','2024-06-06','aajwrnaewkj');

insert into utente(Nome,Cognome,Email,Data_di_Nascita,Data_di_Iscrizione,Password)
	values('Pierluigi','Guiducci','pierlozz@gmail.com','1998-05-15','2024-06-13','xvlkjbwf');

insert into proprietario(Email,Bloccato)
	values('lorenzino69@gmail.com',False);

insert into proprietario(Email,Bloccato)
	values('pietrone03@gmail.com',False);
    
insert into proprietario(Email,Bloccato)
	values('redbro@gmail.com',False);
    
insert into proprietario(Email,Bloccato)
	values('mauritius@tiscali.com',False);
    
insert into proprietario(Email,Bloccato)
	values('luconexx@gmail.com',False);
    
insert into amministratore(Email)
	values('lorenzino69@gmail.com');
    
insert into amministratore(Email)
	values('pietrone03@gmail.com');
    
insert into amministratore(Email)
	values('giovabianchi@hotmail.com');
    
insert into veterinario(Email,Curriculum)
	values('lorenzino69@gmail.com', 'Il miglior veterinario della zona, veramente il meglio che tu possa desiderare');
    
insert into veterinario(Email,Curriculum)
	values('pietrone03@gmail.com', 'Il miglior veterinario della zona, veramente il meglio che tu possa desiderare');
    
insert into veterinario(Email,Curriculum)
	values('pierlozz@gmail.com', 'Il peggior veterinario della zona. Non portare qui il tuo animale, lo dico per il suo bene');
    
insert into veterinario(Email,Curriculum)
	values('asghsa@gmail.com', 'Sono un bravo veterinario. Lavoro da 20 anni nel settore. Ho molta esperienza');
    
insert into gruppo(Nome,Data_apertura,Privato)
	values('Community','2024-06-01',False);
    
insert into partecipazione(ID_Gruppo,Email)
	values(1,'asghsa@gmail.com');
    
insert into partecipazione(ID_Gruppo,Email)
	values(1,'franchino68@gmail.com');

insert into partecipazione(ID_Gruppo,Email)
	values(1,'giovabianchi@hotmail.com');

insert into partecipazione(ID_Gruppo,Email)
	values(1,'lorenzino69@gmail.com');
    
insert into partecipazione(ID_Gruppo,Email)
	values(1,'luconexx@gmail.com');
    
insert into partecipazione(ID_Gruppo,Email)
	values(1,'marcoambro3@gmail.com');

insert into partecipazione(ID_Gruppo,Email)
	values(1,'mauritius@tiscali.com');
    
insert into partecipazione(ID_Gruppo,Email)
	values(1,'maurito@gmail.com');
    
insert into partecipazione(ID_Gruppo,Email)
	values(1,'pierlozz@gmail.com');

insert into partecipazione(ID_Gruppo,Email)
	values(1,'pietrone03@gmail.com');
    
insert into partecipazione(ID_Gruppo,Email)
	values(1,'redbro@gmail.com');
    
insert into specie(Nome,Descrizione)
values('Cane','Il tuo amico peloso a 4 zampe. Fedele.');

insert into specie(Nome,Descrizione)
values('Gatto','Palla di pelo da coccolare. Un po vanitoso');

insert into specie(Nome,Descrizione)
values('Coniglio','Lunghe orecchie e naso a punta. Salta come un coniglio.');

insert into specie(Nome,Descrizione)
values('Geco','Rettile tranquillo da compagnia. Amorevole.');

insert into specie(Nome,Descrizione)
values('Pesce Rosso','Nuotatore professionista. Sta un po sulle sue.');

insert into specie(Nome,Descrizione)
values('Lupo','Re della foresta. Non si fa addomesticare facilmente.');

insert into specie(Nome,Descrizione)
values('Serpente','Animale difficile da gestire. Fare attenzione a quelli velenosi.');

insert into specie(Nome,Descrizione)
values('Ragno','Animale non per tutti. Attenzione a non perderlo.');

insert into specie(Nome,Descrizione)
values('Papera','QUACK!');

insert into specie(Nome,Descrizione)
values('Pollo','Animale estremamente intelligente. Becco giallo. Piumaggio folto.');

insert into specie(Nome,Descrizione)
values('Pappagallo','Ripete tutto ciò che dici. Ripete tutto ciò che dici...');

insert into specie(Nome,Descrizione)
values('Giraffa','Animale stupendo. Serve un soffitto molto alto.');

insert into specie(Nome,Descrizione)
values('Cavallo','Galoppa come un cavallo! Puoi usarlo al posto della macchina.');

insert into specie(Nome,Descrizione)
values('Mucca','MUUUUUUUUUUUUUUUU!');

insert into specie(Nome,Descrizione)
values('Pecora','BEEEEEEEEEEEE!');

insert into specie(Nome,Descrizione)
values('Passerotto','CHIP CHIP');

insert into specie(Nome,Descrizione)
values('Criceto','Animaletto molto goloso e fragile. Non spaventarlo!');

insert into giorno(Nome)
values('Lunedi');

insert into giorno(Nome)
values('Martedi');

insert into giorno(Nome)
values('Mercoledi');

insert into giorno(Nome)
values('Giovedi');

insert into giorno(Nome)
values('Venerdi');

insert into giorno(Nome)
values('Sabato');

insert into giorno(Nome)
values('Domenica');

insert into cibo(Nome)
values('Insalata');

insert into cibo(Nome)
values('Crocchette');

insert into cibo(Nome)
values('Carne in Scatola');

insert into cibo(Nome)
values('Mais');

insert into cibo(Nome)
values('Farro');

insert into cibo(Nome)
values('Pesce in scatola');

insert into cibo(Nome)
values('Fieno');

insert into cibo(Nome)
values('Camola del grano');

insert into cibo(Nome)
values('Carota');

insert into cibo(Nome)
values('Erbetta');

insert into cibo(Nome)
values('Carne fresca');

insert into cibo(Nome)
values('Zolletta');

insert into cibo(Nome)
values('Biscotto');

insert into cibo(Nome)
values('Zampa di topo');

insert into cibo(Nome)
values('Frutta');

insert into cibo(Nome)
values('Verdura');


insert into esercizio(Descrizione,Nome)
values('Camminata tranquilla, goditi il panorama','Passeggiata');

insert into esercizio(Descrizione,Nome)
values('A meta tra passeggiata e corsa','Corsetta');

insert into esercizio(Descrizione,Nome)
values('Dai il massimo! Piu forte!','Corsa');

insert into esercizio(Descrizione,Nome)
values('Scegli l ostacolo piu alto che vedi. Salici sopra!','Arrampicata');

insert into esercizio(Descrizione,Nome)
values('Corsa sostenuta','Galoppo');

insert into esercizio(Descrizione,Nome)
values('Scegli la tua preda. Non avere pieta','Caccia');

insert into esercizio(Descrizione,Nome)
values('Prendi un po d aria','Svolazzata');

insert into esercizio(Descrizione,Nome)
values('Librati in cielo piu in alto che puoi','Volo');

insert into esercizio(Descrizione,Nome)
values('Immerigiti nelle profondita del tuo acquario, se puoi vai al lago','Nuotata');

insert into esercizio(Descrizione,Nome)
values('Spicca un bel balzo, piu in alto che puoi','Salto in alto');

insert into esercizio(Descrizione,Nome)
values('Spicca un bel balzo, piu in lungo che puoi','Salto in lungo');

insert into esercizio(Descrizione,Nome)
values('Non farti trovare!','Nascondino');

insert into farmaco(Nome,Descrizione)
values('Armadinprazolo','Per i brutti mal di testa');

insert into farmaco(Nome,Descrizione)
values('Cromolix','Quando ti fa molto male il pancino');

insert into farmaco(Nome,Descrizione)
values('Mortifer','Per le situazioni peggiori');

insert into farmaco(Nome,Descrizione)
values('Xoloprazolo','Per i cuccioli con le zampe puzzolenti');

insert into farmaco(Nome,Descrizione)
values('Betadyx','Shampoo aromatizzante');

insert into farmaco(Nome,Descrizione)
values('Oxylprazolo','Anti infiammatorio');

insert into farmaco(Nome,Descrizione)
values('Tachipirina','Anti infiammatorio');

insert into farmaco(Nome,Descrizione)
values('OKI','Contro il mal di testa');

insert into farmaco(Nome,Descrizione)
values('Batkiller','Antibatterico molto potente');

insert into farmaco(Nome,Descrizione)
values('Virkiller','Antivirale');

insert into farmaco(Nome,Descrizione)
values('Lemosilprazolo','Antidolorifico imbattibile');

insert into condizione_clinica(Nome)
values('Artrite');

insert into condizione_clinica(Nome)
values('Diabete');

insert into condizione_clinica(Nome)
values('Sordita');

insert into condizione_clinica(Nome)
values('Cecita');

insert into condizione_clinica(Nome)
values('Iperplasia');

insert into condizione_clinica(Nome)
values('Alopecia');

insert into condizione_clinica(Nome)
values('Iperadrenocorticismo');

insert into zona(Nome)
values('Trastevere');
    
insert into zona(Nome)
values('EUR');
    
insert into zona(Nome)
values('Rione II Trevi');
    
insert into zona(Nome)
values('Rione XXII Prati');
    
insert into zona(Nome)
values('Quartiere XVII Trieste');
    
insert into zona(Nome)
values('Tor Bella Monaca');
    
insert into zona(Nome)
values('San Lorenzo');
    
insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via del Melo',4,'Quartiere XVII Trieste');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via Andreotti',23,'EUR');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via Giuseppe',346,'Rione II Trevi');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via del Popolo',6060,'San Lorenzo');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via Albertazzi',346,'Rione XXII Prati');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via Rosa Parks',46996,'Quartiere XVII Trieste');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via E. Pankhurst',75,'Tor Bella Monaca');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via Einstein',3485,'Trastevere');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via Turing',346,'Tor Bella Monaca');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via Roma',9896,'Rione XXII Prati');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via Manzoni',245,'San Lorenzo');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via Mazzini',8400,'Quartiere XVII Trieste');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via Roma',1543,'EUR');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via Mazzini',456,'San Lorenzo');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via Italia',22,'Rione XXII Prati');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via Roma',293,'EUR');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via del Popolo',863,'Rione II Trevi');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via Europa',2353,'Quartiere XVII Trieste');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via Savoia',4007,'EUR');

insert into indirizzo(Ind_Via,Ind_Numero_Civico,Nome)
values('Via Turing',2480,'Rione II Trevi');

insert into animale(Nome,Data_di_Nascita,Peso,APP_Nome,Email)
values('Maya','2013-06-20',50,'Cane','lorenzino69@gmail.com');

insert into animale(Nome,Data_di_Nascita,Peso,APP_Nome,Email)
values('Pippi','2022-10-15',5,'Gatto','lorenzino69@gmail.com');

insert into animale(Nome,Data_di_Nascita,Peso,APP_Nome,Email)
values('Bernie','2020-07-24',1,'Pappagallo','luconexx@gmail.com');

insert into animale(Nome,Data_di_Nascita,Peso,APP_Nome,Email)
values('Anna','2004-08-12',250,'Mucca','pietrone03@gmail.com');

insert into animale(Nome,Data_di_Nascita,Peso,APP_Nome,Email)
values('Kiwi','2021-01-12',2,'Papera','mauritius@tiscali.com');

insert into cartella_clinica(Codice_Identificativo, Data_di_Creazione)
values(1,'2024-06-10');

insert into cartella_clinica(Codice_Identificativo, Data_di_Creazione)
values(2,'2024-06-10');

insert into cartella_clinica(Codice_Identificativo, Data_di_Creazione)
values(3,'2024-06-10');

insert into cartella_clinica(Codice_Identificativo, Data_di_Creazione)
values(4,'2024-06-10');

insert into cartella_clinica(Codice_Identificativo, Data_di_Creazione)
values(5,'2024-06-10');

insert into ambulatorio(Nome,Ind_Via,Ind_Numero_Civico,ZONA_nome,Descrizione)
values('Ambulatorio Developers','Via Savoia',4007,'EUR','L ambulatorio piu bello de tutta Roma. Il meglio che tu possa desiderare.');

insert into ambulatorio(Nome,Ind_Via,Ind_Numero_Civico,ZONA_nome,Descrizione)
values('Ambulatorio Cuore Peloso','Via Turing',346,'Tor Bella Monaca','Ambulatorio spazioso e arieggiato. Luminoso. Aperto 24h.');

insert into ambulatorio(Nome,Ind_Via,Ind_Numero_Civico,ZONA_nome,Descrizione)
values('Ambulatorio e Macelleria','Via Turing',2480,'Rione II Trevi','Ambulatorio puzzolente e mal tenuto. Se porti qui i tuoi animali no li riporti a casa.');

insert into residenza(Email,Ind_Via,Ind_Numero_Civico,ZONA_nome)
values('lorenzino69@gmail.com','Via Albertazzi','346','Rione XXII Prati');

insert into residenza(Email,Ind_Via,Ind_Numero_Civico,ZONA_nome)
values('pietrone03@gmail.com','Via Andreotti','23','EUR');

insert into residenza(Email,Ind_Via,Ind_Numero_Civico,ZONA_nome)
values('mauritius@tiscali.com','Via del Melo','4','Quartiere XVII Trieste');

insert into residenza(Email,Ind_Via,Ind_Numero_Civico,ZONA_nome)
values('redbro@gmail.com','Via del Popolo','6060','San Lorenzo');

insert into residenza(Email,Ind_Via,Ind_Numero_Civico,ZONA_nome)
values('lorenzino69@gmail.com','Via E. Pankhurst','75','Tor Bella Monaca');

insert into residenza(Email,Ind_Via,Ind_Numero_Civico,ZONA_nome)
values('mauritius@tiscali.com','Via E. Pankhurst',75,'Tor Bella Monaca');

insert into collocazione(Nome, Email)
values('Ambulatorio Cuore Peloso','asghsa@gmail.com');

insert into collocazione(Nome, Email)
values('Ambulatorio Developers','pietrone03@gmail.com');

insert into collocazione(Nome, Email)
values('Ambulatorio Developers','lorenzino69@gmail.com');

insert into collocazione(Nome, Email)
values('Ambulatorio e Macelleria','pierlozz@gmail.com');

