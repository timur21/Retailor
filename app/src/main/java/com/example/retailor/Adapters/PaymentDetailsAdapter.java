package com.example.retailor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.retailor.Models.AgreementDetails;
import com.example.retailor.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PaymentDetailsAdapter extends RecyclerView.Adapter<PaymentDetailsAdapter.ViewHolder> {

    List<AgreementDetails> paymentDetails;
    Context context;

    public PaymentDetailsAdapter(Context context, List<AgreementDetails> paymentDetails){
        this.paymentDetails = paymentDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.paymentdetails, parent, false);
        return new PaymentDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.date.setText(paymentDetails.get(position + 1).getDate());
        holder.toBePaid.setText(paymentDetails.get(position + 1).getToBePaidAmount());
        holder.paidAmount.setText(paymentDetails.get(position + 1).getPaidAmount());
        holder.leftAmount.setText(paymentDetails.get(position + 1).getLeftAmount());
    }

    @Override
    public int getItemCount() {
        return (paymentDetails.size() - 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date, toBePaid, paidAmount, leftAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            toBePaid = itemView.findViewById(R.id.toBePaid);
            paidAmount = itemView.findViewById(R.id.paidAmount);
            leftAmount = itemView.findViewById(R.id.leftAmount);
        }
    }
}
