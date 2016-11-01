package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import versie1.Datum;

public class DatumTest {
//	
//Re-usable objects
//@Before code wordt uitgevoerd voor elke test
//
	Datum autoDatum;
	
	@Before
	public void setUp(){
		autoDatum = new Datum();
	}
	
//
//Default constructor
//
	@Test
	public void test_DefaultConstructor_Dag_Heeft_Geldig_Bereik() {		
		assertTrue( "Value out of range", (0 < autoDatum.getDag()) && (autoDatum.getDag() < 32));
	}
	
	@Test
	public void test_DefaultConstructor_Maand_Heeft_Geldig_Bereik() {			
		assertTrue( "Value out of range", (0 < autoDatum.getMaand()) && (autoDatum.getMaand() < 13));
	}
	
//
//datum(datum) constructor
//
	@Test
	public void test_DatumDatumConstructor(){
		Datum datum = new Datum(autoDatum);
		assertEquals(datum.getDag(),autoDatum.getDag());
		assertEquals(datum.getMaand(),autoDatum.getMaand());
		assertEquals(datum.getJaar(),autoDatum.getJaar());
	}
	
	/**
	 * other constructor
	 */
/*	@Test
	public void test_Constructor_dagen_geldig_() {	
		Datum datum = new Datum(0);		
		assertEquals(new Datum(1,1,1900),datum);
	}
*/
	

	/**testen van dagenSinds1900()
	 * 
	 */
	@Test
	public void test_dagenSinds1900_geldig() {	
		Datum datum = new Datum(1,1,1900);
		assertEquals(1,datum.dagenSinds1900());
	}
	@Test
	public void test_dagenSinds1900_geldig_2() {	
		Datum datum = new Datum(31,12,1900);
		assertEquals(365,datum.dagenSinds1900());
	}	
	@Test
	public void test_dagenSinds1900_geldig_3() {	
		Datum datum = new Datum(1,1,1901);
		assertEquals(366,datum.dagenSinds1900());
	}	
	@Test
	public void test_dagenSinds1900_geldig_met_schrikeljaar() {	
		Datum datum = new Datum(1,1,1905);
		assertEquals(1827,datum.dagenSinds1900());
	}		
	@Test
	public void test_dagenSinds1900_geldig_met_schrikeljaar_2() {	
		Datum datum = new Datum(1,3,1904);
		assertEquals(1521,datum.dagenSinds1900());
	}		
	
	/**test van verlaagMetAantalDagen
	 * 
	 */
	@Test
	public void test_verlaagMetAantalDagen_geldig_0() {	
		Datum datum = new Datum(1,1,2016);
		Datum newdatum = datum.verlaagMetAantalDagen(0);
		assertEquals(datum,newdatum);
	}
	@Test
	public void test_verlaagMetAantalDagen_geldig_positief() {	
		Datum datum = new Datum(8,1,2016);
		Datum newdatum = datum.verlaagMetAantalDagen(5);
		assertEquals(new Datum(13,1,2016),newdatum);
	}
	@Test
	public void test_verlaagMetAantalDagen_geldig_negatief() {	
		Datum datum = new Datum(8,1,2016);
		Datum newdatum = datum.verlaagMetAantalDagen(-5);
		assertEquals(new Datum(3,1,2016),newdatum);
	}

	/**testen van getDagNaam()
	 * 
	 */
	@Test
	public void test_getDagNaam_geldig() {	
		Datum datum = new Datum(1,1,2000);
		assertTrue(datum.getDagNaam()=="Zaterdag");
	}
	@Test
	public void test_getDagNaam_geldig_plus_2() {	
		Datum datum = new Datum(3,1,2000);
		assertTrue(datum.getDagNaam()=="Maandag");
	}
	@Test
	public void test_getDagNaam_geldig_schrikeljaar2000() {	
		Datum datum = new Datum(29,2,2000);
		assertTrue(datum.getDagNaam()=="Dinsdag");
	}
	@Test
	public void test_getDagNaam_geldig_schrikeljaar2000_2() {	
		Datum datum = new Datum(1,3,2000);
		assertTrue(datum.getDagNaam()=="Woensdag");
	}
	@Test
	public void test_getDagNaam_geldig_2() {	
		Datum datum = new Datum(18,10,2016);
		assertTrue("Foute dag",datum.getDagNaam()=="Dinsdag");
	}
	@Test
	public void test_getDagNaam_ongeldig() {	
		Datum datum = new Datum(18,10,2016);
		assertFalse("Foute dag",datum.getDagNaam()=="Donderdag");
	}

	
	/**testen van isSchrikelJaar
	 * 
	 */
	@Test
	public void test_IsSchrikelJaar_geldig() {	
		assertTrue(autoDatum.isSchrikelJaar(2016));
	}
	
	@Test
	public void test_IsSchrikelJaar_ongeldig() {	
		assertFalse(autoDatum.isSchrikelJaar(2015));
	}
	
	@Test
	public void test_IsSchrikelJaar_ongeldig_100() {	
		assertFalse(autoDatum.isSchrikelJaar(1900));
	}
	@Test
	public void test_IsSchrikelJaar_geldig_2000() {	
		assertTrue(autoDatum.isSchrikelJaar(2000));
	}
	
/*	@Test
	public void test_IsSchrikelJaar_ongeldig_jaar() {	
		Datum datum = new Datum();
		assertFalse(datum.isSchrikelJaar(8888888));
	}*/
}