JFLAGS = -d classes
JC = javac

SQLPATH = .:sqlite-jdbc-3.34.0.jar

CLASSES = \
		net/stars/Main.java \
		net/stars/entities/Market_Account.java \
		net/stars/entities/Actor.java \
		net/stars/entities/Customer.java \
		net/stars/entities/Manager.java \
		net/stars/entities/MarketGod.java \
		net/stars/entities/Market_Account.java \
		net/stars/entities/Movie.java \
		net/stars/entities/Movie_Contract.java \
		net/stars/entities/Stock.java \
		net/stars/entities/StockAccount.java \
		net/stars/entities/Transaction.java \
		net/stars/UI/Balance.java \
		net/stars/UI/Buy.java \
		net/stars/UI/DTER.java \
		net/stars/UI/Depo.java \
		net/stars/UI/MI.java \
		net/stars/UI/MLog.java \
		net/stars/UI/MovieInfo.java \
		net/stars/UI/stockdisplay.java \
		net/stars/UI/Reg.java \
		net/stars/UI/Sell.java \
		net/stars/UI/TI.java \
		net/stars/UI/TLog.java \
		net/stars/UI/UI.java \
		net/stars/UI/With.java \
		net/stars/UI/addInt.java \
		net/stars/UI/custRep.java \
		net/stars/UI/delTrans.java \
		net/stars/UI/genMonth.java \
		net/stars/UI/listAct.java \
		net/stars/UI/showTransactions.java 

.PHONY: classes

classes:
	$(JC) $(JFLAGS) $(CLASSES)

setup:
	java -cp "$(SQLPATH)":classes net.stars.Main setup

run: 
	java -cp "$(SQLPATH)":classes net.stars.Main run

test:
	java -cp "$(SQLPATH)":classes net.stars.Main test
	
reset: 
	java -cp "$(SQLPATH)":classes net.stars.Main reset
clean:
	rm -r ./classes/*