package edu.feri.virusss8.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class Parser {
	
    private static final String MAIN_URL = "http://www.slovenia.info/si/zdravilisce/";
    private static final String PREDPONA_ZA_LANG = "&lng=";
    public int klic = -1;
    WebPage wp = new WebPage();
	
	public String zdravilisca(int webAddress, int language){
		klic = webAddress;
		getData(xmlCleaner(whichZdravilisce(webAddress, language)), language);
		return zaNaAndroid();
	}
	
	public String seznamZdravilisc() {
		return getLinks(xmlCleaner("http://www.slovenia.info/si/zdravilisce.htm?zdravilisce=0"));
	}
	
	public void getData(TagNode tagNode, int language) {
		
		TagNode[] naslov = findInfo(tagNode, "//div[@class='titBox']//h1");
		TagNode[] opis = findInfo(tagNode, "//div[@class='wpContent']//div");
		TagNode[] slika = findInfo(tagNode, "//div[@class='wpContent']//img");
		TagNode[] informa = findInfo(tagNode, "//div[@class='wpContent']//div[@class='darkbox darkboxtown']//div[@class='info']");

		// testni primerki
//		String[] desc = new String[opis.length];
//		String title = naslov[0].getText().toString();
//		String img = slika[1].getAttributeByName("src").toString().replace("\\", "/");
//		String info = informa[0].getText().toString();
//		String[] info = new String[informa.length];
		
		// moja struktura
		String[] desc = null;
		wp.setDescription(new String[opis.length]);
		wp.setTitle(naslov[0].getText().toString());
		wp.setImage_addr(slika[1].getAttributeByName("src").toString().replace("\\", "/"));
		String inf;
		
		inf = informa[0].getText().toString();
		
		String link = "";
		int sub;
		
		switch(language) {
		case 1:
			inf = inf.replace("Izvajalec :", "");
			inf = inf.replace("Naslov:", "\n");
			inf = inf.replace("Telefon:", "\n");
			inf = inf.replace("Faks:", "\n");
			sub = inf.indexOf("e-mail:");
			inf = inf.replace(link = inf.substring(sub, inf.length()), "");
			wp.setInfo(inf);
			link = link.replace("e-mail: ", "");
			wp.setLink("http://" + link);
			break;
		case 2:
			inf = inf.replace("Organizer :", "");
			inf = inf.replace("Address:", "\n");
			inf = inf.replace("Phone:", "\n");
			inf = inf.replace("Fax:", "\n");
			sub = inf.indexOf("e-mail:");
			inf = inf.replace(link = inf.substring(sub, inf.length()), "");
			wp.setInfo(inf);
			link = link.replace("e-mail: ", "");
			wp.setLink("http://" + link);
			break;
		case 3:
			inf = inf.replace("Straße:", "\n");
			inf = inf.replace("Telefon:", "\n");
			inf = inf.replace("Fax:", "\n");
			sub = inf.indexOf("E-Mail:");
			inf = inf.replace(link = inf.substring(sub, inf.length()), "");
			wp.setInfo(inf);
			link = link.replace("E-Mail: ", "");
			wp.setLink("http://" + link);
			break;
		case 4:
//			inf = inf.replace("Interpretato da :", ""); ?? nekje ja, nekje ne... svašta
			inf = inf.replace("Indirizzo:", "\n");
			inf = inf.replace("Telefono:", "\n");
			inf = inf.replace("Fax:", "\n");
			sub = inf.indexOf("e-mail:");
			inf = inf.replace(link = inf.substring(sub, inf.length()), "");
			wp.setInfo(inf);
			link = link.replace("e-mail: ", "");
			wp.setLink("http://" + link);
			break;
		case 5:
			inf = inf.replace("Adresse:", "\n");
			inf = inf.replace("Téléphone:", "\n");
			inf = inf.replace("Faks:", "\n");
			sub = inf.indexOf("e-mail:");
			inf = inf.replace(link = inf.substring(sub, inf.length()), "");
			wp.setInfo(inf);
			link = link.replace("e-mail: ", "");
			wp.setLink("http://" + link);
			break;
		case 6:
			break;
		case 7:
			break;
		}
		
		System.out.println(inf);
		System.out.println(link);
		
		// kateri desc atributi
		int[] strunjan = {4,5,6,9,10,11,12,14,16};
		int[] portoroz = {4,5,6,9,10,12,13,14,15,16,17,18};
//		int[] portoroz = {4,5}; //prirejeno ruski
		int[] moravske = {4,5,6,9,10,11,12,14,19};
		int[] catez = {4,5,6,8,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,26,27,28,29,30,31,34};
//		int[] catez = {4,5,6,8,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,26,27,28}; //prirejeno italiano
		int[] dobrna = {4,5,7,9,10,11,12,13,14,17,21};
		int[] dolenjske = {4,5,6,7,9,11,12,13,14,16,18,19,20,24};
		int[] lendava = {3,4,5,6};
		int[] podcetrtek = {3,4,5,6,9,10,11,12,14,15,16,18};
		int[] ptuj = {4,5,6,9,10,11,12,13,14,15}; // 16 [0-15]
		int[] smarjeske = {4,5,6,9,10,11,12,13,14,15,16,17}; // 18 [0-17]
		int[] topolsica = {4,5,6,9,10,11,12,13,14,15,16}; // 17 [0-16]
		int[] zrece = {4,5,6,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}; // 25 [0-24]
		int[] lasko = {4,5,6,9,10,11,13,14};
		int[] radenci = {4,5,6,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28}; // 29 [0-28]
		int[] rogaska = {4,5,6,9,10,11,12,13}; // 14 [0-13]
		
		int stevec = 0;
		
		switch(klic) {
    	case 0: // strunjan
    		desc = new String[strunjan.length];
    		for (int i : strunjan) {
    			desc[stevec] = opis[i].getText().toString();
    			stevec++;
    		}
    		wp.setDescription(desc);
    		break;
		case 1: // portoroz
			desc = new String[portoroz.length];
			for (int i : portoroz) {
    			desc[stevec] = opis[i].getText().toString();
    			stevec++;
			}
    		wp.setDescription(desc);
    		break;
		case 2: // moravske
			desc = new String[moravske.length];
			for (int i : moravske) {
    			desc[stevec] = opis[i].getText().toString();
    			stevec++;
			}
    		wp.setDescription(desc);
    		break;
		case 3: // catez
			desc = new String[catez.length];
			for (int i : catez) {
    			desc[stevec] = opis[i].getText().toString();
    			stevec++;
			}
    		wp.setDescription(desc);
    		break;
		case 4: // dobrna
			desc = new String[dobrna.length];
			for (int i : dobrna) {
    			desc[stevec] = opis[i].getText().toString();
    			stevec++;
			}
    		wp.setDescription(desc);
    		break;
		case 5: // dolenjske
			desc = new String[dolenjske.length];
			for (int i : dolenjske) {
    			desc[stevec] = opis[i].getText().toString();
    			stevec++;
			}
    		wp.setDescription(desc);
    		break;
		case 6: // lendava
			desc = new String[lendava.length];
			for (int i : lendava) {
				desc[stevec] = opis[i].getText().toString();
				stevec++;
			}
    		wp.setDescription(desc);
    		break;
		case 7: // podcetrtek
			desc = new String[podcetrtek.length];
			for (int i : podcetrtek) {
    			desc[stevec] = opis[i].getText().toString();
    			stevec++;
			}
    		wp.setDescription(desc);
    		break;
		case 8: // ptuj
			desc = new String[ptuj.length];
			for (int i : ptuj) {
    			desc[stevec] = opis[i].getText().toString();
    			stevec++;
			}
    		wp.setDescription(desc);
    		break;
		case 9: // smarjeske
			desc = new String[smarjeske.length];
			for (int i : smarjeske) {
    			desc[stevec] = opis[i].getText().toString();
    			stevec++;
			}
    		wp.setDescription(desc);
    		break;
		case 10: // topolsica
			desc = new String[topolsica.length];
			for (int i : topolsica) {
    			desc[stevec] = opis[i].getText().toString();
    			stevec++;
			}
    		wp.setDescription(desc);
    		break;
		case 11: // zrece
			desc = new String[zrece.length];
			for (int i : zrece) {
    			desc[stevec] = opis[i].getText().toString();
    			stevec++;
			}
    		wp.setDescription(desc);
    		break;
		case 12: // lasko
			desc = new String[lasko.length];
			for (int i : lasko) {
    			desc[stevec] = opis[i].getText().toString();
    			stevec++;
			}
    		wp.setDescription(desc);
    		break;
		case 13: // radenci
			desc = new String[radenci.length];
			for (int i : radenci) {
    			desc[stevec] = opis[i].getText().toString();
    			stevec++;
			}
    		wp.setDescription(desc);
    		break;
		case 14: // rogaska
			desc = new String[rogaska.length];
			for (int i : rogaska) {
    			desc[stevec] = opis[i].getText().toString();
    			stevec++;
			}
    		wp.setDescription(desc);
    		break;
    	default:
    		break;
    	}
	}
	
	public TagNode xmlCleaner(String webAddress){
		CleanerProperties props = new CleanerProperties();
		props.setTranslateSpecialEntities(true);
		props.setTransResCharsToNCR(true);
		props.setOmitComments(true);
		TagNode tagNode;
		try {
			tagNode = new HtmlCleaner(props).clean(new URL(webAddress));
			return tagNode;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public TagNode[] findInfo(TagNode node, String XPathExpression) {
		TagNode[] tagNode = null;
		Object[] obj = null;
		try {
			obj = node.evaluateXPath(XPathExpression);
			
			tagNode = new TagNode[obj.length];
			
			for(int i = 0; i < obj.length; i++)
				tagNode[i] = (TagNode) obj[i];
			
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		
		return tagNode;
	}

	public String whichZdravilisce(int zdr, int lg) {
    	switch(zdr) {
    	case 0: // strunjan
    		return MAIN_URL + "Talaso-Strunjan.htm?zdravilisce=15" + PREDPONA_ZA_LANG + lg;
		case 1: // portoroz
			return MAIN_URL + "Terme-Wellness-LifeClass.htm?zdravilisce=7" + PREDPONA_ZA_LANG + lg;
		case 2: // moravske
			return MAIN_URL + "Terme-3000-Moravske-Toplice.htm?zdravilisce=56" + PREDPONA_ZA_LANG + lg;
		case 3: // catez
			return MAIN_URL + "Terme-%C4%8Cate%C5%BE.htm?zdravilisce=1" + PREDPONA_ZA_LANG + lg;
		case 4: // dobrna
			return MAIN_URL + "Terme-Dobrna.htm?zdravilisce=2" + PREDPONA_ZA_LANG + lg;
		case 5: // dolenjske
			return MAIN_URL + "Terme-Dolenjske-Toplice.htm?zdravilisce=43" + PREDPONA_ZA_LANG + lg;
		case 6: // lendava
			return MAIN_URL + "Terme-Lendava.htm?zdravilisce=55" + PREDPONA_ZA_LANG + lg;
		case 7: // podcetrtek
			return MAIN_URL + "Terme-Olimia.htm?zdravilisce=57" + PREDPONA_ZA_LANG + lg;
		case 8: // ptuj
			return MAIN_URL + "Terme-Ptuj.htm?zdravilisce=6" + PREDPONA_ZA_LANG + lg;
		case 9: // smarjeske
			return MAIN_URL + "Terme-%C5%A0marje%C5%A1ke-Toplice.htm?zdravilisce=25" + PREDPONA_ZA_LANG + lg;
		case 10: // topolsica
			return MAIN_URL + "Terme-Topol%C5%A1ica.htm?zdravilisce=29" + PREDPONA_ZA_LANG + lg;
		case 11: // zrece
			return MAIN_URL + "Terme-Zre%C4%8De.htm?zdravilisce=33" + PREDPONA_ZA_LANG + lg;
		case 12: // lasko
			return MAIN_URL + "Thermana-d-d-,-dru%C5%BEba-dobrega-po%C4%8Dutja.htm?zdravilisce=44" + PREDPONA_ZA_LANG + lg;
		case 13: // radenci
			return MAIN_URL + "Zdravili%C5%A1%C4%8De-Radenci.htm?zdravilisce=8" + PREDPONA_ZA_LANG + lg;
		case 14: // rogaska
			return MAIN_URL + "Zdravili%C5%A1%C4%8De-Roga%C5%A1ka.htm?zdravilisce=5" + PREDPONA_ZA_LANG + lg;
    	default:
    		return "Error";
    	}
    }
	
	public String zaNaAndroid() {
		String allTogether = "OPIS#";
		for (int i = 0; i < wp.getDescription().length; i++)
			allTogether = allTogether + wp.getDescription()[i] + "#";
		allTogether = allTogether + wp.getTitle() + "€" + wp.getInfo() + "€" + wp.getImage_addr() + "€" + wp.getLink();
		System.out.println(allTogether);
		return allTogether;
	}
	
	public String getLinks(TagNode tagNode) {
		TagNode[] seznamVseh = findInfo(tagNode, "//div[@class='box2']");
		String temp;
		temp = seznamVseh[0].getText().toString();
		temp = temp.replace("&nbsp;&nbsp;", "");
		temp = temp.substring(8, temp.length());
		System.out.println(temp);
		return temp;
	}
	
	public static void main(String [] args){
		new Parser();
	}

	public Parser(){
		zdravilisca(1,1);
	}
}
