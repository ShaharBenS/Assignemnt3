import java.util.Scanner;
import SharedClasses.*;


public class CMD {

	private BL bl;

	public CMD(BL bl) {
		this.bl=bl;
	}

	public void run() throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Analysis and Design of Software Systems - HW2 Transport and Workers Modules");
		System.out.println("for help type HELP");
		boolean exit = false, signed=false;

		while (!exit ) {

			if(!signed){
				try {
					System.out.println("Please enter your ID: ");
					System.out.print("#>");
					String s = scanner.nextLine();
					bl.setUser(s);
					signed=true;
					System.out.println("Welcome to the Super-Li computer System.");
				}catch (NituzException e){
					System.out.println(e);
				}
			}
			else {
				try {
					System.out.print("#>");
					String s = scanner.nextLine();
					if (!s.equals("")) {
						String[] words = trim(s);
						switch (words[0].toUpperCase()) {
							case "TRUCK":
								truck(words);
								break;
							case "TRANSPORT":
								transport(words);
								break;
							case "MANAGE":
								manage(words);
								break;
							/*
							case "SHIFTS":
								shifts(words);
								break;
							*/
							case "HELP":
								help();
								break;
							case "LOGOUT":
								bl.logOut();
								System.out.println("you have logged out of the system.");
								signed = false;
								break;
							case "EXIT":
								System.out.println("Have a nice day!");
								exit = true;
								break;
							default:
								System.out.println("you have entered a none supported function, please try again or type 'HELP' for instructions.");
						}
					} else
						System.out.println("you have entered a none supported function, please try again or type 'HELP' for instructions.");
				} catch (NituzException e) {
					System.out.println(e);
				}
			}
		}
		scanner.close();
	}
