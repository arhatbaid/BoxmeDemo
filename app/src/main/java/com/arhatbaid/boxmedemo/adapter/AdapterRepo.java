package com.arhatbaid.boxmedemo.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arhatbaid.boxmedemo.R;
import com.arhatbaid.boxmedemo.utils.AppHelper;
import com.arhatbaid.boxmedemo.webapi.model.RepoData;

import java.util.ArrayList;

import static com.arhatbaid.boxmedemo.R.id.viewDummy;

public class AdapterRepo extends ArrayAdapter<RepoData> {

    private ArrayList<RepoData> arrayRepo = new ArrayList<>();
    private Context context = null;
    private LayoutInflater inflater = null;

    public AdapterRepo(Context context, int resource, ArrayList<RepoData> arrayRepo) {
        super(context, resource, arrayRepo);
        this.arrayRepo = arrayRepo;
        this.context = context;

        inflater = ((AppCompatActivity) context).getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {

            row = inflater.inflate(R.layout.cell_repo_list, parent, false);

            holder = new ViewHolder();
            holder.viewDummy = (View) row.findViewById(viewDummy);
            holder.lblRepoTitle = (TextView) row.findViewById(R.id.lblRepoTitle);
            holder.lblRepoDesc = (TextView) row.findViewById(R.id.lblRepoDesc);
            holder.llParent = (LinearLayout) row.findViewById(R.id.llParent);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        RepoData repoData = arrayRepo.get(position);
        holder.lblRepoTitle.setText(repoData.getName());
        holder.lblRepoDesc.setText(repoData.getDescription());
        holder.viewDummy.setBackground(context.getResources().getDrawable(AppHelper.getColorDrawable(position % 5)));

     /*   if (position % 2 == 0) {
            YoYo.with(Techniques.SlideInLeft).delay(50).duration(200).playOn(holder.llParent);
        } else {
            YoYo.with(Techniques.SlideInRight).delay(50).duration(200).playOn(holder.llParent);
        }*/
        return row;
    }


    private class ViewHolder {
        View viewDummy;
        TextView lblRepoTitle;
        TextView lblRepoDesc;
        LinearLayout llParent;
    }

}
