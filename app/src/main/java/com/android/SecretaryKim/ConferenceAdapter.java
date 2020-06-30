package com.android.SecretaryKim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.SecretaryKim.DTO.ConferenceDTO;

import java.util.List;

public class ConferenceAdapter extends RecyclerView.Adapter<ConferenceAdapter.MyViewHolder> {
    private List<ConferenceDTO> conDataset;
    private static View.OnClickListener onClickListener;
    private String Uid;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView TextView_title;
        public View rootView;//목록을 클릭할때 몇번째 목록을 클릭했는지 알기위해 선언
        public MyViewHolder(View v) {
            super(v);
            TextView_title = v.findViewById(R.id.TextView_title);
            rootView = v;//findViewById를 안해줘도 됨
            //onBindViewHolder에 리스너를 안달아주는 이유는 퍼포먼스?를 위해서 그런거임
            //onBindViewHolder에 리스너를 달아주면 몇번째 항목을 클릭했는지 알기 쉽게됨
            v.setClickable(true);//클릭을 할 수 있게함
            v.setEnabled(true);//활성화여부
            v.setOnClickListener(onClickListener);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ConferenceAdapter(List<ConferenceDTO> conferenceDataset, Context context, String Uid, View.OnClickListener onClick) {
        conDataset = conferenceDataset;
        onClickListener = onClick;
        this.Uid = Uid;
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
        holder.rootView.setTag(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return conDataset==null ? 0: conDataset.size();
    }

    //어댑터의 데이터를 다른 클래스에서 사용할 수 있도록 한다.
    public ConferenceDTO getConference(int position){
        return conDataset != null ? conDataset.get(position) : null;
    }

    public void addConference(ConferenceDTO conference){//리사이클러뷰 갱신용 이전에 생성된 회의방을 보이게 함
        conDataset.add(conference);
        notifyItemInserted(conDataset.size()-1);
    }
}
