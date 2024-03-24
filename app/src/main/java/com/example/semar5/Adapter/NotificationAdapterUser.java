package com.example.semar5.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.semar5.HistoryActivity;
import com.example.semar5.ModelResponse.NotificationModel;
import com.example.semar5.R;

import java.util.List;

public class NotificationAdapterUser extends RecyclerView.Adapter<NotificationAdapterUser.NotificationViewHolder> {
    private List<NotificationModel> notifications;
    private OnItemClickListener listener;

    public NotificationAdapterUser(List<NotificationModel> notifications) {
        this.notifications = notifications;
    }

    public void addNotification(NotificationModel notification) {
        notifications.add(notification);
        notifyDataSetChanged();
    }

    public void updateNotifications(List<NotificationModel> newNotifications) {
        notifications.clear();
        notifications.addAll(newNotifications);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(NotificationModel notification);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tampilan_notifikasi, parent, false);
        final NotificationViewHolder viewHolder = new NotificationViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationModel notification = notifications.get(position);

        holder.titleTextView.setText(notification.getTitle());
        holder.messageTextView.setText(notification.getMessage());
        holder.timeTextView.setText(notification.getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(notification);

                    Intent intent = new Intent(view.getContext(), HistoryActivity.class);
                    //intent.putExtra("pemesanan_id", notification.getTitle());
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView messageTextView;
        TextView timeTextView;

        NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.notification_title);
            messageTextView = itemView.findViewById(R.id.notification_message);
            timeTextView = itemView.findViewById(R.id.notification_time);
        }
    }
}