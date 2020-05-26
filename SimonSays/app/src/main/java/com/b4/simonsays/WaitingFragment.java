package com.b4.simonsays;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class WaitingFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_waiting, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view.findViewById(R.id.button_second).setOnClickListener(view1 -> NavHostFragment.findNavController(WaitingFragment.this)
//                .navigate(R.id.action_SecondFragment_to_FirstFragment));

        RotateAnimation anim = new RotateAnimation(0f, 350f, 0f, 0f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(700);

// Start animating the image
        final ImageView splash = (ImageView) view.findViewById(R.id.iv_waiting);
        splash.startAnimation(anim);
    }
}