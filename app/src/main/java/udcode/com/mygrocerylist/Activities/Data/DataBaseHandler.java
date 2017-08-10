package udcode.com.mygrocerylist.Activities.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import udcode.com.mygrocerylist.Activities.Util.constants;
import udcode.com.mygrocerylist.Activities.model.Grocery;

/**
 * Created by yudi on 8/9/2017.
 */

public class DataBaseHandler extends SQLiteOpenHelper {
    Context ctx;
    public DataBaseHandler(Context context) {
        super(context, constants.DB_NAME, null, constants.DB_VERSION);
        ctx =context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            String CREATE_DATABASE ="CREATE TABLE "+ constants.TABLE_NAME +"("
                    +constants.KEY_ID+" INTEGER PRIMARY KEY, "+ constants.KEY_GROCERY_ITEM + " TEXT,"
                    +constants.KEY_QTY_NUMBER + " TEXT, "+constants.KEY_DATE + " LONG);";
        db.execSQL(CREATE_DATABASE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+constants.TABLE_NAME);
        onCreate(db);
    }
    /*
    CRUD-----create , read , update , delete
     */
    public void addGrocery(Grocery g){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(constants.KEY_GROCERY_ITEM, g.getGrocery_name());
        values.put(constants.KEY_QTY_NUMBER,g.getQuantity());
        values.put(constants.KEY_DATE,java.lang.System.currentTimeMillis());

        db.insert(constants.TABLE_NAME,null,values);
        Log.d("inseting","inserted to DB");
    }
    public Grocery readGrocery(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(constants.TABLE_NAME,
                new String[]{constants.KEY_ID,constants.KEY_GROCERY_ITEM,constants.KEY_QTY_NUMBER,constants.KEY_DATE},
                constants.KEY_ID +"=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();
        Grocery grocery=new Grocery();
        //grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(constants.KEY_ID))));
        grocery.setGrocery_name(cursor.getString(cursor.getColumnIndex(constants.KEY_GROCERY_ITEM)));
        grocery.setQuantity(cursor.getString(cursor.getColumnIndex(constants.KEY_QTY_NUMBER)));

        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(constants.KEY_DATE)))
                .getTime());

        grocery.setDateAdded(formatedDate);
        return grocery;
    }
    public List<Grocery> getAll(){
        List<Grocery> groceryList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(constants.TABLE_NAME,
                new String[]{constants.KEY_ID,constants.KEY_GROCERY_ITEM,constants.KEY_QTY_NUMBER,constants.KEY_DATE},
                null,null,null,null,constants.KEY_DATE+ " DESC");
        if(cursor.moveToFirst()){
            do{
                Grocery grocery=new Grocery();
                grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(constants.KEY_ID))));
                grocery.setGrocery_name(cursor.getString(cursor.getColumnIndex(constants.KEY_GROCERY_ITEM)));
                grocery.setQuantity(cursor.getString(cursor.getColumnIndex(constants.KEY_QTY_NUMBER)));

                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(constants.KEY_DATE)))
                        .getTime());

                grocery.setDateAdded(formatedDate);

                groceryList.add(grocery);
            }while (cursor.moveToNext());
        }

        return groceryList;
    }
    public int updateGrocery(Grocery grocery){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(constants.KEY_GROCERY_ITEM,grocery.getGrocery_name());
        values.put(constants.KEY_QTY_NUMBER,grocery.getQuantity());
        values.put(constants.KEY_DATE,java.lang.System.currentTimeMillis());
        return db.update(constants.TABLE_NAME,values,constants.KEY_ID+ "=?",new String[]{String.valueOf(grocery.getId())});
    }
    public void deleteGrocery(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(constants.TABLE_NAME,constants.KEY_ID+ "=?",new String[]{String.valueOf(id)});
        db.close();
    }
    public int getGrocerysCount(){
        String getCount = "SELECT * FROM "+ constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getCount,null);
        return cursor.getCount();
    }
}
