package com.example.skladiteapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemModelAdapter extends RecyclerView.Adapter<ItemModelAdapter.ItemModelVH> {

    List<ItemModel> itemModels ;

    public ItemModelAdapter(List<ItemModel> itemModels) {
        this.itemModels = itemModels;
    }

    @NonNull
    @Override
    public ItemModelAdapter.ItemModelVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false) ;

        return new ItemModelAdapter.ItemModelVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemModelAdapter.ItemModelVH holder, int position) {

        ItemModel itemModel = itemModels.get(position) ;

        holder.typeTxt.setText(itemModel.getType());
        holder.modelTxt.setText(itemModel.getModel());
        holder.locamTxt.setText(itemModel.getLocationAmount());

        boolean isExpandable = itemModels.get(position).isExpandable();
        holder.expRelative.setVisibility(isExpandable ? View.VISIBLE : View.GONE);


    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    public class ItemModelVH extends RecyclerView.ViewHolder {

        TextView typeTxt, modelTxt, locamTxt ;
        LinearLayout inCardLinear ;
        RelativeLayout expRelative ;

        public ItemModelVH(@NonNull View itemView) {
            super(itemView);

            typeTxt = itemView.findViewById(R.id.card_item_type) ;
            modelTxt = itemView.findViewById(R.id.card_model) ;
            locamTxt = itemView.findViewById(R.id.card_loc_am) ;

            inCardLinear = itemView.findViewById(R.id.linear_in_card) ;
            expRelative = itemView.findViewById(R.id.expadnable_layout) ;

            inCardLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ItemModel itemModel = itemModels.get(getAbsoluteAdapterPosition()) ;
                    itemModel.setExpandable(!itemModel.isExpandable());
                    notifyItemChanged(getAbsoluteAdapterPosition());
                }
            });
        }
    }
}
