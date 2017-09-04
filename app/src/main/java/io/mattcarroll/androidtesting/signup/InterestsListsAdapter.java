package io.mattcarroll.androidtesting.signup;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * List Adapter that offers a list of personal interests that can be selected.
 */
class InterestsListsAdapter extends BaseAdapter {

    private final ArrayList<String> interests = new ArrayList<String>();
    private final ArrayList<Boolean> isChecked = new ArrayList<Boolean>();

    public boolean isChecked(int position) {
        return isChecked.get(position);
    }

    public void setChecked(int position, boolean isChecked) {
        this.isChecked.set(position, isChecked);
        notifyDataSetChanged();
    }

    @NonNull
    public Set<String> getCheckedItems() {
        Set<String> checkedItems = new HashSet<>();

        for (int i = 0; i < interests.size(); ++i) {
            if (this.isChecked.get(i)) {
                checkedItems.add(interests.get(i));
            }
        }

        return checkedItems;
    }

    public void addInterest(String interest) {
        this.interests.add(interest);
        this.isChecked.add(false);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return interests.size();
    }

    @Override
    public String getItem(int position) {
        return interests.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
        }

        ((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position));
        ((CheckedTextView) convertView.findViewById(android.R.id.text1)).setChecked(isChecked(position));

        return convertView;
    }
}
