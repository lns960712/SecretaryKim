package com.android.SecretaryKim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.SecretaryKim.DTO.UserDTO;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {
    private List<UserDTO> uDataset;
    private static View.OnClickListener onClickListener;
    private String Uid;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView TextView_title;
        public CheckBox CheckBox_invite;
        public View rootView;
        public MyViewHolder(View v) {
            super(v);
            TextView_title = v.findViewById(R.id.TextView_title);
            CheckBox_invite = v.findViewById(R.id.CheckBox_invite);
            rootView = v;
            v.setClickable(true);
            v.setEnabled(true);//활성화여부
            v.setOnClickListener(onClickListener);
        }
    }

    public UserListAdapter(List<UserDTO> userDataset, Context context, String Uid, View.OnClickListener onClick) {
        uDataset = userDataset;
        onClickListener = onClick;
        this.Uid = Uid;
    }

    @Override
    public UserListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_users, parent, false);//어떤 레이아웃을 쓸것인가?
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UserDTO users = uDataset.get(position);
        holder.TextView_title.setText(users.getNickname());
        holder.CheckBox_invite.setChecked(false);
        holder.rootView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return uDataset==null ? 0: uDataset.size();
    }

    public UserDTO getUserDTO(int position) {
        return uDataset != null ? uDataset.get(position) : null;
    }

    public void addUsers(UserDTO users){//리사이클러뷰 갱신용 이전에 생성된 회의방을 보이게 함
        uDataset.add(users);
        notifyItemInserted(uDataset.size()-1);
    }
    public UserDTO getUserid(int position) {
        return uDataset != null ? uDataset.get(position) : null;
    }
}
