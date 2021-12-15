package com.example.fastapi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastapi.R;
import com.example.fastapi.classes.PostsConstants;

import java.text.MessageFormat;
import java.util.ArrayList;

public class PostCardAdapter extends RecyclerView.Adapter<PostCardAdapter.PostCardAdapterViewHolder> {

    ArrayList<PostsConstants> arrayList;

    public PostCardAdapter(ArrayList<PostsConstants> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public PostCardAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostCardAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.post_card, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull PostCardAdapterViewHolder holder, int position) {

        PostsConstants currentItem = arrayList.get(position);
        holder.card_main.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        holder.card_main.startAnimation(holder.animation);
        holder.text_title.setText(currentItem.getTitle());
        holder.text_content.setText(currentItem.getContent());
        holder.text_published.setText(MessageFormat.format("published: {0}", String.valueOf(currentItem.getPublished())));
        holder.text_created_at.setText(currentItem.getCreated_at());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class PostCardAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView text_title, text_content, text_published, text_created_at;
        private CardView card_main;
        private Animation animation;

        public PostCardAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            text_title = itemView.findViewById(R.id.text_title);
            text_content = itemView.findViewById(R.id.text_content);
            text_published = itemView.findViewById(R.id.text_published);
            text_created_at = itemView.findViewById(R.id.text_created_at);
            card_main = itemView.findViewById(R.id.card_main);

            animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.pop_animation);
            animation.setInterpolator(new OvershootInterpolator());

        }
    }
}
