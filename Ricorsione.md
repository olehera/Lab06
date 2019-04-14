# Risolvere il seguente problema di ottimizzazione mediante un algoritmo ricorsivo:

Sapendo che nel database sono presenti 3 città, supponiamo che un tecnico debba compiere delle analisi
tecniche della durata di un giorno in ciascuna citta. Le analisi hanno un costo per ogni giornata,
determinato dalla somma di due contributi: un fattore costante (di valore 100) ogniqualvolta il tecnico si
deve spostare da una città ad un’altra in due giorni successivi, ed un fattore variabile pari al valore
numerico dell’umidità della città nel giorno considerato. Si trovi la sequenza delle città da visitare nei
primi 15 giorni del mese selezionato, tale da minimizzare il costo complessivo rispettando i seguenti
vincoli:

    - Nei primi 15 giorni del mese, tutte le città devono essere visitate almeno una volta
    - In nessuna città si possono trascorrere più di 6 giornate (anche non consecutive)
    - Scelta una città, il tecnico non si può spostare prima di aver trascorso 3 giorni consecutivi.
    
# Approccio

Livello L della ricorsione:

    Rappresenta il giorno, per ogni livello scelgo la città da aggiungere alla soluzione parziale

Soluzione parziale: 

    Sequenza di città dal primo giorno fino al giorno L

Strategia: 

    Genero tutte le possibili soluzioni tramite un filtro e quando arrivo al caso terminale controllo se la soluzione che ho 
    è migliore di quelle che ho trovato fino ad ora.


Generazione del sotto-problema: 

    - se passa il filtro  => aggiungo la città alla soluzione parziale
    - se non passa il filtro  =>  provo un'altra città
    

Condizione di uscita: L == Giorni Totali

    - se punteggo < best  =>  nuovo best
    - se punteggo > best  =>  fine
    

