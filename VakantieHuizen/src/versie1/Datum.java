package versie1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.lang.Throwable;
import java.lang.Exception;
import java.lang.RuntimeException;


//de Datumklasse moet de Comparable<Datum> interface implementeren en bevat dus een  compareTo methode. Door comparable te maken kan je later datums sorteren.
//Nor-Iddin: Exceptions aangepast



//import time.Datum;

//de Datumklasse moet de Comparable<Datum> interface implementeren en bevat dus een  compareTo methode. Door comparable te maken kan je later datums sorteren.


public final class Datum implements Comparable<Datum>{ 
	private int dag = 0, maand = 0, jaar = 0; // variabelen final private maken zorgt voor fouten "Variables not initialized", een default waarde meegeven verhelpt dit ook niet

	/**
	 * Junit feedback
	 * --------------
	 * Wij slagen er niet in om de code te runnen:
	java.lang.Error: Unresolved compilation problems: 
	The type Datum must implement the inherited abstract method Comparable<Datum>.compareTo(Datum)
	The nested type Datum cannot hide an enclosing type
	
	Zonder compileerbare code, kunnen we geen Junit scripts runnen...
	Gelieve onafgewerkte implementaties in comments te laten en Github code telkens 'uitvoerbaar' te houden.
	Maandag effe bespreken
	Kristof/Christophe 2-10-2016
	 */
	
	/**
	 * constructor zonder parameters (object datum gelijk aan de systeemdatum)
	 */
	public Datum(){
		try
		{
			Calendar cal = Calendar.getInstance();
			dag = cal.get(Calendar.DAY_OF_MONTH);
			maand = Calendar.MONTH;
			jaar = Calendar.YEAR;
			
			if(dag == 0 || maand == 0 || jaar == 0){
				
				throw new DatumKonNietOpgehaaldWordenException("De datum kon niet opgehaald worden.");
			}
		}
		catch (DatumKonNietOpgehaaldWordenException ex)
		{
			System.out.println(ex.toString());
		}	
	}
	
	/**
	 * constructor met een datum object als parameter 
	 * @param invoerDatum
	 */
	public Datum(Datum invoerDatum){
		try
		{
			if(invoerDatum != null){
				
				if(invoerDatum.dag > 0 && invoerDatum.dag < 32){
					
					if(invoerDatum.maand > 0 && invoerDatum.maand < 13){
						if(invoerDatum.jaar > 0){
							
							dag = invoerDatum.dag;
							maand = invoerDatum.maand;
							jaar = invoerDatum.jaar;
						}
						else
						{
							throw new OngeldigJaarDatumObjectException("Het object bevat een ongeldig jaar.");
						}
					}
					else
					{
						throw new OngeldigeMaandDatumException("Het object bevat een ongeldige maand");
					}
				}
				else
				{
					throw new OngeldigeDagDatumException("Het object bevat een ongeldige dag");
				}
			}
			else
			{
				throw new LeegDatumObjectException("Het ingevoerde object is leeg.");
			}	
		}
		catch (Exception ex){
			
			System.out.println(ex.toString());
		}
	}
	
