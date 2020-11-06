/**
 * Created by Maria Rudenko on 20.04.2015.
 */
public class Claim {
    private long claimId;
    private Integer tradeId;
    private Integer recAcct;
    private Integer entAcct;
    private Integer amt;

    Claim(Integer tradeId, Integer recAcct, Integer entAcct, Integer amt) {
        this.tradeId = tradeId;
        this.recAcct = recAcct;
        this.entAcct = entAcct;
        this.amt = amt;
        claimId = Dividend.getID();
    }

    public String toString(){
        String str;
        str = claimId + "," + tradeId.toString() + "," + recAcct.toString() + "," + entAcct.toString() + "," + amt.toString();
        return str;
    }
}
