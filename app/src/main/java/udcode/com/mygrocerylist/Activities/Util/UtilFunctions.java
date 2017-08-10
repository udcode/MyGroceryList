package udcode.com.mygrocerylist.Activities.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import udcode.com.mygrocerylist.Activities.Data.DataBaseHandler;
import udcode.com.mygrocerylist.Activities.model.Grocery;
import udcode.com.mygrocerylist.R;

/**
 * Created by yudi on 8/10/2017.
 */

public class UtilFunctions {
    private AlertDialog.Builder dialogbuilder;
    private DataBaseHandler db;
    private AlertDialog dialog;
    private Button saveBtn;
    private EditText grocery,qty;
    private Context context;
    Activity act;
    public UtilFunctions(DataBaseHandler dataBaseHandler,Context ctx){
        context =ctx;
        db = dataBaseHandler;
    }
    public UtilFunctions(Activity activity){
        act = activity;
        db = new DataBaseHandler(act);
    }
    public  void createPopUp(){
        dialogbuilder = new AlertDialog.Builder(act);
        View view = act.getLayoutInflater().inflate(R.layout.alert_dialog,null);
        grocery = (EditText) view.findViewById(R.id.enter_nameID);
        qty = (EditText) view.findViewById(R.id.enter_qtyID);
        saveBtn = (Button) view.findViewById(R.id.save_btnID);
        dialogbuilder.setView(view);
        dialog = dialogbuilder.create();
        dialog.show();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!grocery.getText().toString().isEmpty()&&!qty.getText().toString().isEmpty())
                    saveGroceryToDB(v);
                restart();
            }
        });


    }
    private void saveGroceryToDB(View v){
        Grocery grocery1 =new Grocery();
        String newgroceryname = grocery.getText().toString();
        String newgroceryQty = qty.getText().toString();

        grocery1.setGrocery_name(newgroceryname);
        grocery1.setQuantity(newgroceryQty);
        db.addGrocery(grocery1);
        Snackbar.make(v,"add to list",Snackbar.LENGTH_SHORT).show();
        //Toast.makeText(act,String.valueOf(db.getGrocerysCount()),Toast.LENGTH_LONG).show();
    }
    public void updateGrocery(final Grocery toUpdate){
         LayoutInflater inflater;
        dialogbuilder = new AlertDialog.Builder(context);
        inflater= LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.alert_dialog,null);
        grocery = (EditText) view.findViewById(R.id.enter_nameID);
        qty = (EditText) view.findViewById(R.id.enter_qtyID);
        saveBtn = (Button) view.findViewById(R.id.save_btnID);
        dialogbuilder.setView(view);
        dialog=dialogbuilder.create();
        dialog.show();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!grocery.getText().toString().isEmpty()&&!qty.getText().toString().isEmpty()){
                    Grocery grocery1 =new Grocery();
                String newgroceryname = grocery.getText().toString();
                String newgroceryQty = qty.getText().toString();
                grocery1.setGrocery_name(newgroceryname);
                grocery1.setQuantity(newgroceryQty);
                grocery1.setId(toUpdate.getId());
                db.updateGrocery(grocery1);}
                dialog.dismiss();
            }
        });

    }
    public void restart(){
        Intent i = act.getIntent();
        act.finish();
        act.startActivity(i);
    }

}
