package nje.hu.closetmate;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClothingAdapter extends RecyclerView.Adapter<ClothingAdapter.ClothingViewHolder> {

    private final ArrayList<ClothingItem> clothingList;

    public ClothingAdapter(ArrayList<ClothingItem> clothingList) {
        this.clothingList = clothingList;
    }

    @NonNull
    @Override
    public ClothingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_clothing, parent, false);
        return new ClothingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClothingViewHolder holder, int position) {
        ClothingItem item = clothingList.get(position);

        holder.txtItemName.setText(item.getName());
        holder.txtItemCategory.setText(item.getCategory() + " • " + item.getOccasion());
        holder.txtItemDetails.setText(item.getColor() + " • " + item.getSeason() + " • " + item.getStyle());
        holder.txtWearCount.setText("Worn " + item.getWearCount() + " times");

        if (item.getImageUri() != null && !item.getImageUri().isEmpty()) {
            holder.imgItem.setImageURI(Uri.parse(item.getImageUri()));
        } else {
            holder.imgItem.setImageResource(android.R.drawable.ic_menu_gallery);
        }
    }

    @Override
    public int getItemCount() {
        return clothingList.size();
    }

    public static class ClothingViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView txtItemName, txtItemCategory, txtItemDetails, txtWearCount;

        public ClothingViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItem = itemView.findViewById(R.id.imgItem);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemCategory = itemView.findViewById(R.id.txtItemCategory);
            txtItemDetails = itemView.findViewById(R.id.txtItemDetails);
            txtWearCount = itemView.findViewById(R.id.txtWearCount);
        }
    }
}