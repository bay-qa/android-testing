package io.mattcarroll.androidtesting.signup;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import io.mattcarroll.androidtesting.Bus;
import io.mattcarroll.androidtesting.ListensForBackgroundWork;
import io.mattcarroll.androidtesting.R;

/**
 * Collect info on user's interests.
 */
public class CollectInterestsFragment extends Fragment {

    private static String[] interestsToAdd = new String[] {
            "Snowboarding",
            "Chess",
            "Programming",
            "Graphic Design",
            "Football",
            "Basketball",
            "Soccer",
            "Espresso Testing",
            "Alcohol",
            "Coffee",
            "Long walks on the beach",
            "Astronomy"
    };
    private int lastAddedItem = 0;

    @NonNull
    public static CollectInterestsFragment newInstance() {
        return new CollectInterestsFragment();
    }

    private InterestsListsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collect_interests, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new InterestsListsAdapter();
        ListView listView = (ListView) view.findViewById(R.id.listview_interests);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setChecked(position, !adapter.isChecked(position));
            }
        });

        view.findViewById(R.id.button_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNextSelected();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        // Update ActionBar title for this screen.
        getActivity().setTitle("Sign Up - Interests");

        // Notify activity that background work started/finished
        FragmentActivity activity = getActivity();
        final ListensForBackgroundWork workListener;
        final String workTag = this.getClass().getCanonicalName();
        if (activity instanceof ListensForBackgroundWork) {
            workListener = (ListensForBackgroundWork)activity;
            workListener.onStartWork(workTag);
        } else {
            workListener = null;
        }

        final Timer timer = new Timer();
        final Handler uiHandler = new Handler();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int i = adapter.getCount();
                if (i < interestsToAdd.length) {
                    final String interestToAdd = interestsToAdd[i];
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.addInterest(interestToAdd);
                        }
                    });
                } else if (i == interestsToAdd.length) {
                    timer.cancel();
                    if (workListener != null) {
                        workListener.onFinishWork(workTag);
                        workListener.onFinishWork(workTag); // will not fail
                    }
                }
            };
        }, 0L, 1000);
    }

    private void onNextSelected() {
        Set<String> checkedInterests = adapter.getCheckedItems();
        if (checkedInterests.size() > 0) {
            Bus.getBus().post(new SelectedInterestsCompleteEvent(adapter.getCheckedItems()));
        } else {
            showAlertToSelectInterests();
        }
    }

    private void showAlertToSelectInterests() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_select_interests_title)
                .setMessage(R.string.dialog_select_interests_body)
                .setNeutralButton(R.string.button_ok, null)
                .create()
                .show();
    }

    static class SelectedInterestsCompleteEvent {

        private Set<String> selectedInterests;

        private SelectedInterestsCompleteEvent(@NonNull Set<String> selectedInterests) {
            this.selectedInterests = selectedInterests;
        }

        @NonNull
        public Set<String> getSelectedInterests() {
            return selectedInterests;
        }
    }
}
