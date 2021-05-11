package com.bugapocalypse.srilankarailwaydigital.Adapters;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bugapocalypse.srilankarailwaydigital.CheckedTickets;
import com.bugapocalypse.srilankarailwaydigital.R;
import com.bugapocalypse.srilankarailwaydigital.data.Cheking;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CheckedAdapter extends RecyclerView.Adapter<CheckedAdapter.CheckedAdapterHolder> implements Filterable {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    public static final String POSITION = "position";
    private Context context;
    private ArrayList<Cheking> chekings ;
    private ArrayList<Cheking> searchChecking;
    private CheckedTickets mOnTicketListener;

    public CheckedAdapter(Context context, ArrayList<Cheking> chekings, CheckedTickets mOnTicketListener) {
        this.context = context;
        this.chekings = chekings;
        this.mOnTicketListener = mOnTicketListener;
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        this.searchChecking = chekings ;
    }
    @NonNull
    @Override
    public CheckedAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checked_cart, parent, false);
        CheckedAdapterHolder viewHolder = new CheckedAdapterHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull CheckedAdapter.CheckedAdapterHolder holder,int position) {
        Cheking cheking = chekings.get(position);
        holder.dateAndTime.setText(cheking.getTime());
        holder.train.setText(cheking.getTrain());
        holder.ticketId.setText(cheking.getTicketIdcheak());
        //delete from database
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(position);
            }
        });

    }

    private void deleteData(int position) {
        final AlertDialog dialog = new AlertDialog.Builder(mOnTicketListener.getActivity())
                .setTitle("Delete")
                .setMessage("Delete Checked Tickets")
                .setPositiveButton("Delete", null)
                .setNegativeButton("Cancel", null)
                .show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.getInstance().collection("checking").document(chekings.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    chekings.remove(chekings.get(position));
                                    notifyDataSetChanged();
                                }
                                else{

                                }
                            }
                        });
                Toast.makeText(mOnTicketListener.getActivity(), "Deleting", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return chekings.size();
    }

    public class CheckedAdapterHolder extends RecyclerView.ViewHolder {
        TextView dateAndTime,train,ticketId;
        ImageView delete;
        public CheckedAdapterHolder(View view) {
            super(view);
            Log.d("CHEK","Adapter Line 58");
            dateAndTime = itemView.findViewById(R.id.dateAndTimeCheck);
            train = itemView.findViewById(R.id.trainNameCheck);
            ticketId = itemView.findViewById(R.id.ticketIdCheck );
            delete = itemView.findViewById(R.id.btnDeleteItem );

        }
    }
/*    public void deleteItem(int position){
        firebaseFirestore.collection("checking").document(chekings.get(position).getTicketIdcheak())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                //Get any error
                //Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG);
            }
        });
    }*/

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private  Filter exampleFilter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Cheking> chekingList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                    chekingList.addAll(searchChecking);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Cheking cheking : searchChecking){
                    if(cheking.getTrain().toLowerCase().contains(filterPattern)){
                        chekingList.add(cheking);
                    }

                }
            }
            FilterResults results = new FilterResults();
            results.values = chekingList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            chekings.clear();
            chekings.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
