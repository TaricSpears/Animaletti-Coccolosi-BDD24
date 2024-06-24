-- *********************************************
-- * SQL MySQL generation                      
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Sat Jun 22 12:29:38 2024 
-- * LUN file: C:\Users\Lorenzo\Documents\UNI files\DataBaseAnimaletti\ElaboratoBergamiSbaraccani2024 3NF.lun 
-- * Schema: ER-relazionale/1-1 
-- ********************************************* 


-- Database Section
-- ________________ 

create database DBBANimalettiCoccolosi;
use DBBANimalettiCoccolosi;


-- Tables Section
-- _____________ 

create table ACCETTAZIONE_I (
     ID_intervento int not null,
     Accettato boolean not null,
     Codice_Identificativo int not null,
     constraint FKACC_INT_ID primary key (ID_intervento));

create table ALIMENTAZIONE (
     Codice_Identificativo int not null,
     Codice_Dieta int not null,
     constraint ID_ALIMENTAZIONE_ID primary key (Codice_Identificativo, Codice_Dieta));

create table AMBULATORIO (
     Nome varchar(50) not null,
     Ind_Via varchar(30) not null,
     Ind_Numero_Civico int not null,
     ZONA_nome varchar(50) not null,
     Descrizione varchar(200) not null,
     constraint ID_AMBULATORIO_ID primary key (Nome),
     constraint FKLOCALIZZAZIONE_ID unique (Ind_Via, Ind_Numero_Civico, ZONA_nome));

create table AMMINISTRATORE (
     Email varchar(25) not null,
     constraint FKESSERE_A_ID primary key (Email));

create table ANIMALE (
     Nome varchar(20) not null,
     Data_di_Nascita date not null,
     Peso int not null,
     Valutazione_media int,
     Codice_Identificativo int not null auto_increment,
     APP_Nome varchar(20) not null,
     Email varchar(25) not null,
     constraint ID_ANIMALE_ID primary key (Codice_Identificativo));

create table ASSEGNAZIONE (
     Nome varchar(50) not null,
     Codice_Identificativo int not null,
     constraint ID_ASSEGNAZIONE_ID primary key (Nome, Codice_Identificativo));

create table ASSUNZIONE (
     Nome varchar(20) not null,
     Codice_Regime int not null,
     Frequenza int not null,
     Quantita float(6) not null,
     constraint ID_ASSUNZIONE_ID primary key (Nome, Codice_Regime));

create table CARTELLA_CLINICA (
     Codice_Identificativo int not null,
     Data_di_Creazione date not null,
     constraint FKASSOCIAZIONE_ID primary key (Codice_Identificativo));

create table CIBO (
     Nome varchar(20) not null,
     constraint ID_CIBO_ID primary key (Nome));

create table COLLOCAZIONE (
     Nome varchar(50) not null,
     Email varchar(25) not null,
     constraint ID_COLLOCAZIONE_ID primary key (Nome, Email));

create table COMPOSIZIONE_W (
     Nome varchar(20) not null,
     Codice_Workout int not null,
     Frequenza int not null,
     Quantita float(6) not null,
     constraint ID_COMPOSIZIONE_W_ID primary key (Nome, Codice_Workout));

create table COMPRENSIONE (
     Codice_Dieta int not null,
     Codice_Menu int not null,
     constraint ID_COMPRENSIONE_ID primary key (Codice_Menu, Codice_Dieta));

create table CONDIZIONE_CLINICA (
     Nome varchar(50) not null,
     constraint ID_ID primary key (Nome));

create table DIETA (
     Codice_Dieta int not null auto_increment,
     Nome varchar(20) not null,
     Descrizione varchar(100) not null,
     constraint ID_DIETA_ID primary key (Codice_Dieta));

create table ESERCIZIO (
     Descrizione varchar(50) not null,
     Nome varchar(20) not null,
     constraint ID_ESERCIZIO_ID primary key (Nome));

create table FARMACO (
     Nome varchar(20) not null,
     Descrizione varchar(50) not null,
     constraint ID_FARMACO_ID primary key (Nome));

create table GIORNO (
     Nome varchar(20) not null,
     Codice_Giorno int not null auto_increment,
     constraint ID_GIORNO_ID primary key (Codice_Giorno));

