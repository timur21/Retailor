package com.example.retailor.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.retailor.Fragments.PersonalFragment;
import com.example.retailor.Models.AgreementDetails;
import com.example.retailor.Models.Table;
import com.example.retailor.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AgreementDetailsAdapter extends RecyclerView.Adapter<AgreementDetailsAdapter.ViewHolder> {


    List<List<AgreementDetails>> agreementDetails;
    List<AgreementDetails> arrayList;
    Context context;


    public AgreementDetailsAdapter(Context context, List<List<AgreementDetails>> agreementDetails){
        this.agreementDetails = agreementDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.agreement_details, parent, false);
        return new AgreementDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        boolean isExpaned = agreementDetails.get(position).get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpaned ? View.VISIBLE : View.GONE);

        arrayList = agreementDetails.get(position);

        holder.agreementName.setText(arrayList.get(0).getAgreement());

        PaymentDetailsAdapter paymentDetailsAdapter = new PaymentDetailsAdapter(context, arrayList);

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(paymentDetailsAdapter);
    }

    @Override
    public int getItemCount() {
        return agreementDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView agreementName;
        RecyclerView recyclerView;
        ConstraintLayout expandableLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            agreementName = itemView.findViewById(R.id.agreementName);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            recyclerView = itemView.findViewById(R.id.recyclerView2);

            agreementName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AgreementDetails agreementDetails2 = agreementDetails.get(getAdapterPosition()).get(getAdapterPosition());
                    agreementDetails2.setExpanded(!agreementDetails2.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
