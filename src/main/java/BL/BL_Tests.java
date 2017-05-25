import static org.junit.Assert.*;

import SharedClasses.Worker;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import SharedClasses.*;

import java.io.File;


public class BL_Tests {

	private BL bl;
	private static DAL dal;
	DAL SQLite;
	
	/************************* Before & After **********************************/
	@AfterClass
	public static void tearDownAfterClass() throws Exception {dal = null;}

	@Before
	public void setUp() throws Exception {
		SQLite=new DAL();
		SQLite.addRole("cashier");
		SQLite.addRole("cleaner");
		SQLite.addRole("manager");
		SQLite.addBank(1, "poalim");
		SQLite.addBank(2, "yahav");
		Worker w1 = new Worker(111111111, "david", "david", "01/01/2017", "...", 5000, "cashier", 1, 12345678, 313);
		SQLite.addWorker(w1);
		Worker w2 = new Worker(222222222, "shimon", "shimon", "02/01/2017", "...", 6000, "manager", 2, 23456789, 323);
		SQLite.addWorker(w2);
		Shift s1 = new Shift(1212, "01/04/2017", "Friday", "morning", 313,111111111);
		SQLite.addShift(s1);
		Shift s2 = new Shift(1213, "01/04/2017", "Friday", "evening", 323, 222222222);
		SQLite.addShift(s2);
		String[] roles = {"cashier","manager"};
		SQLite.chooseRolesInShift(roles, s1);
		String[] roles2 = {"cashier","cleaner","manager"};
		SQLite.chooseRolesInShift(roles2, s2);
		SQLite.addPossibleShift(s1, w1.getId());
		SQLite.addPossibleShift(s2, w1.getId());
		SQLite.addPossibleShift(s1, w2.getId());
		SQLite.addPossibleShift(s2, w2.getId());
		SQLite.placeWorkerInShift(w1.getId(), s1);
		SQLite.placeWorkerInShift(w1.getId(), s2);
		SQLite.placeWorkerInShift(w2.getId(), s1);
		SQLite.placeWorkerInShift(w2.getId(), s2);
		bl = new BL(dal);
	}

	@After
	public void tearDown() throws Exception {
		SQLite.getCon().close();
		File f = new File(System.getProperty("user.dir")+ "\\DB.db");
		f.delete();
		bl = null;
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		dal = new DAL();
		
		Truck truck = new Truck("55-2253-98" , "A", 300.98, 10034.78, 5);
		dal.add(truck);
		Transport transport = new Transport("01/01/2016", "09:54", 2); 
		dal.add(transport);
		
		
		truck = new Truck("87-0376-23" , "B", 3009.98, 10034.78, 5);
		dal.add(truck);
		
		transport = new Transport("01/05/2016", "21:50", 3); 
		dal.add(transport);
		
		transport = new Transport("11/05/2016", "00:50", 8); 
		dal.add(transport);
		truck = new Truck("23-2332-32" , "c", 5238.98, 10374.78, 5);
		dal.add(truck);
		dal.signTruckTotransport(8, "23-2332-32");
		dal.selectTruckTotransport(8, "23-2332-32");
		
		truck = new Truck("76-2332-32" , "c", 2358.98, 10374.78, 5);
		dal.add(truck);
		dal.signTruckTotransport(2, "76-2332-32");
		dal.selectTruckTotransport(2, "76-2332-32");
		
		truck = new Truck("12-3456-79" , "H", 2835.98, 10347.78, 2);
		dal.add(truck);
		
		transport = new Transport("01/08/2016", "21:50", 7); 
		dal.add(transport);
	}
	/***************************************************************************/

	@Test
	public void testLeagalDATE() 
	{
		try {bl.leagalDATE("07/08/20655");}
		catch (NituzException e){assertEquals(e.getMessage(), "illegal DATE format");}
		
		try {bl.leagalDATE("07/13/2017");}
		catch (NituzException e){assertEquals(e.getMessage(), "illegal DATE format");}

		try {bl.leagalDATE("07/0/2017");}
		catch (NituzException e){assertEquals(e.getMessage(), "illegal DATE format");}

		try {bl.leagalDATE("40/08/2017");}
		catch (NituzException e){assertEquals(e.getMessage(), "illegal DATE format");}
		
		try {bl.leagalDATE("31/09/20655");}
		catch (NituzException e){assertEquals(e.getMessage(), "illegal DATE format");}
		
		try {bl.leagalDATE("dk/kd/jfkd");}
		catch (NituzException e){assertEquals(e.getMessage(), "illegal DATE format");}
		
		try {assertTrue(bl.leagalDATE("06/03/2017"));}
		catch (NituzException e) {fail(e.getMessage());}
	}
	