create table GRUPPO (
     Nome varchar(50),
     Data_apertura date not null,
     Privato boolean not null,
     ID_Gruppo int not null auto_increment,
     constraint ID_GRUPPO_ID primary key (ID_Gruppo));

create table INDIRIZZO (
     Ind_Via varchar(30) not null,
     Ind_Numero_Civico int not null,
     Nome varchar(50) not null,
     constraint ID_INDIRIZZO_ID primary key (Ind_Via, Ind_Numero_Civico, Nome));

create table INTEGRAZIONE (
     Nome varchar(20) not null,
     Codice_Menu int not null,
     Frequenza int not null,
     Quantita float(6) not null,
     constraint ID_INTEGRAZIONE_ID primary key (Nome, Codice_Menu));

create table INTERVENTO (
     Data date not null,
     Ora int not null,
     Descrizione varchar(200) not null,
     ID_intervento int not null auto_increment,
     Codice_Identificativo int not null,
     Email varchar(25) not null,
     constraint ID_INTERVENTO_ID primary key (ID_intervento));

create table MENU (
     Codice_Menu int not null auto_increment,
     Descrizione varchar(100) not null,
     constraint ID_MENU_ID primary key (Codice_Menu));

create table MESSAGGIO (
     Contenuto varchar(200) not null,
     Istante_Invio datetime not null,
     ID_messaggio int not null auto_increment,
     Email varchar(25) not null,
     ID_Gruppo int not null,
     constraint ID_MESSAGGIO_ID primary key (ID_messaggio));

create table OCCORRENZA_F (
     Codice_Giorno int not null,
     Codice_Regime int not null,
     constraint ID_OCCORRENZA_F_ID primary key (Codice_Giorno, Codice_Regime));

create table OCCORRENZA_M (
     Codice_Giorno int not null,
     Codice_Menu int not null,
     constraint ID_OCCORRENZA_M_ID primary key (Codice_Giorno, Codice_Menu));

create table OCCORRENZA_W (
     Codice_Giorno int not null,
     Codice_Workout int not null,
     constraint ID_OCCORRENZA_W_ID primary key (Codice_Workout, Codice_Giorno));

create table PAGAMENTO (
     CodParcella int not null,
     Pagata boolean not null,
     Email varchar(25) not null,
     constraint FKPAG_PAR_ID primary key (CodParcella));

create table PARCELLA (
     Importo float(7) not null,
     Descrizione varchar(500) not null,
     CodParcella int not null auto_increment,
     Email varchar(25) not null,
     constraint ID_PARCELLA_ID primary key (CodParcella));

create table PARTECIPAZIONE (
     ID_Gruppo int not null,
     Email varchar(25) not null,
     constraint ID_PARTECIPAZIONE_ID primary key (ID_Gruppo, Email));

create table PRESCRIZIONE_A (
     Codice_Regime int not null,
     Codice_Terapia int not null,
     constraint ID_PRESCRIZIONE_A_ID primary key (Codice_Regime, Codice_Terapia));

create table PRESCRIZIONE_E (
     Codice_Terapia int not null,
     Codice_Workout int not null,
     constraint ID_PRESCRIZIONE_E_ID primary key (Codice_Workout, Codice_Terapia));

create table PROPRIETARIO (
     Email varchar(25) not null,
     Bloccato boolean not null,
     constraint FKESSERE_P_ID primary key (Email));

create table REFERTO_CLINICO (
     Data_Emissione date not null,
     Descrizione varchar(1000) not null,
     Codice_Referto int not null auto_increment,
     ID_visita int not null,
     Codice_Identificativo int not null,
     constraint ID_REFERTO_CLINICO_ID primary key (Codice_Referto),
     constraint FKEMISSIONE_ID unique (ID_visita));

create table REGIME_FARMACOLOGICO (
     Codice_Regime int not null auto_increment,
     Descrizione varchar(200) not null,
     constraint ID_REGIME_FARMACOLOGICO_ID primary key (Codice_Regime));

create table RESIDENZA (
     Email varchar(25) not null,
     Ind_Via varchar(30) not null,
     Ind_Numero_Civico int not null,
     ZONA_nome varchar(50) not null,
     constraint ID_RESIDENZA_ID primary key (Ind_Via, Ind_Numero_Civico, ZONA_nome, Email));

