package com.example.poke_asimplemessenger.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poke_asimplemessenger.databinding.ItemContainerRecentConversationsBinding;
import com.example.poke_asimplemessenger.models.ChatMessage;
import com.example.poke_asimplemessenger.models.ConversationListener;
import com.example.poke_asimplemessenger.models.User;

import java.util.List;

public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConversionViewHolder>{

    private final List<ChatMessage> chatMessages;
    private final ConversationListener conversationListener;

    public RecentConversationsAdapter(List<ChatMessage> chatMessages, ConversationListener conversationListener ) {
        this.chatMessages = chatMessages;
        this.conversationListener = conversationListener;
    }

    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversionViewHolder(
                ItemContainerRecentConversationsBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecentConversationsAdapter.ConversionViewHolder holder, int position) {
        holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }


    class ConversionViewHolder extends RecyclerView.ViewHolder{
        private ItemContainerRecentConversationsBinding binding;

        ConversionViewHolder( ItemContainerRecentConversationsBinding binding ){
            super(binding.getRoot());
            this.binding = binding;
        }

        void setData(ChatMessage chatMessage){
            binding.imageProfile.setImageBitmap(getConversionImage(chatMessage.conversationImage));
            binding.textName.setText(chatMessage.conversationName);
            binding.textRecentMessage.setText(chatMessage.message);
            binding.getRoot().setOnClickListener(v -> {
                User user = new User();
                user.id = chatMessage.conversationId;
                user.name = chatMessage.conversationName;
                user.image = chatMessage.conversationImage;
                conversationListener.onConversationClicked(user);
            });
        }
    }

    private Bitmap getConversionImage(String encodedImage){
        System.out.println(encodedImage);
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