	@Test
	public void tetsLeagalID()
	{
		try {bl.legalID("74793843");}
		catch (NituzException e){assertEquals(e.getMessage(), "ID is too short");}
		
		try {bl.legalID("12,kknf13");}
		catch (NituzException e){assertEquals(e.getMessage(), "illegal ID format");}
		
		try {assertTrue(bl.legalID("384990378"));}
		catch (NituzException e) {fail();}
	}

	@Test
	public void testSelectTruckTotransport()
	{
		try {bl.selectTruckTotransport("asa", "55-2253-98");}
		catch (NituzException e){assertEquals(e.getMessage(), "illegal TRANSPORT format");}
		
		try {
			bl.signTruckTotransport("7", "55-2253-98");
			bl.selectTruckTotransport("7", "55-2253-98");}
		catch (NituzException e){fail(e.getMessage());}
		
		Transport t = null;
		Truck tr = null;
		try {
			t = dal.getTransport(7);
			tr = dal.getTruck("55-2253-98");
		}
		catch (NituzException e) {fail(e.getMessage());}
		
		assertEquals(t.getTruck().getPalte(), tr.getPalte());	
	}
	
	@Test
	public void testSignTruckTotransport()
	{
		try {bl.signTruckTotransport("3", "87-0376-23");}
		catch (NituzException e) {fail(e.getMessage());}
		
		Transport t = null;
		try {t = dal.getTransport(3);}
		catch (NituzException e) {fail(e.getMessage());}
		
		for (Truck st: t.getSignedTrucks())
			if (st.getPalte().equals("87-0376-23"))
				assertTrue(true);
	}
	
	@Test
	public void testAddTruck()
	{
		try {bl.addTruck("12-3456-78", "model", "10000.5", "409409.78", "3");}
		catch (NituzException e) {fail(e.getMessage());}
		
		Truck t = null;
		try {t = dal.getTruck("12-3456-78");}
		catch (NituzException e) {fail(e.getMessage());}
		
		assertEquals("12-3456-78", t.getPalte());
		assertEquals("model", t.getModel());
		assertTrue(10000.5==t.getWeight());
		assertTrue(409409.78==t.getMaxWeight());
		assertEquals(3, t.getLicenseType());
	}
	
	@Test
	public void testAddTransport()
	{
		try {bl.addTransport("09/04/2015", "12:59", "80");}
		catch (NituzException e) {fail(e.getMessage());}

		Transport t = null;
		try {t = dal.getTransport(80);}
		catch (NituzException e) {fail(e.getMessage());}
		
		assertEquals(80, t.getNumber());
		assertEquals("09/04/2015", t.getDate());
		assertEquals("12:59", t.getLeavTime());
	}
	
	@Test
	public void testLeagalTime()
	{
		try {bl.leagalTime("00:00");}
		catch (NituzException e) {fail(e.getMessage());}
		
		try {bl.leagalTime("23:59");}
		catch (NituzException e) {fail(e.getMessage());}
		
		try {bl.leagalTime("06:30");}
		catch (NituzException e) {fail(e.getMessage());}
		
		try {bl.leagalTime("24:61");}
		catch (NituzException e) {assertEquals(e.getMessage(), "illegal TIME format");}

		try {bl.leagalTime("-1:99");}
		catch (NituzException e) {assertEquals(e.getMessage(), "illegal TIME format");}
	}
	
	@Test
	public void testLegalLicense()
	{
		try {bl.legalLicense("0");}
		catch (NituzException e) {fail(e.getMessage());}
		
		try {bl.legalLicense("290");}
		catch (NituzException e) {fail(e.getMessage());}
		
		try {bl.legalLicense("-345678");}
		catch (NituzException e) {assertEquals(e.getMessage(), "illegal LICENSETYPE format");}
	}
	
	@Test
	public void testLeagalWeight()
	{
		try {bl.legalWeight("0");}
		catch (NituzException e) {assertEquals(e.getMessage(), "illegal WEIGhT format");}
		
		try {bl.legalWeight("290");}
		catch (NituzException e) {fail(e.getMessage());}
		
		try {bl.legalWeight("-345678");}
		catch (NituzException e) {assertEquals(e.getMessage(), "illegal WEIGhT format");}
	}
	