create table SEGNALAZIONE (
     Motivazione varchar(200) not null,
     Email varchar(25) not null,
     ID_messaggio int not null);

create table SPECIE (
     Nome varchar(20) not null,
     Descrizione varchar(200) not null,
     Peso_medio int,
     constraint ID_SPECIE_ID primary key (Nome));

create table TERAPIA (
     Codice_Terapia int not null auto_increment,
     Data_Inizio date not null,
     Nome varchar(20) not null,
     Descrizione varchar(100) not null,
     Codice_Dieta int,
     Codice_Referto int not null,
     constraint ID_TERAPIA_ID primary key (Codice_Terapia));

create table UTENTE (
     Nome varchar(32) not null,
     Cognome varchar(32) not null,
     Email varchar(25) not null,
     Data_di_Nascita date,
     Data_di_Iscrizione date not null,
     Password varchar(15) not null,
     constraint ID_UTENTE_ID primary key (Email));

create table VALUTAZIONE_A (
     Codice_Identificativo int not null,
     Email varchar(25) not null,
     Voto int not null,
     constraint ID_VALUTAZIONE_A_ID primary key (Email, Codice_Identificativo));

create table VALUTAZIONE_V (
     ID_intervento int not null,
     Voto int not null,
     Email varchar(25) not null,
     constraint FKVAL_INT_ID primary key (ID_intervento));

create table VETERINARIO (
     Email varchar(25) not null,
     Curriculum varchar(200) not null,
     Valutazione_media int,
     constraint FKESSERE_V_ID primary key (Email));

create table VISITA (
     Data date not null,
     Ora int not null,
     Descrizione varchar(100) not null,
     Urgenza int not null,
     ID_visita int not null auto_increment,
     Codice_Identificativo int not null,
     Email varchar(25),
     ACC_Email varchar(25),
     constraint ID_VISITA_ID primary key (ID_visita));

create table WORKOUT (
     Codice_Workout int not null auto_increment,
     Descrizione varchar(200) not null,
     constraint ID_WORKOUT_ID primary key (Codice_Workout));

create table ZONA (
     Nome varchar(50) not null,
     constraint ID_ZONA_ID primary key (Nome));


-- Constraints Section
-- ___________________ 

alter table ACCETTAZIONE_I add constraint FKACC_INT_FK
     foreign key (ID_intervento)
     references INTERVENTO (ID_intervento);

alter table ACCETTAZIONE_I add constraint FKACC_ANI_FK
     foreign key (Codice_Identificativo)
     references ANIMALE (Codice_Identificativo);

alter table ALIMENTAZIONE add constraint FKALI_DIE_FK
     foreign key (Codice_Dieta)
     references DIETA (Codice_Dieta);

alter table ALIMENTAZIONE add constraint FKALI_ANI
     foreign key (Codice_Identificativo)
     references ANIMALE (Codice_Identificativo);

-- Not implemented
-- alter table AMBULATORIO add constraint ID_AMBULATORIO_CHK
--     check(exists(select * from COLLOCAZIONE
--                  where COLLOCAZIONE.Nome = Nome)); 

alter table AMBULATORIO add constraint FKLOCALIZZAZIONE_FK
     foreign key (Ind_Via, Ind_Numero_Civico, ZONA_nome)
     references INDIRIZZO (Ind_Via, Ind_Numero_Civico, Nome);

alter table AMMINISTRATORE add constraint FKESSERE_A_FK
     foreign key (Email)
     references UTENTE (Email);

-- Not implemented
-- alter table ANIMALE add constraint ID_ANIMALE_CHK
--     check(exists(select * from ALIMENTAZIONE
--                  where ALIMENTAZIONE.Codice_Identificativo = Codice_Identificativo)); 

-- Not implemented
-- alter table ANIMALE add constraint ID_ANIMALE_CHK
--     check(exists(select * from CARTELLA_CLINICA
--                  where CARTELLA_CLINICA.Codice_Identificativo = Codice_Identificativo)); 

alter table ANIMALE add constraint FKAPPARTENENZA_A_FK
     foreign key (APP_Nome)
     references SPECIE (Nome);

