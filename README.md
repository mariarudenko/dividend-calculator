# dividend-calculator
Test task, 2015

jdk7 +
Apache Commons CSV

properties.xml contains a directories to logs, input and output files

Input Data format:
CSV with header
name: <Share name>_<EX date >_<REC date>_<Dividend amount per share>.csv  (example: GP123456_21082013_27082013_7.csv)
fields: Trade_id, Seller_acct, Buyer_acct, Amount, TD, SD

Trade_id – trade unique id, 10 digits
Seller_acct– seller account, 8 digits
Buyer_acct – buyer account, 8 digits
Amount – number of shares, integer
TD – Trade Date
SD – Settled date

Dates format: day number, month number, year without any delimits. (example: 01092013)

input_example contains the simplest example of input file. One can copy this in the input directory after launching the program and monitoring starts.
