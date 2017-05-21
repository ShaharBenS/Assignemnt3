package SharedClasses;

/**
 * Created by Omri on 19-Apr-17.
 */
public class Price
{
    private int itemID;
    private double sell_price;
    private int perecentage;
    private Date start;
    private Date end;

    public Price(int itemID, double sell_price, int perecentage, Date start, Date end) {
        this.itemID = itemID;
        this.sell_price = sell_price;
        this.perecentage = perecentage;
        this.start = start;
        this.end = end;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public double getSell_price() {
        return sell_price;
    }

    public void setSell_price(double sell_price) {
        this.sell_price = sell_price;
    }

    public int getPerecentage() {
        return perecentage;
    }

    public void setPerecentage(int perecentage) {
        this.perecentage = perecentage;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }


    //NOTE: PRINTING WITHOUT ID !
    @Override
    public String toString() {
        String str = "";

        str += "----- PRICE -----\n";
        str += "SELL PRICE: " + sell_price +"\n";
        str += "% DISCOUNT: " + perecentage +"\n";
        if(start == null) str += "DATE START: NULL\n";
        else str += "DATE START: " + start.toString() +"\n";
        if(end == null) str += "DATE END: NULL\n";
        else  str += "DATE END: " + end.toString() +"\n";
        str += "----- PRICE -----\n";

        return str;
    }

}
