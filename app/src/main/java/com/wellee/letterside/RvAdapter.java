package com.wellee.letterside;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.RvViewHolder> {

    private List<NameBean> mNameBeans;

    public RvAdapter(List<NameBean> nameBeans) {
        this.mNameBeans = nameBeans;
    }

    @NonNull
    @Override
    public RvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_name, parent, false);
        return new RvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvViewHolder holder, int position) {
        if (mNameBeans == null) return;
        NameBean nameBean = mNameBeans.get(position);
        String name = nameBean.getName();
        String letter = nameBean.getLetter();
        String preLetter = null;
        if (position > 0) {
            preLetter = mNameBeans.get(position - 1).getLetter();
        }
        holder.tvTitle.setText(letter);
        holder.tvName.setText(name);
        if (letter.equals(preLetter)) {
            holder.tvTitle.setVisibility(View.GONE);
        } else {
            holder.tvTitle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mNameBeans == null ? 0 : mNameBeans.size();
    }

    static class RvViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvName;
        public RvViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
