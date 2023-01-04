package com.example.proyectodap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.NumberViewHolder> {

    final private ListItemClickListener mOnClickListener;

    private ArrayList<Champion> mChampionList;

    public interface ListItemClickListener {
        void onListItemClick(Champion clickedItemIndex);
    }

    public ListAdapter(ArrayList<Champion> championList, ListItemClickListener listener) {
        mChampionList = championList;
        mOnClickListener = listener;
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        holder.bind(mChampionList.get(position));
    }

    @Override
    public int getItemCount() {
        return mChampionList.size();
    }

    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView listItemName;
        TextView listItemTitle;

        public NumberViewHolder(View itemView) {
            super(itemView);

            listItemName = (TextView) itemView.findViewById(R.id.tv_item_name);
            listItemTitle = (TextView) itemView.findViewById(R.id.tv_item_title);

            itemView.setOnClickListener(this);
        }

        void bind(Champion champ) {

            listItemName.setText(String.valueOf(champ.getName()));
            listItemTitle.setText(String.valueOf(champ.getTitle()));

        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(mChampionList.get(clickedPosition));
        }
    }
}
