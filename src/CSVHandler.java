import org.apache.commons.csv.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Maria Rudenko on 19.04.2015.
 */
public class CSVHandler extends TextHandler {
    // file directory
    private String filename;
    private Path divFile;
    private Path claimFile;
    private Path inputDir;
    // calculator for dividends
    private DividendCalculator calculator;
    // input data
    private static final String TRADE_ID = "Trade_id";
    private static final String SELLER = "Seller_acct";
    private static final String BUYER = "Buyer_acct";
    private static final String AMOUNT = "Amount";
    private static final String TD = "TD";
    private static final String SD = "SD";
    private String shareName;
    private String exDate;
    private String recDate;


    CSVHandler(Path in, Path outputDir, String filename) {
        this.filename = filename;
        inputDir = in;

        Integer amtPerShare = null;
        int count_ = 1;
        int j = 0;
        for (int i = 0; i < filename.length(); i++) {
            if (filename.charAt(i) == '_') {
                switch (count_) {
                    case 1:
                        shareName = filename.substring(j, i);
                        j = i + 1;
                        break;
                    case 2:
                        exDate = filename.substring(j, i);
                        j = i + 1;
                        break;
                    case 3:
                        recDate = filename.substring(j, i);
                        j = i + 1;
                        break;
                }
                count_++;
            }

            if (filename.charAt(i) == '.') {
                amtPerShare = Integer.parseInt(filename.substring(j, i));
            }
        }

        calculator = new DividendCalculator(exDate, recDate, amtPerShare);
        divFile = Paths.get(outputDir + "/" + "DIV_" + shareName + "_" + exDate + "_" + recDate + ".csv");
        claimFile = Paths.get(outputDir + "/" + "CAMIMS_" + shareName + "_" + exDate + "_" + recDate + ".csv");

    }

    public void recast() {

        String inp = inputDir.toString() + "\\" + filename;

        try (FileReader in = new FileReader(inp);
             FileWriter divOut = new FileWriter(divFile.toString());
             FileWriter clOut = new FileWriter(claimFile.toString())) {

            BufferedWriter divBw = new BufferedWriter(divOut);
            BufferedWriter claimBw = new BufferedWriter(clOut);

            // header
            divBw.write("Div_id," + TRADE_ID + "," + "Acct," + "Amount");
            divBw.newLine();
            claimBw.write("Claim_id," + TRADE_ID + "," + "Rec_acct," + "Ent_acct," + "Amount");
            claimBw.newLine();


            CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(TRADE_ID, SELLER, BUYER, AMOUNT, TD, SD);
            CSVParser csvParser = new CSVParser(in, csvFormat);
            List<CSVRecord> csvRecords = csvParser.getRecords();

            Integer tradeId, seller, buyer, amt;
            String tradeDate, settleDate;
            Dividend div;
            Claim claim;


            for (int i = 1; i < csvRecords.size(); i++) {
                tradeId = Integer.parseInt(csvRecords.get(i).get(TRADE_ID));
                seller = Integer.parseInt(csvRecords.get(i).get(SELLER));
                buyer = Integer.parseInt(csvRecords.get(i).get(BUYER));
                amt = Integer.parseInt(csvRecords.get(i).get(AMOUNT));
                tradeDate = csvRecords.get(i).get(TD);
                settleDate = csvRecords.get(i).get(SD);
                div = calculator.calculateDividends(tradeId, seller, buyer, amt, tradeDate);
                claim = calculator.calculateClaims(tradeId, seller, buyer, amt, tradeDate, settleDate);
                divBw.write(div.toString());
                divBw.newLine();
                if(claim != null) claimBw.write(claim.toString());
                claimBw.newLine();
            }

            divBw.flush();
            divBw.close();
            claimBw.flush();
            claimBw.close();
            Main.LOGGER.info("Processing of dividends calculation completed successfully.");

        } catch (FileNotFoundException e) {
            System.out.println("File is not found");
        } catch (IOException e) {
            System.out.println("IO error");
        }

        try {
            Files.delete(Paths.get(inp));
        } catch (IOException e) {
            System.out.println("Impossible to delete file from input directory");
        }
    }

    public Path getDivFile() {
        return divFile;
    }

    public Path getClaimFile() {
        return claimFile;
    }

}