	/**
	 * constructor met parameters dag, maand en jaar ( 3 gehele getallen).
	 * @param invoerDag
	 * @param invoerMaand
	 * @param invoerJaar
	 */
	public Datum(int invoerDag, int invoerMaand, int invoerJaar){
		try
		{
			if(invoerDag != 0 && invoerMaand != 0 && invoerJaar !=0){
				if(invoerDag < 32){
					if(invoerMaand < 13){
						
						dag = invoerDag;
						maand = invoerMaand;
						jaar = invoerJaar;
					}
					else
					{
						throw new OngeldigeMaandDatumException("De ingevoerde maand is ongeldig.");
					}
				}
				else
				{
					throw new OngeldigeDagDatumException("De ingevoerde dag is ongeldig.");
				}
			}
			else
			{
				throw new OngeldigeParameterDatumException("Een of meerdere van de parameters bevat een nulwaarde.");
			}
		}
		catch (Exception ex){
			
			System.out.println(ex.toString());
		}
	}
	
	
	/**
	 * constructor met een String als parameter. In deze String zit de datum in DDMMJJJJ formaat maar tussen de dag, maand en jaar staat een scheidingsteken (Vb 12/05/2009).
	 * De toegelaten scheidingstekens zijn koppelteken(-), schuine streep(/) of blanco.
	 * Dag en maand moet ingegeven worden met 2 cijfers (Vb 05), jaartal met 4 cijfers (Vb 0567)
	 * @param invoerDatum
	 */
	public Datum(String invoerDatum){
		try
		{
			if(invoerDatum != ""){
				// - en / uit de string filteren 
				String omgezetteDatum = invoerDatum.replaceAll("[-/]", "");			
				
				
				// dag, maand en jaar uit de string filteren + foutafhandeling
				dag = Integer.parseInt(omgezetteDatum.substring(0, 2));
				if(dag <= 0 && dag >= 32){
					
					throw new OngeldigeDagDatumException("Datum bevat een ongeldige dag.");
				}
				
				maand = Integer.parseInt(omgezetteDatum.substring(2, 4));
				if(maand <= 0 && maand >= 13){
					throw new OngeldigeMaandDatumException("Datum bevat een ongeldige maand.");
				}
				
				jaar = Integer.parseInt(omgezetteDatum.substring(4,8));
				if(jaar <= 0){
					throw new OngeldigJaarDatumObjectException("Datum bevat een ongeldig jaartal.");
				}
						
			}
			else
			{
				throw new LeegDatumObjectException("De invoerDatum is leeg");
			}
		}
		catch (Exception ex){
			System.out.println(ex.toString());
		}
	}

	/**constructor creates datum object from long
	 * all days since 1/1/1900
	 * @param days
	 */
	public Datum(long days){
		int dagen=0,maanden=0,jaren,daysleft,dagenInFebruari;
		jaren=(int)days / 365; //jaren uithalen
		jaren+=1900; //het is tegenover het jaar 1900
		daysleft=(int)days%365; //dagen die over zijn
		daysleft++; //een dag bij tellen, want 0 january bestat niet
		daysleft -= jaren/4; //Als er schrikeljaren zijn aftrekken (366 dagen)
		daysleft += jaren/100; //tenzij er een 100 jaar in zit, terug bijtellen
		daysleft -= jaren/400; //400 jaar is dan wel een schrikeljaar0
		
		//nu maanden uithalen; en schrikeljaar?
		if (jaren%400==0) dagenInFebruari=29; 
		else if((jaren % 4 == 0) && (jaren % 100 != 0)) dagenInFebruari=29;
		else dagenInFebruari=28;
		//als het huidige jaar een schrikeljaar is, hebben we een dag teveel afgetrokken
		if(dagenInFebruari==29) daysleft++;
		
		if(daysleft>366) //Als dit voorkomt hebben we fout geteld
		{
			maanden=0;
			dagen=daysleft;
		}
		else if(daysleft > (31+dagenInFebruari+31+30+31+30+31+31+30+31+30))
		{
			maanden = 12;
			dagen = daysleft-(31+dagenInFebruari+31+30+31+30+31+31+30+31+30);
		}
		else if(daysleft > (31+dagenInFebruari+31+30+31+30+31+31+30+31))
		{
			maanden = 11;
			dagen = daysleft-(31+dagenInFebruari+31+30+31+30+31+31+30+31);
		}
		else if(daysleft > (31+dagenInFebruari+31+30+31+30+31+31+30))
		{
			maanden = 10;
			dagen = daysleft-(31+dagenInFebruari+31+30+31+30+31+31+30);
		}
		else if(daysleft > (31+dagenInFebruari+31+30+31+30+31+31))
		{
			maanden = 9;
			dagen = daysleft-(31+dagenInFebruari+31+30+31+30+31+31);
		}
		else if(daysleft > (31+dagenInFebruari+31+30+31+30+31))
		{
			maanden = 8;
			dagen = daysleft-(31+dagenInFebruari+31+30+31+30+31);
		}
		else if(daysleft > (31+dagenInFebruari+31+30+31+30))
		{
			maanden = 7;
			dagen = daysleft-(31+dagenInFebruari+31+30+31+30);
		}
		else if(daysleft > (31+dagenInFebruari+31+30+31))
		{
			maanden = 6;
			dagen = daysleft-(31+dagenInFebruari+31+30+31);
		}
		else if(daysleft > (31+dagenInFebruari+31+30))
		{
			maanden = 5;
			dagen = daysleft-(31+dagenInFebruari+31+30);
		}
		else if(daysleft > (31+dagenInFebruari+31))
		{
			maanden = 4;
			dagen = daysleft-(31+dagenInFebruari+31);
		}
		else if(daysleft > (31+dagenInFebruari))
		{
			maanden = 3;
			dagen = daysleft-(31+dagenInFebruari);
		}
		else if(daysleft > 31)
		{
			maanden = 2;
			dagen = daysleft-31;
		}
		else //if(daysleft <= 31)
		{
			maanden = 1;
			dagen = daysleft;
		}

		dag=dagen;
		maand=maanden;
		jaar=jaren;
	}