	@Test
	public void testLeagalPlate()
	{
		try {bl.leagalPlate("00-7659-87");}
		catch (NituzException e) {fail(e.getMessage());}
		
		try {bl.leagalPlate("45-gkrs-rd");}
		catch (NituzException e) {assertEquals(e.getMessage(), "illegal PLATE format");}
		
		try {bl.leagalPlate("-6890-");}
		catch (NituzException e) {assertEquals(e.getMessage(), "illegal PLATE format");}
	}
	
	@Test
	public void testSelectDriverTotransport()
	{
		try {bl.selectDriverTotransport("asa", "555225398");}
		catch (NituzException e){assertEquals(e.getMessage(), "illegal TRANSPORT format");}
		
		try {bl.selectDriverTotransport("2", "55-2253-98");}
		catch (NituzException e){assertEquals(e.getMessage(), "illegal ID format");}
		
		try {bl.selectDriverTotransport("2", "111111234");}
		catch (NituzException e){assertEquals(e.getMessage(), "driver license and truck license not unfit");}
		
		try {bl.selectDriverTotransport("8", "555225398");}
		catch (NituzException e){fail(e.getMessage());}
		
		Transport t = null;
		Driver d = null;
		try {
			t = dal.getTransport(8);
			d = dal.getDriver(555225398);
		}
		catch (NituzException e) {fail(e.getMessage());}
		
		assertEquals(t.getDriver().getId(), d.getId());	
	}

	@Test
	public void testUpdateMisson()
	{
		try {bl.addMission("2", "2", "5", "4", "78");}
		catch (NituzException e){fail(e.getMessage());}
		
		try {bl.updateMission("2", "5", "78", "4", "1");}
		catch (NituzException e){fail(e.getMessage());}
		
		Transport t = null;
		try {t = dal.getTransport(2);}
		catch (NituzException e) {fail(e.getMessage());}
		
		for (Mission m: t.getMissions())
			if (m.getItem().getCode() == 78)
				assertEquals(m.getAmountActuallySent(), 1);
	}
	
	@Test
	public void testAddMisson()
	{
		try {bl.addMission("3", "1", "5", "4", "78");}
		catch (NituzException e){fail(e.getMessage());}
		
		Transport t = null;
		try {t = dal.getTransport(3);}
		catch (NituzException e) {fail(e.getMessage());}
		
		for (Mission m: t.getMissions())
			if (m.getItem().getCode()==78 && m.getAmount()==1000)
				if (m.getTo().getCode()==5 || m.getTo().getCode()==4)
					assertTrue(true);
	}
	
	@Test
	public void testUpdateTransport()
	{
		// [0] date, [1] leavTime, [2] truck, [3] driver(id)
		String[] s = {"09/12/2015", "04:08", "12-3456-79", "234567891"};
		try {
			bl.signTruckTotransport("3", "12-3456-79");
			bl.updateTransport("3", s);}
		catch (NituzException e) {fail(e.getMessage());}
		
		Transport t = null;
		try {t = dal.getTransport(3);}
		catch (NituzException e) {fail(e.getMessage());}
		
		assertEquals(t.getDate(), "09/12/2015");
		assertEquals(t.getLeavTime(), "04:08");
		assertEquals(t.getTruck().getPalte(), "12-3456-79");
		assertEquals(t.getDriver().getId(), 234567891);

	}

	@Test
	public void testAddWorker() throws NituzException {

		Worker w = new Worker(333333333, "IZHAK", "IZHAK", "01/02/2017", "...", 7000, "cashier", 2, 13131313, 313);
		assertTrue(w.getFname()+" cant be insreted",SQLite.addWorker(w));
		assertFalse(w.getFname()+" should not be inserted, already exist ",SQLite.addWorker(w));
		w = new Worker(333333333, "IZHAK", "IZHAK", "01/02/2017", "...", 7000, "cashier", 2, 1313131, 313);
		assertFalse(w.getFname()+" should not be inserted, bank account to short ",SQLite.addWorker(w));
	}

	@Test
	public void testUpdateWorker() throws NituzException {

		Worker w = new Worker(111111111, "david", "david", "01/02/2017", "...", 5000, "cashier", 1, 12345678, 313);
		assertTrue(w.getFname()+" cant be updated",SQLite.updateWorker(w));
		assertTrue(w.getFname()+" cant be updated",SQLite.updateWorker(w));
		w = new Worker(444444444, "IZHAK", "IZHAK", "01/03/2017", "...", 7000, "cashier", 2, 13131313, 323);
		assertFalse(w.getFname()+" should not be updated, doesnt exist ",SQLite.updateWorker(w));
	}

