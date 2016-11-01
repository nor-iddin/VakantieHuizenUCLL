package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import versie1.Datum;
import versie1.DatumException;

public class DatumTest {
//	
//Re-usable objects
//@Before, code wordt uitgevoerd voor elke test
//
	Datum autoDatum;
	
	@Before
	public void setUp(){
		autoDatum = new Datum();
	}

	
//
//@Rule, gebruikt om onze exceptions te testen
//
	@Rule
    public ExpectedException thrown = ExpectedException.none();

	
//	
//Default constructor
//
	@Test
	public void test_DefaultConstructor_Dag_Heeft_Geldig_Bereik() 
	{		
		assertTrue( "Value out of range", (0 < autoDatum.getDag()) && (autoDatum.getDag() < 32));
	}
	
	@Test
	public void test_DefaultConstructor_Maand_Heeft_Geldig_Bereik() 
	{			
		assertTrue( "Value out of range", (0 < autoDatum.getMaand()) && (autoDatum.getMaand() < 13));
	}
	
	
//
//Datum(datum) constructor
//
	@Test
	public void test_DatumDatum_Constructor() throws DatumException
	{
		Datum datum = new Datum(autoDatum);
		assertEquals(datum.getDag(),autoDatum.getDag());
		assertEquals(datum.getMaand(),autoDatum.getMaand());
		assertEquals(datum.getJaar(),autoDatum.getJaar());
	}
	
	@Test
	public void test_DatumDatum_Constructor_nullException() throws DatumException
	{
		autoDatum = null;
		thrown.expect(DatumException.class);	    
	    thrown.expectMessage("Het ingevoerde object is leeg.");
	    Datum datum = new Datum(autoDatum);
	}

	
//
//Datum(int,int,int) constructor
//
	@Test
	public void test_Datum_int_Constructor_DayNullException() throws DatumException
	{
		thrown.expect(DatumException.class);	    
	    thrown.expectMessage("Een of meerdere van de parameters bevat een nulwaarde.");
	    Datum datum = new Datum(0,1,1);
	}
	
	@Test
	public void test_Datum_int_Constructor_MonthNullException() throws DatumException
	{
		thrown.expect(DatumException.class);	    
	    thrown.expectMessage("Een of meerdere van de parameters bevat een nulwaarde.");
	    Datum datum = new Datum(1,0,1);
	}
	
	@Test
	public void test_Datum_int_Constructor_YearNullException() throws DatumException
	{
		thrown.expect(DatumException.class);	    
	    thrown.expectMessage("Een of meerdere van de parameters bevat een nulwaarde.");
	    Datum datum = new Datum(1,1,0);
	}
	
	
//	
//testen van dagenSinds1900()
//
	@Test
	public void test_dagenSinds1900_geldig() throws DatumException{	
		Datum datum = new Datum(1,1,1900);
		assertEquals(1,datum.dagenSinds1900());
	}
	@Test
	public void test_dagenSinds1900_geldig_2() throws DatumException{	
		Datum datum = new Datum(31,12,1900);
		assertEquals(365,datum.dagenSinds1900());
	}	
	@Test
	public void test_dagenSinds1900_geldig_3() throws DatumException{	
		Datum datum = new Datum(1,1,1901);
		assertEquals(366,datum.dagenSinds1900());
	}	
	@Test
	public void test_dagenSinds1900_geldig_met_schrikeljaar() throws DatumException{	
		Datum datum = new Datum(1,1,1905);
		assertEquals(1827,datum.dagenSinds1900());
	}		
	@Test
	public void test_dagenSinds1900_geldig_met_schrikeljaar_2() throws DatumException{	
		Datum datum = new Datum(1,3,1904);
		assertEquals(1521,datum.dagenSinds1900());
	}		

	
//
//test van verlaagMetAantalDagen
//
	@Test
	public void test_verlaagMetAantalDagen_geldig_0() throws DatumException{	
		Datum datum = new Datum(1,1,2016);
		Datum newdatum = datum.verlaagMetAantalDagen(0);
		assertEquals(datum,newdatum);
	}
	@Test
	public void test_verlaagMetAantalDagen_geldig_positief() throws DatumException{	
		Datum datum = new Datum(8,1,2016);
		Datum newdatum = datum.verlaagMetAantalDagen(5);
		assertEquals(new Datum(13,1,2016),newdatum);
	}
	@Test
	public void test_verlaagMetAantalDagen_geldig_negatief() throws DatumException{	
		Datum datum = new Datum(8,1,2016);
		Datum newdatum = datum.verlaagMetAantalDagen(-5);
		assertEquals(new Datum(3,1,2016),newdatum);
	}

	
//
//testen van getDagNaam()
//
	@Test
	public void test_getDagNaam_geldig() throws DatumException{	
		Datum datum = new Datum(1,1,2000);
		assertTrue(datum.getDagNaam()=="Zaterdag");
	}
	@Test
	public void test_getDagNaam_geldig_plus_2() throws DatumException{	
		Datum datum = new Datum(3,1,2000);
		assertTrue(datum.getDagNaam()=="Maandag");
	}
	@Test
	public void test_getDagNaam_geldig_schrikeljaar2000() throws DatumException{	
		Datum datum = new Datum(29,2,2000);
		assertTrue(datum.getDagNaam()=="Dinsdag");
	}
	@Test
	public void test_getDagNaam_geldig_schrikeljaar2000_2() throws DatumException{	
		Datum datum = new Datum(1,3,2000);
		assertTrue(datum.getDagNaam()=="Woensdag");
	}
	@Test
	public void test_getDagNaam_geldig_2() throws DatumException{	
		Datum datum = new Datum(18,10,2016);
		assertTrue("Foute dag",datum.getDagNaam()=="Dinsdag");
	}
	@Test
	public void test_getDagNaam_ongeldig() throws DatumException{	
		Datum datum = new Datum(18,10,2016);
		assertFalse("Foute dag",datum.getDagNaam()=="Donderdag");
	}

	
//	
//testen van isSchrikelJaar
//
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