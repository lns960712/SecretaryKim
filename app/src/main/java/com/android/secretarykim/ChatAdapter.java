package com.android.secretarykim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatDTO> cDataset;
    private String myNickname;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView TextView_nickname;
        public TextView TextView_message;
        public View rootView;
        public MyViewHolder(View v) {
            super(v);
            TextView_nickname = v.findViewById(R.id.TextView_nickname);
            TextView_message = v.findViewById(R.id.TextView_message);
            rootView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatAdapter(List<ChatDTO> chatDataset, Context context, String myNickname) {
        cDataset = chatDataset;
        this.myNickname = myNickname;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat, parent, false);//어떤 레이아웃을 쓸것인가?
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {//데이터를 세팅한다.
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ChatDTO chat= cDataset.get(position);
        holder.TextView_nickname.setText(chat.getNickname());
        holder.TextView_message.setText(chat.getMessage());
        //상대방이 보낸 메세지는 왼쪽, 내가 보낸 메세지는 오른쪽에 정렬
        if(chat.getNickname().equals(this.myNickname)){
            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.TextView_message.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        }else{
            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.TextView_message.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cDataset==null ? 0: cDataset.size();
    }

    public ChatDTO getChat(int position){
        return cDataset != null ? cDataset.get(position) : null;
    }

    public void addChat(ChatDTO chat){//리사이클러뷰 갱신용 이전문자를 보이게 함
        cDataset.add(chat);
        notifyItemInserted(cDataset.size()-1);
    }
}
