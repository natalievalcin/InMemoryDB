import java.util.HashMap;
import java.util.Map;

interface InMemoryDB {
    int get(String key);
    void put(String key, int val) throws Exception;
    void begin_transaction();
    void commit() throws Exception;
    void rollback() throws Exception;
}

public class InMemoryDBClass implements InMemoryDB {
    private Map<String, Integer> transaction;
    private Map<String, Integer> dataBase;

    private boolean inProgress;

    public  InMemoryDBClass() {
        transaction = new HashMap<>();
        dataBase = new HashMap<>();
        inProgress = false;
    }

    @Override
    public int get(String key) {
        return dataBase.getOrDefault(key, 0);
    }

    @Override
    public void put(String key, int val) throws Exception {
        if (!inProgress) {
            throw new Exception("No transaction in progress!");
        }
        else {
            if (!transaction.containsKey(key))
                transaction.put(key, val);
            else
                transaction.replace(key, val);
        }
    }

    @Override
    public void begin_transaction() {
        inProgress = true;
        transaction.clear();;
    }

    @Override
    public void commit() throws Exception{
        if (inProgress) {
            dataBase.putAll(transaction);
            inProgress = false;
        }
        else
            throw new Exception("There is no open transaction!");

    }

    @Override
    public void rollback() throws Exception {
        if (inProgress) {
            dataBase.clear();
            inProgress = false;
        }
        else
            throw new Exception("There is no ongoing transaction!");
    }

//    public static void main(String[] args) throws Exception {
//        InMemoryDBClass inmemoryDB = new InMemoryDBClass();
//
//        // should return null, in this instance 0, because A doesn’t exist in the DB yet
//        System.out.println(inmemoryDB.get("A"));
//
//        // should throw an error because a transaction is not in progress
//        inmemoryDB.put("A", 5);
//
//        // starts a new transaction
//        inmemoryDB.begin_transaction();
//
//        // set’s value of A to 5, but it's not committed yet
//        inmemoryDB.put("A", 5);
//
//        // should return null, in this instance 0, because updates to A are not committed yet
//        System.out.println(inmemoryDB.get("A"));
//
//        // update A’s value to 6 within the transaction
//        inmemoryDB.put("A", 6);
//
//        // commits the open transaction
//        inmemoryDB.commit();
//
//        // should return 6, that was the last value of A to be committed
//        System.out.println(inmemoryDB.get("A"));

//        // throws an error, because there is no open transaction
//        inmemoryDB.commit();
//
//        // throws an error because there is no ongoing transaction
//        inmemoryDB.rollback();
//
//        // should return null because B does not exist in the database
//        System.out.println(inmemoryDB.get("B"));
//
//        // starts a new transaction
//        inmemoryDB.begin_transaction();
//
//        // Set key B’s value to 10 within the transaction
//        inmemoryDB.put("B", 10);
//
//        // Rollback the transaction - revert any changes made to B
//        inmemoryDB.rollback();
//
//        // Should return null because changes to B were rolled back
//        System.out.println(inmemoryDB.get("B"));
//    }
}


