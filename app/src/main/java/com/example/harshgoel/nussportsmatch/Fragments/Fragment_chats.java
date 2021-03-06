package com.example.harshgoel.nussportsmatch.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.harshgoel.nussportsmatch.Adapters.ChatBarAdapter;
import com.example.harshgoel.nussportsmatch.Chats.ChatMessageActivity;
import com.example.harshgoel.nussportsmatch.Chats.chatbar;
import com.example.harshgoel.nussportsmatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Harsh Goel on 7/16/2017.
 */

public class Fragment_chats extends Fragment {
    public DatabaseReference ref;
    public FirebaseAuth auth;
    public ListView chats_list;
    public List<chatbar> chats;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment_chats=inflater.inflate(R.layout.chats_display,container,false);

        auth=FirebaseAuth.getInstance();
        final String thisuid=auth.getCurrentUser().getUid();
        ref= FirebaseDatabase.getInstance().getReference();
        ref=ref.child("chats");



        chats_list=(ListView)fragment_chats.findViewById(R.id.ChatListView);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chats=new ArrayList<chatbar>();

                for(DataSnapshot datachats:dataSnapshot.getChildren()) {
                    chatbar singlechat = datachats.getValue(chatbar.class);
                    if (singlechat != null) {
                        if(singlechat.getPlayer1id()!=null || singlechat.getPlayer2id()!=null) {
                            if (singlechat.getPlayer1id().equals(thisuid)
                                    || singlechat.getPlayer2id().equals(thisuid)) {
                                chats.add(singlechat);
                            }
                        }

                    }
                }
                if(getActivity()!=null) {
                    chats_list.setAdapter(new ChatBarAdapter(getActivity(), chats,getActivity()));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return fragment_chats;
    }
}
