# Risolvere il seguente problema di ottimizzazione mediante un algoritmo ricorsivo:

Sapendo che nel database sono presenti 3 citt�, supponiamo che un tecnico debba compiere delle analisi
tecniche della durata di un giorno in ciascuna citta. Le analisi hanno un costo per ogni giornata,
determinato dalla somma di due contributi: un fattore costante (di valore 100) ogniqualvolta il tecnico si
deve spostare da una citt� ad un�altra in due giorni successivi, ed un fattore variabile pari al valore
numerico dell�umidit� della citt� nel giorno considerato. Si trovi la sequenza delle citt� da visitare nei
primi 15 giorni del mese selezionato, tale da minimizzare il costo complessivo rispettando i seguenti
vincoli:

    - Nei primi 15 giorni del mese, tutte le citt� devono essere visitate almeno una volta
    - In nessuna citt� si possono trascorrere pi� di 6 giornate (anche non consecutive)
    - Scelta una citt�, il tecnico non si pu� spostare prima di aver trascorso 3 giorni consecutivi.
    
# Approccio

Livello L della ricorsione:

    Rappresenta il giorno, per ogni livello scelgo la citt� da aggiungere alla soluzione parziale

Soluzione parziale: 

    Sequenza di citt� dal primo giorno fino al giorno L

Strategia: 

    Genero tutte le possibili soluzioni tramite un filtro e quando arrivo al caso terminale controllo se la soluzione che ho 
    � migliore di quelle che ho trovato fino ad ora.


Generazione del sotto-problema: 

    - se passa il filtro  => aggiungo la citt� alla soluzione parziale
    - se non passa il filtro  =>  provo un'altra citt�
    

Condizione di uscita: L == Giorni Totali

    - se punteggo < best  =>  nuovo best
    - se punteggo > best  =>  fine
    