/*
	private void shifts(String[] words) throws NituzException {
		if (words.length>=2) {
			switch (words[1].toUpperCase()) {
				case "SHOW":
					bl.showPossibleShifts();
					break;
				case "SIGN":
					if (words.length==5){
						Shift s = bl.getShift(words[2],words[3]);
						if(bl.addPossibleShift(s))
							System.out.println("you have been signed to the " + words[3] + " shift on the " + words[2]);
						else
							System.out.println("Error: you were not signed to the " + words[3] + " shift on the " + words[2]);
					}
					else
						System.out.println("you have entered a none supported attribute for manage, please try again or type 'SHIFTS HELP' for instructions.");
					break;
				case "REMOVE":
					if (words.length==4){
						Shift s = bl.getShift(words[2],words[3]);
						if(bl.deletePossibleShift(s, id))
							System.out.println("you have been removed from the " + words[3] + " shift on the " + words[2]);
						else
							System.out.println("Error: you were not removed from the " + words[3] + " shift on the " + words[2]);
					}
					else
						System.out.println("you have entered a none supported attribute for manage, please try again or type 'SHIFTS HELP' for instructions.");
					break;
					break;
				case "HELP":
					shiftsHelp();
					break;
				default:
					System.out.println("you have entered a none supported attribute for manage, please try again or type 'SHIFTS HELP' for instructions.");
			}
		}
		else{
			System.out.println("you haven't entered a supported attribute for manage , please try again or type 'SHIFTS HELP' for instructions.");
		}
	}

	private void shiftsHelp() {
		System.out.println("SHIFTS [function]");
		System.out.println("	[function]- will by the following:");
		System.out.println("	SHOW - show all upcoming shifts.");
		System.out.println("	SIGN [date] [time] - sign the current logged worker to a shift");
		System.out.println("		[date] - legal date in the format DD/MM/YYYY.");
		System.out.println("		[time] - time of the shift in the day(MORNING,EVENING,NIGHT).");
		System.out.println("	REMOVE [date] [time] - remove the current logged worker to a shift");
		System.out.println("		[date] - legal date in the format DD/MM/YYYY.");
		System.out.println("		[time] - time of the shift in the day(MORNING,EVENING,NIGHT).");
		System.out.println("	HELP - show the help for SHIFTS");
	}
*/
	private void manage(String[] words) throws NituzException {
		if(words[1].toUpperCase().equals("HELP")){
			manageHelp();
		}
		else if (words.length>2) {

			switch (words[1].toUpperCase()) {
				case "WORKER":
					manageWorker(words);
					break;
				case "SHIFTS":
					manageShifts(words);
					break;
				case "BANK":
					manageBank(words);
					break;
				case "ROLES":
					manageRoles(words);
					break;
				default:
					System.out.println("you have entered a none supported attribute for manage, please try again or type 'MANAGE HELP' for instructions.");
			}
		}
		else{
			System.out.println("you haven't entered a supported attribute for manage , please try again or type 'MANAGE HELP' for instructions.");
		}
	}

	private void manageHelp() {
		System.out.println("MANAGE [function]");
		System.out.println("	[function]- will by the following:");
		System.out.println("	BANK [bank number] [command]");
		System.out.println("		ADD [name] - add the bank.");
		System.out.println("			[name] - bank name.");
		System.out.println("		REMOVE - remove the bank.");
		System.out.println("	ROLES [command] {[role]} - ADD/REMOVE roles.");
		System.out.println("		[command] - will be replaced by the following:");
		System.out.println("			ADD - add the roles.");
		System.out.println("			REMOVE - remove the roles.");
		System.out.println("		{[role]} - role to be added to/removed, you may write as many roles as you like.");
		System.out.println("	SHIFTS [date] [time] [command]");
		System.out.println("		[date] - legal date in the format DD/MM/YYYY.");
		System.out.println("		[time] - time of the shift in the day(MORNING,EVENING,NIGHT).");
		System.out.println("		[command] - will be replaced by the following:");
		System.out.println("			ADD [manager ID] - create a new shift.");
		System.out.println("				[manager ID] - ID of the worker which will manage the shift.");
		System.out.println("			DELETE - cancels the shift.");
		System.out.println("			ROLES [command] {[role]} - ADD/REMOVE roles from the shift.");
		System.out.println("				[command] - will be replaced by the following:");
		System.out.println("					ADD - add the role to the shift.");
		System.out.println("					REMOVE - remove the role from the shift.");
		System.out.println("				{[role]} - role to be added to/removed from the shift, you may write as many roles as you like.");
		System.out.println("			SHOW - present yhe current state of the shift.");
		System.out.println("			WORKERS [command] {[worker ID]} - ADD/REMOVE workers from the shift.");
		System.out.println("				[command] - will be replaced by the following:");
		System.out.println("					ADD - add the workers to the shift.");
		System.out.println("					REMOVE - remove the workers from the shift.");
		System.out.println("				{[worker ID]} - Workers to be added to/removed from to the shift, you may write as many workers as you like.");
		System.out.println("	SHIFTS HELP - open the SHIFTS MANAGEMENT HELP");
		System.out.println("	WORKER [ID] [command]");
		System.out.println("		[ID]- the ID of the worker.");
		System.out.println("		[command] - will be replaced by the following:");
		System.out.print("			ADD [last name] [first name] [terms] [salary] ");
		if(bl.isTransportCenter())
			System.out.print("[license]");
		else
			System.out.println("[role]");
		System.out.print(" [bank number] [bank account]");
		System.out.println("- adds a new worker.");
		System.out.println("			[last name]- the last name of the worker.");
		System.out.println("			[first name]- the first name of the worker.");
		System.out.println("			[terms]- terms and agreements with the worker.");
		System.out.println("			[salary]- salary of the worker. must be a positive number.");
		if(bl.isTransportCenter())
			System.out.println("			[license]- the license type of the driver. must be a positive number.");
		else
			System.out.println("			[role]- the role of the new worker.");
		System.out.println("			[bank number]- the number of the bank.");
		System.out.println("			[bank account]- the bunk account number of the new worker.");
		System.out.println("		REMOVE - remove the worker from the list.");
		System.out.println("		SELECT - show the user attributes");
		System.out.println("		UPDATE [attributes]- updates one or more of the workers attributes.");
		System.out.println("			[attributes]- will be replaced by the following, all are optional:");
		System.out.println("				LNAME [last name]- the last name of the worker.");
		System.out.println("				FNAME [first name]- the first name of the worker.");
		System.out.println("				TERMS [terms]- terms and agreements with the worker.");
		System.out.println("				SALARY [salary]- salary of the worker. must be a positive number.");
		if(bl.isTransportCenter())
			System.out.println("			LICENSE [license]- the license type of the driver. must be a positive number.");
		else
			System.out.println("			 ROLE [role]- the role of the new worker.");
		System.out.println("			BANKN [bank number]- the number of the bank.");
		System.out.println("			BANKA [bank account]- the bunk account number of the new worker.");
		System.out.println("	WORKER HELP - open the WORKER MANAGEMENT HELP");
	}

	private void manageWorker(String[] words) throws NituzException {
		if(words[2].toUpperCase().equals("HELP")){
			manageWorkerHelp();
		}
		else if (words.length>3) {
			switch (words[3].toUpperCase()) {
				case "ADD":
					addWorker(words);
					break;
				case "UPDATE":
					updateWorker(words);
					break;
				case "REMOVE":
					if (words.length==4)
						if (bl.remove(words[2]))
							System.out.println("The worker with ID number " + words[2] + " has been removed");
						else
							System.out.println("Couldn't Found remove worker with ID number "+words[2]+", please make sure the ID is correct");
					else
						System.out.println("you have entered a none supported number of attributes for manage , please try again or type 'MANAGE WORKER HELP' for instructions.");
					break;
				case "SELECT":
					if (words.length==4)
						try {
							System.out.println(bl.select(words[2]));
						}catch (Exception e){
							throw new NituzException(1,e.getMessage());
						}
					else
						System.out.println("you have entered a none supported number of attributes for manage , please try again or type 'MANAGE WORKER HELP' for instructions.");
					break;
				default:
					System.out.println("you have entered a none supported attribute for manage, please try again or type 'MANAGE WORKER HELP' for instructions.");
			}
		}
		else{
			System.out.println("you haven't entered a supported attribute for manage , please try again or type 'MANAGE WORKER HELP' for instructions.");
		}
	}

	private void manageWorkerHelp() {
		System.out.println("MANAGE WORKER [ID] [command");
		System.out.println("	[ID]- the ID of the worker.");
		System.out.println("	[command] - will be replaced by the following:");
		System.out.print("		ADD [last name] [first name] [terms] [salary] ");
		if(bl.isTransportCenter())
			System.out.print("[license]");
		else
			System.out.println("[role]");
		System.out.print(" [bank number] [bank account]");
		System.out.println("- adds a new worker.");
		System.out.println("		[last name]- the last name of the worker.");
		System.out.println("		[first name]- the first name of the worker.");
		System.out.println("		[terms]- terms and agreements with the worker.");
		System.out.println("		[salary]- salary of the worker. must be a positive number.");
		if(bl.isTransportCenter())
			System.out.println("		[license]- the license type of the driver. must be a positive number.");
		else
			System.out.println("		[role]- the role of the new worker.");
		System.out.println("		[bank number]- the number of the bank.");
		System.out.println("		[bank account]- the bunk account number of the new worker.");
		System.out.println("	REMOVE - remove the worker from the list.");
		System.out.println("	SELECT - show the user attributes");
		System.out.println("	UPDATE [attributes]- updates one or more of the workers attributes.");
		System.out.println("		[attributes]- will be replaced by the following, all are optional:");
		System.out.println("			LNAME [last name]- the last name of the worker.");
		System.out.println("			FNAME [first name]- the first name of the worker.");
		System.out.println("			TERMS [terms]- terms and agreements with the worker.");
		System.out.println("			SALARY [salary]- salary of the worker. must be a positive number.");
		if(bl.isTransportCenter())
			System.out.println("		LICENSE [license]- the license type of the driver. must be a positive number.");
		else
			System.out.println("		 ROLE [role]- the role of the new worker.");
		System.out.println("		BANKN [bank number]- the number of the bank.");
		System.out.println("		BANKA [bank account]- the bunk account number of the new worker.");
		if(!bl.isTransportCenter())
			System.out.println("		SHOP [shop]- the shop where the worker works");
		System.out.println("MANAGE WORKER HELP - open the WORKER MANAGEMENT HELP");
	}

	private void addWorker(String[] words) throws NituzException {
		if (words.length!=12){
			System.out.println("you have entered a none supported attribute for manage, please try again or type 'MANAGE WORKER HELP' for instructions.");
			return;
		}
		if (bl.addWorkerOrDriver(words[2],words[4],words[5],words[6],words[7], words[8], words[9], words[10],words[11]))
			System.out.println("data was added to DB");
		else
			System.out.println("data wasn't added to DB");
	}

	private void updateWorker(String[] words) throws NituzException {
		if(words.length>=6 && (words.length-4)%2==0){

			String[] arr=new String[9];
			arr[0]=null;
			arr[1]=null;
			arr[2]=null;
			arr[3]=null;
			arr[4]=null;
			arr[5]=null;
			arr[6]=null;
			arr[7]=null;
			arr[8]=null;

			int i=4;
			while (i<words.length){
				switch (words[i].toUpperCase()){
					case "LNAME":
						arr[0]=words[i+1];
						break;
					case "FNAME":
						arr[1]=words[i+1];
						break;
					case "SDATE":
						arr[2]=words[i+1];
						break;
					case "TERMS":
						arr[3]=words[i+1];
						break;
					case "SALARY":
						arr[4]=words[i+1];
						break;
					case "ROLE":
						if(!bl.isTransportCenter()){
							arr[5]=words[i+1];
							break;
						}
						else{
							System.out.println(words[i]+" is not a supported attribute - please try again or type 'HELP' for instructions.");
							return;
						}
					case "LICENSE":
						if(bl.isTransportCenter()){
							arr[5]=words[i+1];
							break;
						}
						else{
							System.out.println(words[i]+" is not a supported attribute - please try again or type 'HELP' for instructions.");
							return;
						}
					case "BANKN":
						arr[6]=words[i+1];
						break;
					case "BANKA":
						arr[7]=words[i+1];
						break;
					default:
						System.out.println(words[i]+" is not a supported attribute - please try again or type 'HELP' for instructions.");
						return;
				}
				i=i+2;
			}

			if(bl.updateWorkerOrDriver(words[2],arr))
				System.out.println("data was updated");
			else
				System.out.println("Update failed");
		}
		else{
			System.out.println("you haven't entered a supported attribute for manage , please try again or type 'MANAGE WORKER HELP' for instructions.");
		}
	}

	private void manageShifts(String[] words) throws NituzException {
		if(words[2].toUpperCase().equals("HELP")){
			manageShiftHelp();
		}
		else if (words.length>=5) {

			switch (words[4].toUpperCase()) {
				case "ADD":
					if(words.length==6 &&this.bl.addShift(words[2],words[3],words[5]))
						System.out.println("the " + words[3] + " shift on the " + words[2] +" has been added");
					else System.out.println("Error: the " + words[3] + " shift on the " + words[2] +" wasn't added");
					break;
				case "DELETE":
					if(this.bl.deleteShift(bl.getShift(words[2],words[3])))
						System.out.println("the " + words[3] + " shift on the " + words[2] +" has been removed");
					else System.out.println("Error: the " + words[3] + " shift on the " + words[2] +" wasn't removed");
					break;
				case "SHOW":
					System.out.println(bl.showWorkersInShift(bl.getShift(words[2],words[3])));
					break;
				case "WORKERS":
					manageShiftsWorkers(words);
					break;
				case "ROLES":
					manageShiftRoles(words);
					break;
				default:
					System.out.println("you have entered a none supported attribute for manage, please try again or type 'MANAGE SHIFTS HELP' for instructions.");
			}
		}
		else{
			System.out.println("you haven't entered a supported attribute for manage , please try again or type 'MANAGE SHIFTS HELP' for instructions.");
		}
	}

	private void manageShiftHelp() {
		System.out.println("MANAGE SHIFTS [date] [time] [command]");
		System.out.println("	[date] - legal date in the format DD/MM/YYYY.");
		System.out.println("	[time] - time of the shift in the day(MORNING,EVENING,NIGHT).");
		System.out.println("	[command] - will be replaced by the following:");
		System.out.println("		ADD [manager ID] - create a new shift.");
		System.out.println("			[manager ID] - ID of the worker which will manage the shift.");
		System.out.println("		DELETE - cancels the shift.");
		System.out.println("		ROLES [command] {[role]} - ADD/REMOVE roles from the shift.");
		System.out.println("			[command] - will be replaced by the following:");
		System.out.println("				ADD - add the role to the shift.");
		System.out.println("				REMOVE - remove the role from the shift.");
		System.out.println("			{[role]} - role to be added to/removed from the shift, you may write as many roles as you like.");
		System.out.println("		SHOW - present the current state of the shift.");
		System.out.println("		AVAILABLE - present the workers who can work in the shift.");
		System.out.println("		WORKERS [command] {[worker ID]}");
		System.out.println("			[command] - will be replaced by the following:");
		System.out.println("				ADD - add signed the workers to the shift.");
		System.out.println("				SIGN - sign the workers to the shift.");
		System.out.println("				UNSIGNED - unsigned the workers to the shift.");
		System.out.println("				REMOVE - remove the workers from the shift.");
		System.out.println("			{[worker ID]} - Workers to be added to/removed from to the shift, you may write as many workers as you like.");
		System.out.println("MANAGE SHIFTS HISTORY - show the history of all shifts in the shop.");
		System.out.println("MANAGE SHIFTS HELP - open the SHIFTS MANAGEMENT HELP");
	}

	private void manageShiftsWorkers(String[] words) throws NituzException {
		if (words.length==6 && words[5].toUpperCase().equals("AVAILABLE") ){
			System.out.println(bl.showPossibleShifts(bl.getShift(words[2],words[3])));
		}
		else if (words.length>=7) {
			Shift s = bl.getShift(words[2], words[3]);
			for (int i = 6; i < words.length; i++) {

				switch (words[5].toUpperCase()) {
					case "ADD":
						if(bl.addWorkerInShift(words[i], s))
							System.out.println("the worker "+ words[i]+" was added to the " + words[3] + " shift on the " + words[2]+" successfully");
						else
							System.out.println("Error: the worker "+ words[i]+" wasn't added to the " + words[3] + " shift on the " + words[2]);
						break;
					case "SIGN":
						if(bl.addPossibleShift(s, words[i]))
							System.out.println("the worker "+ words[i]+" has been signed to the  " + words[3] + " shift on the " + words[2]+" successfully");
						else
							System.out.println("Error: the worker "+ words[i]+" wasn't signed to the " + words[3] + " shift on the " + words[2]);
						break;
					case "UNSIGNED":
						if(bl.deletePossibleShift(s, words[i]))
							System.out.println("the worker "+ words[i]+" has been unsigned to the  " + words[3] + " shift on the " + words[2]+" successfully");
						else
							System.out.println("Error: the worker "+ words[i]+" wasn't unsigned to the " + words[3] + " shift on the " + words[2]);
						break;
					case "REMOVE":
						if (bl.removeWorkerFromShift(words[i], s))
							System.out.println("the worker "+ words[i]+" was removed from the " + words[3] + " shift on the " + words[2]+" successfully");
						else
							System.out.println("Error: the worker "+ words[i]+" wasn't removed from the " + words[3] + " shift on the " + words[2]);
						break;
					default:
						System.out.println("you have entered a none supported attribute for manage, please try again or type 'MANAGE SHIFTS HELP' for instructions.");
						return;
				}
			}
		}
		else{
			System.out.println("you haven't entered a supported attribute for manage , please try again or type 'MANAGE SHIFTS HELP' for instructions.");

		}
	}

	private void manageShiftRoles(String[] words) throws NituzException {
		if(words.length==5){
			System.out.println("you haven't entered a supported attribute for manage , please try again or type 'MANAGE SHIFTS HELP' for instructions.");
		}
		else {
			Shift s = bl.getShift(words[2], words[3]);
			switch (words[5].toUpperCase()) {
				case "ADD":
					String[] roles = new String[1];
					for (int i = 6; i < words.length; i++) {
						roles[0]=words[i];
						if (bl.chooseRolesInShift(roles, s)) {
							System.out.println("the role \"" + roles[0] + "\" has been added to the " + words[3] + " shift on the " + words[2]);
						} else {
							System.out.println("Error: the role \"" + roles[0] + "\" were not added to the " + words[3] + " shift on the " + words[2]);
						}
					}
					break;
				case "REMOVE":
					for (int i = 6; i < words.length; i++) {
						if (this.bl.deleteRoleInShift(words[i], s))
							System.out.println("the role \"" + words[i] + "\" has been removed from the " + words[3] + " shift on the " + words[2]);
						else
							System.out.println("Error: the role \"" + words[i] + "\" wasn't removed from the " + words[3] + " shift on the " + words[2]);
					}
					break;
				default:
					System.out.println("you have entered a none supported attribute for manage, please try again or type 'MANAGE HELP' for instructions.");
					return;
			}
		}
	}

	private void manageBank(String[] words) throws NituzException {
		if (words.length>2) {

			switch (words[3].toUpperCase()) {
				case "ADD":
					if(this.bl.addBank(words[2], words[4]))
						System.out.println("bank number \"" + words[2] + "\" has been add");
					else
						System.out.println("Error: bank number \"" + words[2] + "\" wasn't added");
					break;
				case "REMOVE":
					if(this.bl.deleteBank(words[2]))
						System.out.println("bank number \"" + words[2] + "\" has been removed");
					else
						System.out.println("Error: bank number \"" + words[2] + "\" wasn't removed");
					break;
				default:
					System.out.println("you have entered a none supported attribute for manage, please try again or type 'MANAGE BANK HELP' for instructions.");
			}
		}
		else{
			System.out.println("you haven't entered a supported attribute for manage , please try again or type 'MANAGE BANK HELP' for instructions.");
		}
	}

	private void manageRoles(String[] words) throws NituzException {
		if(words.length>=4) {
			for (int i=3;i<words.length;i++) {
				switch (words[2].toUpperCase()) {
					case "ADD":
						if(this.bl.addRole(words[i]))
							System.out.println("the role \"" + words[i] + "\" has been added");
						else
							System.out.println("Error: the role \"" + words[i] + "\" wasn't added");
						break;
					case "REMOVE":
						if(this.bl.deleteRole(words[i]))
							System.out.println("the role \"" + words[i] + "\" has been removed");
						else
							System.out.println("Error: the role \"" + words[i] + "\" wasn't removed");
						break;
					default:
						System.out.println("you have entered a none supported attribute for manage, please try again or type 'MANAGE HELP' for instructions.");
						return;
				}
			}
		}
		else{
			System.out.println("you haven't entered a supported attribute for manage , please try again or type 'MANAGE HELP' for instructions.");
		}
	}

	private void transport(String[] words) throws NituzException {
		if(words[1].toUpperCase().equals("HELP")){
			transportHelp();
		}
		else if (words.length>2) {

			switch (words[2].toUpperCase()) {
				case "CREATE":
					transportCreate(words);
					break;
				case "DOC":
					transportDoc(words);
					break;
				case "UPDATE":
					transportUpdate(words);
					break;
				case "ASSIGN":
					transportAssign(words);
					break;
				case "MISSION":
					transportMission(words);
					break;
				default:
					System.out.println("you have entered a none supported attribute for transport, please try again or type 'TRANSPORT HELP' for instructions.");
			}
		}
		else{
			System.out.println("you haven't entered a supported attribute for transport , please try again or type 'TRANSPORT HELP' for instructions.");
		}
	}

	private void transportHelp() {
		System.out.println("TRANSPORT [transport number] [command] [attribute]");
		System.out.println("	[transport number]- legal ID of the transport");
		System.out.println("	[command] - will be replaced by the following:");
		System.out.println("		ASSIGN [truck plate] [driver ID]");
		System.out.println("			[truck plate] - legal truck plate ");
		System.out.println("			[driver ID] - id of a driver. optional, will assign the truck and the driver to the transport");
		System.out.println("		CREATE [time] [date]");
		System.out.println("			[time] - legal time in the format HH:MM");
		System.out.println("			[date] - legal date in the format DD/MM/YYYY");
		System.out.println("		DOC [site]");
		System.out.println("			[site] - shop or supplier to produce its specific document in the transport");
		System.out.println("		HELP - open help for transport");
		System.out.println("		MISSION [supplier] [shop] [item] [command]");
		System.out.println("			[supplier] - legal supplier code");
		System.out.println("			[shop] - legal shop code");
		System.out.println("			[item] - legal item code");
		System.out.println("			[command] - will be replaced by the following:");
		System.out.println("				ADD [amount]");
		System.out.println("					[amount] - the number of the items to be delivered");
		System.out.println("				UPDATE [amount]");
		System.out.println("					[amount] - the new number of the items to be delivered");
		System.out.println("				REMOVE");
		System.out.println("		UPDATE [attributes]");
		System.out.println("			[attributes]- will be replaced by the following, all are optional:");
		System.out.println("				TIME [time] - legal time in the format HH:MM");
		System.out.println("				DATE [date] - legal date in the format DD/MM/YYYY");
	}

	private void transportMission(String[] words) throws NituzException {
		if(words.length>=7){
			switch (words[6].toUpperCase()) {
				case "ADD":
					if(words.length==8){
						bl.addMission(words[1],words[7],words[4],words[3],words[5]);
						System.out.println("Mission was added");
						return;
					}
				case "UPDATE":
					if(words.length==8){
						bl.updateMission(words[1],words[4],words[5],words[3],words[7]);
						System.out.println("Mission was update");
						return;
					}
				case "REMOVE":
					if(words.length==7){
						bl.updateMission(words[1],words[4],words[5],words[3],"0");
						System.out.println("Mission was Removed");
						return;
					}
				default:
					System.out.println("you have entered a none supported attribute for transport, please try again or type 'TRANSPORT HELP' for instructions.");
			}
		}
		else{
			System.out.println("you have entered an none supported number attributes , please try again or type 'TRANSPORT HELP' for instructions.");
		}
	}

	private void transportAssign(String[] words) throws NituzException {
		if (words.length==4 || words.length==5){
			bl.signTruckTotransport(words[1],words[3]);
			if (words.length==5){
				bl.selectTruckTotransport(words[1],words[3]);
				bl.selectDriverTotransport(words[1],words[4]);
			}
		}
		else{
			System.out.println("you have entered an none supported number attributes , please try again or type 'TRANSPORT HELP' for instructions.");
		}
	}

	private void transportUpdate(String[] words) throws NituzException {
		String time=null;
		String date=null;
		String driver=null;
		String truck=null;
		int i=3;
		while (i<words.length){
			switch (words[i].toUpperCase()){
				case "TIME":
					if(words.length>i)
						time=words[i+1];
					else{
						System.out.println("you haven't entered a time , please try again or type 'TRANSPORT HELP' for instructions.");
					}
					i=i+2;
					break;
				case "DATE":
					if(words.length>i)
						date=words[i+1];
					else{
						System.out.println("you haven't entered a date , please try again or type 'TRANSPORT HELP' for instructions.");
					}
					i=i+2;
					break;
				case "DRIVER":
					if(words.length>i)
						driver=words[i+1];
					else{
						System.out.println("you haven't entered a driver ID , please try again or type 'TRANSPORT HELP' for instructions.");
					}
					i=i+2;
					break;
				case "TRUCK":
					if(words.length>i)
						truck=words[i+1];
					else{
						System.out.println("you haven't entered a truck plate , please try again or type 'TRANSPORT HELP' for instructions.");
					}
					i=i+2;
					break;
				default:
					System.out.println(words[i]+" is not a supported attribute , please try again or type 'TRANSPORT HELP' for instructions.");
					return;
			}
		}
		if(bl.updateTransport(words[1], new String[]{date, time, truck, driver})){
			System.out.println("Transport was updated");
		}
		else{
			throw new NituzException(0, "unknown error has accrued");
		}
	}

	private void transportCreate(String[] words) throws NituzException {
		if (words.length!=5){
			System.out.println("you haven't entered enough attributes , please try again or type 'TRANSPORT HELP' for instructions.");
			return;
		}
		if(bl.addTransport(words[4],words[3], words[1])){
			System.out.println("Transport was created");
		}
		else{
			throw new NituzException(0, "unknown error has accrued");
		}
	}

	private void transportDoc(String[] words) throws NituzException {
		if(words.length>5){
			System.out.println("you have entered too many attributes, please try again or type 'TRANSPORT HELP' for instructions.");
		}
		else if(words.length==5){
			System.out.println(bl.makeDoc(words[1],words[3],words[4]));
		}
		else if(words.length==4){
			System.out.println(bl.makeDoc(words[1],words[3]));
		}
		else if(words.length==3){
			System.out.println(bl.makeDoc(words[1]));
		}
		else {
			System.out.println("you haven't entered a transport ID , please try again or type 'TRANSPORT HELP' for instructions.");
		}
	}

	private void truck(String[] words) throws NituzException {
		if (words.length>=2) {
			switch (words[1].toUpperCase()) {
				case "ADD":
					if(words.length!=7) {
						System.out.println("you have entered an none supported number attributes , please try again or type 'TRUCK HELP' for instructions.");
						return;
					}
					if(bl.addTruck(words[2],words[3],words[4],words[5],words[6])){
						System.out.println("Truck was added successfully");
					}else
						throw new NituzException(0, "unknown error has accrued");
					break;
				case "HELP":
					truckHelp();
					break;
				default:
					System.out.println("you have entered a none supported attribute for truck, please try again or type 'TRUCK HELP' for instructions.");
			}
		}
		else{
			System.out.println("you haven't entered a supported attribute for truck , please try again or type 'TRUCK HELP' for instructions.");
		}
	}

	private void help(){
		System.out.println("SUPPORTED FUNCTIONS:");
		manageHelp();
		//shiftsHelp();
		transportHelp();
		truckHelp();
		System.out.println("LOGOUT - going out of the program with out closing it.");
		System.out.println("HELP - open the help menu");
		System.out.println("EXIT - terminate the program");
	}

	private void truckHelp() {
		System.out.println("TRUCK [command]");
		System.out.println("	[command] - will be replaced by the following:");
		System.out.println("		ADD [plate] [model] [weight] [carry weight] [license]");
		System.out.println("			[plate] - legal truck plate.");
		System.out.println("			[model] - the truck model.");
		System.out.println("			[weight] - the weight of the truck.");
		System.out.println("			[carry weight] - the max weight the truck can carry.");
		System.out.println("			[license] - the license a driver needed to drive the truck.");
		System.out.println("		HELP - open help for truck");
	}

	private String[] trim(String s) {
		String[] splitString=s.split(" ");
		String[] tmp=new String[splitString.length];
		int counter=0;
		for (int i=0;i<splitString.length;i++)
		{
			if (!splitString[i].equals("")){
				tmp[counter]=splitString[i];
				counter++;
			}
		}
		String[] ans=new String[counter];
		for(int i=0;i<counter;i++){
			ans[i]=tmp[i];
		}
		return ans;
	}

}