alter table ANIMALE add constraint FKPOSSESSO_FK
     foreign key (Email)
     references PROPRIETARIO (Email);

alter table ASSEGNAZIONE add constraint FKASS_CAR_FK
     foreign key (Codice_Identificativo)
     references CARTELLA_CLINICA (Codice_Identificativo);

alter table ASSEGNAZIONE add constraint FKASS_CON
     foreign key (Nome)
     references CONDIZIONE_CLINICA (Nome);

alter table ASSUNZIONE add constraint FKASS_REG_FK
     foreign key (Codice_Regime)
     references REGIME_FARMACOLOGICO (Codice_Regime);

alter table ASSUNZIONE add constraint FKASS_FAR
     foreign key (Nome)
     references FARMACO (Nome);

alter table CARTELLA_CLINICA add constraint FKASSOCIAZIONE_FK
     foreign key (Codice_Identificativo)
     references ANIMALE (Codice_Identificativo);

alter table COLLOCAZIONE add constraint FKCOL_VET_FK
     foreign key (Email)
     references VETERINARIO (Email);

alter table COLLOCAZIONE add constraint FKCOL_AMB
     foreign key (Nome)
     references AMBULATORIO (Nome);

alter table COMPOSIZIONE_W add constraint FKCOM_WOR_FK
     foreign key (Codice_Workout)
     references WORKOUT (Codice_Workout);

alter table COMPOSIZIONE_W add constraint FKCOM_ESE
     foreign key (Nome)
     references ESERCIZIO (Nome);

alter table COMPRENSIONE add constraint FKCOM_MEN
     foreign key (Codice_Menu)
     references MENU (Codice_Menu);

alter table COMPRENSIONE add constraint FKCOM_DIE_FK
     foreign key (Codice_Dieta)
     references DIETA (Codice_Dieta);

-- Not implemented
-- alter table DIETA add constraint ID_DIETA_CHK
--     check(exists(select * from COMPRENSIONE
--                  where COMPRENSIONE.Codice_Dieta = Codice_Dieta)); 

alter table INDIRIZZO add constraint FKAPPARTENENZA_I_FK
     foreign key (Nome)
     references ZONA (Nome);

alter table INTEGRAZIONE add constraint FKINT_MEN_FK
     foreign key (Codice_Menu)
     references MENU (Codice_Menu);

alter table INTEGRAZIONE add constraint FKINT_CIB
     foreign key (Nome)
     references CIBO (Nome);

alter table INTERVENTO add constraint FKDESTINAZIONE_I_FK
     foreign key (Codice_Identificativo)
     references ANIMALE (Codice_Identificativo);

alter table INTERVENTO add constraint FKRICHIESTA_I_FK
     foreign key (Email)
     references VETERINARIO (Email);

-- Not implemented
-- alter table MENU add constraint ID_MENU_CHK
--     check(exists(select * from INTEGRAZIONE
--                  where INTEGRAZIONE.Codice_Menu = Codice_Menu)); 

alter table MESSAGGIO add constraint FKTRASMISSIONE_M_FK
     foreign key (Email)
     references UTENTE (Email);

alter table MESSAGGIO add constraint FKINCLUSIONE_FK
     foreign key (ID_Gruppo)
     references GRUPPO (ID_Gruppo);

alter table OCCORRENZA_F add constraint FKOCC_REG_FK
     foreign key (Codice_Regime)
     references REGIME_FARMACOLOGICO (Codice_Regime);

alter table OCCORRENZA_F add constraint FKOCC_GIO_2
     foreign key (Codice_Giorno)
     references GIORNO (Codice_Giorno);

alter table OCCORRENZA_M add constraint FKOCC_MEN_FK
     foreign key (Codice_Menu)
     references MENU (Codice_Menu);

alter table OCCORRENZA_M add constraint FKOCC_GIO_1
     foreign key (Codice_Giorno)
     references GIORNO (Codice_Giorno);

alter table OCCORRENZA_W add constraint FKOCC_WOR
     foreign key (Codice_Workout)
     references WORKOUT (Codice_Workout);

alter table OCCORRENZA_W add constraint FKOCC_GIO_FK
     foreign key (Codice_Giorno)
     references GIORNO (Codice_Giorno);