	@Test
	public void testSelectWorker() throws NituzException {

		Worker w = new Worker(111111111, "david", "david", "01/02/2017", "...", 5000, "cashier", 1, 12345678, 313);
		assertTrue(w.getFname()+" cant be selected",SQLite.selectWorker(w.getId()).toString().equals(w.toString()));
		w = new Worker(333333333, "IZHAK", "IZHAK", "01/02/2017", "...", 7000, "cashier", 2, 13131313, 323);
		assertFalse(w.getFname()+" cant be selected, doesnt exists ",SQLite.selectWorker(w.getId())!=null);
	}

	@Test
	public void testRemoveWorker() throws NituzException {

		Worker w = new Worker(333333333, "IZHAK", "IZHAK", "01/02/2017", "...", 7000, "cashier", 2, 13131313, 323);
		SQLite.addWorker(w);
		assertTrue(w.getFname()+" cant be selected",SQLite.removeWorker(w.getId()));
		w = new Worker(333333333, "IZHAK", "IZHAK", "01/02/2017", "...", 7000, "cashier", 2, 13131313, 323);
		assertFalse(w.getFname()+" cant be removed, doesnt exists ",SQLite.removeWorker(w.getId()));
	}

	@Test
	public void testGetShift() throws NituzException {

		Shift s1 = new Shift(1212, "01/04/2017", "Friday", "morning", 313,111111111);
		assertTrue("cant be selected",SQLite.getShift(s1.getDate(), s1.getTime(), s1.getWorkPlace()).getCode() == s1.getCode());
		assertTrue("cant be selected",SQLite.getShift(s1.getDate(), s1.getTime(), s1.getWorkPlace()).getCode() == s1.getCode());
		s1 = new Shift(1212, "01/05/2017", "Friday", "morning", 313,111111111);
		assertTrue("cant be selected, doesnt exists ",SQLite.getShift(s1.getDate(), s1.getTime(), s1.getWorkPlace()) == null);
	}

	@Test
	public void testDeletePossibleShift() throws NituzException {

		Shift s1 = new Shift(1212, "01/04/2017", "Friday", "morning", 313,111111111);
		Worker w1 = new Worker(111111111, "david", "david", "01/01/2017", "...", 5000, "cashier", 1, 12345678, 313);
		assertTrue("cant be deleted",SQLite.deletePossibleShift(s1, w1.getId()));
		assertFalse("cant be deleted, doesnt exists ",SQLite.deletePossibleShift(s1, w1.getId()));
	}

	@Test
	public void testAddPossibleShift() throws NituzException {

		Shift s1 = new Shift(1212, "01/04/2017", "Friday", "morning", 313,111111111);
		Worker w1 = new Worker(111111111, "david", "david", "01/01/2017", "...", 5000, "cashier", 1, 12345678, 313);
		SQLite.deletePossibleShift(s1, w1.getId());
		assertTrue("cant be inserted",SQLite.addPossibleShift(s1, w1.getId()));
		assertFalse("cant be inserted, already exists ",SQLite.addPossibleShift(s1, w1.getId()));
	}

	@Test
	public void testAddShift() throws NituzException {

		Shift s1 = new Shift(2424, "01/04/2014", "Friday", "morning", 313,111111111);
		assertTrue("cant be inserted",SQLite.addShift(s1));
		assertFalse("cant be inserted",SQLite.addShift(s1));
	}

	@Test
	public void testDeleteShift() throws NituzException {

		Shift s1 = new Shift(2424, "01/04/2014", "Friday", "morning", 313,111111111);
		SQLite.addShift(s1);
		assertTrue("cant be deleted",SQLite.deleteShift(s1.getCode()));
		assertFalse("cant be deleted, doesnt exists",SQLite.deleteShift(s1.getCode()));
	}

	@Test
	public void testAddRole() throws NituzException {

		String role = "engineer";
		assertTrue("cant be inserted",SQLite.addRole(role));
		assertFalse("cant be inserted, already exists",SQLite.addRole(role));
	}

	@Test
	public void tmd()
	{
		try {bl.addMission("2", "1092", "5", "4", "21212");
		System.out.println(bl.makeDoc("2", "5", "4"));}
		catch (NituzException e){System.out.println(e.getMessage()); fail(e.getMessage());}
	}
}
