package com.example.tartanhacks2019;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    ArrayList<Person> mPeople;
    private Context context;
    private DetailsDialogFragment detailsDialogFragment;
    SharedViewModel model;

    public GridAdapter(ArrayList<Person> people, SharedViewModel model2) {
        mPeople = people;
        model = model2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View gridView = inflater.inflate(R.layout.item_user, parent, false);
        return new ViewHolder(gridView);
    }

    @Override
    public void onBindViewHolder(@NonNull GridAdapter.ViewHolder viewHolder, int i) {
        Person person = mPeople.get(i);
        String name = person.getName();

        if (name != null) {
            viewHolder.tvName.setText(name);
        }
        if(person.getProfileImage() != null) {
            Glide.with(context).load(person.getProfileImage().getUrl()).into(viewHolder.ivProfile);
        }
    }

    public void clearPeople() {
        mPeople.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPeople.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvName;
        public ImageView ivProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = (ImageView) itemView.findViewById(R.id.ivProfile);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d("GridAdapter", Integer.toString(position));
            if (position != RecyclerView.NO_POSITION) {
                model.setGalleryPerson(mPeople.get(position));
                detailsDialogFragment = DetailsDialogFragment.newInstance();
                detailsDialogFragment.show(model.getFragmentManager(), "Details Dialog");
                //FragmentTransaction fragmentTransaction = model.getFragmentManager().beginTransaction();
                //fragmentTransaction.replace(R.id.flContainer, detailsDialogFragment).commit();
            }
        }
    }
}
