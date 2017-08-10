package udcode.com.mygrocerylist.Activities.Ui;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import udcode.com.mygrocerylist.Activities.Data.DataBaseHandler;
import udcode.com.mygrocerylist.Activities.ListActivity;
import udcode.com.mygrocerylist.Activities.Util.UtilFunctions;
import udcode.com.mygrocerylist.Activities.model.Grocery;
import udcode.com.mygrocerylist.R;

/**
 * Created by yudi on 8/9/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    DataBaseHandler db;
    private AlertDialog.Builder dialogbuilder;
    private LayoutInflater inflater;
    private AlertDialog dialog;
    public Context context;
    private List<Grocery>groceryItem;
    public RecyclerViewAdapter(Context context){

    }
    public RecyclerViewAdapter(Context context, List<Grocery> groceryItem) {
        this.context = context;
        this.groceryItem = groceryItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            Grocery grocery = groceryItem.get(position);
        holder.groceryName.setText(grocery.getGrocery_name());
        holder.groceryQTY.setText(grocery.getQuantity());
        holder.dateAdd.setText(grocery.getDateAdded());
    }

    @Override
    public int getItemCount() {
        return groceryItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView groceryName,groceryQTY,dateAdd;
        private Button delete,edit;
        private Button yesBTN,noBTN;
        UtilFunctions util;
        public ViewHolder(View itemView,Context ctx ) {
            super(itemView);
                context =ctx;
                groceryName = (TextView) itemView.findViewById(R.id.grocery_nameID);
                groceryQTY= (TextView) itemView.findViewById(R.id.grocery_qtyID);
                dateAdd=(TextView) itemView.findViewById(R.id.dateID);
                edit =(Button) itemView.findViewById(R.id.edit_buttonID);
                delete=(Button) itemView.findViewById(R.id.delete_buttonID);
                db = new DataBaseHandler(context);
                util=new UtilFunctions(db,context);
                edit.setOnClickListener(this);
                delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //db =new DataBaseHandler(context);
                switch (v.getId()){
                    case R.id.edit_buttonID:
                        Grocery grocery= groceryItem.get(getAdapterPosition());
                       util.updateGrocery(grocery);
                        notifyItemChanged(getAdapterPosition());
                                break;
                    case R.id.delete_buttonID:
                        Grocery grocery2= groceryItem.get(getAdapterPosition());
                        int id=grocery2.getId();
                        createDeleteDialog(id);
                }
        }
        public void createDeleteDialog(final int id){


            dialogbuilder = new AlertDialog.Builder(context);
            inflater= LayoutInflater.from(context);
            View view =inflater.inflate(R.layout.confirmation_dialog,null);
            dialogbuilder.setView(view);
            dialog =dialogbuilder.create();
            dialog.show();
            yesBTN = (Button)view.findViewById(R.id.yesID);
            noBTN = (Button)view.findViewById(R.id.noID);
            yesBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.deleteGrocery(id);
                    groceryItem.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    dialog.dismiss();
                }
            });
            noBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        }
    }
}
