/**
 * Created by Maria Rudenko on 20.04.2015.
 */
public class Dividend {
    private long divId;
    private Integer tradeId;
    private Integer acct;
    private Integer amt;
    private static final long ID_LIMIT = 10000000000L;
    private static long lastID = 0;

    Dividend(Integer tradeId, Integer acct, Integer amt) {
        this.tradeId = tradeId;
        this.acct = acct;
        this.amt = amt;
        divId = getID();
    }

    public String toString(){
        String str;
        str = divId + "," + tradeId.toString() + "," + acct.toString() + "," + amt.toString();
        return str;
    }

    public static long getID(){
        long id = System.currentTimeMillis()%ID_LIMIT;
        if ( id <= lastID ) {
            id = (lastID + 1) % ID_LIMIT;
        }
        return lastID = id;
    }
}
