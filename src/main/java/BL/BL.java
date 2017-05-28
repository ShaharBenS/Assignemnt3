package BL;

import DAL.DAL;

import java.util.LinkedList;

import SharedClasses.*;

public class BL {

	/******
	 * @param: global variable that each class in the system can access to
	 * 			it tells the shopID of the current user who work with the system.
	 * 			need to update it EVERY LOGIN! otherwise we will get rekt.	 *
 	 */
	public static int shopID;


	private DAL dal;
	private Worker user;
	/**
	 * Constructor
	 * @param sqLite the DAL
	 */
	public BL(DAL sqLite) {
		this.dal = sqLite;
	}


	public Worker getUser (){
		return this.user;
	}

	// return 0 if has no valid premission
	public int setUser(String id) throws NituzException{ //LOGIN
		try{
			legalID(id);
			int intID = Integer.parseInt(id);
			user=dal.selectWorker(intID);
			setShopID(intID);
			if(user.getRole().equals("Director of Personal Transport Center")) return 1;
			if(user.getRole().equals("Director of Personal Shops")) return 2;
			if(user.getRole().equals("Director of Logistics")) return 3;
			if(user.getRole().equals("Storekeeper")) return 4;
			if(user.getRole().equals("Shop Manager")) return 5;
			return 0;
		}
		catch(Exception e){
			throw new NituzException(1, id + " wasn't found in the system");
		}
	}

	public boolean setShopID(int id) throws NituzException{
		try{
			Worker w = dal.selectWorker(id);
			shopID = w.getWorkPlace();
		}
		catch(){ throw new NituzException(1, id + " wasn't found in the system");}
	}

	/*public boolean hasPermissionManager(String role) throws NituzException{
		if (role.equals("BranchManager") || role.equals("TransportManager")){
			return true;
		}
		throw new NituzException(6, "You have no permission for that!");
	}

	public boolean hasPermissionBranch(String role) throws NituzException{ 
		if (role.equals("BranchManager")){
			return true;
		}
		throw new NituzException(6, "You have no permission for that!");
	}

	public boolean hasPermissionTransport(String role) throws NituzException{ 
		if (role.equals("TransportManager")){
			return true;
		}
		throw new NituzException(6, "You have no permission for that!");
	}*/

	public boolean logOut() throws NituzException
	{
		isUserLoggedIn();
		user = null;
		return true;
	}


	public boolean isUserLoggedIn() throws NituzException{
		if (user != null){
			return true;
		}
		throw new NituzException(5, "You must login first!");
	}

	/**
	 * check if the given string representing a date in the Format DD/MM/YYYY
	 * @param s
	 * @return
	 * @throws NituzException
	 */
	public boolean leagalDATE(String s) throws NituzException {
		String[] part = s.split("/");
		if (part.length != 3 || part[0].length() != 2 || part[1].length() != 2 || part[2].length() != 4)
			throw new NituzException(2, "illegal DATE format");

		try
		{
			for (int i=0; i<part.length; i++)
				Integer.parseInt(part[i]);
		}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal DATE format");}

		int month = Integer.parseInt(part[1]);
		if (month < 1 && 12 < month)
			throw new NituzException(2, "illegal DATE format");

		int day = Integer.parseInt(part[0]);
		if (day<=0)
			throw new NituzException(2, "illegal DATE format");
		if ((month%2==1 && month<=7) || (month%2==0 && month>=8))
			if (day>31)
				throw new NituzException(2, "illegal DATE format");
		if (month==9 || month==11 || month==4 || month==6)
			if (day>30)
				throw new NituzException(2, "illegal DATE format");
		if (month==2)
			if (day>29)
				throw new NituzException(2, "illegal DATE format");

		return true;
	}

