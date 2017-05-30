package PL;

import SharedClasses.Contact;

import java.util.Scanner;

import BL.SupplierBL;

public class PLContacts {

    SupplierBL bl;
    Scanner sc;

    public PLContacts(SupplierBL bl, Scanner sc) {
        this.bl = bl;
        this.sc = sc;
    }


    ////////////////////////////////////// CONTACTS //////////////////////////////////////
    private void printOptions3() {
        System.out.println("Please choose an option:");
        System.out.println("1- Add new contact");
        System.out.println("2- Update contact's details");
        System.out.println("3- Get exist contact");
        System.out.println("4- Delete exist contact");
        System.out.println("5- Back");

    }

    private void printOptions32() {
        System.out.println("Please choose an option:");
        System.out.println("1- Update contact's email");
        System.out.println("2- Update contact's phone number");
        System.out.println("3- Update contact's name");
        System.out.println("4- Back");
    }

    public void case3() {
        int option;
        printOptions3();

        try {

            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    //add
                    case31();
                    break;
                case 2:
                    //update
                    case32();
                    break;
                case 3:
                    //get
                    case33();
                    break;
                case 4:
                    //delete
                    case34();
                    break;
                case 5:
                    //back
                    return;

                default:
                    System.out.println("ERROR! wrong operation");
                    case3();
                    break;
            }
        } catch (Exception e) {
            System.out.println("ERROR! not a number");
        }
    }

    private int getSupID() {
        int supID = 0;
        System.out.println("Please enter supplier ID");
        try {
            supID = Integer.parseInt(sc.nextLine());
            if (supID <= 0) {
                System.out.println("ERROR! invalid ID");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("ERROR! invalid ID");
            return 0;
        }

        boolean Exist = bl.checkSupExist(supID);
        if (Exist)
            return supID;
        else {
            System.out.println("ERROR! supplier id isn't exist");
            return 0;
        }
    }

    //option: 1-add, 0-exist.
    private String getConID(int option, int supID) {
        String conID = "";
        System.out.println("Please enter contact ID (9 digits)");
        try {
            conID = sc.nextLine();
            if (!(conID.length() == 9)) {
                System.out.println("ERROR! invalid ID");
                return "";
            }
        } catch (Exception e) {
            System.out.println("ERROR! invalid ID");
            return "";
        }
        boolean Exist;
        if (supID == -1) {
            Exist = bl.checkExistConID(conID);
        } else
            Exist = bl.checkExistConSup(supID, conID);

        if (option == 1) {
            if (!Exist)
                return conID;
            else {
                System.out.println("ERROR! contact is already exist ");
                return "";
            }
        } else {
            if (Exist)
                return conID;
            else {
                System.out.println("ERROR! contact is already exist or isn't exist with this supplier");
                return "";
            }
        }
    }

    //add contact
    public void case31() {
        int supID = getSupID();
        String conID = "";
        if (supID != 0)
            conID = getConID(1, supID);
        String FullName;
        String phone;
        String mail;
        if (bl.checkExistConID(conID)) {
            if (bl.addContact(supID, conID, bl.getContactName(conID), bl.getContactPhone(conID), bl.getContactEmail(conID)))
                System.out.println("Contact has been added successfully");
            else {
                System.out.println("ERROR! something went wrong");
                case3();
            }

        } else if (supID != 0 && !conID.equals("")) {
            System.out.println("Please enter contact's full name");
            FullName = sc.nextLine();
            System.out.println("Please enter contact's phone number (10 digits - starts with 05..)");
            phone = sc.nextLine();
            if (checkPhone(phone)) {
                System.out.println("Please enter contact's e-mail");
                mail = sc.nextLine();
                if (checkMail(mail)) {
                    if (bl.addContact(supID, conID, FullName, phone, mail))
                        System.out.println("Contact has been added successfully");
                    else {
                        System.out.println("ERROR! something went wrong");
                        return;
                    }
                } else {
                    System.out.println("ERROR! invalid mail");
                    return;
                }
            } else {
                System.out.println("ERROR! invalid phone number");
                return;
            }
        } else {
            System.out.println("ERROR! something went wrong");
            return;
        }
    }

    //update contact
    public void case32() {
        int option;
        printOptions32();
        try {
            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                //update email
                case 1:
                    case321();
                    break;
                //update phone
                case 2:
                    case322();
                    break;
                //update name
                case 3:
                    case323();
                    break;
                //back
                case 4:
                    case3();
                    break;

                default:
                    System.out.println("ERROR! wrong operation");
                    case32();
                    break;
            }
        } catch (Exception e) {
            System.out.println("ERROR! not a number");
        }
    }

    //update email contact
    public void case321() {
        String conID = "";
        conID = getConID(0, -1);

        String email;
        if (!conID.equals("")) {
            System.out.println("Please enter contact's new E-mail");
            email = sc.nextLine();
            if (checkMail(email)) {
                bl.setContact(conID, email, 3);
                System.out.println("update successfully");
            } else {
                System.out.println("ERROR! invalid mail");
                case32();
            }
        } else
            case32();
    }

    //update phone contact
    public void case322() {
        String conID = "";
        conID = getConID(0, -1);
        String phone;
        if (!conID.equals("")) {
            System.out.println("Please enter contact's new phone number");
            phone = sc.nextLine();
            if (checkPhone(phone)) {
                bl.setContact(conID, phone, 2);
                System.out.println("update successfully");
            } else {
                System.out.println("ERROR! invalid phone number");
                case32();
            }
        } else
            case32();
    }

    //update name contact
    public void case323() {
        String conID = "";
        conID = getConID(0, -1);

        String name;

        if (!conID.equals("")) {
            System.out.println("Please enter contact's new name");
            name = sc.nextLine();
            bl.setContact(conID, name, 1);
            System.out.println("update successfully");
        } else
            case32();
    }

    //get contact details.
    public void case33() {
        String conID = "";
        conID = getConID(0, -1);

        if (!conID.equals("")) {
            Contact con =bl.getContact(conID);
            String ans ="";
            ans+="Supplier ID: " + con.getSiteID()+ "\n";
            ans+= "Contact ID: " + con.getId() + "\n";
            ans += "Name: " + con.getFullName() + "\n";
            ans += "Phone number: " + con.getPhoneNumber() + "\n";
            ans += "Email: " + con.getEmail() + "\n";
            System.out.println(ans);
        }
        case3();
    }

    //delete contact.
    public void case34() {
        int supID = getSupID();
        String conID = "";
        if (supID != 0)
            conID = getConID(0, supID);

        if (supID != 0 && !conID.equals("")) {
            if (bl.removeContact(conID, supID)) System.out.println("deleted successfully");
            else {
                System.out.println("something went wrong");
                case3();
            }
        } else case3();
    }

    private boolean checkPhone(String phone) {
        if (phone.length() != 10) {
            return false;
        }
        if (phone.charAt(0) != '0' || phone.charAt(1) != '5') {
            return false;
        }
        return true;
    }

    private boolean checkMail(String mail) {
        if (mail.split("@").length < 2) {
            return false;
        }
        return true;
    }
}
