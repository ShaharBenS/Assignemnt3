package DAL;

import java.sql.*;
import SharedClasses.*;
import SharedClasses.Driver;
import SharedClasses.Worker;

import javax.management.relation.Role;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

public class DAL {

    private Connection c;
    private Items items;
    private Suppliers suppliers;

    public DAL(Connection c, Items items, Suppliers suppliers){
        this.c = c;
        this.items = items;
        this.suppliers = suppliers;
    }
    public boolean selectTruckTotransport(int t, String s) throws NituzException {
        try {
            if (!isSignTruckTotransport(t,s)) {
                this.signTruckTotransport(t,s);
            }
            Statement stmt = c.createStatement();
            String sql;
            sql="SELECT * FROM driverAsiignmetns WHERE driverAsiignmetns.transport="+t+";";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.isBeforeFirst()) {
                sql = "UPDATE driverAsiignmetns SET driverAsiignmetns.truck="+truckplate(s)+" WHERE transport="+t;
                stmt.executeUpdate(sql);
                return true;
            }else {
                sql = "INSERT INTO driverAsiignmetns (truck , transport, driver) VALUES ('" + truckplate(s) + "'," + t + ",NULL )";
                stmt.executeUpdate(sql);
                return true;
            }
        }
        catch (NituzException e){
            throw e;
        }
        catch (SQLException e) {
            throw new NituzException(0, "in DAL function 'add(Transport t)' -"+e.getSQLState()+", "+e.getLocalizedMessage()+", "+ e.getMessage());
        }
        catch (Exception e){
            throw new NituzException(0, "in DAL function 'add(Transport t)' -"+ e.getMessage());
        }
    }

    public boolean signTruckTotransport(int t, String s) throws NituzException {
        try {
            Statement stmt = c.createStatement();
            String sql;
            sql = "INSERT INTO TrucksTrnsportSigning (truck , transport) VALUES ('"+truckplate(s)+"',"+t+")";
            stmt.executeUpdate(sql);
            return true;
        }
        catch (SQLException e) {
            throw new NituzException(0, "in DAL function 'signTruckTotransport(int t, String s)' -"+e.getSQLState()+", "+e.getLocalizedMessage()+", "+ e.getMessage());
        }
        catch (Exception e){
            throw new NituzException(0, "in DAL function 'signTruckTotransport(int t, String s))' -"+ e.getMessage());
        }
    }

    /**
     * add a new Transport, don't add any mission or trucks which are associated withe this Transport
     * @param t - Transport to be added
     * @return
     */
    public boolean add(Transport t) throws NituzException {
        try {
            if (isTransport(t.getNumber())) {
                throw new NituzException(3, "Transport code already exist in DB");
            }
            Statement stmt = c.createStatement();
            String sql;

            sql = "INSERT INTO Transport (TransportNumber , date) VALUES ("+t.getNumber()+",'"+StringToSQLiteFormat(t.getDate(),t.getLeavTime())+"')";

            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            return true;
        }
        catch (NituzException e){
            throw e;
        }
        catch (SQLException e) {
            throw new NituzException(0, "in DAL function 'add(Transport t)' -"+e.getSQLState()+", "+e.getLocalizedMessage()+", "+ e.getMessage());
        }
        catch (Exception e){
            throw new NituzException(0, "in DAL function 'add(Transport t)' -"+ e.getMessage());
        }
    }


    public boolean add(Truck t) throws NituzException {
        try {
            if (isTruck(t.getPalte())) {
                throw new NituzException(3, "Truck plate already exist in DB");
            }
            Statement stmt = c.createStatement();
            String sql;
            sql = "INSERT INTO Trucks (Plate,Model,licenseType,Wight,MaxWight) VALUES ("+truckplate(t.getPalte())+", '"+t.getModel()+"', "+t.getLicenseType()+", "+t.getWeight()+", "+t.getMaxWeight()+")";
            stmt.executeUpdate(sql);
            return true;
        }
        catch (NituzException e){
            throw e;
        }
        catch (SQLException e) {
            throw new NituzException(0, "in DAL function 'add(Truck t)' -"+e.getSQLState()+", "+e.getLocalizedMessage()+", "+ e.getMessage());
        }
        catch (Exception e){
            throw new NituzException(0, "in DAL function 'add(Truck t)' -"+ e.getMessage());
        }
    }

    /**
     * add or update a mission to the DB
     * @param tran - transport code.
     * @param orig - origin of the mission.
     * @param dest - destination of of the mission.
     * @param item - the code of the item to be delivered on the mission.
     * @param quan - the quantity of the item to be delivered.
     * @return ??
     */
    public boolean AoUMission(int tran, int orig, int dest, int item, int quan) throws NituzException {
        try {
            if (!isTransport(tran)) {
                throw new NituzException(3, "transport not found in DB");
            }
            if (!isShop(dest)){
                throw new NituzException(3, "destination not found in DB");
            }
            if (!isSite(orig) || isShop(orig)){
                throw new NituzException(3, "origin not found in DB");
            }
            if (!isItem(item)){
                throw new NituzException(3, "item not found in DB");
            }
            Statement stmt = c.createStatement();
            String sql;
            if(isMission(tran,orig,dest,item)){
                sql = "UPDATE Missions SET actualQ="+quan+" WHERE Missions.shop="+dest+" AND Missions.Supplier="+orig+" AND Missions.Transport="+tran+" AND Missions.item="+item;
                stmt.executeUpdate(sql);
            }
            else {
                sql = "INSERT INTO Missions (shop , Supplier ,Transport , item ,plandQ ,actualQ ) VALUES (" + dest + ", " + orig + ", " + tran + ", " + item + ", " + quan +", "+ quan + ")";
                stmt.executeUpdate(sql);
            }
            return true;
        }
        catch (NituzException e){
            throw e;
        }
        catch (SQLException e) {
            throw new NituzException(0, "in DAL function 'AoUMission(int tran, int orig, int dest, int item, int quan)' -"+e.getSQLState()+", "+e.getLocalizedMessage()+", "+ e.getMessage());
        }
        catch (Exception e){
            throw new NituzException(0, "in DAL function 'AoUMission(int tran, int orig, int dest, int item, int quan)' -"+ e.getMessage());
        }
    }

    public boolean isItem(int _item) throws NituzException {
        try {
            return items.getItem(_item) != null;
        } catch (Exception e) {
            throw new NituzException(1, e.getMessage());
        }
    }

    public boolean isSite(int _to) throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Sites WHERE code =" + _to +";" );
            if(rs.isBeforeFirst()) {
                return true;
            }
            else return false;
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public boolean isShop(int _to) throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Shops WHERE code =" + _to +";" );
            if(rs.isBeforeFirst()) {
                return true;
            }
            else return false;
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public boolean isTruck(String truck) throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Trucks WHERE Plate =" + truckplate(truck) +";" );
            if(rs.isBeforeFirst()) {
                return true;
            }
            else return false;
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public boolean isTransport(int transport) throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Transport WHERE TransportNumber =" + transport +";" );
            if(rs.isBeforeFirst()) {
                return true;
            }
            else return false;
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public boolean isDriver(int id) throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Drivers WHERE ID =" + id +";" );
            if(rs.isBeforeFirst()) {return true;}
            else return false;
        } catch (Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public boolean isSignTruckTotransport(int t, String s) throws NituzException {
        try {
            if (!isTransport(t)) {
                throw new NituzException(3, "Transport code doesn't exist in DB");
            }
            if(!isTruck(s)){
                throw new NituzException(3, "Truck plate doesn't exist in DB");
            }
            Statement stmt = c.createStatement();
            String sql;
            sql = "SELECT * FROM TrucksTrnsportSigning WHERE TrucksTrnsportSigning.truck='"+truckplate(s)+"' AND TrucksTrnsportSigning.transport="+t;
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.isBeforeFirst()) {
                return true;
            }
            else return false;
        }
        catch (NituzException e){
            throw e;
        }
        catch (SQLException e) {
            throw new NituzException(0, "in DAL function 'add(Transport t)' -"+e.getSQLState()+", "+e.getLocalizedMessage()+", "+ e.getMessage());
        }
        catch (Exception e){
            throw new NituzException(0, "in DAL function 'add(Transport t)' -"+ e.getMessage());
        }
    }

    public boolean isMission(int tran, int orig, int dest, int item) throws NituzException {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Missions WHERE Missions.shop="+dest +" AND Missions.Supplier="+orig+" AND Missions.Transport="+tran+" AND Missions.item="+ item);
            if(rs.isBeforeFirst()) {return true;}
            else return false;
        } catch (SQLException e) {
            throw new NituzException(0, "in DAL function 'isMission(int tran, int orig, int dest, int item)' -"+e.getSQLState()+", "+e.getLocalizedMessage()+", "+ e.getMessage());
        }
        catch (Exception e){
            throw new NituzException(0, "in DAL function 'isMission(int tran, int orig, int dest, int item)' -"+ e.getMessage());
        }
    }

    public Site getSite(int _to) throws NituzException {
        if(isShop(_to)) {
            try {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("Select * From Sites JOIN Shops WHERE Sites.code=Shops.code AND Sites.code=" + _to + ";");
                if (rs.isBeforeFirst()) {
                    Site site = new Shop(rs.getInt("code"),rs.getString("Name"), rs.getString("Address"), rs.getString("Phone"), rs.getString("Contact"),rs.getString("region"));
                    stmt.close();
                    rs.close();
                    return site;
                }
            } catch (Exception e) {
                throw new NituzException(1, e.getMessage());
            }
        }
        else{
            try {
                return suppliers.getSupplierWithContact(_to);
            } catch (Exception e) {
                throw new NituzException(1, e.getMessage());
            }
        }
        return null;
    }

    public Item getItem(int _item) throws NituzException {
        try {
            if (isItem(_item)) {
                return items.getItem(_item);
            } else throw new Exception("Item not found");
        } catch (Exception e) {
            throw new NituzException(1, e.getMessage());
        }
    }

    public Transport getTransport(int transport) throws NituzException {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Transport WHERE TransportNumber =" + transport + ";");
            if (rs.isBeforeFirst()) {
                java.util.Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(rs.getString("date"));//rs.getDate("date");
                Transport tran = new Transport(new SimpleDateFormat("dd/MM/yyyy").format(d),new SimpleDateFormat("HH:mm").format(d),transport);
                stmt.close();
                rs.close();
                signAllTucks(tran);
                getAllMissions(tran);
                return tran;
            } else throw new Exception("transport not found");
        }
        catch (NituzException e ){
            throw e;
        }
        catch (Exception e) {
            throw new NituzException(1, e.getMessage());
        }
    }

    private void getAllMissions(Transport tran) throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Missions.Supplier,Missions.item ,SUM(Missions.actualQ) as actualQ,SUM(Missions.plandQ) as plandQ FROM Missions WHERE Missions.Transport="+tran.getNumber()+" Group By Missions.Supplier AND Missions.item");
			if (rs.isBeforeFirst())

				while (rs.next()) {
					Mission m = new Mission(rs.getInt("actualQ"), getSite(rs.getInt("Supplier")),
							getItem(rs.getInt("item")));
					tran.addMission(m);
				}
			rs = stmt.executeQuery("SELECT Missions.shop ,Missions.item ,Missions.actualQ as actualQ,Missions.plandQ as plandQ FROM Missions WHERE Missions.Transport="+ tran.getNumber());
			if (rs.isBeforeFirst())
				while (rs.next()) {
					Mission m = new Mission(rs.getInt("actualQ"), getSite(rs.getInt("shop")),getItem(rs.getInt("item")));
					tran.addMission(m);
				}
        }
        catch (Exception e) {
            throw new NituzException(1, e.getMessage());
        }
    }

    private void signAllTucks(Transport tran) throws NituzException {
        try{
        	Driver d = null;
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM driverAsiignmetns WHERE transport =" + tran.getNumber() + ";");
            String tPlate;
            if (rs.isBeforeFirst()) {
                tPlate = truckplate(rs.getString("truck"));
                if(rs.getObject("driver")!=null)
                    d = getDriver(rs.getInt("driver"));
            }
            else tPlate="";

            rs = stmt.executeQuery("SELECT * FROM TrucksTrnsportSigning WHERE transport =" + tran.getNumber() + ";");
			if (rs.isBeforeFirst())
				while (rs.next()) {
					Truck t = getTruck(truckplate(rs.getString("truck")));
					tran.signTrck(t);
					if (tPlate.equals(t.getPalte())) {
						tran.selectTruck(t);
					}
				}
			if (d!=null)
				tran.setDriver(d);
        }
        catch (NituzException e ){
            throw e;
        }
        catch (Exception e) {
            throw new NituzException(1, e.getMessage());
        }

    }

    public Truck getTruck(String truck) throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Trucks WHERE Plate =" + truckplate(truck) +";" );
            if(rs.isBeforeFirst()) {
                Truck truck1 = new Truck(truckplate(rs.getString("Plate")),rs.getString("Model"),rs.getDouble("Wight"),rs.getDouble("MaxWight"),rs.getInt("licenseType"));
                stmt.close();
                rs.close();
                return truck1;
            }
            else throw new Exception("Truck not found");
        } catch (Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public SharedClasses.Driver getDriver(int id) throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Drivers WHERE ID =" + id +";" );
            if(rs.isBeforeFirst()) {
                Worker w=selectWorker(id);
                SharedClasses.Driver driver = new SharedClasses.Driver(w,rs.getInt("Licence"));
                stmt.close();
                rs.close();
                return driver;
            }
            else throw new Exception("Driver not found");
        } catch (Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    /**
     * change from the format given from source to SQLite format
     * @param date DD/MM/DDDD
     * @param leavTime HH:MM
     * @return
     */
    private String StringToSQLiteFormat(String date, String leavTime) {
        String[] sdate=date.split("/");
        return sdate[2]+"-"+sdate[1]+"-"+sdate[0]+" "+leavTime;
    }

    public boolean selectDriverTotransport(int t, int d) throws NituzException {
        try {
            Statement stmt = c.createStatement();
            String sql;
            sql = "SELECT * FROM driverAsiignmetns WHERE driverAsiignmetns.transport=" + t + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.isBeforeFirst()) {
                sql = "UPDATE driverAsiignmetns SET driver=" + d + " WHERE transport=" + t;
                stmt.executeUpdate(sql);
                return true;
            }
            else{
                throw new NituzException(1,"no truck is assiged for this transport");
            }
        }
        catch (NituzException e){
            throw e;
        }
        catch (SQLException e) {
            throw new NituzException(0, "in DAL function 'add(Transport t)' -" + e.getSQLState() + ", " + e.getLocalizedMessage() + ", " + e.getMessage());
        } catch (Exception e) {
            throw new NituzException(0, "in DAL function 'add(Transport t)' -" + e.getMessage());
        }
    }

    public boolean update(int t, String date, String time) throws NituzException {
        if (isTransport(t)){
            try {
                Statement stmt = c.createStatement();
                String sql;
                sql = "UPDATE Transport SET date='" + StringToSQLiteFormat(date, time) + "' WHERE Transport.TransportNumber=" + t;
                stmt.executeUpdate(sql);
                return true;
            }
            catch (Exception e) {
                throw new NituzException(0, "in DAL function 'add(Transport t)' -" + e.getMessage());
            }
        }
        else
            throw new NituzException(1,"transport not found");
    }

    private String truckplate(String p){
        String ans;
        if(p.indexOf("-")!=-1){
            String[] parts=p.split("-");
            ans=parts[0]+parts[1]+parts[2];
        }
        else{
            ans=p.substring(0,2)+"-"+p.substring(2,6)+"-"+p.substring(6);
        }
        return ans;
    }

    public Boolean addWorker(Worker w) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT ID FROM Workers WHERE ID =" + w.getId() +";" );
            if(rs.isBeforeFirst()){
                return false;
            }
            else{
                Statement stmt2 = c.createStatement();
                String sql = "INSERT INTO Workers (ID,Lname,Fname,startDate,TermsOfEmployment,Salary,Role,WorkPlace,BankNumber,BankAccountNumber) " +
                        "VALUES ("+w.getId()+",'"+w.getLname()+"','"+w.getFname()+"','"+w.getStartDate()+"','"+w.getTerms()+"',"+
                        w.getSalary()+",'"+w.getRole()+"',"+w.getWorkPlace()+","+w.getBankNumber()+","+w.getBankAccountNumber()+");";
                stmt2.executeUpdate(sql);
                stmt2.close();
            }
            rs.close();
            stmt.close();
            return true;
        } catch ( Exception e){
            throw new NituzException(4,e.getMessage());
        }
    }

    public Boolean addDriver(Driver w) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT ID FROM Workers WHERE ID =" + w.getId() +";" );
            if(rs.isBeforeFirst()){
                return false;
            }
            else{
                Statement stmt2 = c.createStatement();
                String sql = "INSERT INTO Workers (ID,Lname,Fname,startDate,TermsOfEmployment,Salary,Role,WorkPlace,BankNumber,BankAccountNumber) " +
                        "VALUES ("+w.getId()+",'"+w.getLname()+"','"+w.getFname()+"','"+w.getStartDate()+"','"+w.getTerms()+"',"+
                        w.getSalary()+",'"+w.getRole()+"',"+w.getWorkPlace()+","+w.getBankNumber()+","+w.getBankAccountNumber()+");";
                stmt2.executeUpdate(sql);
                    sql = "INSERT INTO Drivers (ID,license) " +
                            "VALUES ("+w.getId()+","+w.getLicense()+");";
                    stmt2.executeUpdate(sql);
                stmt2.close();
            }
            rs.close();
            stmt.close();
            return true;
        } catch ( Exception e){
            throw new NituzException(4,e.getMessage());
        }
    }

    public Boolean updateWorker(Worker w) throws  NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT ID FROM Workers WHERE ID =" + w.getId() +";" );
            if(!rs.isBeforeFirst()){
                return false;
            }
            else{
                stmt = c.createStatement();
                String sql = "UPDATE Workers set Lname = '"+w.getLname()+"', Fname = '"+w.getFname()+"'"
                        + ", startDate = '"+w.getStartDate()+"', TermsOfEmployment = '"+w.getTerms()+"', Salary = "+w.getSalary()+
                        ", Role = '"+w.getRole()+"', WorkPlace = '"+w.getWorkPlace()+"', BankNumber = "+w.getBankNumber()+", BankAccountNumber = "
                        +w.getBankAccountNumber()+" where ID="+w.getId()+";";
                stmt.executeUpdate(sql);
                stmt.close();
            }
            rs.close();
            stmt.close();
            return true;
        } catch ( Exception e){
            throw new NituzException(4,e.getMessage());
        }

    }
    /////
    // FIX NEEDED IN HERE
    /////
    public Worker selectWorker(int id) throws  NituzException { // returns null if no worker was found

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Workers WHERE ID =" + id +";" );
            if(rs.isBeforeFirst()) {

                Worker w = new Worker(id, rs.getString("Lname"), rs.getString("Fname"), rs.getString("startDate"), rs.getString("TermsOfEmployment"),
                        rs.getInt("Salary"), rs.getString("Role"), rs.getInt("BankNumber"), rs.getInt("BankAccountNumber"),rs.getInt("WorkPlace"));
                stmt.close();
                rs.close();
                return w;
            }
            else {
                throw new NituzException(1,"user not found");
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public Boolean removeWorker(int id) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT ID FROM Workers WHERE ID =" + id +";" );
            if(rs.isBeforeFirst()) {

                String sql = "DELETE FROM Workers WHERE ID = "+id+";";
                stmt.executeUpdate(sql);
                stmt.close();
                rs.close();
                return true;
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
        return false;
    }

    public Boolean removeDriver(int id) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT ID FROM Workers WHERE ID =" + id +";" );
            if(rs.isBeforeFirst()) {

                String sql = "DELETE FROM Drivers WHERE ID = "+id+";";
                stmt.executeUpdate(sql);
                sql = "DELETE FROM Workers WHERE ID = "+id+";";
                stmt.executeUpdate(sql);
                stmt.close();
                rs.close();
                return true;
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
        return false;
    }


    public LinkedList<PossibleShiftsForWorkers> possibleShiftsForWorkers(int WorkPlace) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Workers JOIN Shifts JOIN WorkersInShifts "
                    + "ON Workers.ID = WorkersInShifts.ID AND Shifts.Code = WorkersInShifts.Code WHERE WorkersInShifts.Status = 'Available' " +
                    "AND Workers.WorkPlace = " + WorkPlace + ";" );
            if(rs.isBeforeFirst()) {

                LinkedList<PossibleShiftsForWorkers> l = new LinkedList<PossibleShiftsForWorkers>();
                PossibleShiftsForWorkers p;
                Worker w;
                Shift s;
                while(rs.next()){
                    w = new Worker(rs.getInt("ID"), rs.getString("Lname"), rs.getString("Fname"), rs.getString("startDate"), rs.getString("TermsOfEmployment"),
                            rs.getInt("Salary"), rs.getString("Role"), rs.getInt("WorkPlace"), rs.getInt("BankNumber"), rs.getInt("BankAccountNumber"));
                    s = new Shift(rs.getInt("Code"), rs.getString("Date"), rs.getString("Day"), rs.getString("Time"), rs.getInt("WorkPlace"), rs.getInt("ShiftManager"));
                    p = new PossibleShiftsForWorkers(w, s);
                    l.add(p);
                }
                stmt.close();
                rs.close();
                return l;
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
        return null;
    }

    public LinkedList<PossibleShiftsForWorkers> shiftsForWorkers(int WorkPlace) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Workers JOIN Shifts JOIN WorkersInShifts "
                    + "ON Workers.ID = WorkersInShifts.ID AND Shifts.Code = WorkersInShifts.Code WHERE WorkersInShifts.Status = 'Working'"
                    + " AND Workers.WorkPlace = "+WorkPlace+";" );
            if(rs.isBeforeFirst()) {

                LinkedList<PossibleShiftsForWorkers> l = new LinkedList<PossibleShiftsForWorkers>();
                PossibleShiftsForWorkers p;
                Worker w;
                Shift s;
                while(rs.next()){
                    w = new Worker(rs.getInt("ID"), rs.getString("Lname"), rs.getString("Fname"), rs.getString("startDate"), rs.getString("TermsOfEmployment"),
                            rs.getInt("Salary"), rs.getString("Role"), rs.getInt("WorkPlace"), rs.getInt("BankNumber"), rs.getInt("BankAccountNumber"));
                    s = new Shift(rs.getInt("Code"), rs.getString("Date"), rs.getString("Day"), rs.getString("Time"), rs.getInt("WorkPlace"), rs.getInt("ShiftManager"));
                    p = new PossibleShiftsForWorkers(w,s);
                    l.add(p);
                }
                stmt.close();
                rs.close();
                return l;
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
        return null;
    }

    public LinkedList<Worker> shiftsForWorkers(Shift s) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Workers JOIN Shifts JOIN WorkersInShifts "
                    + "ON Workers.ID = WorkersInShifts.ID AND Shifts.Code = WorkersInShifts.Code WHERE WorkersInShifts.Status = 'Working'"
                    + " AND Shifts.Code = "+s.getCode()+";" );
            if(rs.isBeforeFirst()) {

                LinkedList<Worker> l = new LinkedList<Worker>();
                Worker w;
                while(rs.next()){
                    w = new Worker(rs.getInt("ID"), rs.getString("Lname"), rs.getString("Fname"), rs.getString("startDate"), rs.getString("TermsOfEmployment"),
                            rs.getInt("Salary"), rs.getString("Role"), rs.getInt("WorkPlace"), rs.getInt("BankNumber"), rs.getInt("BankAccountNumber"));
                    l.add(w);
                }
                stmt.close();
                rs.close();
                return l;
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
        return null;
    }

    public boolean deletePossibleShift(Shift s, int id) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT ID,Code FROM WorkersInShifts WHERE Status = 'Available' AND ID = "+id+" AND Code = "+s.getCode()+";" );
            if(rs.isBeforeFirst()) {

                String sql = "DELETE FROM WorkersInShifts WHERE ID = "+id+" AND Code = "+s.getCode()+" AND Status = 'Available';";
                stmt.executeUpdate(sql);
                stmt.close();
                rs.close();
                return true;
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
        return false;
    }

    public boolean addPossibleShift(Shift s, int id) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT ID,Code FROM WorkersInShifts WHERE Status = 'Available' AND ID = "+id+" AND Code = "+s.getCode()+";" );
            if(rs.isBeforeFirst()){
                return false;
            }
            else{
                Statement stmt2 = c.createStatement();
                String sql = "INSERT INTO WorkersInShifts (ID,Code,Status) " +
                        "VALUES ("+id+","+s.getCode()+",'Available');";
                stmt2.executeUpdate(sql);
                stmt2.close();
            }
            rs.close();
            stmt.close();
            return true;
        } catch ( Exception e){
            throw new NituzException(4,e.getMessage());
        }
    }

    public boolean chooseRolesInShift(String[] roles, Shift s) throws NituzException {

        try{
            for(int i = 0 ; i < roles.length ; i++){
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery( "SELECT Role,Code FROM RolesInShifts WHERE Role = '"+roles[i]+"' AND Code = "+s.getCode()+";" );
                if(!rs.isBeforeFirst()){
                    Statement stmt2 = c.createStatement();
                    String sql = "INSERT INTO RolesInShifts (Role,Code) " +
                            "VALUES ('"+roles[i]+"',"+s.getCode()+");";
                    stmt2.executeUpdate(sql);
                    stmt2.close();
                    return true;
                }
                rs.close();
                stmt.close();
            }
            return false;
        } catch ( Exception e){
            throw new NituzException(4,e.getMessage());
        }
    }

    public boolean chooseRoleInShift(String role, Shift s) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT Role,Code FROM RolesInShifts WHERE Role = '"+role+"' AND Code = "+s.getCode()+";" );
            if(!rs.isBeforeFirst()){
                Statement stmt2 = c.createStatement();
                String sql = "INSERT INTO RolesInShifts (Role,Code) " +
                        "VALUES ('"+role+"',"+s.getCode()+");";
                stmt2.executeUpdate(sql);
                stmt2.close();
            }
            rs.close();
            stmt.close();
            return true;
        } catch ( Exception e){
            throw new NituzException(4,e.getMessage());
        }
    }

    public boolean deleteRoleInShift(String role, Shift s) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT Role,Code FROM RolesInShifts WHERE Role = '"+role+"' AND Code = "+s.getCode()+";" );
            if(rs.isBeforeFirst()) {

                String sql = "DELETE FROM RolesInShifts WHERE Role = '"+role+"' AND Code = "+s.getCode()+";";
                stmt.executeUpdate(sql);
                stmt.close();
                rs.close();
                return true;
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
        return false;
    }

    public boolean removeWorkerFromShift(int id, Shift s) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT ID,Code FROM WorkersInShifts WHERE Status = 'Working' AND ID = "+id+" AND Code = "+s.getCode()+";" );
            if(rs.isBeforeFirst()) {

                String sql = "DELETE FROM WorkersInShifts WHERE ID = "+id+" AND Code = "+s.getCode()+" AND Status = 'Working';";
                stmt.executeUpdate(sql);
                stmt.close();
                rs.close();
                return true;
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
        return false;
    }

    public boolean placeWorkersInShift(int[] ids, Shift s) throws NituzException {

        try{
            for(int i = 0 ; i < ids.length ; i++){
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery( "SELECT ID,Code FROM WorkersInShifts WHERE Status = 'Working' AND ID = "+ids[i]+" AND Code = "+s.getCode()+";" );
                if(rs.isBeforeFirst()){
                    return false;
                }
                else{
                    Statement stmt2 = c.createStatement();
                    String sql = "INSERT INTO WorkersInShifts (ID,Code,Status) " +
                            "VALUES ("+ids[i]+","+s.getCode()+",'Working');";
                    stmt2.executeUpdate(sql);
                    Statement stmt3 = c.createStatement();
                    sql = "INSERT INTO WorkersInShifts (ID,Code,Status) " +
                            "VALUES ("+s.getManager()+","+s.getCode()+",'Working');";
                    stmt3.executeUpdate(sql);
                    stmt3.close();
                    stmt2.close();
                }
                rs.close();
                stmt.close();
            }
            Statement stmt3 = c.createStatement();
            String sql = "UPDATE Shifts set ShiftManager = "+s.getManager()+" WHERE Code = "+s.getCode()+";";
            stmt3.executeUpdate(sql);
            stmt3.close();
            return true;
        } catch ( Exception e){
            throw new NituzException(4,e.getMessage());
        }
    }

    public boolean placeWorkerInShift(int id, Shift s) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT ID,Code FROM WorkersInShifts WHERE Status = 'Working' AND ID = "+id+" AND Code = "+s.getCode()+";" );
            if(rs.isBeforeFirst()){
                return false;
            }
            else{
                Statement stmt2 = c.createStatement();
                String sql = "INSERT INTO WorkersInShifts (ID,Code,Status) " +
                        "VALUES ("+id+","+s.getCode()+",'Working');";
                stmt2.executeUpdate(sql);
                stmt2.close();
            }
            rs.close();
            stmt.close();
            return true;
        } catch ( Exception e){
            throw new NituzException(4,e.getMessage());
        }
    }

    public boolean removeWorkerInShift(int id, Shift s) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT ID,Code FROM WorkersInShifts WHERE Status = 'Working' AND ID = "+id+" AND Code = "+s.getCode()+";" );
            if(!rs.isBeforeFirst()){
                return false;
            }
            else{
                Statement stmt2 = c.createStatement();
                String sql = "DELETE FROM WorkersInShifts (ID,Code,Status) " +
                        "VALUES ("+id+","+s.getCode()+",'Working');";
                stmt2.executeUpdate(sql);
                stmt2.close();
            }
            rs.close();
            stmt.close();
            return true;
        } catch ( Exception e){
            throw new NituzException(4,e.getMessage());
        }
    }

    public boolean addShift(Shift s) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT Code FROM Shifts WHERE Code =" + s.getCode() +";" );
            if(rs.isBeforeFirst()){
                return false;
            }
            else{
                String Day = "";
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);

                switch (day) {
                    case Calendar.SUNDAY:
                        Day = "SUNDAY";
                        break;
                    case Calendar.MONDAY:
                        Day = "MONDAY";
                        break;
                    case Calendar.TUESDAY:
                        Day = "TUESDAY";
                        break;
                    case Calendar.WEDNESDAY:
                        Day = "WEDNESDAY";
                        break;
                    case Calendar.THURSDAY:
                        Day = "THURSDAY";
                        break;
                    case Calendar.FRIDAY:
                        Day = "FRIDAY";
                        break;
                    case Calendar.SATURDAY:
                        Day = "SATURDAY";
                        break;
                }

                Statement stmt2 = c.createStatement();
                String sql = "INSERT INTO Shifts (Code,Date,Day,Time,WorkPlace,ShiftManager) " +
                        "VALUES ("+s.getCode()+",'"+s.getDate()+"','"+Day+"','"+s.getTime()+"',"+s.getWorkPlace()+","+s.getManager()+");";
                stmt2.executeUpdate(sql);
                stmt2.close();
            }
            rs.close();
            stmt.close();
            return true;
        } catch ( Exception e){
            throw new NituzException(4,e.getMessage());
        }
    }

    public boolean deleteShift(int Code) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT Code FROM Shifts WHERE Code =" + Code +";" );
            if(rs.isBeforeFirst()) {

                String sql = "DELETE FROM Shifts WHERE Code = "+Code+";";
                stmt.executeUpdate(sql);
                stmt.close();
                rs.close();
                return true;
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
        return false;
    }

    public Shift getShift(String Date, String Time, int WorkPlace) throws NituzException{

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Shifts WHERE Date = '" + Date +"' AND Time = '"+Time+"' AND WorkPlace = "+WorkPlace+";" );
            if(rs.isBeforeFirst()) {

                Shift s = new Shift(rs.getInt("Code"), rs.getString("Date"), rs.getString("Day"), rs.getString("Time"), rs.getInt("WorkPlace"), rs.getInt("ShiftManager"));
                stmt.close();
                rs.close();
                return s;
            }
            else {
                throw new NituzException(1,"Shift not found on "+Date+" "+Time+" in workplace "+WorkPlace);
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public boolean addRole(String role) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT Role FROM Roles WHERE Role = '" + role +"';" );
            if(rs.isBeforeFirst()){
                return false;
            }
            else{
                Statement stmt2 = c.createStatement();
                String sql = "INSERT INTO Roles (Role) " +
                        "VALUES ('"+role+"');";
                stmt2.executeUpdate(sql);
                stmt2.close();
            }
            rs.close();
            stmt.close();
            return true;
        } catch ( Exception e){
            throw new NituzException(4,e.getMessage());
        }
    }

    public boolean deleteRole(String role) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT Role FROM Roles WHERE Role ='" + role +"';" );
            if(rs.isBeforeFirst()) {

                String sql = "DELETE FROM Roles WHERE Role = '"+role+"';";
                stmt.executeUpdate(sql);
                stmt.close();
                rs.close();
                return true;
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
        return false;
    }

    public boolean addBank(int bankNum, String name) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT BankNumber FROM Banks WHERE BankNumber = " + bankNum +";" );
            if(rs.isBeforeFirst()){
                return false;
            }
            else{
                Statement stmt2 = c.createStatement();
                String sql = "INSERT INTO Banks (BankNumber,BankName) " +
                        "VALUES ("+bankNum+",'"+name+"');";
                stmt2.executeUpdate(sql);
                stmt2.close();
            }
            rs.close();
            stmt.close();
            return true;
        } catch ( Exception e){
            throw new NituzException(4,e.getMessage());
        }
    }

    public boolean deleteBank(int bankNum) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT BankNumber FROM Banks WHERE BankNumber = " + bankNum +";" );
            if(rs.isBeforeFirst()) {

                String sql = "DELETE FROM Banks WHERE BankNumber = "+bankNum+";";
                stmt.executeUpdate(sql);
                stmt.close();
                rs.close();
                return true;
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
        return false;
    }

    public Shift getShift(int code) throws NituzException{

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Shifts WHERE Code = " + code +";" );
            if(rs.isBeforeFirst()) {

                Shift s = new Shift(rs.getInt("Code"), rs.getString("Date"), rs.getString("Day"), rs.getString("Time"), rs.getInt("WorkPlace"), rs.getInt("ShiftManager"));
                stmt.close();
                rs.close();
                return s;
            }
            else {
                return null;
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public boolean hasShiftManager(Shift s) throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Shifts WHERE ShiftManager = " + s.getManager() +";" );
            if(rs.isBeforeFirst()) {
                stmt.close();
                rs.close();
                return true;
            }
            else {
                return false;
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public boolean setShiftManager(int id, int code) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT Code FROM Shifts WHERE Code = " + code +";" );
            if(rs.isBeforeFirst()){
                return false;
            }
            else{
                Statement stmt2 = c.createStatement();
                String sql = "INSERT INTO Shifts (Code,Date,Day,Time,WorkPlace,ShiftManager) " +
                        "VALUES ("+rs.getInt("Code")+",'"+rs.getString("Date")+"','"+rs.getString("Day")+"','"+rs.getString("Time")+"',"+rs.getInt("WorkPlace")+","+id+");";
                stmt2.executeUpdate(sql);
                stmt2.close();
            }
            rs.close();
            stmt.close();
            return true;
        } catch ( Exception e){
            throw new NituzException(4,e.getMessage());
        }
    }

    public LinkedList<String> rolesInShift(Shift s) throws NituzException {

        try{
            Statement stmt = c.createStatement();
            LinkedList<String> lst = new LinkedList<String>();
            ResultSet rs = stmt.executeQuery( "SELECT Role FROM RolesInShifts WHERE Code =" + s.getCode() +";" );
            if(rs.isBeforeFirst()) {

                while(rs.next()){
                    lst.add(rs.getString("Role"));
                }
                stmt.close();
                rs.close();
                return lst;
            }
            else {
                return null;
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public int maxShiftCode() throws NituzException{
        try{
            int maxCode = 0;
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT Code FROM Shifts ORDER BY Code DESC LIMIT 1;" );
            if(rs.isBeforeFirst()) {
                maxCode = rs.getInt("Code");
                stmt.close();
                rs.close();
            }
            return maxCode;
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public int storeKeepersInShift(Shift s) throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT ID FROM WorkersInShifts WHERE Status = 'Working' AND Code = " + s.getCode() +";" );
            if(rs.isBeforeFirst()) {
                int i = 0;
                while(rs.next()){
                    i++;
                }
                return i;
            }
            else return 0;
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    /**public int transportsInShift(Shift s) throws NituzException {
        here Adi, i need all the Transports scheduled for s.Date, s.Time(מופיע כסטרינג של "morning" או "evening" "night"), s.WorkPlace (סניף).}
    }*/

    public Connection getCon(){
        return this.c;
    }


    public boolean updateDriver(Driver driver) throws NituzException {
        if (updateWorker(driver)){
            try {
                Statement stmt = c.createStatement();
                String sql;
                sql="UPDATE Drivers set license = "+driver.getLicense()+" WHERE ID = "+driver.getId()+";";
                stmt.executeUpdate(sql);
                return true;
            }catch (Exception e){ throw new NituzException(0,e.getMessage());}
        }
        else return false;
    }

    public Boolean isWorkerInShift(int id, String date, String time) throws  NituzException {

        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM WorkersInShifts JOIN Shifts ON WorkersInShifts.Code = Shifts.Code WHERE WorkersInShifts.ID =" + id +
                    "AND Shifts.Date = " + date + " AND Shifts.Time = " + time + ";" );
            if(rs.isBeforeFirst()) {

                return true;
            }
            else {
                return false;
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public boolean transportsInShift(Shift s) throws NituzException {
        try{
            Statement stmt = c.createStatement();
            String sql="";
            switch (s.getTime().toUpperCase()){
                case ("MORNING"):
                    sql="SELECT * FROM Missions JOIN Transport WHERE Missions.Transport==Transport.TransportNumber AND Transport.date>=\'"+
                            StringToSQLiteFormat(s.getDate(), "00:01")+ "\' AND Transport.date<=\'"+StringToSQLiteFormat(s.getDate(), "07:59")+"\';";
                    break;
                case ("EVENING"):
                    sql="SELECT * FROM Missions JOIN Transport WHERE Missions.Transport==Transport.TransportNumber AND Transport.date>=\'"+
                            StringToSQLiteFormat(s.getDate(), "08:00")+ "\' AND Transport.date<=\'"+StringToSQLiteFormat(s.getDate(), "15:59")+"\';";
                    break;
                case ("NIGHT"):
                    sql="SELECT * FROM Missions JOIN Transport WHERE Missions.Transport==Transport.TransportNumber AND Transport.date>=\'"+
                            StringToSQLiteFormat(s.getDate(), "16:00")+ "\' AND Transport.date<=\'"+StringToSQLiteFormat(s.getDate(), "24:00")+"\';";
                    break;
                default:
                    throw new NituzException(1,s.getTime()+" is not supported time of the day");
            }
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.isBeforeFirst()) {
                return true;
            }
            else {
                return false;
            }

        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public boolean hasRole(String role) throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT Role FROM Roles WHERE Role = '" + role +"';" );
            if(rs.isBeforeFirst()) {
                stmt.close();
                rs.close();
                return true;
            }
            else {
                return false;
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public boolean hasShift(Shift s) throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Shifts WHERE Code = " + s.getCode() + " AND Date = '" + s.getDate() + "' AND Day = '"+ s.getDay() +
                    "' AND Time = '" + s.getTime() + "' AND ShiftManager = " + s.getManager() +" AND WorkPlace = " + s.getWorkPlace() + ";" );
            if(rs.isBeforeFirst()) {
                stmt.close();
                rs.close();
                return true;
            }
            else {
                return false;
            }
        } catch ( Exception e){
            throw new NituzException(1,"There isn't a shift entered");
        }
    }

    public Truck[] getAllTrucks() throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Trucks;" );
            if(rs.isBeforeFirst()) {
                LinkedList<Truck> ans=new LinkedList<Truck>();
                while (rs.next()) {
                    Truck truck1 = new Truck(truckplate(rs.getString("Plate")), rs.getString("Model"), rs.getDouble("Wight"), rs.getDouble("MaxWight"), rs.getInt("licenseType"));
                    ans.addLast(truck1);
                }
                Truck w[]=new Truck[ans.size()];
                for (int i=0;i<w.length;i++){
                    w[i]=ans.get(i);
                }
                stmt.close();
                rs.close();
                return w;
            }
            else throw new Exception("There are no Trucks in DB");
        } catch (Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public Transport[] getAllTransports() throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Transport;" );
            if(rs.isBeforeFirst()) {
                LinkedList<Transport> ans=new LinkedList<Transport>();
                while (rs.next()) {
                    java.util.Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(rs.getString("date"));//rs.getDate("date");
                    Transport tran = new Transport(new SimpleDateFormat("dd/MM/yyyy").format(d),new SimpleDateFormat("HH:mm").format(d),Integer.parseInt(rs.getString("TransportNumber")));
                    ans.addLast(tran);
                }
                Transport w[]=new Transport[ans.size()];
                for (int i=0;i<w.length;i++){
                    w[i]=ans.get(i);
                }
                stmt.close();
                rs.close();
                return w;
            }
            else throw new Exception("There are no Trucks in DB");
        } catch (Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }


    public Worker[] getAllWorkers(int workPlace) throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Workers WHERE WorkPlace =" + workPlace +";" );
            if(rs.isBeforeFirst()) {
                LinkedList<Worker> ans=new LinkedList<Worker>();
                while (rs.next()) {
                    Worker w;
                    if(rs.getString("Role").contentEquals("Driver"))
                    {
                        w=getDriver(rs.getInt("ID"));
                    }else {
                        w = new Worker(rs.getInt("ID"), rs.getString("Lname"), rs.getString("Fname"), rs.getString("startDate"), rs.getString("TermsOfEmployment"),
                                rs.getInt("Salary"), rs.getString("Role"), rs.getInt("BankNumber"), rs.getInt("BankAccountNumber"), workPlace);
                    }
                    ans.add(w);
                }
                Worker w[]=new Worker[ans.size()];
                for (int i=0;i<w.length;i++){
                    w[i]=ans.get(i);
                }
                stmt.close();
                rs.close();
                return w;
            }
            else {
                throw new NituzException(1,"user not found");
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public Bank[] getAllBanks() throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Banks;" );
            if(rs.isBeforeFirst()) {
                LinkedList<Bank> ans=new LinkedList<Bank>();
                while (rs.next()) {
                    Bank b = new Bank(rs.getString("BankName"),rs.getInt("BankNumber"));
                    ans.add(b);
                }
                Bank w[]=new Bank[ans.size()];
                for (int i=0;i<w.length;i++){
                    w[i]=ans.get(i);
                }
                stmt.close();
                rs.close();
                return w;
            }
            else {
                throw new NituzException(1,"user not found");
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public String[] getAllRoles() throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Roles;" );
            if(rs.isBeforeFirst()) {
                LinkedList<String> ans=new LinkedList<String>();
                while (rs.next()) {
                    String s = rs.getString("Role");
                    ans.add(s);
                }
                String w[]=new String[ans.size()];
                for (int i=0;i<w.length;i++){
                    w[i]=ans.get(i);
                }
                stmt.close();
                rs.close();
                return w;
            }
            else {
                throw new NituzException(1,"user not found");
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public String[] getAllRoles(int code) throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM RolesInShifts WHERE Code="+code+";" );
            if(rs.isBeforeFirst()) {
                LinkedList<String> ans=new LinkedList<String>();
                while (rs.next()) {
                    String s = rs.getString("Role");
                    ans.add(s);
                }
                String w[]=new String[ans.size()];
                for (int i=0;i<w.length;i++){
                    w[i]=ans.get(i);
                }
                stmt.close();
                rs.close();
                return w;
            }
            else {
                throw new NituzException(1,"user not found");
            }
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public void removeTruck(String plate) throws NituzException {
        try{
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT Plate FROM Trucks WHERE Plate =" + this.truckplate(plate) +";" );
            if(rs.isBeforeFirst()) {

                String sql = "DELETE FROM Trucks WHERE Plate = "+ this.truckplate(plate) +";";
                stmt.executeUpdate(sql);
                stmt.close();
                rs.close();
                return;
            }
            throw new NituzException(1,"Truck not found");
        } catch ( Exception e){
            throw new NituzException(1,e.getMessage());
        }
    }

    public void zeroiesTransport(int orderID) throws NituzException {
        try {
            Statement stmt = c.createStatement();
            String sql = "UPDATE Missions SET actualQ= 0 WHERE  Missions.Transport=" + orderID ;
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            throw new NituzException(0,"unknown error:"+e.getMessage());
        }
    }
}
