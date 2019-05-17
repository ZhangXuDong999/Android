package com.main.zhuce;


import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChooseCityActivity extends ExpandableListActivity{
    private String[] provinces = new String[]{"山西", "山东", "浙江"};
    private String[][] cities = new String[][]{
            { "太原", "大同"},
            { "济南", "青岛"},
            { "杭州", "台州"}
    };
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ExpandableListAdapter adapter = new BaseExpandableListAdapter(){

            public Object getChild(int groupPosition, int childPosition){
                return cities[groupPosition][childPosition];
            }
            public long getChildId(int groupPosition, int childPosition){
                return childPosition;
            }

            public int getChildrenCount(int groupPosition){
                return cities[groupPosition].length;
            }
            private TextView getTextView(){
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 64);
                TextView textView = new TextView(ChooseCityActivity.this);
                textView.setLayoutParams(lp);
                textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                textView.setPadding(36, 0, 0, 0);
                textView.setTextSize(20);
                return textView;
            }

            public View getChildView(int groupPosition, int childPosition,
                                     boolean isLastChild, View convertView, ViewGroup parent){
                TextView textView = getTextView();
                textView.setText(getChild(groupPosition, childPosition).toString());
                return textView;
            }

            public Object getGroup(int groupPosition){
                return provinces[groupPosition];
            }

            public int getGroupCount(){
                return provinces.length;
            }

            public long getGroupId(int groupPosition){
                return groupPosition;
            }

            public View getGroupView(int groupPosition, boolean isExpanded,
                                     View convertView, ViewGroup parent)	{
                LinearLayout ll = new LinearLayout(ChooseCityActivity.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                ImageView logo = new ImageView(ChooseCityActivity.this);
                ll.addView(logo);
                TextView textView = getTextView();
                textView.setText(getGroup(groupPosition).toString());
                ll.addView(textView);
                return ll;
            }
            public boolean isChildSelectable(int groupPosition, int childPosition){
                return true;
            }
            public boolean hasStableIds(){
                return true;
            }
        };

        setListAdapter(adapter);//设置适配器

        getExpandableListView().setOnChildClickListener(
                new OnChildClickListener(){
                    public boolean onChildClick(ExpandableListView parent, View source,
                                                int groupPosition, int childPosition, long id){

                        Intent intent = getIntent();
                        Bundle data = new Bundle();
                        data.putString("city" ,cities[groupPosition][childPosition]);
                        intent.putExtras(data);
                        ChooseCityActivity.this.setResult(0 , intent);
                        ChooseCityActivity.this.finish();
                        return false;
                    }
                });
    }
}
