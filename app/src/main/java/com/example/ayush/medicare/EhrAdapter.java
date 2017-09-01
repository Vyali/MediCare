package com.example.ayush.medicare;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vyali on 31/8/17.
 */

public class EhrAdapter extends RecyclerView.Adapter<EhrAdapter.EhrViewHodler> {
    Context c;
    List<EhrDetails> ehlist;

    public EhrAdapter(Context c, List<EhrDetails> ehlist) {
        this.c = c;
        this.ehlist = ehlist;
    }

    @Override
    public EhrViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ehr_card, parent, false);
        return new EhrViewHodler(view);

    }

    @Override
    public void onBindViewHolder(EhrViewHodler holder, int position) {
        EhrDetails ehrDetails = ehlist.get(position);
        holder.key.setText(ehrDetails.mgetKey());
        holder.val.setText(ehrDetails.mgetValue());
    }

    @Override
    public int getItemCount() {
        return ehlist.size();
    }

    class EhrViewHodler extends RecyclerView.ViewHolder {
        TextView val, key;

        public EhrViewHodler(View itemView) {
            super(itemView);
            key = (TextView) itemView.findViewById(R.id.key_value);
            val = (TextView) itemView.findViewById(R.id.value);
        }
    }


}