alter table PAGAMENTO add constraint FKPAG_PAR_FK
     foreign key (CodParcella)
     references PARCELLA (CodParcella);

alter table PAGAMENTO add constraint FKPAG_PRO_FK
     foreign key (Email)
     references PROPRIETARIO (Email);

-- Not implemented
-- alter table PARCELLA add constraint ID_PARCELLA_CHK
--     check(exists(select * from PAGAMENTO
--                  where PAGAMENTO.CodParcella = CodParcella)); 

alter table PARCELLA add constraint FKCREAZIONE_FK
     foreign key (Email)
     references VETERINARIO (Email);

alter table PARTECIPAZIONE add constraint FKPAR_UTE_FK
     foreign key (Email)
     references UTENTE (Email);

alter table PARTECIPAZIONE add constraint FKPAR_GRU
     foreign key (ID_Gruppo)
     references GRUPPO (ID_Gruppo);

alter table PRESCRIZIONE_A add constraint FKPRE_TER_1_FK
     foreign key (Codice_Terapia)
     references TERAPIA (Codice_Terapia);

alter table PRESCRIZIONE_A add constraint FKPRE_REG
     foreign key (Codice_Regime)
     references REGIME_FARMACOLOGICO (Codice_Regime);

alter table PRESCRIZIONE_E add constraint FKPRE_WOR
     foreign key (Codice_Workout)
     references WORKOUT (Codice_Workout);

alter table PRESCRIZIONE_E add constraint FKPRE_TER_FK
     foreign key (Codice_Terapia)
     references TERAPIA (Codice_Terapia);

alter table PROPRIETARIO add constraint FKESSERE_P_FK
     foreign key (Email)
     references UTENTE (Email);

alter table REFERTO_CLINICO add constraint FKEMISSIONE_FK
     foreign key (ID_visita)
     references VISITA (ID_visita);

alter table REFERTO_CLINICO add constraint FKCOMPOSIZIONE_FK
     foreign key (Codice_Identificativo)
     references CARTELLA_CLINICA (Codice_Identificativo);

-- Not implemented
-- alter table REGIME_FARMACOLOGICO add constraint ID_REGIME_FARMACOLOGICO_CHK
--     check(exists(select * from ASSUNZIONE
--                  where ASSUNZIONE.Codice_Regime = Codice_Regime)); 

alter table RESIDENZA add constraint FKRES_IND
     foreign key (Ind_Via, Ind_Numero_Civico, ZONA_nome)
     references INDIRIZZO (Ind_Via, Ind_Numero_Civico, Nome);

alter table RESIDENZA add constraint FKRES_PRO_FK
     foreign key (Email)
     references PROPRIETARIO (Email);

alter table SEGNALAZIONE add constraint FKTRASMISSIONE_S_FK
     foreign key (Email)
     references UTENTE (Email);

alter table SEGNALAZIONE add constraint FKRIFERIMENTO_FK
     foreign key (ID_messaggio)
     references MESSAGGIO (ID_messaggio);

alter table TERAPIA add constraint FKPRESCRIZIONE_D_FK
     foreign key (Codice_Dieta)
     references DIETA (Codice_Dieta);

alter table TERAPIA add constraint FKDETERMINAZIONE_FK
     foreign key (Codice_Referto)
     references REFERTO_CLINICO (Codice_Referto);

alter table VALUTAZIONE_A add constraint FKVAL_UTE
     foreign key (Email)
     references UTENTE (Email);

alter table VALUTAZIONE_A add constraint FKVAL_ANI_FK
     foreign key (Codice_Identificativo)
     references ANIMALE (Codice_Identificativo);

alter table VALUTAZIONE_V add constraint FKVAL_INT_FK
     foreign key (ID_intervento)
     references INTERVENTO (ID_intervento);

alter table VALUTAZIONE_V add constraint FKVAL_VET_FK
     foreign key (Email)
     references VETERINARIO (Email);

alter table VETERINARIO add constraint FKESSERE_V_FK
     foreign key (Email)
     references UTENTE (Email);

alter table VISITA add constraint FKRICHIESTA_V_FK
     foreign key (Codice_Identificativo)
     references ANIMALE (Codice_Identificativo);

