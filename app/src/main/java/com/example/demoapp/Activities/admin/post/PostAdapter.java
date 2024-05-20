package com.example.demoapp.Activities.admin.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demoapp.Models.Dto.Response.PostResponse;
import com.example.demoapp.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<PostResponse> postList;

    public PostAdapter(List<PostResponse> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostResponse post = postList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnailImageView;
        private TextView nameTextView;
        private TextView contentTextView;
        private TextView likeCountTextView;
        private TextView viewCountTextView;
        private TextView tvID, tvTag;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.tv_post_thumbnail);
            nameTextView = itemView.findViewById(R.id.tv_post_name);
            contentTextView = itemView.findViewById(R.id.tv_post_content);
            likeCountTextView = itemView.findViewById(R.id.tv_post_like_count);
            viewCountTextView = itemView.findViewById(R.id.tv_post_view_count);
            tvID = itemView.findViewById(R.id.tv_post_id);
            tvTag = itemView.findViewById(R.id.tv_post_tag);

        }

        public void bind(PostResponse post) {
            tvID.setText(post.getId());
            nameTextView.setText(post.getName());
            contentTextView.setText(post.getContent());
            likeCountTextView.setText("Likes: " + post.getLikeCount());
            viewCountTextView.setText("Views: " + post.getViewCount());
            Picasso.get().load(post.getThumbnail()).into(thumbnailImageView);
            tvTag.setText(post.getTags().size());
        }
    }
}