	public boolean legalID(String s) throws NituzException {
		if (s.length() != 9)
			throw new NituzException(2, "ID is too short");
		try {Integer.parseInt(s);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal ID format");}
		return true;
	}

	/**
	 * if the given site is included in the given transport return the document
	 * @param transport
	 * @param site
	 * @return the document for the site and transport
	 * @throws NituzException the site not included in this transport
	 */
	public String makeDoc(String transport, String site) throws NituzException {

		int _t;
		try{_t = Integer.parseInt(transport);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal TRANSPORT format");}

		Transport t = this.dal.getTransport(_t);

		int _site;
		try {_site = Integer.parseInt(site);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal SITE format");}
		Site s = this.dal.getSite(_site);

		String doc = t.makeDocForSite(s);
		if (doc == null)
			throw new NituzException(14,
					"transport " + t.getNumber() + " not going to this site (" + s.getCode() + ")");
		return doc;
	}

	/**
	 * if the given sites is included in the given transport return the document
	 * @param transport
	 * @param shop
	 * @param supplier
	 * @return the document for the shop, supplier  and transport
	 * @throws NituzException
	 */
	public String makeDoc(String transport, String shop, String supplier) throws NituzException
	{
		// transport
		int _t;
		try{_t = Integer.parseInt(transport);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal TRANSPORT format");}
		Transport t = this.dal.getTransport(_t);

		// shop
		int _sh;
		try {_sh = Integer.parseInt(shop);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal SITE format");}
		Site sh = this.dal.getSite(_sh);

		// supplier
		int _sp;
		try {_sp = Integer.parseInt(supplier);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal SITE format");}
		Site sp = this.dal.getSite(_sp);

		String doc = t.makeDocForShopAndSupplier(sh, sp);
		if (doc == null)
			throw new NituzException(14,
					"transport " + t.getNumber() + " not going to this site (" + sh.getCode() + " or " + sp.getCode()+")");
		return doc;
	}


	/**
	 * 
	 * @param transport
	 * @return the document for the given transport
	 * @throws NituzException illegal TRANSPORT format
	 */
	public String makeDoc(String transport) throws NituzException {

		int _t;
		try{_t = Integer.parseInt(transport);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal TRANSPORT format");}

		Transport t = this.dal.getTransport(_t);
		return t.makeDoc();
	}

	/**
	 * select the truck to this transport
	 * @param transport
	 * @param truck
	 * @throws NituzException the given string is not representing a transport.
	 * 			Or the given string is not a truck.
	 */
	public void selectTruckTotransport(String transport, String truck) throws NituzException {

		int t;
		try{t = Integer.parseInt(transport);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal TRANSPORT format");}
		leagalPlate(truck);

		if (!this.dal.isTransport(t))
			throw new NituzException(1, "data not found");
		if (!this.dal.isTruck(truck))
			throw new NituzException(1, "data not found");

		this.dal.getTransport(t).selectTruck(this.dal.getTruck(truck));
		this.dal.selectTruckTotransport(t, truck);
	}

	/**
	 * sign the truck to the transport
	 * @param transport
	 * @param truck
	 * @throws NituzException the given string is not representing a transport.
	 * 				Or the given string is not a truck.
	 */
	public void signTruckTotransport(String transport, String truck) throws NituzException {

		leagalPlate(truck);
		int _t;
		try{_t = Integer.parseInt(transport);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal TRANSPORT format");}
		Transport t = this.dal.getTransport(_t);
		Truck s = this.dal.getTruck(truck);

		t.signTrck(s);
		this.dal.signTruckTotransport(_t, truck);
	}

	/**
	 * add a truck to the DB
	 * @param plate
	 * @param model
	 * @param weight
	 * @param maxWeight
	 * @param licenseType
	 * @return true if and only if the truck saved successfully in the DB.
	 * @throws NituzException if something rung is the given parameters.
	 */
	public boolean addTruck(String plate, String model, String weight, String maxWeight, String licenseType)
			throws NituzException {

		leagalPlate(plate);
		legalWeight(weight);
		legalWeight(maxWeight);
		legalLicense(licenseType);

		if (this.dal.isTruck(plate))
			throw new NituzException(20, "Truck alredy exist");

		Truck t = new Truck(plate, model, Double.parseDouble(weight), Double.parseDouble(maxWeight),
				Integer.parseInt(licenseType));
		return this.dal.add(t);
	}

	public boolean addTransport(String date, String leavTime, String number) throws NituzException {

		int num;

		try{num = Integer.parseInt(number);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal TRANSPORT format");}

		leagalDATE(date);
		leagalTime(leavTime);

		Transport t = new Transport(date, leavTime, num);
		return this.dal.add(t);
	}

	void leagalTime(String leavTime) throws NituzException {
		String[] split = leavTime.split(":");
		if (split.length != 2)
			throw new NituzException(2, "illegal TIME format");

		int h = -1, m = -1;
		try {
			h = Integer.parseInt(split[0]);
			m = Integer.parseInt(split[1]);
		}
		catch (NumberFormatException e)
		{throw new NituzException (2, "illegal TIME format");}

		if (h < 0 && 23 < h)
			throw new NituzException(2, "illegal TIME format");

		if (m < 0 && 60 < m)
			throw new NituzException(2, "illegal TIME format");
	}

	/**
	 * add a mission to the DB, and to the transport t
	 * @param amount
	 * @param to
	 * @param from
	 * @param item
	 * @param t
	 * @return true if and only if the mission saved successfully in the DB.
	 * @throws NituzException if something rung is the given parameters.
	 */
	public boolean addMission(String t, String amount, String to, String from, String item) throws NituzException {

		int _amount;
		try {_amount = Integer.parseInt(amount);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal AMOUNT format");}	
		if (_amount <= 0)
			throw new NituzException(13, "no point to add this mission (amount<=0)");

		int _to, _from;
		try 
		{
			_to = Integer.parseInt(to);
			_from = Integer.parseInt(from);
		}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal SITE format");}	

		int _item;
		try {_item = Integer.parseInt(item);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal ITEM format");}


		int _t;
		try {_t = Integer.parseInt(t);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal TRANSPORT format");}

		if (!this.dal.isTransport(_t))
			throw new NituzException(1, "data not found");

		if (!this.dal.isItem(_item))
			throw new NituzException(1, "data not found");

		if (!this.dal.isShop(_to))
			throw new NituzException(1, "data not found");

		if (!this.dal.isSite(_from))
			throw new NituzException(1, "data not found");

		Transport tr = this.dal.getTransport(_t);
		String h = getTimeShift(tr.getLeavTime());
		Shift s = this.dal.getShift(tr.getDate(), h, _to);
		if (this.dal.storeKeepersInShift(s)<0)
			throw new NituzException(4, "no storekeeper in the shop");


		return this.dal.AoUMission(_t, _from, _to , _item, _amount);
	}

	private String getTimeShift(String leavTime) {
		String hh = leavTime.split(":")[0];
		int _hh = Integer.parseInt(hh);
		if (0<=_hh &&_hh < 8)
			return "morning";
		if (8<=_hh &&_hh < 16)
			return "evening";
		/*if (16<=_hh &&_hh <= 24)
			return "night";*/
		return null;
	}

	/**
	 * Check if the given string representing a licenseType
	 * @param licenseType
	 * @throws NituzException licenseType: Not in the appropriate format
	 */
	boolean legalLicense(String licenseType) throws NituzException {
		try {
			int num = Integer.parseInt(licenseType);
			if (num < 0)
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			throw new NituzException(2, "illegal LICENSETYPE format");
		}
		return true;
	}

	/**
	 * Check if the given string representing a weight
	 * @param weight
	 * @throws NituzException weight: Not in the appropriate format
	 */
	void legalWeight(String weight) throws NituzException {
		try 
		{
			double num = Double.parseDouble(weight);
			if (num <= 0)
				throw new NumberFormatException();
		}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal WEIGhT format");}
	}

	/**
	 * Check if the given string representing a plate
	 * @param plate
	 * @throws NituzException plate: Not in the appropriate format
	 */
	void leagalPlate(String plate) throws NituzException {
		String[] split = plate.split("-");
		if (split.length != 3)
			throw new NituzException(2, "illegal PLATE format");
		for (String s : split)
			try {Integer.parseInt(s);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal PLATE format");}

		if (split[0].length()!=2 || split[1].length()!=4 || split[2].length()!=2)
			throw new NituzException(2, "illegal PLATE format");
	}

	/**
	 * 
	 * @param transport
	 * @param to
	 * @param item
	 * @param from
	 * @param newAmount
	 * @return true if and only if the details of the mission update successfully in the DB.
	 * @throws NituzException
	 */
	public boolean updateMission(String transport, String to, String item, String from, String newAmount) throws NituzException
	{
		// test the input
		int _t;
		try {_t = Integer.parseInt(transport);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal TRANSPORT format");}

		if (!this.dal.isTransport(_t))
			throw new NituzException(1, "data not found");

		int _to, _from;
		try {
			_to = Integer.parseInt(to);
			_from = Integer.parseInt(from);
		}
		catch (NumberFormatException e)
		{throw new NituzException (2, "illegal SITE format");}

		int _item;
		try {_item = Integer.parseInt(item);}
		catch (NumberFormatException e)
		{throw new NituzException(2,"illegal ITEM format");}

		int num = 0;
		try	{num = Integer.parseInt(newAmount);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal AMOUNT format");}

		if (num<0)
			throw new NituzException(15,  num + "<0. amount must be >=0");

		// test if all exist in the DB
		if (!this.dal.isSite(_to))
			throw new NituzException(1, "data not found");

		if (!this.dal.isSite(_from))
			throw new NituzException(1, "data not found");

		if (!this.dal.isItem(_item))
			throw new NituzException(1, "data not found");

		// update in the DAL
		return this.dal.AoUMission(_t, _from, _to, _item, num);
	}

	/**
	 * select a driver to this transport
	 * @param transport
	 * @param driver id of a driver
	 * @throws NituzException the given string is not representing a transport.
	 * 			Or the given string is not a driver.
	 */
	public void selectDriverTotransport(String transport, String driver) throws NituzException {

		int t;
		try{t = Integer.parseInt(transport);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal TRANSPORT format");}

		int d;
		try{d = Integer.parseInt(driver);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal ID format");}

		if (!this.dal.isTransport(t))
			throw new NituzException(1, "data not found");

		if (!this.dal.isDriver(d))
			throw new NituzException(1, "data not found");

		Transport tr = this.dal.getTransport(t);
		if (!this.dal.isWorkerInShift(d, tr.getDate(), tr.getLeavTime()))
			throw new NituzException(4, "this driver not assign to a shift when the transport leaving");
		this.dal.getTransport(t).setDriver(this.dal.getDriver(d));
		this.dal.selectDriverTotransport(t, d);
	}

	/**
	 * 
	 * @param transport
	 * @param details: [0] date, [1] leavTime, [2] truck, [3] driver(id)
	 * @return true if and only if the details of the transport update successfully in the DB.
	 * @throws NituzException
	 */
	public boolean updateTransport(String transport, String[] details) throws NituzException
	{
		// check if all the input are legal

		int t;
		try{t = Integer.parseInt(transport);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal TRANSPORT format");}

		int d = -1;
		if (details[3]!=null)
		{
			try{d = Integer.parseInt(details[3]);}
			catch (NumberFormatException e)
			{throw new NituzException(2, "illegal ID format");}
			legalID(details[3]);
		}

		if (details[2]!=null)
			leagalPlate(details[2]);
		if (details[1]!=null)
			leagalTime(details[1]);
		if (details[0]!=null)
			leagalDATE(details[0]);

		// check if exist in the DB
		if (!this.dal.isTransport(t))
			throw new NituzException(1, "data not found");
		if (details[2]!=null && !this.dal.isTruck(details[2]))
			throw new NituzException(1, "data not found");
		if (details[3]!=null && !this.dal.isDriver(d))
			throw new NituzException(1, "data not found");

		if (details[2] != null)
			this.dal.selectTruckTotransport(t, details[2]);
		if (details[3] != null) {
			Driver dr = this.dal.getDriver(d);
			if (details[2] != null) {
				if (this.dal.getTruck(details[2]).getLicenseType() > dr.getLicense())
					throw new NituzException(18, "driver license and truck license not unfit");
			}
			this.dal.getTransport(t).setDriver(dr);
			this.dal.selectDriverTotransport(t, d);
		}
		return this.dal.update(t, details[0], details[1]);
	}

	//shifts and shit starts here

	public boolean addWorkerOrDriver(String id, String lName, String fName, String startingDate, String terms, String salary, String roleOrlicence,
									 String bankNumber, String numAccount) throws NituzException {
		if (user.getWorkPlace()==0){
			return addDriver(id, lName, fName, startingDate, terms, salary, roleOrlicence, bankNumber, numAccount);
		}
		return add(id, lName, fName, startingDate, terms, salary, roleOrlicence, bankNumber, numAccount);
	}

	public boolean isTransportCenter(){
		return user.getWorkPlace()==0;
	}

	/**
	 * Get all the details of a new worker from the PL layer. 
	 * And send a worker to the DAL layer to be saved in the DB.
	 * @m id
	 * @param fName first name
	 * @param lName last name
	 * @param salary
	 * @return true if and only if the worker saved successfully in the DB.
	 */

	public boolean add(String id, String lName, String fName, String startingDate, String terms, String salary, String role,
			String bankNumber, String numAccount) throws NituzException
	{

		legalID(id);
		int intID = Integer.parseInt(id);
		try {
			this.dal.selectWorker(intID);
			return false;
		}catch (NituzException e){}
		legalName(fName);
		legalName(lName);
		legalNumber(salary);
		legalNumber(bankNumber);
		legalBankAccount(numAccount);
		leagalDATE(startingDate);
		legalTerms(terms);
		legalRole(role);
		int intSalary,intBankNumber,intNumAccount;
		try {
			intSalary = Integer.parseInt(salary);
			intBankNumber = Integer.parseInt(bankNumber);
			intNumAccount = Integer.parseInt(numAccount);
		}catch (Exception e){throw new NituzException(0,"");}
		int intWorkPlace = user.getWorkPlace();

		Worker worker = new Worker(intID, fName, lName, startingDate, terms, intSalary, role, intBankNumber, intNumAccount, intWorkPlace);

		return this.dal.addWorker(worker);
	}


	public boolean addDriver(String id, String lName, String fName, String startingDate, String terms, String salary, String license,
					   String bankNumber, String numAccount ) throws NituzException
	{

		legalID(id);
		int intID = Integer.parseInt(id);
		try {
			this.dal.selectWorker(intID);
			return false;
		}catch (NituzException e){}
		legalName(fName);
		legalName(lName);
		legalNumber(salary);
		legalNumber(bankNumber);
		legalBankAccount(numAccount);
		leagalDATE(startingDate);
		legalTerms(terms);
		legalNumber(license);
		int intSalary, intBankNumber, intNumAccount, intLicense;
		try {
			intSalary = Integer.parseInt(salary);
			intBankNumber = Integer.parseInt(bankNumber);
			intNumAccount = Integer.parseInt(numAccount);
			intLicense = Integer.parseInt(license);
		} catch (Exception e){throw new NituzException(0,"");}
		Driver driver = new Driver(intID, lName, fName, startingDate, terms, intSalary, "Driver", intBankNumber, intNumAccount, intLicense, 0);
		return this.dal.addDriver(driver);
	}

	public boolean updateWorkerOrDriver(String id, String[] details) throws NituzException{
		if (user.getWorkPlace()==0){
			return updateDriver(id, details);
		}
		return update(id, details);
	}
	/** ADD DRIVER*/

	/**
	 * Get an id of a worker that his details need to be update from the PL layer.
	 * And send a worker to the DAL layer to be update in the DB.
	 * @param id
	 * @param details
	 * @return true if and only if the details of worker id update successfully in the DB.
	 */
	public boolean update(String id, String[] details) throws NituzException {

		Worker worker = select(id);
		boolean updated = false;
		// last name
		if (details[0] != null && legalName(details[0])){
			worker.setLname(details[0]);
			updated = true;
		}
		// first name
		if (details[1] != null && legalName(details[1])){
			worker.setFname(details[1]);
			updated = true;
		}
		// start date
		if (details[2] != null && leagalDATE(details[2])){
			worker.setStartDate(details[2]);
			updated = true;
		}
		// worker's terms
		if (details[3] != null && legalTerms(details[3])){
			worker.setTerms(details[3]);
			updated = true;
		}
		if (details[4] != null && legalNumber(details[4])){
			worker.setSalary(Integer.parseInt(details[4]));
			updated = true;
		}
		if (details[5] != null && legalRole(details[5])){
			worker.setRole(details[5]);
			updated = true;
		}
		if (details[6] != null && legalNumber(details[6])){
			worker.setBankNumber(Integer.parseInt(details[6]));
			updated = true;
		}
		if (details[7] != null && legalBankAccount(details[7])){
			worker.setBankAccountNumber(Integer.parseInt(details[7]));
			updated = true;
		}
		if (details[8] != null && legalNumber(details[8])){
			worker.setWorkPlace((Integer.parseInt(details[8])));
			updated = true;
		}
		if(updated) return this.dal.updateWorker(worker);
		else return false;
	}

	public boolean updateDriver(String id, String[] details) throws NituzException {

		Driver driver = selectDriver(id);
		boolean updated = false;
		// last name
		if (details[0] != null && legalName(details[0])){
			driver.setLname(details[0]);
			updated = true;
		}
		// first name
		if (details[1] != null && legalName(details[1])){
			driver.setFname(details[1]);
			updated = true;
		}
		// start date
		if (details[2] != null && leagalDATE(details[2])){
			driver.setStartDate(details[2]);
			updated = true;
		}
		// worker's terms
		if (details[3] != null && legalTerms(details[3])){
			driver.setTerms(details[3]);
			updated = true;
		}
		if (details[4] != null && legalNumber(details[4])){
			driver.setSalary(Integer.parseInt(details[4]));
			updated = true;
		}
		if (details[5] != null && legalLicense(details[5])){
			driver.setLicense(Integer.parseInt(details[5]));
			updated = true;
		}
		if (details[6] != null && legalNumber(details[6])){
			driver.setBankNumber(Integer.parseInt(details[6]));
			updated = true;
		}
		if (details[7] != null && legalBankAccount(details[7])){
			driver.setBankAccountNumber(Integer.parseInt(details[7]));
			updated = true;
		}
		if (details[8] != null && legalNumber(details[8])){
			driver.setWorkPlace((Integer.parseInt(details[8])));
			updated = true;
		}
		if(updated) return this.dal.updateDriver(driver);
		else return false;
	}

	private Driver selectDriver(String id) throws NituzException {

		legalID(id);
		return dal.getDriver(Integer.parseInt(id));
	}

	public boolean remove(String id) throws NituzException
	{
		if (user.getWorkPlace()==0 && select(id).getWorkPlace()==0){
			return removeDriver(id);
		}
		else if (user.getWorkPlace()==select(id).getWorkPlace())
			return removeWorker(id);
		return false;
	}

	public boolean removeWorker(String id) throws NituzException{

		legalID(id);
		int intID = Integer.parseInt(id);
		return this.dal.removeWorker(intID);       
	}

	public boolean removeDriver (String id) throws  NituzException{

		legalID(id);
		int intID = Integer.parseInt(id);
		return this.dal.removeDriver(intID);
	}

	/**
	 * 
	 * @param sid
	 * @return the worker with the given id.
	 */
	public Worker select(String sid) throws NituzException
	{
		legalID(sid);
		int id = Integer.parseInt(sid);
		Worker worker = this.dal.selectWorker(id);
		if (worker == null)
			throw new NituzException(1, "Data not found.");
		return worker;
	}


	private boolean legalRole(String role) throws NituzException { //need to check again roles
		if (dal.hasRole(role))
			return true;
		throw new NituzException(1, "no role has been found");

	}

	private boolean legalTerms (String terms) throws NituzException{ // need to implement
		return true;
	}

	// for number of bank, salary and work place
	private boolean legalNumber(String num) throws NituzException{
		try
		{
			int temp = Integer.parseInt(num);
			if(temp < 0)throw new NituzException(2, "must be positive number!");
		}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal number format");}
		return true;
	}

	public boolean legalBankAccount(String bankAccount) throws NituzException{
		if (bankAccount.length() != 8)
			throw new NituzException(2, "bank account must be 8 digits!");
		try {Integer.parseInt(bankAccount);}
		catch (NumberFormatException e)
		{throw new NituzException(2, "illegal bank account format");}
		return true;
	}

	public boolean legalName(String s) throws NituzException{
		String leagalChars="qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
		for (int i=0; i<s.length();i++)
			if (leagalChars.indexOf(s.charAt(i))==-1)
				throw new NituzException(2,"the name" + s + "contain '" + s.charAt(i) + "' which is not a legal character");

		return true;
	}

	//shifts a worker can work in  
	public boolean addPossibleShift(Shift s, String id) throws NituzException{

		Worker w = select(id);
		Shift s1 = dal.getShift(s.getDate(), s.getTime(), s.getWorkPlace());
		return this.dal.addPossibleShift(s1, w.getId());
	}
	public boolean deletePossibleShift(Shift s,String id) throws NituzException{

		Worker w = select(id);
		Shift s1 = dal.getShift(s.getDate(), s.getTime(), s.getWorkPlace());
		return this.dal.deletePossibleShift(s1, w.getId());
	}

	// prints all possible shifts workers can work in
	public String showPossibleShifts( Shift s) throws NituzException{
		int intWorkPlace = user.getWorkPlace();
		String toRet="";
		LinkedList <PossibleShiftsForWorkers> lst = this.dal.possibleShiftsForWorkers(intWorkPlace);
		for (PossibleShiftsForWorkers PSFW: lst){
			leagalDATE(PSFW.getShift().getDate());
			if (PSFW.getShift().getDate().equals(s.getDate()) && PSFW.getShift().getTime().equals((s.getTime())))
				toRet = toRet+PSFW.getWorker() +  " can work in : "+ PSFW.getShift().getDay() + " at: " + PSFW.getShift().getTime() + "\n";
		}
		return toRet;
	}


	private int dateCompare (String date1, String date2){
		String[] part1 =date1.split("/");
		String[] part2 = date2.split("/");
		if (Integer.parseInt(part1[2]) > Integer.parseInt(part2[2])){
			return 1;
		}
		else if (Integer.parseInt(part1[2]) < Integer.parseInt(part2[2])){
			return -1;
		}
		else {
			if (Integer.parseInt(part1[1]) > Integer.parseInt(part2[1])){
				return 1;
			}
			else if (Integer.parseInt(part1[1]) < Integer.parseInt(part2[1])){
				return -1;
			}
			else {
				if (Integer.parseInt(part1[0]) > Integer.parseInt(part2[10])){
					return 1;
				}
				else if (Integer.parseInt(part1[0]) < Integer.parseInt(part2[0])){
					return -1;
				}
				else {
					return 0;
				}
			}
		}
	}

	public String showHistory() throws NituzException{
		LinkedList <PossibleShiftsForWorkers> lst = this.dal.shiftsForWorkers(user.getWorkPlace());
		String toRet="";
		for (PossibleShiftsForWorkers w: lst){
			toRet = toRet + w.getWorker().getFname() + w.getWorker().getLname() + " Worked in : " + w.getShift().getDate() + " at: " +  w.getShift().getTime() + "\n";
		}
		return toRet;
	}

	// call the function above first to show possible shifts for workers
	public boolean placeWorkersInShift(String[] ids, Shift s) throws NituzException{
		// need to add checking for if the ids are all from the branch given if not send a msg to user what workers ids
		// don't work in this branch with workPlace id.
		for (int i = 0; i < ids.length; i++){
			legalID(ids[i]);
		}
		legalShift(s);
		boolean toRet = true;
		for(int i = 0 ; i < ids.length ; i++){
			int id = Integer.parseInt(ids[i]);
			Worker w = this.dal.selectWorker(id);
			//check if worker works in this workPlace
			Shift s1 = dal.getShift(s.getDate(), s.getTime(),s.getWorkPlace());
			LinkedList<String> lst = this.dal.rolesInShift(s1);
			if(lst == null) throw new NituzException(1,"must have roles first");
			boolean b = false;
			for(String r:lst){
				if(w.getRole().equals(r)) b = true;
			}
			if(b == false) throw new NituzException(2,w.getFname()+" "+w.getLname()+" Role is Ilegal");
			toRet = (dal.placeWorkerInShift(id, s1) && toRet);
		}
		return toRet;
	}
	private boolean legalShift(Shift s) throws NituzException { // need to check there is no two same shifts in same workPlace
		leagalDATE(s.getDate());
		if (s.getTime().equals("morning") || s.getTime().equals("evening")){
			return true;
		}
		throw new NituzException( 2, "shift must be in the morning or evening only!");
	}

	public boolean isValidShiftManager(String id) throws NituzException{ //need to check that he works at least one month

		legalID(id);           
		return true;
	}

	public boolean removeWorkerFromShift(String id, Shift s) throws NituzException{  

		select(id);
		int intID = Integer.parseInt(id);
		legalShift(s);
		LinkedList<Worker> lst = this.dal.shiftsForWorkers(s);
		boolean found = false;
		for(Worker w:lst){
			if (w.getId()==intID)	found = true;
		}
		if (!found){
			throw new NituzException( 1, "data not found");
		}
		if (intID == s.getManager()){
			throw new NituzException( 0, "can't remove the shift manager after he was chosen!");
		}
		return this.dal.removeWorkerFromShift(intID, s);
	}
	// print workers in shift
	public String showWorkersInShift(Shift s) throws NituzException{

		LinkedList<Worker> lst = this.dal.shiftsForWorkers(s);
		String str="";
		for(Worker w:lst){
			str=str+w.toString()+"\n";
		}
		return str;
	}
	public boolean chooseRolesInShift(String[] roles,Shift s) throws NituzException{

		for (int i = 0; i < roles.length; i++){
			legalRole(roles[i]);
		}
		legalShift(s);
		return this.dal.chooseRolesInShift(roles, s);
	}

	public boolean deleteRoleInShift(String role, Shift s) throws NituzException {

		if (role.equals("Storekeeper")){
			if(!transportsInShift(s) || storeKeepersInShift(s)>1){
				return dal.deleteRoleInShift(role, s);
			}
			return false;
		}
		return dal.deleteRoleInShift(role, s);
	}

	public int storeKeepersInShift(Shift s) throws NituzException {
		return dal.storeKeepersInShift(s);
	}

	public boolean transportsInShift(Shift s) throws NituzException {
		return dal.transportsInShift(s);
	}

	public boolean roleCanBeDeleted(String role) throws NituzException{
		if (role.equals("Director of Personal Transport Center") || role.equals("Director of Personal Shops")
				|| role.equals("Director of Logistics") || role.equals("Storekeeper")
				|| role.equals("Shop Manager") role.equals("Storekeeper") || role.equals("Driver")){
			throw new NituzException(2, role + " can't be deleted!");
		}
		return true;
	}

	public Shift getShift(String date, String time) throws NituzException{
		return this.dal.getShift(date,time, user.getWorkPlace());
	}

	public Shift getShift(int code) throws NituzException{
		return this.dal.getShift(code);
	}

	public boolean addBank(String bankNum, String bankName) throws NituzException {

		legalNumber(bankNum);
		return this.dal.addBank(Integer.parseInt(bankNum), bankName);
	}

	public boolean addRole(String role) throws NituzException {

		return this.dal.addRole(role);
	}

	public boolean addWorkerInShift(String id, Shift s) throws NituzException {

		legalID(id);
		try {
			dal.hasShift(s);
			return false;
		}catch (NituzException e){}
		int intID = Integer.parseInt(id);
		Worker w = this.dal.selectWorker(intID);
		Shift s1 = getShift(s.getDate(), s.getTime());
		LinkedList<String> lst = this.dal.rolesInShift(s1);
		boolean b = false;
		for(String r:lst){
			if(w.getRole().equals(r)) b = true;
		}
		if(b == false) return false;
		return this.dal.placeWorkerInShift(intID, s);
	}

	public boolean hasShiftManager (Shift s) throws NituzException{

		Shift s1 = getShift(s.getDate(), s.getTime());
		return this.dal.hasShiftManager(s1);
	}

	public boolean deleteRole(String role) throws NituzException {

		if (roleCanBeDeleted(role))
			return this.dal.deleteRole(role);
		return false;
	}

	public boolean deleteBank(String bankNumber)throws NituzException {

		legalNumber(bankNumber);
		return this.dal.deleteBank(Integer.parseInt(bankNumber));
	}

	public boolean deleteShift(Shift s) throws NituzException {

		dal.hasShift(s);
		if (user.getWorkPlace() == s.getWorkPlace()) {
			Shift s1 = getShift(s.getDate(), s.getTime());
			return this.dal.deleteShift(s1.getCode());
		}
		throw new NituzException(1, "manager of different branch can't delete shifts in another branch!");
	}

	public boolean addShift(String date, String time, String idManager) throws NituzException {

		Worker w = select(idManager);
		int intIDmanager = Integer.parseInt(idManager);
		try{
			getShift(date, time);
			return false;
		}
		catch(NituzException e) {
			int code = this.dal.maxShiftCode() + 1;
			Shift s = new Shift(code, date, "", time, user.getWorkPlace(), intIDmanager);
			if (w.getWorkPlace() == s.getWorkPlace())
				return this.dal.addShift(s);
			else
				throw new NituzException(1, "manager is not in the same branch as the shift!");
		}
	}
<<<<<<< HEAD

	public String getAllTrucks() throws NituzException {
		String ans="";
		Truck t[]=dal.getAllTrucks();
		for ( int i=0;i<t.length; i++) {
				ans+=t[i].toString()+"\n";
		}
		return ans;
	}

    public String getAllTransport() throws NituzException {
	    String ans="";
        Transport t[]=dal.getAllTransports();
        for (int i=0;i<t.length;i++){
            ans+= t[i].getNumber()+"|"+t[i].getDate()+","+t[i].getLeavTime()+"\n";
        }
	    return ans;
    }
=======
	public int getWorkPlace() throws NituzException{

		try{
			Worker w = dal.selectWorker(user.getId());
			return w.getWorkPlace();
		}
		catch(){throw new NituzException(1, "manager is not in the same branch as the shift!");}
	}
>>>>>>> origin/Workers
}