alter table VISITA add constraint FKDESTINAZIONE_V_FK
     foreign key (Email)
     references VETERINARIO (Email);

alter table VISITA add constraint FKACCETTAZIONE_V_FK
     foreign key (ACC_Email)
     references VETERINARIO (Email);

-- Not implemented
-- alter table WORKOUT add constraint ID_WORKOUT_CHK
--     check(exists(select * from COMPOSIZIONE_W
--                  where COMPOSIZIONE_W.Codice_Workout = Codice_Workout)); 

-- Not implemented
-- alter table ZONA add constraint ID_ZONA_CHK
--     check(exists(select * from INDIRIZZO
--                  where INDIRIZZO.Nome = Nome)); 


-- Index Section
-- _____________ 

create unique index FKACC_INT_IND
     on ACCETTAZIONE_I (ID_intervento);

create index FKACC_ANI_IND
     on ACCETTAZIONE_I (Codice_Identificativo);

create unique index ID_ALIMENTAZIONE_IND
     on ALIMENTAZIONE (Codice_Identificativo, Codice_Dieta);

create index FKALI_DIE_IND
     on ALIMENTAZIONE (Codice_Dieta);

create unique index ID_AMBULATORIO_IND
     on AMBULATORIO (Nome);

create unique index FKLOCALIZZAZIONE_IND
     on AMBULATORIO (Ind_Via, Ind_Numero_Civico);

create unique index FKESSERE_A_IND
     on AMMINISTRATORE (Email);

create unique index ID_ANIMALE_IND
     on ANIMALE (Codice_Identificativo);

create index FKAPPARTENENZA_A_IND
     on ANIMALE (APP_Nome);

create index FKPOSSESSO_IND
     on ANIMALE (Email);

create unique index ID_ASSEGNAZIONE_IND
     on ASSEGNAZIONE (Nome, Codice_Identificativo);

create index FKASS_CAR_IND
     on ASSEGNAZIONE (Codice_Identificativo);

create unique index ID_ASSUNZIONE_IND
     on ASSUNZIONE (Nome, Codice_Regime);

create index FKASS_REG_IND
     on ASSUNZIONE (Codice_Regime);

create unique index FKASSOCIAZIONE_IND
     on CARTELLA_CLINICA (Codice_Identificativo);

create unique index ID_CIBO_IND
     on CIBO (Nome);

create unique index ID_COLLOCAZIONE_IND
     on COLLOCAZIONE (Nome, Email);

create index FKCOL_VET_IND
     on COLLOCAZIONE (Email);

create unique index ID_COMPOSIZIONE_W_IND
     on COMPOSIZIONE_W (Nome, Codice_Workout);

create index FKCOM_WOR_IND
     on COMPOSIZIONE_W (Codice_Workout);

create unique index ID_COMPRENSIONE_IND
     on COMPRENSIONE (Codice_Menu, Codice_Dieta);

create index FKCOM_DIE_IND
     on COMPRENSIONE (Codice_Dieta);

create unique index ID_IND
     on CONDIZIONE_CLINICA (Nome);

create unique index ID_DIETA_IND
     on DIETA (Codice_Dieta);

create unique index ID_ESERCIZIO_IND
     on ESERCIZIO (Nome);

create unique index ID_FARMACO_IND
     on FARMACO (Nome);

create unique index ID_GIORNO_IND
     on GIORNO (Codice_Giorno);

create unique index ID_GRUPPO_IND
     on GRUPPO (ID_Gruppo);

create unique index ID_INDIRIZZO_IND
     on INDIRIZZO (Ind_Via, Ind_Numero_Civico);

create index FKAPPARTENENZA_I_IND
     on INDIRIZZO (Nome);

create unique index ID_INTEGRAZIONE_IND
     on INTEGRAZIONE (Nome, Codice_Menu);

create index FKINT_MEN_IND
     on INTEGRAZIONE (Codice_Menu);

create unique index ID_INTERVENTO_IND
     on INTERVENTO (ID_intervento);

create index FKDESTINAZIONE_I_IND
     on INTERVENTO (Codice_Identificativo);

create index FKRICHIESTA_I_IND
     on INTERVENTO (Email);

create unique index ID_MENU_IND
     on MENU (Codice_Menu);