	//geters
	public int getDag()
	{
		return dag;
	}
	public int getMaand()
	{
		return maand;
	}
	public int getJaar()
	{
		return jaar;
	}

	/**Functie voor makelijkere met dzgen te tellen
	 * 
	 * @param niet nodig
	 * @return
	 */
	public long dagenSinds1900(){
		//dus jaren + schrikkeljaren
		long dagen,jaren;
		jaren=(long)this.getJaar()-1900;
		dagen=365*jaren;
		dagen+=jaren/4; //schrikeljaren bijtellen
		dagen-=jaren/100;//tenzijn 100 jaar, aftellen
		dagen+= jaren/400; //400 jaar is dan wel een schrikeljaar, let op wij tellen vanaf 1900, niet 0

		//nu maanden
		if( ( (jaar%4 == 0) && (jaar%100 !=0) || this.getJaar()%400==0 ) && maand <3 ) dagen--;//schrikeljaar en vroeger dan maart,dag is al in het jar geteld 
		
		switch(this.getMaand())
		{
			//januri niet tellen,
			//geen break, dan wordt de rest eronder ook uitgevoerd
			case 12: dagen+=30;
			case 11: dagen+=31;
			case 10: dagen+=30;
			case 9: dagen+=31;
			case 8: dagen+=31;
			case 7: dagen+=30;
			case 6: dagen+=31;
			case 5: dagen+=30;
			case 4: dagen+=31;
			case 3: dagen+=28;
			case 2: dagen+=31;
			 break;
			default: dagen+=0; ///zou niet mogen voorkomen
		}
		dagen+=this.getDag(); //de dagen
		return dagen;
	}


	/**
	 * geeft een datum in Amerikaans formaat terug 
	 * @return
	 */
	public String getDatumInAmerikaansFormaat(){
		
		String amDatum = Integer.toString(jaar)+ "/" + Integer.toString(maand) + "/" + Integer.toString(dag);
		return amDatum;
	}
	
	/**
	 * geeft een datum in Europees formaat terug
	 * @return
	 */
	public String getDatumInEuropeesFormaat(){
		String euDatum = Integer.toString(dag) + "/" + Integer.toString(maand) + "/" + Integer.toString(jaar);
		return euDatum;
	}
	
	
	/**
	 *nor-iddin: breaks toegevoegd
	 *
	*/
	public String toString(){
		String maandVolluit = "";
		
		switch(maand){
		
		case 1:
			maandVolluit = "Januari";
			break;
		case 2:
			maandVolluit = "Februari";
			break;
		case 3:
			maandVolluit = "Maart";
			break;
		case 4:
			maandVolluit = "April";
			break;
		case 5:
			maandVolluit = "Mei";
			break;
		case 6:
			maandVolluit = "Juni";
			break;
		case 7:
			maandVolluit = "Juli";
			break;
		case 8:
			maandVolluit = "Augustus";
			break;
		case 9:
			maandVolluit = "September";
			break;
		case 10: 
			maandVolluit = "October";
			break;
		case 11:
			maandVolluit = "November";
			break;
		case 12:
			maandVolluit = "December";
			break;
				
		}
		return Integer.toString(dag) + " " + maandVolluit + " " + Integer.toString(jaar);
	}
	
	/**boolean komtVoor (Datum d) : bepaalt of het het huidig datum object in de tijd voor datum d ligt
	 * 
	 */
/*	public boolean komtVoor(Datum d){

		Calendar dezeDatum = Calendar.getInstance();
		dezeDatum.set(jaar, maand, dag, 0, 0);  

		Calendar dCal = Calendar.getInstance();
		dCal.set(d.jaar, d.maand, d.dag, 0, 0);  

		boolean isBefore =  dCal.before(dezeDatum);
	
		return isBefore;
	}
*/
	public boolean komtVoor(Datum d){
		if( d.dagenSinds1900() < this.dagenSinds1900() ) return true;
		else return false;
	}
	
