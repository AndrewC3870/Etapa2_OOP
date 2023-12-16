# Etapa2_OOP

Am creat clasa Database la care am folosit design patternul Singletone,
care este inițializată cu datele primite că input.
Clasa admin este populata cu datele din Database si conține toate datele static impreuna cu
metodele specifice unui admin în cadrul unei platforme.

Am creat doua clase noi UserArtist și UserHost care extind un User simplu și 
fiecare contine datele specifice pt un Artist, respectiv un Host.
Pentru Artist: cate o lista pentru toate albumele, merch-urile și event-utile care le va adăuga
sau șterge, plus un obiect de tip ArtistPage care reprezintă pagina artistului.
Pentru Host: cate o lista pentru toate Podcasturile, Announcemens și un obiect de tip Host Page
care reprezintă pagina Hostului.

# Paginarea:
Am creat interfața Page care este implementată de toate paginile specificate
(HomePage, Liked content, ArtistPage, HostPage) in care am declarat metoda de printCurrentPage.
Paginile Homepage si LikedContent contin metodele de afisare a recomandarilor, respectiv datele 
unui User. Fiecare User contine un obiect pagina care initial este HomePage, daca doreste sa navigheze
catre o alta pagina, atunci pagina curenta Page se reinitializeaza cu pagina dorita, ArtistPage etc.
Toate paginile sunt construite prin intermediul constructorului.

# Comenzi pentru Admin:
Toate comenzile primite ca input, specifice unui admin, sunt tratate in CommandRuner.
Pentru AddUser verific in toata lista de useri si daca nu exista nici un user cu acelasi nume, il 
adug in lista tuturor userilor si ii setez connection status cu online.

Pentru remove user verific tipul userului care urmeaza sa fie sters, daca e un user simplu atunci 
parcurg toti userii si daca cineva are un follow pe playlistul acestuia atunci sterg cantecul din 
followers playlists, dupa sterg userul din lista de useri. Daca e de tip artist, atunci verifica daca un 
user normal asculta un album sau daca se afla pe pagina acestuia, daca nu, sterg toate melodiile
artistului din lista de melodii si din lista de likedSongs a fiecarui User. Daca e de tip host, verific
daca un user normal asculta un podcast si daca cineva se afla pe pagina acestuia, in caz contrar 
sterg hostul din lista de useri.

Pentru afisare albumelor si a podcasturilor, parcurg listele de artist si de host si afisez datele
cerute. La fel si pt statistici.

# Comenzi pentru Artist:
Pentru add album verific daca artistul are un album cu acelasi nume si in caz contrar adaug albumul
in lista de albume al acestuia si toate melodiile din album le adaug in lista generala de melodii.
Pentru remove album verifica daca acesta nu este ascultat de nici un user normal si in caz afirmativ
sterg albumul si toate melodiile sale din lista de melodii.
AddEvent si AddMerch adauga in listele corespunzatoare a artistului event-urile si merch-urile daca 
acestea nu exista deja, respectiv pentru removeEvent sterg evenimentul daca acesta exista.

# Comenzi pentru Host:
Asemanator cu cele pentru Artist
# Comenzi pentru User Normal:
Am adugat comanda de switchConectionStatus care permite utilizatorului sa schimbe starea sa in platforma
(online/offline), am adaugat posibilitatea de a cauta un Artist sau un Host si in urma executarii 
comenzii select sa fie indreptat spre pagina acestora (initializez pagina curenta a userului cu pagina
Artistului sau a Hostului), cautarea se face prin lista de Useri.
Am adaugat posibilitatea de a cauta si de asculta un Album a unui artist.

# Detalii in plus:
Am creat o clasa noua UtisMethods in care am metode ajutatoare in rezolvarea temei.
Fiecare Artist, Host si User normal au cate o clasa in care sunt create metodele specifice
fiecarui tip de user

# Concluzii:
Tema atinge o multime de cazuri care trebuiesc tratate, care la randul lor
necesita rabdare si stapanirea unor principii de baza OOP.