create unique index ID_MESSAGGIO_IND
     on MESSAGGIO (ID_messaggio);

create index FKTRASMISSIONE_M_IND
     on MESSAGGIO (Email);

create index FKINCLUSIONE_IND
     on MESSAGGIO (ID_Gruppo);

create unique index ID_OCCORRENZA_F_IND
     on OCCORRENZA_F (Codice_Giorno, Codice_Regime);

create index FKOCC_REG_IND
     on OCCORRENZA_F (Codice_Regime);

create unique index ID_OCCORRENZA_M_IND
     on OCCORRENZA_M (Codice_Giorno, Codice_Menu);

create index FKOCC_MEN_IND
     on OCCORRENZA_M (Codice_Menu);

create unique index ID_OCCORRENZA_W_IND
     on OCCORRENZA_W (Codice_Workout, Codice_Giorno);

create index FKOCC_GIO_IND
     on OCCORRENZA_W (Codice_Giorno);

create unique index FKPAG_PAR_IND
     on PAGAMENTO (CodParcella);

create index FKPAG_PRO_IND
     on PAGAMENTO (Email);

create unique index ID_PARCELLA_IND
     on PARCELLA (CodParcella);

create index FKCREAZIONE_IND
     on PARCELLA (Email);

create unique index ID_PARTECIPAZIONE_IND
     on PARTECIPAZIONE (ID_Gruppo, Email);

create index FKPAR_UTE_IND
     on PARTECIPAZIONE (Email);

create unique index ID_PRESCRIZIONE_A_IND
     on PRESCRIZIONE_A (Codice_Regime, Codice_Terapia);

create index FKPRE_TER_1_IND
     on PRESCRIZIONE_A (Codice_Terapia);

create unique index ID_PRESCRIZIONE_E_IND
     on PRESCRIZIONE_E (Codice_Workout, Codice_Terapia);

create index FKPRE_TER_IND
     on PRESCRIZIONE_E (Codice_Terapia);

create unique index FKESSERE_P_IND
     on PROPRIETARIO (Email);

create unique index ID_REFERTO_CLINICO_IND
     on REFERTO_CLINICO (Codice_Referto);

create unique index FKEMISSIONE_IND
     on REFERTO_CLINICO (ID_visita);

create index FKCOMPOSIZIONE_IND
     on REFERTO_CLINICO (Codice_Identificativo);

create unique index ID_REGIME_FARMACOLOGICO_IND
     on REGIME_FARMACOLOGICO (Codice_Regime);

create unique index ID_RESIDENZA_IND
     on RESIDENZA (Ind_Via, Ind_Numero_Civico, Email);

create index FKRES_PRO_IND
     on RESIDENZA (Email);

create index FKTRASMISSIONE_S_IND
     on SEGNALAZIONE (Email);

create index FKRIFERIMENTO_IND
     on SEGNALAZIONE (ID_messaggio);

create unique index ID_SPECIE_IND
     on SPECIE (Nome);

create unique index ID_TERAPIA_IND
     on TERAPIA (Codice_Terapia);

create index FKPRESCRIZIONE_D_IND
     on TERAPIA (Codice_Dieta);

create index FKDETERMINAZIONE_IND
     on TERAPIA (Codice_Referto);

create unique index ID_UTENTE_IND
     on UTENTE (Email);

create unique index ID_VALUTAZIONE_A_IND
     on VALUTAZIONE_A (Email, Codice_Identificativo);

create index FKVAL_ANI_IND
     on VALUTAZIONE_A (Codice_Identificativo);

create unique index FKVAL_INT_IND
     on VALUTAZIONE_V (ID_intervento);

create index FKVAL_VET_IND
     on VALUTAZIONE_V (Email);

create unique index FKESSERE_V_IND
     on VETERINARIO (Email);

create unique index ID_VISITA_IND
     on VISITA (ID_visita);

create index FKRICHIESTA_V_IND
     on VISITA (Codice_Identificativo);

create index FKDESTINAZIONE_V_IND
     on VISITA (Email);

create index FKACCETTAZIONE_V_IND
     on VISITA (ACC_Email);

create unique index ID_WORKOUT_IND
     on WORKOUT (Codice_Workout);

create unique index ID_ZONA_IND
     on ZONA (Nome);