	/**boolean komtNa (Datum d) : bepaalt of het het huidig datum object in de tijd na datum d ligt
	 * 
	 */
/*	public boolean komtNa(Datum d){
		
		Calendar huidigDatumObject = Calendar.getInstance();
		huidigDatumObject.set(jaar, maand, dag, 0, 0);  

		Calendar dCal = Calendar.getInstance();
		dCal.set(d.jaar, d.maand, d.dag, 0, 0);  

		boolean isAfter =  dCal.after(huidigDatumObject);
			
		return isAfter;
	}
*/
	public boolean komtNa(Datum d){
		if( d.dagenSinds1900() > this.dagenSinds1900() ) return true;
		else return false;
	}
	
	
	/**int verschilInMaanden (Datum d) : bepaalt het verschil in volledige maanden tussen datum d en huidig datumobject 
	 * (vb 01032007 en 03012009 -> 22 maanden)
	 * 
	 */
/*	public int verschilInMaanden (Datum d){

		Calendar huidigDatumObject = Calendar.getInstance();
		huidigDatumObject.set(jaar, maand, dag, 0, 0);  

		Calendar dCal = Calendar.getInstance();
		dCal.set(d.jaar, d.maand, d.dag, 0, 0);
			
		int verschilJaren = dCal.get(Calendar.YEAR) - huidigDatumObject.get(Calendar.YEAR);
		int verschilMaanden = verschilJaren * 12 + dCal.get(Calendar.MONTH) - huidigDatumObject.get(Calendar.MONTH);
			
		return verschilMaanden;
	}
	*/
	public int verschilInMaanden (Datum d){
		//verschik in dagen / 30 ?
		int verchilMaanden=0;
		if(d.getJaar() == this.getJaar())
		{
			if( d.getMaand() > this.getMaand() ) verchilMaanden= d.getMaand() - this.getMaand();
			else verchilMaanden= this.getMaand() - d.getMaand();
		}
		else if(d.getJaar() < this.getJaar()) verchilMaanden = (d.getJaar()-1) *12 + ( 12-d.getMaand() ) + this.getMaand();	
		else verchilMaanden = (this.getJaar()-1) *12 + ( 12-this.getMaand() ) + d.getMaand();
			
		return verchilMaanden; //altijd possitief, 
	}
		
	/**int verschilInDagen (Datum d) : bepaalt het verschil in dagen tussen datum d en huidig datumobject 
	 * 
	 */
/*	public long verschilInDagen (Datum d){
		Calendar datum1 = Calendar.getInstance();
		datum1.set(jaar, maand, dag, 0, 0);  
		Date dd1 =  datum1.getTime();
		    
		Calendar datum2 = Calendar.getInstance();
		datum2.set(d.jaar, d.maand, d.dag, 0, 0);
		Date dd =  datum2.getTime();
		     
		long diff = dd1.getTime() - dd.getTime();
			
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
*/
	public long verschilInDagen (Datum d){
		//omzetten naar long, dagen sinds 1900
		return d.dagenSinds1900()-this.dagenSinds1900();
	}
	
	/**Datum verhoogMetAantalDagen (long aantalDagen): maakt een nieuw datum object dat 
	* gelijk is aan het huidige datumobject verhoogt met aantalDagen. 
	* 
	*/
/*	public Date verhoogMetAantalDagen (long aantalDagen){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date huidigDatumObject = null;
		try {
				
			huidigDatumObject= simpleDateFormat.parse(dag + "/" + maand + "/"+ jaar);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(huidigDatumObject);

			calendar.add(Calendar.DAY_OF_MONTH, (int)aantalDagen);
				
			dag = Calendar.DAY_OF_MONTH;
			maand = Calendar.MONTH;
			jaar = Calendar.YEAR;
				
			huidigDatumObject = calendar.getTime();
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		return huidigDatumObject;
	}
*/
	public Datum verhoogMetAantalDagen(long aantalDagen){
		long verschil = this.verschilInDagen(new Datum(1,1,1900));

		return new Datum(verschil+aantalDagen);
	}

