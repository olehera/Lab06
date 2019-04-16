package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private MeteoDAO dao;
	private List<Citta> citta;
	private List<SimpleCity> soluzione;
	private double best;

	public Model() {
		dao = new MeteoDAO();
		citta = dao.getAllLocalita();
	}

	public String getUmiditaMedia(int mese) {
		String s = "";
		
		for (Citta temp: citta)
			s += temp.getNome()+":  "+dao.getAvgRilevamentiLocalitaMese(mese, temp.getNome())+" °C\n";

		return s;
	}

	public String trovaSequenza(int mese) {
		String s = "";
		best = 0.0;
		soluzione = null;
		List<SimpleCity> parziale = new ArrayList<SimpleCity>();
		
		for (Citta c: citta)
			c.setRilevamenti(dao.getAllRilevamentiLocalitaMese(mese, c.getNome()));
		
		ricorsione(parziale, 0);
		
		if (soluzione != null) {
			for (int i=1; i<=soluzione.size(); i++)
				s += i+".  "+soluzione.get(i-1).getNome()+"\n"; 
		} else
			s = "Non è stata trovata nessuna soluzione";

		return s+"Punteggio = "+punteggioSoluzione(soluzione);
	}
	
	private void ricorsione(List<SimpleCity> parziale, int L) {
		
		if (L == NUMERO_GIORNI_TOTALI) {
			Double punteggio = punteggioSoluzione(parziale);
			if (punteggio < best || best == 0.0) {
				best = punteggio;
				soluzione = new ArrayList<SimpleCity>(parziale);
			}
			return ; 
		}
		
		for (Citta c: citta) {
			Rilevamento r = c.getRilevamenti().get(L);
			parziale.add(new SimpleCity(r.getLocalita(), r.getUmidita()));
				
			if (controllaParziale(parziale))
				ricorsione(parziale, L+1);
				
			parziale.remove(parziale.size()-1);
		}
		
	}

	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata) {
		
		double score = 0.0;
		int count = 0;
		
		for (SimpleCity sc: soluzioneCandidata)
			score += sc.getCosto();
		
		for (int i=0; i<soluzioneCandidata.size()-1; i++)
			if (!soluzioneCandidata.get(i).equals(soluzioneCandidata.get(i+1)))
				count++;
		
		return score+(count*COST);
	}

	private boolean controllaParziale(List<SimpleCity> parziale) {
		
		if (parziale.size() > 1) {
			SimpleCity attuale = parziale.get(0);
			int c = 0;
			for (SimpleCity sc: parziale) {
				
				if (attuale.equals(sc))
					c++;
				else if (c >= NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN) {
					c = 1;
					attuale = sc;
				} else
					return false;
				
			}}
			
		for (Citta c: citta) {
			int count = 0;
			for (SimpleCity sc: parziale) {
				if (c.getNome().compareTo(sc.getNome())==0)
					count++;
			}
			if (count > NUMERO_GIORNI_CITTA_MAX)
				return false;
			if (parziale.size() == NUMERO_GIORNI_TOTALI & count < 1)
				return false;
		}
		
		return true;
	}

}
