import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Maria Rudenko on 19.04.2015.
 */
public class DividendCalculator {

    private Date exDate, recDate;
    private Integer amtPerShare;

    DividendCalculator(String exDate, String recDate, Integer amtPerShare) {
        this.exDate = getDate(exDate);
        this.recDate = getDate(recDate);
        this.amtPerShare = amtPerShare;
    }

    public Dividend calculateDividends(Integer tradeId, Integer seller, Integer buyer, Integer amt, String TD){
        Dividend div;
        Integer receiver;
        Date tradeDate = getDate(TD);

        if(exDate.after(tradeDate)) {
            receiver = buyer;
        }
        else {
            receiver = seller;
        }

        div = new Dividend(tradeId, receiver, amt*amtPerShare);
        return  div;
    }

    public Claim calculateClaims(Integer tradeId, Integer seller, Integer buyer, Integer amt, String TD, String SD) {
        Claim claim;
        Integer entAcct, recAcct;
        Date tradeDate = getDate(TD);
        Date settlementDate = getDate(SD);

        if(exDate.after(tradeDate)) {
            entAcct = buyer;
        }
        else {
            entAcct = seller;
        }

        // recAcct - тот у кого акции на момент record date ???
        if(recDate.after(settlementDate)) {
            recAcct = buyer;
        }
        else {
            recAcct = seller;
        }

        if(recAcct.equals(entAcct)) {
            claim = new Claim(tradeId, recAcct, entAcct, amt*amtPerShare);
        }
        else {
            claim = null;
        }

        return  claim;
    }


    private Date getDate(String str) {
        Date date = null;
        DateFormat format = new SimpleDateFormat("ddMMyyyy");
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            System.out.println(e.toString());
        }

        return date;
    }

}