	/**Datum verlaagMetAantalDagen (long aantalDagen): maakt een nieuw 
	* datum object dat gelijk is aan het huidige datumobject vermindert met aantalDagen
	*/
/*	public Date verlaagMetAantalDagen (long aantalDagen){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date huidigDatumObject = null;
		try {
				huidigDatumObject= simpleDateFormat.parse(dag + "/" + maand + "/"+ jaar);

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(huidigDatumObject);

				calendar.add(Calendar.DAY_OF_MONTH, (int)-aantalDagen);

				dag = Calendar.DAY_OF_MONTH;
				maand = Calendar.MONTH;
				jaar = Calendar.YEAR;
				
				huidigDatumObject = calendar.getTime();
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		return huidigDatumObject;
	}
*/
	/**Datum verlaagMetAantalDagen (long aantalDagen): maakt een nieuw 
	* datum object dat gelijk is aan het huidige datumobject vermindert met aantalDagen
	* return datum object
	*/
	public Datum verlaagMetAantalDagen (long aantalDagen){
		long verschil = this.verschilInDagen(new Datum(1,1,1900));

		return new Datum(verschil-aantalDagen);
	}

	
	/**String getDagNaam(): geeft de naam van de weekdag (in Nederlands) 
	* terug van de datum  (01/01/2000 was een zaterdag)
	*/
/*	public String getDagNaam (){
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			String dagNaam = "";
			try {
				
				Date huidigDatumObject= simpleDateFormat.parse(dag + "/" + maand + "/"+ jaar);

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(huidigDatumObject);

				
				//huidigDatumObject = calendar.getTime();
				
				// Then get the day of week from the Date based on specific locale.
				String dagVanWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(calendar.getTime());
								
				switch(dagVanWeek){
				
				case "Saturday":
					dagNaam = "zaterdag";
					break;
				case "Sunday":
					dagNaam = "zondag";
					break;
				case "Monday":
					dagNaam = "maandag";
				 	break;
				case "Thuesday":
					dagNaam = "dinsdag";
					break;
				case "Wednesday":
					dagNaam = "woensdag";
					break;
				case "Thursday":
					dagNaam = "donderdag";
					break;
				case "Friday":
					dagNaam = "vrijdag";
					break;
						
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return dagNaam;
	}
*/
	public String getDagNaam (){
		String dagNaam="";
		//dagen sinds 1/1/2000
		Datum zaterdag = new Datum(1,1,2000);
		long verschil = this.dagenSinds1900() - zaterdag.dagenSinds1900();
		int modVerschil=(int)verschil%7;

		//nu vergelijken
		switch(modVerschil)
		{
		  case 0: dagNaam="Zaterdag";
		  	break;
		  case 1: dagNaam="Zondag";
		  	break;
		  case 2: dagNaam="Maandag";
		  	break;
		  case 3: dagNaam="Dinsdag";
		  	break;
		  case 4: dagNaam="Woensdag";
		  	break;
		  case 5: dagNaam="Donderdag";
		  	break;
		  case 6: dagNaam="Vrijdag";
		  	break;
		}
		
		return dagNaam;
	}
		
	/**boolean isSchrikkeljaar(int jaartal)
	* 
	*/
/*	public boolean isSchrikelJaar(int jaarTal){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, jaarTal);
			  
		return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;			  
	}
*/
	public boolean isSchrikelJaar(int jaarTal){
		if( (jaarTal%4==0 && jaarTal%100!=0) || jaarTal%400==0) return true;
		else return false;
	}
	
		
	//overrides-----------------------------------------------------------------------------------------------
	@Override
	public boolean equals(Object datum) {
		if (this == datum)
			return true;
		if (!super.equals(datum))
			return false;
		if (getClass() != datum.getClass())
			return false;
		final Datum other = (Datum) datum;
		if (dag != other.dag)
			return false;
		if (maand != other.maand)
			return false;
		if (jaar != other.jaar)
			return false;
		return true;
	}
	

	@Override
	public int compareTo(Datum o) {
		return 0;
	}
	
/*	public static void main(String[] args)
	{
		Datum datum = new Datum(18,10,2016);
		
		System.out.println("day "+datum.getDag()+" maand "+datum.getMaand()+" jaar "+datum.getJaar());
		System.out.println("nameday "+datum.getDagNaam());
		System.out.println("dagen "+datum.dagenSinds1900());
		Datum d = new Datum(1,1,2000);
		System.out.println("dagen "+d.dagenSinds1900());
		Datum d1 = new Datum(1,1,2001);
		System.out.println("dagen "+d1.dagenSinds1900());
		System.out.println("verschil "+ (d1.dagenSinds1900()-d.dagenSinds1900()) );
	}
*/
}