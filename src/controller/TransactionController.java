package controller;

import model.TransactionDetail;
import model.TransactionHeader;
import model.User;

import java.util.ArrayList;

public class TransactionController {
    public static ArrayList<TransactionHeader> getAllTHCurr(){
        TransactionHeader.refresh();
        ArrayList<TransactionHeader> th = new ArrayList<>();
        if (TransactionHeader.currTH != null) {
            for (TransactionHeader t: TransactionHeader.currTH) {
                if (t.getUser().getUserId() == User.curr.getUserId()) th.add(t);
            }
        }
        return th;
    }

    public static ArrayList<TransactionDetail> getAllTDCurr(int id){
        ArrayList<TransactionDetail> td = new ArrayList<>();
        ArrayList<TransactionDetail> all = TransactionDetail.selectAllbyID(id);
        if (all != null ) {
            for (TransactionDetail d: all) {
                if (d.getId() == id) td.add(d);
            }
        }
        return td;
    }
}
