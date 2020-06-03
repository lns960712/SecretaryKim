package com.android.SecretaryKim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ConferenceAdapter extends RecyclerView.Adapter<ConferenceAdapter.MyViewHolder> {
    private List<ConferenceDTO> conDataset;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView TextView_title;
        public View rootView;
        public MyViewHolder(View v) {
            super(v);
            TextView_title = v.findViewById(R.id.TextView_title);
            rootView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ConferenceAdapter(List<ConferenceDTO> conferenceDataset, Context context) {
        conDataset = conferenceDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ConferenceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_conference, parent, false);//어떤 레이아웃을 쓸것인가?
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {//데이터를 세팅한다.
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ConferenceDTO conference = conDataset.get(position);
        holder.TextView_title.setText(conference.getTitle());
        //상대방이 보낸 메세지는 왼쪽, 내가 보낸 메세지는 오른쪽에 정렬
            holder.TextView_title.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return conDataset==null ? 0: conDataset.size();
    }

    public ConferenceDTO getConference(int position){
        return conDataset != null ? conDataset.get(position) : null;
    }

    public void addConference(ConferenceDTO conference){//리사이클러뷰 갱신용 이전문자를 보이게 함
        conDataset.add(conference);
        notifyItemInserted(conDataset.size()-1);
    }
}
